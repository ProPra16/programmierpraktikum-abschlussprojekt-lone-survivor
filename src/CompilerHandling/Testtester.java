<<<<<<< HEAD
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

=======
package CompilerHandling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Testtester {
	@Test
	public void test1() {
		  Tester t = new Tester("Foo","public class Foo {}",false);
		  assertFalse(t.Fehlererkennung());
	}
	@Test
	public void test2() {
		  Tester t = new Tester("Foo",
				    "public class Foo {"
			  		+ "int x = 5+5;"
			  		+ "}",false);
		  assertFalse(t.Fehlererkennung());
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
		  assertTrue(t.Fehlererkennung());	
		  assertEquals(t.getFails(),1);}
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
				    +"System.out.println(fehler.giv());"
		  		    +"assertEquals(fehler.giv(),5);"
		  		    + "}"
			  		+ "}";
		  Tester t= new Tester("fehler",Syntax,false);
          t.classehinzufügen("Foo",Syntax1,true);
		  assertTrue(t.Fehlererkennung());
		 // assertEquals(t.getFails(),1);
              }       
	@Test
	public void test5(){
		//

		  String Syntax= "import org.junit.Test;"
				    + "import static org.junit.Assert.assertEquals;"
		  		    + "public class Foo{ "
				    + "@Test "
		  		    +"public void testatest(){"
		  		    +"assertEquals(5,5);"
		  		    + "}"
			  		+ "}";
		 Tester t= new Tester("Foo",Syntax,true);
		  assertFalse(t.Fehlererkennung());
		  assertEquals(t.getPass(),1);

	}
	@Test
	public void test6(){
		//dieser test soll fehlschlagen, der assert ist false wenn ein runtimeerror vor dem assert ist. und er wird endeckt!

		  String Syntax= "import org.junit.Test;"
				    + "import static org.junit.Assert.assertEquals;"
		  		    + "public class Foo{ "
				    + "@Test "
		  		    +"public void testatest(){"
				    +"int [] a= new int[2];"
		  		    +"a[2]=100;"
		  		    +"assertEquals(6,5);"
		  		    + "}"
			  		+ "}";
		 Tester t= new Tester("Foo",Syntax,true);
		  assertTrue(t.Fehlererkennung());
		  assertEquals(t.getFails(),1);
	}
	@Test
	public void test7(){
		//

		  String Syntax= 
				      "import org.junit.Test;"
				    + "import static org.junit.Assert.assertEquals;"
				  
		  		    + "public class Foo{ "
				    
				    + "@Test "
		  		    +"public void testatest(){"
				    
		  		    +"assertEquals(fehler.giv(),5);"
		  		    
		  		    + "}"
		  		    
			  		+ "}";
		  
		 String Syntax2= 
			      "import org.junit.Test;"
			    + "import static org.junit.Assert.assertEquals;"
			  
	  		    + "public class Bar{ "
	  		    + "public int five(){ return 5; }"
		  		+ "}";
		  
		  
		  
		  
		  String Syntax1=
				    
				   "public class fehler{"
				  +"Bar = new Bar(); "
				  +"public static void main(String[] args){}"
				  
		          +"public static int giv(){"
		          +"return bar.five();"
		          +"}"
		          
		          +"public static int giv2(){"
				  +"int[] stringArray = new int [10];"
		          +"return stringArray[11];"
		          +"}"
		          
		          +"}";
		  
		  
		 Tester t= new Tester();
		
		 t.classehinzufügen("Foo",Syntax,true);
         t.classehinzufügen("fehler",Syntax1,false);
         t.classehinzufügen("Bar", Syntax2, false);
         System.out.println(t.Fehlererkennung()); 
		  assertEquals(t.getPass(),0);

	}
	@Test
	public void test8(){
		  String Syntax= 
				    "import org.junit.Test;"
			        + "import static org.junit.Assert.*;"
		 			+"public class FibonacciTest { "
					+ " @Test"
		            + "   public void test1(){"
		            + "     assertEquals(1,1);"
		            + "   }"
		            + "@Test"
		            + "    public void test(){}"
		 			+"} ";
		 Tester t= new Tester("FibonacciTest",Syntax,true);
		 assertFalse(t.Fehlererkennung());
		 assertEquals(t.getPass(),2);
	}
}

>>>>>>> refs/remotes/origin/DenisStuff
