package org.vespene.wizards;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.operation.*;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
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
import org.vespene.project.ProjectUtils;
import org.vespene.project.Utils;
import org.vespene.properties.Config;
import org.vespene.properties.PojoConfig;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;




/**
 * This is a sample new wizard. Its role is to create a new file 
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace 
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "mpe". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class SmartNewWizard extends Wizard implements INewWizard {
	private SmartNewWizardPage page;
	private ISelection selection;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public SmartNewWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("New Vespene artifacts...");
	}
	
	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		Db db = loadDBParams();
		DBConnection dbConn = new DBConnection();
		Connection connection = null;
		
		
		try {
			connection = dbConn.loadDriver(proj.getLocation()+"/"+db.getDriverClassPath(), db.getDriverClass(), db.getDatabaseURL(), db.getUserName(), db.getPassWord());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		page = new SmartNewWizardPage(selection, connection, proj.getLocation().toString());
		addPage(page);

	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final String fileName = "aaaaaa.txt";
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);

		
		ProjectRelationsDefsDao projectRelationsDefsDao = new ProjectRelationsDefsDao(proj.getLocation().toString()+"/.vespene/vespenedb");
		projectRelationsDefsDao.deleteRelationsDefs( page.getSelectTableName() );
		projectRelationsDefsDao.persistRelationsDefs( page.getCustomRelations() );
		
		ProjectTemplatesDao projectTemplatesDao = new ProjectTemplatesDao(proj.getLocation().toString()+"/.vespene/vespenedb");
		projectTemplatesDao.updateTemplatesProject( page.getTemplatesOutputPathList() );
		
		
		final String selectedTableName  = page.getSelectTableName().toUpperCase();
		final List<Template> templateList = projectTemplatesDao.loadTemplatesProject();
		final PojoConfig pojoConfig = new PojoConfig();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				
				try {
					doFinish(pojoConfig, containerName, selectedTableName, templateList , monitor);
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


	
	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 * @param selectedTableName 
	 */
	
	private void doFinish(PojoConfig pojoConfig, String containerName, String selectedTableName, List<Template> templateList, IProgressMonitor monitor) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		

		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		

		
        Map<String, String> mapRoot = new HashMap<String, String>();
        mapRoot.put("Tablename", selectedTableName.substring(0, 1).toUpperCase() + selectedTableName.substring(1).toLowerCase());
        
        
		ParseTemplate parseTemplate = new ParseTemplate();
		

		for(Iterator<Template> it = templateList.iterator(); it.hasNext(); ) {
			org.vespene.orm.Template template = (Template) it.next();
			
			monitor.beginTask("Creating " + template.getTemplatename(), 2);

			
			IResource resource = root.findMember(new Path( template.getOutputpath() ));
			if (!resource.exists() || !(resource instanceof IContainer)) {
				throwCoreException("Container \"" + template.getOutputpath() + "\" does not exist.");
			}
			IContainer container = (IContainer) resource;
			
			String fileName = parseTemplate.loadTemplateFromString(template.getOutfilepattern(), mapRoot);
			final IFile file = container.getFile(new Path( fileName ));
			
			try {
				InputStream stream = openContentStream(selectedTableName, fileName, template);
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
	
	/**
	 * We will initialize file contents with a sample text.
	 * @param selectedTableName 
	 * @param fileName 
	 * @param template 
	 */

	private InputStream openContentStream(String selectedTableName, String fileName, Template template) {
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		
		Utils utils = new Utils();
		
		utils.getSourceFolder(proj);
		
		
		
        Map<String, Object> mapRoot = new HashMap<String, Object>();
        mapRoot.put("package", "br.pacote.kkk");
        mapRoot.put("classname", fileName);
        mapRoot.put("tablename", selectedTableName.substring(0, 1).toUpperCase() + selectedTableName.substring(1).toLowerCase());
        
        
		
		
		ParseTemplate parseTemplate = new ParseTemplate();
		String sourceCode = null;
		try {
			sourceCode = parseTemplate.loadTemplateFromFile(utils.PluginPath()+"/templates", template.getTemplatefile(), mapRoot);
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