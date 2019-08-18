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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fsd.backend.Entity.Task;
import com.fsd.backend.Repository.TaskRepository;

/**
 * 
 * The Class TaskServiceTest.
 *
 * 
 * 
 * @author AK
 * 
 */

@RunWith(SpringRunner.class)

@SpringBootTest

public class TaskServiceTest {

	/** The task service. */

	@Autowired

	private TaskService taskService;

	/** The taskrepo. */

	@MockBean

	private TaskRepository taskrepo;

	/** The task string 1. */

	private String taskString ="{\"taskId\":1,\"task\":\"Task\",\"priority\":1,\"startDate\":\"2019-05-27\",\"endDate\":\"2019-05-27\",\"parentTask\":{\"parentTaskId\":1,\"parentTask\":\"Parent Task\"},\"project\":{\"projectId\":1,\"project\":\"Project\",\"startDate\":\"2019-05-27\",\"endDate\":\"2019-05-29\",\"priority\":1,\"task\":[],\"user\":null},\"user\":{\"userId\":1,\"firstName\":\"Test\",\"lastName\":\"Lname\",\"employeeId\":\"1000\",\"task\":null,\"project\":null},\"status\":\"complete\"}";

	/** The task 1. */

	private Task task1 = new Task();
	/** The task string 2. */
	private String taskString2="{\"taskId\":2,\"task\":\"Task\",\"priority\":2,\"startDate\":\"2019-08-27\",\"endDate\":\"2019-08-27\",\"parentTask\":{\"parentTaskId\":1,\"parentTask\":\"Parent Task\"},\"project\":{\"projectId\":1,\"project\":\"Project\",\"startDate\":\"2019-08-27\",\"endDate\":\"2019-08-29\",\"priority\":1,\"task\":[],\"user\":null},\"user\":{\"userId\":1,\"firstName\":\"Test\",\"lastName\":\"Lname\",\"employeeId\":\"1000\",\"task\":null,\"project\":null},\"status\":\"complete\"}";
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
		task2 = createObject(taskString);
		taskList.add(task1);
		taskList.add(task2);
	}

	/**
	 * 
	 * Test add task.
	 * 
	 */

	@Test

	public void testAddTask() {

		Mockito.when((taskrepo.save(task1))).thenReturn(task1);
		assertEquals(taskService.addTask(task1).getPriority(), task1.getPriority());
	}

	/**
	 * 
	 * Test find task by id.
	 * 
	 */

	@Test

	public void testFindTaskById() {

		Mockito.when((taskrepo.findById(task1.getTaskId()))).thenReturn(Optional.of(task1));
		assertEquals(taskService.findTaskById(task1.getTaskId()).getPriority(), task1.getPriority());
	}

	/**
	 * 
	 * Test find task by id negative.
	 * 
	 */

	@Test

	public void testFindTaskByIdNegative() {

		Mockito.when((taskrepo.findById(task1.getTaskId()))).thenReturn(null);
		Task dummyTask = taskService.findTaskById(task1.getTaskId());
		assertEquals(dummyTask, null);
	}

	/**
	 * 
	 * Test update task.
	 * 
	 */

	@Test

	public void testUpdateTask() {

		Mockito.when((taskrepo.save(task1))).thenReturn(task1);
		assertEquals(taskService.addTask(task1).getPriority(), task1.getPriority());
	}

	/**
	 * 
	 * Testget all tasks.
	 * 
	 */

	@Test

	public void testgetAllTasks() {

		Mockito.when(taskrepo.findAll()).thenReturn(taskList);
		assertEquals(taskService.getAllTask().get(0).getPriority(), taskList.get(0).getPriority());
	}

	/**
	 * 
	 * Testget delete tasks.
	 * 
	 */

	@Test

	public void testgetDeleteTasks() {

		Mockito.doNothing().when(taskrepo).delete(task1);
		taskService.deleteTask(task1);
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