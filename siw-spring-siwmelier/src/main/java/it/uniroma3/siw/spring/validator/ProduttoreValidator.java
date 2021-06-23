package it.uniroma3.siw.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Produttore;
import it.uniroma3.siw.spring.service.ProduttoreService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class ProduttoreValidator implements Validator{
	
	@Autowired
	private ProduttoreService produttoreService;
	
	private static final Logger logger = LoggerFactory.getLogger(ProduttoreValidator.class);
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome","required");
		
		
		if(!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if(this.produttoreService.alreadyExists((Produttore)target)){
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Produttore.class.equals(clazz);
	}

}