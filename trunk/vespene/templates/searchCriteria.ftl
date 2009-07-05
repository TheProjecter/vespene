		
/*
http://www.hibernate.org/hib_docs/v3/api/org/hibernate/criterion/Restrictions.html
http://www.hibernate.org/hib_docs/reference/en/html/querycriteria.html

eq(String propertyName, Object value)         Apply an "equal" constraint to the named property
ge(String propertyName, Object value)         Apply a "greater than or equal" constraint to the named property
gt(String propertyName, Object value)         Apply a "greater than" constraint to the named property
le(String propertyName, Object value)         Apply a "less than or equal" constraint to the named property
lt(String propertyName, Object value)         Apply a "less than" constraint to the named property
ne(String propertyName, Object value)         Apply a "not equal" constraint to the named property
*/		

package br.com.powermind.Search;

import java.util.ArrayList;

public class ${pojo}Criteria {
	DetachedCriteria detachedCriteria;		
	
	public ${pojo}Criteria(${pojo}Search ${table.tablename?lower_case}Search) {

		
		detachedCriteria = DetachedCriteria.forClass(${pojo}.class);
		
		// testa os parametros
<#list table.fields as f>
	<#assign _fieldType = "${f.fieldtype}" >

	<#if _fieldType = "varchar" || _fieldType = "blob sub_type 1" || _fieldType = "char" >
		if ( ${table.tablename?lower_case}Search.getP${f.fieldname}()!=null ) {
			detachedCriteria.add( Restrictions.like( "${f.fieldname}", "%"+${table.tablename?lower_case}Search.getP${f.fieldname}()+"%" ) );
		}
	<#else> 
		if ( ${table.tablename?lower_case}Search.getP${f.fieldname}ini()!=null ) {
			detachedCriteria.add( Restrictions.ge( "${f.fieldname}", ${table.tablename?lower_case}Search.getP${f.fieldname}ini() ) );		
		}

		if ( ${table.tablename?lower_case}Search.getP${f.fieldname}fim()!=null ) {
			detachedCriteria.add( Restrictions.le( "${f.fieldname}", ${table.tablename?lower_case}Search.getP${f.fieldname}fim() ) );		
		}
	</#if>    
</#list>
				
	
	            
<#list table.fields as f>
	<#switch f.ispk?string>
		<#case "true">
		detachedCriteria.addOrder( Order.asc("${f.fieldname}") );
		<#break>
	</#switch>  			
</#list>	            

	}	
    
	
	
	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}



	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}    	
	

}
