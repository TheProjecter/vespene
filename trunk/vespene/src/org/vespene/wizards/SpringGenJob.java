package org.vespene.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.vespene.freemarker.ParseTemplate;
import org.vespene.project.Utils;
import org.vespene.spring.model.SpringDefinitions;
import org.vespene.spring.model.SpringServices;

public class SpringGenJob extends WorkspaceJob {
	private SpringServices springServices; 
	private String srcDir; 
	private String _package; 
	private String fileName; 
	private String templateFile;
	private SpringDefinitions springDefinitions;
	
	
	public SpringGenJob(String arg0) {
		super(arg0);
	}

	
	//@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		Utils utils = new Utils();
		
		IResource resource = root.findMember(new Path( srcDir+"/"+utils.packageToDirectory(_package) ));
		IContainer container = (IContainer) resource;
		IFile file = container.getFile(new Path( fileName ));
		
		
		try {
			monitor.beginTask("Create some files", 1);
			InputStream stream = genSpringContentStream(springServices, templateFile );
			if (file.exists()) {
				try {
					file.setContents(stream, true, false, monitor);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			} else {
				try {
					file.create(stream, true, monitor);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
			stream.close();
			monitor.worked(1);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            monitor.done();
		}		
		
		return Status.OK_STATUS;		
		
	}		
	

	
	
	private InputStream genSpringContentStream(SpringServices springServices, String templateFile) {
		Utils utils = new Utils();
		
        Map<String, Object> mapRoot = new HashMap<String, Object>();
        mapRoot.put("SpringServices", springServices); 
		
		ParseTemplate parseTemplate = new ParseTemplate();
		String sourceCode = null;
		try {
			sourceCode = parseTemplate.loadTemplateFromFile(utils.PluginPath()+"/templates", templateFile, mapRoot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ByteArrayInputStream(sourceCode.getBytes());
	}

	
	public SpringServices getSpringServices() {
		return springServices;
	}

	public void setSpringServices(SpringServices springServices) {
		this.springServices = springServices;
	}

	public String getSrcDir() {
		return srcDir;
	}

	public void setSrcDir(String srcDir) {
		this.srcDir = srcDir;
	}

	public String get_package() {
		return _package;
	}

	public void set_package(String _package) {
		this._package = _package;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}


	public SpringDefinitions getSpringDefinitions() {
		return springDefinitions;
	}


	public void setSpringDefinitions(SpringDefinitions springDefinitions) {
		this.springDefinitions = springDefinitions;
	}


	

}
