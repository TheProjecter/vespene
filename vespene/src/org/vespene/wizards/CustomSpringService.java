package org.vespene.wizards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.vespene.project.ProjectUtils;
import org.vespene.project.Utils;
import org.vespene.properties.SpringProperties;
import org.vespene.spring.model.Entity;
import org.vespene.spring.model.SpringDefinitions;
import org.vespene.spring.model.SpringServices;


public class CustomSpringService {
	
	private Text textServiceInterface;
	private Table tableCustomSpringServices;
	private Table tableSpringServices;
	private List<Entity> entityList;
	
	
	
	public CustomSpringService(Table tableSpringServices, List<Entity> entityList) {
		this.tableSpringServices = tableSpringServices;
		this.entityList = entityList;
		
	}
	
	



	

	public void show(Composite parent) {
        Display display = parent.getDisplay();
        final Shell shell = new Shell(display, SWT.TITLE | SWT.APPLICATION_MODAL | SWT.CLOSE | SWT.CENTER);
        
        //Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        
        
		Image image = new Image(parent.getDisplay(), getClass().getResourceAsStream("/icons/add_obj.gif") );
        shell.setImage(image);
        
        shell.setSize (300, 400);
        
        Monitor primary = display.getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = shell.getBounds();
        
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        
        shell.setLocation(x, y);        
        
        shell.setText("Custom Spring Service");

        
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		shell.setLayout(layout);        
       


		Label label1 = new Label(shell, SWT.NONE);
		label1.setText("Service name");

		textServiceInterface = new Text(shell, SWT.SINGLE | SWT.BORDER);
		GridData text1LData = new GridData();
		text1LData.horizontalAlignment = GridData.FILL;
		text1LData.minimumWidth = 250;
		text1LData.grabExcessHorizontalSpace = true;
		textServiceInterface.setLayoutData(text1LData);
		textServiceInterface.setText("teste1");
		textServiceInterface.setOrientation(SWT.HORIZONTAL);
	     
        
		
        
      
		Group group1 = new Group(shell , SWT.NONE);
		GridLayout group1Layout = new GridLayout();
		group1Layout.numColumns = 1;
		group1.setLayout(group1Layout);
		GridData group1LData = new GridData();
		group1LData.horizontalAlignment = GridData.FILL;
		group1LData.verticalAlignment = GridData.FILL;
		group1LData.grabExcessHorizontalSpace = true;
		group1LData.grabExcessVerticalSpace = true;	
		//group1LData.horizontalSpan = 2;
		

		group1.setLayoutData(group1LData);        
		

		
		{
			GridData table1LData = new GridData();
			table1LData.horizontalSpan = 3;
			table1LData.horizontalAlignment = GridData.FILL;
			table1LData.verticalAlignment = GridData.FILL;
			table1LData.grabExcessVerticalSpace = true;
			
			table1LData.grabExcessHorizontalSpace = true;
			
			tableCustomSpringServices = new Table(group1, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
			tableCustomSpringServices.setLayoutData(table1LData);
			tableCustomSpringServices.setHeaderVisible(true);
			tableCustomSpringServices.setLinesVisible(true);
			
	
			tableCustomSpringServices.setLayoutData(table1LData);		
			
			TableColumn tableColumn1;
			
			{
				tableColumn1 = new TableColumn(tableCustomSpringServices, SWT.NONE);
				tableColumn1.setText("Entity");
				tableColumn1.setWidth(200);
				tableColumn1.setResizable(false);
				tableColumn1.setData(tableCustomSpringServices);
				
			}
			{
				
				for(Iterator<Entity> it = entityList.iterator(); it.hasNext(); ) {
					Entity s = (Entity) it.next();
					
					TableItem tableItem1 = new TableItem(tableCustomSpringServices, SWT.NONE); 
					tableItem1.setText( new String[] { s.getEntityName() } );
				}					

			}				
		}		
		
		
		

		{
			GridData compositeTesteLData = new GridData();
			compositeTesteLData.heightHint = 45;
			compositeTesteLData.horizontalAlignment = GridData.END;
			Composite compositeTeste = new Composite(shell, SWT.NONE);
			GridLayout compositeTesteLayout = new GridLayout();
			compositeTesteLayout.numColumns = 2;
			compositeTeste.setLayout(compositeTesteLayout);
			compositeTeste.setLayoutData(compositeTesteLData);
			{
				Button button14 = new Button(compositeTeste, SWT.PUSH | SWT.CENTER);
				GridData button14LData = new GridData();
				button14LData.grabExcessHorizontalSpace = true;
				button14LData.horizontalAlignment = GridData.END;
				button14LData.widthHint = 70;
				button14.setLayoutData(button14LData);
				button14.setText("OK");
				
				button14.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						updateSpringDefinitions();
						shell.dispose();
					}
				});					
				
			}
			{
				Button button15 = new Button(compositeTeste, SWT.PUSH | SWT.CENTER);
				GridData button15LData = new GridData();
				button15LData.horizontalAlignment = GridData.END;
				button15LData.grabExcessHorizontalSpace = true;
				button15LData.widthHint = 70;
				button15.setLayoutData(button15LData);
				button15.setText("Cancel");
				
				button15.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						shell.dispose();
					}
				});					
				
				
				
			}
		}		

		
        shell.open();
        
        
   
        
		
	}
	
	

	



	private void updateSpringDefinitions() {
        TableItem[] tableItemCustom = tableCustomSpringServices.getItems();

		Device device = Display.getCurrent();
		Color colorCustom = new Color (device, 122, 202, 255);


        TableItem tableItemSpringServices = new TableItem(tableSpringServices, SWT.NONE); 
		
        
        StringBuilder selectedServices = new StringBuilder();
        
		
        for (int i = 0; i < tableItemCustom.length; i++) {
            TableItem item = tableItemCustom[i];
            if (item.getChecked()) {
            	selectedServices.append(", "+item.getText());
//            	if (i==0) {
//            		selectedServices.append(item.getText());
//            	} else {
//            		selectedServices.append(", "+item.getText());
//            	}
            }
        }
        
        tableItemSpringServices.setText( new String[] { textServiceInterface.getText(), selectedServices.toString().substring(2), "true" } );
        tableItemSpringServices.setChecked(true);
        tableItemSpringServices.setBackground(colorCustom);
		
		
	}




	

}
