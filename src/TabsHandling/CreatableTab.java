package TabsHandling;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/***************************************************************************************
 * @author Marc Feger                                                                  *
 ***************************************************************************************/
public class CreatableTab extends Tab{
	
	
	ArrayList<Object> components = new ArrayList<>(); /* enthaelt alle Komponenten */ 
	String tabName = "NO Name Given"; 
	HBox hbox = new HBox(); /* Hier soll der Content des Tabs abgelegt werden.
	                           HBox kann auch bei Bedarf geändert werden.      */ 
	
	VBox Vbox = new VBox();
	StackPane stackPane = new StackPane();
	
	TextArea writeArea = null; 
	TextArea testArea  = null; 
	TextArea taskArea  = null; 
	TreeView contents  = null; 
	Label    status    = null; 
	Label    time      = null; 
	
	Label    consoleMsg  = new Label(""); 
	
	public CreatableTab(String name){
		super.setText(name);
		     tabName = name; 		     
	}
	
	
	/* Hier befinden sich alle Modifier- Methoden */
	
	public void addTextArea(TextArea... textArea){
		hbox.getChildren().addAll(textArea); 
		super.setContent(hbox);
	}
	
	public void addTextLabel(Label... label){
		hbox.getChildren().addAll(label); 
		super.setContent(hbox);
	}
	
	public void addButton(Button... button){
		hbox.getChildren().addAll(button); 
		super.setContent(hbox);
	}
	
	public void removeAll(Object... obj){
	        while(hbox.getChildren().size() != 0)
			hbox.getChildren().remove( hbox.getChildren().get( hbox.getChildren().size()- 1 ));  
	}
	
	public void removeObject(Object obj){
		hbox.getChildren().remove(obj); 
	}
	
	public void addAllComponents(){
		
		if(writeArea != null && !hbox.getChildren().contains(writeArea))
			hbox.getChildren().add(writeArea);
		if(testArea != null&& !hbox.getChildren().contains(testArea))
			hbox.getChildren().add(testArea);
		if(taskArea != null&& !hbox.getChildren().contains(taskArea))
			hbox.getChildren().add(taskArea);
		if(consoleMsg != null&& !hbox.getChildren().contains(consoleMsg))
			hbox.getChildren().add(consoleMsg);
		if(contents != null){		
			Vbox.getChildren().addAll(contents); 
			
		}
		if(time != null){
			Vbox.getChildren().add(time); 
			
		}
		if(status != null){
			Vbox.getChildren().add(status); 
		}
		
		hbox.getChildren().addAll(Vbox); 

		super.setContent(hbox); 
	}

	/*
	 * Die aktuellen Komponenten des Tabs werden gespeichert 
	 */
	public void aktComponents(){
		HashSet<Object> elements = new HashSet<Object>(); 	
		components.stream().forEach(obj -> elements.add(obj));
		hbox.getChildren().stream().forEach(obj -> elements.add(obj));
		components = new ArrayList<>(); 
		elements.stream().forEach(obj -> components.add(obj));
	}
	
	
	
	/* Hier befinden sich alle Setter- Methoden */ 
	public void setTabName(String newName){ tabName = newName; }
    public void setHBox(HBox newHBox)     { hbox = newHBox;    }
    public void setComponents(ArrayList<Object> newComp){ components = newComp; }
    public void setConsoleMsg(Label newLabel){
    	    
    	  
    		removeObject(consoleMsg);
    		consoleMsg = new Label(newLabel.getText()); 
    		System.out.println("New Label");
    		aktComponents();
    		
    	
    }
    
    public void setContentTree(TreeView newView){ contents = newView;}
    public void setStatusLabel(Label label)     { status = label ; }
    public void setTimeLabel(Label label)       { time = label;} 
    
	public void setWriteArea(TextArea newArea){	writeArea = newArea;  }
	public void setTestArea(TextArea newArea){ testArea = newArea; }
	public void setTaskArea(TextArea newArea){ taskArea = newArea; }
	
    
    	
	/* Hier befinden sich alle Getter- Methoden */ 
	
	public String getTabName(){ return tabName; }
	public HBox   getHBox()   { return hbox;    }
	public ArrayList<Object> getComponents()  { return components; }	
	public TextArea getWriteArea(){ return writeArea; }
	public TextArea getTestArea(){ return testArea; }
	public TextArea getTaskArea(){return taskArea; }
	public Label getConsoleMsg(){ return consoleMsg; }
	public TreeView getTreeView(){ return contents; }

}
