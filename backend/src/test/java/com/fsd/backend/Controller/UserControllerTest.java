package com.fsd.backend.Controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsd.backend.Entity.User;
import com.fsd.backend.Service.UserService;

/**
 * 
 * The Class UserControllerTest.
 *
 * 
 * 
 * @author AK
 * 
 */

@RunWith(SpringRunner.class)

@WebMvcTest(value = UserController.class)

public class UserControllerTest {

	/** The mock mvc. */

	@Autowired

	private MockMvc mockMvc;

	/** The usercontroller. */

	@Autowired

	private UserController usercontroller;

	/** The user service. */

	@MockBean

	private UserService userService;

	/** The user 1. */
	@Value("${testcase.user1}")
	private String user1;

	/** The test user 1. */

	private User testUser1 = new User();

	/** The user 2. */
	@Value("${testcase.user2}")
	private String user2;

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
	 * Adds the user.
	 *
	 * 
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void addUser() throws Exception {

		String expectedJson = createJson(testUser1);
		String restURI = "/taskManager/userAction/addUser";
		String outJson = returnExpectedJson(expectedJson, restURI);
		User retUser = createObject(outJson);
		assertEquals(retUser.getEmployeeId(), testUser1.getEmployeeId());

	}

	/**
	 * 
	 * Gets the all user.
	 *
	 * 
	 * 
	 * @return the all user
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void getAllUser() throws Exception {

		Mockito.when(userService.getAllUsers()).thenReturn(userList);
		List<User> actual = usercontroller.getAllUser();
		verify(userService, times(1)).getAllUsers();
		verifyNoMoreInteractions(userService);

	}

	/**
	 * 
	 * Gets the user.
	 *
	 * 
	 * 
	 * @return the user
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void getUser() throws Exception {

		String expectedJson = createJson(testUser1);
		String restURI = "/taskManager/userAction/getUserById/" + Long.toString(testUser1.getUserId());
		String outJson = getreturnExpectedJson(expectedJson, restURI);
		User retUser = createObject(outJson);
		assertEquals(retUser.getEmployeeId(), testUser1.getEmployeeId());
	}

	/**
	 * 
	 * Update user.
	 *
	 * 
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void updateUser() throws Exception {

		String expectedJson = createJson(testUser1);
		String restURI = "/taskManager/userAction/updateUser";
		String outJson = returnExpectedJson(expectedJson, restURI);
		User retUser = createObject(outJson);
		assertEquals(retUser.getEmployeeId(), testUser1.getEmployeeId());
	}

	/**
	 * 
	 * Delete user.
	 *
	 * 
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void deleteUser() throws Exception {

		String expectedJson = createJson(testUser1);
		String restURI = "/taskManager/userAction/deleteUser";
		String outJson = returnExpectedJson(expectedJson, restURI);
		User retUser = createObject(outJson);
		assertEquals(retUser.getEmployeeId(), testUser1.getEmployeeId());
	}

	/**
	 * 
	 * Creates the json.
	 *
	 * 
	 * 
	 * @param object
	 *            the object
	 * 
	 * @return the string
	 * 
	 * @throws JsonProcessingException
	 *             the json processing exception
	 * 
	 */

	private String createJson(Object object) throws JsonProcessingException {

		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.writeValueAsString(object);

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

	/**
	 * 
	 * Return expected json.
	 *
	 * 
	 * 
	 * @param expectedJson
	 *            the expected json
	 * 
	 * @param restURI
	 *            the rest URI
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	private String returnExpectedJson(String expectedJson, String restURI) throws Exception

	{

		Mockito.when(userService.findUserById((Mockito.any(Long.class)))).thenReturn(testUser1);
		Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(testUser1);
		Mockito.when(userService.deleteUser((Mockito.any(User.class)))).thenReturn(testUser1);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.post(restURI).accept(MediaType.APPLICATION_JSON)
				.content(expectedJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();
		MockHttpServletResponse mockresponse = mvcResult.getResponse();
		return mockresponse.getContentAsString();
	}

	/**
	 * 
	 * Gets the return expected json.
	 *
	 * 
	 * 
	 * @param expectedJson
	 *            the expected json
	 * 
	 * @param restURI
	 *            the rest URI
	 * 
	 * @return the return expected json
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	private String getreturnExpectedJson(String expectedJson, String restURI) throws Exception

	{

		Mockito.when(userService.findUserById((Mockito.any(Long.class)))).thenReturn(testUser1);
		Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(testUser1);
		Mockito.when(userService.deleteUser((Mockito.any(User.class)))).thenReturn(testUser1);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get(restURI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();
		MockHttpServletResponse mockresponse = mvcResult.getResponse();
		return mockresponse.getContentAsString();
	}

}
