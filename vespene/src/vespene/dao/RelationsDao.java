package vespene.dao;
 
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import vespene.orm.Relations;

public class RelationsDao {
	

	private String table;
	private Connection connection;

	
	public RelationsDao(Connection connection) {
		this.connection = connection;
	}	
	
	
	
	/*
	public List<RelationsFields> getPrimaryKeyDetail() {
		List<RelationsFields> fieldList = new ArrayList<RelationsFields>();
		
		try	{
			
			//DBConnection db = new DBConnection();
			//Connection connection = db.getConnection();

	        DatabaseMetaData dbmd = connection.getMetaData();
			
			ResultSet rs = dbmd.getImportedKeys(null,null,table); // tables que eu aponto - tenho chave estrangeira

			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			
			int n = 0;
			while (rs.next()) {
				
				RelationsFields rf = new RelationsFields();
				for (int i=1;i<=cols;i++) {
					
					if (rs.getString(i)!=null) {
						
						rf.setTable_name(table);
						
						if ( rsmd.getColumnName(i).toUpperCase().equals("FK_NAME") ) {
							System.out.println( " FK_NAME  "+rs.getString(i).toLowerCase() );	
						}
						

						
						if ( rsmd.getColumnName(i).toUpperCase().equals("PK_NAME") ) {
						//	System.out.println( " PK_NAME  "+rs.getString(i).toLowerCase() );	
						}
						
						
						
						
				        if ( rsmd.getColumnName(i).toUpperCase().equals("PKTABLE_NAME") ) {					
				        	rf.setPktable_name( rs.getString(i).toLowerCase() );
				        }
				        if ( rsmd.getColumnName(i).toUpperCase().equals("PKCOLUMN_NAME") ) {
				        	rf.setPkcolumn_name( rs.getString(i).toLowerCase() );
				        }
				        if ( rsmd.getColumnName(i).toUpperCase().equals("FKTABLE_NAME") ) {
				        	rf.setFktable_name( rs.getString(i).toLowerCase() );
				        }
				        if ( rsmd.getColumnName(i).toUpperCase().equals("FKCOLUMN_NAME") ) {
				        	rf.setFkcolumn_name( rs.getString(i).toLowerCase() );
				        }
				        if ( rsmd.getColumnName(i).toUpperCase().equals("PKTABLE_SCHEM") ) {
				        	rf.setSchemaname( rs.getString(i).toLowerCase() );
				        }
					}
			        
				}
				fieldList.add( rf );
				n++;

			}
			rs.close();
			

		} catch (Exception e) {
			System.err.println("Erro "+e);
		}

		return fieldList;
	}
	
	*/
	
	
	
	
	public List<Relations> getRelations() {
		String fkName = "";
		List<Relations> listRelationsFields = new ArrayList<Relations>();
		List<Relations> listRelationsFieldsRet = new ArrayList<Relations>();
		
		try	{
			
	        DatabaseMetaData dbmd = connection.getMetaData();
			
			ResultSet rs = dbmd.getImportedKeys(null,null,table); 


			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			
			
			
			while (rs.next()) {
				
				Relations relations = new Relations();
				
				
				for (int i=1;i<=cols;i++) {
					
					if (rs.getString(i)!=null) {
						
						System.out.println("rsmd.getColumnName(i).toUpperCase() "+rsmd.getColumnName(i).toUpperCase() );
						System.out.println("rs.getString(i).toLowerCase()       "+rs.getString(i).toLowerCase() );
						
						
						relations.setTablename(table);
						
				        if ( rsmd.getColumnName(i).toUpperCase().equals("PKTABLE_SCHEM") ) {
				        	relations.setTableschema( rs.getString(i).toLowerCase() );
				        }
						if ( rsmd.getColumnName(i).toUpperCase().equals("FK_NAME") ) {
							relations.setFkname( rs.getString(i).toLowerCase() );
						}
				        if ( rsmd.getColumnName(i).toUpperCase().equals("PKTABLE_NAME") ) {					
				        	relations.setPktable( rs.getString(i).toLowerCase() );
				        }
				        if ( rsmd.getColumnName(i).toUpperCase().equals("PKCOLUMN_NAME") ) {
				        	relations.setPkfield( rs.getString(i).toLowerCase() );
				        }
				        if ( rsmd.getColumnName(i).toUpperCase().equals("FKTABLE_NAME") ) {
				        	relations.setFktable( rs.getString(i).toLowerCase() );
				        }
				        if ( rsmd.getColumnName(i).toUpperCase().equals("FKCOLUMN_NAME") ) {
				        	relations.setFkfield( rs.getString(i).toLowerCase() );
				        }
					}
			        
				}
				listRelationsFields.add( relations );

			}
			rs.close();
			
			
			

			Relations relationsFieldsNew = null;
			
			for (int i=0; i<=listRelationsFields.size()-1; i++) {
				Relations relations = listRelationsFields.get(i);
				
				if ( !relations.getFkname().equals(fkName) ) {
					relationsFieldsNew = new Relations();
					relationsFieldsNew.setFkname( relations.getFkname() );
			        relationsFieldsNew.setTablename( relations.getTablename() ); 
					relationsFieldsNew.setPkfield( relations.getPkfield() );
					relationsFieldsNew.setPktable( relations.getPktable() ); 
					relationsFieldsNew.setFkfield( relations.getFkfield() );
					relationsFieldsNew.setReturnfield( relations.getReturnfield() );
					relationsFieldsNew.setCustomfieldname( relations.getCustomfieldname() );
					listRelationsFieldsRet.add( relationsFieldsNew );
				} else {
					relationsFieldsNew = listRelationsFieldsRet.get(i-1);
					relationsFieldsNew.setPkfield( relationsFieldsNew.getPkfield()+", "+relations.getPkfield() );
					relationsFieldsNew.setFkfield( relationsFieldsNew.getFkfield()+", "+relations.getFkfield() );
					listRelationsFieldsRet.set(i-1, relationsFieldsNew);
				}
				
				fkName = relations.getFkname();
			}			

		} catch (Exception e) {
			System.err.println(e);
		}

		return listRelationsFieldsRet;
	}
	




	public String getTable() {
		return table;
	}


	public void setTable(String table) {
		this.table = table;
	}	
	
	
	
	
}
