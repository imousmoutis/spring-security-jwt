package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.model.User;
import spring.repository.UserDAO;

@Service("userService")
@Transactional
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService{
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public User findByUsername(String username) {
		
		return userDAO.findByUsername(username);
	}
}
