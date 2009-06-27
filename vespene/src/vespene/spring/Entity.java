package vespene.spring;

public class Entity {
	private String entityName;
	private String entityPackage;	
	private String absolutePath;
	private String pkType;
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
