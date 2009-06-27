package ${package};

import java.util.Collection;
import java.util.List;
import java.util.Map;



<#list SpringServices.entity as entity>
import ${entity.entityPackage};
</#list>

public interface ${classname} {    

<#list SpringServices.entity as entity>
	
	public ${entity.entityName} find${entity.entityName}ById(${entity.pkType} id) throws Exception;
	public List<${entity.entityName}> findAll${entity.entityName}() throws Exception;
	public List<${entity.entityName}> find${entity.entityName}ByExpressionQuery(final String expression, final Map<String, Object> parameters, final int firstResult, final int maxResults) throws Exception;
	public int find${entity.entityName}ByExpressionRecordCount(final String expression, final Map<String, Object> parameters) throws Exception;	
	public void persist${entity.entityName}(${entity.entityName} ${entity.entityName?lower_case}) throws Exception;
	public void remove${entity.entityName}(${entity.entityName} ${entity.entityName?lower_case}) throws Exception;
	public void removeCollection${entity.entityName}(Collection<${entity.entityName}> list${entity.entityName}) throws Exception;
	
</#list>  		

}