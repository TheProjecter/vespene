package org.vespene.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.vespene.spring.model.SpringDefinitions;


public class VespeneSpringPropertyPage extends PropertyPage {
	private Group group1;
	private Label label1;

	private Button button4;
	
	private Label label7;
	private Button button3;
	private Label label6;
	private Button button2;
	private Label label2;
	private Label label5;
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

	private Image image;	
	

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public VespeneSpringPropertyPage() {
		super();
	}



//	private void addSeparator(Composite parent) {
//		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
//		GridData gridData = new GridData();
//		gridData.horizontalAlignment = GridData.FILL;
//		gridData.grabExcessHorizontalSpace = true;
//		separator.setLayoutData(gridData);
//	}

	private void addSecondSection(Composite parent) {
		
		Composite composite = parent;
//		final Shell shell = parent.getShell();
		

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

		textSpringServiceInterfaceTemplateFile = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData text2LData = new GridData();
		text2LData.grabExcessHorizontalSpace = true;
		text2LData.horizontalAlignment = GridData.FILL;
		textSpringServiceInterfaceTemplateFile.setLayoutData(text2LData);
		
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

		textSpringServiceImplementationTemplateFile = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData textSpringServiceImplementationFileLData = new GridData();
		textSpringServiceImplementationFileLData.grabExcessHorizontalSpace = true;
		textSpringServiceImplementationFileLData.horizontalAlignment = GridData.FILL;
		textSpringServiceImplementationTemplateFile.setLayoutData(textSpringServiceImplementationFileLData);

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

		textSpringJPADAOInterfaceTemplateFile = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData text3LData = new GridData();
		text3LData.horizontalAlignment = GridData.FILL;
		text3LData.grabExcessHorizontalSpace = true;
		textSpringJPADAOInterfaceTemplateFile.setLayoutData(text3LData);

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

		textSpringJPADAOImplementationTemplateFile = new Text(group1, SWT.SINGLE | SWT.BORDER);
		GridData text4LData = new GridData();
		text4LData.grabExcessHorizontalSpace = true;
		text4LData.horizontalAlignment = GridData.FILL;
		textSpringJPADAOImplementationTemplateFile.setLayoutData(text4LData);

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

	

	
	
	private void handleBrowse(String title, Text container) {
		//ContainerSelectionDialog dialog = new ContainerSelectionDialog(
		//		getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
		//		title);
		
		
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		
		//ViewerFilter filter2 = new TypedViewerFilter(acceptedClasses, rejectedElements.toArray());

		//dialog.addFilter(filter);

		//ResourceSelectionDialog dialog = new ResourceSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), "Select Resource:");
		//dialog.setTitle("Resource Selection");		
				
		
			
		
		if (dialog.open() == ElementTreeSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			
			if (result != null && result.length > 0) {
				for (int i = 0; i < result.length; i++) {
					//IFile file = (IFile) result[i];
					//String jarFile = file.getProjectRelativePath().toString();
					//textDriverClassPath.setText( jarFile );
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

//	private Composite createDefaultComposite(Composite parent) {
//		Composite composite = new Composite(parent, SWT.NULL);
//		GridLayout layout = new GridLayout();
//		layout.numColumns = 2;
//		composite.setLayout(layout);
//
//		GridData data = new GridData();
//		data.verticalAlignment = GridData.FILL;
//		data.horizontalAlignment = GridData.FILL;
//		composite.setLayoutData(data);
//
//		return composite;
//	}

	protected void performDefaults() {
		// Populate the owner text field with the default value
		//ownerText.setText(DEFAULT_OWNER);
	}
	
	private void loadProperties() {
		IResource resource =  (IResource) getElement().getAdapter(IResource.class);

		SpringProperties springProperties = new SpringProperties( resource.getProject() );
		SpringDefinitions springDefinitions = springProperties.loadSpringDefinitions();
		
		textSpringServiceInterfacePattern.setText( (springDefinitions.getServiceInterfacePattern()!=null) ? springDefinitions.getServiceInterfacePattern() : "" );
		textSpringServiceInterfaceTemplateFile.setText( (springDefinitions.getServiceInterfaceTemplateFile()!=null) ? springDefinitions.getServiceInterfaceTemplateFile() : "" );
		
		textSpringServiceImplementationPattern.setText( (springDefinitions.getServiceImplementationPattern()!=null) ? springDefinitions.getServiceImplementationPattern() : "" );
		textSpringServiceImplementationTemplateFile.setText( (springDefinitions.getServiceImplementationTemplateFile()!=null) ? springDefinitions.getServiceImplementationTemplateFile() : "" );
		
		textSpringJPADAOInterfacePattern.setText( (springDefinitions.getDaoInterfacePattern()!=null) ? springDefinitions.getDaoInterfacePattern() : "" );
		textSpringJPADAOInterfaceTemplateFile.setText( (springDefinitions.getDaoInterfaceTemplateFile()!=null) ? springDefinitions.getDaoInterfaceTemplateFile() : "" );
		
		textSpringJPADAOImplementationPattern.setText( (springDefinitions.getDaoImplementationPattern()!=null) ? springDefinitions.getDaoImplementationPattern() : "" );
		textSpringJPADAOImplementationTemplateFile.setText( (springDefinitions.getDaoImplementationTemplateFile()!=null) ? springDefinitions.getDaoImplementationTemplateFile() : "" );
	}
	
	public boolean performOk() {
		IResource resource =  (IResource) getElement().getAdapter(IResource.class);

		SpringProperties springProperties = new SpringProperties( resource.getProject() );
		SpringDefinitions springDefinitions = springProperties.loadSpringDefinitions();
		
		springDefinitions.setServiceInterfacePattern( textSpringServiceInterfacePattern.getText() ); 
		springDefinitions.setServiceInterfaceTemplateFile( textSpringServiceInterfaceTemplateFile.getText() ); 
		
		springDefinitions.setServiceImplementationPattern( textSpringServiceImplementationPattern.getText() );
		springDefinitions.setServiceImplementationTemplateFile( textSpringServiceImplementationTemplateFile.getText() );
		
		springDefinitions.setDaoInterfacePattern( textSpringJPADAOInterfacePattern.getText() ); 
		springDefinitions.setDaoInterfaceTemplateFile( textSpringJPADAOInterfaceTemplateFile.getText() ); 
		
		springDefinitions.setDaoImplementationPattern( textSpringJPADAOImplementationPattern.getText() ); 
		springDefinitions.setDaoImplementationTemplateFile( textSpringJPADAOImplementationTemplateFile.getText() ); 
		
		springProperties.storeSpringDefinitions(springDefinitions);
		
		return true;
	}
	
	
}