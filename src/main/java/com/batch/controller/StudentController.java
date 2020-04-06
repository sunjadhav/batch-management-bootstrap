package com.batch.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.batch.dto.BatchDTO;
import com.batch.dto.BatchStudent;
import com.batch.dto.Message;
import com.batch.dto.StudentDTO;
import com.batch.model.Batch;
import com.batch.model.Student;
import com.batch.service.BatchService;
import com.batch.service.StudentService;
import com.batch.service.UserService;

@Controller
public class StudentController {

	@Autowired
	private StudentService service;
	@Autowired
	private BatchService batchService;
	@Autowired
	private UserService userService;

	@GetMapping("/addstudent")
	public String showNewStudentPage(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			List<BatchDTO> batches = batchService.getActiveBatches();
			StudentDTO student = new StudentDTO();
			model.addAttribute("listbatches", batches);
			model.addAttribute("student", student);
			model.addAttribute("user", session.getAttribute("user"));
			return "addstudent";
		} else {
			return userService.indexRedirect(model);
		}
	}

	@GetMapping("/viewstudentlist")
	public String viewStudentPage(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			BatchStudent batchStudent = new BatchStudent();
			batchStudent.setNoRecord("No records found");
			model.addAttribute("batchstudent", batchStudent);
			model.addAttribute("user", session.getAttribute("user"));
			return "viewstudentlist";

		} else {
			return userService.indexRedirect(model);
		}
	}

	@PostMapping(value = "/viewstudentlist")
	public String search(HttpServletRequest request, @ModelAttribute("batchstudent") BatchStudent batchStudent,
			Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			List<Student> students = service.search(batchStudent.getStudentName(), batchStudent.getBatchName());
			if (students.isEmpty()) {
				batchStudent.setNoRecord("No records found");
			}
			model.addAttribute("students", students);
			model.addAttribute("user", session.getAttribute("user"));
			return "viewstudentlist";
		} else {
			return userService.indexRedirect(model);
		}
	}

	@PostMapping(value = "/addstudent")
	public ModelAndView saveStudent(HttpServletRequest request, @ModelAttribute("student") StudentDTO student, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Boolean response = service.saveStudent(student);
			ModelAndView view = new ModelAndView("message");
			Message message = new Message();
			model.addAttribute("user", session.getAttribute("user"));
			if (response)
				message.setInfo("Student added successfully !! :)");
			else
				message.setInfo("Duplicate Student , Please Verify Again :( ");
			view.addObject(message);

			return view;

		} else {
			return userService.indexRedirectMV(model);
		}
	}

	@PostMapping(value = "/updatestudent")
	public ModelAndView updateStudent(HttpServletRequest request, @ModelAttribute("student") StudentDTO student,
			Model model) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			Boolean response = service.updateStudent(student);
			ModelAndView view = new ModelAndView("message");
			Message message = new Message();
			model.addAttribute("user", session.getAttribute("user"));
			if (response)
				message.setInfo("Student updated successfully !! :)");

			view.addObject(message);

			return view;

		} else {
			return userService.indexRedirectMV(model);
		}
	}

	@GetMapping("/updatestudent/{id}")
	public ModelAndView showEditStudentPage(HttpServletRequest request, @PathVariable(name = "id") Long id,
			Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			ModelAndView mav = new ModelAndView("updatestudent");
			Student student = service.get(id);
			StudentDTO studentDto = service.studentEntityToDto(student);
			List<Batch> batches = batchService.listAll();
			model.addAttribute("listbatches", batches);
			mav.addObject("student", studentDto);
			mav.addObject("user", session.getAttribute("user"));
			return mav;
		} else {
			return userService.indexRedirectMV(model);
		}
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteStudent(HttpServletRequest request, @PathVariable(name = "id") Long id, Model model) {
		
		
		HttpSession session = request.getSession(false);

		if (session != null) {
			service.delete(id);
			ModelAndView view = new ModelAndView("message");
			Message message = new Message();
			model.addAttribute("user", session.getAttribute("user"));
			message.setInfo("Student deleted successfully !! :)");
			view.addObject(message);

			return view;

		} else {
			return userService.indexRedirectMV(model);
		}
	}
}
