/*
INSERT INTO menu (text, iconcls, handle, idparent, ordem, isform, isbutton)
VALUES ('Novo', 'add', 'space${table.tablename?lower_case?cap_first}.onNewClick()', idmenu, 0, true, false);

INSERT INTO menu (text, iconcls, handle, idparent, ordem, isform, isbutton)
VALUES ('Editar', 'edit', 'space${table.tablename?lower_case?cap_first}.onEditClick()', idmenu, 1, true, false);


INSERT INTO menu (text, iconcls, handle, idparent, ordem, isform, isbutton)
VALUES ('Salvar', 'save', 'space${table.tablename?lower_case?cap_first}.onSaveClick()', idmenu, 2, true, false);

INSERT INTO menu (text, iconcls, handle, idparent, ordem, isform, isbutton)
VALUES ('Cancelar', 'cancel', 'space${table.tablename?lower_case?cap_first}.onCancelClick()', idmenu, 3, true, false);

INSERT INTO menu (text, iconcls, handle, idparent, ordem, isform, isbutton)
VALUES ('Excluir', 'delete', 'space${table.tablename?lower_case?cap_first}.onDeleteClick()', idmenu, 4, true, false);

INSERT INTO menu (text, iconcls, handle, idparent, ordem, isform, isbutton)
VALUES ('Pesquisar', 'search', 'space${table.tablename?lower_case?cap_first}.onSearchClick()', idmenu, 5, true, false);

INSERT INTO menu (text, iconcls, handle, idparent, ordem, isform, isbutton)
VALUES ('Imprimir', 'print', 'space${table.tablename?lower_case?cap_first}.onPrintClick()', idmenu, 6, true, false);
*/






Ext.ns('space${table.tablename?lower_case?cap_first}');


Ext.form.XmlErrorReader = function(){
    Ext.form.XmlErrorReader.superclass.constructor.call(this, {
            record : 'field',
            success: '@success'
        }, [
            'id', 'msg'
        ]
    );
};
Ext.extend(Ext.form.XmlErrorReader, Ext.data.XmlReader);
 

Ext.onReady(function() {

	space${table.tablename?lower_case?cap_first}.idmenu = null;

	space${table.tablename?lower_case?cap_first}.chkButton = function(item) {
		Ext.each(item, function(res) {
			//console.log( 'space${table.tablename?lower_case?cap_first}.chkButton ', res, res.event );
		});
    };
	
	space${table.tablename?lower_case?cap_first}.onNewClick = function(item) {		
 		space${table.tablename?lower_case?cap_first}.onState('new');
    };

	space${table.tablename?lower_case?cap_first}.onEditClick = function(item) {		
 		space${table.tablename?lower_case?cap_first}.onState('edit');
    };

	space${table.tablename?lower_case?cap_first}.onSaveClick = function(item) {	
	
		spaceFieldsControl.enable( {
				form : space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}
			}
		); 		
		
 		
        space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().submit({
        	url:'${table.tablename?lower_case}!save.action', 
			method:'POST', 
			waitTitle:'Progresso...',
			waitMsg:'Enviando...',
			success:function(form, action) {
			
				Ext.Msg.show({
				   minWidth : 200, 
				   closable : false,
				   msg: 'Salvo com sucesso !',
				   buttons: Ext.Msg.OK,
				   fn: function(btn, text) {
						if (space${table.tablename?lower_case?cap_first}.state == 'edit') {
							if ( store${table.tablename?upper_case}.lastOptions.params===undefined ) {
								store${table.tablename?upper_case}.load();
							} else {
								store${table.tablename?upper_case}.load({params:{start:store${table.tablename?upper_case}.lastOptions.params.start, limit:store${table.tablename?upper_case}.lastOptions.params.limit}});						
							}						
						} else if (space${table.tablename?lower_case?cap_first}.state == 'new') {
							var xml = action.response.responseXML;
							var recordCount = Ext.DomQuery.selectNode('pager', xml, 0).attributes[0].nodeValue; 
							var extra = recordCount % 10; 
							var lastStart = extra ? (recordCount - extra) : recordCount-10; 
							store${table.tablename?upper_case}.load({params:{start: lastStart, limit: 10}});		
						}
				   },   
				   icon: Ext.MessageBox.INFO
				});			
			
			},
			failure : function(form, action) { 
				if (action.response.status==200) {
					Ext.Msg.show({
					   minWidth : 200, 
					   closable : true,
					   msg: 'Verifique os erros no formulário !',
					   buttons: Ext.Msg.OK,
					   icon: Ext.MessageBox.ERROR,
					   fn: function(btn, text) {
					   		space${table.tablename?lower_case?cap_first}.onState('browse');
					   }   
					});		                        
				} else {
					Ext.Msg.show({
					   minWidth : 200, 
					   closable : true,
					   msg: 'O Servidor não responde a requisição !',
					   buttons: Ext.Msg.OK,
					   icon: Ext.MessageBox.ERROR,
					   fn: function(btn, text) {
							space${table.tablename?lower_case?cap_first}.onState('browse');
					   }   
					});	 		  					
				}  		                        
			}
		});
    };
    
	space${table.tablename?lower_case?cap_first}.onDeleteClick = function(item) {		    
	    space${table.tablename?lower_case?cap_first}.onState('browse');
		Ext.MessageBox.buttonText.yes = 'Sim';
		Ext.MessageBox.buttonText.no  = 'Não';
		keyValue = space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getSelectionModel().getSelected().get('id${table.tablename?lower_case}');
		
		
		
		Ext.Msg.show({
		   minWidth : 200, 
		   title: 'Confirmar',
		   closable : true,
		   msg: 'Confirmar a exclusão ?',
		   buttons: Ext.Msg.YESNO,
		   icon: Ext.MessageBox.QUESTION,
		   fn: function(btn, text) {
		   
			    if (btn == 'yes') {
					var req = new Ext.data.Connection({
						url: '${table.tablename?lower_case}!delete.action'
					});
					req.on({
					'beforerequest': function(){
						Ext.Msg.wait('Apagando ${table.tablename?lower_case?cap_first}(s)','Aguarde..');
					},
					'requestcomplete': function(){
						Ext.Msg.hide();
					}
					});		    
					req.request({
					
					    params: { id${table.tablename?lower_case}: keyValue },
					    scriptTag: true, 
					    scripts: true,
					    success: function(response) {
					    	var xml = response.responseXML;
							var success = Ext.DomQuery.selectNode('response', xml, 0).attributes[0].value;
							if (success=='true') {
								var rec = space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getSelectionModel().getSelected();
						    
								Ext.Msg.show({
								   minWidth : 200, 
								   //title: 'Confirmar',
								   closable : true,
								   msg: 'Excluido com sucesso !',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO,
								   fn: function(btn, text) {
										if ( store${table.tablename?upper_case}.lastOptions.params===undefined ) {
											store${table.tablename?upper_case}.load();
										} else {
											store${table.tablename?upper_case}.load({params:{start:store${table.tablename?upper_case}.lastOptions.params.start, limit:store${table.tablename?upper_case}.lastOptions.params.limit}});						
										}
										
										space${table.tablename?lower_case?cap_first}.onState('browse');
																		
								   }   
								   
								});							    
					
							} else {
								Ext.Msg.show({
								   minWidth : 200, 
								   closable : true,
								   msg: 'Não foi possivel excluir !',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO,
								   fn: function(btn, text) {
										space${table.tablename?lower_case?cap_first}.onState('browse');
								   }   
								   
								});							
							}
					    },
						failure: function(){
							if (action.response.status==200) {
							
								Ext.Msg.show({
								   minWidth : 200, 
								   closable : true,
								   msg: 'Verifique os erros no formulário !',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO,
								   fn: function(btn, text) {
										space${table.tablename?lower_case?cap_first}.onState('browse');
								   }   
								   
								});								
							
							} else {
								Ext.Msg.show({
								   minWidth : 200, 
								   closable : true,
								   msg: 'O Servidor não responde a requisição !',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO,
								   fn: function(btn, text) {
										space${table.tablename?lower_case?cap_first}.onState('browse');
								   }   
								   
								});									   	
							   	  		  					
							}		
						}
					});					
			        
			    } else {
			    	space${table.tablename?lower_case?cap_first}.onState('browse');
			    }
		   
		   }   
		   
		});		    		
		
	    
    };
    
    
	space${table.tablename?lower_case?cap_first}.onCancelClick = function(item) {		        
		Ext.MessageBox.buttonText.yes = 'Sim';
		Ext.MessageBox.buttonText.no = 'Não';
		
		if (space${table.tablename?lower_case?cap_first}.state=='new') {
			space${table.tablename?lower_case?cap_first}.msgcancel = 'Cancelar a inclusão ?';				
		} else if (space${table.tablename?lower_case?cap_first}.state=='edit') {
			space${table.tablename?lower_case?cap_first}.msgcancel = 'Cancelar a edição ?';	
		}
		
		Ext.Msg.show({
		   minWidth : 200, 
		   title: 'Confirmar',
		   closable : true,
		   msg: space${table.tablename?lower_case?cap_first}.msgcancel,
		   buttons: Ext.Msg.YESNO,
		   icon: Ext.MessageBox.QUESTION,
		   fn: function(btn, text) {
			    if (btn == 'yes'){
			    	var rec = space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getSelectionModel().getSelected();
			    	space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getSelectionModel().selectRecords([rec]);
			    	space${table.tablename?lower_case?cap_first}.onState('browse');
			    } else {
		<#list table.fields as f>
			<#assign _index = "${f_index}" >
			<#if (_index="1")> 
					space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().findField('${f.fieldname?lower_case}').focus();
			</#if>
		</#list>
			    }
		   }   
		   
		});			
	    
    };
    
	space${table.tablename?lower_case?cap_first}.onSearchClick = function(item) {		            
	    spaceSearch${table.tablename?lower_case?cap_first}.showWindow(
	    	{
	    		callback  : 'space${table.tablename?lower_case?cap_first}.onState("browse")'
	    	}
	    );
    };
    
	space${table.tablename?lower_case?cap_first}.onPrintClick = function(item) {
		spacePrintWindow.show( 
	    	{
				url       : '${table.tablename?lower_case}!print.action?',
				callback  : 'space${table.tablename?lower_case?cap_first}.onState("browse")'
			} 
	    );			                
    };
    
	space${table.tablename?lower_case?cap_first}.onCloseClick = function(item) {	
		space${table.tablename?lower_case?cap_first}.win${table.tablename?upper_case}.destroy();
		space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.destroy();
		space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.destroy();
	
		space${table.tablename?lower_case?cap_first}.win${table.tablename?upper_case}.close();
		
		space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case} = null;
		space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case} = null;
		space${table.tablename?lower_case?cap_first}.win${table.tablename?upper_case} = null;	
		
		spaceSearch${table.tablename?lower_case?cap_first}.frm.destroy();
		spaceSearch${table.tablename?lower_case?cap_first}.winSearch${table.tablename?upper_case}.destroy();		

		spaceSearch${table.tablename?lower_case?cap_first}.frm = null;
		spaceSearch${table.tablename?lower_case?cap_first}.main${table.tablename?upper_case} = null;
		spaceSearch${table.tablename?lower_case?cap_first}.tb = null;
		spaceSearch${table.tablename?lower_case?cap_first}.keys = null;
		spaceSearch${table.tablename?lower_case?cap_first}.winSearch${table.tablename?upper_case} = null;				
    };
	
	space${table.tablename?lower_case?cap_first}.onState = function(type) {
	
		spaceMenuForm.setState(
			{
				type   : type,
				window : space${table.tablename?lower_case?cap_first}.win${table.tablename?upper_case}
			}
		);		
	
		
		if (type=='browse') {    
			space${table.tablename?lower_case?cap_first}.state = 'browse';
			
			//space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().reset();
			
			spaceFieldsControl.disable( 
				{
					form : space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}
				}
			); 	
			
			//spaceUf.win${table.tablename?upper_case}.focus();
			space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.enable();
			space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.focus();
			space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.resumeEvents();
			space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getView().focusRow(0);			
					
	 	} else if (type=='new') {  
	 	    space${table.tablename?lower_case?cap_first}.state = 'new';
	 	    
	 	    space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.disable();
	 	    space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.suspendEvents();

			spaceFieldsControl.enable(
				{
					form		:	space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case},
					ignorefields	:	['id${table.tablename?lower_case}'],
					clearfields		:	['id${table.tablename?lower_case}'],
					empty		:	true
				}
			);
			
	<#list table.fields as f>
		<#assign _index = "${f_index}" >
		<#if (_index="1")> 
			space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().findField('${f.fieldname?lower_case}').focus();
		</#if>
	</#list>			
			
        } else if (type=='edit') {   
	        space${table.tablename?lower_case?cap_first}.state = 'edit';
	        
	        space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.disable();
	        space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.suspendEvents();
			
			spaceFieldsControl.enable(
				{
					form		:	space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case},
					ignorefields	:	['id${table.tablename?lower_case}']
				}
			);
			
	<#list table.fields as f>
		<#assign _index = "${f_index}" >
		<#if (_index="1")> 
			space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().findField('${f.fieldname?lower_case}').focus();
		</#if>
	</#list>				
				
	 	}
	 	
		if (! space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getSelectionModel().hasSelection()) {
			space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getSelectionModel().selectFirstRow();
		}
    };
	
    space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case} = new Ext.FormPanel({
  		id: '${table.tablename?lower_case}-form',
        frame: true,
        labelAlign: 'left',
        labelWidth: 85,
        reader: store${table.tablename?upper_case}.reader,
		errorReader: new Ext.form.XmlErrorReader(),
		items: [
			new Ext.Panel({

			    items: [{
			    	id: 'fieldset${table.tablename?lower_case?cap_first}',
			        //title: 'Dados : ${table.tablename?lower_case?cap_first}',
			        frame: true,
			        autoHeight: true,
			        labelAlign: 'top',
					items: [    	
	    				<#list table.fields as f>
	    				<#if f.quebra?string = "1">
	    			{ // inicio linha 1
				    	layout:'table',
	    				defaults: {
	    					bodyStyle:'padding:0px 5px 5px'
	    				},
	    				layoutConfig: {
		        			columns: 3
	    				},
	    				items: [
	    				</#if>
						<#if f.isrelation?string = "true">
						{ // abre combo
				        items: [{ 
							layout:'form',
								items: [{
								    layout:'table',
					    			defaults: {
					    				bodyStyle:'padding:0px 5px 0px'
					    			},
					    			layoutConfig: {
						        		columns: 2
					    			},
					    			items: [{
										items: [{ 
											layout:'form', 
											items: [{ xtype:'textfield', 
														//id: '${f.fieldname}', 
														name: '${f.fieldname?lower_case}', 														
														fieldLabel: 'Id',  
														maxLength: ${f.fieldlen}, 
														width:${f.fieldlen*4}, 
														style : {textTransform: "uppercase"},
														listeners: { 'blur': function() { 
																		spaceLookUp.find(
																			{
																				url			: '${f.fieldname?substring(2)}.action',
																				params		: {${f.fieldname}: this.getValue() },
																				form		: space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case},
																				fields : [ ['${f.relations.pkcolumn_name?lower_case}','${f.relations.pkcolumn_name?lower_case}'],['${f.relations.referencialcolumn_name?lower_case}','${f.relations.lookupcolumn_name?lower_case}'] ],
																				datanode	: 'dados${f.fieldname?substring(2)?cap_first}'
																			}
																		);
																	  } 
																	  
														}														
													}]
										}]
								    	},{
										items: [{ 
											layout:'form',
											items: [
												new Ext.form.EditTextButton({
													//id: '${f.relations.referencialcolumn_name?lower_case}', 
													name: '${f.relations.referencialcolumn_name?lower_case}', 
													fieldLabel: '${f.relations.referencialcolumn_name?lower_case}',  
													width:200,  
													style : {textTransform: "uppercase"},
													listeners: {
															'click' : function() {
																spaceSearch${f.relations.pktable_name?lower_case?cap_first}.showWindow( 
																	{	enableSelect : true ,
																		form : space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case},
																		fields : [ ['${f.relations.pkcolumn_name?lower_case}','${f.relations.pkcolumn_name?lower_case}'],['${f.relations.referencialcolumn_name?lower_case}','${f.relations.lookupcolumn_name?lower_case}'] ]
																	} 
																);
															}
													}
												})
												
												
												]
											}]
										}]
									}]
								}]
						}<#if f_has_next>,</#if>  // fecha combo
						
						<#if f.ultimo?string = "1">
							]
						</#if>
						<#else> 
						{
							items: [{ 
								layout:'form',
								<#if f.ispk?string = "true">
								items: [{ xtype:'textfield', /*id: '${f.fieldname}',*/ fieldLabel: 'Id',  maxLength: 9, width:45, name: '${f.fieldname?lower_case}'}] 
							}]
			 					<#else>
								<#assign _fieldType = "${f.fieldtype}" >
								<#assign _fieldLen = "${f.fieldlen}" >
								<#if _fieldType = "varchar" || _fieldType = "blob sub_type 1" || _fieldType = "char" || _fieldType = "bpchar">
									<#if _fieldLen = "1">
								items: [ new Ext.form.Checkbox({boxLabel: '${f.fieldname?lower_case?cap_first}', fieldLabel: '', labelSeparator:'', /*id: 'c${f.fieldname}',*/ name: 'c${f.fieldname}', width:85 })	]
							}]
									<#elseif _fieldLen = "4">
								items: [ new Ext.form.TextField({ fieldLabel: '${f.fieldname?lower_case?cap_first}', /*id: '${f.fieldname}',*/ name: '${f.fieldname}',  maxLength: ${f.fieldlen}, width:35, plugins: [new Ext.ux.InputTextMask('(99)', true)] }) ]
							}]
									<#elseif _fieldLen = "9">
								items: [ new Ext.form.TextField({ fieldLabel: '${f.fieldname?lower_case?cap_first}', /*id: '${f.fieldname}',*/ name: '${f.fieldname}',  maxLength: ${f.fieldlen}, width:75, plugins: [new Ext.ux.InputTextMask('99999-999', true)] }) ]							
							}]
									<#elseif _fieldLen = "13">
								items: [ new Ext.form.TextField({ fieldLabel: '${f.fieldname?lower_case?cap_first}', /*id: '${f.fieldname}',*/ name: '${f.fieldname}',  maxLength: ${f.fieldlen}, width:75, plugins: [new Ext.ux.InputTextMask('99.999.999-99', true)] }) ]							
							}]
									<#elseif _fieldLen = "14">
								items: [ new Ext.form.TextField({ fieldLabel: '${f.fieldname?lower_case?cap_first}', /*id: '${f.fieldname}',*/ name: '${f.fieldname}',  maxLength: ${f.fieldlen}, width:75, plugins: [new Ext.ux.InputTextMask('999.999.999-99', true)] }) ]							
							}]
									<#elseif _fieldLen = "15">
								items: [ new Ext.form.TextField({ fieldLabel: '${f.fieldname?lower_case?cap_first}', /*id: '${f.fieldname}',*/ name: '${f.fieldname}',  maxLength: ${f.fieldlen}, width:75, plugins: [new Ext.ux.InputTextMask('999.999.999.999', true)] }) ]							
							}]
									<#elseif _fieldLen = "18">
								items: [ new Ext.form.TextField({ fieldLabel: '${f.fieldname?lower_case?cap_first}', /*id: '${f.fieldname}',*/ name: '${f.fieldname}',  maxLength: ${f.fieldlen}, width:75, plugins: [new Ext.ux.InputTextMask('99.999.999/9999-99', true)] }) ]							
							}]
									<#else>
								items: [{ xtype:'textfield', fieldLabel: '${f.fieldname?lower_case?cap_first}', /*id: '${f.fieldname}',*/   maxLength: ${f.fieldlen}, width:${f.fieldlen*5}, name: '${f.fieldname?lower_case}', 
								autoCreate: { tag: 'input', maxlength: '${f.fieldlen}' }, listeners: { 'blur': function() { this.setValue(Ext.util.Format.uppercase(this.getValue()));}}}]
							}]
									</#if>
								<#elseif _fieldType = "integer" || _fieldType = "int4">                                
								items: [{ xtype:'textfield', fieldLabel: '${f.fieldname?lower_case?cap_first}', /*id: '${f.fieldname}',*/   maxLength: ${f.fieldlen}, width:${f.fieldlen*5}, name: '${f.fieldname?lower_case}', 
								autoCreate: { tag: 'input', maxlength: '${f.fieldlen}' }, listeners: { 'blur': function() { this.setValue(Ext.util.Format.uppercase(this.getValue()));}}}]
							}]
								<#elseif _fieldType = "numeric">
								items: [ new Ext.ux.Decimal2Field({ fieldLabel: '${f.fieldname?lower_case?cap_first}', /*id: '${f.fieldname}',*/ name: '${f.fieldname}', width:85, showCurrency: false, selectOnFocus : true}) ]
							}]
								<#elseif _fieldType = "date">
								items: [ new Ext.form.DateField({ fieldLabel: '${f.fieldname?lower_case?cap_first}', /*id: '${f.fieldname}',*/ name: '${f.fieldname}', format: 'd/m/Y', altFormats:"d/m/Y|d-m-y|d-m-Y|d/m|d-m|dm|dmy|dmY|d|Y-m-d"}) ]
							}]								
								<#elseif _fieldType = "bool">
								items: [ new Ext.ux.form.XCheckbox({ labelSeparator: '', boxLabel: '${f.fieldname?lower_case?cap_first}', labelAlign: 'left', /*id: '${f.fieldname}',*/ name: '${f.fieldname}', checked: false }) ]
							}]								

								</#if> 
						</#if>}<#if f_has_next><#if f.fecha?string = "1"> <#else>,</#if></#if>
						</#if>
						
						<#if f.fecha?string = "1">
							<#if f_has_next>]},
							<#else>
								<#if f.ultimo?string = "1">
									] // FECHAMENTO
								</#if>
							</#if>
						<#else>
							<#if f.ultimo?string = "1">
									] // FECHAMENTO
							</#if>
							
						</#if>
						
						</#list>
						
	    			}]	
			    }] // fim da colecao
            })
        ]
    });
	 
	space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case} = new Ext.grid.GridPanel({
    	title:'',
    	frame: true,
    	stripeRows: true,
        store: store${table.tablename?upper_case},
        //viewConfig: {
			      //forceFit: true,  
			      //autoFill: true 
		//}, 
        columns: [
        	<#list table.fields as f>
        	<#assign _fieldType = "${f.fieldtype}" >
        	<#if f.ispk?string = "true">
    	    {header: "Id", width: 45, dataIndex: '${f.fieldname?lower_case}', sortable: true}<#if f_has_next>,</#if>
			<#else>
				<#if _fieldType = "date">
				{header: "${f.fieldname?lower_case?cap_first}", width: ${f.fieldlen*5}, dataIndex: '${f.fieldname?lower_case}', sortable: true, renderer: formatDate}<#if f_has_next>,</#if>
	    	    <#else>
				{header: "${f.fieldname?lower_case?cap_first}", width: ${f.fieldlen*5}, dataIndex: '${f.fieldname?lower_case}', sortable: true}<#if f_has_next>,</#if>
    	    	</#if>    	    	
    	    </#if>
            </#list>
        ],
        height:280,
		sm: new Ext.grid.RowSelectionModel({
			singleSelect: true,
	        listeners: {
	        	rowselect: function(sm, row, rec) {
	        		space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().loadRecord(rec);
	        		
	            	//selectedRow = row;
	            	<#list table.fields as f>
	            		<#assign _fieldType = "${f.fieldtype}" >
	            		<#assign _fieldLen = "${f.fieldlen}" >
						<#if f.isrelation?string = "true">
		            //space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().findField('t${f.fieldname?lower_case}').setValue( rec.data.${f.fieldname?lower_case} );
						</#if>
						<#if _fieldType = "numeric">
		            //space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().findField('${f.fieldname?lower_case}').setValue( rec.data.${f.fieldname?lower_case} );
						</#if>
						<#if _fieldType = "varchar" || _fieldType = "blob sub_type 1" || _fieldType = "char" || _fieldType = "bpchar" >
							<#if _fieldLen = "1">
					//space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().findField('c${f.fieldname?lower_case}').setValue( rec.data.${f.fieldname?lower_case} );
							</#if>
						</#if>
					</#list>
	            	
	            	// Habilitar Linha para Posicionar Grid de Registro Filho
	            	// _storeTABELAFILHO.baseParams.campoid = rec.get('campoid');
	                //_storeTABELAFILHO.load({params:{start:0, limit:10, campoid:rec.get('campoid')}});
				}
			}
		}),
		bbar: new Ext.PagingToolbar({
			pageSize: 10,
			store: store${table.tablename?upper_case},
			displayInfo: true,
			displayMsg: 'Reg {0} a {1} de {2}',
			emptyMsg: 'Nenhum registro encontrado'
        })        
	});
    
    store${table.tablename?upper_case}.on('load', function() {
		    
		if (space${table.tablename?lower_case?cap_first}.state == 'new') {
			space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getSelectionModel().selectLastRow();			
		} else {
			var selected = space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getSelectionModel().getSelected(); 
			if (selected===undefined) {
				if ( store${table.tablename?upper_case}.getTotalCount() > 0 ) {
					space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getSelectionModel().selectFirstRow();
					space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getView().focusRow(0) ;
				}
			} else {
				space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}.getSelectionModel().selectRecords( [selected] );
			}
		}
		space${table.tablename?lower_case?cap_first}.onState('browse');
	});   
	
	<#list table.fields as f>
		<#if f.isrelation?string = "true">
	store${f.relations.pktable_name?upper_case}.load();
		</#if>
	</#list>
	store${table.tablename?upper_case}.load();           
            
                

	space${table.tablename?lower_case?cap_first}.win${table.tablename?upper_case} = new Ext.Window({
		id:'${table.tablename?lower_case}-window', el:'${table.tablename?lower_case}-window',
		title:'${table.tablename?lower_case?cap_first}', 
		width:900,
		height:500, 
		resizable: false,
		closable: false,
		closeAction:'hide',
		modal: true,
		plain: false, 
		footer: true,
		defaultButton: space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case},
		items: [space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case},space${table.tablename?lower_case?cap_first}.grid${table.tablename?upper_case}], 
		tbar : new Ext.Toolbar({}) 
	});




    
	space${table.tablename?lower_case?cap_first}.win${table.tablename?upper_case}.on('show', function() {
	
		spaceMenuForm.showMenu(
			{
				idmenu     : sessvars.idmenu,
				window 	   : space${table.tablename?lower_case?cap_first}.win${table.tablename?upper_case},
				closeEvent : space${table.tablename?lower_case?cap_first}.onCloseClick
			}
		);	
		
		spaceMenuForm.loadEvent(
			{
				idmenu     : sessvars.idmenu,
				window 	   : space${table.tablename?lower_case?cap_first}.win${table.tablename?upper_case},
				callback   : 'space${table.tablename?lower_case?cap_first}.chkButton'
			}
		);		

		space${table.tablename?lower_case?cap_first}.onState('browse');
    });
    
    
	space${table.tablename?lower_case?cap_first}.win${table.tablename?upper_case}.show();

});    
    

    
