package telran.java2022.book.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java2022.book.dao.AuthorRepository;
import telran.java2022.book.dao.BookRepository;
import telran.java2022.book.dao.PublisherRepository;
import telran.java2022.book.dto.AuthorDto;
import telran.java2022.book.dto.BookDto;
import telran.java2022.book.dto.exceptions.AuthorNotFoundException;
import telran.java2022.book.dto.exceptions.BookNotFoundException;
import telran.java2022.book.dto.exceptions.PublisherNotFoundException;
import telran.java2022.book.model.Author;
import telran.java2022.book.model.Book;
import telran.java2022.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	final BookRepository bookRepository;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public Boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}

		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));

		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());

		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(()-> new BookNotFoundException(isbn));
		return BookToBookDto(book);
	}

	@Override
	@Transactional
	public BookDto removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(()-> new BookNotFoundException(isbn));
		bookRepository.delete(book);
		return BookToBookDto(book);
	}

	@Override
	@Transactional
	public BookDto updateBook(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(()-> new BookNotFoundException(isbn));
		book.setTitle(title);
		return BookToBookDto(book);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<BookDto> findBooksByAuthor(String authorName) {
		Author author = authorRepository.findById(authorName)
				.orElseThrow(() -> new AuthorNotFoundException(authorName));
		return bookRepository.findByAuthorsName(authorName).map(a -> BookToBookDto(a)).collect(Collectors.toSet());
//		return author.getBooks().stream().map(a -> BookToBookDto(a)).collect(Collectors.toSet());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<BookDto> findBookByPublisher(String publisherName) {
		if (!publisherRepository.existsById(publisherName)) {
			throw new PublisherNotFoundException(publisherName);
		}

		return bookRepository.findByPublisherPublisherName(publisherName).map(a -> BookToBookDto(a))
				.collect(Collectors.toSet());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<AuthorDto> findBookAuthors(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(()-> new BookNotFoundException(isbn));
		return book.getAuthors().stream().map(a -> modelMapper.map(a, AuthorDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<String> findPublishersByAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElseThrow(AuthorNotFoundException::new);
		return publisherRepository.findPublishersByAuthor(authorName);
//		return author.getBooks().stream().map(b -> b.getPublisher().getPublisherName()).collect(Collectors.toSet());
	}

	@Override
	@Transactional
	public AuthorDto removeAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElseThrow(()-> new AuthorNotFoundException(authorName));
		bookRepository.deleteAll(author.getBooks());
		authorRepository.delete(author);
		return modelMapper.map(author, AuthorDto.class);
	}
	
	private BookDto BookToBookDto(Book book){
		BookDto bookDto = modelMapper.map(book, BookDto.class);
		bookDto.setPublisher(book.getPublisher().getPublisherName());
		return bookDto;
	}

}
