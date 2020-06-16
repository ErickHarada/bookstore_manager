package com.erickharada.bookstoremanager.service;

import com.erickharada.bookstoremanager.dto.AuthorDTO;
import com.erickharada.bookstoremanager.dto.BookDTO;
import com.erickharada.bookstoremanager.dto.MessageResponseDTO;
import com.erickharada.bookstoremanager.entity.Author;
import com.erickharada.bookstoremanager.entity.Book;
import com.erickharada.bookstoremanager.exception.BookNotFoundException;
import com.erickharada.bookstoremanager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public MessageResponseDTO create(BookDTO bookDTO){
        Book bookToSave = Book.builder()
                .name(bookDTO.getName())
                .pages(bookDTO.getPages())
                .chapters(bookDTO.getChapters())
                .isbn(bookDTO.getIsbn())
                .publisherName(bookDTO.getPublisherName())
                .author(bookDTO.getAuthor())
                .build();
        Book savedBook = bookRepository.save(bookToSave);

        return MessageResponseDTO.builder()
                .message("Book Created with ID "+ savedBook.getId())
                .build();
    }

    public BookDTO findById(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        Author author = Author.builder()
                .id(book.getAuthor().getId())
                .name(book.getAuthor().getName())
                .age(book.getAuthor().getAge())
                .build();

        BookDTO bookDTO = BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .pages(book.getPages())
                .chapters(book.getChapters())
                .isbn(book.getIsbn())
                .publisherName(book.getPublisherName())
                .author(author)
                .build();

        return bookDTO;
    }
}
