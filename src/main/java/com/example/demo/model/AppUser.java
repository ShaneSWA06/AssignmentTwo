package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "name")
    private String name;

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
