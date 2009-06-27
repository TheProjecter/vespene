package vespene.spring;

import java.util.List;

public class SpringServices {
	private String ServiceName;
	private List<Entity> entity;
	
	
	
	
	public SpringServices() {

	}



	public String getServiceName() {
		return ServiceName;
	}



	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}



	public List<Entity> getEntity() {
		return entity;
	}



	public void setEntity(List<Entity> entity) {
		this.entity = entity;
	}



	
	
	
}