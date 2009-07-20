package org.vespene.spring.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Entity {
	@Element(required=false)
	private String entityName;
	@Element(required=false)
	private String entityPackage;
	@Element(required=false)
	private String absolutePath;
	@Element(required=false)
	private String pkType;
	@Element(required=false)
	private String pkVar;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getEntityPackage() {
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getPkType() {
		return pkType;
	}

	public void setPkType(String pkType) {
		this.pkType = pkType;
	}

	public String getPkVar() {
		return pkVar;
	}

	public void setPkVar(String pkVar) {
		this.pkVar = pkVar;
	}


	
	
	

}
