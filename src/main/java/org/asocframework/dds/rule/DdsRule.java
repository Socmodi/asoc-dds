package org.asocframework.dds.rule;

/**
 * Created by june on 2017/8/21.
 */
public class DdsRule {

    public static final String DBINDEX_SUFFIX_READ  = "_r";
    public static final String DBINDEX_SUFFIX_WRITE = "_w";
    private ShardRule shardRule;

    public ShardRule getShardRule() {
        return shardRule;
    }

    public void setShardRule(ShardRule shardRule) {
        this.shardRule = shardRule;
    }
}
