package org.asocframework.dds.parser;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;

/**
 * @author fangjialong
 * @date 2015年9月5日 下午3:54:27
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