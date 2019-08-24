package com.fsd.backend.Repository;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fsd.backend.Entity.Task;

/**
 * The Class taskRepositoryTest.
 */
/**
 * @author AK
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableTransactionManagement
public class TaskRepositoryTest {

	/** The task repo. */
	@Autowired
	private TaskRepository taskRepo;

	/** The task. */
	private Task task = null;

	/** The return task. */
	private Task returnTask = null;

	/** The task string. */
	private String taskString = "{\"taskId\":1,\"task\":\"Task\",\"priority\":1,\"startDate\":\"2019-05-27\",\"endDate\":\"2019-05-27\",\"parentTask\":{\"parentTaskId\":1,\"parentTask\":\"Parent Task\"},\"project\":{\"projectId\":1,\"project\":\"Project\",\"startDate\":\"2019-05-27\",\"endDate\":\"2019-05-29\",\"priority\":1,\"task\":[],\"user\":null},\"user\":{\"userId\":1,\"firstName\":\"Test\",\"lastName\":\"Lname\",\"employeeId\":\"1000\",\"task\":null,\"project\":null},\"status\":\"complete\"}";

	/**
	 * Creates the task.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Before
	public void createTask() throws IOException {
		task = new Task();
		task = createObject(taskString);
	}

	/**
	 * Test add Task.
	 */
	@Test
	@Rollback(false)
	public void testAddTask() {
		returnTask = taskRepo.save(task);
		assertEquals(returnTask.getTask(), task.getTask());
	}

	/**
	 * Testfind task by id.
	 */
	@Test
	@Rollback(false)
	public void testfindTaskById() {
		returnTask = taskRepo.save(task);
		Optional<Task> retTask = taskRepo.findById(returnTask.getTaskId());
		assertEquals(retTask.get().getTask(), task.getTask());
	}

	/**
	 * Test update task.
	 */
	@Test
	@Rollback(false)
	public void testUpdateTask() {
		returnTask = taskRepo.save(task);
		assertEquals(returnTask.getTask(), task.getTask());
	}

	/**
	 * Creates the object.
	 *
	 * @param json
	 *            the json
	 * @return the Task
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private Task createObject(String json) throws IOException {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.registerModule(new JavaTimeModule());
		objMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objMapper.readValue(json, Task.class);

	}
}
