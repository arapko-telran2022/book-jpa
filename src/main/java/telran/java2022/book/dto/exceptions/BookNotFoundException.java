package telran.java2022.book.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -7963954154897706599L;
	
	public BookNotFoundException() {
		super("Book not found");

	}
	
	public BookNotFoundException(String isbn) {
		super("Book with isbn = " + isbn + " not found");

	}

}
