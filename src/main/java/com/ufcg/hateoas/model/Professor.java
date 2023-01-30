package com.ufcg.hateoas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professor {
 
    private Long id;
    private String name;
    private String lastName;

}
