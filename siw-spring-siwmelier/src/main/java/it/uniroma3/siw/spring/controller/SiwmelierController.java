package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.service.SiwmelierService;
import it.uniroma3.siw.spring.validator.SiwmelierValidator;

@Controller
public class SiwmelierController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
