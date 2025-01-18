package com.example.candid.Myql_POC;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@Validated
public class PanglossController {

    @Autowired
    PanglossService panglossService;

    @GetMapping("/books")

    public ResponseEntity<List<VoltireModel>> get()
    {
        log.info("This is an info log message using Lombok!");
       List<VoltireModel> models = panglossService.findAllModels();
       return new ResponseEntity<>(models, HttpStatus.OK);

    }

    @GetMapping("/book/id/{id}")

    @Operation(summary = "Get product details", description = "Fetch details of a product by its ID")
    public ResponseEntity<VoltireModel> getById(
            @NotNull(message = "ID cannot be null")
            @Positive
            @PathVariable Integer id)
    {
       log.info("This is the real call.......................");
        VoltireModel model = panglossService.findModelById(id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping("/books")
    public  ResponseEntity<VoltireModel> post(@Valid @RequestBody VoltireModel voltireModel)
    {
        VoltireModel savedModel =   panglossService.saveModel(voltireModel);
        return new ResponseEntity<>(savedModel, HttpStatus.CREATED);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteMethod(
            @PathVariable
             @Min(value = 1, message = "User ID must be at least 1")
            Integer id)
    {
         panglossService.deleteModelById(id);

        return ResponseEntity.ok("User with ID " + id + " has been deleted.");
    }
    @GetMapping("/test")
    public String test()
    {
        return  "All is for good reson dear condgone";
    }
}
