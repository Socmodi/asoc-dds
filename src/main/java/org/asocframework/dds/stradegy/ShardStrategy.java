package org.asocframework.dds.stradegy;

import java.util.Map;

/**
 * Created by june on 2017/8/25.
 */
public interface ShardStrategy {

    String parse(String logicName,Map params);

}
