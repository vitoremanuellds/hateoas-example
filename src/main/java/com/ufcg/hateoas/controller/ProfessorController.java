package com.ufcg.hateoas.controller;

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

import com.ufcg.hateoas.model.Professor;
import com.ufcg.hateoas.service.ProfessorService;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    
    @Autowired
    private ProfessorService professorService;

    @PostMapping
    public EntityModel<?> createProfessor(@RequestBody Professor professor) {
        Professor newProfessor = this.professorService.createProfessor(professor);

        Link selfLink = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(
                this.getClass()
                ).createProfessor(newProfessor)
            ).withSelfRel();

        Link getProfessorLink = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).getProfessor(newProfessor.getId())
        ).withRel("get-professor");

        Link removeProfessorLink = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).removeProfessor(newProfessor.getId())
        ).withRel("remove-professor");

        return EntityModel.of(
            newProfessor,
            selfLink,
            getProfessorLink,
            removeProfessorLink
        );
    }


    @GetMapping("/{id}")
    public EntityModel<?> getProfessor(@PathVariable("id") Long id) {
        Professor myProfessor = this.professorService.getProfessor(id);

        Link selfLink = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).getProfessor(myProfessor.getId())
        ).withSelfRel();

        Link removeProfessorLink = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).removeProfessor(myProfessor.getId())
        ).withRel("remove-professor");

        return EntityModel.of(myProfessor, selfLink, removeProfessorLink);
    }


    @DeleteMapping("/{id}")
    public EntityModel<?> removeProfessor(@PathVariable("id") Long id) {
        Professor myProfessor = this.professorService.removeProfessor(id);    
        return EntityModel.of(myProfessor);
    }

}
