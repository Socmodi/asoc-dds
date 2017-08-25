package org.asocframework.dds.rule;

import org.asocframework.dds.stradegy.ShardStrategy;
import java.util.Map;

/**
 * Created by june on 2017/8/21.
 */
public class TableRule {

    private String logicName;

    private String tableSuffix;

    private String dbSuffix;

    private String[] dbList;

    private String tableRules;

    private String dbRules;

    private String fields[];

    private ShardStrategy tbStrategy;

    private ShardStrategy dbStrategy;


    public String getLogicName() {
        return logicName;
    }

    public void setLogicName(String logicName) {
        this.logicName = logicName;
    }

    public String getTableSuffix() {
        return tableSuffix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }

    public String getDbSuffix() {
        return dbSuffix;
    }

    public void setDbSuffix(String dbSuffix) {
        this.dbSuffix = dbSuffix;
    }

    public String[] getDbList() {
        return dbList;
    }

    public void setDbList(String[] dbList) {
        this.dbList = dbList;
    }

    public String getTableRules() {
        return tableRules;
    }

    public void setTableRules(String tableRules) {
        this.tableRules = tableRules;
    }

    public String getDbRules() {
        return dbRules;
    }

    public void setDbRules(String dbRules) {
        this.dbRules = dbRules;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public ShardStrategy getTbStrategy() {
        return tbStrategy;
    }

    public void setTbStrategy(ShardStrategy tbStrategy) {
        this.tbStrategy = tbStrategy;
    }

    public ShardStrategy getDbStrategy() {
        return dbStrategy;
    }

    public void setDbStrategy(ShardStrategy dbStrategy) {
        this.dbStrategy = dbStrategy;
    }

    public String tbParse(Map params){
        return tbStrategy.parse(logicName,params);
    }

    public String dbParse(Map parmas){
        return dbStrategy.parse(logicName,parmas);
    }


}
