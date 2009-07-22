package org.vespene.wizards;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.vespene.project.AnnotationsUtils;
import org.vespene.project.ProjectUtils;
import org.vespene.properties.SpringProperties;
import org.vespene.spring.model.Entity;
import org.vespene.spring.model.SpringDefinitions;
import org.vespene.spring.model.SpringServices;





public class SpringNewWizard extends Wizard implements INewWizard {
	private SpringNewWizardPage page;
	private ISelection selection;
	private List<Entity> entityList;


	public SpringNewWizard() {
		super();
		System.out.println("SpringNewWizard");
		setNeedsProgressMonitor(true);
		setWindowTitle("New Vespene artifacts...");
	}
	


	public void addPages() {
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		AnnotationsUtils annotationsUtils = new AnnotationsUtils();
		entityList = annotationsUtils.getFullEntitiesInfo( proj.getLocation().toString() );		
		
		page = new SpringNewWizardPage(selection, entityList);
		addPage(page);
	}


	
	public boolean performFinish() {
		
		ProjectUtils projectUtils = new ProjectUtils();
		IProject proj = projectUtils.getProject(selection);
		
		page.MountSelectedServices();
	
		SpringProperties springProperties = new SpringProperties(proj);
		SpringDefinitions springDefinitions = springProperties.loadSpringDefinitions();
		
		
		
//		Job job = new Job("Vespene this creating the spring files...") {
//	    @Override
//	    protected IStatus run(IProgressMonitor monitor) {
	        
	        
//			try {
				
				List<SpringServices> listSpringServices = springDefinitions.getSpringServices();
				
				//monitor.beginTask("progress...", listSpringServices.size());
				
				for(Iterator<SpringServices> it = listSpringServices.iterator(); it.hasNext(); ) {
					final SpringServices springServices = (SpringServices) it.next();
					
					if ( springServices.getEnable() ) {
			        
						SpringGenJob springGenJob = new SpringGenJob("creating service interface for "+springServices.getServiceName());
						springGenJob.setSpringServices(springServices);
						springGenJob.setSrcDir(springServices.getServiceInterfaceSrcDir());
						springGenJob.set_package(springDefinitions.getServiceInterfacePackage());
						springGenJob.setFileName(springServices.getServiceInterfaceFileName());
						springGenJob.setTemplateFile(springDefinitions.getServiceInterfaceTemplateFile());
						springGenJob.schedule();
						
						springGenJob = new SpringGenJob("creating service implementation for "+springServices.getServiceName());
						springGenJob.setSpringServices(springServices);
						springGenJob.setSrcDir(springServices.getServiceImplementationSrcDir());
						springGenJob.set_package(springDefinitions.getServiceImplementationPackage());
						springGenJob.setFileName(springServices.getServiceImplementationFileName());
						springGenJob.setTemplateFile(springDefinitions.getServiceImplementationTemplateFile());
						springGenJob.schedule();
						
						springGenJob = new SpringGenJob("creating dao interface for "+springServices.getServiceName());
						springGenJob.setSpringServices(springServices);
						springGenJob.setSrcDir(springServices.getDaoInterfaceSrcDir());
						springGenJob.set_package(springDefinitions.getDaoInterfacePackage());
						springGenJob.setFileName(springServices.getDaoInterfaceFileName());
						springGenJob.setTemplateFile(springDefinitions.getDaoInterfaceTemplateFile());
						springGenJob.schedule();
						
						springGenJob = new SpringGenJob("creating dao implementation for "+springServices.getServiceName());
						springGenJob.setSpringServices(springServices);
						springGenJob.setSrcDir(springServices.getDaoImplementationSrcDir());
						springGenJob.set_package(springDefinitions.getDaoImplementationPackage());
						springGenJob.setFileName(springServices.getDaoImplementationFileName());
						springGenJob.setTemplateFile(springDefinitions.getDaoImplementationTemplateFile());
						springGenJob.schedule();
					
					}
					
				}				
				

//			} finally {
//				monitor.done();
//			}
//	        
//	        return Status.OK_STATUS;
//	    }
//	};

//	job.setPriority(Job.INTERACTIVE );
//	job.setUser(true);
//	job.schedule();
		
			
	
		/*
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				
				try {
					doFinish(listSpringServices, serviceInterfacePackage, monitor);
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
		*/
		return true;
	}






	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	

    
    

    
    
    
	
	
	
	
}