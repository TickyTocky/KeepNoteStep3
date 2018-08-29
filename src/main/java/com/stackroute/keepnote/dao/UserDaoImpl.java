package com.stackroute.keepnote.dao;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class UserDaoImpl implements UserDAO {

	private SessionFactory sessionFactory;
	User user;

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired

	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;

	}

	/*
	 * Create a new user
	 */

	public boolean registerUser(User user) {
		user.setUserAddedDate(new Date());
		sessionFactory.getCurrentSession().save(user);
		return true;
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(User user) {
		if (user != null) {
			sessionFactory.getCurrentSession().update(user);
			return true;
		} else
			return false;

	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(String UserId) {
		user = (User) sessionFactory.getCurrentSession().find(User.class, UserId);
		return user;
	}

	/*
	 * validate an user
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {

		if (getUserById(userId) == null) {
			throw new UserNotFoundException("UserNotFoundException");
		} else {
			if (password.equals(getUserById(userId).getUserPassword())) {
				return true;
			} else {

				return false;

			}
		}
	}

	/*
	 * Remove an existing user
	 *
	 */
	public boolean deleteUser(String userId) {
		if (getUserById(userId) == null)
			return false;

		else {

			sessionFactory.getCurrentSession().delete(getUserById(userId));
			return true;

		}

	}
}
