package pl.design.patterns.winter.statements;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@CommonsLog
@Component
public class DropTablesExecutor {

    @Autowired
    private DataSource dataSource;

    public void dropTables() {

        String query = "DROP SCHEMA public CASCADE; CREATE SCHEMA public;";

        try (Connection conn = dataSource.getConnection()) {

            // TODO: mozna dodac najpierw sprawdzenie
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            log.info("Tabele usuniete");

        } catch (SQLException e) {

            log.error("Nie udalo sie dropnac tabeli");
            throw new RuntimeException(e);

        }

    }

}
