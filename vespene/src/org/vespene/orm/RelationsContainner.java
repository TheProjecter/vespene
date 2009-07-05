package org.vespene.orm;

import java.util.List;
 
public class RelationsContainner {
	private String tablename;
	private String pktable_name;
	private String pkcolumn_name;
	//private String fktable_name;
	private String fkcolumn_name;
	private String lookupcolumn_name;
	private String referencialcolumn_name;
	private Boolean	master_key;
	private List<Fields> fields;
	
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
	
	public String getFkcolumn_name() {
		return fkcolumn_name;
	}
	public void setFkcolumn_name(String fkcolumn_name) {
		this.fkcolumn_name = fkcolumn_name;
	}
	public String getLookupcolumn_name() {
		return lookupcolumn_name;
	}
	public void setLookupcolumn_name(String lookupcolumn_name) {
		this.lookupcolumn_name = lookupcolumn_name;
	}
	public String getReferencialcolumn_name() {
		return referencialcolumn_name;
	}
	public void setReferencialcolumn_name(String referencialcolumn_name) {
		this.referencialcolumn_name = referencialcolumn_name;
	}
	public List<Fields> getFields() {
		return fields;
	}
	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}
	public Boolean getMaster_key() {
		return master_key;
	}
	public void setMaster_key(Boolean master_key) {
		this.master_key = master_key;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	} 
	
	
	
	

	
	
	

}
