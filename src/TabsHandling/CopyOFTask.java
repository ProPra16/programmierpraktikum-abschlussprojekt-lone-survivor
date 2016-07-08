package TabsHandling;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import FileHandling.FolderManager;
import FileHandling.Reader;
import FileHandling.Writer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import xmlHandling.XMLReader;

public class CopyOFTask extends CreatableTab{

	TextArea text = new TextArea();
	TextArea test = new TextArea(); 
	TextArea task = new TextArea(); 
	
	
	
	
	FolderManager manager = new FolderManager(); 
	Reader reader =	 new Reader(); 
	Writer writer = new Writer(); 
    XMLReader xmlReader = new XMLReader(); 
    TreeView<String> view = null ; 
	ArrayList<String> filesInFolder = manager.getFilesInFolder(super.getTabName()); 	
	
	
	
	
	public CopyOFTask(String name) {
		super(name);
		// TODO Auto-generated constructor stub
		createTab(); 	
	}
	
/**
 * @author MarcFeger
 * @author SebastianStock
 * @version 02.07.2016
 * Bitte die den Satz von Stock- Feger halten: 
 *  -Keine Fehler in der .xml Datei schreiben
 *  -Test Klassen die als .java geliefert werden muessen auf Test.java enden
 *  
 *  .xml Dateien werden bevorzugt behandelt
 *  .java Dateien zweitrangig 
 *  .txt  Dateien nur dann, wenn .xml nicht vorhanden
 *  
 *  @ToDo Frage ab, ob die Datei neu gemacht werde soll, oder ob eine alte Datei bearbeitet werden soll 
 */
	private void createTab(){
	
		
		
		Collections.sort(filesInFolder); // kann gaendert werden, verhindert, dass .txt spontan bevorzugt wird
		
		
	    System.out.println(filesInFolder);
	 
	    
	    for(String nameOfFile : filesInFolder){
	    	
	    	reader.setDestination(nameOfFile);
	    	xmlReader.setDestination(nameOfFile);

	    	if(nameOfFile.contains(".xml") ){
	    		xmlReader.read(); 
	    		/* lese die Aufgabenstellung ein und gebe sie an die Task Ausgabe */ 
	    		task.setText(xmlReader.aufgabenstellung());
	    		task.setEditable(false);

	    		/* lese den Quelltext ein und gebe ihn an die Text Ausgabe */ 
	    	//	text.setText(xmlReader.hauptklassen().get(0)[1]);// die eins steht fuer den Text

	    		/* lese den Testcode ein und gebe ihn an die Test Ausgabe */ 
	    	//	test.setText(xmlReader.testklassen().get(0)[1]); // die eins steht fuer Text

	    	  
	    		
	    		view = contentView(xmlReader.hauptklassen(),xmlReader.testklassen(),text); 
	    		
	    	
	    	}    	
	    	else{

	    		/* Wenn kein .xml vorhanden lade die Aufgabe aus .txt, falls vorhanden */ 
	    		if(nameOfFile.contains(".txt")){
	    			task.setText(reader.read());
	    			task.setEditable(false);
	    		} 

	    		/* Lade die Quelltexte in die Ausgaben, falls vorhanden */ 
	    		if(nameOfFile.contains(".java")){

	    			if(nameOfFile.contains("Test")){
	    			//	test.setText(reader.read()); 
	    			}
	    			else{
	    			//	text.setText(reader.read()); 
	    			}
	    			
	    		}
	    		
	    	}
	    	
	    }
		
	   
	   
	    for(int i = 0; i < xmlReader.hauptklassen().size(); i++){
	    	 writer.writeJava(xmlReader.hauptklassen().get(i)[1], manager.getLibaryPath()+"\\"+super.getTabName()+ "\\" + xmlReader.hauptklassen().get(i)[0]); 	    	 
	    }
	    for(int i = 0; i < xmlReader.testklassen().size(); i++){
	    	 writer.writeJava(xmlReader.testklassen().get(i)[1], manager.getLibaryPath()+"\\"+super.getTabName()+ "\\" + xmlReader.testklassen().get(i)[0]); 	    	 
	    }
	    
	   
	    
	    
	    
	    super.setContentTree(view);
		super.setWriteArea(text);
		super.setTestArea(test);
		super.setTaskArea(task);
		
		
		
		super.addAllComponents(); 
	}
	
	
	
	
	private TreeView<String> contentView(ArrayList<String[]> code, ArrayList<String[]> tests, TextArea codeArea){
		
		/* Erstelle sortierte ArrayList damit die name mit dem Code stimmig sind */ 
		
		
		
		TreeItem<String> mainItem = new TreeItem<>("DataView"); 
		
		
	
		TreeItem<String> textItem = new TreeItem<>("Java Klassen"); 
		for(int i = 0 ; i < code.size(); i++){
			TreeItem<String> name = new TreeItem<>(code.get(i)[0]);			
			textItem.getChildren().add(name); 
		}
	
		TreeItem<String> testItem = new TreeItem<>("Test Klassen"); 
		
		for(int i = 0 ; i < tests.size(); i++){
			testItem.getChildren().add(new TreeItem<>(tests.get(i)[0]));	
		}
		
		
		TreeItem<String> taskItem = new TreeItem<>("Aufgabenstellung"); 
		mainItem.getChildren().addAll(textItem,testItem, taskItem); 
		
		
		TreeView<String> treeView = new TreeView<>(mainItem);
		treeView.setShowRoot(false);
		treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> changeText(oldValue,newValue));
	    
		
		return treeView; 
	}
	

	private void changeText(TreeItem oldValue,TreeItem newValue){
		
		
		
		if(oldValue == null) oldValue = newValue; 
	
		
	    
	    String saveClass = oldValue.getValue().toString(); 
	    // die alte adresse in die gespeichert werden soll  
		String savePoint = manager.getLibaryPath()+"\\"+ super.getTabName() + "\\" + saveClass ;
		System.out.println("SAVE: " +savePoint);
		
		String loadClass = newValue.getValue().toString(); 
		// die neue adresse von der gelesen werden soll
		String loadPoint = manager.getLibaryPath()+"\\"+ super.getTabName() + "\\"+ loadClass+".java";     
	    System.out.println("LOAD: "+loadPoint);
		
	    
        /*
         * Ab hier werden die Daten gespeichert 
         */
	    
	    
	    // prueffe, ob die saveClass ein SourceCode ist
	    
	    for(int i = 0; i < xmlReader.hauptklassen().size(); i++){
	    	boolean isSource = xmlReader.hauptklassen().get(i)[0].contains(saveClass); 
	    	if(isSource == true){	    	
	    	System.out.println(xmlReader.hauptklassen().get(i)[0]);
	    	System.out.println(saveClass+" is SourceCode: "+isSource);
	    	System.out.println("SAVED: "+savePoint);
	    	if(!text.getText().isEmpty()){
	    	writer.writeJava(text.getText(),savePoint);	    
	    	}
	    	else{
	    		
	    	}
	    	}
	    }
	    
	    // prueffe, ob die saveClass ein TestCode ist
	    
	    for(int i = 0; i < xmlReader.testklassen().size(); i++){
	    	boolean isTest = xmlReader.testklassen().get(i)[0].contains(saveClass); 
	    	if(isTest == true){
	    		System.out.println(xmlReader.testklassen().get(i)[0]);
		    	System.out.println(saveClass+" is SourceCode: "+isTest);
		    	System.out.println("SAVED: "+savePoint);
		    	if(!test.getText().isEmpty()){
		    	writer.writeJava(test.getText(),savePoint);	  
		    	}
		    	else{
		    		
		    	}
	    	}
	    }
	    
	    
	    // prueffe, ob die loadClass ein SourceCode ist
	    for(int i = 0; i < xmlReader.hauptklassen().size(); i++){
	    	boolean isSource = xmlReader.hauptklassen().get(i)[0].contains(loadClass); 
	    	if(isSource == true){	    	
		    	System.out.println(xmlReader.hauptklassen().get(i)[0]);
		    	System.out.println(loadClass+" is SourceCode: "+isSource);
		    	System.out.println("LOAD: "+loadPoint);
		    	reader.setDestination(loadPoint);
		    	text.setText(reader.read());
		    }
	    /*	// das File ist noch nicht vorhanden 
	    	else{
	    		reader.setDestination(loadPoint);
		    	text.setText(xmlReader.hauptklassen().get(i)[1]);
	    	}*/
	    }
	    
	 // prueffe, ob die loadClass ein TestCode ist
	    for(int i = 0; i < xmlReader.testklassen().size(); i++){
	    	boolean isSource = xmlReader.testklassen().get(i)[0].contains(loadClass); 
	    	if(isSource == true){	    	
		    	System.out.println(xmlReader.testklassen().get(i)[0]);
		    	System.out.println(loadClass+" is SourceCode: "+isSource);
		    	System.out.println("LOAD: "+loadPoint);
		    	reader.setDestination(loadPoint);
		    	test.setText(reader.read());
		    }
	    	// das File ist noch nicht vorhanden 
	   /* 	else{
	    		reader.setDestination(loadPoint);
		    	test.setText(xmlReader.testklassen().get(i)[1]);
	    	}*/
	    }
	    
	    
	    
	    
	 


	}




}
