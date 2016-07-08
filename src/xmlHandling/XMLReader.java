package xmlHandling;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Anregung und Codeteile: http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * Code wurde ?bernommen von : http://www.java-samples.com/showtutorial.php?tutorialid=152 , modifiziert und in Teilen ge?ndert
	*
	*Alle Ausgaben sollten halt auf Null abgefragt werden
 */

public class XMLReader {

	Node nNode = null; 
	Element eElement = null;
	boolean eingelesen = false;
	String destination = "";
	Document doc ;
  
	/*
	 * erzeugt ein XML Reader, bereitet ihn aber nicht vor, es muss manuell der Pfad gesetzt werden und gechekt werden
	 */
	public XMLReader(){}
	
	/*
	 * Pr?periert den XML Reader. Date k?nnen sofort gelesen werden
	 */
	public XMLReader(String destination)
	{
		this.destination = destination;
		read();
	}
	
	public void setDestination(String des)
	{
		destination = des;
	}
  
  	public void read ()
  	{
  		if(check()==true)
  		{
  		 try {
  			 
  			 	File fXmlFile = new File(destination);
  			 	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
  			 	DocumentBuilder dBuilder = null;
  			 	dBuilder = dbFactory.newDocumentBuilder();
  			 	doc = dBuilder.parse(fXmlFile);
  			 	doc.getDocumentElement().normalize();
  			 	eingelesen = true;
  		 	}	 
  		 	catch (SAXException e) 
  		 	{
  		 		e.printStackTrace();
  		 	} 
  		 	catch (IOException e) 
		 	{		
		 		e.printStackTrace();
		 	} 
		 	catch (ParserConfigurationException e)
		 	{
		 		e.printStackTrace();
		 	}
  		 	catch(NullPointerException m)
		 	{
		 			
		 	}
  			catch (Exception e) 
		 	{
		 		e.printStackTrace();
		 	} 
  		 
  		 	
  		}
  	}
  	
  	
  	public boolean check() 
	{
		if(destination != "")
		{
				File file = new File(destination);
			
				try
				{
					file.exists();
					if(file.exists())return true;
				}
				catch(Exception e)
				{
					return false;
				}
				
			}
		return false;
	}

  	/*
  	 * Git eine Collection zur?ck. Die Collection enth?lt einen String Aray. Das erste Element enth?lt den Namen der Klasse, die zweite den Code
  	 */
  	public ArrayList <String []> hauptklassen()
  	{
  		try
  		{
  		ArrayList <String[]> tmp = new  ArrayList <> ();
  		
  			NodeList nList = doc.getElementsByTagName("class");
  			for (int temp = 0; temp < nList.getLength(); temp++) 
  			{
  				nNode = nList.item(temp);
  				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
  				{
  					String [] help = new String [2];
  					 eElement = (Element) nNode;	
  					help[0] = eElement.getElementsByTagName("name").item(0).getTextContent();
  					help[1] = eElement.getElementsByTagName("syntax").item(0).getTextContent();
  					tmp.add(help);
  				}
  			}
  			
  			return tmp;
  		}
  		catch(Exception e)
  		{
  			return null;
  		}
  	}
  		 
  		
  	/*
  	 * Git eine Collection zur?ck. Die Collection enth?lt einen String Aray. Das erste Element enth?lt den Namen der Klasse, die zweite den Code, hier werden alle Testklassen zur?ck gegeben
  	 */
  	public ArrayList<String[]> testklassen()
  	{
  		try
  		{
  			ArrayList <String[]> tmp = new  ArrayList <> ();
  		
			NodeList nList = doc.getElementsByTagName("testclass");
			for (int temp = 0; temp < nList.getLength(); temp++) 
			{
				nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					String [] help = new String [2];
					 eElement = (Element) nNode;	
					help[0] = eElement.getElementsByTagName("name").item(0).getTextContent();
					help[1] = eElement.getElementsByTagName("syntax").item(0).getTextContent();
					tmp.add(help);
				}
			}
			return tmp;
  		}
  		catch(Exception o)
  		{
  			return null;
  		}
	}
  	

  //gibt die Aufgabenstellung als String zur?ck
  public String aufgabenstellung ()
  {
	  try
	  {
		  if(eingelesen == true ) 
		  {
			  NodeList nList = doc.getElementsByTagName("task");
			  nNode = nList.item(0);
		  		eElement = (Element) nNode;
		  		return eElement.getElementsByTagName("discription").item(0).getTextContent();  
		  }
		  return "Kein Element eingelesen";	
	  }
	  catch(Exception o)
	  {
		  return "Kein Element eingelesen";	
	  }
  }

  
  
}