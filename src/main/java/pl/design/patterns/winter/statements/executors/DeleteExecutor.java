package pl.design.patterns.winter.statements.executors;

import lombok.extern.apachecommons.CommonsLog;
import pl.design.patterns.winter.exceptions.CouldNotInsertIntoTableException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.statements.query.DeleteQueryBuilder;
import pl.design.patterns.winter.statements.query.QueryBuildDirector;
import pl.design.patterns.winter.statements.query.QueryBuilder;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@CommonsLog
public class DeleteExecutor {

    private DataSource dataSource;

    private InheritanceMapping inheritanceMapping;

    public void setInheritanceMapping(InheritanceMapping inheritanceMapping) {
        this.inheritanceMapping = inheritanceMapping;
    }

    public DeleteExecutor(DataSource dataSource, InheritanceMapping inheritanceMapping) {
        this.dataSource = dataSource;
        this.inheritanceMapping = inheritanceMapping;
    }

    public <T> void execute(int id, Class clazz) {
        log.info("Deleting object of the class: " + clazz);

        QueryBuilder builder = new DeleteQueryBuilder(inheritanceMapping);
        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(builder);
        String query;
        try {
            query = queryBuildDirector.withObject((T)clazz).withCondition(id)
                    .build();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new CouldNotInsertIntoTableException(String.format("Could not delete object %s", clazz.toString()), e);
        }

        try (Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            log.info("Deleted object of class: " + clazz
                    .toString());
        } catch (SQLException e) {
            log.error("Could not delete object of class: " + clazz
                    .toString());
            throw new CouldNotInsertIntoTableException(String.format("Could not delete object %s", clazz.toString()), e);

        }
    }
}
