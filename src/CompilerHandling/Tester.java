package CompilerHandling;


import java.util.ArrayList;
import java.util.Set;

import FileHandling.Reader;
import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;
		

public class Tester {
	CompilationUnit[] compileunits;
	int n =1;
    

    // bekommt classname (vorallem filename)  und die class( gesamten file inhalt), als übergabeprameter,auserdem ture wenn es sich um eine junit klasse handelt
	public Tester(String classname, String compileclass, boolean Testklasse){	
		CompilationUnit compileunit= new CompilationUnit(classname,compileclass, Testklasse);
		compileunits = new CompilationUnit[1];
		compileunits[0]= compileunit;
		}
	
    // bekommt classname (vorallem filename)  und die class( gesamten file inhalt), als übergabeprameter ,auserdem ture wenn es sich um eine junit klasse handelt
    public void classehinzufügen(String classname, String compileclass,boolean Testklasse){
		CompilationUnit compileunit= new CompilationUnit(classname,compileclass, Testklasse);
		n = n+1;
		CompilationUnit[] arr = new CompilationUnit[n];
		for(int i=0;i < compileunits.length;i++){ 
			System.out.println(i);
			arr[i]= compileunits[i];
			}
	    arr[this.n-1] = compileunit;
	    compileunits = arr;
    }
    

	// Achtung!!!! gibt true bei nicht bestandenen test und flas bei bestandentest aus.
    public boolean compiletest(){
		JavaStringCompiler compiler = CompilerFactory.getCompiler(compileunits);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		compiler.getTestResult();
    	return compilerResult.hasCompileErrors();
    	
    }
    // false mindestens ein test nicht geklappt hat und true wenn alles richtig läuft
    public boolean Testrun(){
		JavaStringCompiler compiler = CompilerFactory.getCompiler(compileunits);
		compiler.compileAndRunTests();
		TestResult testResult = compiler.getTestResult();
		if(testResult.getNumberOfFailedTests()== 0 )return true;
        else {
        	return false;
        }
    }
    ////////////////////////////////////////////////
    // gibt die Errors aus 
    
    public String getErrorMessages() {
    	// 
		JavaStringCompiler compiler = CompilerFactory.getCompiler(compileunits);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		//////// 
		Set<String> cus = compiler.getAllCompilationUnitNames();
		StringBuilder sb = new StringBuilder();
		for (String cuName : cus) {
			CompilationUnit cu = compiler.getCompilationUnitByName(cuName);
			for (CompileError compileError : compilerResult.getCompilerErrorsForCompilationUnit(cu)) {
				sb.append(compileError.toString());
				sb.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}

 					


    /**
     *@author MarcFeger 
     * Diese Methode soll eine ArrayList erstellen die den Projekt Ordner
     * eines aktuellen Tab auf Syntax Fehler durchsucht und diese Speicher
     */

    public ArrayList<String> filesWithSyntaxError(ArrayList<String> filesInFolder){
    	ArrayList<String> filesWithError = new ArrayList<>(); 	
    	Reader reader = new Reader(); 	
    	
    	//gehe durch alle Files in dem Ordner aus dem du kommst 
    	for(String currentFile : filesInFolder){
    		if(currentFile.endsWith(".java")){
    			System.out.println("LOOKING IN:" +currentFile);
    			reader.setDestination(currentFile);
    			String classCode = reader.read(); 
    			String className = classCode.substring(classCode.indexOf("class"),classCode.indexOf("{")); 
    			className = className.replace("class",""); 
    			className = className.replaceAll(" ",""); 
    			System.out.println("CLASSNAME: "+className);
    			System.out.println(classCode);
           //     filesWithError.add("Detected Error in: "+ className +"\n"+
          //                         "Errors are: \n"+
           //                        getErrorMessages(className, classCode)); 
    			
    		}

    	}

    	
    	return filesWithError; 
    }

}













