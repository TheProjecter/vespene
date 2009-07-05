package org.vespene.preferences;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.vespene.Activator;
import org.vespene.dao.FieldsDao;
import org.vespene.daoh2.template.TemplateDefsDao;
import org.vespene.orm.Fields;
import org.vespene.orm.Relations;
import org.vespene.orm.Template;
import org.vespene.project.Utils;


/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

//public class VespenePreferencePage	extends FieldEditorPreferencePage	implements IWorkbenchPreferencePage {
	
public class VespenePreferencePage	extends PreferencePage	implements IWorkbenchPreferencePage {	

	public VespenePreferencePage() {
		super();
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		//setDescription("A demonstration of a preference page implementation");
		
		
		
		
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
/*
	public void createFieldEditors() {
		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
				"&Directory preference:", getFieldEditorParent()));
		addField(
			new BooleanFieldEditor(
				PreferenceConstants.P_BOOLEAN,
				"&An example of a boolean preference",
				getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(
				PreferenceConstants.P_CHOICE,
			"An example of a multiple-choice preference",
			1,
			new String[][] { { "&Choice 1", "choice1" }, {
				"C&hoice 2", "choice2" }
		}, getFieldEditorParent()));
		addField(
			new StringFieldEditor(PreferenceConstants.P_STRING, "A &text preference:", getFieldEditorParent()));
	}
	*/
	
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		//layout.verticalSpacing = 9;
		container.setLayout(layout);
		
		
		
		GridData composite1LData = new GridData();
		composite1LData.horizontalAlignment = GridData.FILL;
		composite1LData.verticalAlignment = GridData.FILL;
		composite1LData.grabExcessHorizontalSpace = true;
		composite1LData.grabExcessVerticalSpace = true;
		//composite1LData.minimumWidth = 600;
		//composite1LData.widthHint = 600;
		container.setLayoutData(composite1LData);
		
		
			Group group3 = new Group(container, SWT.NONE);
			GridLayout group3Layout = new GridLayout();
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
				GridData table2LData = new GridData();
				table2LData.horizontalAlignment = GridData.FILL;
				table2LData.verticalAlignment = GridData.FILL;
				table2LData.grabExcessHorizontalSpace = true;
				table2LData.verticalSpan = 2;
				table2LData.minimumHeight = 200;
				table2LData.grabExcessVerticalSpace = true;
				
				

				final Table tableTemplates = new Table(group3, SWT.CHECK | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
				tableTemplates.setHeaderVisible(true);
				tableTemplates.setLinesVisible(true);
				tableTemplates.setLayoutData(table2LData);
				/*
				tableTemplates.addListener(SWT.MeasureItem, new Listener() {
					public void handleEvent(Event event) {
						int clientWidth = tableTemplates.getClientArea().width;
						event.height = event.gc.getFontMetrics().getHeight() * 2;
						event.width = clientWidth * 2;
					}


				});
				*/

				

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
				
				
				
				
				Utils utils = new Utils();
				TemplateDefsDao templateDefsDao = new TemplateDefsDao();
				templateDefsDao.setFullPath( utils.PluginPath()+"/preferencesdb/vespenedb" );
				
				List<Template> templateList = templateDefsDao.loadTemplates();
				
				
				for(Iterator<Template> it = templateList.iterator(); it.hasNext(); ) {
					Template template = (Template) it.next();
					
					
					TableItem tableItem = new TableItem(tableTemplates, SWT.NONE); 
					tableItem.setText(new String[] { template.getTemplatename(), template.getTemplatefile(), template.getOutfilepattern() } );
					

					
					//System.out.println(	"* - "+template.getId()+" - "+template.getTemplatename() );
					
					 
				}						
				
				
				
				
				
				/*
				
				TableItem tableItem1 = new TableItem(tableDBRelations, SWT.NONE); 
				tableItem1.setText(new String[] { "Struts servlet","" } );
				
				tableItem1 = new TableItem(tableDBRelations, SWT.NONE); 
				tableItem1.setText(new String[] { "Extended Pojo class","" } );
				
				tableItem1 = new TableItem(tableDBRelations, SWT.NONE); 
				tableItem1.setText(new String[] { "Search class","" } );  
				
				tableItem1 = new TableItem(tableDBRelations, SWT.NONE); 
				tableItem1.setText(new String[] { "Query class","" } );
				
				tableItem1 = new TableItem(tableDBRelations, SWT.NONE); 
				tableItem1.setText(new String[] { "Extjs Dataset","" } );
				
				tableItem1 = new TableItem(tableDBRelations, SWT.NONE); 
				tableItem1.setText(new String[] { "Extjs window","" } );
				
				tableItem1 = new TableItem(tableDBRelations, SWT.NONE); 
				tableItem1.setText(new String[] { "Extjs search","" } );  
				*/
				
		
				
			}
		
		
			
			
			
			
			
			
			
			//System.out.println( "Activator.getDefault().getBundle().getLocation() "+Activator.getDefault().getBundle().getEntry("vespene") );
			
			//System.out.println( "Platform.getBundle(vespene).getEntry(/) "+Platform.getBundle("vespene").getEntry("/") );
			
			
			
			// URL to the root ("/") of the plugin-path:
			URL relativeURL = Platform.getBundle("vespene").getEntry("/");


			// Turn relative path to a local path with the help of Eclipse-platform:
			URL localURL = null;
			try {
				localURL = Platform.asLocalURL(relativeURL);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			// From this you can get the path
			String pluginDirString = localURL.getPath();
			
			//System.out.println( pluginDirString );
			
			//MessageDialog.openInformation(parent.getShell(), "Information", pluginDirString   );
			
			//MessageDialog.openInformation(parent.getShell(), "Information", Activator.getDefault().getBundle().getEntry("/") );
			
			
			
			//MessageDialog.openInformation(getShell(), "Information", Platform.getBundle("vespene").getEntry("/"));			
			
			
	
		
		
		return null;
	}
	
}