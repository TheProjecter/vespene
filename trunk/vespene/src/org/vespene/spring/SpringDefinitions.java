package org.vespene.spring;

import java.util.List;


public class SpringDefinitions {
	
	private String ServiceNamePattern;
	private String serviceInterfacePackage;
	private String daoInterfacePackage; 
	private String serviceImplementationPackage; 
	private String daoImplementationPackage; 
	
	
	
	private String serviceInterfaceTemplateFile;
	private String serviceInterfacePattern;
	
	private String daoInterfaceTemplateFile;
	private String daoInterfacePattern;
	
	private String serviceImplementationTemplateFile;
	private String serviceImplementationPattern;
	
	private String daoImplementationTemplateFile;
	private String daoImplementationPattern;	

	
	private List<SpringServices> springServices;


	public String getServiceInterfaceTemplateFile() {
		return serviceInterfaceTemplateFile;
	}


	public void setServiceInterfaceTemplateFile(String serviceInterfaceTemplateFile) {
		this.serviceInterfaceTemplateFile = serviceInterfaceTemplateFile;
	}


	public String getServiceInterfacePattern() {
		return serviceInterfacePattern;
	}


	public void setServiceInterfacePattern(String serviceInterfacePattern) {
		this.serviceInterfacePattern = serviceInterfacePattern;
	}


	public String getDaoInterfaceTemplateFile() {
		return daoInterfaceTemplateFile;
	}


	public void setDaoInterfaceTemplateFile(String daoInterfaceTemplateFile) {
		this.daoInterfaceTemplateFile = daoInterfaceTemplateFile;
	}


	public String getDaoInterfacePattern() {
		return daoInterfacePattern;
	}


	public void setDaoInterfacePattern(String daoInterfacePattern) {
		this.daoInterfacePattern = daoInterfacePattern;
	}


	public String getServiceImplementationTemplateFile() {
		return serviceImplementationTemplateFile;
	}


	public void setServiceImplementationTemplateFile(
			String serviceImplementationTemplateFile) {
		this.serviceImplementationTemplateFile = serviceImplementationTemplateFile;
	}


	public String getServiceImplementationPattern() {
		return serviceImplementationPattern;
	}


	public void setServiceImplementationPattern(String serviceImplementationPattern) {
		this.serviceImplementationPattern = serviceImplementationPattern;
	}


	public String getDaoImplementationTemplateFile() {
		return daoImplementationTemplateFile;
	}


	public void setDaoImplementationTemplateFile(
			String daoImplementationTemplateFile) {
		this.daoImplementationTemplateFile = daoImplementationTemplateFile;
	}


	public String getDaoImplementationPattern() {
		return daoImplementationPattern;
	}


	public void setDaoImplementationPattern(String daoImplementationPattern) {
		this.daoImplementationPattern = daoImplementationPattern;
	}


	public List<SpringServices> getSpringServices() {
		return springServices;
	}


	public void setSpringServices(List<SpringServices> springServices) {
		this.springServices = springServices;
	}


	public String getServiceInterfacePackage() {
		return serviceInterfacePackage;
	}


	public void setServiceInterfacePackage(String serviceInterfacePackage) {
		this.serviceInterfacePackage = serviceInterfacePackage;
	}


	public String getDaoInterfacePackage() {
		return daoInterfacePackage;
	}


	public void setDaoInterfacePackage(String daoInterfacePackage) {
		this.daoInterfacePackage = daoInterfacePackage;
	}


	public String getServiceImplementationPackage() {
		return serviceImplementationPackage;
	}


	public void setServiceImplementationPackage(String serviceImplementationPackage) {
		this.serviceImplementationPackage = serviceImplementationPackage;
	}


	public String getDaoImplementationPackage() {
		return daoImplementationPackage;
	}


	public void setDaoImplementationPackage(String daoImplementationPackage) {
		this.daoImplementationPackage = daoImplementationPackage;
	}


	public String getServiceNamePattern() {
		return ServiceNamePattern;
	}


	public void setServiceNamePattern(String serviceNamePattern) {
		ServiceNamePattern = serviceNamePattern;
	}

	
	
}
