package org.vespene.wizards;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Map.Entry;


import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.PackageFragment;



import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
//import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.vespene.dao.DBConnection;
import org.vespene.dao.FieldsDao;
import org.vespene.dao.RelationsDao;
import org.vespene.dao.TablesDao;
import org.vespene.daoh2.project.ProjectRelationsDefsDao;
import org.vespene.daoh2.project.ProjectTemplatesDao;
import org.vespene.freemarker.ParseTemplate;
import org.vespene.orm.Db;
import org.vespene.orm.Fields;
import org.vespene.orm.Relations;
import org.vespene.orm.RelationsContainner;
import org.vespene.orm.Tables;
import org.vespene.orm.Template;
import org.vespene.project.AnnotationsUtils;
import org.vespene.project.ProjectUtils;
import org.vespene.project.Sorter;
import org.vespene.project.SpringPersistProperties;
import org.vespene.project.Utils;
import org.vespene.properties.Config;
import org.vespene.properties.PojoConfig;
import org.vespene.spring.Entity;
import org.vespene.spring.SpringDefinitions;
import org.vespene.spring.SpringServices;






/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class SpringNewWizardPage extends WizardPage {
	List<Entity> entityList;
	
	private Group group1;
	private Group group3;
	private Group group2;
	private Table tableCustomRelations;
	private Table tableSpringServices;
	private Table tableTemplates;
	private TableColumn tableColumn1;
	private TableItem tableItem1;
	
	private Text textServiceInterfacePackage;
	private Text textServiceImplementationPackage;
	private Text textDaoImplementationPackage;
	private Text textDaoInterfacePackage;
	




	//private Text textDriverClassPath;
	//private Text textDriverClass;
	//private Text textDatabaseURL;
	//private Text textUserName;
	//private Text textPassWord;
	
	private Image image;

	//private Text fileText;

	private ISelection selection;
	private Connection connection;
	private String projPath;
	
	private CCombo combo; // = new CCombo(tableDBRelations, SWT.READ_ONLY);
	private Text textServiceNamePattern;
	
//	private Display display;

	/**
	 * Constructor for SampleNewWizardPage.
	 * @param conn 
	 * 
	 * @param pageName
	 */
	public SpringNewWizardPage(ISelection selection, String projPath, List<Entity> entityList) {
		super("wizardPage");
		setTitle("Vespene Generator");
		setDescription("Generate Spring DAO classes");
		this.selection = selection;
		this.connection = connection;
		this.projPath = projPath;
		this.entityList = entityList;
	}



	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		
		
		ProfileManager pf = new ProfileManager();
		IConnectionProfile[] gp = pf.getProfiles();
		System.out.println( "gp[0].getProviderName() "+gp[0].getProviderName() );
		System.out.println( "gp[0].getProviderName() "+gp[0].getName() );
		@SuppressWarnings("unused")
		Properties aa = gp[0].getBaseProperties();

		
		//gp[0].createConnection(arg0);

		
		
		
		
		 //aa = org.eclipse.datatools.connectivity.internal.ui.DriverListCombo; 
	  
		//IConnectionProfile[] databaseProfiles = ProfileManager.getProfilesByCategory("org.eclipse.datatools.connectivity.db.category"); 
		
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		Utils utils = new Utils();
		List<String> listSourceFolder = utils.getSourceFolder(proj);
		
		

		System.out.println( "proj.getLocation().toString() "+proj.getLocation().toString() );

		
		
		//AnnotationsUtils annotationsUtils = new AnnotationsUtils();
		//final List<String> entityList = annotationsUtils.getEntities( proj.getLocation().toString() );
		//final List<String> entityList = annotationsUtils.getEntitiesInfo( proj.getLocation().toString() );
		//final List<Entity> entityList = annotationsUtils.getFullEntitiesInfo( proj.getLocation().toString() );
		
		
		
		
	
		
//		PojoConfig pojoConfig = new PojoConfig();
		
		Composite container1 = new Composite(parent, SWT.NONE);
		
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		//layout.verticalSpacing = 9;
		container1.setLayout(layout);
		
		
		
		GridData composite1LData = new GridData();
		composite1LData.horizontalAlignment = GridData.FILL;
		composite1LData.verticalAlignment = GridData.FILL;
		composite1LData.grabExcessHorizontalSpace = true;
		composite1LData.grabExcessVerticalSpace = true;
		//composite1LData.minimumWidth = 600;
		//composite1LData.widthHint = 600;
		container1.setLayoutData(composite1LData);		
		
	
		
		
		//*************
		
		
		{

			
			Group group4 = new Group(container1, SWT.NONE);
			GridLayout group1Layout = new GridLayout();
			group1Layout.numColumns = 3;
			//group1Layout.makeColumnsEqualWidth = true;
			group4.setLayout(group1Layout);
			GridData group4LData = new GridData();
			group4LData.horizontalAlignment = GridData.FILL;
			group4LData.verticalAlignment = GridData.FILL;
			group4LData.grabExcessHorizontalSpace = true;
			//group4LData.grabExcessVerticalSpace = true;			
			
			//group4LData.heightHint = 400;
			group4.setLayoutData(group4LData);
			
			
			{
				Label label1 = new Label(group4, SWT.NONE);
				label1.setText("Service Name Pattern");
			}
			{
				textServiceNamePattern = new Text(group4, SWT.SINGLE | SWT.BORDER);
				GridData text1LData = new GridData();
				text1LData.horizontalAlignment = GridData.FILL;
				text1LData.minimumWidth = 250;
				text1LData.grabExcessHorizontalSpace = true;
				textServiceNamePattern.setLayoutData(text1LData);
				textServiceNamePattern.setText("s${EntityName}");
				textServiceNamePattern.setOrientation(SWT.HORIZONTAL);
				
				textServiceNamePattern.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent evt) {
						dialogChanged();
						setPatternServiceName();
					}
				});				
				
				textServiceNamePattern.addFocusListener(new FocusAdapter() {
					public void focusLost(FocusEvent evt) {
						setPatternServiceName();
					}
				});				
				
			}			
			
			
		

		}			
		
		
		// ************
		
		
		{

			
			group1 = new Group(container1, SWT.NONE);
			GridLayout group1Layout = new GridLayout();
			group1Layout.numColumns = 3;
			//group1Layout.makeColumnsEqualWidth = true;
			group1.setLayout(group1Layout);
			GridData group1LData = new GridData();
			group1LData.horizontalAlignment = GridData.FILL;
			group1LData.verticalAlignment = GridData.FILL;
			group1LData.grabExcessHorizontalSpace = true;
			group1LData.grabExcessVerticalSpace = true;			
			
			group1LData.heightHint = 400;
			group1.setLayoutData(group1LData);
			
			{
				Button button12 = new Button(group1, SWT.PUSH | SWT.CENTER);
				GridData button12LData = new GridData();
				button12.setLayoutData(button12LData);
				//button12.setText("Add custom service");
				
				image = new Image(parent.getDisplay(), getClass().getResourceAsStream("/icons/add_obj.gif") );
				button12.setImage(image);
				
				final Composite p = parent;
				final List<Entity> l = entityList;
				
				button12.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						CustomSpringService customSpringService = new CustomSpringService();
						customSpringService.show(p, entityList); //   show(p,entityList);
					}
				});					
				
			}
			{
				Button button13 = new Button(group1, SWT.PUSH | SWT.CENTER);
				GridData button13LData = new GridData();
				button13.setLayoutData(button13LData);
				//button13.setText("Edit");
				
				image = new Image(parent.getDisplay(), getClass().getResourceAsStream("/icons/remove_correction.gif") );
				button13.setImage(image);	
				
				final Composite p = parent;
				
				button13.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
					}
				});					
				
				
				
			}			
			{
				Button button14 = new Button(group1, SWT.PUSH | SWT.CENTER);
				GridData button13LData = new GridData();
				
				image = new Image(parent.getDisplay(), getClass().getResourceAsStream("/icons/custom.gif") );
				button14.setImage(image);	
				
				final Composite p = parent;
				
				button14.setLayoutData(button13LData);
				//button14.setText("Delete");
			}					
			
		

			{
				GridData table1LData = new GridData();
				table1LData.horizontalSpan = 3;
				table1LData.horizontalAlignment = GridData.FILL;
				table1LData.verticalAlignment = GridData.FILL;
				table1LData.grabExcessVerticalSpace = true;
				
				table1LData.grabExcessHorizontalSpace = true;
				
				tableSpringServices = new Table(group1, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
				tableSpringServices.setLayoutData(table1LData);
				tableSpringServices.setHeaderVisible(true);
				tableSpringServices.setLinesVisible(true);
				
		
				tableSpringServices.setLayoutData(table1LData);		
				
				{
					tableColumn1 = new TableColumn(tableSpringServices, SWT.NONE);
					tableColumn1.setText("Service name");
					tableColumn1.setWidth(150);
					tableColumn1.setResizable(false);
					tableColumn1.setData(tableSpringServices);
					
				}				
				
				{
					tableColumn1 = new TableColumn(tableSpringServices, SWT.NONE);
					tableColumn1.setText("Entity");
					tableColumn1.setWidth(200);
					tableColumn1.setResizable(false);
					tableColumn1.setData(tableSpringServices);
					
				}

				
				{
					
					//setEntityGrid(entityList);
					
//					for(Iterator<String> it = entityList.iterator(); it.hasNext(); ) {
//						String s = it.next();
//						
//						tableItem1 = new TableItem(tableDBTables, SWT.NONE); 
//						tableItem1.setText( new String[] { "s"+s, s } );
//					}					
				}
				
				
				
			}
		}	
		
	
			
		
		{

			group2 = new Group(container1, SWT.NONE);
			GridLayout group2Layout = new GridLayout();
			group2Layout.numColumns = 3;
			group2.setLayout(group2Layout);
			GridData group2LData = new GridData();
			group2LData.horizontalAlignment = GridData.FILL;
			group2LData.grabExcessHorizontalSpace = true;
			group2LData.verticalAlignment = GridData.FILL;
			group2.setLayoutData(group2LData);
			//group2.setText("group2");			
			
			
			GridData table1LData = new GridData();
			table1LData.horizontalAlignment = GridData.FILL;
			table1LData.verticalAlignment = GridData.FILL;
			table1LData.grabExcessVerticalSpace = true;
			
			table1LData.grabExcessHorizontalSpace = true;		
			
			
			/*
			{
				Label label1 = new Label(group2, SWT.NONE);
				label1.setText("Source Folder");
			}			
			{
				Combo comboListSource = new Combo(group2, SWT.READ_ONLY);
				//comboListSource.setEditable(false);
				GridData combo1LData = new GridData();
				//combo1LData.heightHint = 21;
				combo1LData.horizontalSpan = 2;
				combo1LData.minimumWidth = 250;
				combo1LData.horizontalAlignment = GridData.FILL;
				combo1LData.grabExcessHorizontalSpace = true;
				comboListSource.setLayoutData(combo1LData);
			
				
				for(Iterator<String> it = listSourceFolder.iterator(); it.hasNext(); ) {
					String src = (String) it.next();
					comboListSource.add( src );
				}				
				
			}	
			*/		
			
			
			
			{
				Label label1 = new Label(group2, SWT.NONE);
				label1.setText("Service interface");
			}
			{
				textServiceInterfacePackage = new Text(group2, SWT.SINGLE | SWT.BORDER);
				GridData text1LData = new GridData();
				text1LData.horizontalAlignment = GridData.FILL;
				text1LData.minimumWidth = 250;
				text1LData.grabExcessHorizontalSpace = true;
				textServiceInterfacePackage.setLayoutData(text1LData);
				textServiceInterfacePackage.setText("spring.dao");
				textServiceInterfacePackage.setOrientation(SWT.HORIZONTAL);
				textServiceInterfacePackage.setEditable(false);
			}
			{
				Button button1 = new Button(group2, SWT.PUSH | SWT.CENTER);
				GridData button1LData = new GridData();
				button1LData.horizontalAlignment = GridData.END;
				button1LData.heightHint = 23;
				button1LData.widthHint = 23;				
				button1.setLayoutData(button1LData);
				//button1.setText("");
				
				image = new Image(group2.getDisplay(), getClass().getResourceAsStream("/icons/package_obj.gif") );
				button1.setImage(image);
				button1.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						handleBrowse(textServiceInterfacePackage, "Service interface");
					}
				});	
				
			}
			
			
			
	
		
			{
				Label label1 = new Label(group2, SWT.NONE);
				label1.setText("Service implementation");
			}
			{
				textServiceImplementationPackage = new Text(group2, SWT.SINGLE | SWT.BORDER);
				GridData text1LData = new GridData();
				text1LData.horizontalAlignment = GridData.FILL;
				text1LData.minimumWidth = 250;
				text1LData.grabExcessHorizontalSpace = true;
				textServiceImplementationPackage.setLayoutData(text1LData);
				textServiceImplementationPackage.setText("spring.dao");
				textServiceImplementationPackage.setOrientation(SWT.HORIZONTAL);
				textServiceImplementationPackage.setEditable(false);
			}
			{
				Button button1 = new Button(group2, SWT.PUSH | SWT.CENTER);
				GridData button1LData = new GridData();
				button1LData.horizontalAlignment = GridData.END;
				button1LData.heightHint = 23;
				button1LData.widthHint = 23;				
				button1.setLayoutData(button1LData);
				//button1.setText("Browse...");

				image = new Image(group2.getDisplay(), getClass().getResourceAsStream("/icons/package_obj.gif") );
				button1.setImage(image);
				button1.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						handleBrowse(textServiceImplementationPackage, "Service implementation");
					}
				});	
				
				
			}				
			
		
			
			
			{
				Label label1 = new Label(group2, SWT.NONE);
				label1.setText("DAO interface");
			}
			{
				textDaoInterfacePackage = new Text(group2, SWT.SINGLE | SWT.BORDER);
				GridData text1LData = new GridData();
				text1LData.horizontalAlignment = GridData.FILL;
				text1LData.minimumWidth = 250;
				text1LData.grabExcessHorizontalSpace = true;
				textDaoInterfacePackage.setLayoutData(text1LData);
				textDaoInterfacePackage.setText("spring.dao");
				textDaoInterfacePackage.setOrientation(SWT.HORIZONTAL);
				textDaoInterfacePackage.setEditable(false);
			}
			{
				Button button1 = new Button(group2, SWT.PUSH | SWT.CENTER);
				GridData button1LData = new GridData();
				button1LData.horizontalAlignment = GridData.END;
				button1LData.heightHint = 23;
				button1LData.widthHint = 23;				
				button1.setLayoutData(button1LData);
				//button1.setText("Browse...");
				
				image = new Image(group2.getDisplay(), getClass().getResourceAsStream("/icons/package_obj.gif") );
				button1.setImage(image);
				button1.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						handleBrowse(textDaoInterfacePackage, "DAO interface");
					}
				});	

				
			}		
			
			
			
			{
				Label label1 = new Label(group2, SWT.NONE);
				label1.setText("DAO implementation");
			}
			{
				textDaoImplementationPackage = new Text(group2, SWT.SINGLE | SWT.BORDER);
				GridData text1LData = new GridData();
				text1LData.horizontalAlignment = GridData.FILL;
				text1LData.minimumWidth = 250;
				text1LData.grabExcessHorizontalSpace = true;
				textDaoImplementationPackage.setLayoutData(text1LData);
				textDaoImplementationPackage.setText("spring.dao");
				textDaoImplementationPackage.setOrientation(SWT.HORIZONTAL);
				textDaoImplementationPackage.setEditable(false);
			}
			{
				Button button1 = new Button(group2, SWT.PUSH | SWT.CENTER);
				GridData button1LData = new GridData();
				button1LData.horizontalAlignment = GridData.END;
				button1LData.heightHint = 23;
				button1LData.widthHint = 23;				
				button1.setLayoutData(button1LData);
				//button1.setText("Browse...");
				
				image = new Image(group2.getDisplay(), getClass().getResourceAsStream("/icons/package_obj.gif") );
				button1.setImage(image);
				
				button1.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						handleBrowse(textDaoImplementationPackage, "DAO implementation");
					}
				});	
				
			}				
			
			
			
			
			
			
			

		}		
		
		
		
		
		
		
	
		
		

		
		initialize();
		dialogChanged();
		//validateOutputPathList();
		setControl(container1);
		
		setEntityPatternPackages();
		setEntityGrid();
		tableSpringServices.setSelection(0);
		
		
		
		
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				
				System.out.println("initialize "+ container.getFullPath().toString() );
				
				//setEntityGrid(entityList);
				

			}
		}
	
		
		
		
		
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 * @param text 
	 * @param column 
	 */

	
	private void handleBrowse(Text text, String msg) {

		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
	
		try {
			

			
//			IJavaProject javaProject = JavaCore.create(proj);
		
			
			SelectionDialog packages=JavaUI.createPackageDialog(getShell(), JavaCore.create(proj), IJavaElementSearchConstants.CONSIDER_REQUIRED_PROJECTS);
			packages.setTitle("Package selection");
			packages.setMessage("Choose a package for "+msg);
			packages.open();
			if (packages.getReturnCode()!=SelectionDialog.CANCEL) {
				PackageFragment pf=(PackageFragment) packages.getResult()[0];
				
				text.setText(pf.getElementName());
				dialogChanged();

			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		
	
	}
	
	

	
	private void validateOutputPathList() {
		IResource container = null; 
		
		List<Template> templateList = getTemplatesOutputPathList();
		
		for(Iterator<Template> it = templateList.iterator(); it.hasNext(); ) {
			Template template = it.next();
			
			container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(template.getOutputpath()) );	
			
			if (template.getOutputpath().length() == 0) {
				updateStatus(template.getTemplatename()+" - Output path must be specified");
				return;
			}
			if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
				updateStatus(template.getTemplatename()+" - Output path must exist");
				return;
			}
			if (!container.isAccessible()) {
				updateStatus("Project must be writable");
				return;
			}
			
		}	
		
		
		updateStatus(null);
	}
		



	private void dialogChanged() {
		//IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getContainerName()));
		
		IResource containerServiceInterface = null;
		

		
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);

		
		Utils utils = new Utils();
		List<String> listSourceFolder = utils.getSourceFolder(proj);
		
		
		
		//System.out.println( "utils.packageToDirectory( textServiceInterface.getText() ) "+utils.packageToDirectory( textServiceInterfacePackage.getText() ) );
		
		
		if ( !textServiceNamePattern.getText().contains("${EntityName}") ) {
			updateStatus("Service name pattern is invalid, ${EntityName} not found.");
			return;
		}
		
		
		
		
        Map<String, String> mapPackages = new LinkedHashMap<String, String>();
        mapPackages.put("Service Interface", utils.packageToDirectory( textServiceInterfacePackage.getText() ));
        mapPackages.put("Service Implementation", utils.packageToDirectory( textServiceImplementationPackage.getText() ));
        mapPackages.put("Dao Interface", utils.packageToDirectory( textDaoInterfacePackage.getText() ));
        mapPackages.put("Dao Implementation", utils.packageToDirectory( textDaoImplementationPackage.getText() ));
        
        
 
		
		for (Map.Entry<String,String> pair : mapPackages.entrySet()) {


            //Map.Entry pairs = (Map.Entry)itPkg.next();
            //System.out.println("Iterator itPkg "+pair.getKey() + " = " + pair.getValue());
            
            Boolean isServiceInterfaceValidDir = true;
    		for(Iterator<String> it = listSourceFolder.iterator(); it.hasNext(); ) {
    			String src = it.next();
    			
    			
				//System.out.println("      source folder "+ src +" / "+pair.getValue() );
    			IResource containerPackage = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path( src+"/"+pair.getValue()  ));
    			
    			if (containerPackage == null || (containerPackage.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
    				isServiceInterfaceValidDir = false;
    			} else {
    				isServiceInterfaceValidDir = true;
    				break;
    			}
    		}
    		System.out.println(" ");
    		if (!isServiceInterfaceValidDir) {
    			updateStatus("Package for "+pair.getKey()+" must exist.");
    			return;
    		}
            
        }
        
		
		updateStatus(null);
	}
	
	
	
	
	

	
	

	
	

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

//	public String getContainerName() {
//		return "AAA"; //containerServletText.getText();
//	}




	
	
	public void setPatternServiceName() {
		TableItem[] tableItem = tableSpringServices.getItems();
		
        for (int i = 0; i < tableItem.length; i++) {
            TableItem item = tableItem[i];
            
            
	        Map<String, String> mapRoot = new HashMap<String, String>();
	        mapRoot.put("EntityName", item.getText(1));
	        
	        ParseTemplate parseTemplate = new ParseTemplate();
	        String serviceName = parseTemplate.loadTemplateFromString(textServiceNamePattern.getText(), mapRoot);
			
 
	        item.setText(0, serviceName);
	        
        }
		
		
/*		
		if ( textServiceNamePattern.getText().contains("${EntityName}") ) {
			tableDBTables.removeAll();
			
			for(Iterator<String> it = entityList.iterator(); it.hasNext(); ) {
				String s = it.next();
				
		        Map<String, String> mapRoot = new HashMap<String, String>();
		        mapRoot.put("EntityName", s);
		        
		        ParseTemplate parseTemplate = new ParseTemplate();
		        String serviceName = parseTemplate.loadTemplateFromString(textServiceNamePattern.getText(), mapRoot);
				
				tableItem1 = new TableItem(tableDBTables, SWT.NONE); 
				tableItem1.setText( new String[] { serviceName, s } );
			}		
		} else {
			//updateStatus("Service name pattern is invalid, ${EntityName}");
		}
		*/
        
		dialogChanged();
			
		
		
	}
	
	
	
	
	
	/*
	public List<SpringServices> getSelectedServices() {
        TableItem[] tableItem = tableSpringServices.getItems();
        List<SpringServices> listSpringDef = new ArrayList<SpringServices>();
        
        Utils utils = new Utils();
        
        for (int i = 0; i < tableItem.length; i++) {
            TableItem item = tableItem[i];
            if (item.getChecked()) {
            	
            	List<Entity> entityListLocal = new ArrayList<Entity>();
            	
                StringTokenizer cpTokenizer = new StringTokenizer(item.getText(1), ",");
                while ( cpTokenizer.hasMoreElements() ) {
                    String element = cpTokenizer.nextToken();
                    
                    System.out.println("entity string  "+element);

        			for(Iterator<Entity> it = entityList.iterator(); it.hasNext(); ) {
        				Entity entity = (Entity) it.next();
        				
                        System.out.println("entity.getEntityName()  "+entity.getEntityName()+" - "+entity.getEntityName().equals(element) );        				

        				if (entity.getEntityName().equals(element.trim())  ) {
        					System.out.println("       entity list  "+entity.getEntityName());
                        	entityListLocal.add(entity);
        				}

        			}            	
                    

                }            	
            	
            	
            	SpringServices springServices = new SpringServices();
				springServices.setEntity(entityListLocal);
				
				springServices.setServiceName( item.getText(0) );
				
				
				springDef.setServiceInterfacePackage( textServiceInterfacePackage.getText() );
				springDef.setServiceImplementationTemplate("");
				springDef.setServiceImplementationFilename("");
				
				springDef.setServiceImplementationPackage( textServiceImplementationPackage.getText() );
				springDef.setServiceImplementationTemplate("");
				springDef.setServiceImplementationFilename("");
				
				springDef.setDaoInterfacePackage( textDaoInterfacePackage.getText() );
				springDef.setDaoInterfaceTemplate("");
				springDef.setDaoInterfaceFilename("");
				
				springDef.setDaoImplementationPackage( textDaoImplementationPackage.getText() );
				springDef.setDaoImplementationTemplate("");
				springDef.setDaoImplementationFilename("");
				
				

				listSpringDef.add(springServices);
				
            }
        }
        return listSpringDef;
		
	}
	*/
	
	
	
	
	public List<SpringServices> getSelectedServices() {
        TableItem[] tableItem = tableSpringServices.getItems();
        List<SpringServices> listSpringDef = new ArrayList<SpringServices>();
        
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);		
        
        
		Utils utils = new Utils();
		List<String> listSourceFolder = utils.getSourceFolder(proj);
		
		String srcDir = null;
		for(Iterator<String> itSrc = listSourceFolder.iterator(); itSrc.hasNext(); ) {
			srcDir = itSrc.next();
			break;
		}		
        
        
        try {
			String serviceInterfacePackage = proj.getPersistentProperty(new QualifiedName("", "ServiceInterfacePackage"));
	        String serviceInterfaceTemplateFile = proj.getPersistentProperty(new QualifiedName("", "SpringServiceInterfaceTemplateFile")); 
	        String serviceInterfacePattern = proj.getPersistentProperty(new QualifiedName("", "SpringServiceInterfacePattern")); 
			
			
	        String daoInterfacePackage = proj.getPersistentProperty(new QualifiedName("", "DaoInterfacePackage")); 
	        String daoInterfaceTemplateFile = proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOInterfaceTemplateFile")); 
	        String daoInterfacePattern = proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOInterfacePattern")); 
			
			
	        String serviceImplementationPackage = proj.getPersistentProperty(new QualifiedName("", "ServiceImplementationPackage")); 
	        String serviceImplementationTemplateFile = proj.getPersistentProperty(new QualifiedName("", "SpringServiceImplementationTemplateFile")); 
	        String serviceImplementationPattern = proj.getPersistentProperty(new QualifiedName("", "SpringServiceImplementationPattern")); 
			
			
	        String daoImplementationPackage = proj.getPersistentProperty(new QualifiedName("", "DaoImplementationPackage")); 
	        String daoImplementationTemplateFile = proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOImplementationTemplateFile")); 
	        String daoImplementationPattern = proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOImplementationPattern")); 

        
        
	        for (int i = 0; i < tableItem.length; i++) {
	            TableItem item = tableItem[i];
	            if (item.getChecked()) {
	            	
	            	List<Entity> listEntity = new ArrayList<Entity>();
	            	
	                StringTokenizer cpTokenizer = new StringTokenizer(item.getText(1), ",");
	                while ( cpTokenizer.hasMoreElements() ) {
	                    String element = cpTokenizer.nextToken();
	                    
	                    //System.out.println("entity string  "+element);
	
	        			for(Iterator<Entity> it = entityList.iterator(); it.hasNext(); ) {
	        				Entity entity = (Entity) it.next();
	        				
	                        //System.out.println("entity.getEntityName()  "+entity.getEntityName()+" - "+entity.getEntityName().equals(element) );        				
	
	        				if (entity.getEntityName().equals(element.trim())  ) {
	        					//System.out.println("       entity list  "+entity.getEntityName());
	        					listEntity.add(entity);
	        				}
	
	        			}            	
	                    
	
	                }            	
	            	
	            	
	            	SpringServices springServices = new SpringServices();
					springServices.setEntity(listEntity);
					
					springServices.setServiceName( item.getText(0) );
				
					
			        Map<String, String> mapRoot = new HashMap<String, String>();
			        mapRoot.put("serviceName", item.getText(0) );
			        ParseTemplate parseTemplate = new ParseTemplate();
			        
			        
					springServices.setServiceInterfacePackage( serviceInterfacePackage );
					springServices.setServiceInterfaceTemplateFile( serviceInterfaceTemplateFile );
					springServices.setServiceInterfacePattern( serviceInterfacePattern );
					springServices.setServiceInterfaceClassName( parseTemplate.loadTemplateFromString( serviceInterfacePattern, mapRoot) );
					springServices.setServiceInterfaceFileName( springServices.getServiceInterfaceClassName()+".java" );
					springServices.setServiceInterfaceSrcDir(srcDir);
					
					
					springServices.setDaoInterfacePackage( daoInterfacePackage );
					springServices.setDaoInterfaceTemplateFile( daoInterfaceTemplateFile );
					springServices.setDaoInterfacePattern( daoInterfacePattern );
					springServices.setDaoInterfaceClassName( parseTemplate.loadTemplateFromString( daoInterfacePattern, mapRoot) );
					springServices.setDaoInterfaceFileName( springServices.getDaoInterfaceClassName()+".java" );
					springServices.setDaoInterfaceSrcDir(srcDir);
					
					
					springServices.setServiceImplementationPackage( serviceImplementationPackage );
					springServices.setServiceImplementationTemplateFile( serviceImplementationTemplateFile );
					springServices.setServiceImplementationPattern( serviceImplementationPattern );
					springServices.setServiceImplementationClassName( parseTemplate.loadTemplateFromString( serviceImplementationPattern, mapRoot) );
					springServices.setServiceImplementationFileName( springServices.getServiceImplementationClassName()+".java" );
					springServices.setServiceImplementationSrcDir(srcDir);
					
					springServices.setDaoImplementationPackage( daoImplementationPackage );
					springServices.setDaoImplementationTemplateFile( daoImplementationTemplateFile );
					springServices.setDaoImplementationPattern( daoImplementationPattern );
					springServices.setDaoImplementationClassName( parseTemplate.loadTemplateFromString( daoImplementationPattern, mapRoot) );
					springServices.setDaoImplementationFileName( springServices.getDaoImplementationClassName()+".java" );
					springServices.setDaoImplementationSrcDir(srcDir);
					
					listSpringDef.add(springServices);
					
	            }
	        }
        
		} catch (CoreException e) {
			e.printStackTrace();
		} 
        
        
        return listSpringDef;
		
	}
	
	
	
	

	public void setEntityPatternPackages() {
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);		

		try {
			
			
			String temp = null;
			
			temp = proj.getPersistentProperty(new QualifiedName("", "ServiceNamePattern"));
			if (temp!=null)  {
				textServiceNamePattern.setText(temp);
			} else {
				textServiceNamePattern.setText("s${EntityName}");			    	
			}
			
			temp = proj.getPersistentProperty(new QualifiedName("", "ServiceInterfacePackage"));
			if (temp!=null)  {
				textServiceInterfacePackage.setText(temp);
			} else {
				textServiceInterfacePackage.setText("");			    	
			}	
			
			temp = proj.getPersistentProperty(new QualifiedName("", "ServiceImplementationPackage"));
			if (temp!=null)  {
				textServiceImplementationPackage.setText(temp);
			} else {
				textServiceImplementationPackage.setText("");			    	
			}				
			
			
			temp = proj.getPersistentProperty(new QualifiedName("", "DaoInterfacePackage"));
			if (temp!=null)  {
				textDaoInterfacePackage.setText(temp);
			} else {
				textDaoInterfacePackage.setText("");			    	
			}				
			
			temp = proj.getPersistentProperty(new QualifiedName("", "DaoImplementationPackage"));
			if (temp!=null)  {
				textDaoImplementationPackage.setText(temp);
			} else {
				textDaoImplementationPackage.setText("");			    	
			}				
			

		} catch (CoreException e) {
			e.printStackTrace();
		}		
		
		
	}
	
	
	public void setEntityGrid() {
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		

		//Utils utils = new Utils();
		//List<String> listSourceFolder = utils.getSourceFolder(proj);

		
		AnnotationsUtils annotationsUtils = new AnnotationsUtils();
		List<Entity> entityList = annotationsUtils.getFullEntitiesInfo( proj.getLocation().toString() );		
		
		
		

		
		SpringPersistProperties springDefPersist = new SpringPersistProperties(proj);
		List<SpringServices> listSpringDef = springDefPersist.loadSpringServices();
		
		
		for(Iterator<Entity> it = entityList.iterator(); it.hasNext(); ) {
			Entity s = it.next();
			
			
	        Map<String, String> mapRoot = new HashMap<String, String>();
	        mapRoot.put("EntityName", s.getEntityName());
	        
	        ParseTemplate parseTemplate = new ParseTemplate();
	        String serviceName = parseTemplate.loadTemplateFromString(textServiceNamePattern.getText(), mapRoot);
			
			
			tableItem1 = new TableItem(tableSpringServices, SWT.NONE); 
			tableItem1.setText( new String[] { serviceName, s.getEntityName() } );
			
			for(Iterator<SpringServices> itSpringDef = listSpringDef.iterator(); itSpringDef.hasNext(); ) {
				SpringServices springDef = (SpringServices) itSpringDef.next();
				
				//System.out.println( "springDef "+springDef.getServiceName());
				
				if (springDef.getServiceName().equals(serviceName) ) {
					tableItem1.setChecked(true);
				}
				
				List<Entity> listEntity = springDef.getEntity();
				for(Iterator<Entity> itEntity = listEntity.iterator(); itEntity.hasNext(); ) {
					Entity entity = (Entity) itEntity.next();
					//System.out.println( "    entities "+entity.getEntityName()+" ");
				}
			}			
			

			
			
		}			
		

		
		
		
	}
	
	

	public List<Template> getTemplatesOutputPathList() {
		List<Template> templateList = new ArrayList<Template>();
		
		TableItem[] items = tableTemplates.getItems();
		
		for (int i = 0; i < items.length; i++) {
			Template template = new Template();
			template.setId( Integer.parseInt(items[i].getText(2)) );
			template.setTemplatename( items[i].getText(0) );
			template.setOutputpath( items[i].getText(1) );
			
			templateList.add(template);
		}			
		
		return templateList;
	}
	
	/*
	public List<SpringDefinitions> getSpringDefinitions() {
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);		
		
		List<SpringDefinitions> listSpringDefinitions = new ArrayList<SpringDefinitions>();
		
		//  
		try {
			SpringDefinitions springDefinitions = new SpringDefinitions();
			springDefinitions.setPackage( proj.getPersistentProperty(new QualifiedName("", "ServiceInterfacePackage")) );
			springDefinitions.setTemplateFile( proj.getPersistentProperty(new QualifiedName("", "SpringServiceInterfaceTemplateFile")) );
			springDefinitions.setPattern( proj.getPersistentProperty(new QualifiedName("", "SpringServiceInterfacePattern")) );
			listSpringDefinitions.add(springDefinitions);
			
			springDefinitions = new SpringDefinitions();
			springDefinitions.setPackage( proj.getPersistentProperty(new QualifiedName("", "ServiceImplementationPackage")) );
			springDefinitions.setTemplateFile( proj.getPersistentProperty(new QualifiedName("", "SpringServiceImplementationTemplateFile")) );
			springDefinitions.setPattern( proj.getPersistentProperty(new QualifiedName("", "SpringServiceImplementationPattern")) );
			springDefinitions.setNestedDef(nestedDef);
			listSpringDefinitions.add(springDefinitions);
			
			springDefinitions = new SpringDefinitions();			
			springDefinitions.setPackage( proj.getPersistentProperty(new QualifiedName("", "DaoInterfacePackage")) );
			springDefinitions.setTemplateFile( proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOInterfaceTemplateFile")) );
			springDefinitions.setPattern( proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOInterfacePattern")) );
			listSpringDefinitions.add(springDefinitions);
			
			springDefinitions = new SpringDefinitions();			
			springDefinitions.setPackage( proj.getPersistentProperty(new QualifiedName("", "DaoImplementationPackage")) );
			springDefinitions.setTemplateFile( proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOImplementationTemplateFile")) );
			springDefinitions.setPattern( proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOImplementationPattern")) );
			listSpringDefinitions.add(springDefinitions);
		} catch (CoreException e) {
			e.printStackTrace();
		}		
		
	
		return listSpringDefinitions;
		
	}
	*/


	
	public List<SpringDefinitions> getSpringDefinitions() {
		SpringDefinitions springDefinitions;
		SpringDefinitions springNestedDefinitions;
		
		ProjectUtils projectUtils = new ProjectUtils();
		
		
		
		IProject proj = projectUtils.getProject(selection);		
		
		List<SpringDefinitions> listSpringDefinitions = new ArrayList<SpringDefinitions>();
		
		//  
		try {
			springNestedDefinitions = new SpringDefinitions();
			springNestedDefinitions.setPackage( proj.getPersistentProperty(new QualifiedName("", "ServiceInterfacePackage")) );
			springNestedDefinitions.setTemplateFile( proj.getPersistentProperty(new QualifiedName("", "SpringServiceInterfaceTemplateFile")) );
			springNestedDefinitions.setPattern( proj.getPersistentProperty(new QualifiedName("", "SpringServiceInterfacePattern")) );
			
			springDefinitions = new SpringDefinitions();
			springDefinitions.setPackage( proj.getPersistentProperty(new QualifiedName("", "ServiceImplementationPackage")) );
			springDefinitions.setTemplateFile( proj.getPersistentProperty(new QualifiedName("", "SpringServiceImplementationTemplateFile")) );
			springDefinitions.setPattern( proj.getPersistentProperty(new QualifiedName("", "SpringServiceImplementationPattern")) );
			springDefinitions.setSpringNestedDefinitions(springNestedDefinitions);
			listSpringDefinitions.add(springDefinitions);

			
			springNestedDefinitions = new SpringDefinitions();			
			springNestedDefinitions.setPackage( proj.getPersistentProperty(new QualifiedName("", "DaoInterfacePackage")) );
			springNestedDefinitions.setTemplateFile( proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOInterfaceTemplateFile")) );
			springNestedDefinitions.setPattern( proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOInterfacePattern")) );
			
			springDefinitions = new SpringDefinitions();			
			springDefinitions.setPackage( proj.getPersistentProperty(new QualifiedName("", "DaoImplementationPackage")) );
			springDefinitions.setTemplateFile( proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOImplementationTemplateFile")) );
			springDefinitions.setPattern( proj.getPersistentProperty(new QualifiedName("", "SpringJPADAOImplementationPattern")) );
			springDefinitions.setSpringNestedDefinitions(springNestedDefinitions);
			listSpringDefinitions.add(springDefinitions);
		} catch (CoreException e) {
			e.printStackTrace();
		}		
		
	
		return listSpringDefinitions;
		
	}



	public String getSelectTableName() {
		//return tableDBTables.items[0].getText(0);
		return tableSpringServices.getItem( tableSpringServices.getSelectionIndex() ).getText();
	}



	public String getTextServiceInterfacePackage() {
		return textServiceInterfacePackage.getText();
	}




	public String getTextServiceImplementationPackage() {
		return textServiceImplementationPackage.getText();
	}


	public String getTextDaoImplementationPackage() {
		return textDaoImplementationPackage.getText();
	}



	public String getTextDaoInterfacePackage() {
		return textDaoInterfacePackage.getText();
	}


	public String getTextServiceNamePattern() {
		return textServiceNamePattern.getText();
	}



}