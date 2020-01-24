package pl.design.patterns.winter.statements;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import pl.design.patterns.winter.exceptions.CouldNotInsertIntoTableException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.query.InsertQueryBuilder;
import pl.design.patterns.winter.query.QueryBuildDirector;
import pl.design.patterns.winter.query.QueryBuilder;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class InsertExecutor {
    private DataSource dataSource;

    private InheritanceMapping inheritanceMapping;

    public void setInheritanceMapping(InheritanceMapping inheritanceMapping) {
        this.inheritanceMapping = inheritanceMapping;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public InsertExecutor(DataSource dataSource, InheritanceMapping inheritanceMapping) {
        this.dataSource = dataSource;
        this.inheritanceMapping = inheritanceMapping;
    }

    public <T> void execute(T object) {
        log.info("Insert object of class: " + object.getClass()
                .getName());

        QueryBuilder builder = new InsertQueryBuilder(inheritanceMapping);
        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(builder);
        String query;
        try {
            query = queryBuildDirector.withObject(object)
                    .build();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new CouldNotInsertIntoTableException(String.format("Could not insert object %s", object.toString()), e);
        }

        try (Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            log.info("Inserted  object of class: " + object.getClass()
                    .toString());
        } catch (SQLException e) {
            log.error("Could not insert object of class: " + object.getClass()
                    .toString());
            throw new CouldNotInsertIntoTableException(String.format("Could not insert object %s", object.toString()), e);

        }
    }
}
