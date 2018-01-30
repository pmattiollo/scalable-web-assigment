package com.wearewaes.controller;

import com.wearewaes.controller.response.ResponseBuilder;
import com.wearewaes.repository.JSONDataRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class JSONDataDiffControllerIT {

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JSONDataRepository jsonDataRepository;

    @Before
    public void setUp() {
        jsonDataRepository.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void uploadLeftFile() throws Exception {
        // Action
        MvcResult mvcResult = mockMvc.perform(post("/v1/diff/1/left")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\": \"UGVkcm8gSHVtYmVydG8gTWF0dGlvbGxv=\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        // Validation
        MockHttpServletResponse response = mvcResult.getResponse();
        errorCollector.checkThat(response.getHeaderValue(HttpHeaders.LOCATION), CoreMatchers.is(ServletUriComponentsBuilder.fromCurrentContextPath().path("/diff/1").build().toUriString()));
        errorCollector.checkThat(response.getContentAsString(), containsString(ResponseBuilder.oneLeftDataSettedUpResponse().get().getUserMessage()));
    }

}
