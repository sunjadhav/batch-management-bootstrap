package com.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.batch.dto.UserDTO;
import com.batch.model.User;
import com.batch.repository.UserRepository;
import com.batch.utils.Constants;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public User  get(String userName, String password) {
		return repo.findByUserNameAndPassword(userName,password);
	}
	
	public String indexRedirect(Model model)
	{
		UserDTO user = new UserDTO();
		user.setError(Constants.LOGIN_FAILED);
		model.addAttribute("user", user);
		
		return Constants.LOGIN;
	}
	
	public ModelAndView indexRedirectMV(Model model)
	{
		ModelAndView mav = new ModelAndView(Constants.LOGIN);
		UserDTO user = new UserDTO();
		user.setError(Constants.LOGIN_FAILED);
		mav.addObject("user", user);
		
		return mav;
	}
	
}
