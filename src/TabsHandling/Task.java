package TabsHandling;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TimerTask;

import FileHandling.FolderManager;
import FileHandling.Reader;
import FileHandling.Writer;
import PopUpHandling.PopUpDialog;
import StringHandling.StringFilter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Duration;
import xmlHandling.XMLReader;
/***************************************************************************************
 *                                                                                     *
 * @author Marc Feger                                                                  *
 * @version 04.07.2016                                                                 *
 ***************************************************************************************/
public class Task extends CreatableTab{

	TextArea text = new TextArea();
	TextArea test = new TextArea(); 
	TextArea task = new TextArea(); 
	FolderManager manager = new FolderManager(); 
	Reader reader =	 new Reader(); 
	Writer writer = new Writer(); 
	XMLReader xmlReader = new XMLReader(); 
	TreeView<String> view = null ; 
	ArrayList<String> filesInFolder = manager.getFilesInFolder(super.getTabName()); 	
	PopUpDialog popUp= new PopUpDialog(); 
	boolean xmlExists = false; 
	boolean oldProjectExists = false; 	
	boolean isRefactoring = false; 
	boolean isPhaseGREEN  = false; 
	boolean isPhaseRED    = true ; 
	public boolean haveBabySteps = xmlReader.babysteps(); 

	PhasenAnzeige phaseStatus = new PhasenAnzeige(true, false, false); 
	BackgroundActions backaction = new BackgroundActions();
	public Clock clock = new Clock(isSelected()); 

	//public BabyStep babyStep = new BabyStep(); 

	public Task(String name) {
		super(name);
		// TODO Auto-generated constructor stub
		createTab();

	}

	/**
	 * @author MarcFeger
	 * @author SebastianStock
	 * @version 02.07.2016
	 * Bitte dran halten: 
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


		xmlExists = false; 
		oldProjectExists = false; 
		xmlReader = new XMLReader(); // zum aktualisieren der Daten 
		manager = new FolderManager(); 
		filesInFolder = manager.getFilesInFolder(super.getTabName()); 	
		Collections.sort(filesInFolder); // kann gaendert werden, verhindert, dass .txt spontan bevorzugt wird
		//System.out.println("FIles in Folder"+super.getTabName()+" "+ filesInFolder);

		for(String nameOfFile : filesInFolder){

			reader.setDestination(nameOfFile);
			xmlReader.setDestination(nameOfFile);

			if(nameOfFile.contains(".xml") ){
				// gebe die ausgabe aus, und erstelle TreeView
				xmlReader.read(); 
				haveBabySteps = xmlReader.babysteps(); 
				task.setText(xmlReader.aufgabenstellung());
				view = contentView(xmlReader.hauptklassen(),xmlReader.testklassen(),text); 
				xmlExists = true; 
			}    	
			else{

			}
		}

		/**
		 * @author MarcFeger
		 */
		if(xmlExists == true){
			// erstelle ab hier die klassen die XML vorgibt, wenn noch keine vorhanden 
			File file; 			
			for(int i = 0; i < xmlReader.hauptklassen().size(); i++){
				String destination = manager.getLibaryPath()+"\\"+super.getTabName()+ "\\" + xmlReader.hauptklassen().get(i)[0];
				file = new File(destination+".java"); 
				if(!file.exists()){
				//	System.out.println("CREATED SOURCE: "+destination+".java");
					writer.writeJava(xmlReader.hauptklassen().get(i)[1], destination); 
				}
				else{
					oldProjectExists = true; 
				}
			}
			for(int i = 0; i < xmlReader.testklassen().size(); i++){
				String destination = manager.getLibaryPath()+"\\"+super.getTabName()+ "\\" + xmlReader.testklassen().get(i)[0];
				file = new File(destination+".java"); 

				if(!file.exists()){
					//System.out.println("CREATED TEST: "+destination+".java");
					writer.writeJava(xmlReader.testklassen().get(i)[1], destination); 
				}   
				else{
					oldProjectExists = true; 
				}
			}

		}
		else{
			///// Kann noch ergaenzt werden um zu nerven dass das dokument fehlt
			popUp.showXmlNotFound();
			createTab(); 
			return ; 
		}


		if(oldProjectExists == true){
			filesInFolder = manager.getFilesInFolder(super.getTabName()); 
			if(popUp.deleteOldProject(filesInFolder) == true){
				oldProjectExists = false; 
				createTab(); 
				return ; 	
			}

		}




		filesInFolder = manager.getFilesInFolder(super.getTabName()); 	
		task.setEditable(false);
		test.setEditable(false);
		text.setEditable(false);

		super.setContentTree(view);
		super.setWriteArea(text);
		super.setTestArea(test);
		super.setTaskArea(task);

		setRefactoring(false); 
		setPhaseGREEN(false); 
		setPhaseRED(true); 

		//aktualisiere(); 

		super.setStatusLabel(phaseStatus);

		babyStepConfic(); 

		super.addAllComponents(); 
	}

	public void babyStepConfic(){
		if(this.haveBabySteps == true){
		//	babyStep.saveDatas(); 
			clock.setEnde((int)xmlReader.babystepsValue() / 1000); ////////////// veraendern
			//	clock.setEnde(50);
			clock.setSelected(isSelected()); 
			super.setTimeLabel(clock);
			bindToClock(); 
		//	backaction.start();
		}
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
	//	System.out.println("SAVE: " +savePoint);
//
		String loadClass = newValue.getValue().toString(); 
		// die neue adresse von der gelesen werden soll
		String loadPoint = manager.getLibaryPath()+"\\"+ super.getTabName() + "\\"+ loadClass+".java";     
	//	System.out.println("LOAD: "+loadPoint);


		/*
		 * Ab hier werden die Daten gespeichert 
		 */

		// prueffe, ob die saveClass ein SourceCode ist

		for(int i = 0; i < xmlReader.hauptklassen().size(); i++){
			boolean isSource = xmlReader.hauptklassen().get(i)[0].equals(saveClass); //contains
			if(isSource == true){	    	
				//System.out.println(xmlReader.hauptklassen().get(i)[0]);
			//	System.out.println(saveClass+" is SourceCode: "+isSource);
			//	System.out.println("SAVED: "+savePoint);
				if(!text.getText().isEmpty()){// empty pruefen wegen dem ersten schritt, sonst wird falsch gespeichert
					writer.writeJava(text.getText(),savePoint);	    
				}
				else{

				}
			}
		}

		// prueffe, ob die saveClass ein TestCode ist

		for(int i = 0; i < xmlReader.testklassen().size(); i++){
			boolean isTest = xmlReader.testklassen().get(i)[0].equals(saveClass); // contains
			if(isTest == true){
				//System.out.println(xmlReader.testklassen().get(i)[0]);
				//System.out.println(saveClass+" is SourceCode: "+isTest);
			//	System.out.println("SAVED: "+savePoint);
				if(!test.getText().isEmpty()){ // empty pruefen wegen dem ersten schritt, sonst wird falsch gespeichert
					writer.writeJava(test.getText(),savePoint);	  
				}
				else{

				}
			}
		}


		// prueffe, ob die loadClass ein SourceCode ist
		for(int i = 0; i < xmlReader.hauptklassen().size(); i++){
			boolean isSource = xmlReader.hauptklassen().get(i)[0].equals(loadClass); // contains
			if(isSource == true){	    	
			//	System.out.println(xmlReader.hauptklassen().get(i)[0]);
			//	System.out.println(loadClass+" is SourceCode: "+isSource);
			//	System.out.println("LOAD: "+loadPoint);
				reader.setDestination(loadPoint);
				text.setText(reader.read());
				text.setEditable(true);

			}
		}

		// prueffe, ob die loadClass ein TestCode ist
		for(int i = 0; i < xmlReader.testklassen().size(); i++){
			boolean isSource = xmlReader.testklassen().get(i)[0].equals(loadClass); // contains 
			if(isSource == true){	    	
			//	System.out.println(xmlReader.testklassen().get(i)[0]);
			//	System.out.println(loadClass+" is SourceCode: "+isSource);
			//	System.out.println("LOAD: "+loadPoint);
				reader.setDestination(loadPoint);
				test.setText(reader.read());
				test.setEditable(true);				
			}
		}



		//text.setEditable(false);
	}


	public boolean xmlExists(){ return xmlExists; }
	public ArrayList<String> filesInProject(){ return filesInFolder; }

	public boolean isPhaseRED(){ return isPhaseRED; }

	public void setPhaseRED(boolean status){
		phaseStatus.setPhaseRED(status); 
		isPhaseRED = status; }

	public boolean isPhaseGREEN(){ return isPhaseGREEN; }

	public void setPhaseGREEN(boolean status){
		phaseStatus.setPhaseGREEN(status);
		isPhaseGREEN = status; }

	public boolean isRefactoring(){ return isRefactoring; }

	public void setRefactoring(boolean status){ 
		phaseStatus.setRefactoring(status);
		isRefactoring = status; }


	public void aktualisiere(){

		Label timeLabel = new Label(); 

		String color = ""; 

		if(isPhaseRED()){
			timeLabel.setStyle("-fx-border-color:black; -fx-background-color: red;"); 
		//	System.out.println("RED");
		}
		else if(isPhaseGREEN()){
			timeLabel.setStyle("-fx-border-color:black; -fx-background-color: green;"); 
		//	System.out.println("GREEN");
		}
		else if(isRefactoring()){
			timeLabel.setStyle("-fx-border-color:black; -fx-background-color: blue"); 
		//	System.out.println("BLUE"); 
		}

		//timeLabel.setStyle(color);

		timeLabel.prefWidthProperty().bind(super.Vbox.widthProperty());


		super.setTimeLabel(timeLabel);
	}

	public ArrayList<String> testsInProject(){
		ArrayList<String> testClasses = new ArrayList<>(); 
		//System.out.println("READ TESTS IN PROJECT");
		for(String currentFile : filesInFolder){
			if(currentFile.endsWith(".java")){
				String className = currentFile.substring(currentFile.lastIndexOf("\\")+1 ,currentFile.lastIndexOf(".")); 
				for(int i = 0; i < xmlReader.testklassen().size(); i++)
					if(xmlReader.testklassen().get(i)[0].contains(className) && !testClasses.contains(currentFile)){
						//System.out.println("THIS IS A TEST: "+className);
						testClasses.add(currentFile); 
					}			
			}
		}
		return testClasses; 
	}

	public ArrayList<String> sourcesInProject(){
		ArrayList<String> sourcesClasses = new ArrayList<>(); 
	//	System.out.println("READ SOURCES IN PROJECT");
		for(String currentFile : filesInFolder){
			if(currentFile.endsWith(".java")){
				String className = currentFile.substring(currentFile.lastIndexOf("\\")+1 ,currentFile.lastIndexOf(".")); 
				for(int i = 0; i < xmlReader.hauptklassen().size(); i++)
					if(xmlReader.hauptklassen().get(i)[0].contains(className) && !sourcesClasses.contains(currentFile)){
					//	System.out.println("THIS IS A SOURCE: "+className);
						sourcesClasses.add(currentFile); 
					}			
			}
		}
		return sourcesClasses; 
	}


	class BackgroundActions extends Thread{		
		
		@Override
		public void run(){
			while(true){
				clock.setSelected(isSelected());
			}
		}

		BackgroundActions(){
			super.run();
		}

	}


	public void bindToClock(){
		 Timeline timeline = new Timeline(
				  new KeyFrame(Duration.seconds(0),
						  new EventHandler<ActionEvent>() {
					  @Override
					  public void handle(ActionEvent actionEvent) { 
						  clock.setSelected(isSelected());
					  }
				  }
						  ),
				  new KeyFrame(Duration.seconds(1))
				  );
		  timeline.setCycleCount(Animation.INDEFINITE);
		  timeline.play();
	}
	


}





	
	
	
	
	
	
	
	
	
	
	
	



