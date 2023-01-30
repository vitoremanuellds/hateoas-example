package com.ufcg.hateoas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ufcg.hateoas.model.Class;
import com.ufcg.hateoas.model.Student;

@Service
public class ClassService {

    private Map<Long, Class> classes = new HashMap<>();

    public Class createClass(Class newClass) {
        this.classes.put(newClass.getId(), newClass);
        return this.classes.get(newClass.getId());
    }


    public Class getClass(Long id) {
        return this.classes.get(id);
    }


    public Class removeClass(Long id) {
        return this.classes.remove(id);
    }

    public Class addStudentToClass(Long classId, Student student) {
        Class myClass = this.classes.get(classId);
        myClass.getStudents().put(student.getId(), student);
        this.classes.put(classId, myClass);
        return this.classes.get(classId);    
    }

    public List<Class> getAllClasses() {
        return new ArrayList<Class>(this.classes.values());
    }

}
