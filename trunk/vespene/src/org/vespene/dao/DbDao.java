package org.vespene.dao;

import org.vespene.orm.Db;

 

public class DbDao  {
	private Db dbConfig;

	private static final long serialVersionUID = 1L;


	public DbDao() {
		System.out.println("DbDao");
		//super();
	}





	public Db getDbConfig() {
		
		System.out.println("DbDao getDbConfig");
		/*
		Db d = new Db();
		d.setId(2);
		d.setName("dmanager.fdb");
		d.setDriver("org.firebirdsql.jdbc.FBDriver");
		d.setDb("jdbc:firebirdsql:localhost:dmanager.fdb");
		d.setUser("SYSDBA");
		d.setPass("masterkey");
		d.setIsschema(false);		
		return d;
		*/
		return dbConfig;
	}

	
	


	public void setDbConfig(Db dbConfig) {
		this.dbConfig = dbConfig;
	}





	
}	
	
	
	


