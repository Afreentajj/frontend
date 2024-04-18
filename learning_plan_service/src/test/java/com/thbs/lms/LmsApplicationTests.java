package com.thbs.lms;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LmsApplicationTests {
	@Autowired
	private LmsApplication application;

	@Test
	void contextLoads() {
		assertNotNull(application);
	}

}
