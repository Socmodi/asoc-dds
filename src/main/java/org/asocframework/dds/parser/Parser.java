package org.asocframework.dds.parser;

import net.sf.jsqlparser.schema.Table;

import java.util.List;

/**
 * Created by june on 2017/8/9.
 */
public interface Parser {

    List<Table> getTables();

    String parseSql();

}
