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
import com.fsd.backend.Entity.Project;
import com.fsd.backend.Repository.ProjectRepository;


/**

* The Class ProjectServiceTest.

*/

/**
 * @author AK
 *
 */
@RunWith(SpringRunner.class)

@SpringBootTest

public class ProjectServiceTest {

	/** The project service. */

	@Autowired

	private ProjectService projectService;

	/** The projectrepo. */

	@MockBean

	private ProjectRepository projectrepo;

	/** The project 1. */

	private Project project1 = new Project();;

	/** The project string 1. */

	private String projectString1 = "{\"projectId\":1,\"project\":\"P1\",\"startDate\":\"2019-07-27\",\"endDate\":\"2019-08-27\",\"priority\":1}";

	/** The project 2. */

	private Project project2 = new Project();;

	/** The project string 2. */

	private String projectString2 = "{\"projectId\":2,\"project\":\"P2\",\"startDate\":\"2019-07-28\",\"endDate\":\"2019-08-28\",\"priority\":2}";

	/** The project list. */

	private List<Project> projectList = new ArrayList();

	/**
	 * 
	 * Creates the project.
	 *
	 * 
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * 
	 */

	@Before

	public void createProject() throws IOException

	{
		project1 = createObject(projectString1);
		project2 = createObject(projectString1);
		projectList.add(project1);
		projectList.add(project2);

	}

	/**
	 * 
	 * Test add project.
	 * 
	 */

	@Test

	public void testAddProject() {

		Mockito.when((projectrepo.save(project1))).thenReturn(project1);
		assertEquals(projectService.addProject(project1).getPriority(), project1.getPriority());

	}

	/**
	 * 
	 * Test find project by id.
	 * 
	 */

	@Test

	public void testFindProjectById() {

		Mockito.when((projectrepo.findById(project1.getProjectId()))).thenReturn(Optional.of(project1));
		assertEquals(projectService.findProjectById(project1.getProjectId()).getPriority(), project1.getPriority());

	}

	/**
	 * 
	 * Test negative for project id.
	 * 
	 */

	@Test

	public void testNegativeForProjectId()

	{

		Mockito.when((projectrepo.findById(project1.getProjectId()))).thenReturn(null);
		Project projectDummy = projectService.findProjectById(project1.getProjectId());
		assertEquals(projectDummy, null);

	}

	/**
	 * 
	 * Test update project.
	 * 
	 */

	@Test

	public void testUpdateProject() {

		Mockito.when((projectrepo.save(project1))).thenReturn(project1);
		assertEquals(projectService.addProject(project1).getPriority(), project1.getPriority());

	}

	/**
	 * 
	 * Testget all projects.
	 * 
	 */

	@Test

	public void testgetAllProjects() {

		Mockito.when(projectrepo.findAll()).thenReturn(projectList);
		assertEquals(projectService.getAllProject().get(0).getPriority(), projectList.get(0).getPriority());

	}

	/**
	 * 
	 * Testget delete projects.
	 * 
	 */

	@Test

	public void testgetDeleteProjects() {

		Mockito.doNothing().when(projectrepo).delete(project1);
		projectService.deleteProject(project1);
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
	 * @return the project
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * 
	 */

	private Project createObject(String json) throws IOException {

		ObjectMapper objMapper = new ObjectMapper();
		objMapper.registerModule(new JavaTimeModule());
		objMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objMapper.readValue(json, Project.class);

	}

}