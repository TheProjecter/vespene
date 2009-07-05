package org.vespene.orm;


 
public class Fields {
	private int id;
	private String fieldname;
	private String fieldtype;
	private int fieldlen;
	private int quebra;
	private int fecha;
	private int ultimo;
	
	private boolean ispk;
	private String pk;
	private RelationsContainner relations;
	private boolean isrelation;
	
	
	public int getUltimo() {
		return ultimo;
	}


	public void setUltimo(int ultimo) {
		this.ultimo = ultimo;
	}


	public int getFecha() {
		return fecha;
	}


	public void setFecha(int fecha) {
		this.fecha = fecha;
	}


	public int getQuebra() {
		return quebra;
	}


	public void setQuebra(int quebra) {
		this.quebra = quebra;
	}


	public Fields() {
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public boolean isIspk() {
		return ispk;
	}


	public void setIspk(boolean ispk) {
		this.ispk = ispk;
	}


	public String getPk() {
		return pk;
	}


	public void setPk(String pk) {
		this.pk = pk;
	}


	public RelationsContainner getRelations() {
		return relations;
	}


	public void setRelations(RelationsContainner relations) {
		this.relations = relations;
	}


	public boolean isIsrelation() {
		return isrelation;
	}


	public void setIsrelation(boolean isrelation) {
		this.isrelation = isrelation;
	}







	
	

}
