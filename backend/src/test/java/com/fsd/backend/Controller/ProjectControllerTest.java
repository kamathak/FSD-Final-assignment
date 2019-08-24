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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fsd.backend.Entity.Project;
import com.fsd.backend.Service.ProjectService;

/**
 * 
 * The Class ProjectControllerTest.
 *
 * 
 * 
 * @author AK
 * 
 */

@RunWith(SpringRunner.class)

@WebMvcTest(value = ProjectController.class)

public class ProjectControllerTest {

	/** The mock mvc. */

	@Autowired

	private MockMvc mockMvc;

	/** The project service. */

	@MockBean

	private ProjectService projectService;

	/** The project 1. */

	private Project project1 = new Project();;

	/** The project string 1. */
	@Value("${testcase.project1}")
	private String projectString1;

	/** The project 2. */

	private Project project2 = new Project();;

	/** The project string 2. */
	@Value("${testcase.project2}")
	private String projectString2;

	private List<Project> projectList = new ArrayList();

	/** The project controller. */

	@Autowired

	private ProjectController projectController;

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
		project1 = createObject(projectString1);
		project2 = createObject(projectString2);
		projectList.add(project1);
		projectList.add(project2);

	}

	/**
	 * 
	 * Adds the project.
	 *
	 * 
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void addProject() throws Exception {

		String expectedJson = createJson(project1);
		String restURI = "/taskManager/projectAction/addProject";
		String outJson = returnExpectedJson(expectedJson, restURI);
		Project retProject = createObject(outJson);
		assertEquals(retProject.getProject(), project1.getProject());

	}

	/**
	 * 
	 * Gets the project.
	 *
	 * 
	 * 
	 * @return the project
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void getProject() throws Exception {

		String expectedJson = createJson(project1);
		String restURI = "/taskManager/projectAction/getProjectById/" + Long.toString(project1.getProjectId());
		String outJson = returnExpectedJson(expectedJson, restURI);
		Project retProject = createObject(outJson);
		assertEquals(retProject.getProject(), project1.getProject());
	}

	/**
	 * 
	 * Update project.
	 *
	 * 
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void updateProject() throws Exception {

		String expectedJson = createJson(project1);
		String restURI = "/taskManager/projectAction/updateProject";
		String outJson = returnExpectedJson(expectedJson, restURI);
		Project retProject = createObject(outJson);
		assertEquals(retProject.getProject(), project1.getProject());

	}

	@Test

	public void deleteProject() throws Exception {

		String expectedJson = createJson(project1);
		String restURI = "/taskManager/projectAction/deleteProject";
		String outJson = returnExpectedJson(expectedJson, restURI);
		Project retProject = createObject(outJson);
		assertEquals(retProject.getProject(), project1.getProject());
	}

	/**
	 * 
	 * Gets the all project.
	 *
	 * 
	 * 
	 * @return the all project
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 */

	@Test

	public void getAllProject() throws Exception {

		Mockito.when(projectService.getAllProject()).thenReturn(projectList);
		List<Project> actual = projectController.getAllProject();
		verify(projectService, times(1)).getAllProject();
		verifyNoMoreInteractions(projectService);
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
		Mockito.when(projectService.findProjectById((Mockito.any(Long.class)))).thenReturn(project1);
		Mockito.when(projectService.addProject(Mockito.any(Project.class))).thenReturn(project1);
		Mockito.when(projectService.deleteProject((Mockito.any(Project.class)))).thenReturn(project1);
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