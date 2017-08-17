package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class DBCon{
    private static Connection con;
	
public static Connection getCon()throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    con = DriverManager.getConnection("jdbc:mysql://localhost/hybridcloud","root","root");
    return con;
}
public static String checkDuplicate(String key)throws Exception{
    String msg="none";
    con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select filename from privilege where token='"+key+"'");
	if(rs.next()){
        msg = rs.getString(1);
    }
    return msg;
}
public static void addKey(String user,String privilege,String filename,String key,String token)throws Exception{
    con = getCon();
    PreparedStatement stat=con.prepareStatement("insert into privilege values(?,?,?,?,?)");
	stat.setString(1,user);
	stat.setString(2,privilege);
	stat.setString(3,filename);
	stat.setString(4,key);
	stat.setString(5,token);
	stat.executeUpdate();
}
}
