package pl.design.patterns.winter.statements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.design.patterns.winter.query.CreateTableQuery;
import pl.design.patterns.winter.schemas.TableSchema;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class CreateTableExecutor {

    @Autowired
    private DataSource dataSource;

    public void createTable(TableSchema tableSchema) {

        String query = CreateTableQuery.prepare(tableSchema);

        // System.out.println(query);

        Connection conn = null;

        try {
            conn = dataSource.getConnection();

            Statement stmt = conn.createStatement();

            stmt.executeUpdate(query);
            System.out.println("Utworzono tabele: " + tableSchema.getTableName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                }
            }
        }

    }
}
