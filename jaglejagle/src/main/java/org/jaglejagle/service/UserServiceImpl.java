package org.jaglejagle.service;

import org.jaglejagle.dao.UserDao;
import org.jaglejagle.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// controller 가 요청한 것을 처리 .
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao dao;

	/**
	 * service 역할 1. dao 값을 가져와서 어떤 처리를 한다거나 .. 2. 넘겨받은 값들의 대한 유효성검사
	 */
	@Override
	public boolean selectOne(String id, String passworld) {
		// TODO Auto-generated method stub
		if (id == null || passworld == null || "".equals(id) || "".equals(passworld)) {
			return false;
		}
		dao.selectOne();
		return true;
	}

	@Override
	public long insert(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateRecentDate(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
