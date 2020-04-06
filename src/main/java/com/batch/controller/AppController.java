package com.batch.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.batch.dto.Message;
import com.batch.dto.UserDTO;
import com.batch.model.User;
import com.batch.service.UserService;
import com.batch.utils.Constants;

@Controller
public class AppController {

	@Autowired
	UserService userService;
	HttpSession session = null;

	@GetMapping("")
	public String loginPage(Model model) {
		UserDTO user = new UserDTO();
		Message message = new Message();
		model.addAttribute("user", user);
		model.addAttribute("message", message);
		return Constants.LOGIN;
	}

	@GetMapping("/plain-page")
	public String plain(Model model) {
		return "plain-page";
	}
	
	@GetMapping("/pricing-tables")
	public String pricing(Model model) {
		return "pricing-tables";
	}
	
	@GetMapping("/home")
	public String homePage(HttpServletRequest request, Model model) {
		HttpSession s = request.getSession(false);
		if (s != null) {
			model.addAttribute("user", s.getAttribute("user"));
			return "home";
		} else {
			return userService.indexRedirect(model);
		}
	}

	@GetMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request, @ModelAttribute("user") UserDTO user, Model model) {
		ModelAndView view = null;
		Message message = new Message();
		view = new ModelAndView(Constants.LOGIN);
		view.addObject(message);
		return view;
	}

	@PostMapping(value = "/login")
	public ModelAndView loginUser(HttpServletRequest request, @ModelAttribute("user") UserDTO user, Model model) {
		User loginUser = userService.get(user.getUserName(), user.getPassword());
		ModelAndView view = new ModelAndView(Constants.LOGIN);
		
		if (session == null) {

			user.setError(Constants.LOGIN_FAILED);
		}

		if (loginUser != null) {
			view = new ModelAndView("redirect:activebatches");
			session = request.getSession();
			
			session.setAttribute("user", user.getUserName());

		} else {
			user.setError("Invalid Username or Password ");
			view.addObject("user", user);
		}

		return view;
	}

	@GetMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, Model model) {
		session = request.getSession(false);
		ModelAndView view = new ModelAndView(Constants.LOGIN);
		UserDTO user = new UserDTO();
		user.setError("User Logout !! :( ");
		if (session == null) {
			user.setError(Constants.LOGIN_FAILED);
		} else {
			session.invalidate();
		}
		view.addObject("user", user);
		return view;
	}
	
}
