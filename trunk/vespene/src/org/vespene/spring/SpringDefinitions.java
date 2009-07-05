package org.vespene.spring;

import java.util.List;

public class SpringDefinitions {
	private String Package;
	private String templateFile; 
	private String pattern;
	private SpringDefinitions springNestedDefinitions; 
	
	public String getPackage() {
		return Package;
	}
	public void setPackage(String package1) {
		Package = package1;
	}
	public String getTemplateFile() {
		return templateFile;
	}
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public SpringDefinitions getSpringNestedDefinitions() {
		return springNestedDefinitions;
	}
	public void setSpringNestedDefinitions(SpringDefinitions springNestedDefinitions) {
		this.springNestedDefinitions = springNestedDefinitions;
	}

//	private List<SpringServices> springServices;

	
	
}
