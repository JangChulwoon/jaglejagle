package org.crossit.service;

import org.crossit.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDAO userDAO;

	@Override
	public Integer selectCount() {
		// TODO Auto-generated method stub
		return userDAO.selectCount();
	}
	
	
}
