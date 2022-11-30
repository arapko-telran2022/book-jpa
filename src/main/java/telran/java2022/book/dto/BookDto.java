package telran.java2022.book.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.java2022.book.model.Book;
import telran.java2022.book.model.Publisher;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
	String isbn;
	String title;
	Set<AuthorDto> authors;
	String publisher;
}
