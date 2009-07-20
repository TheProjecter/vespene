package org.vespene.project;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;

import org.apache.commons.io.FileUtils;
import org.vespene.spring.model.Entity;



public class AnnotationsUtils {

	public AnnotationsUtils() {
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Entity> getEntities(String directory)  {
		File rootDirName = new File(directory);
		Collection<File> listClassFiles = FileUtils.listFiles(rootDirName, new String[] { "class" }, true);
		
		List<Entity> listEntities = new ArrayList<Entity>();
		

		for (Iterator<File> iter = listClassFiles.iterator(); iter.hasNext();) {
			File entityFile = iter.next();
			
			File f = new File(entityFile.getAbsolutePath());
			InputStream is = null;
			try {
				is = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			DataInputStream dstream = new DataInputStream(new BufferedInputStream(is));

			ClassFile cf = null;
			try {
				cf = new ClassFile(dstream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String className = cf.getName(); // nome da classe + package
			
			
		
			
			AnnotationsAttribute visible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
			//AnnotationsAttribute invisible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.invisibleTag);
			
			//System.out.println("className "+ className );
			
			if (visible!=null) { 
				for (Annotation ann : visible.getAnnotations())	{
					 System.out.println("ann.getTypeName() "+ ann.getTypeName() );
				     if ("javax.persistence.Entity".equals( ann.getTypeName() ) ) {
				    	 
				    	 Entity entity = new Entity();
				    	 entity.setEntityName( className.substring( className.lastIndexOf(".")+1 ) );
				    	 entity.setAbsolutePath( entityFile.getAbsolutePath() );
				    	 
				    	 
				    	 listEntities.add(entity);
				    	 
				    	 
				     }
				}		
			}

		}
		return listEntities;
	}	
	
	
	
	

	
	@SuppressWarnings("unchecked")
	public List<String> getEntitiesInfo(String directory)  {
		File rootDirName = new File(directory);
		Collection<File> ormClassFiles = FileUtils.listFiles(rootDirName, new String[] { "class" }, true);
		List<String> classList = new ArrayList<String>();
		

		for (Iterator<File> iter = ormClassFiles.iterator(); iter.hasNext();) {
			File ormClass = iter.next();
			
			File f = new File(ormClass.getAbsolutePath());
			InputStream is = null;
			try {
				is = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			DataInputStream dstream = new DataInputStream(new BufferedInputStream(is));

			ClassFile cf = null;
			try {
				cf = new ClassFile(dstream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String className = cf.getName(); // nome da classe + package
			
			
			
			
			AnnotationsAttribute visible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
			//AnnotationsAttribute invisible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.invisibleTag);
			
			
			
			if (visible!=null) { 
				System.out.println("1 className "+ className );
				for (Annotation ann : visible.getAnnotations())	{
					 System.out.println("2        ann.getTypeName() "+ ann.getTypeName() );
				     if ("javax.persistence.Entity".equals( ann.getTypeName() ) ) {
				    	 classList.add( className.substring( className.lastIndexOf(".")+1 ) );
						
				    	 

					    	 
//							ClassPool cp = ClassPool.getDefault();
//							CtClass ctClass = null;
//							try {
//								ctClass = cp.get( cf.getSourceFile() );
//							} catch (NotFoundException e) {
//								e.printStackTrace();
//							}
//	
//					    	 
//	
//					    	 for (CtField field : ctClass.getDeclaredFields() ) {
//					    		 try {
//									for ( Object fann : field.getAnnotations() ) {
//										 System.out.println("3            field annotation "+ fann.toString() );
//									 }
//								} catch (ClassNotFoundException e) {
//									e.printStackTrace();
//								}
//					    		 
//					    		 
					    	 }
					    	 
				    	 
				    	 
//				    	 List listFields = cf.getFields();
//				    	 
//				    	 for(Iterator<FieldInfo> it = listFields.iterator(); it.hasNext(); ) {
//				    		 FieldInfo field = it.next();
//				    		 
//				    		 
//				    		 ConstPool constPool = field.getConstPool();
//				    		
//				    		 
//				    		 System.out.println("3              field name "+ field.getName() );
//				    		 System.out.println("4                  field attr "+ field.getAttributes().toString()  );
//
//				    		 
//				    		 
//				    		 for (int index = 1, size = constPool.getSize(); index < size; index++) {
//			    	             if ( constPool.getTag(index)==ConstPool.CONST_Fieldref ) {
//			    	            	 System.out.println("5                      field type "+ constPool.getFieldrefName(index) +" - "+constPool.getFieldrefType(index) );			    	            	 
//			    	             }
//			    	             
//			    	   
//				    		 }
//				    	 }

				    	 
				    	 
				    	 
			    	 List listFields = cf.getFields();
			    	 
			    	 for(Iterator<FieldInfo> it = listFields.iterator(); it.hasNext(); ) {
			    		 FieldInfo field = it.next();
			    		 
			    		 
//			    		 AnnotationsAttribute visible2 = (AnnotationsAttribute) field.getAttribute(AnnotationsAttribute.visibleTag);
//			    		 
//			    		 
//			 			if (visible2!=null) { 
//							System.out.println("AAAAAAAAAAAAAAAAAAAAAA "+ field.getName() );
//							System.out.println("AAAAAAAAAAAAAAAAAAAAAA "+ field.getDescriptor() );
//							System.out.println("AAAAAAAAAAAAAAAAAAAAAA "+ field.getClass() );
//							for (Annotation ann2 : visible2.getAnnotations())	{
//								 System.out.println("BBBBBBBBBBBBBBBBBBBBB "+ ann.getTypeName() );
//								 
//							}
//			 			}
			    		 
			    		 
			    		 
			    		 
			    		 ConstPool constPool = field.getConstPool();
			    		
			    		 
			    		 System.out.println("3              field name "+ field.getName() );
			    		 System.out.println("31             field type "+ field.getDescriptor() );
			    		 System.out.println("4              field attr "+ field.getAttributes().toString()  );
			    		 System.out.println("41             field attr "+ field.getAttribute("javax.persistence.EmbeddedId")  );
			    		 System.out.println("=========================="  );

			    		 
			    		 
			    		 for (int index = 1, size = constPool.getSize(); index < size; index++) {
		    	             if ( constPool.getTag(index)==ConstPool.CONST_Fieldref ) {
		    	            	 System.out.println("5                      field type "+ constPool.getFieldrefName(index) +" - "+constPool.getFieldrefType(index) );			    	            	 
		    	             }
		    	             
		    	   
			    		 }
				    	 
				     }
				}		
			}

		}
		return classList;
	}		
	
	
	
	
	//*************************************************************************************
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<String> getEntityInfo(String directory)  {
		File rootDirName = new File(directory);
		Collection<File> ormClassFiles = FileUtils.listFiles(rootDirName, new String[] { "class" }, true);
		List<String> classList = new ArrayList<String>();
		

		for (Iterator<File> iter = ormClassFiles.iterator(); iter.hasNext();) {
			File ormClass = iter.next();
			
			File f = new File(ormClass.getAbsolutePath());
			InputStream is = null;
			try {
				is = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			DataInputStream dstream = new DataInputStream(new BufferedInputStream(is));

			ClassFile cf = null;
			try {
				cf = new ClassFile(dstream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String className = cf.getName(); // nome da classe + package
			
			
			
			
			AnnotationsAttribute visible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
			//AnnotationsAttribute invisible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.invisibleTag);
			
			
			
			if (visible!=null) { 
				System.out.println("1 className "+ className );
				for (Annotation ann : visible.getAnnotations())	{
					 System.out.println("2        ann.getTypeName() "+ ann.getTypeName() );
				     if ("javax.persistence.Entity".equals( ann.getTypeName() ) ) {
				    	 classList.add( className.substring( className.lastIndexOf(".")+1 ) );
		    		 
			    	 }
					    	 
				    	 
				    	 
				    	 
			    	 List listFields = cf.getFields();
			    	 
			    	 for(Iterator<FieldInfo> it = listFields.iterator(); it.hasNext(); ) {
			    		 FieldInfo field = it.next();
			    		 
			    		 
//			    		 AnnotationsAttribute visible2 = (AnnotationsAttribute) field.getAttribute(AnnotationsAttribute.visibleTag);
//			    		 
//			    		 
//			 			if (visible2!=null) { 
//							System.out.println("AAAAAAAAAAAAAAAAAAAAAA "+ field.getName() );
//							System.out.println("AAAAAAAAAAAAAAAAAAAAAA "+ field.getDescriptor() );
//							System.out.println("AAAAAAAAAAAAAAAAAAAAAA "+ field.getClass() );
//							for (Annotation ann2 : visible2.getAnnotations())	{
//								 System.out.println("BBBBBBBBBBBBBBBBBBBBB "+ ann.getTypeName() );
//								 
//							}
//			 			}
			    		 
			    		 
			    		 
			    		 
			    		 ConstPool constPool = field.getConstPool();
			    		
			    		 
			    		 System.out.println("3              field name "+ field.getName() );
			    		 System.out.println("31             field type "+ field.getDescriptor() );
			    		 System.out.println("4              field attr "+ field.getAttributes().toString()  );
			    		 System.out.println("41             field attr "+ field.getAttribute("javax.persistence.EmbeddedId")  );
			    		 System.out.println("=========================="  );

			    		 
			    		 
			    		 for (int index = 1, size = constPool.getSize(); index < size; index++) {
		    	             if ( constPool.getTag(index)==ConstPool.CONST_Fieldref ) {
		    	            	 System.out.println("5                      field type "+ constPool.getFieldrefName(index) +" - "+constPool.getFieldrefType(index) );			    	            	 
		    	             }
		    	             
		    	   
			    		 }
				    	 
				     }
				}		
			}

		}
		return classList;
	}			
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Entity> getFullEntitiesInfo(String directory)  {
		File rootDirName = new File(directory);
		Collection<File> ormClassFiles = FileUtils.listFiles(rootDirName, new String[] { "class" }, true);
		List<Entity> classList = new ArrayList<Entity>();
		
		Utils utils = new Utils();
		
		for (Iterator<File> iter = ormClassFiles.iterator(); iter.hasNext();) {
			File entityClassFile = iter.next();
			
			File f = new File(entityClassFile.getAbsolutePath());
			InputStream is = null;
			try {
				is = new FileInputStream(f);
			} catch (FileNotFoundException e) { 
				e.printStackTrace();
			}
			
			DataInputStream dstream = new DataInputStream(new BufferedInputStream(is));

			ClassFile cf = null;
			try {
				cf = new ClassFile(dstream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String className = cf.getName(); // nome da classe + package
			
			
			
			
			AnnotationsAttribute visible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
			//AnnotationsAttribute invisible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.invisibleTag);
			
			
			
			if (visible!=null) { 
				
				for (Annotation ann : visible.getAnnotations())	{
					 
				     if ("javax.persistence.Entity".equals( ann.getTypeName() ) ) {
				    	 Entity entity = new Entity();
				    	 entity.setEntityName( className.substring( className.lastIndexOf(".")+1 ) );
				    	 entity.setEntityPackage( className );
				    	 entity.setAbsolutePath( entityClassFile.getAbsolutePath() );
				    	 
				    	 
				    	 List listFields = cf.getFields();
				    	 
				    	 for(Iterator<FieldInfo> it = listFields.iterator(); it.hasNext(); ) {
				    		FieldInfo fieldInfo = it.next();
				    		 
				    		Boolean foundId = false;
				    		
				    		 
				    		List fieldAttributesList = fieldInfo.getAttributes();
				    		for(Iterator<AttributeInfo> itFldAttr = fieldAttributesList.iterator(); itFldAttr.hasNext(); ) {
				    			AttributeInfo attr = itFldAttr.next();
				    			if ( "@javax.persistence.EmbeddedId".equals(attr.toString()) || "@javax.persistence.Id".equals(attr.toString()) ) {
				    				foundId = true;
				    				
				    				entity.setPkVar( fieldInfo.getName() );
				    				

				    				
						    		String descriptor = fieldInfo.getDescriptor();
						    		
						    		if (descriptor.equals("I") ) {
						    			entity.setPkType("int");
						    		} else if (descriptor.equals("S") ) {
						    			entity.setPkType("java.lang.Short");
						    		} else if (descriptor.equals("J") ) {
						    			entity.setPkType("java.lang.Long");
						    		} else if (descriptor.equals("B") ) {
						    			entity.setPkType("java.lang.Byte");
						    		} else if (descriptor.equals("D") ) {
						    			entity.setPkType("java.lang.Double");
						    		} else if (descriptor.equals("F") ) {
						    			entity.setPkType("java.lang.Float");
						    		} else if (descriptor.equals("C") ) {
						    			entity.setPkType("char");
						    		} else if (descriptor.equals("Z") ) {
						    			entity.setPkType("java.lang.Boolean");
						    		} else {
						    			entity.setPkType( utils.directoryToPackage( fieldInfo.getDescriptor().substring(1, fieldInfo.getDescriptor().lastIndexOf(";")) ) );
						    		}				    				

				    				break;
				    			}
				    		}
				    		
				    		
				    		if (foundId) {
				    			break;
				    		}
		 
					    	 
					     }				    	 
				    	 
				    	 
				  
				    	 
				    	 
				    	 classList.add(entity);
			    	 }
					    	 
				    	 


				    	 
				    	 
				    	 

			    	 
				}		
			}

		}
		return classList;
	}			
	
	
	
	
	
	
	
	
	
	
	
	

}
