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

@RestController
@RequestMapping("/v1/diff")
public class JSONDataDiffController {

    private JSONDataDiffService diffFilesService;
    private ApplicationEventPublisher publisher;

    public JSONDataDiffController(JSONDataDiffService diffFilesService, ApplicationEventPublisher publisher) {
        this.diffFilesService = diffFilesService;
        this.publisher = publisher;
    }

    @ApiOperation(
            value = "This will upload a left data for comparision",
            notes = "Please make sure that ID is not null, the left data for the ID was not uploaded yet and that json value is not empty")
    @PostMapping("/{id}/left")
    public ResponseEntity<Response> uploadLeftFile(@PathVariable Long id, @RequestBody JSONDataDTO jsonDataDTO, HttpServletResponse response) {
        diffFilesService.saveLeft(id, jsonDataDTO);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, id));
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilder.oneLeftDataSettedUpResponse().get());
    }

    @ApiOperation(
            value = "This will upload a right data for comparision",
            notes = "Please make sure that ID is not null, the right data for the ID was not uploaded yet and that json value is not empty")
    @PostMapping("/{id}/right")
    public ResponseEntity<Response> uploadRightFile(@PathVariable Long id, @RequestBody JSONDataDTO jsonDataDTO, HttpServletResponse response) {
        diffFilesService.saveRight(id, jsonDataDTO);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, id));
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilder.oneRightDataSettedUpResponse().get());
    }

    @ApiOperation(
            value = "This will get the diff between left and right data",
            notes = "Please make sure that ID is not null and that the both data were uploaded successfully")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getDiffResults(@PathVariable Long id) {
        String result = diffFilesService.getJSONDiffResult(id);
        return ResponseEntity.ok(ResponseBuilder.oneDiffResponse(result).get());
    }
}
