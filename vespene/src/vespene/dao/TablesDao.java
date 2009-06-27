package vespene.dao;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;

import vespene.orm.Tables;


public class TablesDao  {

	private static final long serialVersionUID = 1L;
	private Connection connection;

	
	public TablesDao(Connection connection) {
		this.connection = connection;
	}


	public List<Tables> getTables() {
		List<Tables> listTables = new ArrayList<Tables>();
		int i = 1;

		
		try	{
			
			//DBConnection db = new DBConnection();
			//Connection connection = db.getConnection();
			
			
			

	        DatabaseMetaData dbmd = connection.getMetaData();
	        String[] types = {"TABLE"};
	        ResultSet rs = dbmd.getTables("%", "%", "%", types);

	        while (rs.next()) {
	        	Tables t = new Tables();
	        	t.setId( i++ );
	        	t.setName( rs.getString(3).toLowerCase() );
	        	t.setSchemaname( rs.getString(2) );
	            listTables.add( t );
	        }
	        
	        

	        rs.close();
	        //connection.close();
	        
		} catch (Exception e) { 
			 System.out.println( e );
		}		
	    
	    
		return listTables;

		
		
	}
	
	
	

}
