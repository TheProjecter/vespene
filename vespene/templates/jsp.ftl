<%@ page language="java"%> 

<%
	String idmenu = request.getParameter("idmenu");
	boolean alone;
	if ( ( request.getParameter("alone")!=null ) &&  ( request.getParameter("alone").equals("true") ) ) {
%>
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="pragma" content="no-cache" />
	<link rel="stylesheet" type="text/css" href="../../ext-2.0.1/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="../css/desktop.css" />
	<script type="text/javascript" src="../../ext-2.0.1/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="../../ext-2.0.1/ext-all.js"></script>

	<script type="text/javascript">
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'qtip'; 
		Ext.BLANK_IMAGE_URL = '../../view/images/s.gif';
		
		Ext.apply(Ext.QuickTips.getQuickTip(), {
		    showDelay: 50,
		    trackMouse: true
		});
	</script>

	<script type="text/javascript" src="aerin/printWindow.js"></script>
	<script type="text/javascript" src="aerin/overRadio.js"></script>	
	<script type="text/javascript" src="aerin/uxmedia.js"></script>		
	<script type="text/javascript" src="aerin/mediawindow.js"></script>	
	<script type="text/javascript" src="aerin/util.js"></script>						
	<script type="text/javascript" src="aerin/Ext.Form.ColorField.js"></script>
	<script type="text/javascript" src="aerin/Ext.ux.Decimal4Field.js"></script>
	<script type="text/javascript" src="aerin/Ext.ux.Decimal6Field.js"></script>
	<script type="text/javascript" src="aerin/Ext.ux.Decimal2Field.js"></script>
	<script type="text/javascript" src="aerin/Ext.ux.netbox.InputTextMask.js"></script>
	<script type="text/javascript" src="aerin/Ext.ux.form.RadioGroup.js"></script>
	<script type="text/javascript" src="aerin/lookup.js"></script>					
	<script type="text/javascript" src="aerin/fieldscontrol.js"></script>						
	<script type="text/javascript" src="aerin/Ext.form.EditTextButton.js"></script>		
	<script type="text/javascript" src="aerin/FormatCurrencyUS.js"></script>
	<script type="text/javascript" src="aerin/lookup.js"></script>
	<script type="text/javascript" src="aerin/Ext.ux.form.XCheckbox.js"></script>	
	
	<script type="text/javascript" src="aerin/sessvars.js"></script>
	<script type="text/javascript" src="view/js/security/menuform.js"></script>
	
<%	} %>

	<script type="text/javascript">
		//sessvars.$.debug();
		sessvars.idmenu = <%=idmenu%>;
	</script>	
	
		

	<#list table.fields as f>
		<#if f.isrelation?string = "true">
	<script type="text/javascript" src="../js/dataset/${f.relations.pktable_name?upper_case}dataset.js"></script>
		</#if>  	   
	</#list>
	
	<script type="text/javascript" src="../js/dataset/${table.tablename?upper_case}dataset.js"></script>
	<script type="text/javascript" src="../js/form/${table.tablename?upper_case}form.js"></script>
	<script type="text/javascript" src="../js/search/${table.tablename?upper_case}search.js"></script>



<div id="${table.tablename?lower_case}-window" class="x-hidden">
</div>

<div id="${table.tablename?lower_case}-search" class="x-hidden">
</div>	


