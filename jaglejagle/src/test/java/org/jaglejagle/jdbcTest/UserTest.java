package org.jaglejagle.jdbcTest;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/root-context.xml",
"classpath:spring/appServlet/servlet-context.xml" })
@Transactional
public class UserTest {
	
	
	
	@Test
	public void shouldSelectOne() {
		
	}
}
