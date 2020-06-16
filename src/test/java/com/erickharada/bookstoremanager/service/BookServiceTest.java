package com.erickharada.bookstoremanager.service;

import com.erickharada.bookstoremanager.dto.BookDTO;
import com.erickharada.bookstoremanager.entity.Author;
import com.erickharada.bookstoremanager.entity.Book;
import com.erickharada.bookstoremanager.exception.BookNotFoundException;
import com.erickharada.bookstoremanager.repository.BookRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenGiveExistingIdThenReturnBook() throws BookNotFoundException {
        Author author = Author.builder()
                .name("Benjamin Graham")
                .age(100)
                .build();
        Book fakeBook = Book.builder()
                .id(1L)
                .name("Investidor Inteligente")
                .pages(200)
                .chapters(20)
                .isbn("0-596-52068-9")
                .publisherName("Harper Collins")
                .author(author)
                .build();

        Mockito.when(bookRepository.findById(fakeBook.getId())).thenReturn(Optional.of(fakeBook));

        BookDTO bookDTO = bookService.findById(fakeBook.getId());

        Assertions.assertEquals(fakeBook.getName(), bookDTO.getName());
        Assertions.assertEquals(fakeBook.getIsbn(), bookDTO.getIsbn());
        Assertions.assertEquals(fakeBook.getPublisherName(), bookDTO.getPublisherName());
    }

    @Test
    void whenGiveUnexistingIdThenNotFoundThrowAnException() {
        long invalidId = 10L;

        Mockito.when(bookRepository.findById(invalidId)).thenReturn(Optional.empty());

        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.findById(invalidId));
    }
}
