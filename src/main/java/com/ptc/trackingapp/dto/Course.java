package com.ptc.trackingapp.dto;


import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Course {
  private String name;
  private String code;
  private List<String> students;
}
