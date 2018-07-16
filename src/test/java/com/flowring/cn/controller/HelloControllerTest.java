package com.flowring.cn.controller;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@Test
	public void testHello() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMember() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/hello/getMember"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json("{'memId':'jeff','name':'Jeff'}"));
	}

	@Test
	public void testGetMemberJson() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/hello/getMemberJson"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
