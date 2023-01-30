package com.ufcg.hateoas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.hateoas.model.Class;
import com.ufcg.hateoas.model.Student;
import com.ufcg.hateoas.service.ClassService;
import com.ufcg.hateoas.service.StudentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/class")
public class ClassController {
    
    @Autowired
    private ClassService classService;

    @Autowired 
    private StudentService studentService;

    
    @PostMapping
    public EntityModel<?> createClass(@RequestBody Class newClass) {
        Class myClass = this.classService.createClass(newClass);

        List<Link> links = new ArrayList<>();

        links.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).createClass(newClass)).withSelfRel()
        );
        links.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getClass(newClass.getId())).withRel("get-class")
        );
        links.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteClass(newClass.getId())).withRel("delete-class")
        );

        List<Student> students = this.studentService.getAllStudents();

        students.forEach((s) -> links.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).addStudentToClass(newClass.getId(), s.getId())).withRel("add-student-to-class")));

        return EntityModel.of(myClass, links);
    }


    @GetMapping("/{id}")
    public EntityModel<?> getClass(@PathVariable("id") Long id) {
        Class myClass = this.classService.getClass(id);

        List<Link> links = new ArrayList<>();

        links.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getClass(id)).withSelfRel()
        );
        links.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteClass(id)).withRel("delete-class")
        );

        List<Student> students = this.studentService.getAllStudents();

        students.forEach((s) -> links.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).addStudentToClass(id, s.getId())).withRel("add-student-to-class")));

        return EntityModel.of(myClass, links);
    }
    

    @DeleteMapping("/{id}")
    public EntityModel<?> deleteClass(@PathVariable("id") Long id) {
        Class myClass = this.classService.getClass(id);

        List<Link> links = new ArrayList<>();

        links.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteClass(id)).withSelfRel()
        );

        return EntityModel.of(myClass, links);
    }

    @PutMapping("/{id}/{studentId}")
    public EntityModel<?> addStudentToClass(@PathVariable("id") Long classId, @PathVariable("studentId") Long studentId) {
        Class myClass = this.classService.getClass(classId);

        List<Link> links = new ArrayList<>();

        links.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).addStudentToClass(classId, studentId)).withSelfRel()
        );
        links.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getClass(classId)).withRel("get-class")
        );
        links.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteClass(classId)).withRel("delete-class")
        );

        return EntityModel.of(myClass, links);
    }
}
