package SceneHandling;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import CompilerHandling.Tester;
import FileHandling.FileExport;
import FileHandling.FolderManager;
import FileHandling.ImportManager;
import FileHandling.Reader;
import FileHandling.Writer;
import StringHandling.StringFilter;
import TabsHandling.CreatableTab;
import TabsHandling.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/***************************************************************************************
 *                                                                                     *
 * @author Marc Feger                                                                  *                                                                             *
 ***************************************************************************************/

public class ScreenCopy extends Scene{

	static Group root = new Group();

	BorderPane borderPane = new BorderPane(); // Hier wird alles drauf abgebildet

	TabPane tabPane = new TabPane(); // Fuer die Anzeige der Tabs

	HBox menuePane = new HBox(); // Fuer die Anzeige der Buttons

	MenuBar menuBar = new MenuBar();

	CreatableTab currentTab; 

	HashMap<String, CreatableTab> tabs = new HashMap<>();

	FolderManager folderManager = new FolderManager(); 

	List<String> dialogData = folderManager.getFolderNames(); 

	Timer timer = new Timer();    

	HBox writeBox = new HBox(); // fuer die Eingabe der Methoden und Tests, sowie der Ausgabe ueber eine Konsole
	TextArea console = new TextArea(); 
	TextArea methodArea = new TextArea();   
	TextArea testArea = new TextArea(); 


	public ScreenCopy(double width, double height) {
		super(root, width, height);

		initComponents();

		root.getChildren().addAll(borderPane); 

		timer.schedule(new AutoSaver(), 1000, 1000); // fuehrt alle einhundert millisekunden einen autosave durch 
	}


	/* 
	 * In dieser Methode werden alle Panes erstellt. 
	 * Sie ruft alle Methoden auf die fuer die Anzeige wichtig sind.
	 * Wird IMMER im Konsturktor aufgerufen.
	 */
	private void initComponents(){
		initBorderPane(); 
		initMenuBar(); 
		initTabPane();
		writeBox.getChildren().addAll(console); // ConsolenAusgabe 
		borderPane.setBottom(console);
	}


	/*
	 * Legt die Eigenschaften, Inhalte etc. des BorderPanes an. 
	 * Auf dem BorderPane wird alles angezeigt was fuer den-
	 * -User wichtig ist. 
	 */
	private void initBorderPane(){

		borderPane.prefHeightProperty().bind(super.heightProperty());
		borderPane.prefWidthProperty().bind(super.widthProperty());      
	}


	/* 
	 * Legt die Eigenschaften, Inhalte etc. der HBox an. 
	 * Die HBox beinhaltet alle Optionen aus denen der-
	 * -User waehlen kann. 
	 * @ToDo: 
	 *       - Die Komponenten der HBox in eine ArrayList packen
	 *       - Die die HBox kann ersetzt werden falls erforderlich
	 *       - Die Buttons muessen den Aktuellen Reiter etc. kennen
	 */
	private void initMenuePane(){
		/* 
		 * Hier koennen alle Komponenten fuer die Anzeige-
		 * eingefuegt werden.
		 */
		Button button = new Button("Compile");
		button.setOnAction(new EventHandler<ActionEvent>() {
			/* BeipspielCode: Gibt den Inhalt von Tab1 aus */ 
			@Override
			public void handle(ActionEvent e) {
				System.out.println(getTextOfCurrentTab());
			}
		}); 
		menuePane.getChildren().add(button); // Beispiel 		
		/* Lege die MenuAnzeige in der borderPane an
		 * Nicht entfernen
		 */
		borderPane.setTop(menuePane); 
	}

	/* 
	 * Legt die Eigenschaften, Inhalte etc. der MenuBar an. 
	 * Die MenuBar beinhaltet alle Optionen aus denen der-
	 * -User waehlen kann. 
	 */
	private void initMenuBar(){
		/* 
		 * Hier koennen alle Komponenten fuer die Anzeige-
		 * eingefuegt werden.
		 */
		if(menuBar.getMenus().size() == 0){
			menuBar.getMenus().addAll(fileMenu(), runMenu());  
		}
		else {		
			menuBar.getMenus().remove(1);		
			menuBar.getMenus().add(runMenu()); 
		}
		/* Lege die MenuAnzeige in der borderPane an
		 * Nicht entfernen
		 */
		borderPane.setTop(menuBar); 
	}

	/*
	 * Erstellt alle Komponenten die fuer das aktuelle Tab 
	 * zustaendig sind
	 */

	final Menu fileMenu(){
		Menu menu = new Menu("File"); 

		/* fuege die komponenten hinzu */ 
		MenuItem newFile = new MenuItem("New File"); 
		MenuItem importFile = new MenuItem("refresh Projects"); 
		MenuItem exportFile = new MenuItem("zip-Project"); 



		exportFile.setOnAction((event) -> exportAndZip());
		importFile.setOnAction((event) -> importFromServer()); 
		newFile.setOnAction((event) -> showChoiceInput());

		menu.getItems().addAll(newFile, importFile, exportFile); 

		return menu; 
	}

	public  Menu runMenu(){
		Menu menu = new Menu("Run"); 

		/* fuege die komponenten hinzu */ 


		MenuItem compileREDFile = new MenuItem("Phase RED: Compile Test"); 
		MenuItem compileGREENFile = new MenuItem("Phase GREEN: Compile Test"); 
		MenuItem refactorFile = new MenuItem("Refactor Test"); 

		if(getCurrentTab()!= null){
			compileREDFile.setDisable(!getCurrentTab().isPhaseRED());
			compileGREENFile.setDisable(!getCurrentTab().isPhaseGREEN()); 
			refactorFile.setDisable(!getCurrentTab().isRefactoring());
			console.appendText("RED: "+getCurrentTab().isPhaseRED()+ "\nGREEN: "+getCurrentTab().isPhaseGREEN()+"\nREF "+getCurrentTab().isRefactoring()+"\n");
		}



		menu.setOnAction((event)-> doCompileSteps(event, menu, compileREDFile, compileGREENFile, refactorFile));

		menu.getItems().addAll(compileREDFile, compileGREENFile,refactorFile  ); 

		return menu; 
	}


	/* 
	 * Legt die Eigenschaften, Inhalte etc. der HBox an. 
	 * Die HBox beinhaltet alle Optionen aus denen der-
	 * -User waehlen kann. 
	 * @ToDo: 
	 *       - Die Komponenten das TabPane in eine ArrayList packen
	 *       - Klasse CreatableTab in spezielle Klassen unterteilen
	 */
	private void initTabPane(){
		tabPane.prefHeightProperty().bind(super.heightProperty());
		tabPane.prefWidthProperty().bind(super.widthProperty());
		borderPane.setCenter(tabPane);
	}


	/*
	 * Hier wird der Text, der im aktuell ausgewaehlten Tab steht-
	 * -ausgelesn.
	 * @ToDo: 
	 *       - Einen Thread erstellen der immer den aktuellen Tab bestimmt
	 */
	private String getTextOfCurrentTab(){		
		String output = ""; 
		for(int i = 0; i < tabPane.getTabs().size(); i++){
			CreatableTab currentTab = (CreatableTab) tabPane.getTabs().get(i); 
			if(currentTab.isSelected()){
				currentTab.aktComponents();
				output = currentTab.getWriteArea().getText();			 
			}
		}  
		System.out.println(output);
		return output;    
	}
	/*
	 * Hier wird die aktuell ausgewaehlte projekt gesucht 
	 * -ausgelesn.
	 * @ToDo: 
	 *       - Einen Thread erstellen der immer den aktuellen Tab bestimmt
	 */
	private Task getCurrentTab(){		

		for(int i = 0; i < tabPane.getTabs().size(); i++){
			Task currentTab =  (Task) tabPane.getTabs().get(i); 
			if(currentTab.isSelected()){
				System.out.println("CURRENT TAB: "+currentTab.getTabName());				
				return currentTab;   				 
			}
		}  	
		return null;    
	}


	private void showChoiceInput(){
		if(dialogData.size() > 0){
			dialogData = folderManager.getFolderNames(); 
			Dialog<String>dialog = new ChoiceDialog(dialogData.get(0), dialogData);

			dialog.setTitle("What Task would you like to Open");
			dialog.setHeaderText("Select your choice");

			Optional<String> result = dialog.showAndWait();
			String selected = "cancelled.";

			if (result.isPresent()) {

				selected = result.get();

				if(!tabs.containsKey(selected)){
					tabs.put(selected,new Task(selected)); 
				}
				else{
					tabPane.getTabs().remove(tabs.get(selected)); 		    	
				}


			}

			//addTabToScreen(selected); 
			tabPane.getTabs().add(tabs.get(selected))	; 
		}



	}



	public void doCompileSteps(Event event, Menu menu, MenuItem compileREDFile, MenuItem compileGREENFile, MenuItem refactorFile){
		initMenuBar();
		Tester tester = new Tester(); 
		Reader reader = new Reader(); 

		Timer babyTimer = new Timer(); 
		BabyStep babyStep = new BabyStep();
		babyStep.saveDatas(); 

		//babyTimer.schedule(babyStep, 100, 1000);

		if(getCurrentTab().haveBabySteps){
			babyTimer.schedule(babyStep, 100, 60000); // alles was vor dem Starten durch run geschehen ist wird geloescht
			console.appendText("Have BabySteps");
		}


		aktButtons(compileREDFile, compileGREENFile, refactorFile);



		// Stufe eins: einen Test schreiben der fehl schlaegt 

		if(event.getTarget() == compileREDFile ){		

			tester = new Tester(); 

			for(String testFiles : getCurrentTab().testsInProject()){

				System.out.println(testFiles);
				reader.setDestination(testFiles);
				String testCode = reader.read(); 
				String testName = StringFilter.filteredName(testCode); 

				System.out.println("NAME: "+ testName);
				System.out.println("Code: "+ testCode);
				tester.classehinzufuegen(testName, testCode, true);
			}


			for(String sourceFiles : getCurrentTab().sourcesInProject()){


				System.out.println(sourceFiles);
				reader.setDestination(sourceFiles);
				String methodCode = reader.read(); 
				String methodName = StringFilter.filteredName(methodCode); 

				System.out.println("NAME: "+ methodName);
				System.out.println("Code: "+ methodCode);			
				tester.classehinzufuegen(methodName, methodCode, false);
			}


			if(tester.Fehlererkennung() == true ){
				String compileerror = tester.getErrorMessage(); 
				console.appendText(compileerror+"\n");
				console.appendText("Please add a Method that solves the Test"+"\n");
				// fuehre durch die steps 
				getCurrentTab().setPhaseRED(false);
				getCurrentTab().setPhaseGREEN(true);
				getCurrentTab().setRefactoring(false);
				getCurrentTab().aktualisiere(); 
				console.appendText(getCurrentTab().getTabName()+" "+getCurrentTab().isPhaseRED());
				this.aktButtons(compileREDFile, compileGREENFile, refactorFile);
			}
			else {
				console.appendText("Please write any Test that fails or watch other Tests\n");
				this.aktButtons(compileREDFile, compileGREENFile, refactorFile);
			}
		}

		if(event.getTarget() == compileGREENFile){


			tester = new Tester(); 

			for(String testFiles : getCurrentTab().testsInProject()){

				System.out.println(testFiles);
				reader.setDestination(testFiles);
				String testCode = reader.read(); 
				String testName = StringFilter.filteredName(testCode); 

				System.out.println("NAME: "+ testName);
				System.out.println("Code: "+ testCode);
				tester.classehinzufuegen(testName, testCode, true);

			}


			for(String sourceFiles : getCurrentTab().sourcesInProject()){


				System.out.println(sourceFiles);
				reader.setDestination(sourceFiles);
				String methodCode = reader.read(); 
				String methodName = StringFilter.filteredName(methodCode); 

				System.out.println("NAME: "+ methodName);
				System.out.println("Code: "+ methodCode);			
				tester.classehinzufuegen(methodName, methodCode, false);
			}


			if(tester.Fehlererkennung() == false ){
				console.appendText("Refactor the code \n");
				// fuehre durch die steps 
				getCurrentTab().setPhaseRED(false);
				getCurrentTab().setPhaseGREEN(false);
				getCurrentTab().setRefactoring(true);
				
				this.aktButtons(compileREDFile, compileGREENFile, refactorFile);
			}
			else {
				console.appendText("The Test is not solved \n");
				console.appendText( tester.getErrorMessage()+"\n");
				this.aktButtons(compileREDFile, compileGREENFile, refactorFile);
			}




		}

		if(event.getTarget() == refactorFile){

			tester = new Tester(); 

			for(String testFiles : getCurrentTab().testsInProject()){

				System.out.println(testFiles);
				reader.setDestination(testFiles);
				String testCode = reader.read(); 
				String testName = StringFilter.filteredName(testCode); 

				System.out.println("NAME: "+ testName);
				System.out.println("Code: "+ testCode);
				tester.classehinzufuegen(testName, testCode, true);

			}


			for(String sourceFiles : getCurrentTab().sourcesInProject()){


				System.out.println(sourceFiles);
				reader.setDestination(sourceFiles);
				String methodCode = reader.read(); 
				String methodName = StringFilter.filteredName(methodCode); 

				System.out.println("NAME: "+ methodName);
				System.out.println("Code: "+ methodCode);			
				tester.classehinzufuegen(methodName, methodCode, false);
			}

			if(tester.Fehlererkennung() == false ){
				console.appendText("Please write a Test that fails \n");
				// fuehre durch die steps
				getCurrentTab().setPhaseRED(true);
				getCurrentTab().setPhaseGREEN(false);
				getCurrentTab().setRefactoring(false);				
				
				this.aktButtons(compileREDFile, compileGREENFile, refactorFile);
				babyStep.saveDatas(); 

			}
			else {
				console.appendText("Still wrong code \n");
				console.appendText( tester.getErrorMessage()+"\n");
				this.aktButtons(compileREDFile, compileGREENFile, refactorFile);
			}



		}	
		this.aktButtons(compileREDFile, compileGREENFile, refactorFile);

	}	

	public void importFromServer(){		
		String from = "http://upload.worldofplayers.de/files10/Aufgaben.zip"; 
		String to   = folderManager.getLibaryPath(); 
		String name = "LibaryFolder"; 
		ImportManager importManager = new ImportManager(from, to, name); 
	}

	public void exportAndZip(){
		FileExport exporter = new FileExport(getCurrentTab().filesInProject(), getCurrentTab().getTabName()+".zip"); 	
		console.appendText(getCurrentTab().getTabName()+".zip is ready");
	}

	public void aktButtons(MenuItem compileREDFile, MenuItem compileGREENFile, MenuItem refactorFile){
		compileREDFile.setDisable(!getCurrentTab().isPhaseRED());
		compileGREENFile.setDisable(!getCurrentTab().isPhaseGREEN());
		refactorFile.setDisable(!getCurrentTab().isRefactoring());
	}




	class AutoSaver extends TimerTask {

		@Override
		public void run() {
			if(getCurrentTab() != null)
				if(!getCurrentTab().getWriteArea().getText().isEmpty() && !getCurrentTab().getTestArea().getText().isEmpty()){


					Writer writer = new Writer(); 
					// speichere alle Sources neu				
					String sourceCode = getCurrentTab().getWriteArea().getText(); 
					String sourceName = StringFilter.filteredName(sourceCode); 
					writer.writeJava(sourceCode, folderManager.getLibaryPath()+"\\"+getCurrentTab().getTabName()+"\\"+sourceName);

					// speichere alle Tests neu

					String testCode = getCurrentTab().getTestArea().getText(); 
					String testName = StringFilter.filteredName(testCode); 
					writer.writeJava(testCode, folderManager.getLibaryPath()+"\\"+getCurrentTab().getTabName()+"\\"+testName);
				}


		}

	}


	class BabyStep extends TimerTask {

		ArrayList<String[]> datas = new ArrayList<>(); 
		ArrayList<String[]> oldTests = new ArrayList<>(); 
		ArrayList<String[]> oldCodes = new ArrayList<>(); 

		boolean doSteps = true; 

		Reader reader = new Reader(); 
		Writer writer = new Writer(); 

		public ArrayList<String[]> lastDatas(ArrayList<String> dataDir){			

			for(String directory : dataDir){
				reader.setDestination(directory);
				String[] input = new String[2]; 
				String code = reader.read(); 
				String name = StringFilter.filteredName(code); 
				input[0] = name ; 
				input[1] = code ; 
				if(!datas.contains(input))
					datas.add(input);
				System.out.println("SAVED OLD DATAS: " + directory);
			}
			return datas; 		
		}


		public void saveDatas(){
			oldTests = lastDatas(getCurrentTab().testsInProject()); 

			oldCodes = lastDatas(getCurrentTab().sourcesInProject()); 
		}

		public boolean getStepping(){ return doSteps; }
		public void setStepping(boolean isStepping){ doSteps = isStepping; }     

		@Override
		public void run() {

			if(doSteps == true){
				if(getCurrentTab() != null)
					if(!getCurrentTab().getWriteArea().getText().isEmpty() && !getCurrentTab().getTestArea().getText().isEmpty()){		
						console.appendText("BabyStep made, be faster \n" );

						// babysteps vorbereiten 
						for(String[] des : oldTests){
							writer.writeJava(des[1],folderManager.getLibaryPath()+"\\"+ getCurrentTab().getTabName()+"\\"+ des[0]);					
						}
						for(String[] des : oldCodes){
							writer.writeJava(des[1],folderManager.getLibaryPath()+"\\"+ getCurrentTab().getTabName()+"\\"+ des[0]);					
						}

						// ergaenze dann die betreffenden Texte im Screen 
						for(String currentData : getCurrentTab().testsInProject()){
							if(StringFilter.nameFromPath(currentData).equals(StringFilter.filteredName(getCurrentTab().getTestArea().getText()))){
								reader.setDestination(currentData);
								getCurrentTab().getTestArea().setText(reader.read());
							}
						}

						for(String currentData : getCurrentTab().sourcesInProject()){
							if(StringFilter.nameFromPath(currentData).equals(StringFilter.filteredName(getCurrentTab().getWriteArea().getText()))){
								reader.setDestination(currentData);
								getCurrentTab().getWriteArea().setText(reader.read());
							}
						}
					}

			}else{
				console.appendText("No BabySteps");
			}
		}


	}


}

