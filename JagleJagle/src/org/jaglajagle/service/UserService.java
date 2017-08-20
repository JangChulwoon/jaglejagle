package org.jaglajagle.service;

import org.jaglejagle.domain.User;

interface UserService {
	// INSERT
	// UPDATE 
	// SELECT 
	// DELETE 
	boolean selectOne(String id, String password);
	
	long insert(User user);
	
	boolean updateRecentDate(String id);

}
