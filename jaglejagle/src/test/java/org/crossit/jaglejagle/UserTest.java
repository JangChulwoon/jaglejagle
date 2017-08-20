package org.crossit.jaglejagle;

import org.crossit.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/root-context.xml",
"classpath:/spring/appServlet/context-*.xml"})
@Transactional
public class UserTest {
	
	@Autowired
	UserService service;
	
	@Test
	public void shouldSelectOne() {
		System.out.println(service.selectCount());
	}

}
