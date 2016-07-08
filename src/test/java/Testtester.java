import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Testtester {
	@Test
	public void test1() {
		  Tester t = new Tester();
		  System.out.println(t.getErrorMessages("Foo","public class Foo {}"));
		  assertTrue(!t.compiletest("Foo","public class Foo {}"));
	}
	@Test
	public void test2() {
		  Tester t = new Tester();
		  System.out.println(t.getErrorMessages("Foo","public class Foo {"
		  		+ "int x = 5+5;"
		  		+ "}"));
		  assertTrue(!t.compiletest("Foo","public class Foo {"
			  		+ "int x = 5+5;"
			  		+ "}"));
		  
	}
	
	@Test
	public void test3(){
		Tester t= new Tester();
		  String Syntax=   "import static org.junit.Assert.assertEquals; "
		  		    + "import org.junit.Test;"
		  		    + "public class Foo{ "
				    + "@Test "
		  		    +"public void testatest(){"
		  		    +"assertEquals(4,5);"
		  		    + "}"
			  		+ "}";
		  System.out.println(t.getErrorMessages("Foo",Syntax));
		  assertTrue(!t.compiletest("Foo",Syntax));

	}

}

