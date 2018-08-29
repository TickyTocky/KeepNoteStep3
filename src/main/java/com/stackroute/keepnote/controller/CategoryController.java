package com.stackroute.keepnote.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.CategoryService;

/* * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
*/
@RestController
@RequestMapping("/category")
public class CategoryController {

	/*
	 * * Autowiring should be implemented for the CategoryService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */
	private CategoryService categoryservice;

	@Autowired
	public CategoryController(CategoryService categoryservice) {
		this.categoryservice = categoryservice;
	}

	/*
	 * * Define a handler method which will create a category by reading the
	 * Serialized category object from request body and save the category in
	 * category table in database. Please note that the careatorId has to be unique
	 * and the loggedIn userID should be taken as the categoryCreatedBy for the
	 * category. This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED - In case of successful
	 * creation of the category 2. 409(CONFLICT) - In case of duplicate categoryId
	 * 3. 401(UNAUTHORIZED) - If the user trying to perform the action has not
	 * logged in.
	 * 
	 * This handler method should map to the URL "/category" using HTTP POST
	 * method".
	 * 
	 */

	@PostMapping("")
	public ResponseEntity<?> createCategory(@RequestBody Category category, HttpSession session) {
		if (session != null && session.getAttribute("loggedInUserId") != null) {
			if (categoryservice.createCategory(category)) {
				return new ResponseEntity<String>("Created", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<String>("Conflict", HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<String>("user id didn't match", HttpStatus.UNAUTHORIZED);
		}
	}

	/*
	 * * Define a handler method which will delete a category from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the category with specified categoryId is
	 * not found. 3. 401(UNAUTHORIZED) - If the user trying to perform the action
	 * has not logged in.
	 * 
	 * This handler method should map to the URL "/category/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid categoryId without {}
	 * 
	 */ @DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable int id, HttpSession session) {
		ResponseEntity<?> responseEntity = null;

		try {
			if (session.getAttribute("loggedInUserId") != null) {
				if (categoryservice.deleteCategory(id)) {
					responseEntity = new ResponseEntity<>("User deleted", HttpStatus.OK);

				} else {
					throw new UserNotFoundException("user not found");
				}
			} else {
				responseEntity = new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}
	/*
	 * * Define a handler method which will update a specific category by reading
	 * the Serialized object from request body and save the updated category details
	 * in a category table in database handle CategoryNotFoundException as well.
	 * please note that the loggedIn userID should be taken as the categoryCreatedBy
	 * for the category. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the category updated
	 * successfully. 2. 404(NOT FOUND) - If the category with specified categoryId
	 * is not found. 3. 401(UNAUTHORIZED) - If the user trying to perform the action
	 * has not logged in.
	 * 
	 * This handler method should map to the URL "/category/{id}" using HTTP PUT
	 * method.
	 */

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody Category category, HttpSession session) {
		ResponseEntity<?> responseEntity = null;
		try {
			if (session.getAttribute("loggedInUserId") != null) {
				if (categoryservice.updateCategory(category, id) == null) {
					throw new Exception("User not found");
				} else {
					responseEntity = new ResponseEntity<>("User updated", HttpStatus.OK);
				}
			} else {
				responseEntity = new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	/*
	 * * Define a handler method which will get us the category by a userId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category found successfully. 2.
	 * 401(UNAUTHORIZED) -If the user trying to perform the action has not logged
	 * in.
	 * 
	 * 
	 * This handler method should map to the URL "/category" using HTTP GET method
	 */
	@GetMapping()
	public ResponseEntity<?> getCategoryById(HttpSession session) {
		if (session != null && session.getAttribute("loggedInUserId") != null) {
			String userId =  (String) session.getAttribute("loggedInUserId");
			List<Category> categories = categoryservice
					.getAllCategoryByUserId(userId);
			return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("Not logged in", HttpStatus.UNAUTHORIZED);
		}
	}
}