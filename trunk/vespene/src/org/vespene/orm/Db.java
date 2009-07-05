package org.vespene.orm;


public class Db {
	private String DriverClassPath;
	private String DriverClass;
	private String DatabaseURL;
	private String UserName;
	private String PassWord;
	
	
	 
	
	public Db() {
	}


	public String getDriverClassPath() {
		return DriverClassPath;
	}


	public void setDriverClassPath(String driverClassPath) {
		DriverClassPath = driverClassPath;
	}


	public String getDriverClass() {
		return DriverClass;
	}


	public void setDriverClass(String driverClass) {
		DriverClass = driverClass;
	}

	public String getDatabaseURL() {
		return DatabaseURL;
	}


	public void setDatabaseURL(String databaseURL) {
		DatabaseURL = databaseURL;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String passWord) {
		PassWord = passWord;
	}

	
	
}
