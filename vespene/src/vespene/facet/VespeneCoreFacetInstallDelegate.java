package vespene.facet;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

import vespene.project.Utils;

public class VespeneCoreFacetInstallDelegate implements IDelegate {

	public void execute(final IProject pj, final IProjectFacetVersion fv, final Object config, final IProgressMonitor monitor)	
		throws CoreException {
		
		

		System.out.println("VespeneCoreFacetInstallDelegate ****");
		
		monitor.beginTask("", 2);

		try {
			//final IFolder webInfLib = Utils.getWebInfLibDir(pj);

			//Utils.copyFromPlugin(new Path("libs/formgen-core.jar"), webInfLib
			//		.getFile("formgen-core.jar"));

			//monitor.worked(1);
			//Utils.registerFormGenServlet(pj);
			
			final IFolder webInfLib = Utils.getWebInfLibDir( pj );
			Utils.copyFromPlugin(new Path("lib/h2-1.1.111.jar.jar"), webInfLib
					.getFile("h2-1.1.111.jar.jar"));
			
			System.out.println("VespeneCoreFacetInstallDelegate copiou *******");
			
		
			

			monitor.worked(1);
		} finally {
			monitor.done();
		}
	}

}
