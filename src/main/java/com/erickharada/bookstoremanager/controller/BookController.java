package com.erickharada.bookstoremanager.controller;

import com.erickharada.bookstoremanager.dto.MessageResponseDTO;
import com.erickharada.bookstoremanager.entity.Book;
import com.erickharada.bookstoremanager.repository.BookRepository;
import com.erickharada.bookstoremanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public MessageResponseDTO create(@RequestBody Book book){
        return bookService.create(book);
    }
}
