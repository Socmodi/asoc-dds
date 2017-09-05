package org.asocframework.dds.test.biz;

import org.asocframework.dds.stradegy.TableShardStrategy;

import java.util.Map;

/**
 * Created by june on 2017/8/30.
 */
public class TestTbStrategy extends TableShardStrategy{


    @Override
    public String parse(String logicName, Map params) {
        if(params.containsKey("txId")){
            return "asset_01";
        }
        return "asset";
    }
}
