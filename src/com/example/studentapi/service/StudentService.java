package com.example.studentapi.service;

import com.example.studentapi.entity.Student;
import com.example.studentapi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student create(Student s) { return studentRepository.save(s); }
    public Optional<Student> get(Long id) { return studentRepository.findById(id); }
    public List<Student> list() { return studentRepository.findAll(); }
    public Student update(Student s) { return studentRepository.save(s); }
    public void delete(Long id) { studentRepository.deleteById(id); }
}

