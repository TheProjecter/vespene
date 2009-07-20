package org.vespene.spring.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class SpringDefinitions {
	
	@Element(required=false)
	private String ServiceNamePattern;
	@Element(required=false)
	private String serviceInterfacePackage;
	@Element(required=false)
	private String daoInterfacePackage;
	@Element(required=false)
	private String serviceImplementationPackage;
	@Element(required=false)
	private String daoImplementationPackage; 
	
	
	@Element(required=false)
	private String serviceInterfaceTemplateFile;
	@Element(required=false)
	private String serviceInterfacePattern;
	
	@Element(required=false)
	private String daoInterfaceTemplateFile;
	@Element(required=false)
	private String daoInterfacePattern;
	
	@Element(required=false)
	private String serviceImplementationTemplateFile;
	@Element(required=false)
	private String serviceImplementationPattern;
	
	@Element(required=false)
	private String daoImplementationTemplateFile;
	@Element(required=false)
	private String daoImplementationPattern;	

	@ElementList(required=false, inline=true)
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
