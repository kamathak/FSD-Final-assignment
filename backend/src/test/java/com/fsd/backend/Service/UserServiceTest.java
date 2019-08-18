package com.fsd.backend.Service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsd.backend.Entity.User;
import com.fsd.backend.Repository.UserRepo;

/**

* The Class UserServiceTest.

*/

/**
 * @author AK
 *
 */
@RunWith(SpringRunner.class)

@SpringBootTest

public class UserServiceTest {

	/** The user service. */

	@Autowired

	private UserService userService;

	/** The urepo. */

	@MockBean

	private UserRepo urepo;

	/** The user 1. */

	private String user1 = "{\"employeeId\": \"1\", \"firstName\": \"Fname1\",\"lastName\": \"Lname1\"}";

	/** The test user 1. */

	private User testUser1 = new User();

	/** The user 2. */

	private String user2 = "{\"employeeId\": \"2\", \"firstName\": \"Fname2\",\"lastName\": \"Lname2\"}";

	/** The test user 2. */

	private User testUser2 = new User();

	/** The user list. */

	private List<User> userList = new ArrayList();

	/**
	 * 
	 * Adds the user details.
	 *
	 * 
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * 
	 */

	@Before

	public void addUserDetails() throws IOException

	{
		testUser1 = createObject(user1);
		testUser2 = createObject(user2);
		userList.add(testUser1);
		userList.add(testUser2);
	}

	/**
	 * 
	 * Test add user.
	 * 
	 */

	@Test

	public void testAddUser() {

		Mockito.when((urepo.save(testUser1))).thenReturn(testUser1);
		assertEquals(userService.addUser(testUser1).getEmployeeId(), testUser1.getEmployeeId());
	}

	/**
	 * 
	 * Test find user by id.
	 * 
	 */

	@Test

	public void testFindUserById() {

		Mockito.when((urepo.findById(testUser1.getUserId()))).thenReturn(Optional.of(testUser1));
		assertEquals(userService.findUserById(testUser1.getUserId()).getEmployeeId(), testUser1.getEmployeeId());
	}

	/**
	 * 
	 * Test find user by id negative.
	 * 
	 */

	@Test

	public void testFindUserByIdNegative() {

		Mockito.when((urepo.findById(testUser1.getUserId()))).thenReturn(null);
		User userDummy = userService.findUserById(testUser1.getUserId());
		assertEquals(userDummy, null);

	}

	/**
	 * 
	 * Testget all users.
	 * 
	 */

	@Test

	public void testgetAllUsers() {

		Mockito.when(urepo.findAll()).thenReturn(userList);
		assertEquals(userService.getAllUsers().get(0).getEmployeeId(), userList.get(0).getEmployeeId());
	}

	/**
	 * 
	 * Test update user.
	 * 
	 */

	@Test

	public void testUpdateUser() {

		Mockito.when((urepo.save(testUser1))).thenReturn(testUser1);
		assertEquals(userService.addUser(testUser1).getEmployeeId(), testUser1.getEmployeeId());
	}

	/**
	 * 
	 * Testget delete users.
	 * 
	 */

	@Test

	public void testgetDeleteUsers() {

		Mockito.doNothing().when(urepo).delete(testUser1);
		userService.deleteUser(testUser1);
	}

	/**
	 * 
	 * Creates the object.
	 *
	 * 
	 * 
	 * @param json
	 *            the json
	 * 
	 * @return the user
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * 
	 */

	private User createObject(String json) throws IOException {

		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.readValue(json, User.class);
	}

}