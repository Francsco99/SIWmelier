package it.uniroma3.siw.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.uniroma3.siw.spring.model.Catalogo;
import it.uniroma3.siw.spring.service.CatalogoService;

@Component
public class CatalogoValidator implements Validator{
	
	@Autowired
	private CatalogoService catalogoService;
	
	private static final Logger logger = LoggerFactory.getLogger(CatalogoValidator.class);
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione","required");
		
		if(!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if(this.catalogoService.alreadyExists((Catalogo)target)){
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Catalogo.class.equals(clazz);
	}

}
