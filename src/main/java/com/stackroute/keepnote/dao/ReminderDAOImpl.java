package com.stackroute.keepnote.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Reminder;

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
public class ReminderDAOImpl implements ReminderDAO {
	
	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

	private SessionFactory sessionFactory;
	Reminder reminder;
	
	public ReminderDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new reminder
	 */

	public boolean createReminder(Reminder reminder) 
	{
		if(reminder != null)
		{
			reminder.setReminderCreationDate(new Date());
			sessionFactory.getCurrentSession().save(reminder);
			return true;
		}
		return false;
	}
	
	/*
	 * Update an existing reminder
	 */

	public boolean updateReminder(Reminder reminder)
	{
		if(reminder != null)
		{
			sessionFactory.getCurrentSession().update(reminder);
			return true;
		}
		return false;
	}

	/*
	 * Remove an existing reminder
	 */
	
	public boolean deleteReminder(int reminderId) 
	{
		Reminder ra = sessionFactory.getCurrentSession().find(Reminder.class, reminderId);
		if(ra != null)
		{
			sessionFactory.getCurrentSession().delete(ra);
			return true;
		}
		return false;
	}

	/*
	 * Retrieve details of a specific reminder
	 */
	
	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException 
	{
		Reminder r2 = sessionFactory.getCurrentSession().find(Reminder.class, reminderId);
		if(r2 == null)
		{
			throw new ReminderNotFoundException("Reminder Not Found");
		}
		else
			return r2;
	}

	/*
	 * Retrieve details of all reminders by userId
	 */
	
	public List<Reminder> getAllReminderByUserId(String userId) 
	{
		@SuppressWarnings("unchecked")
		List<Reminder> list = sessionFactory.getCurrentSession().createQuery("FROM Reminder").list();
		return list;
	}

}
