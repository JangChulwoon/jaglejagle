package org.jaglejagle.dao;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao  extends AbstractDAO{
	
	public Integer selectOne() {
		return (Integer) selectOne("user.insert");
	}
}
