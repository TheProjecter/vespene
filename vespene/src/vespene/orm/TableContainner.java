package vespene.orm;
 
import java.util.List;

public class TableContainner {
	private String tablename;
	private List<Fields> fields;
	
	
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

	

	
	
	
}
