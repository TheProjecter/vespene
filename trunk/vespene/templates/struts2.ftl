package br.com.powermind.Actions.CRUD;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.web.context.support.WebApplicationContextUtils;
import com.opensymphony.webwork.ServletActionContext;


import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.webwork.interceptor.ParameterAware;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Preparable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import br.com.powermind.xstream.CurrencyBR;
import br.com.powermind.ORM.Ext.${pojo}Ext;
<#list table.fields as f>
			<#if f.isrelation?string = "true">
import br.com.powermind.ORM.${f.relations.pktable_name?lower_case?cap_first};
			</#if>
</#list>
import br.com.powermind.ORM.${pojo};
import br.com.powermind.Util.Error;
import br.com.powermind.Util.Pager;
import br.com.powermind.Util.SendXMLToBrowser;
import br.com.powermind.Util.Validator;
import br.com.powermind.response.PrepareError;
import br.com.powermind.response.PrepareResponse;
import br.com.powermind.response.PrepareXStream;
import br.com.powermind.service.${serviceInterface};
import br.com.powermind.xstream.HibernateMapper;
import br.com.powermind.xstream.HibernateProxyConverter;
import br.com.powermind.xstream.XStreamAttribute;
import br.com.powermind.Search.${pojo}Search;
import br.com.powermind.Search.query.${pojo}HqlQuery;


// ActionSupport
// WebWorkResultSupport
// ModelDriven

public class ${actionClass} extends WebWorkResultSupport implements Preparable, ParameterAware {	
	private static final long serialVersionUID = 1L;
	private Map parameters;
	private StringBuffer xml;
	private WebApplicationContext wac = null;
	private ${serviceInterface} ${serviceDAO} = null;
	private String tipoform = "";

	private int currentPage = 0;
	private int maxResults  = 10;
	private Boolean isPager = false;
	
	public void prepare() throws Exception {
		System.out.println("prepare");
		wac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
		//wac = (WebApplicationContext)ActionContext.getContext().getApplication().get(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		${serviceDAO} = (${serviceInterface}) wac.getBean("${service}");
	}
 
	
	public String execute() throws Exception {
		System.out.println("execute");
		return list();
	}


	public String validate() {
		
		
		${pojo}Search ${table.tablename?lower_case}Search = new ${pojo}Search();
			 
			
		// inicia a validacao por type conversion
		Validator val = new Validator();
		val.setO(${table.tablename?lower_case}Search);
		val.setParameters(parameters);
	<#list table.fields as f>
		<#switch f.ispk?string>
		  <#case "true">
		val.setPk( "${f.fieldname}" );
			<#break>
		</#switch>  			
	</#list>
			
		// cria lista dos campos a serem ignorados
		//List<String> ignoreFields = new ArrayList<String>();
	<#list table.fields as f>
		<#switch f.ispk?string>
			<#case "true">
		//ignoreFields.add( "${f.fieldname}" );
			<#break>
		</#switch>  			
	</#list>			
			
		ArrayList<PrepareError> listPrepareError = val.ExecuteTypeConversion();  	
		
		PrepareResponse prepareResponse = new PrepareResponse();
		prepareResponse.setSuccess( listPrepareError.isEmpty() );
		prepareResponse.setErrors( listPrepareError );

		
		PrepareXStream prepareXStream = new PrepareXStream();
		prepareXStream.setPrepareResponse(prepareResponse);
		prepareXStream.setDataTag("dados${pojo?lower_case?cap_first}");
		prepareXStream.setORMClass(${pojo?lower_case?cap_first}Ext.class);
		
		String response = prepareXStream.Execute();
		SendXMLToBrowser s = new SendXMLToBrowser();
		
		xml = new StringBuffer()
		.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>")
		.append( response );
		
		
		s.setContent( xml );
		s.Send();		
		
		return Action.SUCCESS;
	}



	public String list() {
		int recordCount = 0;
		int firstResult	= 0;
		int start    	= 0;
		
		String hqlquery = null;
		String hqlcount = null;
		Map hqlparam    = null;
		
		ArrayList<Error> listError = null;
		//XStream xstreamError = null;
		Map session = (Map) ActionContext.getContext().get("session");


		try {
			String[] paramValue = (String[]) parameters.get("start"); 
			start = Integer.parseInt(paramValue[0].toString());
			paramValue = (String[]) parameters.get("limit"); 				
			maxResults  = Integer.parseInt(paramValue[0].toString());
		} catch (Exception e) {
		}

		List<${pojo}> list${pojo} = new ArrayList<${pojo}>();
		
		System.out.println("list");
		
		try {  
		
			${pojo}Search ${table.tablename?lower_case}Search = new ${pojo}Search();
			 
			
			// inicia a validacao por type conversion
			Validator val = new Validator();
			val.setO(${table.tablename?lower_case}Search);
			val.setParameters(parameters);
		<#list table.fields as f>
		<#switch f.ispk?string>
		  <#case "true">
			val.setPk( "${f.fieldname}" );
			<#break>
		</#switch>  			
		</#list>
			
			// cria lista dos campos a serem ignorados
			//List<String> ignoreFields = new ArrayList<String>();
		<#list table.fields as f>
		<#switch f.ispk?string>
		  <#case "true">
			//ignoreFields.add( "${f.fieldname}" );
			<#break>
		</#switch>  			
		</#list>			
			
			ArrayList<PrepareError> listPrepareError = val.ExecuteTypeConversion();  //listError = val.Execute();

			/*
			xstreamError = new XStream();
			xstreamError.alias("errossearch", List.class);
			xstreamError.alias("dadoserrosearch", Error.class);
			
			XStreamAttribute xa  = new XStreamAttribute();
			xa.setXstream(xstreamError);
			xa.toAttributeFor();	
			*/			
			
			${pojo}HqlQuery ${pojo?lower_case}HqlQuery = new ${pojo}HqlQuery( ${pojo?lower_case}Search, true );
			
			if ( isPager ) {
				hqlcount = (String) session.get("${pojo?upper_case}COUNT");
				hqlparam = (Map) session.get("${pojo?upper_case}PARAM");
			} else {
				hqlcount = ${pojo?lower_case}HqlQuery.getHql(); 
				hqlparam = ${pojo?lower_case}HqlQuery.getParameters();
				session.put("${pojo?upper_case}COUNT", ${pojo?lower_case}HqlQuery.getHql()  );
			}
			recordCount  = s${pojo}DAO.find${pojo}ByHqlRecordCount(hqlcount, hqlparam);
			
			Pager pager = new Pager();
			pager.setRecordCount(recordCount);
			pager.setCurrentPage(currentPage);
			pager.setMaxResults(maxResults);
			pager.execute();
			
			/*
			XStream xsPager = new XStream();
			xsPager.alias("pager", Pager.class);
			xa  = new XStreamAttribute();
			xa.setXstream(xsPager);
			xa.toAttributeFor();
			*/

			${pojo?lower_case}HqlQuery = new ${pojo}HqlQuery( ${pojo?lower_case}Search, false );
			
			if ( isPager ) {
		    	hqlquery = (String) session.get("${pojo?upper_case}HQL");
			} else {
				hqlquery = ${pojo?lower_case}HqlQuery.getHql();
			}
			
		    firstResult = pager.getFirstResult();
			list${pojo} = s${pojo}DAO.find${pojo}sByHqlQuery(hqlquery, hqlparam, start, maxResults);

			/*
			XStream xstream = new XStream()		{
				protected MapperWrapper wrapMapper(MapperWrapper next) {
					return new HibernateMapper(next);
				}
			};

			xstream.registerConverter(new HibernateProxyConverter(xstream.getMapper(),new PureJavaReflectionProvider()),XStream.PRIORITY_VERY_HIGH);
			xStream.registerConverter( new CurrencyBR() );
			xstream.alias("dados${pojo}", ${pojo}Ext.class);			
			*/
			
			if ( !isPager ) {
				session.put("${pojo?upper_case}HQL", ${pojo?lower_case}HqlQuery.getHql() );
				session.put("${pojo?upper_case}PARAM", ${pojo?lower_case}HqlQuery.getParameters()  );
			}
			
			PrepareResponse prepareResponse = new PrepareResponse();
			prepareResponse.setSuccess( true );
			prepareResponse.setPager( pager );
			prepareResponse.setErrors( listPrepareError );
			prepareResponse.setListPojo( getPlainList(list${pojo?lower_case?cap_first}) );
			
			PrepareXStream prepareXStream = new PrepareXStream();
			prepareXStream.setPrepareResponse(prepareResponse);
			prepareXStream.setDataTag("dados${pojo?lower_case?cap_first}");
			prepareXStream.setORMClass(${pojo?lower_case?cap_first}Ext.class);
			
			String response = prepareXStream.Execute();
			
			SendXMLToBrowser s = new SendXMLToBrowser();

			
			xml = new StringBuffer()
			.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>")
			.append( response );
			
			
			s.setContent( xml );
			s.Send();
			
		} catch (Exception e) { 
			System.out.println( e );
			e.printStackTrace();
		}
		
		return "list";
	}
	
	
	

	public String add() {
		System.out.println("add");
		return Action.INPUT;
	}

	public String edit() {
		System.out.println("edit");
		return Action.INPUT;
	}

	public String save() {
		System.out.println("save");
		
		${pojo} ${table.tablename?lower_case} = new ${pojo}();
		<#list table.fields as f>
			<#if f.isrelation?string = "true">
		${f.relations.pktable_name?lower_case?cap_first} ${f.relations.pktable_name?lower_case} = new ${f.relations.pktable_name?lower_case?cap_first}();
			</#if>
		</#list>
		
		
		// inicia a validacao por type conversion
		Validator val = new Validator();
		val.setO(${table.tablename?lower_case});
		val.setParameters(parameters);
	<#list table.fields as f>
	<#switch f.ispk?string>
	  <#case "true">
		val.setPk("${f.fieldname}");
	     <#break>
	</#switch>  			
	</#list>			
		
		// cria lista dos campos a serem ignorados
		//List<String> ignoreFields = new ArrayList<String>();
		//ignoreFields.add("idtipocontabancaria");
		//val.setIgnoreFields(ignoreFields);

		ArrayList<PrepareError> listPrepareError = val.ExecuteTypeConversion();
		PrepareError err = new PrepareError();

		//ArrayList<Error> listError = val.Execute();
		// colocar aqui validacoes individuais
		//Map map = new HashMap();
		

		<#list table.fields as f>
			<#if f.isrelation?string = "true">


		try {
		  String[] paramValue = (String[]) parameters.get("${f.relations.fkcolumn_name?lower_case}"); // pega o valor que esta no map
		  ${f.relations.pktable_name?lower_case}.set${f.relations.pkcolumn_name?lower_case?cap_first}( Integer.parseInt(paramValue[0].toString()) );
		  ${table.tablename?lower_case}.set${f.relations.pktable_name?lower_case?cap_first}(${f.relations.pktable_name?lower_case});
		} catch (Exception e) {
		  err = new PrepareError();
		  err.setId("${f.relations.fkcolumn_name?lower_case}");
		  err.setMsg("Selecione uma ${f.relations.pktable_name?lower_case?cap_first} válida.");
		  listPrepareError.add(err);
		}


			</#if>
		</#list>
		
		//XStream xstreamError = new XStream(new JettisonMappedXmlDriver());
		//xstreamError.alias("erros", List.class);
		//xstreamError.alias("dadosErro", Error.class);
		//new XStreamAttribute( err, xstreamError );
		
		//XStreamAttribute x = new XStreamAttribute();
		//x.setO(err);
		//x.setXstream(xstreamError);
		//xstreamError  = x.toAttribute();		
		
		
		try {
			if ( listPrepareError.isEmpty() ) {
				s${pojo}DAO.persist${pojo}(${table.tablename?lower_case});
			}
			
			//${table.tablename?lower_case} = s${pojo}DAO.find${pojo}ById(${table.tablename?lower_case}.getId${table.tablename?lower_case}());	
			List list${table.tablename?lower_case?cap_first} = s${pojo}DAO.find${table.tablename?lower_case?cap_first}sByExample(${table.tablename?lower_case});
				
			//List<${pojo}> list${pojo} = new ArrayList<${pojo}>();
			//list${pojo}.add(${table.tablename?lower_case});
				
			//String dados;
			
			${table.tablename?lower_case?cap_first}Search ${table.tablename?lower_case}Search = new ${table.tablename?lower_case?cap_first}Search();
	<#list relations as f>
		<#if f.master_key?string = "true">				
			${table.tablename?lower_case}Search.set${f.pkcolumn_name?lower_case?cap_first}( ${f.pktable_name?lower_case}.get${f.pkcolumn_name?lower_case?cap_first}()   );
		</#if>				
	</#list>				
			${table.tablename?lower_case?cap_first}HqlQuery ${table.tablename?lower_case}HqlQuery = new ${table.tablename?lower_case?cap_first}HqlQuery( ${table.tablename?lower_case}Search, true );
			String hqlcount = ${table.tablename?lower_case}HqlQuery.getHql(); 
			Map hqlparam = ${table.tablename?lower_case}HqlQuery.getParameters();
			
	
			int recordCount  = s${table.tablename?lower_case?cap_first}DAO.find${table.tablename?lower_case?cap_first}ByHqlRecordCount(hqlcount, hqlparam);
			
			Pager pager = new Pager();
			pager.setRecordCount(recordCount);
			
			PrepareResponse prepareResponse = new PrepareResponse();
			prepareResponse.setSuccess( listPrepareError.isEmpty() );
			prepareResponse.setPager( pager );
			prepareResponse.setErrors( listPrepareError );
			prepareResponse.setListPojo( getPlainList(list${table.tablename?lower_case?cap_first}) );
			
			PrepareXStream prepareXStream = new PrepareXStream();
			prepareXStream.setPrepareResponse(prepareResponse);
			prepareXStream.setDataTag("dados${pojo?lower_case?cap_first}");
			prepareXStream.setORMClass(${pojo?lower_case?cap_first}Ext.class);
			
			String response = prepareXStream.Execute();		
			
			xml = new StringBuffer()
			.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>")
			.append( response );
			
			System.out.println("****************" );
			System.out.println( response );
			System.out.println("****************" );	
			
			
			SendXMLToBrowser s = new SendXMLToBrowser();
			s.setContent(xml);
			s.Send();
			
			
		} catch (Exception e) { 
			System.out.println( e );
			e.printStackTrace();
		}

		
		return Action.SUCCESS;
	}



	public String delete() {
		System.out.println("delete");
		boolean success = true;
		
		${pojo} ${table.tablename?lower_case} = new ${pojo}();
		
		// inicia a validacao por type conversion
		Validator val = new Validator();
		val.setO(${table.tablename?lower_case});
		val.setParameters(parameters);
	<#list table.fields as f>
	<#switch f.ispk?string>
	  <#case "true">
		val.setPk("${f.fieldname}");
	     <#break>
	</#switch>  			
	</#list>			

		@SuppressWarnings("unused")
		ArrayList<PrepareError> listPrepareError = val.ExecuteTypeConversion();
		
		
		try {
			s${pojo}DAO.remove${pojo}( ${table.tablename?lower_case} );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			
			PrepareResponse prepareResponse = new PrepareResponse();
			prepareResponse.setSuccess( success );
			
			PrepareXStream prepareXStream = new PrepareXStream();
			prepareXStream.setPrepareResponse(prepareResponse);
			prepareXStream.setDataTag("dados${pojo?lower_case?cap_first}");
			prepareXStream.setORMClass(${pojo?lower_case?cap_first}Ext.class);
			
			String response = prepareXStream.Execute();			
			
			xml = new StringBuffer()
			.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>")
			.append( response );
			

			System.out.println("****************" );
			System.out.println( response );
			System.out.println("****************" );				
			
			SendXMLToBrowser s = new SendXMLToBrowser();
			s.setContent(xml);
			s.Send();
			
			
		} catch (Exception e) { 
			System.out.println( e );
			e.printStackTrace();
		}		
		
		return Action.SUCCESS;		
		
		
	}







	public Map getParameters() {
		return parameters;
	}	


	public void setParameters(Map arg0) {
		this.parameters = arg0;
	}

	

	public String getTipoform() {
		return tipoform;
	}


	public void setTipoform(String tipoform) {
		this.tipoform = tipoform;
	}


	@Override
	protected void doExecute(String arg0, ActionInvocation arg1) throws Exception {
	}
	
	
	
	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getMaxResults() {
		return maxResults;
	}


	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public Boolean getIsPager() {
		return isPager;
	}


	public void setIsPager(Boolean isPager) {
		this.isPager = isPager;
	}	

	public List getPlainList(List list${pojo}) {
		ArrayList<${pojo}Ext> list${pojo}Ext = new ArrayList<${pojo}Ext>();

		for(Iterator it = list${pojo}.iterator(); it.hasNext(); ) {
			${pojo} a = (${pojo}) it.next();
			${pojo}Ext b = new ${pojo}Ext();

		<#list table.fields as f>
			<#if f.isrelation?string = "true">
			b.set${f.fieldname?cap_first}( a.get${f.relations.pktable_name?lower_case?cap_first}().get${f.fieldname?cap_first}() );
			b.set${f.relations.referencialcolumn_name?lower_case?cap_first}( a.get${f.relations.pktable_name?lower_case?cap_first}().get${f.relations.lookupcolumn_name?lower_case?cap_first}() );			
			<#else>
			b.set${f.fieldname?cap_first}( a.get${f.fieldname?cap_first}() );
			</#if>
		</#list>    			
			
			list${pojo}Ext.add( b  );
		}
		
		return list${pojo}Ext;
	}


	
}
