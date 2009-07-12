package org.vespene.properties;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.vespene.dao.DBConnection;
import org.vespene.daoh2.project.ProjectTemplatesDao;
import org.vespene.daoh2.template.TemplateDefsDao;
import org.vespene.orm.Template;
import org.vespene.project.Utils;


public class VespenePropertyPage extends PropertyPage {
	private Text textDriverClassPath;
	private Text textDriverClass;
	private Text textDatabaseURL;
	private Text textUserName;
	private Text textPassWord;
	
	
	private Table tableTemplates;
	
	private Image image;	
	
	
	

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public VespenePropertyPage() {
		super();
	}

	private void addFirstSection(Composite parent) {
		//Composite composite = createDefaultComposite(parent);

		//Label for path field
		//Label pathLabel = new Label(composite, SWT.NONE);
		//pathLabel.setText(PATH_TITLE);

		// Path text field
		//Text pathValueText = new Text(composite, SWT.WRAP | SWT.READ_ONLY);
		//pathValueText.setText(((IResource) getElement()).getFullPath().toString());
	}

	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	private void addSecondSection(Composite parent) {
		
		Composite composite = parent;
		final Shell shell = parent.getShell();

		
		Group group4 = new Group(composite, SWT.NONE);
		GridLayout group2Layout = new GridLayout();
		group2Layout.numColumns = 3;
		group4.setLayout(group2Layout);
		GridData group2LData = new GridData();
		group2LData.minimumWidth = 450;
		group2LData.horizontalAlignment = GridData.FILL;
		group2LData.verticalAlignment = GridData.FILL;
		group2LData.grabExcessHorizontalSpace = true;
		group4.setLayoutData(group2LData);
		group4.setText("Database Properties");		
		

		{
			Label label8 = new Label(group4, SWT.NONE);
			GridData label8LData = new GridData();
			label8.setLayoutData(label8LData);
			label8.setText("Driver Claspath");
		}
		
		{
			textDriverClassPath = new Text(group4, SWT.BORDER | SWT.SINGLE);
			GridData text8LData = new GridData();

			text8LData.horizontalAlignment = GridData.FILL;
			text8LData.minimumWidth = 250;
			text8LData.grabExcessHorizontalSpace = true;
			
			text8LData.widthHint = 150;
			text8LData.heightHint = 13;
			textDriverClassPath.setLayoutData(text8LData);
			
		}
		{
			Button button8 = new Button(group4, SWT.PUSH | SWT.CENTER);
			GridData button8LData = new GridData();
			
			//button8LData.horizontalAlignment = GridData.END;
			
			button8.setLayoutData(button8LData);
			
			image = new Image(composite.getDisplay(), getClass().getResourceAsStream("/icons/jar.png") );
			button8.setImage(image);
			button8.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					handleBrowse("Select Driver ClassPath container", textDriverClassPath);
				}
			});	
		
			
		}
		{
			Label label9 = new Label(group4, SWT.NONE);
			GridData label9LData = new GridData();
			label9.setLayoutData(label9LData);
			label9.setText("Driver Class");
		}
		{
			textDriverClass = new Text(group4, SWT.BORDER | SWT.SINGLE);
			GridData text9LData = new GridData();
			text9LData.horizontalSpan = 2;
			text9LData.widthHint = 300;
			text9LData.heightHint = 13;
			text9LData.horizontalAlignment = GridData.FILL;
			textDriverClass.setLayoutData(text9LData);
		}
		{
			Label label10 = new Label(group4, SWT.NONE);
			GridData label10LData = new GridData();
			label10.setLayoutData(label10LData);
			label10.setText("Driver URL");
		}
		{
			textDatabaseURL = new Text(group4, SWT.BORDER | SWT.SINGLE);
			GridData text10LData = new GridData();
			text10LData.widthHint = 300;
			text10LData.heightHint = 13;
			text10LData.horizontalSpan = 2;
			text10LData.horizontalAlignment = GridData.FILL;
			textDatabaseURL.setLayoutData(text10LData);
		}
		{
			Label label11 = new Label(group4, SWT.NONE);
			GridData label11LData = new GridData();
			label11.setLayoutData(label11LData);
			label11.setText("User Name");
		}
		{
			textUserName = new Text(group4, SWT.BORDER | SWT.SINGLE);
			GridData text11LData = new GridData();
			text11LData.horizontalSpan = 2;
			text11LData.widthHint = 177;
			text11LData.heightHint = 13;
			textUserName.setLayoutData(text11LData);
			textUserName.setSize(177, 13);
		}
		{
			Label label12 = new Label(group4, SWT.NONE);
			GridData label12LData = new GridData();
			label12.setLayoutData(label12LData);
			label12.setText("Password");
		}
		{
			textPassWord = new Text(group4, SWT.BORDER | SWT.SINGLE);
			GridData text12LData = new GridData();
			text12LData.horizontalSpan = 2;
			text12LData.widthHint = 177;
			text12LData.heightHint = 13;
			textPassWord.setLayoutData(text12LData);
			textPassWord.setSize(177, 13);
		}
		{
			Button button9 = new Button(group4, SWT.PUSH | SWT.CENTER);
			GridData button9LData = new GridData();
			button9LData.horizontalAlignment = GridData.END;
			button9LData.horizontalSpan = 3;
			//button9LData.widthHint = 50;
			//button9LData.heightHint = 23;
			button9.setLayoutData(button9LData);
			button9.setText("Test");
			
			image = new Image(composite.getDisplay(), getClass().getResourceAsStream("/icons/databaseconn.png") );
			button9.setImage(image);
			button9.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					handleTestConnect(shell);
				}
			});	
		}		
		
		
				
		
		
		Group group3 = new Group(composite, SWT.NONE);
		GridLayout group3Layout = new GridLayout();
		group3Layout.makeColumnsEqualWidth = true;
		group3.setLayout(group3Layout);
		group3.setText("Avaliables Templates");
		
		GridData group3LData = new GridData();
		group3LData.horizontalAlignment = GridData.FILL;
		group3LData.verticalAlignment = GridData.FILL;
		group3LData.grabExcessVerticalSpace = true;
		group3LData.grabExcessHorizontalSpace = true;
		group3LData.horizontalSpan = 2;
		group3.setLayoutData(group3LData);

		{
			GridData table2LData = new GridData();
			table2LData.horizontalAlignment = GridData.FILL;
			table2LData.verticalAlignment = GridData.FILL;
			table2LData.grabExcessHorizontalSpace = true;
			table2LData.verticalSpan = 2;
			table2LData.minimumHeight = 200;
			table2LData.grabExcessVerticalSpace = true;
			
			


			tableTemplates = new Table(group3, SWT.CHECK | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
			tableTemplates.setHeaderVisible(true);
			tableTemplates.setLinesVisible(true);
			tableTemplates.setLayoutData(table2LData);
			
			tableTemplates.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		          //String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
		        }
		    });			

			
			TableColumn tableColumn1 = new TableColumn(tableTemplates, SWT.NONE);
			tableColumn1.setText("Template Name");
			tableColumn1.setWidth(200);
			tableColumn1.setResizable(true);
			tableColumn1.setData(tableTemplates);
			
			
			tableColumn1 = new TableColumn(tableTemplates, SWT.NONE);
			tableColumn1.setText("Template File");
			tableColumn1.setWidth(150);
			tableColumn1.setResizable(true);
			tableColumn1.setData(tableTemplates);
			
			tableColumn1 = new TableColumn(tableTemplates, SWT.NONE);
			tableColumn1.setText("File pattern");
			tableColumn1.setWidth(150);
			tableColumn1.setResizable(true);
			tableColumn1.setData(tableTemplates);	
			
			
			tableColumn1 = new TableColumn(tableTemplates, SWT.NONE);
			tableColumn1.setText("id");
			tableColumn1.setWidth(0);
			tableColumn1.setResizable(false);
			tableColumn1.setData(tableTemplates);
			
			
			
			
			Utils utils = new Utils();
			TemplateDefsDao templateDefsDao = new TemplateDefsDao();
			templateDefsDao.setFullPath( utils.PluginPath()+"/preferencesdb/vespenedb" );
			
			List<Template> templateList = templateDefsDao.loadTemplates();
			
			
			String fullPath = ((IResource) getElement()).getLocation().toString()+"/.vespene/vespenedb";			
			ProjectTemplatesDao projectTemplatesDao = new ProjectTemplatesDao(fullPath);
			List<Template> templateProject = projectTemplatesDao.loadTemplatesProject();
			
			
			for(Iterator<Template> it = templateList.iterator(); it.hasNext(); ) {
				Template template = (Template) it.next();
				
				TableItem tableItem = new TableItem(tableTemplates, SWT.NONE); 
				tableItem.setText(new String[] { template.getTemplatename(), template.getTemplatefile(), template.getOutfilepattern(), template.getId().toString() } );
				
				if ( templateProject.contains(template) ) {
					tableItem.setChecked(true);	
				}
			}						
			
	
			
		}		
		
		
		
		
				
		
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "DriverClassPath"));
			textDriverClassPath.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textDriverClassPath.setText("");
		}		
		
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "DriverClass"));
			textDriverClass.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textDriverClass.setText("");
		}			
		
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "DatabaseURL"));
			textDatabaseURL.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textDatabaseURL.setText("");
		}
		
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "UserName"));
			textUserName.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textUserName.setText("");
		}			

		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "PassWord"));
			textPassWord.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textPassWord.setText("");
		}			

	
		
	}

	
	/*
	ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(window.getShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
	dialog.setTitle("Tree Selection");
	dialog.setMessage("Select the elements from the tree:");
	dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
	dialog.open();
	*/
	
	
	private void handleBrowse(String title, Text container) {
		//ContainerSelectionDialog dialog = new ContainerSelectionDialog(
		//		getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
		//		title);
		
		
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		
//		ViewerFilter filter2 = new TypedViewerFilter(acceptedClasses, rejectedElements.toArray());

		//dialog.addFilter(filter);
		
		

		//ResourceSelectionDialog dialog = new ResourceSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), "Select Resource:");
		//dialog.setTitle("Resource Selection");		
				
		
			
		
		if (dialog.open() == ElementTreeSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			
			if (result != null && result.length > 0) {
				for (int i = 0; i < result.length; i++) {
					IFile file = (IFile) result[i];
					String jarFile = file.getProjectRelativePath().toString();
					textDriverClassPath.setText( jarFile );
				}
				
			}
			

		}
	}	
	
	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		

		addSecondSection(composite);
		return composite;
	}

	private Composite createDefaultComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

//	protected void performDefaults() {
		// Populate the owner text field with the default value
		//ownerText.setText(DEFAULT_OWNER);
//	}
	
	public boolean performOk() {
		// store the value in the owner text field
		/*
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", OWNER_PROPERTY),ownerText.getText());
		} catch (CoreException e) {
			return false;
		}
		*/
		
		
		
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "DriverClassPath"),textDriverClassPath.getText());
		} catch (CoreException e) {
			return false;
		}		
		
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "DriverClass"),textDriverClass.getText());

		} catch (CoreException e) {
			return false;
		}	
		
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "DatabaseURL"),textDatabaseURL.getText());
		} catch (CoreException e) {
			return false;
		}	
		
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "UserName"),textUserName.getText());
		} catch (CoreException e) {
			return false;
		}	
		
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "PassWord"),textPassWord.getText());
		} catch (CoreException e) {
			return false;
		}			
		
		
		String fullPath = ((IResource) getElement()).getLocation().toString()+"/.vespene/vespenedb";		
		//ProjectMetadata projectMetadata = new ProjectMetadata(fullPath);
		//projectMetadata.create();
		
		
		ProjectTemplatesDao projectTemplatesDao = new ProjectTemplatesDao(fullPath);
		projectTemplatesDao.deleteTemplatesProject();
		projectTemplatesDao.insertTemplatesProject( getSelectedTemplates(tableTemplates) );
		

		
		return true;
	}
	
	
	private void handleTestConnect(Shell shell) {
		//IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		
		String fullPath = ((IResource) getElement()).getLocation().toString();
		
		DBConnection dbConn = new DBConnection();
		
		Connection conn = null;
		try {
			conn = dbConn.loadDriver(fullPath+"/"+textDriverClassPath.getText(), textDriverClass.getText(), textDatabaseURL.getText(), textUserName.getText(), textPassWord.getText());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			MessageDialog.openError(shell, "Error", "Malformed URL");
		} catch (InstantiationException e) {
			e.printStackTrace();
			MessageDialog.openError(shell, "Error", "Instantiation");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			MessageDialog.openError(shell, "Error", "Illegal Access");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			MessageDialog.openError(shell, "Error", "Class not found");
		} catch (SQLException e) {
			e.printStackTrace();
			MessageDialog.openError(shell, "Error", "SQL Exception");
		}
		
		if (conn!=null) {
			MessageDialog.openInformation(shell, "Information", "Conection sucesfull");			
		}
		
	}
	
	
	
	public List<Template> getSelectedTemplates(Table table) {
        TableItem[] tableItem = table.getItems();
        List<Template> templateList = new ArrayList<Template>();
        
        for (int i = 0; i < tableItem.length; i++) {
            TableItem item = tableItem[i];
            if (item.getChecked()) {
            	Template template = new Template();
            	template.setId( Integer.parseInt( item.getText(3) ) );
            	template.setTemplatename( item.getText(0) );
            	template.setTemplatefile( item.getText(1) );
            	template.setOutfilepattern( item.getText(2) );
            	
            	templateList.add(template);
            }
        }
        return templateList;
		
		
		
	}
	
	
	public Object getCheckedElements(Table table) {
		TableItem[] children = table.getItems();
		ArrayList<Object> v = new ArrayList<Object>(children.length);
		for (int i = 0; i < children.length; i++) {
			TableItem item = children[i];
			if (item.getChecked()) {
				v.add(item.getData());
			}
		}
		return v.toArray();
	}	
	
	
	

}