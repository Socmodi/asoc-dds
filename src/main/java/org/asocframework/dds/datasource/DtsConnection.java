package org.asocframework.dds.datasource;


import javax.sql.DataSource;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by june on 2017/8/21.
 */
public class DtsConnection implements Connection{

    private static Logger LOGGER = LoggerFactory.getLogger(DtsConnection.class);

    private Connection instance = null;

    private boolean autoCommit = true;

    private boolean closed = false;

    private boolean prepared = false;

    private DdsDataSource ddsDataSource;

    public DtsConnection(DdsDataSource ddsDataSource) {
        this.ddsDataSource = ddsDataSource;
    }

    public DdsDataSource getDdsDataSource() {
        return ddsDataSource;
    }

    public void setDdsDataSource(DdsDataSource ddsDataSource) {
        this.ddsDataSource = ddsDataSource;
    }

    public void prepare(String dbsuffix)throws SQLException {
        if (prepared) {
            throw new SQLException("Shard Proxy Connection Initialized");
        }
        DataSource dataSource = ddsDataSource.getDataSource(dbsuffix);
        instance = dataSource.getConnection();
        instance.setAutoCommit(autoCommit);

    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.isWrapperFor(iface);
    }

    @Override
    public Statement createStatement() throws SQLException {

        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        if (instance == null) {
            this.autoCommit = autoCommit;
        } else {
            instance.setAutoCommit(autoCommit);
        }
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        if (instance == null) {
            return autoCommit;
        } else {
            return instance.getAutoCommit();
        }
    }

    @Override
    public void commit() throws SQLException {
        if (instance != null) {
            instance.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (instance != null) {
            instance.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        if (instance != null) {
            instance.close();
        } else {
            closed = true;
        }
    }

    @Override
    public boolean isClosed() throws SQLException {
        if (instance != null) {
            return instance.isClosed();
        } else {
            return closed;
        }
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        instance.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        instance.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        instance.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        instance.clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        instance.setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        instance.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
        return instance.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        if (instance != null) {
            instance.rollback(savepoint);
        }
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        if (instance != null) {
            instance.releaseSavepoint(savepoint);
        }
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                              int resultSetHoldability) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                         int resultSetHoldability) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.prepareStatement(sql, columnIndexes);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.prepareStatement(sql, columnNames);
    }

    @Override
    public Clob createClob() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        if (instance == null) {
            throw new RuntimeException("Shard Proxy Connection Uninitialized");
        }
        instance.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        if (instance == null) {
            throw new RuntimeException("Shard Proxy Connection Uninitialized");
        }
        instance.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        if (instance == null) {
            throw new SQLException("Shard Proxy Connection Uninitialized");
        }
        return instance.createStruct(typeName, attributes);
    }

    public void setSchema(String schema) throws SQLException {

    }

    public String getSchema() throws SQLException {
        return null;
    }

    public void abort(Executor executor) throws SQLException {

    }


    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    public int getNetworkTimeout() throws SQLException {
        return 0;
    }
}
