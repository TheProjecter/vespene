/*
	sintaxe:

		spaceSearchUnidade.showWindow( 
			{	enableSelect : true ,  // habilita o botao select
				form : spaceVendaunidade.formVENDAUNIDADE,  // form pai
				fields : [ ['idunidade','idunidade'],['descricaonumero','numero'],['descricaoandar','andar'] ],  // campos do form pai
				updatefields: ['idtorre','descricaotorre','idempreendimento','descricaoempreendimento'],  // campos do form pai que sao atualizados
				fieldnode : 'idunidade'  // chave 
			} 
		);

*/



Ext.ns('spaceSearch${table.tablename?lower_case?cap_first}');

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

//var winSearch${table.tablename?lower_case?cap_first};	
Ext.onReady(function(){

    spaceSearch${table.tablename?lower_case?cap_first}.config= null;
	spaceSearch${table.tablename?lower_case?cap_first}.windowHeight = 600;
	spaceSearch${table.tablename?lower_case?cap_first}.windowWidth = 600;	
	spaceSearch${table.tablename?lower_case?cap_first}.formHeight = 300;
	
	
	spaceSearch${table.tablename?lower_case?cap_first}.onSelectClick = function(item) {		
		
		if ( ( store${table.tablename?upper_case}.getTotalCount() > 0 ) && ( spaceSearch${table.tablename?lower_case?cap_first}.grid${table.tablename?lower_case?cap_first}.getSelectionModel().getSelected()!==undefined) ) {
			spaceSearch${table.tablename?lower_case?cap_first}.gridSelection = spaceSearch${table.tablename?lower_case?cap_first}.grid${table.tablename?lower_case?cap_first}.getSelectionModel().selections.items[0].data;
			
			for (i=0;i<spaceSearch${table.tablename?lower_case?cap_first}.config.fields.length;i++) {
				spaceSearch${table.tablename?lower_case?cap_first}.config.form.getForm().findField( spaceSearch${table.tablename?lower_case?cap_first}.config.fields[i][0] ).setValue( eval('spaceSearch${table.tablename?lower_case?cap_first}.gridSelection'+'.'+spaceSearch${table.tablename?lower_case?cap_first}.config.fields[i][1]) );				
				
			}


			for (var i in spaceSearch${table.tablename?lower_case?cap_first}.gridSelection) {
				if (i==spaceSearch${table.tablename?lower_case?cap_first}.config.fieldnode) {
					spaceSearch${table.tablename?lower_case?cap_first}.query = store${table.tablename?upper_case}.query(spaceSearch${table.tablename?lower_case?cap_first}.config.fieldnode, spaceSearch${table.tablename?lower_case?cap_first}.gridSelection[i]).items[0].data;
					if (spaceSearch${table.tablename?lower_case?cap_first}.config.updatefields!=undefined) {
						for (k=0;k< spaceSearch${table.tablename?lower_case?cap_first}.config.updatefields.length;k++) {					
							for (var j in spaceSearch${table.tablename?lower_case?cap_first}.query) {
								if (spaceSearch${table.tablename?lower_case?cap_first}.config.updatefields[k]==j) {
									spaceSearch${table.tablename?lower_case?cap_first}.config.form.getForm().findField( spaceSearch${table.tablename?lower_case?cap_first}.config.updatefields[k] ).setValue( spaceSearch${table.tablename?lower_case?cap_first}.query[j] );
								}
							}
						}
					}								
					break;
				}
			}	

			spaceSearch${table.tablename?lower_case?cap_first}.onCloseClick();
			
		} else {
			Ext.MessageBox.show({
	           msg: 'Escolha um item da tabela !',
	           buttons: Ext.MessageBox.OK,
	           icon: Ext.MessageBox.INFO
	       	});				
			
		}
		
	};
	
	spaceSearch${table.tablename?lower_case?cap_first}.onSearchClick = function(item) {		
		if ( spaceFieldsControl.isOneChecked( {form : spaceSearch${table.tablename?lower_case?cap_first}.frm} ) ) {				
			spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().submit({
					url:'${table.tablename?lower_case}!validate.action', 
					method:'POST',
					waitMsg:'Enviando...',
								
					success:function(form, action) {
						var paramValues = spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().getValues();
									
						//store${table.tablename?upper_case}.baseParams.isPager = true;

						store${table.tablename?upper_case}.baseParams = [];
						for (var i in paramValues) {									
							store${table.tablename?upper_case}.baseParams[i] = paramValues[i];
						}
									
						paramValues.start = 0;
						paramValues.limit = 10;
									
						store${table.tablename?upper_case}.load({params:paramValues});									
					},
					failure : function(form, action) { 
						//var result = Ext.decode(action.response.responseText);
							
						if ( action.result.errors===null ) {
							Ext.MessageBox.show({
								msg: 'O Servidor não responde, tente novamente !',
								buttons: Ext.MessageBox.OK,
								icon: Ext.MessageBox.ERROR
							});									
						} else {
							Ext.MessageBox.show({
								msg: 'Verifique os campos do formulário !',
								buttons: Ext.MessageBox.OK,
								icon: Ext.MessageBox.INFO
							});									
						}
					}
									
			});
		} else {
			Ext.MessageBox.show({
	           msg: 'Informe um critério de busca !',
	           buttons: Ext.MessageBox.OK,
	           icon: Ext.MessageBox.INFO
	       	});			
		}		
	};


	spaceSearch${table.tablename?lower_case?cap_first}.onCloseClick = function(item) {		
		spaceSearch${table.tablename?lower_case?cap_first}.winSearch${table.tablename?upper_case}.hide();
        
		if ( spaceSearch${table.tablename?lower_case?cap_first}.config.callback!=undefined ) {
			eval( spaceSearch${table.tablename?lower_case?cap_first}.config.callback );
		}	        
    };	
    
    spaceSearch${table.tablename?lower_case?cap_first}.frm = new Ext.FormPanel({
	    id: 'search-${table.tablename?lower_case}-form',
        frame: true,
        title:'',
        labelAlign: 'left',
        labelWidth: 85,
        //autoScroll: true,
        //width:355,
        autoWidth:true,
        height: spaceSearch${table.tablename?lower_case?cap_first}.formHeight,
        //waitMsgTarget: true,
		reader: store${table.tablename?upper_case}.reader,
		errorReader: new Ext.form.XmlErrorReader(),
		items: [
		
			new Ext.TabPanel({
			    activeTab: 0,
			    layoutOnTabChange: true,
			    items: [{
			    	id: 'fieldset${table.tablename?lower_case?cap_first}',
			        title: 'Dados : ${table.tablename?lower_case?cap_first}',
			        autoScroll: true, 
			        frame: true,
			        //autoHeight: true,
			        height: spaceSearch${table.tablename?lower_case?cap_first}.formHeight-45,
			        labelAlign: 'top',
					items: [    			
	    			{ // inicio linha 1
				    	layout:'table',
	    				defaults: {
	    					bodyStyle:'padding:0px 5px 5px'
	    				},
	    				layoutConfig: {
		        			columns: 1
	    				},
	    				items: [
					
		<#list table.fields as f>
			<#assign _fieldType = "${f.fieldtype}" >
			
				<#if _fieldType = "varchar" || _fieldType = "blob sub_type 1" || _fieldType = "char" >
								
			    	{ // abre linha 
    					layout:'table',
	    				defaults: {
	    					bodyStyle:'padding:0px 5px 5px'
	    				},
	    				layoutConfig: {
		        			columns: 2
	    				},
	    				items: [{
								layout:'form',
								items: [
									new Ext.ux.form.XCheckbox({ labelSeparator: '', boxLabel: '${f.fieldname?lower_case?cap_first}', checked: false, /*id: 'ncheck${table.tablename?upper_case}${f.fieldname?lower_case}',*/ name: 'ncheck${table.tablename?upper_case}${f.fieldname?lower_case}',
									listeners: {check: function() { 
											if ( spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('ncheck${table.tablename?upper_case}${f.fieldname?lower_case}').getValue() ) {									
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}').enable();
											} else {
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}').disable();
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}').setValue('');
											}
										}
									}
							})],
								width:130
				    	},{
				        items: [{ 
							layout:'form',
								items: [{
									xtype:'textfield', /*id: 'p${f.fieldname}',*/ fieldLabel: 'Contendo', name: 'p${f.fieldname}',
									autoCreate: { tag: 'input', maxlength: '${f.fieldlen}' }, value:'', width:${f.fieldlen*5},
									listeners: { 'blur': function() { 
										this.setValue(Ext.util.Format.uppercase(this.getValue()));}}									
									}]
								}]
					    }]
					    
			    	}  // fecha linha
			    <#elseif _fieldType = "date" >
			    	{ // abre linha 
    					layout:'table',
	    				defaults: {
	    					bodyStyle:'padding:0px 5px 5px'
	    				},
	    				layoutConfig: {
		        			columns: 3
	    				},
	    				items: [{
								layout:'form',
								items: [
									new Ext.ux.form.XCheckbox({ labelSeparator: '', boxLabel: '${f.fieldname?lower_case?cap_first}', labelAlign: 'left', /*id: 'ncheck${table.tablename?upper_case}${f.fieldname?lower_case}',*/ name: 'ncheck${table.tablename?upper_case}${f.fieldname?lower_case}', checked: false,
									listeners: {check: function() { 
											if ( spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('ncheck${table.tablename?upper_case}${f.fieldname?lower_case}').getValue() ) {
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}ini').enable();
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}fim').enable();
											} else {
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}ini').disable();
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}fim').disable();
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}ini').setValue('');
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}fim').setValue('');
												
											}
										}
									}
							})],
								width:130
				    	},{
				        items: [{ 
							layout:'form',
								items: [ new Ext.form.DateField({ fieldLabel: 'de', /*id: 'p${f.fieldname}ini',*/ name: 'p${f.fieldname}ini', format: 'd/m/Y', altFormats:"d/m/Y|d-m-y|d-m-Y|d/m|d-m|dm|dmy|dmY|d|Y-m-d"}) ]
								}]
					    },{
					    	items: [{ 
								layout:'form',
									items: [ new Ext.form.DateField({ fieldLabel: 'até', /*id: 'p${f.fieldname}fim',*/ name: 'p${f.fieldname}fim', format: 'd/m/Y', altFormats:"d/m/Y|d-m-y|d-m-Y|d/m|d-m|dm|dmy|dmY|d|Y-m-d"}) ]
									
									}]
					    }]
			    	}  // fecha linha		
			    <#elseif _fieldType = "integer" || _fieldType = "int4">                                
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
						        		columns: 3
					    			},
					    			items: [{
										layout:'form',
										items: [
											new Ext.ux.form.XCheckbox({ labelSeparator: '', boxLabel: '${f.fieldname?lower_case?cap_first}', labelAlign: 'left', /*id: 'ncheck${table.tablename?upper_case}${f.fieldname?lower_case}',*/ name: 'ncheck${table.tablename?upper_case}${f.fieldname?lower_case}', checked: false,
											listeners: {check: function() { 
													if ( spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('ncheck${table.tablename?upper_case}${f.fieldname?lower_case}').getValue() ) {
														spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('${f.fieldname}').enable();
														spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('${f.relations.referencialcolumn_name?lower_case}').enable();
													} else {
														spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('${f.fieldname}').disable();
														spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('${f.relations.referencialcolumn_name?lower_case}').disable();
														spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('${f.fieldname}').setValue('');
														spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('${f.relations.referencialcolumn_name?lower_case}').setValue('');
														
													}
												}
											}
									})],
										width:130
						    	},{
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
																				form		: spaceSearch${table.tablename?lower_case?cap_first}.frm,
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
																		form : spaceSearch${table.tablename?lower_case?cap_first}.frm,
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
						} // fecha combo<#else>  
			    	{ // abre linha 
    					layout:'table',
	    				defaults: {
	    					bodyStyle:'padding:0px 5px 5px'
	    				},
	    				layoutConfig: {
		        			columns: 3
	    				},
	    				items: [{
								layout:'form',
								items: [
									new Ext.ux.form.XCheckbox({ labelSeparator: '', boxLabel: '${f.fieldname?lower_case?cap_first}', labelAlign: 'left', /*id: 'ncheck${table.tablename?upper_case}${f.fieldname?lower_case}',*/ name: 'ncheck${table.tablename?upper_case}${f.fieldname?lower_case}', checked: false,
									listeners: {check: function() { 
											if ( spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('ncheck${table.tablename?upper_case}${f.fieldname?lower_case}').getValue() ) {
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}ini').enable();
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}fim').enable();
											} else {
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}ini').disable();
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}fim').disable();
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}ini').setValue('');
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}fim').setValue('');
												
											}
										}
									}
							})],
								width:130
				    	},{
				        items: [{ 
							layout:'form',
								items: [{ xtype:'textfield', fieldLabel: 'de', /*id: 'p${f.fieldname}ini',*/ name: 'p${f.fieldname}ini', value:'', width:${f.fieldlen*5}, plugins: [new Ext.ux.InputTextMask('999999999', false)] }]
								}]
					    },{
					    	items: [{ 
								layout:'form',
								items: [{ xtype:'textfield', fieldLabel: 'até', /*id: 'p${f.fieldname}fim',*/ name: 'p${f.fieldname}fim', value:'', width:${f.fieldlen*5}, plugins: [new Ext.ux.InputTextMask('999999999', false)] }]
								}]
					    }]
			    	}  // fecha linha
			    	</#if>
			    	
			    	
			    <#else>
			    	{ // abre linha 
    					layout:'table',
	    				defaults: {
	    					bodyStyle:'padding:0px 5px 5px'
	    				},
	    				layoutConfig: {
		        			columns: 3
	    				},
	    				items: [{
								layout:'form',
								items: [
									new Ext.ux.form.XCheckbox({ labelSeparator: '', boxLabel: '${f.fieldname?lower_case?cap_first}', labelAlign: 'left', /*id: 'ncheck${table.tablename?upper_case}${f.fieldname?lower_case}',*/ name: 'ncheck${table.tablename?upper_case}${f.fieldname?lower_case}', checked: false,
									listeners: {check: function() { 
											if ( spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('ncheck${table.tablename?upper_case}${f.fieldname?lower_case}').getValue() ) {
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}ini').enable();
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}fim').enable();
											} else {
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}ini').disable();
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}fim').disable();
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}ini').setValue('');
												spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('p${f.fieldname}fim').setValue('');
												
											}
										}
									}
							})],
								width:130
				    	},{
				        items: [{ 
							layout:'form',
								items: [{ xtype:'textfield', fieldLabel: 'de', /*id: 'p${f.fieldname}ini',*/ name: 'p${f.fieldname}ini', style : {textTransform: "uppercase"}, value:'', width:${f.fieldlen*5} }]
								}]
					    },{
					    	items: [{ 
								layout:'form',
									items: [{ xtype:'textfield', fieldLabel: 'até', width:${f.fieldlen*5}, /*id: 'p${f.fieldname}fim',*/ name: 'p${f.fieldname}fim', style : {textTransform: "uppercase"}, value:'' }]
									}]
					    }]
			    	}  // fecha linha		

			</#if> 
			<#if f_has_next>,</#if>   
		</#list>

			    ] // fim da colecao
			}]
			}]

            })
        ]
    });
    

// ========================== grid
	spaceSearch${table.tablename?lower_case?cap_first}.grid${table.tablename?lower_case?cap_first} = new Ext.grid.GridPanel({
    	title:'',
		height: spaceSearch${table.tablename?lower_case?cap_first}.windowHeight-spaceSearch${table.tablename?lower_case?cap_first}.formHeight-56, //280,
    	frame: true,
    	stripeRows: true,
        store: store${table.tablename?upper_case},
        viewConfig: {
			      //forceFit: true, // autoExpandColumn: 'descricao' 
			      autoFill: true 
		}, 
        columns: [
        	<#list table.fields as f>
        	<#assign _fieldType = "${f.fieldtype}" >
        	<#if f.ispk?string = "true">
    	    {header: "Id", width: 45, dataIndex: '${f.fieldname?lower_case}', sortable: true}<#if f_has_next>,</#if>
			<#else>
				<#if _fieldType = "date">
	    	    {header: "${f.fieldname?lower_case?cap_first}", width: ${f.fieldlen*5}, dataIndex: '${f.fieldname?lower_case}', sortable: true, renderer: formatDate}<#if f_has_next>,</#if>
    	    	</#if>
    	    	{header: "${f.fieldname?lower_case?cap_first}", width: ${f.fieldlen*5}, dataIndex: '${f.fieldname?lower_case}', sortable: true}<#if f_has_next>,</#if>
    	    </#if>
            </#list>
        ],
		sm: new Ext.grid.RowSelectionModel({
			singleSelect: true,
	        listeners: {
	        	rowselect: function(sm, row, rec) {
	        		//space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().loadRecord(rec);
	        		
	            	//selectedRow = row;
	            	/*
	            	<#list table.fields as f>
	            		<#assign _fieldType = "${f.fieldtype}" >
	            		<#assign _fieldLen = "${f.fieldlen}" >
						<#if f.isrelation?string = "true">
		            space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().findField('t${f.fieldname?lower_case}').setValue( rec.data.${f.fieldname?lower_case} );
						</#if>
						<#if _fieldType = "numeric">
		            space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().findField('${f.fieldname?lower_case}').setValue( rec.data.${f.fieldname?lower_case} );
						</#if>
						<#if _fieldType = "varchar" || _fieldType = "blob sub_type 1" || _fieldType = "char" || _fieldType = "bpchar" >
							<#if _fieldLen = "1">
					space${table.tablename?lower_case?cap_first}.form${table.tablename?upper_case}.getForm().findField('c${f.fieldname?lower_case}').setValue( rec.data.${f.fieldname?lower_case} );
							</#if>
						</#if>
					</#list>
	            	*/
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

	 
// ========================== menu    
	spaceSearch${table.tablename?lower_case?cap_first}.menu = new Ext.menu.Menu({
	    id: 'menu',
	    items: [
	        new Ext.menu.Item(
	        	{ id : 'mnu_search${table.tablename?lower_case?cap_first}', text: 'Pesquisar', iconCls:'search', handler: spaceSearch${table.tablename?lower_case?cap_first}.onSearchClick}
	        ), new Ext.menu.Item(
	        	{ id : 'mnu_Select${table.tablename?lower_case?cap_first}', text: 'Selecionar', iconCls:'select', handler: spaceSearch${table.tablename?lower_case?cap_first}.onSelectClick}
	        ),'-', new Ext.menu.Item(
	        	{ id : 'mnu_close${table.tablename?lower_case?cap_first}', text: 'Fechar', iconCls:'close', handler: spaceSearch${table.tablename?lower_case?cap_first}.onCloseClick }
	        )
	    ]
	});
	
// ========================== toolbar    


		spaceSearch${table.tablename?lower_case?cap_first}.tb = new Ext.Toolbar({
	        items : [{
	             id : 'tbar_opcSearch${table.tablename?lower_case?cap_first}',
	             text:'Opções',
	             iconCls:'',
	             menu: spaceSearch${table.tablename?lower_case?cap_first}.menu
			 }, '-', {
	         	 id : 'tbar_search${table.tablename?lower_case?cap_first}',
	             text:'',
	             tooltip:'Pesquisar',
	             iconCls:'search',
	             handler : spaceSearch${table.tablename?lower_case?cap_first}.onSearchClick
			 }, '-', {
	         	 id : 'tbar_Select${table.tablename?lower_case?cap_first}',
	             text:'',
	             tooltip:'Selecionar',
	             iconCls:'select',
	             handler : spaceSearch${table.tablename?lower_case?cap_first}.onSelectClick
	         },'-',{
	             id : 'tbar_close${table.tablename?lower_case?cap_first}',
	             text:'',
	             tooltip:'Fechar',
	             iconCls:'close',
	             handler : spaceSearch${table.tablename?lower_case?cap_first}.onCloseClick
	         }]		 
		});			
	
	
	
	
// ========================== window    
//	if(!spaceSearch${table.tablename?lower_case?cap_first}.winSearch${table.tablename?upper_case}){
		spaceSearch${table.tablename?lower_case?cap_first}.winSearch${table.tablename?upper_case} = new Ext.Window({
				id:'${table.tablename?lower_case}-search',
				el:'${table.tablename?lower_case}-search',
				title:'Pesquisa ${table.tablename?lower_case?cap_first}',
				resizable: false, 
				closable: false,
				modal : true,
				//layout:'border',
				//layout:'card',
				//layout:'fit',
				height:spaceSearch${table.tablename?lower_case?cap_first}.windowHeight,
				width:	spaceSearch${table.tablename?lower_case?cap_first}.windowWidth,
				closeAction:'hide',
				plain: false,
				tbar : spaceSearch${table.tablename?lower_case?cap_first}.tb,
				items: [spaceSearch${table.tablename?lower_case?cap_first}.frm, spaceSearch${table.tablename?lower_case?cap_first}.grid${table.tablename?lower_case?cap_first} ]
		});
		
//	}


// ========================== showWindow
	spaceSearch${table.tablename?lower_case?cap_first}.showWindow = function( config ) {		
		
		spaceSearch${table.tablename?lower_case?cap_first}.config = config;
		 
		spaceSearch${table.tablename?lower_case?cap_first}.winSearch${table.tablename?upper_case}.show();
		
//		if ( (spaceSearch${table.tablename?lower_case?cap_first}.config!==null) && (spaceSearch${table.tablename?lower_case?cap_first}.config.enableSelect) ) {
		if ( spaceSearch${table.tablename?lower_case?cap_first}.config!==null && spaceSearch${table.tablename?lower_case?cap_first}.config.enableSelect ) {		
			spaceSearch${table.tablename?lower_case?cap_first}.menu.items.get('mnu_Select${table.tablename?lower_case?cap_first}').setVisible(true); //enable();			
			spaceSearch${table.tablename?lower_case?cap_first}.tb.items.get('tbar_Select${table.tablename?lower_case?cap_first}').setVisible(true); //enable();
			spaceSearch${table.tablename?lower_case?cap_first}.tb.items.items[3].setVisible(true);
		} else {
			spaceSearch${table.tablename?lower_case?cap_first}.menu.items.get('mnu_Select${table.tablename?lower_case?cap_first}').setVisible(false); //disable();			
			spaceSearch${table.tablename?lower_case?cap_first}.tb.items.get('tbar_Select${table.tablename?lower_case?cap_first}').setVisible(false); //disable();
			spaceSearch${table.tablename?lower_case?cap_first}.tb.items.items[3].setVisible(false);			
		}
        
    };	



// ========================== keymap
	spaceSearch${table.tablename?lower_case?cap_first}.keys = new Ext.KeyMap("${table.tablename?lower_case}-search", [
		{
	   		key: [13], // enter
	   		scope: spaceSearch${table.tablename?lower_case?cap_first}.winSearch${table.tablename?upper_case},
	   		fn: function() { spaceSearch${table.tablename?lower_case?cap_first}.onSearchClick(); }
		}, {
			key: [83],  // S
			scope: spaceSearch${table.tablename?lower_case?cap_first}.winSearch${table.tablename?upper_case},
			alt: true,
			ctrl: true,
			fn: function() { spaceSearch${table.tablename?lower_case?cap_first}.onSearchClick(); }
		}, {
			key: [27],  // N
			scope: spaceSearch${table.tablename?lower_case?cap_first}.winSearch${table.tablename?upper_case},
			ctrl: true,
			alt: true,
			fn: function() { spaceSearch${table.tablename?lower_case?cap_first}.onCloseClick(); }
		}
		
	]);


	spaceSearch${table.tablename?lower_case?cap_first}.winSearch${table.tablename?upper_case}.on('show', function() {
		spaceFieldsControl.setCheckValue( {form : spaceSearch${table.tablename?lower_case?cap_first}.frm, value : false} )	
		/*
    <#list table.fields as f>
		spaceSearch${table.tablename?lower_case?cap_first}.frm.getForm().findField('ncheck${table.tablename?upper_case}${f.fieldname?lower_case}').setValue(false);    
    </#list>	
		*/
    });	
    


});