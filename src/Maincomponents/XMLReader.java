package Maincomponents;


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

/*
 * Anregung und Codeteile: http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * Code wurde übernommen von : http://www.java-samples.com/showtutorial.php?tutorialid=152 , modifiziert und in Teilen geändert
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
	 * Präperiert den XML Reader. Date können sofort gelesen werden
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
  	 * Git eine Collection zurück. Die Collection enthält einen String Aray. Das erste Element enthält den Namen der Klasse, die zweite den Code
  	 */
  	public ArrayList <String []> hauptklassen()
  	{
  		try
  		{
  		ArrayList <String[]>  tmp = new  ArrayList <> ();
  		NodeList nList = doc.getElementsByTagName("class");
  		nNode = nList.item(0);
  		eElement = (Element) nNode;	  	
  		
  		for(int i = 0 ; i < eElement.getElementsByTagName("name").getLength() ; i++ )
  		{
  			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
  			{
  				String [] help = new String [2];		
  				help[0] = eElement.getElementsByTagName("name").item(i).getTextContent();
  				help[1] = eElement.getElementsByTagName("syntax").item(i).getTextContent();
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
  	 * Git eine Collection zurück. Die Collection enthält einen String Aray. Das erste Element enthält den Namen der Klasse, die zweite den Code, hier werden alle Testklassen zurück gegeben
  	 */
  	public ArrayList<String[]> testklassen()
  	{
  		try
  		{
  		ArrayList <String[]> tmp = new  ArrayList <> ();
  		NodeList nList = doc.getElementsByTagName("testclass");
  		nNode = nList.item(0);
  		eElement = (Element) nNode;	  	
  		
  		for(int i = 0 ; i < eElement.getElementsByTagName("name").getLength() ; i++ )
  		{
  			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
  			{
  				String [] help = new String [2];		
  				help[0] = eElement.getElementsByTagName("name").item(i).getTextContent();
  				help[1] = eElement.getElementsByTagName("syntax").item(i).getTextContent();
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
  	 * Gibt ein boolean zurück ob für diese Aufgabe Babysteps vorgesehen sind
  	 */
  public boolean babysteps()
  {
	  try
	  {
		  if(eingelesen == true ) 
		  {
			  NodeList nList = doc.getElementsByTagName("babysteps");
			  nNode = nList.item(0);
		  		eElement = (Element) nNode;
		  		System.out.println("bla");
		  		if(eElement.getElementsByTagName("activate").item(0).getTextContent().equals("true"))return true;
		  		else{return false;}
		  }
		  return false;	
	  }
	  catch(Exception o)
	  {
		  return false;	
	  }
  }
  
  /*
   * Gibt den Wert zurück der die Babysteps in Millisekunden repräsentiert
   * Prüft vorher ob Babysteps enabeld sind, gibt ansonsten 0 zurück
   */
  public double babystepsValue()
  {
	  try
	  {
		  if(eingelesen == true ) 
		  {
			  NodeList nList = doc.getElementsByTagName("babysteps");
			  nNode = nList.item(0);
		  		eElement = (Element) nNode;
		  		if(babysteps() == true)
		  		{
		  			return Double.parseDouble(eElement.getElementsByTagName("time").item(0).getTextContent())*1000;
		  		}
		  		else{return 0;}
		  }
		  return 0;	
	  }
	  catch(Exception o)
	  {
		  return 0;	
	  }
  }

  //gibt die Aufgabenstellung als String zurück
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