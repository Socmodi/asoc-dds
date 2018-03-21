package org.asocframework.dds.sequence;

import org.asocframework.dds.datasource.DdsDataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiqing
 * @version $Id: SequenceFactory，v 1.0 2018/3/21 15:36 jiqing Exp $
 * @desc
 */
public class SequenceFactory {

    private Integer maxSize;

    private String logicSequence;

    private DdsDataSource ddsDataSource;

    private Map<String,Boolean> sequenceTables = new ConcurrentHashMap();

    private Map<String,Sequence> sequences = new ConcurrentHashMap();



    public int next(String table, String dbSuffix,String tbSuffix) {
/*        if(!sequenceTables.containsKey(table)){
            throw new DdsException(table+"无法使用Sequence");
        }*/
        int next;
        String sequenceName = logicSequence+"_"+tbSuffix;
        Sequence sequence = sequences.get(sequenceName);
        if(sequence==null) {
            sequence = new ShardSequence(ddsDataSource, dbSuffix,logicSequence, tbSuffix, maxSize);
            sequences.put(sequenceName,sequence);
        }
        return sequence.next();
    }


    public String getLogicSequence() {
        return logicSequence;
    }

    public void setLogicSequence(String logicSequence) {
        this.logicSequence = logicSequence;
    }

    public DdsDataSource getDdsDataSource() {
        return ddsDataSource;
    }

    public void setDdsDataSource(DdsDataSource ddsDataSource) {
        this.ddsDataSource = ddsDataSource;
    }

    public Map<String, Boolean> getSequenceTables() {
        return sequenceTables;
    }

    public void setSequenceTables(Map<String, Boolean> sequenceTables) {
        this.sequenceTables = sequenceTables;
    }

    public Map<String, Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(Map<String, Sequence> sequences) {
        this.sequences = sequences;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }
}
