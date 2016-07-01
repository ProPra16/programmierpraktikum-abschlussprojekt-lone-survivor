package Maincomponents;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Reader {
	
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
	
	/*
	 * Pr�ft ob es sich um eine g�ltige Datei handelt, gibt ansonsten false zur�ck
	 * Ebenso pr�ft es ob es sich um ein g�ltiges Dokument, also XML oder txt handelt.
	 */
	public boolean check() 
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
		if(check()==false) return "Falscher Pfad/Name";
		if(xml = true ) return "Es handelt sich um den falschen Typ, benutzen sie den XML- Reader";
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
