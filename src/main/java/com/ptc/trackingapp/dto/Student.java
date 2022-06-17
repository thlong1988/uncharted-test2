package com.ptc.trackingapp.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Student {
  private String name;
  private String code;
  private List<String> courses;
}
