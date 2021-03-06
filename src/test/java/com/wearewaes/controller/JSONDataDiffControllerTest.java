package com.wearewaes.controller;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wearewaes.builder.JSONDataDTOBuilder;
import com.wearewaes.controller.response.Response;
import com.wearewaes.model.InputType;
import com.wearewaes.model.JSONDataDTO;
import com.wearewaes.service.JSONDataDiffService;

/**
 * Responsible for perform all unit tests over the {@link JSONDataDiffController} class
 */
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

    /**
     * Test if the controller will return the proper response when the left data is uploaded
     */
    @Test
    public void shouldReturnCreatedWhenLeftDataUpload() {
        // Scenario
        Long id = 1L;
        JSONDataDTO jsonDataDTO = JSONDataDTOBuilder.oneData().get();
        Mockito.doReturn(null).when(jsonDataDiffService).saveLeft(id, jsonDataDTO);
        Mockito.doNothing().when(publisher).publishEvent(ArgumentMatchers.any(ApplicationEvent.class));

        // Action and verification
        ResponseEntity<Response> response = jsonDataDiffController.uploadLeftFile(id, jsonDataDTO, null);

        // Verification
        errorCollector.checkThat(response.getStatusCode(), is(HttpStatus.CREATED));
        errorCollector.checkThat(response.hasBody(), is(true));
        errorCollector.checkThat(response.getBody().getUserMessage(), is("[OK] - " + InputType.LEFT.name() + " data has been uploaded sucessfully"));
    }

    /**
     * Test if the controller will return the proper response when the right data is uploaded
     */
    @Test
    public void shouldReturnCreatedWhenRightDataUpload() {
        // Scenario
        Long id = 1L;
        JSONDataDTO jsonDataDTO = JSONDataDTOBuilder.oneData().get();
        Mockito.doReturn(null).when(jsonDataDiffService).saveRight(id, jsonDataDTO);
        Mockito.doNothing().when(publisher).publishEvent(ArgumentMatchers.any(ApplicationEvent.class));

        // Action and verification
        ResponseEntity<Response> response = jsonDataDiffController.uploadRightFile(id, jsonDataDTO, null);

        // Verification
        errorCollector.checkThat(response.getStatusCode(), is(HttpStatus.CREATED));
        errorCollector.checkThat(response.hasBody(), is(true));
        errorCollector.checkThat(response.getBody().getUserMessage(), is("[OK] - " + InputType.RIGHT.name() + " data has been uploaded sucessfully"));
    }

    /**
     * Test if the controller will return the proper response when the diff operation is performed
     */
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
