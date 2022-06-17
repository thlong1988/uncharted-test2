package com.ptc.trackingapp.contract.api;

import com.ptc.trackingapp.dto.Course;
import com.ptc.trackingapp.dto.Student;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(
    name = "Tracking API",
    description = "Track the number of students and courses they choose"
)
@RequestMapping("track")
public interface TrackingApi {

  @Operation(summary = "Get list courses. This code reads a JSON document. "
      + "It steps through array elements as a stream to avoid loading the complete document into memory. "
      + "Best approach to parse huge (extra large) JSON file")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Successfully",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = Course.class)),
              examples = {@ExampleObject("["
                  + "{"
                  + "  \"name\": \"Courses A\","
                  + "  \"code\": \"CA\","
                  + "  \"students\": [ "
                  + "      \"SA\","
                  + "      \"SC\","
                  + "      \"SB\""
                  + "  ]"
                  + "},"
                  + "{"
                  + "  \"name\": \"Courses B\","
                  + "  \"code\": \"CA\","
                  + "  \"students\": [ "
                  + "      \"SA\","
                  + "      \"SC\","
                  + "      \"SB\""
                  + "  ]"
                  + "}"
                  + "]")}))
  })
  @GetMapping("/v1/courses")
  List<Course> findCouses_streamSolution(
      @Parameter(description = "Find a course with the minimum number of students register from X students. Default find all")
      @RequestParam(name = "min_students", required = false) Integer minNumberOfRegister) throws IOException;

  @Operation(summary = "Get list students in no courses. This code reads a JSON document. "
      + "It steps through array elements as a stream to avoid loading the complete document into memory. "
      + "Best approach to parse huge (extra large) JSON file")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Successfully",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = Student.class)),
              examples = {@ExampleObject("["
                  + "{"
                  + "  \"name\": \"Student B\","
                  + "  \"code\": \"SB0123\","
                  + "  \"students\": []"
                  + "},"
                  + "{"
                  + "  \"name\": \"Student D\","
                  + "  \"code\": \"SD0123\","
                  + "  \"students\": []"
                  + "}"
                  + "]")}))
  })
  @GetMapping("/v1/students/not-register")
  List<Student> findStudentsNotRegisted_streamSolution() throws IOException;

  @Operation(summary = "Get list courses. Strategy approach for read Json file: Abstract Model Binding")
  @GetMapping("/v2/courses")
  List<Course> findCouses_abstractModelBindingSolution(
      @Parameter(description = "Find a course with the minimum number of students register from X students. Default find all")
      @RequestParam(name = "min_students", required = false) Integer minNumberOfRegister) throws IOException;

  @Operation(summary = "Get list students in no courses. Strategy approach for read Json file: Abstract Model Binding")
  @GetMapping("/v2/students/not-register")
  List<Student> findStudentsNotRegisted_abstractModelBindingSolution() throws IOException;

  @Operation(summary = "Get list courses. Strategy approach for read Json file: Domain Model Binding")
  @GetMapping("/v3/courses")
  List<Course> findCouses_domainModelBindingSolution(
      @Parameter(description = "Find a course with the minimum number of students register from X students. Default find all")
      @RequestParam(name = "min_students", required = false) Integer minNumberOfRegister) throws IOException;

  @Operation(summary = "Get list students in no courses. Strategy approach for read Json file: Domain Model Binding")
  @GetMapping("/v3/students/not-register")
  List<Student> findStudentsNotRegisted_domainModelBindingSolution() throws IOException;

}
