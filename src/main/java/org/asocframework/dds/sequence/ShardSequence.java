package org.asocframework.dds.sequence;

import org.asocframework.dds.datasource.DdsDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiqing
 * @version $Id: ShardSequence，v 1.0 2018/3/21 14:22 jiqing Exp $
 * @desc
 */
public class ShardSequence implements Sequence{

    private String logicSequence;

    private DdsDataSource ddsDataSource;

    private String dbSuffix;

    private String tbSuffix;

    private AtomicInteger sequence = new AtomicInteger();

    private Integer maxSequence = 0;

    private Integer step = 10;

    private Integer maxSize;

    public ShardSequence(DdsDataSource ddsDataSource, String dbSuffix, String logicSequence, String tbSuffix,Integer maxSize) {
        this.ddsDataSource = ddsDataSource;
        this.dbSuffix = dbSuffix;
        this.logicSequence = logicSequence;
        this.tbSuffix = tbSuffix;
        this.maxSize = maxSize;
        if(step>maxSize){
            step = maxSize;
        }
        maxSequence = step;
        refresh();
    }

    @Override
    public int next() {
        int next = sequence.incrementAndGet();
        if(next<=maxSequence){
            return next;
        }else {
            refresh();
            return next();
        }
    }

    public void refresh(){
        DataSource dataSource = ddsDataSource.getDataSource(ddsDataSource.getDbName()+"_"+dbSuffix);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(true);
            doRefresh(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void doRefresh(Connection connection) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            int nowStep = getCurrentStep(connection);
            int toStep = 0;
            if(nowStep < maxSize){
                toStep = nowStep +this.step;
            }
            if(toStep>maxSize){
                toStep = maxSize;
            }
            String updateSql = "UPDATE `" + logicSequence +"_"  + tbSuffix
                    + "` SET `sequence`=? WHERE `sequence`=?";
            ps = connection.prepareStatement(updateSql);
            ps.setInt(1, toStep);
            ps.setInt(2, nowStep);
            int result = ps.executeUpdate();
            if (result > 0) {
                maxSequence = toStep;
                sequence = new AtomicInteger(nowStep);
            } else {
                doRefresh(connection);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }

    }

    public int getCurrentStep(Connection connection) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM `" + logicSequence +"_" +tbSuffix
                    + "` LIMIT 1";

            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next()) {
                doRefresh(connection);
            }
            Integer nowStep = rs.getInt("sequence");
            return nowStep;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }


    public DdsDataSource getDdsDataSource() {
        return ddsDataSource;
    }

    public void setDdsDataSource(DdsDataSource ddsDataSource) {
        this.ddsDataSource = ddsDataSource;
    }

    public String getDbSuffix() {
        return dbSuffix;
    }

    public void setDbSuffix(String dbSuffix) {
        this.dbSuffix = dbSuffix;
    }

    public String getTbSuffix() {
        return tbSuffix;
    }

    public void setTbSuffix(String tbSuffix) {
        this.tbSuffix = tbSuffix;
    }

    public AtomicInteger getSequence() {
        return sequence;
    }

    public void setSequence(AtomicInteger sequence) {
        this.sequence = sequence;
    }

    public Integer getMaxSequence() {
        return maxSequence;
    }

    public void setMaxSequence(Integer maxSequence) {
        this.maxSequence = maxSequence;
    }

    public String getLogicSequence() {
        return logicSequence;
    }

    public void setLogicSequence(String logicSequence) {
        this.logicSequence = logicSequence;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }
}
