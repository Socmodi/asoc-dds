/**
 * 
 */
package org.asocframework.dds.parser;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;

/**
 * Created by june on 2017/8/9.
 */
public class InsertSqlParser extends AbstractParser  implements Parser {

	public InsertSqlParser(Statement statement) {
		this.statement = statement;
	}

	@Override
	public void doInit() {
		Insert insert = (Insert) statement;
		registerTable(insert.getTable());
	}


}
