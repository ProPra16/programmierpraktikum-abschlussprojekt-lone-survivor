package Maincomponents;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Writer{
	String input;
	String fileName;
	
	public Writer(){
	}
	
	public Writer(String input, String fileName){
		setInput(input);
		setFileName(fileName);
	}
	
	public void setInput(String input){
		this.input=input;
	}
	public void setFileName(String fileName){
		this.fileName=fileName;
	}
	
	
	public void write(String input, String fileName){
		try{
		
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
			BufferedWriter writer = new BufferedWriter(outputStreamWriter);
			writer.write(input);
			writer.flush();
			writer.close();
			
		}
		
		catch (IOException e){
			
		}
	}
}
