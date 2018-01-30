package com.wearewaes.service;

import com.wearewaes.builder.JSONDataBuilder;
import com.wearewaes.builder.JSONDataDTOBuilder;
import com.wearewaes.model.InputType;
import com.wearewaes.model.JSONData;
import com.wearewaes.model.JSONDataDTO;
import com.wearewaes.repository.JSONDataRepository;
import com.wearewaes.service.exception.EmptyJsonDataException;
import com.wearewaes.service.exception.FileAlreadyExistsException;
import com.wearewaes.service.exception.IDNotFoundException;
import com.wearewaes.service.exception.NumberOfFilesToDiffException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;

public class JSONDataDiffServiceTest {

    @Mock
    public JSONDataRepository jsonDataRepository;

    @InjectMocks
    public JSONDataDiffServiceImpl jSONDataDiffService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveDataWithNoDiffIdRegistered() {
        // Scenario
        Long id = 1L;
        JSONDataDTO jsonDataDTO = JSONDataDTOBuilder.oneData().get();
        JSONData jsonDataCreated = JSONDataBuilder.oneData().withLeft(jsonDataDTO.getValue()).get();

        Mockito.doReturn(null).when(jsonDataRepository).findByDiffId(id);
        Mockito.when(jsonDataRepository.save(Mockito.any(JSONData.class))).thenReturn(null);

        // Action
        jSONDataDiffService.saveLeft(id, jsonDataDTO);

        // Validation
        Mockito.verify(jsonDataRepository).save(jsonDataCreated);
    }

    @Test(expected = EmptyJsonDataException.class)
    public void shouldNotSaveEmptyData() {
        // Scenario
        Long id = 1L;
        JSONDataDTO jsonDataDTO = JSONDataDTOBuilder.oneData().withoutValue().get();

        // Action
        jSONDataDiffService.saveRight(id, jsonDataDTO);
    }

    @Test
    public void shouldNotSaveIfDataAlreadyExists() {
        // Scenario
        Long id = 1L;
        JSONDataDTO jsonDataDTO = JSONDataDTOBuilder.oneData().get();
        JSONData jsonData = JSONDataBuilder.oneData().withId(id).withRight(jsonDataDTO.getValue()).get();
        Mockito.doReturn(jsonData).when(jsonDataRepository).findByDiffId(id);

        // Action
        try {
            jSONDataDiffService.saveRight(id, jsonDataDTO);
            Assert.fail();
        } catch (FileAlreadyExistsException e) {
            Assert.assertThat(e.getId(), is(id));
            Assert.assertThat(e.getType(), is(InputType.RIGHT));
        }
    }

    @Test
    public void shouldNotDiffFilesWhenIdIsNotFound(){
        // Scenario
        Long id = 1L;
        Mockito.doReturn(null).when(jsonDataRepository).findByDiffId(id);

        // Action
        try {
            jSONDataDiffService.getJSONDiffResult(id);
            Assert.fail();
        } catch (IDNotFoundException e) {
            Assert.assertThat(e.getId(), is(id));
        }
    }

    @Test
    public void shouldNotDiffFilesWhenIsMissingOne(){
        // Scenario
        Long id = 1L;
        JSONData jsonData = JSONDataBuilder.oneData().withId(id).withLeft(JSONDataDTOBuilder.oneData().get().getValue()).get();
        Mockito.doReturn(jsonData).when(jsonDataRepository).findByDiffId(id);

        // Action
        try {
            jSONDataDiffService.getJSONDiffResult(id);
            Assert.fail();
        } catch (NumberOfFilesToDiffException e) {
            Assert.assertThat(e.getId(), is(id));
            Assert.assertThat(e.isHasLeftJson(), is(true));
            Assert.assertThat(e.isHasRightJson(), is(false));
        }
    }

    @Test
    public void shouldBeEqual(){
        // Scenario
        Long id = 1L;
        String value = JSONDataDTOBuilder.oneData().get().getValue();
        JSONData jsonData = JSONDataBuilder.oneData().withId(id).withLeft(value).withRight(value).get();
        Mockito.doReturn(jsonData).when(jsonDataRepository).findByDiffId(id);

        // Action
        String result = jSONDataDiffService.getJSONDiffResult(id);

        // Verification
        Assert.assertThat(result, is("They are equal"));
    }

    @Test
    public void shouldHaveDifferentSize(){
        // Scenario
        Long id = 1L;
        String left = "UGVkcm8gSHVtYmVydG8=";
        String right = "UGVkcm8=";
        JSONData jsonData = JSONDataBuilder.oneData().withId(id).withLeft(left).withRight(right).get();
        Mockito.doReturn(jsonData).when(jsonDataRepository).findByDiffId(id);

        // Action
        String result = jSONDataDiffService.getJSONDiffResult(id);

        // Verification
        Assert.assertThat(result, is("They have different sizes. Left JSON size: " + left.length() + ", Right JSON size: " + right.length()));
    }

    @Test
    public void shouldHaveSameSizeButDifferentContent(){
        // Scenario
        Long id = 1L;
        JSONData jsonData = JSONDataBuilder.oneData().withId(id).withLeft("QnJlbm8=").withRight("UGVkcm8=").get();
        Mockito.doReturn(jsonData).when(jsonDataRepository).findByDiffId(id);

        // Action
        String result = jSONDataDiffService.getJSONDiffResult(id);

        // Verification
        Assert.assertThat(result, is("They have same sizes, but their content are different. Wrong bytes offsets: 0, 1, 2, 3, 4"));
    }

}