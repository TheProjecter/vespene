package vespene.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import vespene.orm.Fields;
import vespene.orm.RelationsContainner;


 

public class FieldsDao  {
	private static final long serialVersionUID = 1L;
	private String tablename;
	private String schemaname;
	private List listRelations;

	
	public List<Fields> getFieldsRelations() {
		List<Fields> listFields= new ArrayList<Fields>();
		

		try	{
			
			DBConnection db = new DBConnection();
			Connection connection = db.getConnection();
			
			PreparedStatement ps;
		    if ( schemaname.length()>0 ) {
				ps = connection.prepareStatement( "select * from "+schemaname+"."+tablename );
			} else {
				ps = connection.prepareStatement( "select * from "+tablename );	
			}
			
			ps.clearParameters();
			ResultSet rs = null;
			rs = ps.executeQuery();
			
	        DatabaseMetaData meta = connection.getMetaData();	        
	        ResultSet rspk = meta.getPrimaryKeys("%", schemaname, tablename.toLowerCase());
 
	        java.util.List<String> listPK = new java.util.ArrayList<String>();
	        while (rspk.next()) {
	          String columnName = rspk.getString("COLUMN_NAME").toLowerCase() ;
	          String pk = columnName;
	          listPK.add(pk);
	        }
			
			
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int numColumns = rsmd.getColumnCount();
	        
	        int i = 1;
	        int j = 1;
	        int q = 0;
	        int tt = 1;
	        int quebra = 1;
	        int fecha = 0;
	        
	        for ( i=1; i<numColumns+1; i++) {
	        	String fieldname = rsmd.getColumnName(i).toLowerCase();
	            String fieldtype = rsmd.getColumnTypeName(i).toLowerCase();
	            int fieldlen  = rsmd.getColumnDisplaySize(i);
	            
	            if (q == 2) {
	            	fecha = 1;
	            }
	            
	            if (q == 3) {
	            	  quebra = 1;
	            	  q = 0;
	            }
	            q = (q + 1);
	            if (fieldlen==0) { // acho q blob retorna zero
	            	fieldlen = 100;
	            }
	        	Fields f = new Fields();
	        	f.setId(i);
	        	f.setFieldname(fieldname);
	        	f.setFieldtype(fieldtype);
	        	f.setFieldlen(fieldlen);
	        	f.setFecha(fecha);
	        	if (tt == 1) {
	        		f.setQuebra(1);	
	        	} else {
	        		f.setQuebra(quebra);
	        	}
	        	
	        	if (i == numColumns) {
	        		f.setUltimo(1);
	        	}
	        	tt = 2;
	        	quebra = 0;
	        	fecha = 0;
	        	
	    
	        	
	        	for(Iterator it = listRelations.iterator(); it.hasNext(); ) {
					RelationsContainner a = (RelationsContainner) it.next();
					
					if ( a.getFkcolumn_name().toLowerCase().equals(fieldname) ) {
						f.setIsrelation(true);
						RelationsContainner relations = new RelationsContainner();
			        	relations.setFkcolumn_name( a.getFkcolumn_name());
			        	relations.setLookupcolumn_name( a.getLookupcolumn_name() );
			        	relations.setPkcolumn_name( a.getPkcolumn_name() );
			        	relations.setPktable_name( a.getPktable_name() );
			        	relations.setReferencialcolumn_name( a.getReferencialcolumn_name() );
			        	
			        	f.setRelations(relations);
			        	break;
					}
				}		
	        	
	        	if ( listPK.contains( fieldname ) ) {
	        		f.setIspk( true );	
	        		f.setPk("Y");
	        	} else {
	        		f.setIspk( false );
	        		f.setPk("N");
	        	}
	        	
	        	listFields.add(f);

	            j++;
	        }				
			
	        
	        ps.close();
	        connection.close();
	        
		} catch (Exception e) { 
			 System.out.println( e );
		}		
	    
	    
		return listFields;

		
		
	}
	
	
	
	public List<Fields> getFields(Connection connection) {
		List<Fields> listFields= new ArrayList<Fields>();
		

		try	{
			
			//DBConnection db = new DBConnection();
			//Connection connection = db.getConnection();
			
		    PreparedStatement ps = null;
/*
		    if ( schemaname.trim().length()>0 ) {
				ps = connection.prepareStatement( "select * from "+schemaname+"."+tablename );
			} else {
				ps = connection.prepareStatement( "select * from "+tablename );	
			}
			*/
		    
		    ps = connection.prepareStatement( "select * from "+tablename );
						
			ps.clearParameters();
			ResultSet rs = null;
			rs = ps.executeQuery();
			
	        DatabaseMetaData meta = connection.getMetaData();	        
	        
	        ResultSet rspk = meta.getPrimaryKeys("%", schemaname, tablename.toLowerCase());
	        
	        java.util.List<String> listPK = new java.util.ArrayList<String>();
	        while (rspk.next()) {
	          String columnName = rspk.getString("COLUMN_NAME").toLowerCase();
	          String pk = columnName;
	          listPK.add(pk);
	        }
			
			
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int numColumns = rsmd.getColumnCount();
	        
	        int j = 1;
	        for (int i=1; i<numColumns+1; i++) {
	        	String fieldname = rsmd.getColumnName(i).toLowerCase();
	            String fieldtype = rsmd.getColumnTypeName(i).toLowerCase();
	            int fieldlen  = rsmd.getColumnDisplaySize(i);	        	
	        	
	        	Fields f = new Fields();
	        	f.setId(i);
	        	f.setFieldname(fieldname);
	        	f.setFieldtype(fieldtype);
	        	f.setFieldlen(fieldlen);
	        	
	        	if ( listPK.contains( fieldname ) ) {
	        		f.setIspk( true );	
	        		f.setPk("Y");
	        	} else {
	        		f.setIspk( false );
	        		f.setPk("N");
	        	}
	        	
	        	listFields.add(f);

	            j++;
	        }				
			
	        
	        ps.close();
	        rs.close();
	        //connection.close();
	        
		} catch (Exception e) { 
			 System.out.println( e );
		}		
	    
	    
		return listFields;

		
		
	}
	


	public String getTablename() {
		return tablename;
	}


	public void setTablename(String tablename) {
		this.tablename = tablename;
	}


	public List getRelations() {
		return listRelations;
	}


	public void setRelations(List relations) {
		this.listRelations = relations;
	}



	public String getSchemaname() {
		return schemaname;
	}



	public void setSchemaname(String schemaname) {
		this.schemaname = schemaname;
	}



	
	

}
