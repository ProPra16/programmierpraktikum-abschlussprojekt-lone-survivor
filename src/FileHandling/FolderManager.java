package FileHandling;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/***************************************************************************************
 *                                                                                     *
 * @author Marc Feger                                                                  *
 * @version 30.07.2016                                                                 *
 ***************************************************************************************/
public class FolderManager {
	
	String libaryDirectory = "LibaryFolder"; 
	File libary = null; 
	String libaryPath = ""; 


	public FolderManager(){
		createLibary(); 
	    getFolderNames(); 
	}


	/*
	 * Diese Methode erstellt einen Ordner in dem die die Datein liegen 
	 * Auch ueberprueft diese Methode, ob die Datein bereits existieren 
	 */
	private void createLibary(){
        // Libary muss nicht erstellt werden, wenn sie schon existiert
		libary =	new File(libaryDirectory);
		if(!libary.exists()){
			libary.mkdir(); 
			System.out.println("Folder Created " + libary.getPath());
		}
		libaryPath = libary.getAbsolutePath(); 
	}

	
	/* Hier stehen alle Setter- Methoden */ 
	public void setLibaryDirectory(String newDirectory){ libaryDirectory = newDirectory; }
	public void setLibaryFoler(File newFolder){ libary = newFolder; }
	


	/* Hier stehen alle Getter- Methoden */ 
	public String getLibaryDirectory(){return libaryDirectory; }
	public String getLibaryPath(){ return libaryPath; }
    public File getLibaryFolder(){return libary; }

	
	/*
	 * Diese Methode gibt eine ArrayList mit all den Namen der Ordner, 
	 * die sich in der Libary befinden, zurueck. 
	 * Wenn man den Namen des Ordner hat, so kann man auch auf saemtliche Inhalte 
	 * des Ordens zugreifen
	 */
	
	public ArrayList<String> getFolderNames(){
		
		ArrayList<String> nameList = new ArrayList<>(); 	
			for(File file : libary.listFiles())
				nameList.add(file.getName());		
		return nameList; 
	}

	public ArrayList<String> getFilesInFolder(String directory){
		
		ArrayList<String> nameList = new ArrayList<>(); 
		File folder = new File(libary.getAbsolutePath()+"\\"+directory); 
		
		for(File file: folder.listFiles()){
			if(file.getName().endsWith(".java") || file.getName().endsWith(".txt") || file.getName().endsWith(".xml")){
				nameList.add(file.getAbsolutePath()); 				
			}
		}		
		
		return nameList; 
	}    
    

	public static void main(String[] args){
		FolderManager manager = new FolderManager(); 
		String dest = manager.getFilesInFolder("Aufgabe3").get(1); 
		System.out.println(dest);
		Reader reader = new Reader(dest); 
		System.out.println(reader.read());
	
	}



}
