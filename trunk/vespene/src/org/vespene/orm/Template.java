package org.vespene.orm;

public class Template {
	private Integer id;
	private String templatename;
	private String templatefile;
	private String outputpath;
	private String outfilepattern;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTemplatename() {
		return templatename;
	}
	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}
	public String getTemplatefile() {
		return templatefile;
	}
	public void setTemplatefile(String templatefile) {
		this.templatefile = templatefile;
	}
	public String getOutputpath() {
		return outputpath;
	}
	public void setOutputpath(String outputpath) {
		this.outputpath = outputpath;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((templatefile == null) ? 0 : templatefile.hashCode());
		result = prime * result
				+ ((templatename == null) ? 0 : templatename.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Template other = (Template) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (templatefile == null) {
			if (other.templatefile != null)
				return false;
		} else if (!templatefile.equals(other.templatefile))
			return false;
		if (templatename == null) {
			if (other.templatename != null)
				return false;
		} else if (!templatename.equals(other.templatename))
			return false;
		return true;
	}
	public String getOutfilepattern() {
		return outfilepattern;
	}
	public void setOutfilepattern(String outfilepattern) {
		this.outfilepattern = outfilepattern;
	}

	
	
	

}
