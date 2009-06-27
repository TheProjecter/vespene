package vespene.daoh2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectH2 {

	String fullPath;
	
	public ConnectH2() {
		super();
	}

	
	public Connection connect() {
		
		Connection connection = null;
        try {
			Class.forName("org.h2.Driver");
			//connection = DriverManager.getConnection("jdbc:h2:"+fullPath+"/.vespene/vespenedb", "sa", "");
			connection = DriverManager.getConnection("jdbc:h2:"+fullPath, "sa", "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}


	public String getFullPath() {
		return fullPath;
	}


	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	
	

	

}
