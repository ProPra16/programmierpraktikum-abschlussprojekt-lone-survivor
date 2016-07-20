package GUI.Handling;

import SceneHandling.Screen;
import TabsHandling.CreatableTab;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/***************************************************************************************
 *                                                                                     *
 * @author Marc Feger                                                                  *
 * @version 26.06.2016                                                                 *
 * @ToDo                                                                               *
 ***************************************************************************************/
public class MainApp extends Application {	
	
   public static void main(String[] args) {
      Application.launch(args);
   }
 
   @Override
   public void start(Stage mainStage) {
      mainStage.setTitle("ADAMS"); // Andreas, Andreas, Denis, Marc, Sebastian      
      mainStage.getIcons().add(new Image("bilder/icon.jpg"));
      
      mainStage.setScene(new Screen(640,400));
      mainStage.show();
   }
}

