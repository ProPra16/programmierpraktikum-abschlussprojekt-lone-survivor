package SceneHandling;

import javax.swing.JOptionPane;

import TabsHandling.CreatableTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
		newFile.setOnAction((event) -> addTabToScreen());
		
		menu.getItems().addAll(newFile); 
		
		return menu; 
	}
	
	final Menu runMenu(){
        Menu menu = new Menu("Run"); 
		
		/* fuege die komponenten hinzu */ 
		MenuItem runFile = new MenuItem("Run"); 
		runFile.setOnAction((event) -> System.out.println(getTextOfCurrentTab()));
		
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
		borderPane.setLeft(tabPane);
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
		return output;    
	}

	private void addTabToScreen(){
		TextArea text = new TextArea();
		TextArea test = new TextArea(); 
		Label    label = new Label("Hier koennte Ihre Werbung stehen "); 
		
		CreatableTab newTab = new CreatableTab(JOptionPane.showInputDialog("New File Name")); 
		
		text.setText(
				"public class "+ newTab.getTabName()+""
						+ "{\n"
						+ "// auto- generated code \n\n"
						+ "}");
		
		test.setText(
				"public class "+ newTab.getTabName()+"Test"
						+ "{\n"
						+ "// auto- generated code \n\n"
						+ "}");
		
		
		newTab.setWriteArea(text);
		newTab.setTestArea(test);
		newTab.setConsoleMsg(label);
		newTab.addAllComponents(); 
		tabPane.getTabs().add(newTab); 
	}
	

	

}
