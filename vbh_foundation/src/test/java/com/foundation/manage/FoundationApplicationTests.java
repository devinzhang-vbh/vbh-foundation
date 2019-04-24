package com.foundation.manage;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.foundation.manage.controller.web.NewsWebController;
import com.foundation.manage.entity.VBC.News;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class FoundationApplicationTests {
	@Test
	public void contextLoads() {
	}
	@Autowired
	private WebApplicationContext webApplicationContext;


	@Autowired
	private NewsWebController controller;

	private MockMvc mockMvc;

	//初始当前页码:1
	private int  currentPage = 1;
	//默认页码列数：20
	private int pageSize = 20;

	@Before
	public void setup()throws  Exception{
		mockMvc =MockMvcBuilders.standaloneSetup(controller).build();
	}
	@Test
	public void runTest() throws Exception{
		System.out.println("=====================================\n"+
		"项目contextPath='"+webApplicationContext.getServletContext().getContextPath()+"'"
		);
		Page<News> newsPage = new Page<>(currentPage,pageSize);
		setPageSize(1);
		Map<String, Object> condition = new HashMap<>();
		condition.put("Lang","CN");
		newsPage.setCondition(condition);
		String string = JSONUtils.toJSONString(JSONObject.toJSON(newsPage));
		System.out.println(string);
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
						.post("/api/news/list")
						.content(string)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
		).andExpect(
				MockMvcResultMatchers.status().isOk()
		).andDo(
				MockMvcResultHandlers.print()
		).andReturn();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}

