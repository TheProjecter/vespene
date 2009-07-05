package org.vespene.daoh2.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.vespene.daoh2.ConnectH2;
import org.vespene.orm.Relations;
import org.vespene.orm.Template;


public class TemplateDefsDao {
	String fullPath;

	
	
	
	public List<Template> loadTemplates() {
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
				templatesList.add( template );
			}
			
			st.close();
			conn.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
		
		
		return templatesList;
	}
	
	
	
	
	
	
	
	
	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	
	

}
