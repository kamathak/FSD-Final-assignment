package com.fsd.backend.Service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.fsd.backend.Entity.ParentTask;
import com.fsd.backend.Repository.ParentTaskRepo;

/**
 * 
 * The Class ParentServiceTest.
 *
 * 
 * 
 * @author AK
 * 
 */

@RunWith(SpringRunner.class)

@SpringBootTest

public class ParentServiceTest {

	/** The parent service. */

	@Autowired

	private ParentService parentService;

	/** The parentrepo. */

	@MockBean

	private ParentTaskRepo parentrepo;

	/** The parent task1. */

	private ParentTask parentTask1 = null;
	/** The parent task2. */

	private ParentTask parentTask2 = null;

	/** The parent task string 1. */

	private String parentTaskString1 = "{" + "\"parentTaskId\":1," + "\"parentTask\":\"PT1\"" + "}";

	/** The parent task string 1. */

	private String parentTaskString2 = "{" + "\"parentTaskId\":2," + "\"parentTask\":\"PT2\"" + "}";

	/** The parent list. */

	private List<ParentTask> parentList = new ArrayList();

	/**
	 * 
	 * Creates the test data.
	 *
	 * 
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * 
	 */

	@Before

	public void createTestData() throws IOException

	{
		parentTask1 = new ParentTask();
		parentTask1 = createObject(parentTaskString1);
		parentTask2 = new ParentTask();
		parentTask2 = createObject(parentTaskString2);
		parentList.add(parentTask1);
		parentList.add(parentTask2);

	}

	/**
	 * 
	 * Test add parent.
	 * 
	 */

	@Test

	public void testAddParent() {

		Mockito.when((parentrepo.save(parentTask1))).thenReturn(parentTask1);
		assertEquals(parentService.addParentTask(parentTask1).getParentTask(), parentTask1.getParentTask());

	}

	/**
	 * 
	 * Test get all parents.
	 * 
	 */

	@Test

	public void testgetAllParents() {

		Mockito.when(parentrepo.findAll()).thenReturn(parentList);
		assertEquals(parentService.getAllParent().get(0).getParentTask(), parentList.get(0).getParentTask());

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
	 * @return the parent task
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * 
	 */

	private ParentTask createObject(String json) throws IOException {

		ObjectMapper objMapper = new ObjectMapper();
		objMapper.registerModule(new JavaTimeModule());
		objMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objMapper.readValue(json, ParentTask.class);

	}

}