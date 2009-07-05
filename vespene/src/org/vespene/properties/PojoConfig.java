package org.vespene.properties;

public class PojoConfig {
	
	private String driverClassPath;
	private String driverClass;
	private String databaseURL;
	private String userName;
	private String passWord;
	
	private String pathSearchView;
	private String pathWindowView;
	private String pathDatasetView;
	private String pathQueryClass;
	private String pathSearchClass;
	private String pathExtendedPojoClass;
	private String pathServletClass;
	
	
	public PojoConfig() {
		System.out.println("PojoConfig****************");
		//super();
	}


	public String getPathSearchView() {
		return pathSearchView;
	}


	public void setPathSearchView(String pathSearchView) {
		this.pathSearchView = pathSearchView;
	}


	public String getPathWindowView() {
		return pathWindowView;
	}


	public void setPathWindowView(String pathWindowView) {
		this.pathWindowView = pathWindowView;
	}


	public String getPathDatasetView() {
		return pathDatasetView;
	}


	public void setPathDatasetView(String pathDatasetView) {
		this.pathDatasetView = pathDatasetView;
	}


	public String getPathQueryClass() {
		return pathQueryClass;
	}


	public void setPathQueryClass(String pathQueryClass) {
		this.pathQueryClass = pathQueryClass;
	}


	public String getPathSearchClass() {
		return pathSearchClass;
	}


	public void setPathSearchClass(String pathSearchClass) {
		this.pathSearchClass = pathSearchClass;
	}


	public String getPathExtendedPojoClass() {
		return pathExtendedPojoClass;
	}


	public void setPathExtendedPojoClass(String pathExtendedPojoClass) {
		this.pathExtendedPojoClass = pathExtendedPojoClass;
	}


	public String getPathServletClass() {
		return pathServletClass;
	}


	public void setPathServletClass(String pathServletClass) {
		this.pathServletClass = pathServletClass;
	}


	public String getDriverClassPath() {
		return driverClassPath;
	}


	public void setDriverClassPath(String driverClassPath) {
		this.driverClassPath = driverClassPath;
	}


	public String getDriverClass() {
		return driverClass;
	}


	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}





	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassWord() {
		return passWord;
	}


	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public String getDatabaseURL() {
		return databaseURL;
	}


	public void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}
	

	

}
