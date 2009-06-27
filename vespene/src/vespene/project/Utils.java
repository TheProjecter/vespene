package vespene.project;

/*
 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.IOException;
 import java.io.InputStream;

 import org.eclipse.core.resources.IFile;
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/*
 import org.eclipse.core.resources.IResource;
 import org.eclipse.core.runtime.CoreException;
 import org.eclipse.core.runtime.FileLocator;
 import org.eclipse.core.runtime.IPath;
 import org.eclipse.core.runtime.IProgressMonitor;
 import org.eclipse.core.runtime.NullProgressMonitor;
 import org.eclipse.jface.preference.IPreferenceStore;
 import org.eclipse.jface.preference.PreferenceDialog;
 import org.eclipse.jface.preference.PreferenceManager;
 import org.eclipse.jface.preference.PreferenceNode;
 import org.eclipse.jface.resource.ImageDescriptor;
 import org.eclipse.jface.viewers.ISelection;
 import org.eclipse.jface.viewers.IStructuredSelection;
 import org.eclipse.jface.viewers.TreeViewer;
 */

//import org.eclipse.jst.j2ee.web.componentcore.util.WebArtifactEdit;
//import org.eclipse.jst.j2ee.webapplication.WebApp;
//import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.osgi.framework.Bundle;


//import org.osgi.framework.Bundle;

public class Utils {
	
	
	public String packageToDirectory(String s) {
		return s.replaceAll("\\.", "/");  
	}
	
	
    
    public String directoryToPackage(String s) {
    	return s.replaceAll("\\/", ".");
    }	
	
	

	public static IFolder getWebInfLibDir(final IProject pj) {
		final IVirtualComponent vc = ComponentCore.createComponent(pj);
		final IVirtualFolder vf = vc.getRootFolder().getFolder("WEB-INF/lib");
		return (IFolder) vf.getUnderlyingFolder();
	}

	public static void copyFromPlugin(final IPath src, final IFile dest)
			throws CoreException {
		try {
			
			System.out.println( "src.toFile().getAbsolutePath() "+src.toFile().getAbsolutePath() );
			System.out.println( "dest.getFullPath().toFile()    "+dest.getFullPath().toFile().getAbsolutePath() );
			
			final Bundle bundle = ResourcesPlugin.getPlugin().getBundle(); // InfoglueConnectorPlugin.getDefault().getBundle();
			final InputStream in = FileLocator.openStream(bundle, src, false);
			dest.create(in, true, null);
		} catch (IOException e) {
			// throw new CoreException( FormGenPlugin.createErrorStatus(
			// e.getMessage(), e ) );
		}
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public String PluginPath() {
		
		URL relativeURL = Platform.getBundle("vespene").getEntry("/");
		URL localURL = null;
		try {
			localURL = Platform.asLocalURL(relativeURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return localURL.getPath();
	}
	
	
	
	public List<String> getSourceFolder(IProject proj) {
		IJavaProject jproj = JavaCore.create(proj);
		
		List<String> listSourceFolder = new ArrayList<String>();
		
		try {
			for( IClasspathEntry entry : jproj.getRawClasspath() ) {
			   if( entry.getEntryKind() == IClasspathEntry.CPE_SOURCE )  {
				   listSourceFolder.add( entry.getPath().toString() );
				  // System.out.println( "entry.getOutputLocation().toString() "+entry.getPath().toString() );
			   }
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return listSourceFolder;		
		
	}
	
	
	
	// http://www.bytemycode.com/snippets/snippet/793/
    public static final String search(String ad, String dir) {
        String res = null;
        File[] fs = new File(dir).listFiles();
        if (fs != null) {
            //System.out.println("" + fs.length);
            for (int i = 0; i < fs.length; i++) {
                if (fs[i].isFile()) {
                    if (ad.equals(fs[i].getName())) {
                        //System.out.println("Lo encontre " + fs[i].getAbsolutePath());
                        res = fs[i].getAbsolutePath();
                        break;
                    }
                }else{
                     res = search(ad, fs[i].getAbsolutePath());
                }//if
            }//for
        }
        return res;
    }//method	
    
    


	
	
	
	
	

}
