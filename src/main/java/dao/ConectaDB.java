package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import config.PropertiesLoad;

public class ConectaDB {
    private static final String URL = "jdbc:postgresql://"+PropertiesLoad.loadProperties().getProperty("database");
    private static final String LOGIN = PropertiesLoad.loadProperties().getProperty("userDatabase");
    private static final String SENHA = PropertiesLoad.loadProperties().getProperty("dbPassword");

    public Connection getConnection2(){
        return getConnection();
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL,LOGIN,SENHA);
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
            System.out.println("error connection "+ex.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("usuario ou senha do banco errados "+ex.getMessage());
        }
        return conn;
    }

//    teste
//    public static void main(String[] args) throws ClassNotFoundException {
//        getConnection();
//    }
}
