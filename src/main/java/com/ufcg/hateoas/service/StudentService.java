package com.ufcg.hateoas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ufcg.hateoas.model.Student;

@Service
public class StudentService {
    
    private Map<Long, Student> students = new HashMap<>();

    public Student createStudent(Student student) {
        this.students.put(student.getId(), student);
        return this.students.get(student.getId());
    }

    public Student getStudent(Long id) {
        return this.students.get(id);
    }

    public Student removeStudent(Long id) {
        return this.students.remove(id);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<Student>(this.students.values());
    }
}
