package CompilerHandling;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestFailure;
import vk.core.api.TestResult;
/**
 * @author DenisStroh
 * 
 */
			

public class Tester {
	private static final char[] charArray = null;
	CompilationUnit[] compileunits;
	int n =0;
	boolean compileerror = false;
	boolean laufzeiterror = false;
	String errormessage;
	Duration CompileDuration;
	Duration TestDuration;
	int ignore = 0;
	int fails = 0;
	int pass = 0;
    

    // bekommt classname (vorallem filename)  und die class( gesamten file inhalt), als übergabeprameter,auserdem ture wenn es sich um eine junit klasse handelt
	public Tester(String classname, String compileclass,boolean Testklasse){	
		CompilationUnit compileunit= new CompilationUnit(classname,compileclass, Testklasse);
		compileunits = new CompilationUnit[n+1];
		compileunits[n]= compileunit;
		}
	public Tester(){
		compileunits = new CompilationUnit[1];
	}
	
    // bekommt classname (vorallem filename)  und die class( gesamten file inhalt), als übergabeprameter ,auserdem ture wenn es sich um eine junit klasse handelt
    public void classehinzufügen(String classname, String compileclass,boolean Testklasse){
		CompilationUnit compileunit= new CompilationUnit(classname,compileclass, Testklasse);
		n = n+1;
		CompilationUnit[] arr = new CompilationUnit[n];
		for(int i=0;i < compileunits.length;i++){ 
			arr[i]= compileunits[i];
			}
	    arr[this.n-1] = compileunit;
	    compileunits = arr;
    }
    // unterscheidet zwischen compile fehler und laufzeitfehler
    public boolean Fehlererkennung(){
		JavaStringCompiler compiler = CompilerFactory.getCompiler(compileunits);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		// speicher compilerzeit
		CompileDuration = compilerResult.getCompileDuration();
		//
	   	if(compilerResult.hasCompileErrors()){
	   		compileerror = true;
	   		errormessage = this.ErrorMessages();	
	   	}
	   	///
		TestResult testResult = compiler.getTestResult();
		try{
			fails =testResult.getNumberOfFailedTests();
			pass = testResult.getNumberOfSuccessfulTests();
			ignore = testResult.getNumberOfIgnoredTests();
			TestDuration = testResult.getTestDuration();
	   	if(testResult.getNumberOfFailedTests()!= 0){
	   		laufzeiterror = true;
	   		errormessage = this.Testrunerrors();
	
	   		}
		}catch(Exception e){
			return true;
		}
	   	if(laufzeiterror || compileerror)return true;
	   	return false;
    }

    public String Testrunerrors(){
		JavaStringCompiler compiler = CompilerFactory.getCompiler(compileunits);
		compiler.compileAndRunTests();
		TestResult testResult = compiler.getTestResult();
    	Collection<TestFailure> testfails = testResult.getTestFailures();
    	String r = "";
    	for(TestFailure t :testfails){ 
    	      System.out.println(t.getExceptionStackTrace());
    	      r = t.getExceptionStackTrace(); 
    		}
    	return r;
    }

    
    public String ErrorMessages() {
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
       ///GetMethoden
     public String getErrorMessage(){
		return errormessage;
    	 
     }
     public int getFails(){
		return fails;
    	 
     }
     public int getIgnore(){
		return ignore;
    	 
     }
     public int getPass(){
		return pass;
		}
     public Duration getCompileDuration(){
		return CompileDuration;
    	 
     }
     public Duration getTestDuration(){
		return TestDuration ;
    	 
     }
 }

