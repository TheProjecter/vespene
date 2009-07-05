package org.vespene.orm;
 
public class RelationsOLD {
	
	//TABLENAME, PKFIELD, PKTABLE, FKFIELD, RETURNFIELD, LOOKUPFIELD) VALUES(?,?,?,?,?,?)";	
	
	private String table_name;
	private String fk_name;
	private String pktable_name;
	private String pkcolumn_name;
	private String fktable_name;
	private String fkcolumn_name;
	private String schemaname;
	private String returnfield;
	private String lookupfield;
	
	
	


	public RelationsOLD() {
	}
	
	
	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}	
	
	public String getPktable_name() {
		return pktable_name;
	}
	public void setPktable_name(String pktable_name) {
		this.pktable_name = pktable_name;
	}
	public String getPkcolumn_name() {
		return pkcolumn_name;
	}
	public void setPkcolumn_name(String pkcolumn_name) {
		this.pkcolumn_name = pkcolumn_name;
	}
	public String getFktable_name() {
		return fktable_name;
	}
	public void setFktable_name(String fktable_name) {
		this.fktable_name = fktable_name;
	}
	public String getFkcolumn_name() {
		return fkcolumn_name;
	}
	public void setFkcolumn_name(String fkcolumn_name) {
		this.fkcolumn_name = fkcolumn_name;
	}
	
	
	public String getSchemaname() {
		return schemaname;
	}

	public void setSchemaname(String schemaname) {
		this.schemaname = schemaname;
	}


	public String getReturnfield() {
		return returnfield;
	}


	public void setReturnfield(String returnfield) {
		this.returnfield = returnfield;
	}


	public String getLookupfield() {
		return lookupfield;
	}


	public void setLookupfield(String lookupfield) {
		this.lookupfield = lookupfield;
	}


	public String getFk_name() {
		return fk_name;
	}


	public void setFk_name(String fk_name) {
		this.fk_name = fk_name;
	}	

}
