/**
 * 
 */
package org.asocframework.dds.parser;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;
import java.util.List;

/**
 * Created by june on 2017/8/9.
 */
public class UpdateSqlParser extends AbstractParser implements Parser {

	public UpdateSqlParser(Statement statement) {
		this.statement = statement;
	}

	@Override
	public List<Table> getTables() {
		return tables;
	}


	@Override
	public void doInit() {
		Update update = (Update) statement;
		tables.addAll(update.getTables());
	}

}