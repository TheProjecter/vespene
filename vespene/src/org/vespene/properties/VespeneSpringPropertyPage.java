package org.vespene.properties;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.vespene.dao.DBConnection;
import org.vespene.daoh2.project.ProjectMetadata;
import org.vespene.daoh2.project.ProjectTemplatesDao;
import org.vespene.daoh2.template.TemplateDefsDao;
import org.vespene.orm.Db;
import org.vespene.orm.Relations;
import org.vespene.orm.Template;
import org.vespene.project.ProjectUtils;
import org.vespene.project.Utils;


public class VespeneSpringPropertyPage extends PropertyPage {

	private static final String PATH_TITLE = "Path:";

	private Group group1;
	private Label label1;

	private Button button4;
	
	private Label label7;
	private Button button3;
	private Text textkkk;
	private Label label6;
	private Button button2;
	private Label label2;
	private Label label5;
	private Button button1;
	private Label label4;
	private Label label3;
	

	private Text textSpringServiceInterfacePattern;
	private Text textSpringServiceInterfaceTemplateFile;
	
	
	private Text textSpringServiceImplementationPattern;
	private Text textSpringServiceImplementationTemplateFile;
	
	
	private Text textSpringJPADAOInterfacePattern;
	private Text textSpringJPADAOInterfaceTemplateFile;
	
	
	private Text textSpringJPADAOImplementationPattern;
	private Text textSpringJPADAOImplementationTemplateFile;
	
		
	
	
	
	
	
	private Text ownerText;
	
	
	

	
	
	
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
	public VespeneSpringPropertyPage() {
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
		
		
		

		group1 = new Group(composite, SWT.NONE);
		GridLayout group1Layout = new GridLayout();
		group1Layout.numColumns = 4;
		group1Layout.marginWidth = 6;
		group1Layout.horizontalSpacing = 4;
		group1.setLayout(group1Layout);
		GridData group1LData = new GridData();
		group1LData.horizontalAlignment = GridData.FILL;
		group1LData.verticalAlignment = GridData.FILL;
		group1LData.grabExcessVerticalSpace = true;
		group1LData.grabExcessHorizontalSpace = true;
		group1.setLayoutData(group1LData);
		

		label2 = new Label(group1, SWT.NONE);
		GridData label2LData = new GridData();
		label2.setLayoutData(label2LData);
		label2.setText("");
		
		label4 = new Label(group1, SWT.NONE);
		GridData label4LData = new GridData();
		label4LData.heightHint = 13;
		label4.setLayoutData(label4LData);
		label4.setText("File Pattern");
		

		label3 = new Label(group1, SWT.NONE);
		GridData label3LData = new GridData();
		label3LData.horizontalSpan = 2;
		label3.setLayoutData(label3LData);
		label3.setText("Template File");


		label1 = new Label(group1, SWT.NONE);
		label1.setText("Spring Service Interface");


		
		textSpringServiceInterfacePattern = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData text1LData = new GridData();
		text1LData.widthHint = 121;
		text1LData.heightHint = 13;
		
		textSpringServiceInterfacePattern.setLayoutData(text1LData);
		textSpringServiceInterfacePattern.setText("textSpringServiceInterfacePattern");


		textSpringServiceInterfaceTemplateFile = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData text2LData = new GridData();
		text2LData.grabExcessHorizontalSpace = true;
		text2LData.horizontalAlignment = GridData.FILL;
		textSpringServiceInterfaceTemplateFile.setLayoutData(text2LData);
		textSpringServiceInterfaceTemplateFile.setText("textSpringServiceInterfaceTemplateFile");
		
		
		Button button1 = new Button(group1, SWT.PUSH | SWT.CENTER);
		GridData button1LData = new GridData();
		
		button1.setLayoutData(button1LData);
		
		image = new Image(composite.getDisplay(), getClass().getResourceAsStream("/icons/threadgroup_obj.gif") );
		button1.setImage(image);
		button1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse("Select custom template", textSpringServiceInterfaceTemplateFile);
			}
		});	
		
			
				
		
		label5 = new Label(group1, SWT.NONE);
		GridData label5LData = new GridData();
		label5.setLayoutData(label5LData);
		label5.setText("Spring Service Implementation");


		textSpringServiceImplementationPattern = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData textSpringServiceImplementationLData = new GridData();
		textSpringServiceImplementationLData.widthHint = 121;
		textSpringServiceImplementationLData.heightHint = 13;
		textSpringServiceImplementationPattern.setLayoutData(textSpringServiceImplementationLData);
		textSpringServiceImplementationPattern.setText("textSpringServiceImplementationPattern");


		textSpringServiceImplementationTemplateFile = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData textSpringServiceImplementationFileLData = new GridData();
		textSpringServiceImplementationFileLData.grabExcessHorizontalSpace = true;
		textSpringServiceImplementationFileLData.horizontalAlignment = GridData.FILL;
		textSpringServiceImplementationTemplateFile.setLayoutData(textSpringServiceImplementationFileLData);
		textSpringServiceImplementationTemplateFile.setText("textSpringServiceImplementationTemplateFile");

		button2 = new Button(group1, SWT.PUSH | SWT.CENTER);
		GridData button2LData = new GridData();
		
		button2.setLayoutData(button2LData);
		
		image = new Image(composite.getDisplay(), getClass().getResourceAsStream("/icons/threadgroup_obj.gif") );
		button2.setImage(image);
		button2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse("Select custom template", textSpringServiceImplementationTemplateFile);
			}
		});			
		
		


		label6 = new Label(group1, SWT.NONE);
		GridData label6LData = new GridData();
		label6.setLayoutData(label6LData);
		label6.setText("Spring JPA DAO Interface");


		textSpringJPADAOInterfacePattern = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData textSpringJPADAOInterfaceLData = new GridData();
		textSpringJPADAOInterfaceLData.widthHint = 120;
		textSpringJPADAOInterfaceLData.heightHint = 13;
		textSpringJPADAOInterfacePattern.setLayoutData(textSpringJPADAOInterfaceLData);
		textSpringJPADAOInterfacePattern.setText("textSpringJPADAOInterfacePattern");


		textSpringJPADAOInterfaceTemplateFile = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData text3LData = new GridData();
		text3LData.horizontalAlignment = GridData.FILL;
		text3LData.grabExcessHorizontalSpace = true;
		textSpringJPADAOInterfaceTemplateFile.setLayoutData(text3LData);
		textSpringJPADAOInterfaceTemplateFile.setText("textSpringJPADAOInterfaceTemplateFile");

		button3 = new Button(group1, SWT.PUSH | SWT.CENTER);
		GridData button3LData = new GridData();
		
		button2.setLayoutData(button3LData);
		
		image = new Image(composite.getDisplay(), getClass().getResourceAsStream("/icons/threadgroup_obj.gif") );
		button3.setImage(image);
		button3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse("Select custom template", textSpringJPADAOInterfaceTemplateFile);
			}
		});				
		


		

		label7 = new Label(group1, SWT.NONE);
		GridData label7LData = new GridData();
		label7.setLayoutData(label7LData);
		label7.setText("Spring JPA DAO Implementation");


		textSpringJPADAOImplementationPattern = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData textSpringJPADAOImplementationLData = new GridData();
		textSpringJPADAOImplementationLData.widthHint = 120;
		textSpringJPADAOImplementationLData.heightHint = 13;
		textSpringJPADAOImplementationPattern.setLayoutData(textSpringJPADAOImplementationLData);
		textSpringJPADAOImplementationPattern.setText("textSpringJPADAOImplementationPattern");


		textSpringJPADAOImplementationTemplateFile = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData text4LData = new GridData();
		text4LData.grabExcessHorizontalSpace = true;
		text4LData.horizontalAlignment = GridData.FILL;
		textSpringJPADAOImplementationTemplateFile.setLayoutData(text4LData);
		textSpringJPADAOImplementationTemplateFile.setText("textSpringJPADAOImplementationTemplateFile");
		

		button4 = new Button(group1, SWT.PUSH | SWT.CENTER);
		GridData button4LData = new GridData();
		
		button2.setLayoutData(button4LData);
		
		image = new Image(composite.getDisplay(), getClass().getResourceAsStream("/icons/threadgroup_obj.gif") );
		button4.setImage(image);
		button4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse("Select custom template", textSpringJPADAOImplementationTemplateFile);
			}
		});				
				
		
		
		
		
		
		loadProperties();
		
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
		
		//GridData data = new GridData(GridData.FILL);
		//data.grabExcessHorizontalSpace = true;
		//data.horizontalAlignment = GridData.FILL;
		//composite.setLayoutData(data);

		//addFirstSection(composite);
		//addSeparator(composite);
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
	
	
	
	

	private void loadProperties() {
		
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "SpringServiceInterfacePattern"));
			textSpringServiceInterfacePattern.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textSpringServiceInterfacePattern.setText("");
		}		
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "SpringServiceInterfaceTemplateFile"));
			textSpringServiceInterfaceTemplateFile.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textSpringServiceInterfaceTemplateFile.setText("");
		}		
		
		
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "SpringServiceImplementationPattern"));
			textSpringServiceImplementationPattern.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textSpringServiceImplementationPattern.setText("");
		}
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "SpringServiceImplementationTemplateFile"));
			textSpringServiceImplementationTemplateFile.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textSpringServiceImplementationTemplateFile.setText("");
		}			

		
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "SpringJPADAOInterfacePattern"));
			textSpringJPADAOInterfacePattern.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textSpringJPADAOInterfacePattern.setText("");
		}	
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "SpringJPADAOInterfaceTemplateFile"));
			textSpringJPADAOInterfaceTemplateFile.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textSpringJPADAOInterfaceTemplateFile.setText("");
		}	
		
		
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "SpringJPADAOImplementationPattern"));
			textSpringJPADAOImplementationPattern.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textSpringJPADAOImplementationPattern.setText("");
		}	
		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", "SpringJPADAOImplementationTemplateFile"));
			textSpringJPADAOImplementationTemplateFile.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			textSpringJPADAOImplementationTemplateFile.setText("");
		}	
		

		
		
	}
	
	
	
	
	
	
	public boolean performOk() {

		
		
		
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "SpringServiceInterfacePattern"),textSpringServiceInterfacePattern.getText());
		} catch (CoreException e) {
			return false;
		}				
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "SpringServiceInterfaceTemplateFile"),textSpringServiceInterfaceTemplateFile.getText());
		} catch (CoreException e) {
			return false;
		}		
		
		
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "SpringServiceImplementationPattern"),textSpringServiceImplementationPattern.getText());
		} catch (CoreException e) {
			return false;
		}		
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "SpringServiceImplementationTemplateFile"),textSpringServiceImplementationTemplateFile.getText());
		} catch (CoreException e) {
			return false;
		}	
		
		
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "SpringJPADAOInterfacePattern"),textSpringJPADAOInterfacePattern.getText());
		} catch (CoreException e) {
			return false;
		}	
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "SpringJPADAOInterfaceTemplateFile"),textSpringJPADAOInterfaceTemplateFile.getText());
		} catch (CoreException e) {
			return false;
		}	
		
		
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "SpringJPADAOImplementationPattern"),textSpringJPADAOImplementationPattern.getText());
		} catch (CoreException e) {
			return false;
		}			
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", "SpringJPADAOImplementationTemplateFile"),textSpringJPADAOImplementationTemplateFile.getText());
		} catch (CoreException e) {
			return false;
		}	
		
		
	
		
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