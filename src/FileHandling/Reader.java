package FileHandling;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
/**
 * 
 * @author SebastianStock
 *
 */
public class Reader extends FolderManager {
	
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
	
	public boolean check() //Pr?ft ob es sich um eine g?ltige Datei handelt, gibt ansonsten false zur?ck
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
	
	/**
	 * @mod MarcFeger
	 */
	public String read() //liest die Datei ein
	{
		//if(check()==false) return "Falscher Pfad/Name";
		
		ArrayList <String> zeilenliste = new ArrayList <>();
        try
        {
        	FileReader fr = new FileReader(destination);
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
		for(String tmp : zeilenliste)
		{
			out = out + zeilenliste.get(index) + " \n " ;
			index ++;
		}
		
		return out;
	}
}
