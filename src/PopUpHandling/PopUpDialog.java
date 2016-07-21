package PopUpHandling;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

/***************************************************************************************
 *                                                                                     *
 * @author Marc Feger                                                                  *
 * @version 02.07.2016                                                                 *
 ***************************************************************************************/
public class PopUpDialog {
	
	public PopUpDialog(){
		
	}
	
	public void showXmlNotFound(){
		Alert noXmlAlert = new Alert(AlertType.WARNING,null);
		noXmlAlert.setContentText("404: XML-File not found"+"\n"+
				"Please add XML File and press OK");
		ButtonType doNothing = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		noXmlAlert.getButtonTypes().setAll(doNothing);
		ImageView view = new ImageView("stopIt.jpg"); 
		view.setFitWidth(100);
		view.setFitHeight(100);
		noXmlAlert.setGraphic(view);

		Optional<ButtonType> result = noXmlAlert.showAndWait();
		if (result.get() == doNothing){
		  return ; 		    
		}
		
		 
	}
	
	public boolean deleteOldProject(ArrayList<String> filesInFolder){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Existing Project");
		alert.setHeaderText("Existing Project found");
		alert.setContentText("AADMS found existing Project"+"\n"+
	                         "Continue: For working with old Project"+"\n"+
			                 "Reset: For reset Project");
        
		ButtonType continueBtn = new ButtonType("Continue");
		ButtonType resetBtn = new ButtonType("Reset");
		
	//	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ImageView view = new ImageView("bilder/oldFilesFound.jpg"); 
		//ImageView view = new ImageView(); 
		view.setFitWidth(100);
		view.setFitHeight(100);
		alert.setGraphic(view);
		alert.getButtonTypes().setAll(continueBtn, resetBtn);
       
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == continueBtn){
		    System.out.println("CHOOSEN: Continue");
		    return false; 
		    
		} else if (result.get() == resetBtn) {
			System.out.println("CHOOSEN: Reset"); 
			for(String directory : filesInFolder){
				System.out.println("DELETE: "+directory);
				File file = new File(directory); 
				if(directory.contains(".java"))
				file.delete(); 
			}
			return true; 
		}
		return false; 
	}

}
