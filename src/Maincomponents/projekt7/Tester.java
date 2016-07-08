package projekt7;

import java.util.Set;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;

public class Tester {

    
    // bekommt classname (vorallem filename)  und die class( gesamten file inhalt), als übergabeprameter 
	// Achtung!!!! gibt true bei nicht bestandenen test und flas bei bestandentest aus.
    public boolean compiletest(String classname, String compileclass){
		CompilationUnit compileunit= new CompilationUnit(classname,compileclass, false);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(compileunit);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
    	return compilerResult.hasCompileErrors();
    	
    }
    
    ////////////////////////////////////////////////
    // bekommt classname (vorallem filename)  und die class( gesamten file inhalt), als übergabeprameter 
    
    public String getErrorMessages(String classname, String compileclass) {
    	// Erzeugt die CompilationUnit:
		CompilationUnit compileunit= new CompilationUnit(classname,compileclass, false);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(compileunit);
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

}
