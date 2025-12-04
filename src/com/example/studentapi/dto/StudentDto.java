package com.example.studentapi.dto;

import java.util.HashSet;
import java.util.Set;

public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Long> courseIds = new HashSet<>();

    public StudentDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<Long> getCourseIds() { return courseIds; }
    public void setCourseIds(Set<Long> courseIds) { this.courseIds = courseIds; }
}

