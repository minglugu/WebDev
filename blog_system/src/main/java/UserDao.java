import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 提供了 针对 用户表 的基本操作
public class UserDao {
    // 需要实现的操作
    // 针对这个类来说，就简化的写就型了。类似 注册/销号这样的功能就暂不考虑

    // 主要实现
    // 1.   根据用户名来查找用户信息。
    //      会在登录逻辑中使用
    public User selectByName(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from user where username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            // 查询（executeQuery），新增删除修改（executeUpdate）
            resultSet = statement.executeQuery();
            // 此处的 username 用了 unique 约束，要么查到一个，要么一个都查不到
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }

        return null;
    }

    // 2.   根据用户 id 来找用户信息
    //      博客详情页，就可以根据用户 id 来查询作者的名字，把作者的名字显示出来
    public User selectById(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from user where userId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            // 查询（executeQuery），新增删除修改（executeUpdate）
            resultSet = statement.executeQuery();
            // 此处的 username 用了 unique 约束，要么查到一个，要么一个都查不到
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }
}
