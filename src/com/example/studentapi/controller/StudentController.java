package com.example.studentapi.controller;

import com.example.studentapi.dto.StudentDto;
import com.example.studentapi.entity.Role;
import com.example.studentapi.entity.Student;
import com.example.studentapi.entity.User;
import com.example.studentapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    private User currentUser(HttpServletRequest req) {
        return (User) req.getAttribute("currentUser");
    }

    private boolean isAdmin(User u) { return u != null && u.getRole() == Role.ADMIN; }

    @GetMapping
    public ResponseEntity<?> list(HttpServletRequest req) {
        User u = currentUser(req);
        if (u == null) return ResponseEntity.status(401).build();
        if (!isAdmin(u)) return ResponseEntity.status(403).build();
        List<StudentDto> list = studentService.list().stream().map(s -> {
            StudentDto d = new StudentDto();
            d.setId(s.getId()); d.setFirstName(s.getFirstName()); d.setLastName(s.getLastName()); d.setEmail(s.getEmail());
            return d;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody StudentDto dto, HttpServletRequest req) {
        User u = currentUser(req);
        if (u == null) return ResponseEntity.status(401).build();
        if (!isAdmin(u)) return ResponseEntity.status(403).build();
        Student s = new Student();
        s.setFirstName(dto.getFirstName()); s.setLastName(dto.getLastName()); s.setEmail(dto.getEmail());
        Student saved = studentService.create(s);
        dto.setId(saved.getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, HttpServletRequest req) {
        User u = currentUser(req);
        if (u == null) return ResponseEntity.status(401).build();
        if (!isAdmin(u)) return ResponseEntity.status(403).build();
        return studentService.get(id).map(s -> {
            StudentDto d = new StudentDto(); d.setId(s.getId()); d.setFirstName(s.getFirstName()); d.setLastName(s.getLastName()); d.setEmail(s.getEmail());
            return ResponseEntity.ok(d);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody StudentDto dto, HttpServletRequest req) {
        User u = currentUser(req);
        if (u == null) return ResponseEntity.status(401).build();
        if (!isAdmin(u)) return ResponseEntity.status(403).build();
        return studentService.get(id).map(s -> {
            s.setFirstName(dto.getFirstName()); s.setLastName(dto.getLastName()); s.setEmail(dto.getEmail());
            studentService.update(s);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest req) {
        User u = currentUser(req);
        if (u == null) return ResponseEntity.status(401).build();
        if (!isAdmin(u)) return ResponseEntity.status(403).build();
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }
}

