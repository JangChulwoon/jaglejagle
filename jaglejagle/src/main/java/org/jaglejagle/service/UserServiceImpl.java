package org.jaglejagle.service;

import org.jaglejagle.dao.UserDao;
import org.jaglejagle.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// controller �� ��û�� ���� ó�� .
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao dao;

	/**
	 * service ���� 1. dao ���� �����ͼ� � ó���� �Ѵٰų� .. 2. �Ѱܹ��� ������ ���� ��ȿ���˻�
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
