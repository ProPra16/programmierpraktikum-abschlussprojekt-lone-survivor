package CompilerHandling;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Testtester {
	@Test
	public void test1() {
		  Tester t = new Tester("Foo","public class Foo {}",false);
		  assertTrue(!t.compiletest());
	}
	@Test
	public void test2() {			
		  Tester t = new Tester("Foo",
				    "public class Foo {"
			  		+ "int x = 5+5;"
			  		+ "}",false);
		  assertTrue(!t.compiletest());
		  
	}

	@Test
	public void test3(){
		//dieser test soll fehlschlagen

		  String Syntax= "import org.junit.Test;"
				    + "import static org.junit.Assert.assertEquals;"
		  		    + "public class Foo{ "
				    + "@Test "
		  		    +"public void testatest(){"
		  		    +"assertEquals(4,5);"
		  		    + "}"
			  		+ "}";
		 Tester t= new Tester("Foo",Syntax,true);
		  assertTrue(!t.compiletest());
		  assertFalse(t.Testrun());

	}
   @Test
	public void test4(){
		//nullpouint exaption 
		  String Syntax=  "public class fehler{"
		          +"public static int giv(){"
				  +"int[] stringArray = new int [10];"
		          +"return stringArray[11];"
		          +"}"
		          +"}";
          // keine compilier errors
		  String Syntax1=  "import org.junit.Test;"
				    + "import static org.junit.Assert.assertEquals;"
		  		    + "public class Foo{ "
				    + "@Test "
		  		    +"public void testatest(){"
		  		    +"assertEquals(fehler.giv(),5);"
		  		    + "}"
			  		+ "}";
		  Tester t= new Tester("fehler",Syntax,false);
          t.classehinzufügen("Foo",Syntax1,true);
 
		  assertTrue(!t.compiletest());
		  assertFalse(t.Testrun());

              }       
}

