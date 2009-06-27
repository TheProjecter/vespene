package vespene.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;

import vespene.spring.SpringServices;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SpringPersistProperties {
	
	private IProject proj;
	private List<SpringServices> listSpringDef;	
	
	public SpringPersistProperties(IProject proj, List<SpringServices> listSpringDef) {
		super();
		this.proj = proj;
		this.listSpringDef = listSpringDef;
	}
	
	

	public SpringPersistProperties(IProject proj) {
		super();
		this.proj = proj;
	}




	public void storeSpringDef() {
		Type listType = new TypeToken<List<SpringServices>>() {}.getType();
		Gson gson = new Gson();

		String serialize = gson.toJson(listSpringDef, listType);
		
		
	    try {
	        BufferedWriter out = new BufferedWriter(new FileWriter(proj.getLocation().toString()+"/.settings/org.vespene.spring"));
	        out.write(serialize);
	        out.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		
		
		/* string to long for setPersistentProperty
		try {
			proj.setPersistentProperty(new QualifiedName("", "SpringDef"), serialize  );
		} catch (CoreException e) {
			e.printStackTrace();	
		}
		*/	
	}
	
	
	public List<SpringServices> loadSpringDef() {
		List<SpringServices> listSpringDef = null;
		
		Type listType = new TypeToken<List<SpringServices>>() {}.getType();
		Gson gson = new Gson();
		
		
		System.out.println( "load "+proj.getLocation().toString()  );
		
	    try {
	        BufferedReader in = new BufferedReader(new FileReader(proj.getLocation().toString()+"/.settings/org.vespene.spring"));
	        String deserialize = "";
	        String str="";
	        while ((str = in.readLine()) != null) {
	        	deserialize = deserialize+str;
	        }
	        in.close();

	      
			listSpringDef = gson.fromJson(deserialize, listType);
	        
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		
		/*
		try {
			String deserialize = proj.getPersistentProperty(new QualifiedName("", "SpringDef"));
			listSpringDef = gson.fromJson(deserialize, listType);
		} catch (CoreException e) {
			e.printStackTrace();
		}	
		*/		
		
		return listSpringDef;
				
	}
	
	

}
