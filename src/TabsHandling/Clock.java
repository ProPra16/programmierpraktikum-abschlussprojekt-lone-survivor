package TabsHandling;
import java.sql.Time;
import java.util.Calendar;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class Clock extends Label{
	

		
	  public boolean isSelected = false;  
	  public boolean timeIsUp   = false; 
	  int time = 0; 
	  int ende = 0; 
	  
	  public Clock(boolean selected) {
	      isSelected = selected; 
		  bindToTime();
	  }

	  private void bindToTime() {	
	    Timeline timeline = new Timeline(
	      new KeyFrame(Duration.seconds(0),
	        new EventHandler<ActionEvent>() {
	          @Override
	          public void handle(ActionEvent actionEvent) { 
	        	
	        	setStyle("-fx-border-color:white; -fx-background-color: white");

	        	if(isSelected){	 

	        		if(time < ende){
	        			timeIsUp = false; 
	        			time = time + 1;
	        		}
	        		else{
	        			timeIsUp = true; 
	        			time = 0; 
	        		}

	        		setText("Verstrichene Zeit: "+time+".sek von "+ ende); 
	        	}
	        	else{

	        	}
	        	
	          }
	        }
	      ),
	      new KeyFrame(Duration.seconds(1))
	    );
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	  }
	  

	  public void setTime(int newTime){ time = newTime; }
	  public void setEnde(int newEnde){ ende = newEnde; }
	  public void setTimeIsUp(boolean isUp){ timeIsUp = isUp;}
	  public void setSelected(boolean selected){ isSelected = selected; }

	  public boolean timeIsUp(){return timeIsUp; }
	  
	



}
