package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/pooweb";
    private static final String LOGIN = "postgres";
    private static final String SENHA = "1234";

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
