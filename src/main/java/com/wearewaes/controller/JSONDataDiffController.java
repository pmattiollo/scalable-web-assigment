package com.wearewaes.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wearewaes.controller.response.Response;
import com.wearewaes.controller.response.ResponseBuilder;
import com.wearewaes.event.ResourceCreatedEvent;
import com.wearewaes.model.JSONDataDTO;
import com.wearewaes.service.JSONDataDiffService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller that defines endpoints to receive all user requests and return the results
 */
@RestController
@RequestMapping("/v1/diff")
public class JSONDataDiffController {

    private JSONDataDiffService diffFilesService;
    private ApplicationEventPublisher publisher;

    /**
     * Constructor of the controller where dependencies will get injected
     *
     * @param diffFilesService that represents the service
     * @param publisher that represents the event publisher
     */
    public JSONDataDiffController(JSONDataDiffService diffFilesService, ApplicationEventPublisher publisher) {
        this.diffFilesService = diffFilesService;
        this.publisher = publisher;
    }

    /**
     * Endpoint that defines an entry point for posting left value for comparison
     *
     * @param id that represents the data identifier to be uploaded
     * @param jsonDataDTO that represents the base64 encoded value
     * @param response that represents the requisition HTTP response
     * @return the {@link ResponseEntity} with the standardized response informing properly feedback messages for the user
     */
    @ApiOperation(
            value = "This will upload a left data for comparision",
            notes = "Please make sure that ID is not null, the left data for the ID was not uploaded yet and that json value is not empty")
    @PostMapping("/{id}/left")
    public ResponseEntity<Response> uploadLeftFile(@PathVariable Long id, @RequestBody JSONDataDTO jsonDataDTO, HttpServletResponse response) {
        diffFilesService.saveLeft(id, jsonDataDTO);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, id));
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilder.oneLeftDataSettedUpResponse().get());
    }

    /**
     * Endpoint that defines an entry point for posting right value for comparison
     *
     * @param id that represents the data identifier to be uploaded
     * @param jsonDataDTO that represents the base64 encoded value
     * @param response that represents the requisition HTTP response
     * @return the {@link ResponseEntity} with the standardized response informing properly feedback messages for the user
     */
    @ApiOperation(
            value = "This will upload a right data for comparision",
            notes = "Please make sure that ID is not null, the right data for the ID was not uploaded yet and that json value is not empty")
    @PostMapping("/{id}/right")
    public ResponseEntity<Response> uploadRightFile(@PathVariable Long id, @RequestBody JSONDataDTO jsonDataDTO, HttpServletResponse response) {
        diffFilesService.saveRight(id, jsonDataDTO);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, id));
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilder.oneRightDataSettedUpResponse().get());
    }

    /**
     * Endpoint that perform the comparison between left and right data previously uploaded
     *
     * @param id that represents the uploaded data identifier
     * @return the {@link ResponseEntity} with the standardized response informing properly data diff results
     */
    @ApiOperation(
            value = "This will get the diff between left and right data",
            notes = "Please make sure that ID is not null and that the both data were uploaded successfully")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getDiffResults(@PathVariable Long id) {
        String result = diffFilesService.getJSONDiffResult(id);
        return ResponseEntity.ok(ResponseBuilder.oneDiffResponse(result).get());
    }
}
