package com.erickharada.bookstoremanager.service;

import com.erickharada.bookstoremanager.dto.MessageResponseDTO;
import com.erickharada.bookstoremanager.entity.Book;
import com.erickharada.bookstoremanager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public MessageResponseDTO create(Book book){
        Book savedBook = bookRepository.save(book);

        return MessageResponseDTO.builder()
                .message("Book Created with ID "+ savedBook.getId())
                .build();
    }
}
