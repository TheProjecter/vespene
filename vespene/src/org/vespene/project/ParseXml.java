package org.vespene.project;

import java.io.FileInputStream;
import java.io.FileWriter;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class ParseXml {
	
	
	
	public void readConfig(String fileName) {
		int count = 0;
		try {
			
			
			XMLOutputFactory xof =  XMLOutputFactory.newInstance();
			XMLStreamWriter xtw = xof.createXMLStreamWriter(new FileWriter("\\myFile.xml"));
		
			

			
			
			FileInputStream fileInputStream = new FileInputStream(fileName);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(fileInputStream);
			
			
			//FileInputStream fileInputStream = new FileInputStream(fileName);
			//XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(fileInputStream);
			while (xmlStreamReader.hasNext()) {
				int eventCode = xmlStreamReader.next();
				switch (eventCode) {
				case XMLStreamConstants.START_DOCUMENT :
					System.out.println("event = START_DOCUMENT");
					System.out.println("Localname = "+xmlStreamReader.getLocalName());
					xtw.writeStartDocument(); //xmlStreamReader.getEncoding(), xmlStreamReader.getVersion()); //   writeStartDocument( xmlStreamReader.getLocalName() );
					break;
				case XMLStreamConstants.END_DOCUMENT :
					System.out.println("event = END_DOCUMENT");
					System.out.println("Localname = "+xmlStreamReader.getLocalName());
					xtw.writeEndDocument();
					break;
				case XMLStreamConstants.START_ELEMENT :
					System.out.println("event = START_ELEMENT");
					System.out.println("Localname = "+xmlStreamReader.getLocalName());
					xtw.writeStartElement( xmlStreamReader.getLocalName() );
					break;
				case XMLStreamConstants.END_ELEMENT :
					System.out.println("event = END_ELEMENT");
					System.out.println("Localname = "+xmlStreamReader.getLocalName());
					xtw.writeEndElement();
					break;
				case XMLStreamConstants.CHARACTERS :
					System.out.println("event = CHARACTERS");
					System.out.println("Value = "+xmlStreamReader.getText());
					xtw.writeCharacters( xmlStreamReader.getText() );
					break;
				case XMLStreamConstants.ATTRIBUTE :
					System.out.println("event = ATTRIBUTE");
					System.out.println("Value = "+xmlStreamReader.getText());
					xtw.writeAttribute(xmlStreamReader.getLocalName(), xmlStreamReader.getText());
					break;
				case XMLStreamConstants.DTD :
					System.out.println("event = DTD");
					System.out.println("Value = "+xmlStreamReader.getText());
					xtw.writeDTD( xmlStreamReader.getText() );
					break;
				case XMLStreamConstants.ENTITY_REFERENCE :					
					System.out.println("event = ENTITY_REFERENCE");
					System.out.println("Value = "+xmlStreamReader.getText());
					xtw.writeEntityRef( xmlStreamReader.getText() );
					System.out.println("zzzzzzzzzzzzzzzzzz");
					break;					
				case XMLStreamConstants.NAMESPACE :					
					System.out.println("event = NAMESPACE");
					System.out.println("Value = "+xmlStreamReader.getText());
					xtw.writeNamespace(xmlStreamReader.getPrefix(), xmlStreamReader.getNamespaceURI() );
					System.out.println("zzzzzzzzzzzzzzzzzz");
					break;
					
					
				}
				xmlStreamReader.close();
			}
			//System.out.println("xtw = "+xtw.toString());
			

		   //xtw.flush();
		   //xtw.close();			

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	

}
