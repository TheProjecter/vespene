var store${table.tablename?upper_case};

Ext.onReady(function(){

    store${table.tablename?upper_case} = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({url: '${table.tablename?lower_case}.action'}),  
	reader: new Ext.data.XmlReader({
               record: 'dados${table.tablename?lower_case?cap_first}',
               id: 'id${table.tablename?lower_case}',
               totalRecords: 'pager > @recordCount'  
           }, [
	<#list table.fields as f>
		<#if f.ispk?string = "true">
		{name: 'id${table.tablename?lower_case}', type: 'int', mapping: 'dados${table.tablename?lower_case?cap_first} > id${table.tablename?lower_case}'},
		</#if>
		<#assign _fieldType = "${f.fieldtype}" >
			<#if _fieldType = "varchar" || _fieldType = "blob sub_type 1" || _fieldType = "char" || _fieldType = "bpchar" || _fieldType = "bool" >
		{name: '${f.fieldname}', type: 'string'}<#if f_has_next>,</#if>
			<#elseif _fieldType = "integer" || _fieldType = "int4">
		{name: '${f.fieldname}', type: 'int'}<#if f_has_next>,</#if>
			<#elseif _fieldType = "numeric">
		{name: '${f.fieldname}', type: 'string'}<#if f_has_next>,</#if>
			<#elseif _fieldType = "date">
		{name: '${f.fieldname}', type: 'date', dateFormat: 'Y-m-d'}<#if f_has_next>,</#if>
			</#if> 
			<#if f.isrelation?string = "true">
		{name: '${f.relations.referencialcolumn_name}', type: 'string'}<#if f_has_next>,</#if>		
			</#if>
	</#list>  
           ])
    });

  // store${table.tablename?upper_case}.load();
  // trigger the data store load
  // store${table.tablename?upper_case}.load({params:{start:0, limit:10}});
 	
});