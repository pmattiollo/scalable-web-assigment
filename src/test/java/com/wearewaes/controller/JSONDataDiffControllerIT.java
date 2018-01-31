package com.wearewaes.controller;

import com.wearewaes.controller.response.ResponseBuilder;
import com.wearewaes.model.InputType;
import com.wearewaes.repository.JSONDataRepository;
import com.wearewaes.service.exception.EmptyJsonDataException;
import com.wearewaes.service.exception.InputDataAlreadyExistsException;
import com.wearewaes.service.exception.IDNotFoundException;
import com.wearewaes.service.exception.InsufficientDataToDiffException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static com.wearewaes.controller.response.ResponseBuilder.oneLeftDataSettedUpResponse;
import static com.wearewaes.controller.response.ResponseBuilder.oneRightDataSettedUpResponse;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private MessageSource messageSource;

    @Before
    public void setUp() {
        jsonDataRepository.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldContainsLocationAndLeftUploadOkMessage() throws Exception {
        // Action
        MvcResult mvcResult = doSimpleUpload(1L, "UGVkcm8gSHVtYmVydG8gTWF0dGlvbGxv=", InputType.LEFT);

        // Validation
        MockHttpServletResponse response = mvcResult.getResponse();
        errorCollector.checkThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        errorCollector.checkThat(response.getHeaderValue(HttpHeaders.LOCATION), is(ServletUriComponentsBuilder.fromCurrentContextPath().path("/diff/1").build().toUriString()));
        errorCollector.checkThat(response.getContentAsString(), containsString(oneLeftDataSettedUpResponse().get().getUserMessage()));
    }

    @Test
    public void shouldContainsLocationAndRightUploadOkMessage() throws Exception {
        // Action
        MvcResult mvcResult = doSimpleUpload(1L, "UGVkcm8gSHVtYmVydG8gTWF0dGlvbGxv=", InputType.RIGHT);

        // Validation
        MockHttpServletResponse response = mvcResult.getResponse();
        errorCollector.checkThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        errorCollector.checkThat(response.getHeaderValue(HttpHeaders.LOCATION), is(ServletUriComponentsBuilder.fromCurrentContextPath().path("/diff/1").build().toUriString()));
        errorCollector.checkThat(response.getContentAsString(), containsString(oneRightDataSettedUpResponse().get().getUserMessage()));
    }

    @Test
    public void shouldContainsSameSizeMessage() throws Exception {
        // Scenario
        doSimpleUpload(1L, "UGVkcm8gSHVtYmVydG8gTWF0dGlvbGxv=", InputType.LEFT);
        doSimpleUpload(1L, "UGVkcm8gSHVtYmVydG8gTWF0dGlvbGxv=", InputType.RIGHT);

        // Action
        MvcResult mvcResult = mockMvc.perform(get("/v1/diff/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Validation
        MockHttpServletResponse response = mvcResult.getResponse();
        errorCollector.checkThat(response.getStatus(), is(HttpStatus.OK.value()));
        errorCollector.checkThat(response.getContentAsString(), containsString(ResponseBuilder.oneDiffResponse("They are equal").get().getUserMessage()));
    }

    @Test
    public void shouldContainsDiffSizeMessage() throws Exception {
        // Scenario
        String left = "UGVkcm8gSHVtYmVydG8gTWF0dGlvbGxv";
        String right = "UGVkcm8=";
        doSimpleUpload(1L, left, InputType.LEFT);
        doSimpleUpload(1L, right, InputType.RIGHT);

        // Action
        MvcResult mvcResult = mockMvc.perform(get("/v1/diff/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Validation
        MockHttpServletResponse response = mvcResult.getResponse();
        errorCollector.checkThat(response.getStatus(), is(HttpStatus.OK.value()));
        errorCollector.checkThat(response.getContentAsString(), containsString(ResponseBuilder.oneDiffResponse("They have different sizes. Left JSON size: " + left.length() + ", Right JSON size: " + right.length()).get().getUserMessage()));
    }

    @Test
    public void shouldContainsOffsetDiffSMessage() throws Exception {
        // Scenario
        String left = "QnJlbm8=";
        String right = "TWFydGE=";
        doSimpleUpload(1L, left, InputType.LEFT);
        doSimpleUpload(1L, right, InputType.RIGHT);

        // Action
        MvcResult mvcResult = mockMvc.perform(get("/v1/diff/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Validation
        MockHttpServletResponse response = mvcResult.getResponse();
        errorCollector.checkThat(response.getStatus(), is(HttpStatus.OK.value()));
        errorCollector.checkThat(response.getContentAsString(), containsString(ResponseBuilder.oneDiffResponse("They have same sizes, but their content are different. Wrong bytes offsets: 0, 1, 2, 3, 4, 5, 6").get().getUserMessage()));
    }

    @Test
    public void shouldReturnIdNotFoundErrorMessage() throws Exception {
        // Action
        MvcResult mvcResult = mockMvc.perform(get("/v1/diff/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Validation
        IDNotFoundException ex = new IDNotFoundException(1L);
        String[] args = { ex.getId().toString() };
        String userMessage = messageSource.getMessage("id.notfound", args, LocaleContextHolder.getLocale());

        MockHttpServletResponse response = mvcResult.getResponse();
        errorCollector.checkThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        errorCollector.checkThat(response.getContentAsString(), containsString(userMessage));
        errorCollector.checkThat(response.getContentAsString(), containsString(ex.toString()));
    }

    @Test
    public void shouldReturnInsufficientDataToDiffErrorMessage() throws Exception {
        // Scenario
        doSimpleUpload(1L, "UGVkcm8gSHVtYmVydG8gTWF0dGlvbGxv=", InputType.LEFT);

        // Action
        MvcResult mvcResult = mockMvc.perform(get("/v1/diff/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Validation
        InsufficientDataToDiffException ex = new InsufficientDataToDiffException(1L, true, false);
        String[] args = { ex.getId().toString(), ex.isHasLeftJson() ? "OK" : "Missing", ex.isHasRightJson() ? "OK" : "Missing" };
        String userMessage = messageSource.getMessage("file.missing", args, LocaleContextHolder.getLocale());

        MockHttpServletResponse response = mvcResult.getResponse();
        errorCollector.checkThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        errorCollector.checkThat(response.getContentAsString(), containsString(userMessage));
        errorCollector.checkThat(response.getContentAsString(), containsString(ex.toString()));
    }

    @Test
    public void shouldReturnInputDataAlreadyExistsErrorMessage() throws Exception {
        // Scenario
        doSimpleUpload(1L, "UGVkcm8gSHVtYmVydG8gTWF0dGlvbGxv=", InputType.LEFT);

        // Action
        MvcResult mvcResult = mockMvc.perform(post("/v1/diff/1/left")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\": \"QnJlbm8=\"}"))
                .andReturn();

        // Validation
        InputDataAlreadyExistsException ex = new InputDataAlreadyExistsException(1L, InputType.LEFT);
        String[] args = { ex.getType().name(), ex.getId().toString() };
        String userMessage = messageSource.getMessage("already.exists.jsondata", args, LocaleContextHolder.getLocale());

        MockHttpServletResponse response = mvcResult.getResponse();
        errorCollector.checkThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        errorCollector.checkThat(response.getContentAsString(), containsString(userMessage));
        errorCollector.checkThat(response.getContentAsString(), containsString(ex.toString()));
    }

    @Test
    public void shouldReturnEmptyJsonDataErrorMessage() throws Exception {
        // Action
        MvcResult mvcResult = mockMvc.perform(post("/v1/diff/1/left")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\": \"\"}"))
                .andReturn();

        // Validation
        EmptyJsonDataException ex = new EmptyJsonDataException();
        String userMessage = messageSource.getMessage("empty.jsondata.uploaded", null, LocaleContextHolder.getLocale());

        MockHttpServletResponse response = mvcResult.getResponse();
        errorCollector.checkThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        errorCollector.checkThat(response.getContentAsString(), containsString(userMessage));
        errorCollector.checkThat(response.getContentAsString(), containsString(ex.toString()));
    }

    private MvcResult doSimpleUpload(Long id, String value, InputType type) throws Exception {
        return mockMvc.perform(post("/v1/diff/1/" + type.name().toLowerCase())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\": \"" + value + "\"}"))
                .andExpect(status().isCreated())
                .andReturn();
    }

}
