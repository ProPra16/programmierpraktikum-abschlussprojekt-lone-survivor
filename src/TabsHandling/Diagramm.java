package TabsHandling;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.util.Duration;
/***************************************************************************************
 * @author Marc Feger                                                                  *
 ***************************************************************************************/

public class Diagramm extends PieChart{
	ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
	
	PieChart.Data ERRORd = new PieChart.Data("Errors", 0); 
	PieChart.Data TIMEd   = new PieChart.Data("Time", 0); 
	PieChart.Data REDd    = new PieChart.Data("RED", 0); 
	PieChart.Data GREENd   = new PieChart.Data("GREEN", 0); 
	PieChart.Data REFACTORd    = new PieChart.Data("REFACTOR", 0); 	
	
	int RED = 0; 
	int GREEN = 0; 
	int REFACTOR = 0; 	
	int ERROR = 0; 

    
	public Diagramm(String title){
		super.setTitle(title);
		chartData.addAll(ERRORd, TIMEd, REDd, GREENd, REFACTORd);
		setData(getChartDatas());
		bindToTime();  
	}
	
	

	public void setChartData(PieChart.Data newData ){		
		// ergaenze ein schon vorkommenden Wert
		if(chartData.contains(newData.getName())){
			System.out.println("VORHANDEN");
			chartData.remove(newData);
			chartData.add(newData); 
		}else{		
		chartData.add(newData); 
		}
		
	}
	
	public void setREDValue(int value){ RED = value;  }
	public void setGREENValue(int value){ GREEN = value; }
	public void setREFACTORValue(int value){ REFACTOR = value;  }
	public void setERRORValue(int value){ ERROR = value; }
	
	
	public void calcDatas(){
		chartData.removeAll(chartData); 		
		REDd       = new PieChart.Data("RED", RED); 
		GREENd     = new PieChart.Data("GREEN", GREEN); 		
		REFACTORd  = new PieChart.Data("REFACTOR", REFACTOR); 		
		ERRORd     = new PieChart.Data("ERROR", ERROR);				
		chartData.addAll( REDd, GREENd, REFACTORd, ERRORd);
	}
	
	
	public void aktualisiere(){
		calcDatas(); 
		setData(getChartDatas());
	}


	public void bindToTime(){
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(0),
						new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) { 
						aktualisiere(); 
					}
				}
						),
				new KeyFrame(Duration.seconds(60))
				);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	public  ObservableList<PieChart.Data> getChartDatas(){ return chartData; } 


}
