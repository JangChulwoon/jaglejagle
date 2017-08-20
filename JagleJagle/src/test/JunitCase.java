package test;


import org.junit.Test;
import dbconfig.DBInit;


public class JunitCase {
	
	@Test
	public void Test(){
		
		DBInit init = new DBInit();
		init.init();
	}
	
	
	

}
