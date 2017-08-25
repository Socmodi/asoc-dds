package org.asocframework.dds.rule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by june on 2017/8/25.
 */
public class RulesStore {

    private static Map<String ,TableRule> tableRules = new ConcurrentHashMap();

    public static TableRule get(String logicName){
        return tableRules.get(logicName);
    }

    public static void  register(TableRule tableRule){
        String logicName = tableRule.getLogicName();
        if(!tableRules.containsKey(logicName)){
            tableRules.put(logicName,tableRule);
        }
    }

}
