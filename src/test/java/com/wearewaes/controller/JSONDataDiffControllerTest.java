package com.wearewaes.controller;

import com.wearewaes.builder.JSONDataDTOBuilder;
import com.wearewaes.controller.response.Response;
import com.wearewaes.model.InputType;
import com.wearewaes.model.JSONDataDTO;
import com.wearewaes.service.JSONDataDiffService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class JSONDataDiffControllerTest {

    @Mock
    public JSONDataDiffService jsonDataDiffService;

    @Mock
    public ApplicationEventPublisher publisher;

    @InjectMocks
    private JSONDataDiffController jsonDataDiffController;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnCreatedWhenLeftDataUpload() {
        // Scenario
        Long id = 1L;
        JSONDataDTO jsonDataDTO = JSONDataDTOBuilder.oneData().get();
        Mockito.doReturn(null).when(jsonDataDiffService).saveLeft(id, jsonDataDTO);
        Mockito.doNothing().when(publisher).publishEvent(Mockito.any(ApplicationEvent.class));

        // Action and verification
        ResponseEntity<Response> response = jsonDataDiffController.uploadLeftFile(id, jsonDataDTO, null);

        // Verification
        errorCollector.checkThat(response.getStatusCode(), is(HttpStatus.CREATED));
        errorCollector.checkThat(response.hasBody(), is(true));
        errorCollector.checkThat(response.getBody().getUserMessage(), is("[OK] - " + InputType.LEFT.name() + " data has been uploaded sucessfully"));
    }

    @Test
    public void shouldReturnCreatedWhenRightDataUpload() {
        // Scenario
        Long id = 1L;
        JSONDataDTO jsonDataDTO = JSONDataDTOBuilder.oneData().get();
        Mockito.doReturn(null).when(jsonDataDiffService).saveRight(id, jsonDataDTO);
        Mockito.doNothing().when(publisher).publishEvent(Mockito.any(ApplicationEvent.class));

        // Action and verification
        ResponseEntity<Response> response = jsonDataDiffController.uploadRightFile(id, jsonDataDTO, null);

        // Verification
        errorCollector.checkThat(response.getStatusCode(), is(HttpStatus.CREATED));
        errorCollector.checkThat(response.hasBody(), is(true));
        errorCollector.checkThat(response.getBody().getUserMessage(), is("[OK] - " + InputType.RIGHT.name() + " data has been uploaded sucessfully"));
    }

    @Test
    public void shouldReturnOKWhenDiff() {
        // Scenario
        Long id = 1L;
        Mockito.doReturn("They are equal").when(jsonDataDiffService).getJSONDiffResult(id);

        // Action
        ResponseEntity<Response> response = jsonDataDiffController.getDiffResults(id);

        // Verification
        errorCollector.checkThat(response.getStatusCode(), is(HttpStatus.OK));
        errorCollector.checkThat(response.hasBody(), is(true));
        errorCollector.checkThat(response.getBody().getUserMessage(), is("They are equal"));
    }
}