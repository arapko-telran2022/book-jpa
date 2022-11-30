package telran.java2022.book.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 8074111631736819908L;
	
	public AuthorNotFoundException() {
		super("Person not found");

	}
	
	public AuthorNotFoundException(String name) {
		super("Author with name = " + name + " not found");

	}
	
}
