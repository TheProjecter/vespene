package vespene.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class ParseTemplate {
	
	public String loadTemplateFromString(String templateString, Map<String,String> mapRoot ) {
		
		Configuration cfg = new Configuration();    
        cfg.setTemplateLoader(new StringTemplateLoader(templateString));    
        cfg.setDefaultEncoding("UTF-8");
        
        StringWriter writer = new StringWriter();
   
        Template template;
		try {
			template = cfg.getTemplate("");
	        template.process(mapRoot, writer);    
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} 		
		
		return writer.toString();
	}
	
	
	public String loadTemplateFromFile(String directory, String templateFile, Map<String, Object> mapRoot) throws Exception {
		StringWriter writer = new StringWriter();
		
		Configuration cfg = new Configuration();
		
		cfg.setDirectoryForTemplateLoading( new File( directory ) );
		cfg.setObjectWrapper(new DefaultObjectWrapper());  	
        Template t = cfg.getTemplate(templateFile);
        
        t.process(mapRoot, writer);
        
        writer.flush();
        writer.close();        
		
		return writer.toString();
	}	
	
	

}
