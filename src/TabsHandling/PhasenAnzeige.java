package TabsHandling;

import java.util.Calendar;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

class PhasenAnzeige extends Label {
	
  public boolean red, green, refactor,AkzeptanzRed,AkzeptanzGreen = false; 
  
  public PhasenAnzeige(boolean a, boolean b, boolean c,boolean d,boolean e) {
      red = a; 
      green = b; 
      refactor = c; 
      AkzeptanzRed = d;
      AkzeptanzGreen = e;
	  bindToTime();
  }

  private void bindToTime() {	
    Timeline timeline = new Timeline(
      new KeyFrame(Duration.seconds(0),
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) { 
        	
        	setStyle("-fx-border-color:black; -fx-background-color: white ");  
        	  
        	if(red) { 
        	setStyle("-fx-border-color:black; -fx-background-color: red");
        	setText("PHASE: RED                                                             "
        			+ "                                                                     "); 
        	}
        	 if(green){
        	setStyle("-fx-border-color:black; -fx-background-color: green");
        	setText("PHASE: GREEN                                                           "
        			+ "                                                                     "); 
        	}
        	 if(refactor){
        	setStyle("-fx-border-color:black; -fx-background-color: blue");             
        	setText("PHASE: REFACTORING                                                     "
        			+ "                                                                     "); 
          }  
        	 if(AkzeptanzRed){
        	setStyle("-fx-border-color:black; -fx-background-color: pink");             
        	setText("PHASE: Akzeptanztest Schreiben                                                     "
        			+ "                                                                     "); 
          }  
        	 if(AkzeptanzGreen){
        	setStyle("-fx-border-color:black; -fx-background-color: yellow");             
        	setText("PHASE: Akzeptanztest                                                     "
        			+ "                                                                     "); 
          }  
        	
          }
        }
      ),
      new KeyFrame(Duration.seconds(0.1))
    );
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }
  
  public void setPhaseRED(boolean status)   { red = status; }
  public void setPhaseGREEN(boolean status) { green = status; }
  public void setRefactoring(boolean status){ refactor = status; }
  public void setAPhaseRed(boolean status){   AkzeptanzRed = status; }
  public void setAPhaseGreen(boolean status){ AkzeptanzGreen = status; }
  
  

  
}

