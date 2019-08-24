package com.fsd.backend.Repository;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsd.backend.Entity.User;

/**
 * The Class UserRepoTest.
 */
/**
 * @author AK
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableTransactionManagement
public class UserRepoTest {


	@Autowired
	private TestEntityManager em;

	@Autowired
	private UserRepo userRepo;

	private String testUser = "{\"employeeId\": \"12345\", \"firstName\": \"Fname1\",\"lastName\": \"Lname1\"}";

	private User user = null;

	@Before
	public void createUser() throws IOException {
		user = new User();
		user = createObject(testUser);

	}

	@Test
	@Rollback(false)
	public void testAddUser() {
		User returnUser = null;
		returnUser = userRepo.save(user);
		assertEquals(returnUser.getEmployeeId(), user.getEmployeeId());
	}

	@Test
	@Rollback(false)
	public void testUpdateUser() {
		User returnUser = null;
		returnUser = userRepo.save(user);
		assertEquals(returnUser.getEmployeeId(), user.getEmployeeId());
	}

	@Test
	public void testfindUserById() {
		User returnUser = userRepo.save(user);
		Optional<User> retUser = userRepo.findById(returnUser.getUserId());
		assertEquals(retUser.get().getEmployeeId(), user.getEmployeeId());
	}

	private User createObject(String json) throws IOException {
		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.readValue(json, User.class);

	}

}
