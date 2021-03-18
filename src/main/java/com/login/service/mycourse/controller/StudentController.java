package com.login.service.mycourse.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.service.mycourse.model.Student;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

	public static final List<Student> STUDENTS = Arrays.asList(new Student(1, "John"), new Student(2, "Bob"),
			new Student(3, "Max"));

	@GetMapping(path = "/{id}")
	public Student getStudent(@PathVariable("id") Integer studentId) {
		return STUDENTS.stream().filter(student -> studentId.equals(student.getId())).findFirst()
				.orElseThrow(() -> new IllegalStateException("Student " + studentId + " doesn't exist."));
	}
}
