package telran.java2022.book.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PublisherNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -255960429937890276L;
	
	public PublisherNotFoundException() {
		super("Publisher not found");

	}
	
	public PublisherNotFoundException(String name) {
		super("Publisher with name = " + name + " not found");

	}
	
}
