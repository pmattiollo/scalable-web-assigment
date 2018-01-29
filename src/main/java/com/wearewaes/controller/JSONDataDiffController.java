package com.wearewaes.controller;

import com.wearewaes.controller.response.Response;
import com.wearewaes.controller.response.ResponseBuilder;
import com.wearewaes.event.ResourceCreatedEvent;
import com.wearewaes.model.JSONDataTO;
import com.wearewaes.service.JSONDataDiffService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/diff/{id}")
public class JSONDataDiffController {

    private JSONDataDiffService diffFilesService;
    private ApplicationEventPublisher publisher;

    public JSONDataDiffController(JSONDataDiffService diffFilesService, ApplicationEventPublisher publisher) {
        this.diffFilesService = diffFilesService;
        this.publisher = publisher;
    }

    @PostMapping("/left")
    public ResponseEntity<Response> uploadLeftFile(@PathVariable Long id, @RequestBody JSONDataTO inputJSONData, HttpServletResponse response) {
        diffFilesService.saveLeft(id, inputJSONData);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, id));
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilder.oneLeftDataSettedUpResponse().get());
    }

    @PostMapping("/right")
    public ResponseEntity<Response> uploadRightFile(@PathVariable Long id, @RequestBody JSONDataTO inputJSONData, HttpServletResponse response) {
        diffFilesService.saveRight(id, inputJSONData);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, id));
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilder.oneRightDataSettedUpResponse().get());
    }

    @GetMapping()
    public ResponseEntity<Response> getDiffResults(@PathVariable Long id) {
        String result = diffFilesService.getJSONDiffResult(id);
        return ResponseEntity.ok(ResponseBuilder.oneDiffResponse(result).get());
    }
}
