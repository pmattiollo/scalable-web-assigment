package com.wearewaes.controller;

import com.wearewaes.controller.response.Response;
import com.wearewaes.controller.response.ResponseBuilder;
import com.wearewaes.model.JSONDataTO;
import com.wearewaes.service.JSONDataDiffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diff/{id}")
public class JSONDataDiffController {

    private JSONDataDiffService diffFilesService;

    public JSONDataDiffController(JSONDataDiffService diffFilesService) {
        this.diffFilesService = diffFilesService;
    }

    @PostMapping("/left")
    public ResponseEntity<Response> uploadLeftFile(@PathVariable Long id, @RequestBody JSONDataTO inputJSONData) {
        diffFilesService.saveLeft(id, inputJSONData);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilder.oneLeftDataSettedUpResponse().get());
    }

    @PostMapping("/right")
    public ResponseEntity<Response> uploadRightFile(@PathVariable Long id, @RequestBody JSONDataTO inputJSONData) {
        diffFilesService.saveRight(id, inputJSONData);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilder.oneRightDataSettedUpResponse().get());
    }

    @GetMapping()
    public ResponseEntity<Response> getDiffResults(@PathVariable Long id) {
        String result = diffFilesService.getJSONDiffResult(id);
        return ResponseEntity.ok(ResponseBuilder.oneDiffResponse(result).get());
    }
}
