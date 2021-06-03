package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DOMAIN_NAME = "localhost:3306";
    static final String DB_NAME = "lms-db";
    static final String DB_URL = "jdbc:mysql://" + DOMAIN_NAME + "/" + DB_NAME + "?autoReconnect=true&useSSL=false";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "mysql";

    private Connection connection;

    public Connection connect() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("BAĞLANDIIII");
        } catch (Exception e) {
            System.out.println("HATA!!!");
            System.out.println(e.getMessage());
        }
        return this.connection;
    }
}
