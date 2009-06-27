package vespene.dao;
 
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;


//import vespene.orm.Db;




public class DBConnection {
 
	private String driver;
	private String db;
	private String user;
	private String pass;
	private Connection connection;
	
	public DBConnection() {
		/*
		DbDao dbDao = new DbDao();
		Db dbConfig = dbDao.getDbConfig();
		
		driver = dbConfig.getDriver();
		db = dbConfig.getDb();
		user = dbConfig.getUser();
		pass = dbConfig.getPass();
		*/

	}

	public Connection getConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(db,user,pass);
		}catch (ClassNotFoundException x){
			System.out.print("Class not found "+x);
		}catch (SQLException e){
			System.out.print("BD not found "+e);
		}
		
		return connection;
	}
	
	
	@SuppressWarnings("deprecation")
	public Connection loadDriver(String driverClassPath, String driverClass, String databaseURL, String userName, String passWord) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			Properties props = new Properties(); 
			props.setProperty("user", userName );			
			props.setProperty("password", passWord );
			
			System.out.println("password "+passWord);
			
			File jarFile = new File(driverClassPath);  


			URL jarUrl = jarFile.toURL();
		
			ClassLoader cl = URLClassLoader.newInstance(new URL[] { jarUrl }, this.getClass().getClassLoader());  
		   
		   //Driver driver = (Driver) cl.loadClass( driverClass ).newInstance();  
		   //DriverManager.registerDriver( driver );  
		   //Connection connection = DriverManager.getConnection( databaseURL, userName, passWord );
		   
		   
			Driver driver = (Driver) cl.loadClass( driverClass ).newInstance();
			
			System.out.println( "zzzzzzzzzzzz "+driver.getClass().getName());
			
			Connection 	connection = driver.connect(databaseURL,props);
			
			
			
			/*
			Class.forName(driverClass).newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, userName, passWord);
			*/			
		   
		
		
		
		return connection;
		
	}
	
	

	
	
	
	
	
	
}
