package com.login.service.mycourse.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.service.mycourse.model.Student;

@RestController
@RequestMapping("admin/api/v1/students")
public class StudentAdminController {
	public static final List<Student> STUDENTS = Arrays.asList(new Student(1, "John"), new Student(2, "Bob"),
			new Student(3, "Max"));
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
	public List<Student> getAllStudents(){
		return STUDENTS;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public void registerNewStudent(@RequestBody Student student) {
		System.out.println("Create new student" + student);
	}
	
	@DeleteMapping(path="{id}")
	@PreAuthorize("hasAuthority('student:write')")
	public void deleteStudentById(@PathVariable("id") Integer id) {
		System.out.println("Delete Student id: " + id);
	}
	
	@PutMapping(path = "{id}")
	@PreAuthorize("hasAuthority('student:write')")
	public void updateStudentById(@PathVariable("id") Integer id, @RequestBody Student student) {
		System.out.println("Update Student: " + id);
	}
	
	
}
