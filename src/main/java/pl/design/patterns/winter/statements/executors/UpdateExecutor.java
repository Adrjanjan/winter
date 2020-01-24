package pl.design.patterns.winter.statements.executors;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import pl.design.patterns.winter.exceptions.CouldNotSelectFromTableException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.statements.query.QueryBuildDirector;
import pl.design.patterns.winter.statements.query.QueryBuilder;
import pl.design.patterns.winter.statements.query.UpdateQueryBuilder;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class UpdateExecutor {
    private DataSource dataSource;

    private InheritanceMapping inheritanceMapping;

    public void setInheritanceMapping(InheritanceMapping inheritanceMapping) {
        this.inheritanceMapping = inheritanceMapping;
    }

    public UpdateExecutor(DataSource dataSource, InheritanceMapping inheritanceMapping) {
        this.dataSource = dataSource;
        this.inheritanceMapping = inheritanceMapping;
    }

    public <T> ResultSet update(T objectToUpdate) {

        QueryBuilder builder = new UpdateQueryBuilder(inheritanceMapping);
        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(builder);
        String query;

        try {
            query = queryBuildDirector.withObject(objectToUpdate)
                    .build();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new CouldNotSelectFromTableException(String.format("Could not update object %s", objectToUpdate.toString()), e);
        }

        try (Connection conn = dataSource.getConnection()) {

            Statement stmt = conn.createStatement();
            log.info("Wykonuje update na : <tu moze dac ten obiekt>");
            stmt.executeUpdate(query);
            return stmt.getResultSet();

        } catch (SQLException e) {
            log.error("Nie udalo sie wykonać  update ");
            throw new RuntimeException(e);
        }
    }
}
