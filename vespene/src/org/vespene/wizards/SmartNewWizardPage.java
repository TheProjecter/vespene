package org.vespene.wizards;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;


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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
//import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
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
import org.vespene.orm.Db;
import org.vespene.orm.Fields;
import org.vespene.orm.Relations;
import org.vespene.orm.RelationsContainner;
import org.vespene.orm.Tables;
import org.vespene.orm.Template;
import org.vespene.project.ProjectUtils;
import org.vespene.project.Sorter;
import org.vespene.properties.Config;
import org.vespene.properties.PojoConfig;






/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class SmartNewWizardPage extends WizardPage {

	
	private Group group1;
	private Group group3;
	private Group group2;
	private Table tableCustomRelations;
	private Table tableDBTables;
	private Table tableTemplates;
	private TableColumn tableColumn1;
	private TableItem tableItem1;
	





	private TabFolder tabFolder1;
	
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
	
//	private Display display;

	/**
	 * Constructor for SampleNewWizardPage.
	 * @param conn 
	 * 
	 * @param pageName
	 */
	public SmartNewWizardPage(ISelection selection, Connection connection, String projPath) {
		super("wizardPage");
		setTitle("Vespene Generator");
		setDescription("Generate Java Servlets and JavaScript Windows, Forms, Datasets.\nI wrote this little letter, because I had lazy to write much.");
		this.selection = selection;
		this.connection = connection;
		this.projPath = projPath;
		
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
		
		
		
		System.out.println("VepeneNewwizardPage createControl");
		

		
		
		TablesDao tablesDao = new TablesDao(connection);
		List<Tables> listTables = tablesDao.getTables();
		
	
		
//		PojoConfig pojoConfig = new PojoConfig();
		
		Composite container1 = new Composite(parent, SWT.NONE);
		
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
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
		
	
		
		{
			/*
			group1 = new Group(container1, SWT.NONE);
			GridLayout group1Layout = new GridLayout();
			group1Layout.makeColumnsEqualWidth = true;
			group1.setLayout(group1Layout);
			GridData group1LData = new GridData();
			group1LData.horizontalAlignment = GridData.FILL;
			group1LData.heightHint = 200;
			group1.setLayoutData(group1LData);
			*/
			
			group1 = new Group(container1, SWT.NONE);
			GridLayout group1Layout = new GridLayout();
			group1Layout.makeColumnsEqualWidth = true;
			group1.setLayout(group1Layout);
			GridData group1LData = new GridData();
			group1LData.horizontalAlignment = GridData.FILL;
			group1LData.verticalAlignment = GridData.FILL;
			group1LData.heightHint = 200;
			group1.setLayoutData(group1LData);
						
		

			{
				GridData table1LData = new GridData();
				table1LData.horizontalAlignment = GridData.FILL;
				table1LData.verticalAlignment = GridData.FILL;
				table1LData.grabExcessVerticalSpace = true;
				
				table1LData.grabExcessHorizontalSpace = true;
				
				tableDBTables = new Table(group1, SWT.BORDER | SWT.FULL_SELECTION);
				tableDBTables.setLayoutData(table1LData);
				tableDBTables.setHeaderVisible(true);
				tableDBTables.setLinesVisible(true);
				
		
				tableDBTables.setLayoutData(table1LData);		
				
				
				
				tableDBTables.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						if (combo!=null) {
						   combo.dispose();
						}
						TableItem a = tableDBTables.getItem( tableDBTables.getSelectionIndex() );
						tableCustomRelations.removeAll();
						
				        RelationsDao relationsDao = new RelationsDao(connection);
				        relationsDao.setTable( a.getText() );
						List<Relations> listRelationsFields = relationsDao.getRelations();
						
						
						ProjectRelationsDefsDao projectRelationsDefsDao = new ProjectRelationsDefsDao(projPath+"/.vespene/vespenedb"); 
						List<Relations> listRelationsDefsFields = projectRelationsDefsDao.loadRelationsDefs(a.getText());
						
						
						List<Relations> listResultRelationsFields = new ArrayList();
						
						
						for(Iterator<Relations> it = listRelationsFields.iterator(); it.hasNext(); ) {
							Relations relations = (Relations) it.next();
							
							System.out.println(	"* - "+relations.getPkfield()+" - "+relations.getPktable() );
						}		
						
						
						
						
						for(Iterator<Relations> it = listRelationsDefsFields.iterator(); it.hasNext(); ) {
							Relations relations = (Relations) it.next();
							
							if ( !listRelationsFields.contains(relations) ) {
								listResultRelationsFields.add(relations);
							}
						}	
						
						
						
						
						/*
						
						Boolean found = false;
						
						for(Iterator<Relations> it = listRelationsFields.iterator(); it.hasNext(); ) {
							Relations relations = (Relations) it.next();
							
							System.out.println(	"1 - "+relations.getPkfield()+" - "+relations.getPktable() );
							
							
							found = false;
							
							if ( !listRelationsDefsFields.isEmpty() ) {

								for(Iterator<Relations> itDefs = listRelationsDefsFields.iterator(); itDefs.hasNext(); ) {
									Relations relationsDefsFields = (Relations) itDefs.next();
									
									System.out.println(	"	2 - "+relationsDefsFields.getPkfield()+" - "+relationsDefsFields.getPktable() );
									
									if ( ( relations.getPkfield().equals(relationsDefsFields.getPkfield()) ) &&
									     ( relations.getPktable().equals(relationsDefsFields.getPktable()) ) &&
									     ( relations.getFkfield().equals(relationsDefsFields.getFkfield()) ) ) {
										System.out.println("bbbbbbbbbbbbbbbbbbbbb");
										found = true;
										listResultRelationsFields.add(relationsDefsFields);
										break;
							
									}								
									
									
								}
								
							}
							
							
							if (!found) {
								System.out.println("nao achou");
								listResultRelationsFields.add(relations);
							}
							
						}			
						*/			
						
						
						for(Iterator<Relations> it = listResultRelationsFields.iterator(); it.hasNext(); ) {
							Relations relations = (Relations) it.next();
							
							tableItem1 = new TableItem(tableCustomRelations, SWT.NONE); 
							tableItem1.setText(new String[] { relations.getTablename(), 
									relations.getFkfield(),
									relations.getPktable(), 
									relations.getPkfield(),								
									relations.getReturnfield(),
									relations.getCustomfieldname() } );

						}		
								
						
						
						
						

								
					}
				});
				
				
				
				{
					tableColumn1 = new TableColumn(tableDBTables, SWT.NONE);
					tableColumn1.setText("Tables");
					tableColumn1.setWidth(200);
					tableColumn1.setResizable(false);
					tableColumn1.setData(tableDBTables);
					
				}
				{
					
					
					for(Iterator<Tables> it = listTables.iterator(); it.hasNext(); ) {
						Tables tables = (Tables) it.next();
						
						tableItem1 = new TableItem(tableDBTables, SWT.NONE); 
						tableItem1.setText( tables.getName() );
					}
					

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
			
			
			tableTemplates = new Table(group2, SWT.BORDER | SWT.FULL_SELECTION);
			tableTemplates.setLayoutData(table1LData);
			tableTemplates.setHeaderVisible(true);
			tableTemplates.setLinesVisible(true);	
			
			final TableEditor editor = new TableEditor(tableTemplates);
			
		    // Use a mouse listener, not a selection listener, since we're interested
		    // in the selected column as well as row
			tableTemplates.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent event) {

					TableItem a = tableTemplates.getItem(tableTemplates.getSelectionIndex());


					// Dispose any existing editor
					Control old = editor.getEditor();
					if (old != null)
						old.dispose();

					// Determine where the mouse was clicked
					Point pt = new Point(event.x, event.y);

					// Determine which row was selected
					final TableItem item = tableTemplates.getItem(pt);
					if (item != null) {
						// Determine which column was selected
						int column = -1;
						for (int i = 0, n = tableTemplates.getColumnCount(); i < n; i++) {
							Rectangle rect = item.getBounds(i);
							if (rect.contains(pt)) {
								// This is the selected column
								column = i;
								break;
							}
						}


						if (column == 1) {
							
							final Button button = new Button(tableTemplates, SWT.PUSH );
							button.setText("...");
							//button.setSize(50, 50);
							
							button.setForeground(item.getForeground());
							button.setFocus();
							
							final int col = column;
							button.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									handleBrowse("Select "+item.getText(0)+" directory",item,col);
								}
							});							
							
							
							
							editor.minimumWidth = 18;
							editor.horizontalAlignment = SWT.RIGHT;
							
							editor.setEditor(button, item, column);
						
							
							
							
							/*
					        // Create the Text object for our editor
				            final Text text = new Text(tableTemplates, SWT.NONE);
				            text.setForeground(item.getForeground());

				            // Transfer any text from the cell to the Text control,
				            // set the color to match this row, select the text,
				            // and set focus to the control
				            text.setText(item.getText(column));
				            text.setForeground(item.getForeground());
				            text.selectAll();
				            text.setFocus();

				            // Recalculate the minimum width for the editor
				            editor.minimumWidth = 150; //text.getBounds().width;

				            // Set the control into the editor
				            editor.setEditor(text, item, column);

				            // Add a handler to transfer the text back to the cell
				            // any time it's modified
				            final int col = column;
				            text.addModifyListener(new ModifyListener() {
				              public void modifyText(ModifyEvent event) {
				                // Set the text of the editor's control back into the cell
				                item.setText(col, text.getText());
				              }
				            });		
				            */						
							

						}							
						
						
						
						
						
						
					}
				}
			});			
			
			
			
			
			tableColumn1 = new TableColumn(tableTemplates, SWT.NONE);
			tableColumn1.setText("Template");
			tableColumn1.setWidth(200);
			tableColumn1.setResizable(false);
			tableColumn1.setData(tableTemplates);
			
			tableColumn1 = new TableColumn(tableTemplates, SWT.NONE);
			tableColumn1.setText("Output Path");
			tableColumn1.setWidth(300);
			tableColumn1.setResizable(false);
			tableColumn1.setData(tableTemplates);
			
			tableColumn1 = new TableColumn(tableTemplates, SWT.NONE);
			tableColumn1.setText("id");
			tableColumn1.setWidth(0);
			tableColumn1.setResizable(false);
			tableColumn1.setData(tableTemplates);			
			
			
			
			
			
			String fullPath = projPath+"/.vespene/vespenedb";			
			ProjectTemplatesDao projectTemplatesDao = new ProjectTemplatesDao(fullPath);
			List<Template> templateProject = projectTemplatesDao.loadTemplatesProject();
			
			
			for(Iterator<Template> it = templateProject.iterator(); it.hasNext(); ) {
				Template template = (Template) it.next();
				
				TableItem tableItem = new TableItem(tableTemplates, SWT.NONE); 
				tableItem.setText(new String[] { template.getTemplatename(), template.getOutputpath(), template.getId().toString() } ); //, template.getTemplatefile(), template.getId().toString() } );
				
				if ( templateProject.contains(template) ) {
					tableItem.setChecked(true);	
				}
			}				
			

		}		
		
		
		
		
		
		

		{
			group3 = new Group(container1, SWT.NONE);
			GridLayout group3Layout = new GridLayout();
			
			group3Layout.numColumns  = 3;
			
			group3Layout.makeColumnsEqualWidth = true;
			group3.setLayout(group3Layout);
			GridData group3LData = new GridData();
			group3LData.horizontalAlignment = GridData.FILL;
			group3LData.verticalAlignment = GridData.FILL;
			group3LData.grabExcessVerticalSpace = true;
			group3LData.grabExcessHorizontalSpace = true;
			group3LData.horizontalSpan = 2;
			group3.setLayoutData(group3LData);
			
			
			
			
			
			
			{
				Button button9 = new Button(group3, SWT.PUSH | SWT.CENTER);
				GridData button9LData = new GridData();
				button9LData.horizontalAlignment = GridData.END;
				button9LData.horizontalSpan = 1;
				//button9LData.widthHint = 50;
				//button9LData.heightHint = 23;
				button9.setLayoutData(button9LData);
				button9.setText("Add Custom Relation");
				
				image = new Image(container1.getDisplay(), getClass().getResourceAsStream("/icons/databaseconn.png") );
				button9.setImage(image);
				button9.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						insertNewRelation();
					}
				});	
			}				
			
			
			
			{
				Button button9 = new Button(group3, SWT.PUSH | SWT.CENTER);
				GridData button9LData = new GridData();
				button9LData.horizontalAlignment = GridData.END;
				button9LData.horizontalSpan = 2;
				//button9LData.widthHint = 50;
				//button9LData.heightHint = 23;
				button9.setLayoutData(button9LData);
				button9.setText("Reload metadata def");
				
				image = new Image(container1.getDisplay(), getClass().getResourceAsStream("/icons/databaseconn.png") );
				button9.setImage(image);
				button9.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						//insertNewRelation();
					}
				});	
			}				
			
			
			

			{
				GridData table2LData = new GridData();
				table2LData.horizontalAlignment = GridData.FILL;
				table2LData.verticalAlignment = GridData.FILL;
				table2LData.grabExcessHorizontalSpace = true;
				table2LData.verticalSpan = 2;
				
				table2LData.horizontalSpan = 3;		
				
				
				table2LData.minimumHeight = 200;
				table2LData.grabExcessVerticalSpace = true;
				tableCustomRelations = new Table(group3, SWT.NONE | SWT.FULL_SELECTION);
				tableCustomRelations.setHeaderVisible(true);
				tableCustomRelations.setLinesVisible(true);
				tableCustomRelations.setLayoutData(table2LData);
				
				
				final TableEditor editor = new TableEditor(tableCustomRelations);
				
				 
				
			    // Use a mouse listener, not a selection listener, since we're interested
			    // in the selected column as well as row
				tableCustomRelations.addMouseListener(new MouseAdapter() {
					public void mouseDown(MouseEvent event) {

						TableItem a = tableCustomRelations.getItem(tableCustomRelations.getSelectionIndex());

						FieldsDao fieldsDao = new FieldsDao();
						fieldsDao.setTablename(a.getText(2));
						fieldsDao.setSchemaname(a.getText(2));
						
						List<Fields> listFields = fieldsDao.getFields(connection);

						// Dispose any existing editor
						Control old = editor.getEditor();
						if (old != null)
							old.dispose();

						// Determine where the mouse was clicked
						Point pt = new Point(event.x, event.y);

						// Determine which row was selected
						final TableItem item = tableCustomRelations.getItem(pt);
						if (item != null) {
							// Determine which column was selected
							int column = -1;
							for (int i = 0, n = tableCustomRelations.getColumnCount(); i < n; i++) {
								Rectangle rect = item.getBounds(i);
								if (rect.contains(pt)) {
									// This is the selected column
									column = i;
									break;
								}
							}

							// Column 2 holds dropdowns
							if (column == 4) {
								// Create the dropdown and add data to it
								//final CCombo combo = new CCombo(tableDBRelations, SWT.READ_ONLY);
								
								combo = new CCombo(tableCustomRelations, SWT.READ_ONLY);

								for(Iterator<Fields> it = listFields.iterator(); it.hasNext(); ) {
									Fields fields = (Fields) it.next();
									combo.add( fields.getFieldname() );
								}
								
								

								// Select the previously selected item from the cell
								combo.select(combo.indexOf(item.getText(column)));

								// editor.minimumWidth = combo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
								editor.minimumWidth = 150;
								tableCustomRelations.getColumn(column).setWidth(editor.minimumWidth);

								// Set the focus on the dropdown and set into
								// the editor
								combo.setFocus();
								editor.setEditor(combo, item, column);

								// Add a listener to set the selected item back
								// into the cell
								final int col = column;
								combo.addSelectionListener(new SelectionAdapter() {
											public void widgetSelected(SelectionEvent event) {
												item.setText(col, combo.getText());
												
												//item.setText(4, "lookup"+combo.getText());
												
												item.setText(5, "lookup"+combo.getText().substring(0, 1).toUpperCase() + combo.getText().substring(1).toLowerCase());
												
												
												// They selected an item; end
												// the editing session
												combo.dispose();
											}
										});
							}
							
														

							if (column == 5) {
								
								
						        // Create the Text object for our editor
					            final Text text = new Text(tableCustomRelations, SWT.NONE);
					            text.setForeground(item.getForeground());

					            // Transfer any text from the cell to the Text control,
					            // set the color to match this row, select the text,
					            // and set focus to the control
					            text.setText(item.getText(column));
					            text.setForeground(item.getForeground());
					            text.selectAll();
					            text.setFocus();

					            // Recalculate the minimum width for the editor
					            editor.minimumWidth = 150; //text.getBounds().width;

					            // Set the control into the editor
					            editor.setEditor(text, item, column);

					            // Add a handler to transfer the text back to the cell
					            // any time it's modified
					            final int col = column;
					            text.addModifyListener(new ModifyListener() {
					              public void modifyText(ModifyEvent event) {
					                // Set the text of the editor's control back into the cell
					                item.setText(col, text.getText());
					              }
					            });								
								

							}							
							
							
							
							
							
							
						}
					}
				});
				
				
		
				
			}
			

			
			{
				tableColumn1 = new TableColumn(tableCustomRelations, SWT.NONE);
				tableColumn1.setText("Table name");
				tableColumn1.setWidth(150);
				tableColumn1.setResizable(true);
				tableColumn1.setData(tableCustomRelations);
				tableColumn1.setWidth(0);
				
				
				tableColumn1 = new TableColumn(tableCustomRelations, SWT.NONE);
				tableColumn1.setText("On Field(s)");
				tableColumn1.setWidth(150);
				tableColumn1.setResizable(true);
				tableColumn1.setData(tableCustomRelations);
				
				
				tableColumn1 = new TableColumn(tableCustomRelations, SWT.NONE);
				tableColumn1.setText("Foreign Table");
				tableColumn1.setWidth(150);
				tableColumn1.setResizable(true);
				tableColumn1.setData(tableCustomRelations);
				
				tableColumn1 = new TableColumn(tableCustomRelations, SWT.NONE);
				tableColumn1.setText("Foreign Field(s)");
				tableColumn1.setWidth(150);
				tableColumn1.setResizable(true);
				tableColumn1.setData(tableCustomRelations);
				
				tableColumn1 = new TableColumn(tableCustomRelations, SWT.NONE);
				tableColumn1.setText("Return Field");
				tableColumn1.setWidth(150);
				tableColumn1.setResizable(false);
				tableColumn1.setData(tableCustomRelations);
				
				tableColumn1 = new TableColumn(tableCustomRelations, SWT.NONE);
				tableColumn1.setText("Custom field name");
				tableColumn1.setWidth(150);
				tableColumn1.setResizable(false);
				tableColumn1.setData(tableCustomRelations);
				
			}			
			
			
			
		}		
		
		

		
		initialize();
		dialogChanged();
		validateOutputPathList();
		setControl(container1);
		
		tableDBTables.setSelection(1);
		
		
		
		
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
				//containerServletText.setText(container.getFullPath().toString());
				
				System.out.println("initialize "+ container.getFullPath().toString() );
				
				//propertiesToForm();
			}
		}
		//fileText.setText("new_file.mpe");
		
		//ProjectUtils projectUtils = new ProjectUtils();
		//IProject proj = projectUtils.getProject(selection);
		
		//propertiesToForm();
		

		
		
		
		
		
		
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 * @param column 
	 */

	
	private void handleBrowse(String title, TableItem item, int column) {

		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
	
		try {
			

			
			IJavaProject javaProject = JavaCore.create(proj);

			
		
			
			
			SelectionDialog packages=JavaUI.createPackageDialog(getShell(), JavaCore.create(proj), IJavaElementSearchConstants.CONSIDER_REQUIRED_PROJECTS);
			packages.setTitle("Package selection");
			packages.setMessage("Choose a package:");
			packages.open();
			if (packages.getReturnCode()!=SelectionDialog.CANCEL) {
				PackageFragment pf=(PackageFragment) packages.getResult()[0];
				
				item.setText(column, pf.getElementName() );
				validateOutputPathList();
				
				//container.setText(pf.getElementName());

			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		

/*
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false, title);
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				item.setText(column, ((Path) result[0]).toString() );
				validateOutputPathList();
			}
		}
		*/
		
	}
	
	
	/*
	private void handleBrowse(String title, Text container) {
		
		ResourceSelectionDialog dialog2 = new ResourceSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), title);
		
		
		if (dialog2.open() == ResourceSelectionDialog.OK) {
			Object[] result = dialog2.getResult();
			if (result.length == 1) {
				container.setText(((Path) result[0]).toString());
				
				pojoConfig.setTextServletClass(((Path) result[0]).toString());
				
			}
		}
	}	
	*/
	
	
	
	
	/*
	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
				"Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerServletText.setText(((Path) result[0]).toString());
			}
		}
	}
	*/
	
	
	
	
	
	private void validateOutputPathList() {
		IResource container = null; 
		
		List<Template> templateList = getTemplatesOutputPathList();
		
		for(Iterator<Template> it = templateList.iterator(); it.hasNext(); ) {
			Template template = (Template) it.next();
			
			container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(template.getOutputpath()));	
			
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
		IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getContainerName()));
		
	
		
		//System.out.println("dialogChanged "+getTextServletClass().getText() );
		
		//IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getContainerName()));
		//String fileName = getFileName();

		/*
		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		*/
		/*
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("mpe") == false) {
				updateStatus("File extension must be \"mpe\"");
				return;
			}
		}
		*/
		updateStatus(null);
	}
	
	
	
	
	
	
	/*
	private void propertiesToForm() {
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		Config config = new Config();
		config.setProj(proj);
		PojoConfig pojoConfig = config.loadConfig();
		
		textServletClass.setText( pojoConfig.getPathServletClass() );
		textExtendedPojoClass.setText( pojoConfig.getPathExtendedPojoClass() );
		textQueryClass.setText( pojoConfig.getPathQueryClass() );
		textSearchClass.setText( pojoConfig.getPathSearchClass() );
		textDatasetView.setText( pojoConfig.getPathDatasetView() );
		textWindowView.setText( pojoConfig.getPathWindowView() );
		textSearchView.setText( pojoConfig.getPathSearchView() );
	}
	
	public PojoConfig formToProperties() {
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		PojoConfig pojoConfig = new PojoConfig();
		
		
		pojoConfig.setPathServletClass( textServletClass.getText() );
		pojoConfig.setPathExtendedPojoClass( textExtendedPojoClass.getText() );
		pojoConfig.setPathQueryClass( textQueryClass.getText() );
		pojoConfig.setPathSearchClass( textSearchClass.getText() );
		pojoConfig.setPathDatasetView( textDatasetView.getText() );
		pojoConfig.setPathWindowView( textWindowView.getText() );
		pojoConfig.setPathSearchView( textSearchView.getText() );
		
		Config config = new Config();
		config.setProj(proj);
		config.persistConfig(pojoConfig);
		
		return pojoConfig;
	}
*/
	
	
	
	private void insertNewRelation() {
		TableItem[] item = tableCustomRelations.getSelection();
		
		
		for (int i = 1; i < item.length; i++) {
			System.out.println("for "+item[i]);
		}		
		
		System.out.println("item[0].getText(0) "+item[0].getText(0)  );
		System.out.println("item[0].getText(1) "+item[0].getText(1)  );
		System.out.println("item[0].getText(2) "+item[0].getText(2)  );
		System.out.println("item[0].getText(3) "+item[0].getText(3)  );
		System.out.println("item[0].getText(4) "+item[0].getText(4)  );
		
		TableItem tableItem = new TableItem(tableCustomRelations, SWT.NONE, tableCustomRelations.getSelectionIndex()+1);
		

		
		
		tableItem.setText(new String[] { item[0].getText(0), 
				item[0].getText(1), 
				item[0].getText(2), 
				item[0].getText(3), 
				item[0].getText(4) } );
				
		
		//tableItem.setData(item);
		
		
		/*		
		tableItem1 = new TableItem(tableDBRelations, SWT.NONE); 
		tableItem1.setText(new String[] { relationsFields.getPkcolumn_name(), 
				relationsFields.getPktable_name(), 
				relationsFields.getFkcolumn_name(), 
				"...", 
				"lookup" } );
				*/
		
		
	
		//for (int i = 1; i < item.length; i++) {
		//	item[i]
		//}
		
		


		
	}
	
	

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return "AAA"; //containerServletText.getText();
	}


	
	public Table getTableDBRelations() {
		return tableCustomRelations;
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
	
	
	
	
	public List<Relations> getCustomRelations() {
		List<Relations> relationsList = new ArrayList<Relations>();
		
		TableItem[] items = tableCustomRelations.getItems();
		
		for (int i = 0; i < items.length; i++) {
			Relations relations = new Relations();
			
			relations.setTablename( items[i].getText(0).toLowerCase() );
			relations.setFkfield( items[i].getText(1).toLowerCase() );
			relations.setPktable( items[i].getText(2).toLowerCase() );
			relations.setPkfield( items[i].getText(3).toLowerCase() );
			relations.setReturnfield( items[i].getText(4).toLowerCase() );
			relations.setCustomfieldname( items[i].getText(5).toLowerCase() );

			
			relationsList.add( relations );	
					
		}			
		
		return relationsList;		
		
		
		
		
		
	}



	public String getSelectTableName() {
		//return tableDBTables.items[0].getText(0);
		return tableDBTables.getItem( tableDBTables.getSelectionIndex() ).getText();
	}
	
	
	

	/*
	public String getFileName() {
		return fileText.getText();
	}
	*/
}