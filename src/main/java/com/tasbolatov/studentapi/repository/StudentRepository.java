package com.tasbolatov.studentapi.repository;

import com.tasbolatov.studentapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Added by Tasbolatov Temirlan: search students by last name
    List<Student> findByLastNameIgnoreCase(String lastName);
}
