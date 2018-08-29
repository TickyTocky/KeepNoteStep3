package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.dao.ReminderDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Reminder;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class ReminderServiceImpl implements ReminderService 
{
	
	ReminderDAO reminderdao;

	/*
	 * Autowiring should be implemented for the ReminderDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */

	

	/*
	 * This method should be used to save a new reminder.
	 */
	
	@Autowired
	public ReminderServiceImpl(ReminderDAO reminderdao)
	{
		this.reminderdao = reminderdao;
	}

	public boolean createReminder(Reminder reminder) 
	{
		if(reminderdao.createReminder(reminder))
			return true;
		
		return false;
	}

	/*
	 * This method should be used to update a existing reminder.
	 */

	public Reminder updateReminder(Reminder reminder, int id) throws ReminderNotFoundException 
	{
		Reminder r3 = reminderdao.getReminderById(id);
		if(r3 == null)
			throw new ReminderNotFoundException("Reminder Not Found...");
		else
		{
			reminderdao.updateReminder(r3);
			return r3;
		}
	}

	/* This method should be used to delete an existing reminder. */
	
	public boolean deleteReminder(int reminderId) 
	{
		if(reminderdao.deleteReminder(reminderId))
			return true;
		
		return false;
	}

	/*
	 * This method should be used to get a reminder by reminderId.
	 */
	
	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException 
	{
		Reminder r4 = reminderdao.getReminderById(reminderId);
		if(r4 == null)
			throw new ReminderNotFoundException("Reminder Not Found...");
		else
			return r4;
	}

	/*
	 * This method should be used to get a reminder by userId.
	 */

	public List<Reminder> getAllReminderByUserId(String userId) 
	{
		List<Reminder> reminder = reminderdao.getAllReminderByUserId(userId);
		return reminder;
	}
}
