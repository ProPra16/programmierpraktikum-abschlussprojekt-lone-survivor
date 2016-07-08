package FileHandling;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Reader extends FolderManager {
	
String destination = "";
boolean xml = false;
	
	public Reader(){}
	
	public Reader(String des)
	{
		destination = des;
	}
	
	public void setDestination(String des)
	{
		destination = des;
	}
	
<<<<<<< HEAD:src/FileHandling/Reader.java
	public boolean check() //Pr?ft ob es sich um eine g?ltige Datei handelt, gibt ansonsten false zur?ck
=======
	/*
	 * Prüft ob es sich um eine gültige Datei handelt, gibt ansonsten false zurück
	 * Ebenso prüft es ob es sich um ein gültiges Dokument, also XML oder txt handelt.
	 */
	public boolean check() 
>>>>>>> refs/remotes/origin/master:src/Maincomponents/Reader.java
	{
		if(destination != "")
		{
			if(destination.substring(destination.length()-4,destination.length()-1).equals("txt") )
			{
				if( destination.substring(destination.length()-4,destination.length()-1).equals("xml")) xml = true;
				
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
		}
		
		return false;
	}
	
	
	public String readTxt() //liest die Textdatei ein
	{
<<<<<<< HEAD:src/FileHandling/Reader.java
		//if(check()==false) return "Falscher Pfad/Name";
		
=======
		if(check()==false) return "Falscher Pfad/Name";
		if(xml = true ) return "Es handelt sich um den falschen Typ, benutzen sie den XML- Reader";
>>>>>>> refs/remotes/origin/master:src/Maincomponents/Reader.java
		ArrayList <String> zeilenliste = new ArrayList <>();
        try
        {
        	FileReader fr = new FileReader(destination);
            @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
            
            
            String zeile = br.readLine(); 
        	
        	while(zeile != null)
        	{
        	
        		zeilenliste.add(zeile);
                zeile = br.readLine(); 
        	}
        	br.close(); 
        }
        catch(Exception e){}
 
        return refactor(zeilenliste);
	}
	
	private String refactor(ArrayList <String> zeilenliste)
	{
		String out="";
		int index = 0;
		for(@SuppressWarnings("unused") String tmp : zeilenliste)
		{
			out = out + zeilenliste.get(index) + " \n " ;
			index ++;
		}
		
		return out;
	}
}
