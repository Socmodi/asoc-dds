package org.asocframework.dds.mybatis;


import net.sf.jsqlparser.schema.Table;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.asocframework.dds.datasource.DtsConnection;
import org.asocframework.dds.parser.Parser;
import org.asocframework.dds.parser.ParserFactory;
import org.asocframework.dds.rule.RulesStore;
import org.asocframework.dds.rule.TableRule;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by june on 2017/8/25.
 */
public class StatementInterceptor implements Interceptor {


    private final static Set<Class<?>> SINGLE_PARAM_CLASSES = new HashSet<Class<?>>();

    private Field sqlField;

    static {
        SINGLE_PARAM_CLASSES.add(int.class);
        SINGLE_PARAM_CLASSES.add(Integer.class);
        SINGLE_PARAM_CLASSES.add(long.class);
        SINGLE_PARAM_CLASSES.add(Long.class);
        SINGLE_PARAM_CLASSES.add(short.class);
        SINGLE_PARAM_CLASSES.add(Short.class);
        SINGLE_PARAM_CLASSES.add(byte.class);
        SINGLE_PARAM_CLASSES.add(Byte.class);
        SINGLE_PARAM_CLASSES.add(float.class);
        SINGLE_PARAM_CLASSES.add(Float.class);
        SINGLE_PARAM_CLASSES.add(double.class);
        SINGLE_PARAM_CLASSES.add(Double.class);
        SINGLE_PARAM_CLASSES.add(boolean.class);
        SINGLE_PARAM_CLASSES.add(Boolean.class);
        SINGLE_PARAM_CLASSES.add(char.class);
        SINGLE_PARAM_CLASSES.add(Character.class);
        SINGLE_PARAM_CLASSES.add(String.class);
    }

    public StatementInterceptor() {
        try {
            sqlField = BoundSql.class.getDeclaredField("sql");
            sqlField.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        Object arg = args[0];
        DtsConnection conn = null;

        if (arg instanceof DtsConnection) {
            conn = (DtsConnection) arg;
        } else {
            if (!Proxy.isProxyClass(arg.getClass())) {
                return invocation.proceed();
            }

            InvocationHandler handler = Proxy.getInvocationHandler(arg);
            if (!(handler instanceof ConnectionLogger)) {
                return invocation.proceed();
            }

            ConnectionLogger connLogger = (ConnectionLogger) handler;
            Connection c = connLogger.getConnection();
            if (c instanceof DtsConnection) {
                conn = (DtsConnection) c;
            } else { // return
                invocation.proceed();
            }
        }

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();

        String originalSql = boundSql.getSql();

        Parser sqlParser = ParserFactory.getInstance().createParser(originalSql);
        List<Table> tables = sqlParser.getTables();

        if (tables.isEmpty()) {
            return invocation.proceed();
        }
        /*默认处理单表的情况*/
        Table table = tables.get(0);
        boolean strict = false;
        String n = table.getName();
        String logicTableName = null;
        if (n.startsWith("`") && n.endsWith("`")) {
            logicTableName = n.substring(1, n.length() - 1);
        } else {
            logicTableName = n;
        }
        TableRule tableRule = RulesStore.get(logicTableName);
        if (tableRule == null) {
            throw new SQLException("Shard Strategy Query Failed");
        }

        Object parameterObject = boundSql.getParameterObject();
        Map<String, Object> params = null;
        if (SINGLE_PARAM_CLASSES.contains(parameterObject.getClass())) {
            // 单一参数
            List<ParameterMapping> mapping = boundSql.getParameterMappings();
            if (mapping != null && !mapping.isEmpty()) {
                ParameterMapping m = mapping.get(0);
                params = new HashMap<String, Object>();
                params.put(m.getProperty(), parameterObject);
            } else {
                params = Collections.emptyMap();
            }
        } else {
            if (parameterObject instanceof Map) {
                params = (Map<String, Object>) parameterObject;
            } else {
                params = new HashMap<String, Object>();
                BeanInfo beanInfo = Introspector.getBeanInfo(parameterObject.getClass());
                PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
                if (proDescrtptors != null && proDescrtptors.length > 0) {
                    for (PropertyDescriptor propDesc : proDescrtptors) {
                        params.put(propDesc.getName(), propDesc.getReadMethod().invoke(parameterObject));
                    }
                }
            }
        }

        String realTable = tableRule.tbParse(params);
        String realDb = tableRule.dbParse(params);
        conn.prepare(realDb);
        for (Table t : tables) {
            n = t.getName();
            if (n.startsWith("`") && n.endsWith("`")) {
                strict = true;
            } else {
                strict = false;
            }
            if (strict) {
                t.setName("`"+realTable+"`");
                t.setSchemaName("`" + realDb + "`");
            } else {
                t.setName(realTable);
                t.setSchemaName(realDb);
            }
        }
        String targetSQL = sqlParser.parseSql();
        sqlField.set(boundSql, targetSQL);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
