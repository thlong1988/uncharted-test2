package com.ptc.trackingapp.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ptc.trackingapp.contract.api.TrackingApi;
import com.ptc.trackingapp.dto.Course;
import com.ptc.trackingapp.dto.Student;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackingController implements TrackingApi {

  private Gson gson = new Gson();

  @Override
  public List<Course> findCouses_streamSolution(Integer minNumberOfRegister)
      throws IOException {
    Integer serachCondition = Optional.ofNullable(minNumberOfRegister).orElse(0);

    JsonReader reader = getJsonFile(false);
    List<Course> courses = new ArrayList<Course>();
    reader.beginArray();
    while (reader.hasNext()) {
      Course course = gson.fromJson(reader, Course.class);
      // filter
      if (serachCondition <= 0) {
        courses.add(course);
      } else if (course.getStudents() != null && course.getStudents().size() >= serachCondition) {
        courses.add(course);
      }
    }
    reader.endArray();
    reader.close();
    return courses;
  }

  @Override
  public List<Student> findStudentsNotRegisted_streamSolution() throws IOException {
    JsonReader reader = getJsonFile(true);
    List<Student> students = new ArrayList<Student>();
    reader.beginArray();
    while (reader.hasNext()) {
      Student student = gson.fromJson(reader, Student.class);
      if (student.getCourses() == null || student.getCourses().size() == 0) {
        students.add(student);
      }

    }
    reader.endArray();
    reader.close();
    return students;
  }

  @Override
  public List<Course> findCouses_abstractModelBindingSolution(Integer minNumberOfRegister)
      throws IOException {
    List<Course> courses = new ArrayList<>();
    Integer serachCondition = Optional.ofNullable(minNumberOfRegister).orElse(0);

    JsonElement parsedTree = JsonParser.parseReader(getJsonFile(false));
    JsonArray root = parsedTree.getAsJsonArray();
    for (int i = 0; i < root.size(); i++) {
      JsonObject element = root.get(i).getAsJsonObject();
      String courseName = element.get("name").getAsString();
      String courseCode = element.get("code").getAsString();
      JsonArray studentsJson = Optional.ofNullable(element.get("students"))
          .map(a -> a.getAsJsonArray())
          .orElse(new JsonArray());

      List<String> students = gson.fromJson(studentsJson.toString(),
          new TypeToken<List<String>>() {
          }.getType());

      Course course = Course.builder().code(courseCode).name(courseName).students(students).build();
      // filter
      if (serachCondition <= 0) {
        courses.add(course);
      } else if (studentsJson != null && studentsJson.size() >= serachCondition) {
        courses.add(course);
      }
    }

    return courses;
  }

  @Override
  public List<Student> findStudentsNotRegisted_abstractModelBindingSolution() throws IOException {
    List<Student> students = new ArrayList<>();

    JsonElement parsedTree = JsonParser.parseReader(getJsonFile(true));
    JsonArray root = parsedTree.getAsJsonArray();
    for (int i = 0; i < root.size(); i++) {
      JsonObject element = root.get(i).getAsJsonObject();
      String studentName = element.get("name").getAsString();
      String studentCode = element.get("code").getAsString();
      JsonArray course = Optional.ofNullable(element.get("courses")).map(a -> a.getAsJsonArray())
          .orElse(null);
      if (course == null || course.size() == 0) {
        Student student = Student.builder().code(studentCode).name(studentName).build();
        students.add(student);
      }
    }
    return students;
  }

  @Override
  public List<Course> findCouses_domainModelBindingSolution(Integer minNumberOfRegister)
      throws IOException {
    Integer serachCondition = Optional.ofNullable(minNumberOfRegister).orElse(0);

    Type listType = new TypeToken<List<Course>>() {
    }.getType();
    List<Course> students = gson.fromJson(getJsonFile(false), listType);
    if (serachCondition <= 0) {
      return students;
    }
    List<Course> response = students.stream()
        .filter(a -> a.getStudents() != null && a.getStudents().size() >= minNumberOfRegister)
        .collect(
            Collectors.toList());

    return response;
  }

  @Override
  public List<Student> findStudentsNotRegisted_domainModelBindingSolution() throws IOException {
    Type listType = new TypeToken<List<Student>>() {
    }.getType();
    List<Student> students = gson.fromJson(getJsonFile(true), listType);
    List<Student> response = students.stream()
        .filter(a -> a.getCourses() == null || a.getCourses().size() == 0).collect(
            Collectors.toList());
    return response;
  }

  private JsonReader getJsonFile(boolean isFromStudentFile) throws IOException {
    InputStream resource = new ClassPathResource("/json/courses.json").getInputStream();
    if (isFromStudentFile) {
      resource = new ClassPathResource("/json/students.json").getInputStream();
    }
    JsonReader reader = new JsonReader(new InputStreamReader(resource, "UTF-8"));
    return reader;
  }
}
