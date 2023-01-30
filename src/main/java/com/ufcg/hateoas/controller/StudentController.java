package com.ufcg.hateoas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.hateoas.model.Class;
import com.ufcg.hateoas.model.Student;
import com.ufcg.hateoas.service.ClassService;
import com.ufcg.hateoas.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

    @PostMapping
    public EntityModel<?> createStudent(@RequestBody Student student) {
        Student newStudent = this.studentService.createStudent(student);

        List<Link> links = new ArrayList<>();
        links.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).createStudent(newStudent)
        ).withSelfRel());
        links.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).getStudent(newStudent.getId())
        ).withRel("get-student"));
        links.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).deleteStudent(newStudent.getId())
        ).withRel("delete-student"));

        List<Class> classes = this.classService.getAllClasses();

        classes.forEach((c) -> links.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClassController.class).addStudentToClass(c.getId(), newStudent.getId())).withRel("add-student-to-class")
        ));


        return EntityModel.of(newStudent, links);
    }

    @GetMapping("/{id}")
    public EntityModel<?> getStudent(@PathVariable("id") Long id) {
        Student student = this.studentService.getStudent(id);

        List<Link> links = new ArrayList<>();

        links.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).getStudent(id)
        ).withSelfRel());
        links.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).deleteStudent(student.getId())
        ).withRel("delete-student"));

        List<Class> classes = this.classService.getAllClasses();

        classes.forEach((c) -> links.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClassController.class).addStudentToClass(c.getId(), id)).withRel("add-student-to-class")
        ));

        return EntityModel.of(student, links);
    }

    @DeleteMapping("/{id}")
    public EntityModel<?> deleteStudent(@PathVariable("id") Long id) {
        Student student = this.studentService.getStudent(id);

        List<Link> links = new ArrayList<>();

        links.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).deleteStudent(id)
        ).withSelfRel());

        return EntityModel.of(student, links);
    }

}
