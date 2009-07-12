package org.vespene.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

public class Config {
	private IProject proj;

	public Config() {
		super();
	}


	public void persistConfig(PojoConfig pojoConfig ) {
		try {

			System.out.println( "persistConfig 1 " );
			
			proj.setPersistentProperty(new QualifiedName("", "path.servletclass"),pojoConfig.getPathServletClass());
			proj.setPersistentProperty(new QualifiedName("", "path.extendedpojoclass"),pojoConfig.getPathExtendedPojoClass());
			proj.setPersistentProperty(new QualifiedName("", "path.queryclass"),pojoConfig.getPathQueryClass());
			proj.setPersistentProperty(new QualifiedName("", "path.searchclass"),pojoConfig.getPathSearchClass());
			proj.setPersistentProperty(new QualifiedName("", "path.datasetview"),pojoConfig.getPathDatasetView());
			proj.setPersistentProperty(new QualifiedName("", "path.windowview"),pojoConfig.getPathWindowView());
			proj.setPersistentProperty(new QualifiedName("", "path.searchview"),pojoConfig.getPathSearchView());
			
			System.out.println( "persistConfig 2 " );
			
 
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
	}
	
	
	public PojoConfig loadConfig() {
		
		PojoConfig pojoConfig = new PojoConfig();
		System.out.println( "loadConfig " );
		
		try {
			System.out.println( "aaaaaaaaaaaaaaaaaaaa "+proj.getPersistentProperty(new QualifiedName("", "path.servletclass")) );

			if ( proj.getPersistentProperty(new QualifiedName("", "path.servletclass"))!=null)  {
			    pojoConfig.setPathServletClass( proj.getPersistentProperty(new QualifiedName("", "path.servletclass")) );
			} else {
				pojoConfig.setPathServletClass("" );			    	
			}
			 
			
			if ( proj.getPersistentProperty(new QualifiedName("", "path.extendedpojoclass"))!=null ) {
			    pojoConfig.setPathExtendedPojoClass( proj.getPersistentProperty(new QualifiedName("", "path.extendedpojoclass")) );
			} else {
				pojoConfig.setPathExtendedPojoClass("");
			}
			
			if ( proj.getPersistentProperty(new QualifiedName("", "path.queryclass"))!=null ) {
			    pojoConfig.setPathQueryClass( proj.getPersistentProperty(new QualifiedName("", "path.queryclass")) );
			} else {
				pojoConfig.setPathQueryClass("" );
			}
			
			if ( proj.getPersistentProperty(new QualifiedName("", "path.searchclass"))!=null ) {
			    pojoConfig.setPathSearchClass( proj.getPersistentProperty(new QualifiedName("", "path.searchclass")) );
			} else {
				pojoConfig.setPathSearchClass("");
			}
			
			if ( proj.getPersistentProperty(new QualifiedName("", "path.datasetview"))!=null ) {
			    pojoConfig.setPathDatasetView( proj.getPersistentProperty(new QualifiedName("", "path.datasetview")) );
			} else {
				pojoConfig.setPathDatasetView("");
			}
			
			if ( proj.getPersistentProperty(new QualifiedName("", "path.windowview"))!=null ) {
			    pojoConfig.setPathWindowView( proj.getPersistentProperty(new QualifiedName("", "path.windowview")) );
			} else {
				pojoConfig.setPathWindowView("");
			}
			
			if ( proj.getPersistentProperty(new QualifiedName("", "path.searchview"))!=null ) {
			    pojoConfig.setPathSearchView( proj.getPersistentProperty(new QualifiedName("", "path.searchview")) );
			} else {
				pojoConfig.setPathSearchView( "" );
			}

			

			
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return pojoConfig;		
		
	}	





	public IProject getProj() {
		return proj;
	}


	public void setProj(IProject proj) {
		this.proj = proj;
	}
	
	
}
