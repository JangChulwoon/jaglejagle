package org.crossit.dao;

import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends AbstractDAO {

	public Integer selectCount() {
		return (Integer) selectOne("user.selectCount");
	}

}
