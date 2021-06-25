package it.uniroma3.siw.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.uniroma3.siw.spring.model.Vino;
import it.uniroma3.siw.spring.service.VinoService;

@Component
public class VinoValidator implements Validator{
	
	@Autowired
	private VinoService vinoService;
	
	private static final Logger logger = LoggerFactory.getLogger(VinoValidator.class);
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "voto","required");
		
		
		if(!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if(this.vinoService.alreadyExists((Vino)target)){
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Vino.class.equals(clazz);
	}

}