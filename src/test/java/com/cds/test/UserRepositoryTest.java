package com.cds.test;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cds.model.User;
import com.cds.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	UserRepository repository;

	@Test
	public void testRepository() {
		User usr = new User("Test", new BigDecimal(22.33), new Date(), 'A');
		repository.save(usr);
		Assert.assertNotNull(usr.getId());
	}

	@Test
	public void testRepositoryGet() {
		Assert.assertNotNull(repository.findUsers(new BigDecimal(0),new BigDecimal(20)));
	}
}