package hybridcloud;
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

public static String register(String[] input)throws Exception{
    String msg="fail";
    boolean flag=false;
    boolean flag1=false;
    con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select user from newemp where user='"+input[0]+"'");
    if(rs.next()){
        flag=true;
        msg = "Username already exist";
    }
    stmt=con.createStatement();
    rs=stmt.executeQuery("select pass from newemp where pass='"+input[1]+"'");
    if(rs.next() && !flag){
        flag1=true;
        msg = "Password already exist";
    }
    if(!flag && !flag1){
		PreparedStatement stat=con.prepareStatement("insert into newemp values(?,?,?,?,?,?,?)");
		stat.setString(1,input[0]);
		stat.setString(2,input[1]);
		stat.setString(3,input[2]);
		stat.setString(4,input[3]);
		stat.setString(5,input[4]);
		stat.setString(6,input[5]);
		stat.setString(7,input[6]);
		int i=stat.executeUpdate();
		if(i > 0){
			msg = "success";
		}
    }
    return msg;
}
public static String login(String input[])throws Exception{
    String msg="fail";
    con = getCon();
    System.out.println(input[0]);
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select designation from newemp where user='"+input[0]+"' && pass='"+input[1]+"'");
    if(rs.next()){
        msg = rs.getString(1);
    }
    return msg;
}
public static String recover(String user,String email)throws Exception{
    String msg="fail";
    con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select designation from newemp where user='"+user+"' && email_id='"+email+"'");
    if(rs.next()){
        msg = rs.getString(1);
    }
    return msg;
}
}
