package pl.design.patterns.winter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WinterDataSource {

    @Value("${winter.datasource.url}")
    private String url;

    @Value("${winter.datasource.driver}")
    private String driverClassName;

    @Value("${winter.datasource.username}")
    private String username;

    @Value("${winter.datasource.password}")
    private String password;

    // @PostConstruct
    // private void configureConnection()
    // throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    //// Class.forName(driverClassName)
    //// .getConstructor()
    //// .newInstance();
    // }
    //
    // public Connection getConnection() throws SQLException {
    //// return DriverManager.getConnection(url, username, password);
    // }
    //
    // public Connection getConnection(String username, String password) throws SQLException {
    //// return DriverManager.getConnection(url, username, password);
    // }
}