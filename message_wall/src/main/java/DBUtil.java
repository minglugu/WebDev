import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
    // 连接数据库的地址
    private static final String URL= "jdbc:mysql://127.0.0.1:3306/java102?characterEncoding=utf8&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "111111";

    // 单例懒汉模式
    private volatile static DataSource dataSource = null;

    // 涉及到线程安全问题，所以可以通过加锁
    private static DataSource getDataSource() {
        // 双重校验锁，来保证线程安全，同时避免程序因为锁竞争，降低效率
        // 如果实例已经创建好，则再次调用时，不会出现锁竞争的不安全问题
        if(dataSource == null) {
            // 针对类对象，加锁
            synchronized (DBUtil.class) {
                if(dataSource == null) {
                    dataSource = new MysqlDataSource();
                    // 构造对象
                    ((MysqlDataSource) dataSource).setURL(URL);
                    ((MysqlDataSource) dataSource).setUser(USERNAME);
                    ((MysqlDataSource) dataSource).setPassword(PASSWORD);
                }
            }
        }
        return dataSource;
    }

    // 获取连接
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    // 关闭连接的 close()
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement != null) {
            try {
                statement.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection != null) {
            try {
                connection.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
