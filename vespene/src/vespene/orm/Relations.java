package vespene.orm;
 
public class Relations {
	
	//TABLENAME, PKFIELD, PKTABLE, FKFIELD, RETURNFIELD, LOOKUPFIELD) VALUES(?,?,?,?,?,?)";	
	
	private String tablename;
	private String tableschema;
	private String fkname;
	private String pktable;
	private String pkfield;
	private String fktable;
	private String fkfield;
	private String returnfield;	
	private String customfieldname;
	
	


	public Relations() {
		
	}




	public String getTablename() {
		return tablename;
	}




	public void setTablename(String tablename) {
		this.tablename = tablename;
	}




	public String getTableschema() {
		return tableschema;
	}




	public void setTableschema(String tableschema) {
		this.tableschema = tableschema;
	}




	public String getFkname() {
		return fkname;
	}




	public void setFkname(String fkname) {
		this.fkname = fkname;
	}




	public String getPktable() {
		return pktable;
	}




	public void setPktable(String pktable) {
		this.pktable = pktable;
	}




	public String getPkfield() {
		return pkfield;
	}




	public void setPkfield(String pkfield) {
		this.pkfield = pkfield;
	}




	public String getFktable() {
		return fktable;
	}




	public void setFktable(String fktable) {
		this.fktable = fktable;
	}




	public String getFkfield() {
		return fkfield;
	}




	public void setFkfield(String fkfield) {
		this.fkfield = fkfield;
	}




	public String getReturnfield() {
		return returnfield;
	}




	public void setReturnfield(String returnfield) {
		this.returnfield = returnfield;
	}




	public String getCustomfieldname() {
		return customfieldname;
	}




	public void setCustomfieldname(String customfieldname) {
		this.customfieldname = customfieldname;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((customfieldname == null) ? 0 : customfieldname.hashCode());
		result = prime * result + ((fkfield == null) ? 0 : fkfield.hashCode());
		result = prime * result + ((fkname == null) ? 0 : fkname.hashCode());
		result = prime * result + ((fktable == null) ? 0 : fktable.hashCode());
		result = prime * result + ((pkfield == null) ? 0 : pkfield.hashCode());
		result = prime * result + ((pktable == null) ? 0 : pktable.hashCode());
		result = prime * result
				+ ((returnfield == null) ? 0 : returnfield.hashCode());
		result = prime * result
				+ ((tablename == null) ? 0 : tablename.hashCode());
		result = prime * result
				+ ((tableschema == null) ? 0 : tableschema.hashCode());
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relations other = (Relations) obj;
		if (customfieldname == null) {
			if (other.customfieldname != null)
				return false;
		} else if (!customfieldname.equals(other.customfieldname))
			return false;
		if (fkfield == null) {
			if (other.fkfield != null)
				return false;
		} else if (!fkfield.equals(other.fkfield))
			return false;
		if (fkname == null) {
			if (other.fkname != null)
				return false;
		} else if (!fkname.equals(other.fkname))
			return false;
		if (fktable == null) {
			if (other.fktable != null)
				return false;
		} else if (!fktable.equals(other.fktable))
			return false;
		if (pkfield == null) {
			if (other.pkfield != null)
				return false;
		} else if (!pkfield.equals(other.pkfield))
			return false;
		if (pktable == null) {
			if (other.pktable != null)
				return false;
		} else if (!pktable.equals(other.pktable))
			return false;
		if (returnfield == null) {
			if (other.returnfield != null)
				return false;
		} else if (!returnfield.equals(other.returnfield))
			return false;
		if (tablename == null) {
			if (other.tablename != null)
				return false;
		} else if (!tablename.equals(other.tablename))
			return false;
		if (tableschema == null) {
			if (other.tableschema != null)
				return false;
		} else if (!tableschema.equals(other.tableschema))
			return false;
		return true;
	}
	
}
