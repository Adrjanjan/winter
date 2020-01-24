package pl.design.patterns.winter.statements;

import lombok.extern.apachecommons.CommonsLog;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.query.DeleteQuery;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
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

    public ResultSet delete(int id, Class<?> clazz) {
        String query = DeleteQuery.prepareDelete(id, clazz, inheritanceMapping);

        try (Connection conn = dataSource.getConnection()) {

            Statement stmt = conn.createStatement();
            log.info("Wykonuje delete gdzie (" + id + ")");
            stmt.executeUpdate(query);
            return stmt.getResultSet();

        } catch (SQLException e) {
            log.error("Nie udalo sie wykonać  delete gdzie id =("+id+")");
            throw new RuntimeException(e);
        }
    }
}
