package org.vespene.daoh2.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.TableItem;
import org.vespene.daoh2.ConnectH2;
import org.vespene.orm.Relations;


public class ProjectRelationsDefsDao {
	String fullPath;	
	
	
	public ProjectRelationsDefsDao(String fullPath) {
		super();
		this.fullPath = fullPath;
	}

	
	public List<Relations> loadRelationsDefs(String tableName) {
		List<Relations> relationsFieldsList = new ArrayList<Relations>();
		
		ConnectH2 connectH2 = new ConnectH2();
		connectH2.setFullPath(fullPath);
		Connection conn = connectH2.connect();
		try {
			
			String sqlSelect = "select * from RELATIONSDEFS where  RELATIONSDEFS.tablename=?";
			
			PreparedStatement st = conn.prepareStatement(sqlSelect);
			st.setString(1, tableName);
			
			ResultSet rs = st.executeQuery();
			
			while ( rs.next() ) {
				Relations relations = new Relations();
				relations.setTablename( rs.getString("TABLENAME") );
				relations.setPktable( rs.getString("PKTABLE") );
				relations.setPkfield( rs.getString("PKFIELD") );
				relations.setPktable( rs.getString("FKTABLE") );
				relations.setFkfield( rs.getString("FKFIELD") );
				relations.setReturnfield( rs.getString("RETURNFIELD") );
				relations.setCustomfieldname( rs.getString("CUSTOMFIELDNAME") );
				
				relationsFieldsList.add( relations );
			}
			
			st.close();
			conn.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return relationsFieldsList;
		
	}
	
	
	
	public void persistRelationsDefs(List<Relations> listRelations) {
		ConnectH2 connectH2 = new ConnectH2();
		connectH2.setFullPath(fullPath);
		Connection conn = connectH2.connect();
		try {
			String sqlInsert = "INSERT INTO RELATIONSDEFS (TABLENAME, TABLESCHEMA, FKNAME, PKTABLE, " +
				"PKFIELD, FKTABLE, FKFIELD, RETURNFIELD, CUSTOMFIELDNAME) VALUES(?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement st = conn.prepareStatement(sqlInsert);
			

			for(Iterator<Relations> it = listRelations.iterator(); it.hasNext(); ) {
				Relations relations = (Relations) it.next();
				
				st.setString(1, relations.getTablename()  );
				st.setString(2, relations.getTableschema() );
				st.setString(3, relations.getFkfield() );
				st.setString(4, relations.getPktable() );
				st.setString(5, relations.getPkfield() );
				st.setString(6, relations.getPktable() );
				st.setString(7, relations.getFkfield() );
				st.setString(8, relations.getReturnfield() );
				st.setString(9, relations.getCustomfieldname() );
				st.execute();				
			}					
			

			st.close();
			conn.close();
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	public void deleteRelationsDefs(String tableName) {
		ConnectH2 connectH2 = new ConnectH2();
		connectH2.setFullPath(fullPath);
		Connection conn = connectH2.connect();
		try {
			
			String sqlSelect = "delete from RELATIONSDEFS where  RELATIONSDEFS.tablename=?";
			
			PreparedStatement st = conn.prepareStatement(sqlSelect);
			st.setString(1, tableName);
			
			st.execute();
			
			
			st.close();
			conn.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}			

}
