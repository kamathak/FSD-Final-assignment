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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fsd.backend.Entity.Task;
import com.fsd.backend.Service.TaskService;

/**
 * 
 * The Class TaskControllerTest.
 *
 * 
 * 
 * @author AK
 * 
 */

@RunWith(SpringRunner.class)

@WebMvcTest(value = TaskController.class)

public class TaskControllerTest {

	/** The mock mvc. */

	@Autowired

	private MockMvc mockMvc;

	/** The task service. */

	@MockBean

	private TaskService taskService;

	/** The taskcontroller. */

	@Autowired

	private TaskController taskcontroller;

	/** The task string 1. */

	private String taskString = "{\"taskId\":1,\"task\":\"Task\",\"priority\":1,\"startDate\":\"2019-05-27\",\"endDate\":\"2019-05-27\",\"parentTask\":{\"parentTaskId\":1,\"parentTask\":\"Parent Task\"},\"project\":{\"projectId\":1,\"project\":\"Project\",\"startDate\":\"2019-05-27\",\"endDate\":\"2019-05-29\",\"priority\":1,\"task\":[],\"user\":null},\"user\":{\"userId\":1,\"firstName\":\"Test\",\"lastName\":\"Lname\",\"employeeId\":\"1000\",\"task\":null,\"project\":null},\"status\":\"complete\"}";

	/** The task 1. */

	private Task task1 = new Task();

	/** The task string 2. */

	private String taskString2 = "{\"taskId\":2,\"task\":\"Task\",\"priority\":2,\"startDate\":\"2019-08-27\",\"endDate\":\"2019-08-27\",\"parentTask\":{\"parentTaskId\":1,\"parentTask\":\"Parent Task\"},\"project\":{\"projectId\":1,\"project\":\"Project\",\"startDate\":\"2019-08-27\",\"endDate\":\"2019-08-29\",\"priority\":1,\"task\":[],\"user\":null},\"user\":{\"userId\":1,\"firstName\":\"Test\",\"lastName\":\"Lname\",\"employeeId\":\"1000\",\"task\":null,\"project\":null},\"status\":\"complete\"}";
	/** The task 2. */

	private Task task2 = new Task();

	/** The task list. */

	private List<Task> taskList = new ArrayList();

	/**
	 * 
	 * Creates the task.
	 *
	 * 
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * 
	 */

	@Before

	public void createTask() throws IOException

	{
		task1 = createObject(taskString);
		task2 = createObject(taskString2);
		taskList.add(task1);
		taskList.add(task2);
	}

	/**
	 * 
	 * Adds the task.
	 *
	 * 
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void addTask() throws Exception {

		String expectedJson = createJson(task1);
		String restURI = "/taskManager/taskAction/addTask";
		String outJson = returnExpectedJson(expectedJson, restURI);
		Task retTask = createObject(outJson);
		assertEquals(retTask.getTask(), task1.getTask());

	}

	/**
	 * 
	 * Gets the task.
	 *
	 * 
	 * 
	 * @return the task
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void getTask() throws Exception {

		String expectedJson = createJson(task1);
		String restURI = "/taskManager/taskAction/getTaskById/" + Long.toString(task1.getTaskId());
		String outJson = returnExpectedJson(expectedJson, restURI);
		Task retTask = createObject(outJson);
		assertEquals(retTask.getTask(), task1.getTask());
	}

	/**
	 * 
	 * Delete task.
	 *
	 * 
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void deleteTask() throws Exception {

		String expectedJson = createJson(task1);
		String restURI = "/taskManager/taskAction/deleteTask";
		String outJson = returnExpectedJson(expectedJson, restURI);
		Task retTask = createObject(outJson);
		assertEquals(retTask.getTask(), task1.getTask());

	}

	/**
	 * 
	 * Update task.
	 *
	 * 
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void updateTask() throws Exception {

		String expectedJson = createJson(task1);
		String restURI = "/taskManager/taskAction/updateTask";
		String outJson = returnExpectedJson(expectedJson, restURI);
		Task retTask = createObject(outJson);
		assertEquals(retTask.getTask(), task1.getTask());
	}

	/**
	 * 
	 * Gets the all task.
	 *
	 * 
	 * 
	 * @return the all task
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void getAllTask() throws Exception {

		Mockito.when(taskService.getAllTask()).thenReturn(taskList);
		List<Task> actual = taskcontroller.getAllTask();
		verify(taskService, times(1)).getAllTask();
		verifyNoMoreInteractions(taskService);
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

	private String returnExpectedJson(String expectedJson, String restURI) throws Exception {
		
		Mockito.when(taskService.findTaskById((Mockito.any(Long.class)))).thenReturn(task1);
		Mockito.when(taskService.addTask(Mockito.any(Task.class))).thenReturn(task1);
		Mockito.when(taskService.deleteTask((Mockito.any(Task.class)))).thenReturn(task1);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.post(restURI).accept(MediaType.APPLICATION_JSON)
				.content(expectedJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();
		MockHttpServletResponse mockresponse = mvcResult.getResponse();
		return mockresponse.getContentAsString();
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
		objMapper.registerModule(new JavaTimeModule());
		objMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
	 * @return the task
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * 
	 */

	private Task createObject(String json) throws IOException {

		ObjectMapper objMapper = new ObjectMapper();
		objMapper.registerModule(new JavaTimeModule());
		objMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objMapper.readValue(json, Task.class);

	}

}
