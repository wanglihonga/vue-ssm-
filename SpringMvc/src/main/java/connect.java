import java.sql.*;

/*
 * 数据库连接
 */
public class connect {
    public static void main(String[] args) {
        Connection con;
        //jdbc驱动
        String driver="com.mysql.cj.jdbc.Driver";
        //这里我的数据库是cxxt
        String url="jdbc:mysql://localhost:3306/test?&useSSL=false&serverTimezone=UTC";
        String user="root";
        String password="root";
        try {
            //注册JDBC驱动程序
            Class.forName(driver);
            //建立连接
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed()) {
                System.out.println("数据库连接成功");
            }
            Statement statement = con.createStatement();
            String sql = "select * from user;";//我的表格叫home
            ResultSet resultSet = statement.executeQuery(sql);
            String name;
            while (resultSet.next()) {
                name = resultSet.getString("userName");
                System.out.println("姓名：" + name);
            }
            resultSet.close();
            con.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动没有安装");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }
    }
}