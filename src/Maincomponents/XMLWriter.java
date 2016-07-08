package Maincomponents;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/*
 * @Sebastian STock
 */
public class XMLWriter {

	private ArrayList<String []> klassen ;
	private ArrayList<String []> testklassen ;
	private String beschreibung   ;
	private String babysteps [];
	private String filename = null;
	
	/*
	 * Konstruktor falls der User alle XML Fragmente einzeln übergeben will
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public XMLWriter()
	{
		klassen = (ArrayList<String[]>)new ArrayList();
		testklassen = (ArrayList<String[]>)new ArrayList();
	}
	
	/*
	 * Konstruktor falls der User alle XML Fragmente einzeln übergeben will, hier kann der Filename schon mit angegeben werden
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public XMLWriter(String filename)
	{
		klassen = (ArrayList<String[]>)new ArrayList();
		testklassen = (ArrayList<String[]>)new ArrayList();
		this.filename = filename;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	/*
	 * Konstruktor falls der User die einzelnen Komponenten eines XML Dokumentes übergeben möchte
	 */

	public XMLWriter( ArrayList<String []> klassen, ArrayList<String []> testklassen, String beschreibung, String [] babysteps  )
	{
		this.klassen = klassen;
		this.testklassen = testklassen;
		this.beschreibung = beschreibung;
		this.babysteps = babysteps;
	}
	
	/*
	 * Hier kann man gepackte Klassen übergben
	 */
	public void addKlassen(String [] klassen)
	{
		this.klassen.add(klassen);
	}
	
	/*
	 * Hier kann man die komponenten einer Klasse nahc dem verwendeten XML standart übergeben, diese wird automatisch gepackt
	 */
	public void addKlassen(String name, String content)
	{
		String [] tmp = new String [2];
		tmp[0] = name;
		tmp[1] = content;
		klassen.add(tmp);
	}
	
	/*
	 * Siehe Klasse
	 */
	public void addTestKlassen(String [] testklassen)
	{
		this.testklassen.add(testklassen);
	}
	
	/*
	 * Siehe Klasse
	 */
	public void addTestKlassen(String name, String content)
	{
		String [] tmp = new String [2];
		tmp[0] = name;
		tmp[1] = content;
		testklassen.add(tmp);
	}
	
	/*
	 * Hier übergibt man die Aufgabenstellung
	 */
	public void addBeschreibung(String content)
	{
		beschreibung = content;
	}
	
	/*
	 * Hier kann man gepackte BabySteps übergeben
	 */
	public void addBabysteps(String [] steps)
	{
		babysteps=steps;
	}
	
	/*
	 * Hier kann man die Werte der BabySteps übergeben, diese sind ein boolean und ein double. Der Double sind MiliSekunden um die Umrechnung bei Systemzeiten zu ersparen. Umgerechnet wird automatisch
	 */
	public void addBabysteps(boolean active, double milSec)
	{
		String [] tmp = new String [2];
		if(active == true)tmp[0] = "true";
		else tmp[0] = "false";	
		tmp[1] = Double.toString(milSec/1000);
		babysteps=tmp;
	}
	
	
	/*
	 * prüft ob ein Teilelement nicht befüllt wurde
	 */
	private boolean check()
	{
		if(klassen.get(0)== null||testklassen.get(0)== null||beschreibung== null||babysteps== null) return false;
		return true;
	}
	
	
	public void writeXML()
	{
		if(check() ==true && filename != null)
		{
			try{
				
				filename = filename+".xml";
				FileOutputStream fileOutputStream = new FileOutputStream(filename);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
				BufferedWriter writer = new BufferedWriter(outputStreamWriter);
				
				/**
				 * Hier beginnt der Interessante Teil
				 */
				
				String help1 =  " \"1.0\" ";
				String help2 =  " \"UTF-8\" ";
				String tmp = "<?xml version="+help1+" encoding="+help2+"?>" + "\n" + "<task>" + "\n";
				
				tmp = tmp +"<class>" + "\n" ;
				for(String [] l : klassen )
				{
					tmp = tmp + "<name>" + l[0] + "</name>" + "\n" + "<syntax>" + l[1] + "</syntax>";
				}
				tmp = tmp +"</class>" +"\n" + "<testclass>";
				
				
				for(String [] l : klassen )
				{
					tmp = tmp + "<name>" + l[0] + "</name>" + "\n" + "<syntax>" + l[1] + "</syntax>";
				}
				
				tmp = tmp +"</testclass>" +"\n" + "<discription>" + beschreibung + "</discription>" +"\n"+ "<babysteps>"+ "\n" + "<activate>"+ 
				 babysteps[0] + "</activate>"+ "\n"+ "<time>" + "\n"+ babysteps[1]+ "\n"+"</time>"+"\n" + "</babysteps>" +"\n"+ "</task>";
				
				
				/*
				 * Ende vom coolen Kram
				 */
				writer.write(tmp);
				writer.flush();
				writer.close();
			}
			catch (IOException e){}
		}
	}
}

