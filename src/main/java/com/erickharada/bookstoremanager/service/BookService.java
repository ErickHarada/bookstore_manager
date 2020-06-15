package com.erickharada.bookstoremanager.service;

import com.erickharada.bookstoremanager.dto.AuthorDTO;
import com.erickharada.bookstoremanager.dto.BookDTO;
import com.erickharada.bookstoremanager.dto.MessageResponseDTO;
import com.erickharada.bookstoremanager.entity.Author;
import com.erickharada.bookstoremanager.entity.Book;
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

    public BookDTO findById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Author author = Author.builder()
                .id(optionalBook.get().getAuthor().getId())
                .name(optionalBook.get().getAuthor().getName())
                .age(optionalBook.get().getAuthor().getAge())
                .build();

        BookDTO bookDTO = BookDTO.builder()
                .id(optionalBook.get().getId())
                .name(optionalBook.get().getName())
                .pages(optionalBook.get().getPages())
                .chapters(optionalBook.get().getChapters())
                .isbn(optionalBook.get().getIsbn())
                .publisherName(optionalBook.get().getPublisherName())
                .author(author)
                .build();

        return bookDTO;
    }
}
