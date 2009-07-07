package org.vespene.wizards;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.operation.*;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.Job;

import java.io.*;

import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;
import org.vespene.dao.DBConnection;
import org.vespene.daoh2.ConnectH2;
import org.vespene.daoh2.project.ProjectRelationsDefsDao;
import org.vespene.daoh2.project.ProjectTemplatesDao;
import org.vespene.daoh2.template.TemplateDefsDao;
import org.vespene.freemarker.ParseTemplate;
import org.vespene.freemarker.StringTemplateLoader;
import org.vespene.orm.Db;
import org.vespene.orm.Relations;
import org.vespene.orm.Template;
import org.vespene.project.AnnotationsUtils;
import org.vespene.project.ProjectUtils;
import org.vespene.project.SpringPersistProperties;
import org.vespene.project.Utils;
import org.vespene.properties.Config;
import org.vespene.properties.PojoConfig;
import org.vespene.spring.Entity;
import org.vespene.spring.SpringDefinitions;
import org.vespene.spring.SpringServices;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import flexjson.ClassLocator;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;





public class SpringNewWizard extends Wizard implements INewWizard {
	private SpringNewWizardPage page;
	private ISelection selection;
	private List<Entity> entityList;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public SpringNewWizard() {
		super();
		System.out.println("SpringNewWizard");
		setNeedsProgressMonitor(true);
		setWindowTitle("New Vespene artifacts...");
	}
	
	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		
//		Db db = loadDBParams();
//		DBConnection dbConn = new DBConnection();
//		Connection connection = null;
//		
//		
//		
//		try {
//			connection = dbConn.loadDriver(proj.getLocation()+"/"+db.getDriverClassPath(), db.getDriverClass(), db.getDatabaseURL(), db.getUserName(), db.getPassWord());
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		AnnotationsUtils annotationsUtils = new AnnotationsUtils();
		entityList = annotationsUtils.getFullEntitiesInfo( proj.getLocation().toString() );		
		
		
		page = new SpringNewWizardPage(selection, proj.getLocation().toString(), entityList);
		addPage(page);

	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		final List<SpringServices> listSpringServices = page.getSelectedServices();
		
		SpringPersistProperties springPersistProperties = new SpringPersistProperties(proj, listSpringServices);
		springPersistProperties.storeSpringServices();

		try {
			proj.setPersistentProperty(new QualifiedName("", "ServiceNamePattern"), page.getTextServiceNamePattern() );
			proj.setPersistentProperty(new QualifiedName("", "ServiceInterfacePackage"), page.getTextServiceInterfacePackage() );
			proj.setPersistentProperty(new QualifiedName("", "ServiceImplementationPackage"), page.getTextServiceImplementationPackage() );
			proj.setPersistentProperty(new QualifiedName("", "DaoInterfacePackage"), page.getTextDaoInterfacePackage() );
			proj.setPersistentProperty(new QualifiedName("", "DaoImplementationPackage"), page.getTextDaoImplementationPackage() );
		} catch (CoreException e) {
			e.printStackTrace();
		}			
		
		
		//final List<SpringDefinitions> listSpringDefinitions = page.getSpringDefinitions();
		//final String serviceInterfacePackage = page.getTextServiceInterfacePackage();
		
		
		Job job = new Job("Vespene this creating the spring files...") {
		    @Override
		    protected IStatus run(IProgressMonitor monitor) {
		        monitor.beginTask("progress...", listSpringServices.size());
		        
				try {
					doFinish(listSpringServices, monitor);
				} catch (CoreException e) {
					try {
						throw new InvocationTargetException(e);
					} catch (InvocationTargetException e1) {
						e1.printStackTrace();
					}
				} finally {
					monitor.done();
				}
		        
		        return Status.OK_STATUS;
		    }
		};
		job.schedule();
				
				
	
		/*
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				
				try {
					doFinish(listSpringServices, serviceInterfacePackage, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
				
			}
		};
		
		
		
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		*/
		return true;
	}



	/*
	private void doFinish(List<SpringServices> listSpringServices, List<SpringDefinitions> listSpringDefinitions, String serviceInterfacePackage, IProgressMonitor monitor) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		
		Utils utils = new Utils();
		
		
		//ProjectUtils projectUtils = new ProjectUtils();
		//IProject proj = projectUtils.getProject(selection);
		
		for(Iterator<SpringDefinitions> itDef = listSpringDefinitions.iterator(); itDef.hasNext(); ) {
			SpringDefinitions springDefinitions = (SpringDefinitions) itDef.next();
			
			String dirOut = "/testeJPA/src/"+utils.packageToDirectory( springDefinitions.getPackage() ); 
			String pattern = springDefinitions.getPattern();
			
			ParseTemplate parseTemplate = new ParseTemplate();
			for(Iterator<SpringServices> it = listSpringServices.iterator(); it.hasNext(); ) {
				SpringServices springService = (SpringServices) it.next();
				
				
		        Map<String, String> mapRoot = new HashMap<String, String>();
		        mapRoot.put("serviceName", springService.getServiceName() );
		     
				monitor.beginTask("Creating " + springService.getServiceName(), 2);
				
				IResource resource = root.findMember(new Path( dirOut ));
				if (!resource.exists() || !(resource instanceof IContainer)) {
					throwCoreException("Container \"" + utils.packageToDirectory( springDefinitions.getPackage() ) + "\" does not exist.");
				}
				IContainer container = (IContainer) resource;
				
				String className = parseTemplate.loadTemplateFromString( pattern, mapRoot);
				String fileName = parseTemplate.loadTemplateFromString( pattern, mapRoot)+".java";
				final IFile file = container.getFile(new Path( fileName ));
				
				try {
					InputStream stream = genStpringContentStream(className, fileName, springService, springDefinitions.getPackage(), springDefinitions.getTemplateFile() );
					if (file.exists()) {
						file.setContents(stream, true, true, monitor);
					} else {
						file.create(stream, true, monitor);
					}
					stream.close();
				} catch (IOException e) {
				}
				//monitor.worked(1);
				
			}	
		}
		
		monitor.worked(1);

	}
	*/

	
	/*
	
	private void doFinish(List<SpringServices> listSpringServices, String serviceInterfacePackage, IProgressMonitor monitor) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = null;
		IContainer container;
		IFile file;
		String srcDir;
		Boolean isValidDir;

		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		Utils utils = new Utils();
		
		List<String> listSourceFolder = utils.getSourceFolder(proj);
		
		monitor.beginTask("Creating Spring files" , listSpringServices.size());
		
		for(Iterator<SpringServices> it = listSpringServices.iterator(); it.hasNext(); ) {
			SpringServices springServices = (SpringServices) it.next();
			
			
	        Map<String, String> mapRoot = new HashMap<String, String>();
	        mapRoot.put("serviceName", springServices.getServiceName() );
	     
			//monitor.beginTask("Creating " + springServices.getServiceName(), 2);
	        
	        monitor.subTask("Creating service " + springServices.getServiceName());
			
			isValidDir = true;
    		for(Iterator<String> itSrc = listSourceFolder.iterator(); itSrc.hasNext(); ) {
    			srcDir = itSrc.next();
    			
				resource = root.findMember(new Path( srcDir+"/"+utils.packageToDirectory(springServices.getServiceInterfacePackage()) ));
				if (!resource.exists() || !(resource instanceof IContainer)) {
					isValidDir = false;
					//throwCoreException("Container \"" + utils.packageToDirectory( springServices.getServiceInterfacePackage() ) + "\" does not exist.");
				} else {
					isValidDir = true;
					break;
				}
				
			
    		}	
    		
    		
    		if (isValidDir) {
				container = (IContainer) resource;
				
				file = container.getFile(new Path( springServices.getServiceInterfaceFileName() ));
				
				try {
					InputStream stream = genSpringContentStream(springServices, springServices.getServiceInterfaceTemplateFile() );
					if (file.exists()) {
						file.setContents(stream, true, true, monitor);
					} else {
						file.create(stream, true, monitor);
					}
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}		    			
    		}
 
    		
			for(Iterator<String> itSrc = listSourceFolder.iterator(); itSrc.hasNext(); ) {
    			String src = itSrc.next();

				resource = root.findMember(new Path( src+"/"+utils.packageToDirectory(springServices.getServiceImplementationPackage() ) ));
				if (!resource.exists() || !(resource instanceof IContainer)) {
					isValidDir = false;
					//throwCoreException("Container \"" + utils.packageToDirectory( springServices.getServiceImplementationPackage() ) + "\" does not exist.");
				} else {
					isValidDir = true;
					break;
				}
    		}
			
			if (isValidDir) {
				container = (IContainer) resource;
				
				file = container.getFile(new Path( springServices.getServiceImplementationFileName() ));
				
				try {
					InputStream stream = genSpringContentStream(springServices, springServices.getServiceImplementationTemplateFile() );
					if (file.exists()) {
						file.setContents(stream, true, true, monitor);
					} else {
						file.create(stream, true, monitor);
					}
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}					
			}
			
			
		
			
    		for(Iterator<String> itSrc = listSourceFolder.iterator(); itSrc.hasNext(); ) {
    			String src = itSrc.next();
    			
				resource = root.findMember(new Path( src+"/"+utils.packageToDirectory(springServices.getDaoInterfacePackage() ) ));
				if (!resource.exists() || !(resource instanceof IContainer)) {
					isValidDir = false;
					//throwCoreException("Container \"" + utils.packageToDirectory( springServices.getDaoInterfacePackage() ) + "\" does not exist.");
				} else {
					isValidDir = true;
					break;
				}
				
    		}
    		
    		if (isValidDir) {
				container = (IContainer) resource;
				
				file = container.getFile(new Path( springServices.getDaoInterfaceFileName() ));
				
				try {
					InputStream stream = genSpringContentStream(springServices, springServices.getDaoInterfaceTemplateFile() );
					if (file.exists()) {
						file.setContents(stream, true, true, monitor);
					} else {
						file.create(stream, true, monitor);
					}
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	
    			
    		}

			
    		for(Iterator<String> itSrc = listSourceFolder.iterator(); itSrc.hasNext(); ) {
    			String src = itSrc.next();
    
				resource = root.findMember(new Path( src+"/"+utils.packageToDirectory(springServices.getDaoImplementationPackage() ) ));
				if (!resource.exists() || !(resource instanceof IContainer)) {
					isValidDir = false;
					//throwCoreException("Container \"" + utils.packageToDirectory( springServices.getDaoImplementationPackage() ) + "\" does not exist.");
				} else {
					isValidDir = true;
					break;
				}					
    		}
    		
    		if (isValidDir) {
				container = (IContainer) resource;
				
				file = container.getFile(new Path( springServices.getDaoImplementationFileName() ));
				
				try {
					InputStream stream = genSpringContentStream(springServices, springServices.getDaoImplementationTemplateFile() );
					if (file.exists()) {
						file.setContents(stream, true, true, monitor);
					} else {
						file.create(stream, true, monitor);
					}
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}		    			
    		}
			
		
    		//monitor.worked(1);
		}
		
		monitor.worked(listSpringServices.size());
		
		
		

	}	
	*/
	
	
	
	
	private void doFinish(List<SpringServices> listSpringServices, IProgressMonitor monitor) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = null;
		IContainer container;
		IFile file;
		String srcDir;
		Boolean isValidDir;

		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		Utils utils = new Utils();
		
		List<String> listSourceFolder = utils.getSourceFolder(proj);
		
		//monitor.beginTask("Creating Spring files" , listSpringServices.size());
		
		for(Iterator<SpringServices> it = listSpringServices.iterator(); it.hasNext(); ) {
			SpringServices springServices = (SpringServices) it.next();
			
			
	        Map<String, String> mapRoot = new HashMap<String, String>();
	        mapRoot.put("serviceName", springServices.getServiceName() );
	     
	        //monitor.subTask("Creating service " + springServices.getServiceName());
			
    		resource = root.findMember(new Path( springServices.getServiceInterfaceSrcDir()+"/"+utils.packageToDirectory(springServices.getServiceInterfacePackage()) ));
			container = (IContainer) resource;
			file = container.getFile(new Path( springServices.getServiceInterfaceFileName() ));
			try {
				InputStream stream = genSpringContentStream(springServices, springServices.getServiceInterfaceTemplateFile() );
				if (file.exists()) {
					file.setContents(stream, true, false, monitor);
				} else {
					file.create(stream, true, monitor);
				}
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		    			
    		
 
    		

			
			resource = root.findMember(new Path( springServices.getServiceImplementationSrcDir()+"/"+utils.packageToDirectory(springServices.getServiceImplementationPackage() ) ));
			container = (IContainer) resource;
			file = container.getFile(new Path( springServices.getServiceImplementationFileName() ));
			
			try {
				InputStream stream = genSpringContentStream(springServices, springServices.getServiceImplementationTemplateFile() );
				if (file.exists()) {
					file.setContents(stream, true, true, monitor);
				} else {
					file.create(stream, true, monitor);
				}
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}					
			
			
		

			resource = root.findMember(new Path( springServices.getDaoInterfaceSrcDir()+"/"+utils.packageToDirectory(springServices.getDaoInterfacePackage() ) ));
			container = (IContainer) resource;
			file = container.getFile(new Path( springServices.getDaoInterfaceFileName() ));
			
			try {
				InputStream stream = genSpringContentStream(springServices, springServices.getDaoInterfaceTemplateFile() );
				if (file.exists()) {
					file.setContents(stream, true, true, monitor);
				} else {
					file.create(stream, true, monitor);
				}
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
   

			resource = root.findMember(new Path( springServices.getDaoInterfaceSrcDir()+"/"+utils.packageToDirectory(springServices.getDaoImplementationPackage() ) ));
			container = (IContainer) resource;
			file = container.getFile(new Path( springServices.getDaoImplementationFileName() ));
			
			try {
				InputStream stream = genSpringContentStream(springServices, springServices.getDaoImplementationTemplateFile() );
				if (file.exists()) {
					file.setContents(stream, true, true, monitor);
				} else {
					file.create(stream, true, monitor);
				}
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		    			
    	
			
		
    		monitor.worked(1);
		}
		
		//monitor.worked(listSpringServices.size());
		
		
		

	}		
	

	private InputStream genSpringContentStream(SpringServices springServices, String templateFile) {
		
//		ProjectUtils projectUtils = new ProjectUtils();
//		IProject proj = projectUtils.getProject(selection);
		
		
		Utils utils = new Utils();
		
		//utils.getSourceFolder(proj);
		
		
        Map<String, Object> mapRoot = new HashMap<String, Object>();
        mapRoot.put("serviceInterfacePackage", springServices.getServiceInterfacePackage() );
        mapRoot.put("serviceInterfaceClassName", springServices.getServiceInterfaceClassName() ); 
        mapRoot.put("SpringServices", springServices); //springDef.getServiceName() );
        mapRoot.put("serviceName", springServices.getServiceName() );
        
        
		
		ParseTemplate parseTemplate = new ParseTemplate();
		String sourceCode = null;
		try {
			sourceCode = parseTemplate.loadTemplateFromFile(utils.PluginPath()+"/templates", templateFile, mapRoot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ByteArrayInputStream(sourceCode.getBytes());
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "vespene", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	
    
	private String entitiesArraytoString(List<SpringServices> listSpringDef) {
		String result = "";
		for(Iterator<SpringServices> itSpringDef = listSpringDef.iterator(); itSpringDef.hasNext(); ) {
			SpringServices springDef = (SpringServices) itSpringDef.next();
			
			List<Entity> listEntity = springDef.getEntity();
			for(Iterator<Entity> it = listEntity.iterator(); it.hasNext(); ) {
				Entity entity = (Entity) it.next();
				result += entity.getEntityName()+" ";
			}
		}
		return result;
	}
    
    
    
    private Array[] entitiesStringtoArray (String entities) {
        if (entities == null) {
            return null;
        }
        
        Vector result = new Vector();
        StringTokenizer cpTokenizer = new StringTokenizer(entities, " ");
        while ( cpTokenizer.hasMoreElements() ) {
            String element = cpTokenizer.nextToken();
            try {
                result.add(element);
            } catch (Exception e) {
              
            }
        }
        return (Array[]) result.toArray();
	}
    
    
    
	
	
	private Db loadDBParams() {
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		Db db = new Db();
		try {
			db.setDriverClassPath(proj.getPersistentProperty(new QualifiedName("", "DriverClassPath")));
			db.setDriverClass(proj.getPersistentProperty(new QualifiedName("", "DriverClass")));
			db.setDatabaseURL(proj.getPersistentProperty(new QualifiedName("", "DatabaseURL")));	
			db.setUserName(proj.getPersistentProperty(new QualifiedName("", "UserName")));
			db.setPassWord(proj.getPersistentProperty(new QualifiedName("", "PassWord")));
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return db;
		
	}	
	
	
}