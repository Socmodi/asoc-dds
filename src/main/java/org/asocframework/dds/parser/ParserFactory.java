package org.asocframework.dds.parser;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import java.io.StringReader;
import java.sql.SQLException;

/**
 * Created by june on 2017/8/9.
 */
public class ParserFactory {

    private static ParserFactory instance = new ParserFactory();

    public static ParserFactory getInstance() {
        return instance;
    }

    private final CCJSqlParserManager manager;

    public ParserFactory() {
        manager = new CCJSqlParserManager();
    }

    public Parser createParser(String originalSql) throws SQLException {
        try {
            Statement statement = manager.parse(new StringReader(originalSql));
            if (statement instanceof Select) {

                return new SelectSqlParser(statement);
            } else if (statement instanceof Update) {

                return new UpdateSqlParser(statement);
            } else if (statement instanceof Insert) {

                return new InsertSqlParser(statement);
            } else if (statement instanceof Delete) {

                return new DeleteSqlParser(statement);
            } else {
                throw new SQLException("Unsupported Parser[" + statement.getClass().getName() + "]");
            }
        } catch (Exception e) {
            throw new SQLException("SQL Parse Failed", e);
        }

    }

}
