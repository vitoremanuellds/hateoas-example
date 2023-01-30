package com.ufcg.hateoas.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ufcg.hateoas.model.Professor;

@Service
public class ProfessorService {
    
    private Map<Long, Professor> professors = new HashMap<>();

    public Professor createProfessor(Professor professor) {
        this.professors.put(professor.getId(), professor);
        return this.professors.get(professor.getId());
    }

    public Professor getProfessor(Long id) {
        return this.professors.get(id);
    }

    public Professor removeProfessor(Long id) {
        return this.professors.remove(id);
    }

}
