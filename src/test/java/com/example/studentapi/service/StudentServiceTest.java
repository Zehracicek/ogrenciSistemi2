package com.example.studentapi.service;

import com.example.studentapi.entity.Student;
import com.example.studentapi.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {
    private StudentRepository repo = mock(StudentRepository.class);
    private StudentService service;

    @BeforeEach
    public void setup() {
        service = new StudentService();
        TestUtils.inject(service, "studentRepository", repo);
    }

    @Test
    public void createAndList() {
        Student s = new Student(); s.setFirstName("A"); s.setLastName("B"); s.setEmail("a@b.com");
        when(repo.save(s)).thenReturn(s);
        when(repo.findAll()).thenReturn(List.of(s));

        var created = service.create(s);
        assertNotNull(created);
        var list = service.list();
        assertEquals(1, list.size());
    }
}

