package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.PiattoService;

@Component
public class PiattoValidator implements Validator{
	
	@Autowired
	private PiattoService catalogoService;
	
	private static final Logger logger = LoggerFactory.getLogger(PiattoValidator.class);
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome","required");
		
		
		if(!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if(this.catalogoService.alreadyExists((Piatto)target)){
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Piatto.class.equals(clazz);
	}

}
