package vespene.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

public class CopyOfConfig {
	private IProject proj;

	private String path;
	
	
	
	public CopyOfConfig() {
		super();
	}


	public void persistConfig(PojoConfig pojoConfig ) {
		try {
			System.out.println("persistConfig "+ path );
			OutputStream outputStream = new FileOutputStream(new File(path+"/vespene.properties"));
			//OutputStream propOut = new FileOutputStream(new File("vespene.properties"));
		  
			Properties props = new Properties(); 
			
			props.setProperty("driver.classpath", pojoConfig.getDriverClassPath() );			
			props.setProperty("driver.class", pojoConfig.getDriverClass() );
			props.setProperty("database.URL", pojoConfig.getDatabaseURL() );
			props.setProperty("username", pojoConfig.getUserName() );
			props.setProperty("password", pojoConfig.getPassWord() );
			
			props.setProperty("path.servletclass", pojoConfig.getPathServletClass());
			props.setProperty("path.extendedpojoclass", pojoConfig.getPathExtendedPojoClass());
			props.setProperty("path.queryclass", pojoConfig.getPathQueryClass());
			props.setProperty("path.searchclass", pojoConfig.getPathSearchClass());
			props.setProperty("path.datasetview", pojoConfig.getPathDatasetView());
			props.setProperty("path.windowview", pojoConfig.getPathWindowView());
			props.setProperty("path.searchview", pojoConfig.getPathSearchView());
			
			props.store(outputStream, "Vespene properties");
			outputStream.close();
			
			
			proj.setPersistentProperty(new QualifiedName("", "path.servletclass"),pojoConfig.getPathServletClass());
			proj.setPersistentProperty(new QualifiedName("", "path.extendedpojoclass"),pojoConfig.getPathExtendedPojoClass());
			proj.setPersistentProperty(new QualifiedName("", "path.queryclass"),pojoConfig.getPathQueryClass());
			proj.setPersistentProperty(new QualifiedName("", "path.searchclass"),pojoConfig.getPathSearchClass());
			proj.setPersistentProperty(new QualifiedName("", "path.datasetview"),pojoConfig.getPathDatasetView());
			proj.setPersistentProperty(new QualifiedName("", "path.windowview"),pojoConfig.getPathWindowView());
			proj.setPersistentProperty(new QualifiedName("", "path.searchview"),pojoConfig.getPathSearchView());
			
			

		} catch (IOException ex) {  
		    ex.printStackTrace();  
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
	}
	
	
	public PojoConfig loadConfig() {
		
		PojoConfig pojoConfig = new PojoConfig();
		System.out.println( "loadConfig "+path );
		
		try {
			
			File file = new File(path+"/vespene.properties");
			
			if ( file.isFile() ) {
				FileInputStream fileInputStream = new FileInputStream( file );
				
				Properties props = new Properties();    
			    props.load(fileInputStream);
			    
			    
			    System.out.println( "loadConfig "+props.getProperty("path.servletClass") );
			    
				pojoConfig.setDriverClassPath( props.getProperty("driver.classpath","") );
				pojoConfig.setDriverClass( props.getProperty("driver.class","") );
				pojoConfig.setDatabaseURL( props.getProperty("database.URL","") );
				pojoConfig.setUserName( props.getProperty("username","") );
				pojoConfig.setPassWord( props.getProperty("password","") );
			    												   
			    pojoConfig.setPathServletClass( props.getProperty("path.servletclass") );
			    pojoConfig.setPathExtendedPojoClass( props.getProperty("path.extendedpojoclass") );
			    pojoConfig.setPathQueryClass( props.getProperty("path.queryclass") );
			    pojoConfig.setPathSearchClass( props.getProperty("path.searchclass") );
			    pojoConfig.setPathDatasetView( props.getProperty("path.datasetview") );
			    pojoConfig.setPathWindowView( props.getProperty("path.windowview") );
			    pojoConfig.setPathSearchView( props.getProperty("path.searchview") );
			    
			    fileInputStream.close();
			} else {
				
				pojoConfig.setDriverClassPath(" ");			
				pojoConfig.setDriverClass(" ");
				pojoConfig.setDatabaseURL(" ");
				pojoConfig.setUserName(" ");
				pojoConfig.setPassWord(" ");
				
			    pojoConfig.setPathServletClass(" ");
			    pojoConfig.setPathExtendedPojoClass(" ");
			    pojoConfig.setPathQueryClass(" ");
			    pojoConfig.setPathSearchClass(" ");
			    pojoConfig.setPathDatasetView(" ");
			    pojoConfig.setPathWindowView(" ");
			    pojoConfig.setPathSearchView(" ");
			}
			
			
			/*
			if ( proj.getPersistentProperty(new QualifiedName("", "path.servletclass"))!=null)  {
			    pojoConfig.setPathServletClass( proj.getPersistentProperty(new QualifiedName("", "path.servletclass")) );
			    pojoConfig.setPathExtendedPojoClass( proj.getPersistentProperty(new QualifiedName("", "path.extendedpojoclass")) );
			    pojoConfig.setPathQueryClass( proj.getPersistentProperty(new QualifiedName("", "path.queryclass")) );
			    pojoConfig.setPathSearchClass( proj.getPersistentProperty(new QualifiedName("", "path.searchclass")) );
			    pojoConfig.setPathDatasetView( proj.getPersistentProperty(new QualifiedName("", "path.datasetview")) );
			    pojoConfig.setPathWindowView( proj.getPersistentProperty(new QualifiedName("", "path.windowview")) );
			    pojoConfig.setPathSearchView( proj.getPersistentProperty(new QualifiedName("", "path.searchview")) );
			}
			*/
			
  
		} catch (IOException ex) {  
  
		    ex.printStackTrace();  
//		} catch (CoreException e) {
//			e.printStackTrace();
		}
		return pojoConfig;		
		
	}	


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public IProject getProj() {
		return proj;
	}


	public void setProj(IProject proj) {
		this.proj = proj;
	}
	
	
}
