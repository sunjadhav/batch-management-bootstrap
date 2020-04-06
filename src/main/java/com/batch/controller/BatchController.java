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
import com.batch.dto.BatchSearchDTO;
import com.batch.dto.Message;
import com.batch.model.Batch;
import com.batch.service.BatchService;
import com.batch.service.UserService;

@Controller
public class BatchController {

	@Autowired
	private BatchService service;
	@Autowired
	private UserService userService;

	@GetMapping("/addbatch")
	public String showNewBatchPage(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Batch batch = new Batch();
			model.addAttribute("batch", batch);
			model.addAttribute("user", session.getAttribute("user"));
			return "addbatch";
		} else {
			return userService.indexRedirect(model);
		}

	}

	@PostMapping(value = "/addbatch")
	public ModelAndView saveBatch(HttpServletRequest request, @ModelAttribute("batch") BatchDTO batch,
			Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Boolean response = service.save(batch);

			ModelAndView view = new ModelAndView("message");
			Message message = new Message();
			
			model.addAttribute("user", session.getAttribute("user"));
			if (response)
				message.setInfo("Batch added successfully !! :)");
			else
				message.setInfo("Duplicate Batch Id OR Batch Name, Please Verify Again :( ");
			view.addObject(message);

			return view;

		} else {
			return userService.indexRedirectMV(model);
		}
	}

	@PostMapping(value = "/updatebatch")
	public ModelAndView updateBatch(HttpServletRequest request, @ModelAttribute("batch") BatchDTO batch,
			Model model) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			Boolean response = service.updateBatch(batch);
			ModelAndView view = new ModelAndView("message");
			Message message = new Message();
			model.addAttribute("user", session.getAttribute("user"));
			if (response)
				message.setInfo("batch updated successfully !! :)");

			view.addObject(message);

			return view;

		} else {
			return userService.indexRedirectMV(model);
		}
	}

	@GetMapping("/updatebatch/{id}")
	public ModelAndView showEditbatchPage(HttpServletRequest request, @PathVariable(name = "id") Long id,
			Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			ModelAndView mav = new ModelAndView("updatebatch");
			Batch batch = service.get(id);
			BatchDTO batchDto = service.batchEntityToDto(batch);
			mav.addObject("batch", batchDto);
			mav.addObject("user", session.getAttribute("user"));
			return mav;
		} else {
			return userService.indexRedirectMV(model);
		}
	}

	@GetMapping("/batch/archive/{id}")
	public ModelAndView archiveBatch(HttpServletRequest request, @PathVariable(name = "id") Long id, Model model) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			service.archive(id);
			ModelAndView view = new ModelAndView("message");
			Message message = new Message();
			model.addAttribute("user", session.getAttribute("user"));
			message.setInfo("Batch archived successfully !! :)");
			view.addObject(message);

			return view;

		} else {
			return userService.indexRedirectMV(model);
		}
	}
	@GetMapping("/batch/delete/{id}")
	public ModelAndView deleteBatch(HttpServletRequest request, @PathVariable(name = "id") Long id, Model model) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			service.delete(id);
			ModelAndView view = new ModelAndView("message");
			Message message = new Message();
			model.addAttribute("user", session.getAttribute("user"));
			message.setInfo("Batch deleted successfully !! :)");
			view.addObject(message);

			return view;

		} else {
			return userService.indexRedirectMV(model);
		}
	}
	@GetMapping("/undobatch/{id}")
	public ModelAndView undoBatch(HttpServletRequest request, @PathVariable(name = "id") Long id, Model model) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			service.undo(id);
			ModelAndView view = new ModelAndView("message");
			Message message = new Message();
			model.addAttribute("user", session.getAttribute("user"));
			message.setInfo("Batch restored successfully !! :)");
			view.addObject(message);

			return view;

		} else {
			return userService.indexRedirectMV(model);
		}
	}
	@GetMapping("/viewbatchlist")
	public String viewbatchPage(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			BatchSearchDTO batchSearchDTO = new BatchSearchDTO();
			batchSearchDTO.setNoRecord("No records found");
			model.addAttribute("batchSearchDTO", batchSearchDTO);
			model.addAttribute("user", session.getAttribute("user"));
			return "viewbatchlist";

		} else {
			return userService.indexRedirect(model);
		}
	}

	@PostMapping(value = "/viewbatchlist")
	public String search(HttpServletRequest request, @ModelAttribute("batchSearchDTO") BatchSearchDTO batchSearchDTO,
			Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			List<BatchDTO> listbatches = service.search(batchSearchDTO);
			if (listbatches.isEmpty()) {
				batchSearchDTO.setNoRecord("No records found");
			}
			model.addAttribute("listbatches", listbatches);
			model.addAttribute("user", session.getAttribute("user"));
			return "viewbatchlist";
		} else {
			return userService.indexRedirect(model);
		}
	}
	
	@GetMapping(value = "/activebatches")
	public String activeBatches(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			List<BatchDTO> listbatches = service.getActiveBatches();
			if (listbatches.isEmpty()) {
				model.addAttribute("noRecords", "No active batches available!");
			}
			model.addAttribute("activeBatches", listbatches);
			model.addAttribute("user", session.getAttribute("user"));
			return "activebatches";
		} else {
			return userService.indexRedirect(model);
		}
	}
	
	@GetMapping(value = "/archivedbatches")
	public String archivedBatches(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			List<BatchDTO> listbatches = service.getArchivedBatches();
			if (listbatches.isEmpty()) {
				model.addAttribute("noRecords", "No archived batches available!");
			}
			model.addAttribute("archivedBatches", listbatches);
			model.addAttribute("user", session.getAttribute("user"));
			return "archivedbatches";
		} else {
			return userService.indexRedirect(model);
		}
	}

}
