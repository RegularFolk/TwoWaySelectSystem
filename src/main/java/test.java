import com.utils.JDBCUtil;

import java.sql.Connection;

public class test {
    public static void main(String[] args) {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
    }
}
