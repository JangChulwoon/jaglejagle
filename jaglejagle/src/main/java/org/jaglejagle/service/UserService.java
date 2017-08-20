package org.jaglejagle.service;

import org.jaglejagle.domain.User;

public interface UserService {
	
	boolean selectOne(String id, String passworld);
	
	long insert(User user);
	
	boolean updateRecentDate(String id);

}
