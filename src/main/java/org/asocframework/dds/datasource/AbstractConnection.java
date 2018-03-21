package org.asocframework.dds.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by june on 2017/8/21.
 */
public abstract class AbstractConnection extends AbstractUnsuportConnction{

    private boolean autoCommit = true;

    private boolean readOnly = true;

    private boolean closed;

    private int transactionIsolation = TRANSACTION_READ_UNCOMMITTED;

    protected Collection<Connection> connections;

    //protected abstract Collection<Connection> getConnections();

    @Override
    public final boolean getAutoCommit() throws SQLException {
        return autoCommit;
    }

    @Override
    public final void setAutoCommit(final boolean autoCommit) throws SQLException {
        this.autoCommit = autoCommit;
        if (connections.isEmpty()) {
            return;
        }
        for (Connection each : connections) {
            each.setAutoCommit(autoCommit);
        }
    }

    @Override
    public final void commit() throws SQLException {
        for (Connection each : connections) {
            each.commit();
        }
    }

    @Override
    public final void rollback() throws SQLException {
        Collection<SQLException> exceptions = new LinkedList();
        for (Connection each : connections) {
            try {
                each.rollback();
            } catch (final SQLException ex) {
                exceptions.add(ex);
            }
        }
        //throwSQLExceptionIfNecessary(exceptions);
    }

    @Override
    public void close() throws SQLException {
        closed = true;
        Collection<SQLException> exceptions = new LinkedList();
        for (Connection each : connections) {
            try {
                each.close();
            } catch (final SQLException ex) {
                exceptions.add(ex);
            }
        }
        //throwSQLExceptionIfNecessary(exceptions);
    }

    @Override
    public final boolean isClosed() throws SQLException {
        return closed;
    }

    @Override
    public final boolean isReadOnly() throws SQLException {
        return readOnly;
    }

    @Override
    public final void setReadOnly(final boolean readOnly) throws SQLException {
        this.readOnly = readOnly;
        if (connections.isEmpty()) {
            return;
        }
        for (Connection each : connections) {
            each.setReadOnly(readOnly);
        }
    }

    @Override
    public final int getTransactionIsolation() throws SQLException {
        return transactionIsolation;
    }

    @Override
    public final void setTransactionIsolation(final int level) throws SQLException {
        transactionIsolation = level;
        if (connections.isEmpty()) {
            return;
        }
        for (Connection each : connections) {
            each.setTransactionIsolation(level);
        }
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
    }

    @Override
    public final int getHoldability() throws SQLException {
        return ResultSet.CLOSE_CURSORS_AT_COMMIT;
    }

    @Override
    public final void setHoldability(final int holdability) throws SQLException {
    }

    @Override
    public final <T> T unwrap(final Class<T> iface) throws SQLException {
        if (isWrapperFor(iface)) {
            return (T) this;
        }
        throw new SQLException(String.format("[%s] cannot be unwrapped as [%s]", getClass().getName(), iface.getName()));
    }

    @Override
    public final boolean isWrapperFor(final Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }

}
