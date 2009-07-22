package org.vespene.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.vespene.wizards.SpringNewWizard;

public class VespeneSpringAction implements IObjectActionDelegate {

	private Shell shell;
	private IWorkbenchPart targetPart;
	ISelection selection;
	
	/**
	 * Constructor for Action1.
	 */
	public VespeneSpringAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.shell = targetPart.getSite().getShell();
		this.targetPart = targetPart;
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		//ISelection selection = targetPart.getSite().getWorkbenchWindow().getActivePage().getSelection();
		
		SpringNewWizard springNewWizard = new SpringNewWizard();
		springNewWizard.init(targetPart.getSite().getWorkbenchWindow().getWorkbench(), (IStructuredSelection)selection);
	
		WizardDialog dialog = new WizardDialog(shell, springNewWizard);
		dialog.create();
		dialog.open();	
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
