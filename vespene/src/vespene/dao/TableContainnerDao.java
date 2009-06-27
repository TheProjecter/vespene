package vespene.dao;

import java.util.List;

import vespene.orm.Fields;
import vespene.orm.PKs;
import vespene.orm.TableContainner;


 
public class TableContainnerDao {
	private String tablename;
	private String schemaname;
	private List<Fields> fields;
	private List<PKs> pks;
	private List relations;
	
	
	
	public TableContainner getTableDef() {
		
		FieldsDao fieldsDao = new FieldsDao();
		fieldsDao.setTablename(tablename);
		fieldsDao.setSchemaname(schemaname);
		fieldsDao.setRelations(relations);
		List<Fields> fieldList = fieldsDao.getFieldsRelations();
		
		TableContainner tc = new TableContainner();
		tc.setTablename(tablename);
		tc.setFields(fieldList);
		
		return tc;
	}
	
	

	public String getTablename() {
		return tablename;
	}



	public void setTablename(String tablename) {
		this.tablename = tablename;
	}



	public List<Fields> getFields() {
		return fields;
	}
	
	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}



	public List<PKs> getPks() {
		return pks;
	}



	public void setPks(List<PKs> pks) {
		this.pks = pks;
	}



	public List getRelations() {
		return relations;
	}



	public void setRelations(List relations) {
		this.relations = relations;
	}



	public String getSchemaname() {
		return schemaname;
	}



	public void setSchemaname(String schemaname) {
		this.schemaname = schemaname;
	}

	

	
	
	
}
