package pl.design.patterns.winter.statements;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.design.patterns.winter.query.InsertQueryBuilder;
import pl.design.patterns.winter.query.QueryBuildDirector;
import pl.design.patterns.winter.query.QueryBuilder;
import pl.design.patterns.winter.schemas.DatabaseSchema;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Component
public class InsertExecutor {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private DatabaseSchema databaseSchema;

    public <T> void execute(T object) throws InvocationTargetException, IllegalAccessException {
        log.info("Insert object of class: " + object.getClass()
                .getName());
        var inheritanceMapping = databaseSchema.getMapping(object.getClass());

        QueryBuilder builder = new InsertQueryBuilder(inheritanceMapping);
        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(builder);
        String query = queryBuildDirector.withObject(object)
                .build();

        try (Connection conn = dataSource.getConnection()) {

            // TODO: mozna dodac najpierw sprawdzenie
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            log.info("Inserted  object of class: " + object.getClass()
                    .toString());

        } catch (SQLException e) {

            log.error("Could not insert object of class:  " + object.getClass()
                    .toString());
            throw new RuntimeException(e);

        }
    }
}
