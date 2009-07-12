package org.vespene.properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import org.eclipse.core.resources.IProject;
import org.vespene.spring.SpringDefinitions;

import com.google.gson.Gson;

public class SpringProperties {
	private static final String SETTINGS_FILE = "/.settings/org.vespene.spring";
	
	private IProject proj;

	
	public SpringProperties(IProject proj, SpringDefinitions springDefinitions) {
		super();
		this.proj = proj;
	}
	
	
	public SpringProperties(IProject proj) {
		super();
		this.proj = proj;
	}


	
	public void storeSpringDefinitions(SpringDefinitions springDefinitions) {
		Gson gson = new Gson();
		String serialize = gson.toJson(springDefinitions);
		
	    try {
	        BufferedWriter out = new BufferedWriter(new FileWriter(proj.getLocation().toString()+SETTINGS_FILE));
	        out.write(serialize);
	        out.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}	
	
	
	public SpringDefinitions loadSpringDefinitions() {
		SpringDefinitions springDefinitions = new SpringDefinitions();
		
		Gson gson = new Gson();
		
		
		if ( (new File( proj.getLocation().toString()+SETTINGS_FILE )).exists() ) {
		    try {
		        BufferedReader in = new BufferedReader(new FileReader(proj.getLocation().toString()+SETTINGS_FILE));
		        String deserialize = "";
		        String str="";
		        while ((str = in.readLine()) != null) {
		        	deserialize = deserialize+str;
		        }
		        in.close();

		      
		        springDefinitions = gson.fromJson(deserialize, SpringDefinitions.class);
		        
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
		
		return springDefinitions;
				
	}	
	
	

}
