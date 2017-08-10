package org.asocframework.dds.parser;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;

/**
 * Created by june on 2017/8/9.
 */
public class DeleteSqlParser extends AbstractParser  implements Parser  {


	public DeleteSqlParser(Statement statement) {
		this.statement = statement;
	}

	@Override
	public void doInit() {
		registerTable(((Delete) statement).getTable());
	}

}