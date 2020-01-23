package pl.design.patterns.winter.statements;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.design.patterns.winter.query.CreateTableQuery;
import pl.design.patterns.winter.query.QueryBuilder;
import pl.design.patterns.winter.schemas.TableSchema;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@CommonsLog
@Component
public class CreateTableExecutor {

    @Autowired
    private DataSource dataSource;

    public void createTable(TableSchema tableSchema) {
        log.info("Tworze tabele...: " + tableSchema.getTableName());

        QueryBuilder builder = new CreateTableQuery();
        String query = builder.setObject(tableSchema)
                .createOperation()
                .setTable()
                .setFields()
                .generate();

        try (Connection conn = dataSource.getConnection()) {

            // TODO: mozna dodac najpierw sprawdzenie
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            log.info("Tabela utworzona: " + tableSchema.getTableName());

        } catch (SQLException e) {

            log.error("Nie udalo sie utworzyc tabeli " + tableSchema.getTableName());
            throw new RuntimeException(e);

        }

    }
}
