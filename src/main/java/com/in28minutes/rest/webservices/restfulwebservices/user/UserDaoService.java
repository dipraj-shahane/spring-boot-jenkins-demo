package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	protected static List<User> users = new ArrayList<>();
	
	public static int userCount = 4;
	
	static {
		users.add(new User(1, "Dipraj", new Date()));
		users.add(new User(2, "Yuvraj", new Date()));
		users.add(new User(3, "Sujata", new Date()));
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++userCount);	
		}
		users.add(user);
		return user;
	}

	public User findOne(int id) {
		for (User user : users) {
			if (id == user.getId()) {
				return user;
			}
		}
		return null;
	}
	
	public User deleteById(int id) {
		final Iterator<User> iterator = users.iterator();
		while ( iterator.hasNext()) {
			User user = iterator.next();
			if (user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}
}
