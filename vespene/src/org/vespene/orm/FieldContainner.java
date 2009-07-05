package org.vespene.orm;

 
public class FieldContainner {
	private String fieldname;
	private String fieldtype;
	private int fieldlen;
	private boolean ispk;
	private RelationsContainner relations;

	
	
	public boolean isIspk() {
		return ispk;
	}


	public void setIspk(boolean ispk) {
		this.ispk = ispk;
	}


	public FieldContainner() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getFieldname() {
		return fieldname;
	}


	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}


	public String getFieldtype() {
		return fieldtype;
	}


	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}


	public int getFieldlen() {
		return fieldlen;
	}


	public void setFieldlen(int fieldlen) {
		this.fieldlen = fieldlen;
	}


	public RelationsContainner getRelations() {
		return relations;
	}


	public void setRelations(RelationsContainner relations) {
		this.relations = relations;
	}





	
	

}
