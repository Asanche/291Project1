import java.sql.*;

public class sql {
	
	public static Connection getConnected(String drivername, String dbstring, String username, String password)
		throws Exception{
			Class drvClass = Class.forName(drivername);
			DriverManager.registerDriver((Driver)drvClass.newInstance());
			return(DriverManager.getConnection(dbstring,username,password));
		}
}
