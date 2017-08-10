/**
 * 
 */
package org.asocframework.dds.parser;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;
import java.util.List;

/**
 * @author fangjialong
 * @date 2015年9月5日 下午3:55:28
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