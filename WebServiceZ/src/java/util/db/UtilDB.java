package util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francesco Forcellato
 */
public class UtilDB {
    
    private static final String NOME_DB = "Social.db";
    private static final String PATH = "/home/force/NetBeansProjects/WebServiceZ/";
    //private static final String CREA_DB = "src/java/util/sql/creaDB.sql";
    private static Connection conn;
    
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + PATH + NOME_DB);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }
    
    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}
