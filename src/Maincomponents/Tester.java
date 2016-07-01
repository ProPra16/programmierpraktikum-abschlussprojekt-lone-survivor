package Maincomponents;
import java.io.IOException;
import java.util.ArrayList;

public class Tester {

	public static  void main (String [] args) throws IOException 
	{
		/*
		String tmp= "test.txt";
	//	Reader r = new Reader ();
		r.setDestination(tmp);
		if(r.check()==true) System.out.println("true");
		if(r.check()==false) System.out.println("false");
		System.out.println(r.read());
	
		FileReader fr = new FileReader(tmp);
        BufferedReader br = new BufferedReader(fr);
        
    	
    	
    	
    		System.out.println(br.readLine());
    
    	
	/*	File folder = new File(tmp);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
	*/	  
		String tmp= "D:/programmierpraktikum-abschlussprojekt-lone-survivor/Svenja.xml";
		XMLReader r = new XMLReader (tmp);
		if(r.check()==true) System.out.println("true");
		if(r.check()==false) System.out.println("false");
		System.out.println(r.aufgabenstellung());
		ArrayList<String []> l = new ArrayList <>();
		l = r.hauptklassen();
		
		//System.out.println(l.size());
		for(String [] o : l)
		{
			System.out.println(o[0]);
			System.out.println(o[1]);
		}
		
		l = r.testklassen();
		
		System.out.println("--------------------------");
		for(String [] o : l)
		{
			System.out.println(o[0]);
			System.out.println(o[1]);
		}
		
	}
}
