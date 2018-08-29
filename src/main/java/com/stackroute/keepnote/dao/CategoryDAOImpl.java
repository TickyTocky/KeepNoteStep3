package com.stackroute.keepnote.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

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
public class CategoryDAOImpl implements CategoryDAO
{

	private SessionFactory sessionFactory;
	Category category;
	
	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	
	@Autowired
	public CategoryDAOImpl(SessionFactory sessionFactory) 
	{
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new category
	 */
	
	public boolean createCategory(Category category) 
	{
		if(category != null)
		{
			category.setCategoryCreationDate(new Date());
			sessionFactory.getCurrentSession().save(category);
			return true;
		}
		return false;
	}

	/*
	 * Remove an existing category
	 */
	
	public boolean deleteCategory(int categoryId) 
	{
		Category ca = sessionFactory.getCurrentSession().find(Category.class, categoryId);
		if(ca != null)
		{
			sessionFactory.getCurrentSession().delete(ca);
			return true;
		}
		return false;
	}
	
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) 
	{
		if(category != null)
		{
			sessionFactory.getCurrentSession().update(category);
			return true;
		}
		return false;
	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException 
	{
		Category c2 = sessionFactory.getCurrentSession().find(Category.class, categoryId);
		if(c2 == null)
		{
			throw new CategoryNotFoundException("Category Not Found");
		}
		else
			return c2;
	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(String userId) 
	{
		@SuppressWarnings("unchecked")
		List<Category> list = sessionFactory.getCurrentSession().createQuery("FROM Category").list();
		return list;
	}
}
