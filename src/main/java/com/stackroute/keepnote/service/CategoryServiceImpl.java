package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

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
public class CategoryServiceImpl implements CategoryService 
{
	/*
	 * Autowiring should be implemented for the CategoryDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */

	/*
	 * This method should be used to save a new category.
	 */
	
	CategoryDAO categorydao;
	
	@Autowired
	public CategoryServiceImpl(CategoryDAO categorydao)
	{
		this.categorydao = categorydao;
	}
	
	public boolean createCategory(Category category) 
	{
		if(categorydao.createCategory(category))
			return true;
		
		return false;
	}

	/* This method should be used to delete an existing category. */
	public boolean deleteCategory(int categoryId) 
	{
		if(categorydao.deleteCategory(categoryId))
			return true;
		
		return false;

	}

	/*
	 * This method should be used to update a existing category.
	 */

	public Category updateCategory(Category category, int id) throws CategoryNotFoundException 
	{
		Category c3 = categorydao.getCategoryById(id);
		if(c3 == null)
			throw new CategoryNotFoundException("Category Not Found...");
		else
		{
			categorydao.updateCategory(c3);
			return c3;
		}
	}

	/*
	 * This method should be used to get a category by categoryId. We are Experimenting - Success I won.
	 */
	public Category getCategoryById(int categoryId) throws CategoryNotFoundException 
	{
		Category c4 = categorydao.getCategoryById(categoryId);
		if(c4 == null)
			throw new CategoryNotFoundException("Category Not Found...");
		else
			return c4;
	}

	/*
	 * This method should be used to get a category by userId.
	 */

	public List<Category> getAllCategoryByUserId(String userId) 
	{
		List<Category> category = categorydao.getAllCategoryByUserId(userId);
		return category;
	}

}
