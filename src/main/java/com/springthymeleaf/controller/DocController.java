package com.springthymeleaf.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springthymeleaf.model.DocDetails;
import com.springthymeleaf.service.NoteService;

@Controller
public class DocController {


	@Autowired
	NoteService noteService;
	
	
	@RequestMapping("/insertdetails")
	public String login(ModelMap modelMap) {
		modelMap.put("details",new DocDetails());
		return "insertdetails";
	}
	
	@RequestMapping(value="/savedetails",method = RequestMethod.POST)
	public String saveDetails(DocDetails docdetails) {
		ModelMap modelMap = new ModelMap();
		if(docdetails == null) {
			modelMap.put("docdetails", docdetails);
			return "insertdetails";
		}
		noteService.saveDetails(docdetails);
		modelMap.put("docdetails", docdetails);
		return "redirect:/docdetails";
	}
	
	@RequestMapping(value="/docdetails")
	public ModelAndView docDetails() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("docDetails");
		List<DocDetails> allDoc = noteService.getAllDoc();
		modelAndView.addObject("allDoc", allDoc);
		return modelAndView;
	}
	
	@RequestMapping(value="/viewTax/{id}", method = RequestMethod.GET)
	public ModelAndView viewTax(@PathVariable("id") int id ,HttpServletRequest request) {
		
		DocDetails docDetails =  new DocDetails();
		docDetails.setDocId(id);
		DocDetails taxDetailsById = noteService.getDocDetails(docDetails.getDocId());
		ModelAndView modelAndView = new ModelAndView();
	
		modelAndView.addObject("docName", taxDetailsById.getDocName());
		modelAndView.addObject("docType", taxDetailsById.getDocType());
		modelAndView.addObject("docAmount", taxDetailsById.getDocAmount());
		modelAndView.addObject("tax", taxDetailsById.getTax());
		modelAndView.addObject("taxType", taxDetailsById.getTaxType());
		
		modelAndView.setViewName("viewTaxDetails");
		return modelAndView;
	}
}
