package vespene.wizards;

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
import java.io.*;

import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import flexjson.ClassLocator;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;



import vespene.dao.DBConnection;
import vespene.daoh2.ConnectH2;
import vespene.daoh2.project.ProjectRelationsDefsDao;
import vespene.daoh2.project.ProjectTemplatesDao;
import vespene.daoh2.template.TemplateDefsDao;
import vespene.freemarker.ParseTemplate;
import vespene.freemarker.StringTemplateLoader;
import vespene.orm.Db;
import vespene.orm.Relations;
import vespene.orm.Template;
import vespene.project.AnnotationsUtils;
import vespene.project.ProjectUtils;
import vespene.project.SpringPersistProperties;
import vespene.project.Utils;
import vespene.properties.Config;
import vespene.properties.PojoConfig;
import vespene.spring.Entity;
import vespene.spring.SpringDefinitions;
import vespene.spring.SpringServices;


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
		//final String containerName = page.getContainerName();
		final String fileName = "aaaaaa.txt";
		
		final List<SpringServices> listSpringDef = page.getSelectedServices();
		final String serviceInterfacePackage   = page.getTextServiceInterfacePackage();
				
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		
		System.out.println("perform setPersistentProperty");
		
		

		
		SpringPersistProperties springPersistProperties = new SpringPersistProperties(proj, listSpringDef);
		springPersistProperties.storeSpringDef();

		try {
			proj.setPersistentProperty(new QualifiedName("", "ServiceNamePattern"), page.getTextServiceNamePattern() );
			proj.setPersistentProperty(new QualifiedName("", "ServiceInterfacePackage"), page.getTextServiceInterfacePackage() );
			proj.setPersistentProperty(new QualifiedName("", "ServiceImplementationPackage"), page.getTextServiceImplementationPackage() );
			proj.setPersistentProperty(new QualifiedName("", "DaoInterfacePackage"), page.getTextDaoInterfacePackage() );
			proj.setPersistentProperty(new QualifiedName("", "DaoImplementationPackage"), page.getTextDaoImplementationPackage() );
		} catch (CoreException e) {
			e.printStackTrace();
		}	
		

		
		
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				
				try {
					doFinish(listSpringDef, serviceInterfacePackage, monitor);
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
		return true;
	}



	
	private void doFinish(List<SpringServices> listSpringServices, String serviceInterfacePackage, IProgressMonitor monitor) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		
		Utils utils = new Utils();
		
		
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		
		String serviceInterfaceDir = "/testeJPA/src/"+utils.packageToDirectory( serviceInterfacePackage ); 
		
		String springServiceInterfacePattern = proj.getPersistentProperty(new QualifiedName("", "SpringServiceInterfacePattern"));
		

		SpringDefinitions springDefinitions = new SpringDefinitions();
		
		
//		springDefinitions.setServiceInterfacePackage();
//		springDefinitions.setServiceInterfaceTemplate();
//		springDefinitions.setServiceInterfaceFilename();
//
//		springDefinitions.setServiceImplementationPackage();
//		springDefinitions.setServiceImplementationTemplate();
//		springDefinitions.setServiceImplementationFilename();
//
//		
//		springDefinitions.setDaoInterfacePackage();
//		springDefinitions.setDaoInterfaceTemplate();
//		springDefinitions.setDaoInterfaceFilename();
//		
//		
//		springDefinitions.setDaoImplementationPackage();
//		springDefinitions.setDaoImplementationTemplate();
//		springDefinitions.setDaoImplementationFilename();
		
		
		
        
		ParseTemplate parseTemplate = new ParseTemplate();
		
		
		
		for(Iterator<SpringServices> it = listSpringServices.iterator(); it.hasNext(); ) {
			SpringServices springService = (SpringServices) it.next();
			
	        Map<String, String> mapRoot = new HashMap<String, String>();
	        mapRoot.put("serviceName", springService.getServiceName() );
	     
			
			
			monitor.beginTask("Creating " + springService.getServiceName(), 2);

			System.out.println("1111111111111111111 " );
			System.out.println("222222222222222222 "+ serviceInterfacePackage );
			System.out.println("aaaaaaaaaaaa "+ utils.packageToDirectory( serviceInterfacePackage ) );
			
			IResource resource = root.findMember(new Path( serviceInterfaceDir ));
			if (!resource.exists() || !(resource instanceof IContainer)) {
				throwCoreException("Container \"" + utils.packageToDirectory( serviceInterfacePackage ) + "\" does not exist.");
			}
			IContainer container = (IContainer) resource;
			
			
			
			
			
			
			String className = parseTemplate.loadTemplateFromString( springServiceInterfacePattern, mapRoot);
			String fileName = parseTemplate.loadTemplateFromString( springServiceInterfacePattern, mapRoot)+".java";
			
			final IFile file = container.getFile(new Path( fileName ));
			
			try {
				InputStream stream = openContentStream(className, fileName, springService, serviceInterfacePackage);
				if (file.exists()) {
					file.setContents(stream, true, true, monitor);
				} else {
					file.create(stream, true, monitor);
				}
				stream.close();
			} catch (IOException e) {
			}
			monitor.worked(1);
			
			
			

		}	
		

	}
	

	

	private InputStream openContentStream(String className, String fileName, SpringServices springServices, String serviceInterfacePackage) {
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		
		Utils utils = new Utils();
		
		utils.getSourceFolder(proj);
		
		
		
        Map<String, Object> mapRoot = new HashMap<String, Object>();
        mapRoot.put("package", serviceInterfacePackage );
        mapRoot.put("classname", className);
       // mapRoot.put("EntityName", "entidade"); //springDef.getServiceName() );
        mapRoot.put("SpringServices", springServices); //springDef.getServiceName() );
        
        
        
        
        
//        springDef.get
//        mapRoot.put("PkType",   ); //springDef.getServiceName() );
        
        
        
        //mapRoot.put("tablename", selectedTableName.substring(0, 1).toUpperCase() + selectedTableName.substring(1).toLowerCase());
        
        
		
		
		ParseTemplate parseTemplate = new ParseTemplate();
		String sourceCode = null;
		try {
			String springServiceInterfaceTemplateFile = proj.getPersistentProperty(new QualifiedName("", "SpringServiceInterfaceTemplateFile"));
			sourceCode = parseTemplate.loadTemplateFromFile(utils.PluginPath()+"/templates", springServiceInterfaceTemplateFile, mapRoot);
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