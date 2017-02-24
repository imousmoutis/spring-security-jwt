package spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import spring.model.User;
import spring.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/api/user/add", method = RequestMethod.POST)
	public User addUser(User newUser, HttpServletResponse response){
		
		return userService.create(newUser);
	}
	
	@RequestMapping(value = "/api/user/get/{userId}", method = RequestMethod.GET)
	public User getUser(@PathVariable("userId") Integer userId, HttpServletResponse response){
		
		return userService.findById(userId);
	}
	
	@RequestMapping(value = "/api/user/get", method = RequestMethod.GET)
	public List<User> getAllUsers(HttpServletResponse response){
		
		return userService.findAll();
	}
	
	@RequestMapping(value = "/api/user/update", method = RequestMethod.POST)
	public User updateUser(User newUser, HttpServletResponse response){
		
		return userService.update(newUser);
	}
	
	@RequestMapping(value = "/api/user/delete/{userId}", method = RequestMethod.POST)
	public User deleteUser(@PathVariable("userId") Integer userId, HttpServletResponse response){
		
		return userService.delete(userId);
	}

}
