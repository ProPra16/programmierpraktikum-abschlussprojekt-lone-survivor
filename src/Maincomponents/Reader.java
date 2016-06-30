package Maincomponents;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Reader {
	
String destination = "";
	
	public Reader(){}
	
	public Reader(String des)
	{
		destination = des;
	}
	
	public void setDestination(String des)
	{
		destination = des;
	}
	
	public boolean check() //Prüft ob es sich um eine gültige Datei handelt, gibt ansonsten false zurück
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
	
	public String read() //liest die Datei ein
	{
		if(check()==false) return "Falscher Pfad/Name";
		
		ArrayList <String> zeilenliste = new ArrayList <>();
        try
        {
        	FileReader fr = new FileReader(destination);
            @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
            
        	while(!br.readLine().equals(null))
        	{
        	
        		zeilenliste.add(br.readLine());
        
        	}
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
