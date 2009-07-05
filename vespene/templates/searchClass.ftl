package br.com.powermind.Search;

import java.util.Date;

public class ${pojo}Search {
	
	//private Integer id${pojo?lower_case};			
<#list table.fields as f>
   <#assign _fieldType = "${f.fieldtype}" >
   <#if _fieldType = "varchar">
	private String p${f.fieldname};
   <#elseif _fieldType = "char">
	private String p${f.fieldname};
   <#elseif _fieldType = "integer" || _fieldType = "int4">
   	<#if f.ispk?string = "true">
   	private Integer ${f.fieldname};
    </#if>	
	<#if f.isrelation?string = "true">
	private Integer ${f.fieldname};
	<#else>
	private Integer p${f.fieldname}ini;
	private Integer p${f.fieldname}fim;
	</#if>
   <#elseif _fieldType = "bool" >	
	private Boolean p${f.fieldname} = false;
   <#elseif _fieldType = "double precision" >	
	private Double p${f.fieldname}ini;
	private Double p${f.fieldname}fim;
   <#elseif _fieldType = "numeric" >		
	private Double p${f.fieldname}ini;
	private Double p${f.fieldname}fim;
   <#elseif _fieldType = "date">	
	private Date p${f.fieldname}ini;
	private Date p${f.fieldname}fim;
   <#else>
   	private String p${f.fieldname};
   </#if>    
	<#if _fieldType != "bool" >	
	private Boolean ncheck${table.tablename?upper_case}${f.fieldname} = false;
	</#if>
</#list>

/*
	public Integer getId${pojo?lower_case}() {
		return id${pojo?lower_case};
	}

	public void setId${pojo?lower_case}(Integer id${pojo?lower_case}) {
		this.id${pojo?lower_case} = id${pojo?lower_case};
	}
	*/
<#list table.fields as f>
	<#assign _fieldName = "${f.fieldname}" >
	<#assign _fieldType = "${f.fieldtype}" >	
   <#if _fieldType = "varchar">
	public String getP${_fieldName}() {
		return p${_fieldName};
	}

	public void setP${_fieldName}(String p${_fieldName}) {
		this.p${f.fieldname} = p${f.fieldname};
	}	
   <#elseif _fieldType = "char">
	public String getP${_fieldName}() {
		return p${_fieldName};
	}

	public void setP${_fieldName}(String p${_fieldName}) {
		this.p${f.fieldname} = p${f.fieldname};
	}	
	<#elseif _fieldType = "bool">
	public Boolean getP${_fieldName}() {
		return p${_fieldName};
	}

	public void setP${_fieldName}(Boolean p${_fieldName}) {
		this.p${f.fieldname} = p${f.fieldname};
	}
   <#elseif _fieldType = "integer" || _fieldType = "int4">
   <#if f.ispk?string = "true">
	public Integer get${_fieldName?lower_case?cap_first}() {
		return ${_fieldName};
	}

	public void set${_fieldName?lower_case?cap_first}(Integer ${_fieldName}) {
		this.${f.fieldname} = ${f.fieldname};
	}
	</#if>
	
	<#if f.isrelation?string = "true">
	public Integer get${_fieldName?lower_case?cap_first}() {
		return ${_fieldName};
	}

	public void set${_fieldName?lower_case?cap_first}(Integer ${_fieldName}) {
		this.${f.fieldname} = ${f.fieldname};
	}
	<#else>
	public Integer getP${_fieldName}ini() {
		return p${_fieldName}ini;
	}

	public void setP${_fieldName}ini(Integer p${_fieldName}ini) {
		this.p${f.fieldname}ini = p${f.fieldname}ini;
	}
	
	public Integer getP${_fieldName}fim() {
		return p${_fieldName}fim;
	}

	public void setP${_fieldName}fim(Integer p${_fieldName}fim) {
		this.p${f.fieldname}fim = p${f.fieldname}fim;
	}
	</#if>
	<#elseif _fieldType = "double precision" || _fieldType = "numeric" >	
	public Double getP${_fieldName}ini() {
		return p${_fieldName}ini;
	}

	public void setP${_fieldName}ini(Double p${_fieldName}ini) {
		this.p${f.fieldname}ini = p${f.fieldname}ini;
	}
	
	public Double getP${_fieldName}fim() {
		return p${_fieldName}fim;
	}

	public void setP${_fieldName}fim(Double p${_fieldName}fim) {
		this.p${f.fieldname}fim = p${f.fieldname}fim;
	}
   <#elseif _fieldType = "date">	
	public Date getP${_fieldName}ini() {
		return p${_fieldName}ini;
	}

	public void setP${_fieldName}ini(Date p${_fieldName}ini) {
		this.p${f.fieldname}ini = p${f.fieldname}ini;
	}
	
	public Date getP${_fieldName}fim() {
		return p${_fieldName}fim;
	}

	public void setP${_fieldName}fim(Date p${_fieldName}fim) {
		this.p${f.fieldname}fim = p${f.fieldname}fim;
	}
   <#else>
	public String getP${_fieldName}() {
		return p${_fieldName};
	}

	public void setP${_fieldName}(String p${_fieldName}) {
		this.p${f.fieldname} = p${f.fieldname};
	}

   </#if>    
	
	<#if _fieldType != "bool">
	public Boolean getNcheck${table.tablename?upper_case}${f.fieldname}() {
		return ncheck${table.tablename?upper_case}${_fieldName};
	}
	
	public void setNcheck${table.tablename?upper_case}${f.fieldname}(Boolean ncheck${table.tablename?upper_case}${_fieldName}) {
		this.ncheck${table.tablename?upper_case}${_fieldName} = ncheck${table.tablename?upper_case}${_fieldName};
	}
	</#if>
 

</#list>

}
