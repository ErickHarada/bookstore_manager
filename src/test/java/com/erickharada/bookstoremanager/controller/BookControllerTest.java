package com.erickharada.bookstoremanager.controller;

import com.erickharada.bookstoremanager.dto.BookDTO;
import com.erickharada.bookstoremanager.dto.MessageResponseDTO;
import com.erickharada.bookstoremanager.entity.Author;
import com.erickharada.bookstoremanager.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.expression.Format;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.json.JsonParser;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2XmlView())
                .build();
    }

    @Test
    void testWhenPostIsCalledThenABookShouldBeCreated() throws Exception {
        Author author = Author.builder()
                .name("Benjamin Graham")
                .age(100)
                .build();
        BookDTO fakeBook = BookDTO.builder()
                .id(1L)
                .name("Investidor Inteligente")
                .pages(200)
                .chapters(20)
                .isbn("0-596-52068-9")
                .publisherName("Harper Collins")
                .author(author)
                .build();
        MessageResponseDTO messageResponseDTO = MessageResponseDTO.builder()
                .message("Book created with ID " + fakeBook.getId())
                .build();

        Mockito.when(bookService.create(fakeBook)).thenReturn(messageResponseDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(fakeBook).toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Is.is(messageResponseDTO.getMessage())));
    }
}
