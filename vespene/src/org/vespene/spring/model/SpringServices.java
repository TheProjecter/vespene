package org.vespene.spring.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class SpringServices {
	@Element(required=false)
	private String serviceName;
	@Element(required=false)
	private Boolean isCustom;
	@Element(required=false)
	private Boolean enable;
	
	
	
	@ElementList(required=false, inline=true)
	private List<Entity> entity;
	
	@Element(required=false)
	private String serviceInterfacePackage;
	@Element(required=false)
	private String serviceInterfaceClassName;
	@Element(required=false)
	private String serviceInterfaceFileName;
	@Element(required=false)
	private String serviceInterfaceSrcDir;
	
	@Element(required=false)
	private String daoInterfacePackage;
	@Element(required=false)
	private String daoInterfaceClassName;
	@Element(required=false)
	private String daoInterfaceFileName;
	@Element(required=false)
	private String daoInterfaceSrcDir;
	
	@Element(required=false)
	private String serviceImplementationPackage;
	@Element(required=false)
	private String serviceImplementationClassName;
	@Element(required=false)
	private String serviceImplementationFileName;
	@Element(required=false)
	private String serviceImplementationSrcDir;
	
	@Element(required=false)
	private String daoImplementationPackage;
	@Element(required=false)
	private String daoImplementationClassName;
	@Element(required=false)
	private String daoImplementationFileName;
	@Element(required=false)
	private String daoImplementationSrcDir;
	
		
	
	public SpringServices() {

	}



	public String getServiceName() {
		return serviceName;
	}



	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}



	public Boolean getIsCustom() {
		return isCustom;
	}



	public void setIsCustom(Boolean isCustom) {
		this.isCustom = isCustom;
	}



	public Boolean getEnable() {
		return enable;
	}



	public void setEnable(Boolean enable) {
		this.enable = enable;
	}



	public List<Entity> getEntity() {
		return entity;
	}



	public void setEntity(List<Entity> entity) {
		this.entity = entity;
	}



	public String getServiceInterfacePackage() {
		return serviceInterfacePackage;
	}



	public void setServiceInterfacePackage(String serviceInterfacePackage) {
		this.serviceInterfacePackage = serviceInterfacePackage;
	}



	public String getServiceInterfaceClassName() {
		return serviceInterfaceClassName;
	}



	public void setServiceInterfaceClassName(String serviceInterfaceClassName) {
		this.serviceInterfaceClassName = serviceInterfaceClassName;
	}



	public String getServiceInterfaceFileName() {
		return serviceInterfaceFileName;
	}



	public void setServiceInterfaceFileName(String serviceInterfaceFileName) {
		this.serviceInterfaceFileName = serviceInterfaceFileName;
	}



	public String getServiceInterfaceSrcDir() {
		return serviceInterfaceSrcDir;
	}



	public void setServiceInterfaceSrcDir(String serviceInterfaceSrcDir) {
		this.serviceInterfaceSrcDir = serviceInterfaceSrcDir;
	}



	public String getDaoInterfacePackage() {
		return daoInterfacePackage;
	}



	public void setDaoInterfacePackage(String daoInterfacePackage) {
		this.daoInterfacePackage = daoInterfacePackage;
	}



	public String getDaoInterfaceClassName() {
		return daoInterfaceClassName;
	}



	public void setDaoInterfaceClassName(String daoInterfaceClassName) {
		this.daoInterfaceClassName = daoInterfaceClassName;
	}



	public String getDaoInterfaceFileName() {
		return daoInterfaceFileName;
	}



	public void setDaoInterfaceFileName(String daoInterfaceFileName) {
		this.daoInterfaceFileName = daoInterfaceFileName;
	}



	public String getDaoInterfaceSrcDir() {
		return daoInterfaceSrcDir;
	}



	public void setDaoInterfaceSrcDir(String daoInterfaceSrcDir) {
		this.daoInterfaceSrcDir = daoInterfaceSrcDir;
	}



	public String getServiceImplementationPackage() {
		return serviceImplementationPackage;
	}



	public void setServiceImplementationPackage(String serviceImplementationPackage) {
		this.serviceImplementationPackage = serviceImplementationPackage;
	}



	public String getServiceImplementationClassName() {
		return serviceImplementationClassName;
	}



	public void setServiceImplementationClassName(
			String serviceImplementationClassName) {
		this.serviceImplementationClassName = serviceImplementationClassName;
	}



	public String getServiceImplementationFileName() {
		return serviceImplementationFileName;
	}



	public void setServiceImplementationFileName(
			String serviceImplementationFileName) {
		this.serviceImplementationFileName = serviceImplementationFileName;
	}



	public String getServiceImplementationSrcDir() {
		return serviceImplementationSrcDir;
	}



	public void setServiceImplementationSrcDir(String serviceImplementationSrcDir) {
		this.serviceImplementationSrcDir = serviceImplementationSrcDir;
	}



	public String getDaoImplementationPackage() {
		return daoImplementationPackage;
	}



	public void setDaoImplementationPackage(String daoImplementationPackage) {
		this.daoImplementationPackage = daoImplementationPackage;
	}



	public String getDaoImplementationClassName() {
		return daoImplementationClassName;
	}



	public void setDaoImplementationClassName(String daoImplementationClassName) {
		this.daoImplementationClassName = daoImplementationClassName;
	}



	public String getDaoImplementationFileName() {
		return daoImplementationFileName;
	}



	public void setDaoImplementationFileName(String daoImplementationFileName) {
		this.daoImplementationFileName = daoImplementationFileName;
	}



	public String getDaoImplementationSrcDir() {
		return daoImplementationSrcDir;
	}



	public void setDaoImplementationSrcDir(String daoImplementationSrcDir) {
		this.daoImplementationSrcDir = daoImplementationSrcDir;
	}



	
	
}