package telran.java2022.book.service;

import telran.java2022.book.dto.AuthorDto;
import telran.java2022.book.dto.BookDto;

public interface BookService {
	
	Boolean addBook(BookDto bookDto);

	BookDto findBookByIsbn(String isbn);

	BookDto removeBook(String isbn);

	BookDto updateBook(String isbn, String title);

	Iterable<BookDto> findBooksByAuthor(String authorName);

	Iterable<BookDto> findBookByPublisher(String publisherName);

	Iterable<AuthorDto> findBookAuthors(String isbn);
	
	Iterable<String> findPublishersByAuthor(String authorName);
	
	AuthorDto removeAuthor(String authorName);
}
