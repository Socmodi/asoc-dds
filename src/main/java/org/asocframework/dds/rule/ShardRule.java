package org.asocframework.dds.rule;

import java.util.Map;

/**
 * Created by june on 2017/8/21.
 */
public class ShardRule {

    private Map<String ,TableRule> tableRules;

    public Map<String, TableRule> getTableRules() {
        return tableRules;
    }

    public void setTableRules(Map<String, TableRule> tableRules) {
        this.tableRules = tableRules;
    }
}
