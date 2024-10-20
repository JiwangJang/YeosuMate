package com.practice.entities;

import java.util.Date;

import lombok.Data;

@Data
public class Role {
    private Integer id;
    private RoleEnum name;
    private String description;
    private Date createdAt;
    private Date updatedAt;

}
