package br.com.powermind.ORM.Ext;

import br.com.powermind.ORM.${pojo};

public class ${pojo}Ext extends ${pojo} {
<#list table.fields as f>
   <#assign _fieldType = "${f.fieldtype}" >
<#if f.isrelation?string = "true">   
   <#if _fieldType = "varchar">
	private String p${f.fieldname?lower_case};
   <#elseif _fieldType = "char">
	private String p${f.fieldname?lower_case};
   <#elseif _fieldType = "integer" || _fieldType = "int4">
	private Integer ${f.fieldname?lower_case};
   <#elseif _fieldType = "double precision" >	
	private Double ${f.fieldname?lower_case};
   <#elseif _fieldType = "numeric" >		
	private Double ${f.fieldname?lower_case};
   <#elseif _fieldType = "date">	
	private Date ${f.fieldname?lower_case};
   <#else>
   	private String ${f.fieldname?lower_case};
   </#if>
	private String ${f.relations.referencialcolumn_name?lower_case};      
</#if>   
</#list>

<#list table.fields as f>
	<#assign _fieldName = "${f.fieldname}" >
	<#assign _fieldType = "${f.fieldtype}" >	
<#if f.isrelation?string = "true">   	
   <#if _fieldType = "varchar">
	public String get${_fieldName?lower_case?cap_first}() {
		return ${_fieldName?lower_case};
	}

	public void set${_fieldName?lower_case?cap_first}(String ${_fieldName?lower_case}) {
		this.${f.fieldname?lower_case} = ${f.fieldname?lower_case};
	}	
   <#elseif _fieldType = "char">
	public String get${_fieldName?lower_case?cap_first}() {
		return ${_fieldName?lower_case};
	}

	public void set${_fieldName?lower_case?cap_first}(String ${_fieldName?lower_case}) {
		this.${f.fieldname?lower_case} = ${f.fieldname?lower_case};
	}	
   <#elseif _fieldType = "integer" || _fieldType = "int4">
	public Integer get${_fieldName?lower_case?cap_first}() {
		return ${_fieldName?lower_case};
	}

	public void set${_fieldName?lower_case?cap_first}(Integer ${_fieldName?lower_case}) {
		this.${f.fieldname?lower_case} = ${f.fieldname?lower_case};
	}
	<#elseif _fieldType = "double precision" || _fieldType = "numeric" >	
	public Double get${_fieldName?lower_case?cap_first}() {
		return ${_fieldName?lower_case};
	}

	public void set${_fieldName?lower_case?cap_first}(Double ${_fieldName?lower_case}) {
		this.${f.fieldname?lower_case} = ${f.fieldname?lower_case};
	}
   <#elseif _fieldType = "date">	
	public Date get${_fieldName?lower_case?cap_first}() {
		return ${_fieldName?lower_case};
	}

	public void set${_fieldName?lower_case?cap_first}(Date ${_fieldName?lower_case}) {
		this.${f.fieldname?lower_case} = ${f.fieldname?lower_case};
	}
   <#else>
	public String get${_fieldName?lower_case?cap_first}() {
		return ${_fieldName?lower_case};
	}

	public void set${_fieldName?lower_case?cap_first}(String ${_fieldName?lower_case}) {
		this.${f.fieldname?lower_case} = ${f.fieldname?lower_case};
	}
   </#if>    
	public String get${f.relations.referencialcolumn_name?lower_case?cap_first}() {
		return ${f.relations.referencialcolumn_name?lower_case};
	}

	public void set${f.relations.referencialcolumn_name?lower_case?cap_first}(String ${f.relations.referencialcolumn_name?lower_case}) {
		this.${f.relations.referencialcolumn_name?lower_case} = ${f.relations.referencialcolumn_name?lower_case};
	}
</#if>     
</#list>

}
