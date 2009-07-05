/*
http://www.hibernate.org/hib_docs/reference/en/html/queryhql.html1
*/		

package br.com.powermind.Search.query;

import java.util.HashMap;
import java.util.Map;
import br.com.powermind.Search.${pojo}Search;


public class ${pojo}HqlQuery {
	String hql;
	String order;
	${table.tablename?lower_case?cap_first}Search ${table.tablename?lower_case}Search; 
	Boolean recordCount;
	Map<String, Object> parameters = new HashMap<String, Object>();

		
	public ${pojo}HqlQuery(${pojo}Search ${table.tablename?lower_case}Search, Boolean recordCount) {		
		boolean firstClause = true;
		StringBuffer queryBuf = new StringBuffer("");
		
		
		if (recordCount) {
			queryBuf.append("select count(*) from ${table.tablename?cap_first} ");
		} else {
			queryBuf.append("from ${table.tablename?cap_first} ");
		}
		
		// testa os parametros
		/*
		if ( ${table.tablename?lower_case}Search.getId${table.tablename?lower_case}()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("id${table.tablename?lower_case}=:id${table.tablename?lower_case}");
			parameters.put("id${table.tablename?lower_case}", ${table.tablename?lower_case}Search.getId${table.tablename?lower_case}());
			firstClause = false;
		}
		*/
<#list table.fields as f>
	<#assign _fieldType = "${f.fieldtype}" >

	<#if _fieldType = "varchar" || _fieldType = "blob sub_type 1" || _fieldType = "char" >
		if ( ${table.tablename?lower_case}Search.getP${f.fieldname}()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname} like :p${f.fieldname}");
			parameters.put("p${f.fieldname}", "%"+${table.tablename?lower_case}Search.getP${f.fieldname}()+"%" );
			firstClause = false;
		}
	<#elseif _fieldType = "bool" >
		if ( ${table.tablename?lower_case}Search.getP${f.fieldname}()) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname} =:p${f.fieldname}");
			parameters.put("p${f.fieldname}", ${table.tablename?lower_case}Search.getP${f.fieldname}() );
			firstClause = false;
		}
	<#else> 
	   <#if f.ispk?string = "true">	
		if ( ${table.tablename?lower_case}Search.get${f.fieldname?lower_case?cap_first}()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname}=:${f.fieldname}");
			parameters.put("${f.fieldname}", ${table.tablename?lower_case}Search.get${f.fieldname?lower_case?cap_first}());
			firstClause = false;
		}
		</#if>
		<#if f.isrelation?string = "true">
		if ( ${table.tablename?lower_case}Search.get${f.fieldname?lower_case?cap_first}()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname}=:${f.fieldname}");
			parameters.put("${f.fieldname}", ${table.tablename?lower_case}Search.get${f.fieldname?lower_case?cap_first}());
			firstClause = false;
		}
		<#else>
		if ( ${table.tablename?lower_case}Search.getP${f.fieldname}ini()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname}>=:p${f.fieldname}ini");
			parameters.put("p${f.fieldname}ini", ${table.tablename?lower_case}Search.getP${f.fieldname}ini());
			firstClause = false;
		}

		if ( ${table.tablename?lower_case}Search.getP${f.fieldname}fim()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname}<=:p${f.fieldname}fim");
			parameters.put("p${f.fieldname}fim", ${table.tablename?lower_case}Search.getP${f.fieldname}fim());
			firstClause = false;
		}
		</#if>
	
	</#if>
</#list>
				
				
				
<#list table.fields as f>
	<#switch f.ispk?string>
		<#case "true">
		if (!recordCount) {
			queryBuf.append(" order by ${f.fieldname} ");
		}
		<#break>
	</#switch>  			
</#list>	            

		hql = queryBuf.toString();

	}	
    
    
    
	public void Execute() {
		boolean firstClause = true;
		StringBuffer queryBuf = new StringBuffer("");
		
		
		if (recordCount) {
			queryBuf.append("select count(*) from ${table.tablename?cap_first} ");
		} else {
			queryBuf.append("from ${table.tablename?cap_first} ");
		}
		
		// testa os parametros
		/*
		if ( ${table.tablename?lower_case}Search.getId${table.tablename?lower_case}()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("id${table.tablename?lower_case}=:id${table.tablename?lower_case}");
			parameters.put("id${table.tablename?lower_case}", ${table.tablename?lower_case}Search.getId${table.tablename?lower_case}());
			firstClause = false;
		}
		*/
<#list table.fields as f>
	<#assign _fieldType = "${f.fieldtype}" >

	<#if _fieldType = "varchar" || _fieldType = "blob sub_type 1" || _fieldType = "char" >
		if ( ${table.tablename?lower_case}Search.getP${f.fieldname}()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname} like :p${f.fieldname}");
			parameters.put("p${f.fieldname}", "%"+${table.tablename?lower_case}Search.getP${f.fieldname}()+"%" );
			firstClause = false;
		}
	<#elseif _fieldType = "bool" >
		if ( ${table.tablename?lower_case}Search.getP${f.fieldname}()) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname} =:p${f.fieldname}");
			parameters.put("p${f.fieldname}", ${table.tablename?lower_case}Search.getP${f.fieldname}() );
			firstClause = false;
		}
	<#else> 
	   <#if f.ispk?string = "true">		
		if ( ${table.tablename?lower_case}Search.get${f.fieldname?lower_case?cap_first}()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname}=:${f.fieldname}");
			parameters.put("${f.fieldname}", ${table.tablename?lower_case}Search.get${f.fieldname?lower_case?cap_first}());
			firstClause = false;
		}
	   </#if>
	
		<#if f.isrelation?string = "true">
		if ( ${table.tablename?lower_case}Search.get${f.fieldname?lower_case?cap_first}()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname}=:${f.fieldname}");
			parameters.put("${f.fieldname}", ${table.tablename?lower_case}Search.get${f.fieldname?lower_case?cap_first}());
			firstClause = false;
		}
		<#else>
		if ( ${table.tablename?lower_case}Search.getP${f.fieldname}ini()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname}>=:p${f.fieldname}ini");
			parameters.put("p${f.fieldname}ini", ${table.tablename?lower_case}Search.getP${f.fieldname}ini());
			firstClause = false;
		}

		if ( ${table.tablename?lower_case}Search.getP${f.fieldname}fim()!=null ) {
			queryBuf.append(firstClause ? " where " : " and ");
			queryBuf.append("${f.fieldname}<=:p${f.fieldname}fim");
			parameters.put("p${f.fieldname}fim", ${table.tablename?lower_case}Search.getP${f.fieldname}fim());
			firstClause = false;
		}
		</#if>
	</#if>    
</#list>
				
				
				
<#list table.fields as f>
	<#switch f.ispk?string>
		<#case "true">
		if (!recordCount) {
			if (order==null) {		
				queryBuf.append(" order by ${f.fieldname} ");
			} else {
				queryBuf.append( order );
			}
		}
		<#break>
	</#switch>  			
</#list>	            

		hql = queryBuf.toString();	
	
	}    
    
    
    
    
	
	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}	
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Boolean getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Boolean recordCount) {
		this.recordCount = recordCount;
	}


	 

	public ${pojo?lower_case?cap_first}Search get${table.tablename?lower_case?cap_first}Search() {
		return ${table.tablename?lower_case}Search;
	}

	public void set${pojo?lower_case?cap_first}Search(${table.tablename?lower_case?cap_first}Search ${table.tablename?lower_case}Search) {
		this.${table.tablename?lower_case}Search = ${table.tablename?lower_case}Search;
	}	


}
