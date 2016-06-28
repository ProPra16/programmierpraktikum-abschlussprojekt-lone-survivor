package TabsHandling;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

/***************************************************************************************
 *                                                                                     *
 * @author Marc Feger                                                                  *
 * @version 26.06.2016                                                                 *
 * @ToDo                                                                               *
 ***************************************************************************************/
public class CreatableTab extends Tab{
	
	
	ArrayList<Object> components = new ArrayList<>(); /* enthaelt alle Komponenten */ 
	String tabName = "NO Name Given"; 
	HBox hbox = new HBox(); /* Hier soll der Content des Tabs abgelegt werden.
	                           HBox kann auch bei Bedarf geändert werden.      */ 
	
	
	
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
	
    
    	
	/* Hier befinden sich alle Getter- Methoden */ 
	
	public String getTabName(){ return tabName; }
	public HBox   getHBox()   { return hbox;    }
	public ArrayList<Object> getComponents()  { return components; }	

}
