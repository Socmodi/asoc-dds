package org.asocframework.dds.parser;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.deparser.StatementDeParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by june on 2017/8/9.
 */
public abstract class AbstractParser {

    protected volatile boolean inited = false;

    protected Statement statement;

    protected List<Table> tables = new ArrayList<Table>();

    public void init() {
        if (inited) {
            return;
        }
        inited = true;
        doInit();
    }

    public abstract void doInit();

    public String parseSql(){
        StatementDeParser deParser = new StatementDeParser(new StringBuilder());
        statement.accept(deParser);
        return deParser.getBuffer().toString();
    }

    public List<Table> getTables(){
        return tables;
    }


    public void registerTable(Table table){
        tables.add(table);
    }

    public void registerTables(Collection<Table> tables){
        tables.addAll(tables);
    }

}
