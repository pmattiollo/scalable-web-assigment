package com.wearewaes.controller;

import com.wearewaes.controller.response.Response;
import com.wearewaes.controller.response.ResponseBuilder;
import com.wearewaes.event.ResourceCreatedEvent;
import com.wearewaes.model.JSONDataDTO;
import com.wearewaes.service.JSONDataDiffService;
import com.wearewaes.service.JSONDataDiffServiceImpl;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/diff")
public class JSONDataDiffController {

    private JSONDataDiffService diffFilesService;
    private ApplicationEventPublisher publisher;

    public JSONDataDiffController(JSONDataDiffService diffFilesService, ApplicationEventPublisher publisher) {
        this.diffFilesService = diffFilesService;
        this.publisher = publisher;
    }

    @PostMapping("/{id}/left")
    public ResponseEntity<Response> uploadLeftFile(@PathVariable Long id, @RequestBody JSONDataDTO jsonDataDTO, HttpServletResponse response) {
        diffFilesService.saveLeft(id, jsonDataDTO);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, id));
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilder.oneLeftDataSettedUpResponse().get());
    }

    @PostMapping("/{id}/right")
    public ResponseEntity<Response> uploadRightFile(@PathVariable Long id, @RequestBody JSONDataDTO jsonDataDTO, HttpServletResponse response) {
        diffFilesService.saveRight(id, jsonDataDTO);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, id));
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilder.oneRightDataSettedUpResponse().get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getDiffResults(@PathVariable Long id) {
        String result = diffFilesService.getJSONDiffResult(id);
        return ResponseEntity.ok(ResponseBuilder.oneDiffResponse(result).get());
    }
}
