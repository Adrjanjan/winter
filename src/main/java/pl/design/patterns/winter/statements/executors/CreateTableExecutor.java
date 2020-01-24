package pl.design.patterns.winter.statements.executors;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.design.patterns.winter.exceptions.CouldNotCreateTableException;
import pl.design.patterns.winter.schemas.TableSchema;
import pl.design.patterns.winter.statements.query.CreateTableQuery;
import pl.design.patterns.winter.statements.query.QueryBuildDirector;
import pl.design.patterns.winter.statements.query.QueryBuilder;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Component
public class CreateTableExecutor {

    @Autowired
    private DataSource dataSource;

    @SuppressWarnings("unchecked")
    public <T> void createTable(TableSchema tableSchema) {
        log.info("Tworze tabele...: " + tableSchema.getTableName());

        QueryBuilder builder = new CreateTableQuery();
        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<T>(builder);
        String query;
        try {
            query = queryBuildDirector.withObject((T) tableSchema)
                    .build();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new CouldNotCreateTableException(String.format("Table %s could not be created ", tableSchema.getTableName()), e);
        }

        try (Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            log.info("Created table: " + tableSchema.getTableName());
        } catch (SQLException e) {
            log.error("Could not crate table " + tableSchema.getTableName());
            throw new CouldNotCreateTableException(String.format("Table %s could not be created ", tableSchema.getTableName()), e);
        }
    }
}
