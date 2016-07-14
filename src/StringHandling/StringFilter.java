package StringHandling;

import FileHandling.Writer;
/***************************************************************************************
 * @author Marc Feger                                                                  *
 ***************************************************************************************/

public class StringFilter {
	
	
	public static String filteredName(String input){
		
		String name = input.substring(input.lastIndexOf("class")+5, input.indexOf("{")).replaceAll(" ", "");
		name = name.replaceAll("\n", "");
		name = name.replaceAll(" ", "");
		name = name.replaceAll("\t", "");		
		return name ; 
	}
    public static String nameFromPath(String input){
		
		String name = input.substring(input.lastIndexOf("\\")+1, input.indexOf(".java")).replaceAll(" ", "");
		name = name.replaceAll("\n", "");
		name = name.replaceAll(" ", "");
		name = name.replaceAll("\t", "");		
		return name ; 
	}
	
	
	public void saveCurrentText(String Name, String destination, String content){
		String fileName = filteredName(Name); 
		System.out.println("I SAVED: "+fileName);
		
		Writer writer = new Writer(); 
		writer.writeJava(content, destination+"\\"+ Name);
			
	}

}
