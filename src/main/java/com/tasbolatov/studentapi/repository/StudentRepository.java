package com.tasbolatov.studentapi.repository;

import com.tasbolatov.studentapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
