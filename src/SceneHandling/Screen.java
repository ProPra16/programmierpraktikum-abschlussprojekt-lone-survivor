package SceneHandling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import CompilerHandling.Tester;
import FileHandling.FolderManager;
import TabsHandling.CreatableTab;
import TabsHandling.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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

/***************************************************************************************
 *                                                                                     *
 * @author Marc Feger                                                                  *
 * @version 27.06.2016                                                                 *
 * @ToDo - initMenuePane                                                               *
 *       - initTabPane                                                                 *
 *       - getTextOfCurrentTab                                                         *
 *                                                                                     *
 ***************************************************************************************/

public class Screen extends Scene{

	  static Group root = new Group();
	  
	  BorderPane borderPane = new BorderPane(); // Hier wird alles drauf abgebildet
	
	  TabPane tabPane = new TabPane(); // Fuer die Anzeige der Tabs
	  
	  HBox menuePane = new HBox(); // Fuer die Anzeige der Buttons
	  
	  MenuBar menuBar = new MenuBar();

      CreatableTab currentTab; 
    
      HashMap<String, CreatableTab> tabs = new HashMap<>();
      
      FolderManager folderManager = new FolderManager(); 
    
      List<String> dialogData = folderManager.getFolderNames(); 
      
      Label label = new Label("Konsole"); 
      TextArea console = new TextArea(); 
       
	  
	  
	public Screen(double width, double height) {
		super(root, width, height);
		
		initComponents();
		
		root.getChildren().addAll(borderPane); 
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
	 *       - Die Buttons müssen den Aktuellen Reiter etc. kennen
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
		 menuBar.getMenus().addAll(fileMenu(), runMenu());  		
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
		newFile.setOnAction((event) -> showChoiceInput());
		
		menu.getItems().addAll(newFile); 
		
		return menu; 
	}
	
	final Menu runMenu(){
        Menu menu = new Menu("Run"); 
		
		/* fuege die komponenten hinzu */ 
		MenuItem runFile = new MenuItem("Run"); 
		
		runFile.setOnAction((event) -> compileProjekt());
		
		menu.getItems().addAll(runFile); 
		
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
	
	public void compileProjekt(){
		
		//ArrayList<String> errors = tester.filesWithSyntaxError(getCurrentTab().filesInProject()); 
		System.out.println("TESTS ARE: \n"+getCurrentTab().testsInProject());
		System.out.println("SOURCES ARE: \n"+getCurrentTab().sourcesInProject());
		Task tab = getCurrentTab(); 
		for(String errorMsg : tab.compileAndShowResult())
		console.appendText(errorMsg); 
		
	}
	

	
	

}
