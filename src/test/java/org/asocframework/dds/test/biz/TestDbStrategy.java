package org.asocframework.dds.test.biz;

import org.asocframework.dds.stradegy.DataSourceShardStrategy;

import java.util.Map;

/**
 * Created by june on 2017/8/30.
 */
public class TestDbStrategy extends DataSourceShardStrategy{


    @Override
    public String parse(String logicName, Map params) {
        if(params.containsKey("txId")){
            return "db_1";
        }
        return "db";
    }
}
