package vespene.daoh2.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import vespene.daoh2.ConnectH2;




public class ProjectMetadata {
	String fullPath;
	
	
	
	
	
	public ProjectMetadata(String fullPath) {
		super();
		this.fullPath = fullPath;
	}

	public void create() {
		
		String tableRelationsDefs = "CREATE TABLE IF NOT EXISTS PUBLIC.RELATIONSDEFS ("+
			    "TABLENAME VARCHAR(100),"+
			    "TABLESCHEMA VARCHAR(100),"+
			    "FKNAME VARCHAR(100),"+
			    "PKTABLE VARCHAR(100),"+
			    "PKFIELD VARCHAR(100),"+
			    "FKTABLE VARCHAR(100),"+			    
			    "FKFIELD VARCHAR(100),"+
			    "RETURNFIELD VARCHAR(100),"+	
			    "CUSTOMFIELDNAME VARCHAR(100) );";		
		

		
		String indexRelationsDefs = "CREATE INDEX IF NOT EXISTS RELATIONSDEFSINDEX ON PUBLIC.RELATIONSDEFS (TABLENAME);";
		
		
		
		
		String tableTemplates = "CREATE TABLE IF NOT EXISTS PUBLIC.TEMPLATES ("+
			    "ID INTEGER(10) NOT NULL,"+
			    "TEMPLATENAME VARCHAR(30),"+
			    "TEMPLATEFILE VARCHAR(100),"+
			    "OUTPUTFILEPATTERN VARCHAR(100),"+
			    "OUTPUTPATH VARCHAR(100),"+
			    "CONSTRAINT TEMPLATESPK1 PRIMARY KEY (ID));";
		
		
		
		ConnectH2 connectH2 = new ConnectH2();
		connectH2.setFullPath(fullPath);
		Connection conn = connectH2.connect();
		try {
			PreparedStatement st = conn.prepareStatement(tableRelationsDefs);
			st.execute();
			st = conn.prepareStatement(indexRelationsDefs);
			st.execute();
			
			st = conn.prepareStatement(tableTemplates);
			st.execute();
			
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		
	}

	

	
	
	
	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}


	
	
}
