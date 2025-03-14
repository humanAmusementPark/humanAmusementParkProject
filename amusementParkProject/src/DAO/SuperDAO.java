package javaproject.DAO;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SuperDAO<T> {
    private String url = "jdbc:mariadb://localhost:3306/mysql";
    private String user = "root";
    private String password = "park1676";
    private Connection conn;

    public SuperDAO() throws SQLException {
        this.conn = DriverManager.getConnection(url, user, password);
    }

    public Connection getConnection() {
        return conn;
    }

    public List<T> selectAll(){
        return null;
    }
    public void insert(){

    }
    public void update(int choiceNum){

    }
    public void delete(int choiceNum){

    }

}
