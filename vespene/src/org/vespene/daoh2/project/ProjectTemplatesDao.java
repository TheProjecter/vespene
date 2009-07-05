package org.vespene.daoh2.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;
import org.vespene.daoh2.ConnectH2;
import org.vespene.orm.Relations;
import org.vespene.orm.Template;


public class ProjectTemplatesDao {
	String fullPath;	
	
	
	public ProjectTemplatesDao(String fullPath) {
		super();
		this.fullPath = fullPath;
	}

	
	
	
	
	public List<Template> loadTemplatesProject() {
		List<Template> templatesList = new ArrayList<Template>();

		
		ConnectH2 connectH2 = new ConnectH2();
		connectH2.setFullPath(fullPath);
		Connection conn = connectH2.connect();

		
		try {
			
			String sqlSelect = "select * from TEMPLATES";
			
			PreparedStatement st = conn.prepareStatement(sqlSelect);
			ResultSet rs = st.executeQuery();
			
			while ( rs.next() ) {
				Template template = new Template();
				template.setId( rs.getInt("id") );
				template.setTemplatename( rs.getString("templatename") );
				template.setTemplatefile( rs.getString("templatefile") );
				template.setOutfilepattern( rs.getString("outputfilepattern") );
				template.setOutputpath( rs.getString("outputpath") );
				templatesList.add( template );
			}
			
			st.close();
			conn.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
		
		
		return templatesList;
	}
	
	
	

	
	public void insertTemplatesProject(List<Template> selectedTemplates) {
		ConnectH2 connectH2 = new ConnectH2();
		connectH2.setFullPath(fullPath);
		Connection conn = connectH2.connect();
		try {
			String sqlInsert = "INSERT INTO TEMPLATES (ID, TEMPLATENAME, TEMPLATEFILE, OUTPUTFILEPATTERN) VALUES(?,?,?,?)";
			
			PreparedStatement st = conn.prepareStatement(sqlInsert);

			for(Iterator<Template> it = selectedTemplates.iterator(); it.hasNext(); ) {
				Template template = (Template) it.next();
				
				st = conn.prepareStatement(sqlInsert);
				st.setInt(1, template.getId() );
				st.setString(2, template.getTemplatename() );
				st.setString(3, template.getTemplatefile() );
				st.setString(4, template.getOutfilepattern() );
				st.execute();
			}				
			
			st.close();
			conn.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	public void deleteTemplatesProject() {
		ConnectH2 connectH2 = new ConnectH2();
		connectH2.setFullPath(fullPath);
		Connection conn = connectH2.connect();
		try {
			String sqlSelect = "delete from TEMPLATES";
			PreparedStatement st = conn.prepareStatement(sqlSelect);
			st.execute();
			st.close();
			conn.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}





	public void updateTemplatesProject(List<Template> templateList) {
		ConnectH2 connectH2 = new ConnectH2();
		connectH2.setFullPath(fullPath);
		Connection conn = connectH2.connect();
		try {
			String sqlUpdate = "UPDATE TEMPLATES SET OUTPUTPATH=? WHERE ID=?";
			
			PreparedStatement st = conn.prepareStatement(sqlUpdate);

			for(Iterator<Template> it = templateList.iterator(); it.hasNext(); ) {
				Template template = (Template) it.next();
				
				st = conn.prepareStatement(sqlUpdate);
				st.setString(1, template.getOutputpath() );
				st.setInt(2, template.getId() );				
				st.execute();
			}				
			
			st.close();
			conn.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
	}


		

}
