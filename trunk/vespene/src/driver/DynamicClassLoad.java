package driver;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;

public class DynamicClassLoad {
	private String jarpath;

	public DynamicClassLoad() {
		//super();
		// TODO Auto-generated constructor stub
	}

	
	public void loadClass() {
		
		File jarFile = new File(jarpath);  
		//URL jarUrl = jarFile.toURL();  
	//	ClassLoader cl = URLClassLoader.newInstance(new URL[] { jarUrl }, this.getClass().getClassLoader());  
	//	Driver driver = (Driver) cl.loadClass( driverName ).newInstance();
		
		//Driver driver = (Driver) cl.loadClass( driverName ).newInstance();
		//conn = driver.connect("url",propertiesComUserePassword); 		
		
		//DriverManager.registerDriver( driver );  
		//conn = DriverManager.getConnection( "url", "user", "pass" ); 		
		
	}
	
	
	
	



	public String getJarpath() {
		return jarpath;
	}


	public void setJarpath(String jarpath) {
		this.jarpath = jarpath;
	}
	
	
	
	
	
	

}
