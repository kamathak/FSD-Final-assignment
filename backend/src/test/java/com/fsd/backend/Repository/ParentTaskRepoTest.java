package com.fsd.backend.Repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

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
import com.fsd.backend.Entity.ParentTask;
import com.google.common.collect.Lists;

/**
 * The Class ParentTaskRepoTest.
 */
/**
 * @author AK
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableTransactionManagement
public class ParentTaskRepoTest {

	/** The parent repo. */
	@Autowired
	private ParentTaskRepo parentRepo;

	/** The parent task string. */
	private String parentTaskString = "{" + "\"parentTaskId\":1," + "\"parentTask\":\"parentTask\"" + "}";

	/** The parent task. */
	private ParentTask parentTask;

	/**
	 * Creates the parent.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Before
	public void createParent() throws IOException {
		parentTask = new ParentTask();
		parentTask = createObject(parentTaskString);
	}

	/**
	 * Test add parent.
	 */
	@Test
	@Rollback(false)
	public void testAddParent() {
		ParentTask returnParent = parentRepo.save(parentTask);
		assertEquals(returnParent.getParentTask(), parentTask.getParentTask());
	}

	/**
	 * Test get parent.
	 */
	@Test
	@Rollback(false)
	public void testGetParent() {
		parentRepo.save(parentTask);
		Iterable<ParentTask> parentTask = parentRepo.findAll();
		List<ParentTask> parentTaskList = Lists.newArrayList(parentTask);
		assertNotNull(parentTaskList);
	}

	/**
	 * Creates the object.
	 *
	 * @param json
	 *            the json
	 * @return the parent task
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private ParentTask createObject(String json) throws IOException {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.registerModule(new JavaTimeModule());
		objMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objMapper.readValue(json, ParentTask.class);

	}

}
