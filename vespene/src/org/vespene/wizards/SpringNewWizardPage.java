package org.vespene.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.vespene.builder.VespeneNature;
import org.vespene.freemarker.ParseTemplate;
import org.vespene.project.AnnotationsUtils;
import org.vespene.project.ProjectUtils;
import org.vespene.project.Utils;
import org.vespene.properties.SpringProperties;
import org.vespene.spring.model.Entity;
import org.vespene.spring.model.SpringDefinitions;
import org.vespene.spring.model.SpringServices;




@SuppressWarnings("restriction")
public class SpringNewWizardPage extends WizardPage  {
	private ISelection selection;
	
	List<Entity> entityList;
	
	private Group group1;
	private Group group2;

	private Table tableSpringServices;
	private TableColumn tableColumn1;
	private TableItem tableItem1;
	
	private Text textServiceNamePattern;
	private Text textServiceInterfacePackage;
	private Text textServiceImplementationPackage;
	private Text textDaoImplementationPackage;
	private Text textDaoInterfacePackage;
	
	private Image image;
	

	public SpringNewWizardPage(ISelection selection, List<Entity> entityList) {
		super("wizardPage");
		setTitle("Vespene Generator");
		setDescription("Generate Spring DAO classes");
		this.selection = selection;
		this.entityList = entityList;
	}



	public void createControl(Composite parent) {
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		
		IProjectDescription description = null;
		try {
			description = proj.getDescription();
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
		String[] natures = description.getNatureIds();

		Boolean isVespeneNature = false;
		for (int i = 0; i < natures.length; ++i) {
			if (VespeneNature.NATURE_ID.equals(natures[i])) {
				isVespeneNature = true;
				break;
			} 
		}		
		
		if (!isVespeneNature) {
			MessageDialog.openInformation(parent.getShell(), "Information", "This project is not Vespene nature.");
			return;
		}
		
		
		Composite container1 = new Composite(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		container1.setLayout(layout);
		
		
		GridData composite1LData = new GridData();
		composite1LData.horizontalAlignment = GridData.FILL;
		composite1LData.verticalAlignment = GridData.FILL;
		composite1LData.grabExcessHorizontalSpace = true;
		composite1LData.grabExcessVerticalSpace = true;
		container1.setLayoutData(composite1LData);		
		
		{
			Group group4 = new Group(container1, SWT.NONE);
			GridLayout group1Layout = new GridLayout();
			group1Layout.numColumns = 3;
			group4.setLayout(group1Layout);
			GridData group4LData = new GridData();
			group4LData.horizontalAlignment = GridData.FILL;
			group4LData.verticalAlignment = GridData.FILL;
			group4LData.grabExcessHorizontalSpace = true;
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
						parseServiceName();
					}
				});				
				
				textServiceNamePattern.addFocusListener(new FocusAdapter() {
					public void focusLost(FocusEvent evt) {
						parseServiceName();
					}
				});				
				
			}			
			
			
		

		}			
		
		
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
				
				image = new Image(parent.getDisplay(), getClass().getResourceAsStream("/icons/add_obj.gif") );
				button12.setImage(image);
				
				final Composite p = parent;
				
				button12.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						CustomSpringService customSpringService = new CustomSpringService(tableSpringServices, entityList);
						customSpringService.show(p); 
					}
				});					
				
			}
			{
				Button button13 = new Button(group1, SWT.PUSH | SWT.CENTER);
				GridData button13LData = new GridData();
				button13.setLayoutData(button13LData);
				
				image = new Image(parent.getDisplay(), getClass().getResourceAsStream("/icons/remove_correction.gif") );
				button13.setImage(image);
				
				button13.addListener(SWT.Selection, new Listener() {
			        public void handleEvent(Event event) {
			        	TableItem item = tableSpringServices.getItem(tableSpringServices.getSelectionIndex());
			        	if ( Boolean.parseBoolean(item.getText(2)) ) {
			        		tableSpringServices.remove(tableSpringServices.getSelectionIndices());
			        	}
			        }
				});				
				
			}			
			{
				Button button14 = new Button(group1, SWT.PUSH | SWT.CENTER);
				GridData button13LData = new GridData();
				
				image = new Image(parent.getDisplay(), getClass().getResourceAsStream("/icons/custom.gif") );
				button14.setImage(image);	
				button14.setLayoutData(button13LData);
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
				}				
				
				{
					tableColumn1 = new TableColumn(tableSpringServices, SWT.NONE);
					tableColumn1.setText("Entity");
					tableColumn1.setWidth(200);
					tableColumn1.setResizable(false);
				}
				
				{
					tableColumn1 = new TableColumn(tableSpringServices, SWT.NONE);
					tableColumn1.setText("Custom");
					tableColumn1.setWidth(0);
					tableColumn1.setResizable(false);
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
		setTexts();
		dialogChanged();
		setControl(container1);
		setEntityGrid();
		tableSpringServices.setSelection(0);
	}



	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				@SuppressWarnings("unused")
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
			}
		}

	}

	
	
	private void handleBrowse(Text text, String msg) {

		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
	
		try {
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
	
	

	private void dialogChanged() {
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);

		
		Utils utils = new Utils();
		List<String> listSourceFolder = utils.getSourceFolder(proj);
		
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
            Boolean isValidDir = true;
    		for(Iterator<String> it = listSourceFolder.iterator(); it.hasNext(); ) {
    			String src = it.next();
    			
    			IResource containerPackage = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path( src+"/"+pair.getValue()  ));
    			
    			if (pair.getValue().equals("") || containerPackage == null || (containerPackage.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
    				isValidDir = false;
    			} else {
    				isValidDir = true;
    				break;
    			}
    		}
    		if (!isValidDir) {
    			updateStatus("Package for "+pair.getKey()+" must exist.");
    			return;
    		}
            
        }
        

		SpringProperties springProperties = new SpringProperties(proj);
		SpringDefinitions springDefinitions  = springProperties.loadSpringDefinitions();
		
		
		springDefinitions.setServiceNamePattern( textServiceNamePattern.getText() );
		springDefinitions.setServiceInterfacePackage( textServiceInterfacePackage.getText() );
		springDefinitions.setServiceImplementationPackage( textServiceImplementationPackage.getText() );
		springDefinitions.setDaoInterfacePackage( textDaoInterfacePackage.getText() );
		springDefinitions.setDaoImplementationPackage( textDaoImplementationPackage.getText() );
		springProperties.storeSpringDefinitions(springDefinitions);		
		
		updateStatus(null);
	}
	
	
	
	

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}


	
	
	public void parseServiceName() {
		TableItem[] tableItem = tableSpringServices.getItems();
		
        for (int i = 0; i < tableItem.length; i++) {
            TableItem item = tableItem[i];
            
            if ( !Boolean.parseBoolean(item.getText(2)) ) {
    	        Map<String, String> mapRoot = new HashMap<String, String>();
    	        mapRoot.put("EntityName", item.getText(1));
    	        
    	        ParseTemplate parseTemplate = new ParseTemplate();
    	        String serviceName = parseTemplate.loadTemplateFromString(textServiceNamePattern.getText(), mapRoot);
    			
    	        item.setText(0, serviceName);
            }
        }
		
		dialogChanged();
	}
	
	
	
	
	
	

	public void MountSelectedServices() {
        TableItem[] tableItem = tableSpringServices.getItems();
        List<SpringServices> listSpringServices = new ArrayList<SpringServices>();
        
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);		
        
        
		Utils utils = new Utils();
		List<String> listSourceFolder = utils.getSourceFolder(proj);
		
		String srcDir = null;
		for(Iterator<String> itSrc = listSourceFolder.iterator(); itSrc.hasNext(); ) {
			srcDir = itSrc.next();
			break;
		}
		
		
		
		SpringProperties springPersistProperties = new SpringProperties(proj);
		SpringDefinitions springDefinitions  = springPersistProperties.loadSpringDefinitions();
		
        for (int i = 0; i < tableItem.length; i++) {
            TableItem item = tableItem[i];
           	
            	List<Entity> listEntity = new ArrayList<Entity>();
            	
                StringTokenizer cpTokenizer = new StringTokenizer(item.getText(1), ",");
                while ( cpTokenizer.hasMoreElements() ) {
                    String element = cpTokenizer.nextToken();

        			for(Iterator<Entity> it = entityList.iterator(); it.hasNext(); ) {
        				Entity entity = (Entity) it.next();

        				if (entity.getEntityName().equals(element.trim())  ) {
        					listEntity.add(entity);
        				}
        			}            	
                }            	
            	
            	
            	SpringServices springServices = new SpringServices();
				springServices.setEntity(listEntity);
				
				springServices.setServiceName( item.getText(0) );
				springServices.setEnable( item.getChecked() );
		        springServices.setIsCustom( Boolean.parseBoolean(item.getText(2)) );
			
				
		        Map<String, String> mapRoot = new HashMap<String, String>();
		        mapRoot.put("serviceName", item.getText(0) );
		        ParseTemplate parseTemplate = new ParseTemplate();
		        
		        
				springServices.setServiceInterfacePackage( springDefinitions.getServiceInterfacePackage() );
				springServices.setServiceInterfaceClassName( parseTemplate.loadTemplateFromString( springDefinitions.getServiceInterfacePattern(), mapRoot) );
				springServices.setServiceInterfaceFileName( springServices.getServiceInterfaceClassName()+".java" );
				springServices.setServiceInterfaceSrcDir(srcDir);
				
				springServices.setDaoInterfacePackage( springDefinitions.getDaoInterfacePackage() );
				springServices.setDaoInterfaceClassName( parseTemplate.loadTemplateFromString( springDefinitions.getDaoInterfacePattern(), mapRoot) );
				springServices.setDaoInterfaceFileName( springServices.getDaoInterfaceClassName()+".java" );
				springServices.setDaoInterfaceSrcDir(srcDir);
				
				
				springServices.setServiceImplementationPackage( springDefinitions.getServiceImplementationPackage() );
				springServices.setServiceImplementationClassName( parseTemplate.loadTemplateFromString( springDefinitions.getServiceImplementationPattern(), mapRoot) );
				springServices.setServiceImplementationFileName( springServices.getServiceImplementationClassName()+".java" );
				springServices.setServiceImplementationSrcDir(srcDir);
				
				springServices.setDaoImplementationPackage( springDefinitions.getDaoImplementationPackage() );
				springServices.setDaoImplementationClassName( parseTemplate.loadTemplateFromString( springDefinitions.getDaoImplementationPattern(), mapRoot) );
				springServices.setDaoImplementationFileName( springServices.getDaoImplementationClassName()+".java" );
				springServices.setDaoImplementationSrcDir(srcDir);
				
				listSpringServices.add(springServices);
				
            }
     
       
		springDefinitions.setSpringServices(listSpringServices);
		
		SpringProperties springProperties = new SpringProperties(proj);
		springProperties.storeSpringDefinitions(springDefinitions);		
	}		
	
	
	
	


	public void setTexts() {
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);		

		SpringProperties springPersistProperties = new SpringProperties(proj);
		SpringDefinitions springDefinitions  = springPersistProperties.loadSpringDefinitions();
		
		textServiceNamePattern.setText( (springDefinitions.getServiceNamePattern()!=null) ? springDefinitions.getServiceNamePattern() : "s${EntityName}" );
		textServiceInterfacePackage.setText( (springDefinitions.getServiceInterfacePackage()!=null) ? springDefinitions.getServiceInterfacePackage() : "" );
		textServiceImplementationPackage.setText( (springDefinitions.getServiceImplementationPackage()!=null) ? springDefinitions.getServiceImplementationPackage() : "" );
		textDaoInterfacePackage.setText( (springDefinitions.getDaoInterfacePackage()!=null) ? springDefinitions.getDaoInterfacePackage() : "" );
		textDaoImplementationPackage.setText( (springDefinitions.getDaoImplementationPackage()!=null) ? springDefinitions.getDaoImplementationPackage() : "" );
	}
	
	

	
	
	
	public void setEntityGrid() {
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		AnnotationsUtils annotationsUtils = new AnnotationsUtils();
		List<Entity> entityList = annotationsUtils.getFullEntitiesInfo( proj.getLocation().toString() );		
		
		
		SpringProperties springProperties = new SpringProperties(proj);
		SpringDefinitions springDefinitions = springProperties.loadSpringDefinitions();
		List<SpringServices> listSpringServices = springDefinitions.getSpringServices();
		
		
		
		for(Iterator<Entity> it = entityList.iterator(); it.hasNext(); ) {
			Entity s = it.next();
			
	        Map<String, String> mapRoot = new HashMap<String, String>();
	        mapRoot.put("EntityName", s.getEntityName());
	        
	        ParseTemplate parseTemplate = new ParseTemplate();
	        
	        String serviceName = parseTemplate.loadTemplateFromString(textServiceNamePattern.getText(), mapRoot);
			
			
			tableItem1 = new TableItem(tableSpringServices, SWT.NONE); 
			tableItem1.setText( new String[] { serviceName, s.getEntityName(), "false" } );
			
			if (listSpringServices!=null) {
				for(Iterator<SpringServices> itSpringServices = listSpringServices.iterator(); itSpringServices.hasNext(); ) {
					SpringServices springServices = (SpringServices) itSpringServices.next();

					if (springServices.getServiceName().equals(serviceName) ) {
						tableItem1.setChecked( springServices.getEnable() );
					}
				}		
			}
			
		}
		
		
		if ( listSpringServices!=null ) {
			Device device = Display.getCurrent();
			Color customColor = new Color (device, 122, 202, 255);
			for(Iterator<SpringServices> itSpringServices = listSpringServices.iterator(); itSpringServices.hasNext(); ) {
				SpringServices springServices = (SpringServices) itSpringServices.next();
				
				if ( springServices.getIsCustom() ) {
					
					StringBuilder selectedServices = new StringBuilder();
					
					List<Entity> listEntity = springServices.getEntity();
					for(Iterator<Entity> itEntity = listEntity.iterator(); itEntity.hasNext(); ) {
						Entity entity = (Entity) itEntity.next();
						selectedServices.append( ", "+entity.getEntityName() );
					}				
					
					tableItem1 = new TableItem(tableSpringServices, SWT.NONE);
					tableItem1.setText( new String[] { springServices.getServiceName(), selectedServices.toString().substring(2) , "true" } );
					tableItem1.setChecked( springServices.getEnable() );
					tableItem1.setBackground(customColor);
					
				}
			}			
		}

		
	}	
	

//	public List<Template> getTemplatesOutputPathList() {
//		List<Template> templateList = new ArrayList<Template>();
//		
//		TableItem[] items = tableTemplates.getItems();
//		
//		
//		for (int i = 0; i < items.length; i++) {
//			Template template = new Template();
//			template.setId( Integer.parseInt(items[i].getText(2)) );
//			template.setTemplatename( items[i].getText(0) );
//			template.setOutputpath( items[i].getText(1) );
//			
//			templateList.add(template);
//		}			
//		
//		return templateList;
//	}
	






	public String getSelectTableName() {
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