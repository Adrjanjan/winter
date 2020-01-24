package pl.design.patterns.winter.statements;

import lombok.extern.apachecommons.CommonsLog;
import pl.design.patterns.winter.exceptions.CouldNotInsertIntoTableException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.query.DeleteQueryBuilder;
import pl.design.patterns.winter.query.QueryBuildDirector;
import pl.design.patterns.winter.query.QueryBuilder;

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

    public <T> void execute(T object) {
        log.info("Deleting object of the class: " + object.getClass());

        QueryBuilder builder = new DeleteQueryBuilder(inheritanceMapping);
        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(builder);
        String query;
        try {
            query = queryBuildDirector.withObject(object)
                    .build();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new CouldNotInsertIntoTableException(String.format("Could not delete object %s", object.toString()), e);
        }

        try (Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            log.info("Deleted object of class: " + object.getClass()
                    .toString());
        } catch (SQLException e) {
            log.error("Could not delete object of class: " + object.getClass()
                    .toString());
            throw new CouldNotInsertIntoTableException(String.format("Could not delete object %s", object.toString()), e);

        }
    }
}
