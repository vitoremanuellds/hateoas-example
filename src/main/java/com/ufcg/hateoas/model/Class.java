package com.ufcg.hateoas.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    
    private Long id;
    private String name;
    private Professor professor;
    private Map<Long, Student> students;
    private Map<Long, Double> finalGrades;

}
