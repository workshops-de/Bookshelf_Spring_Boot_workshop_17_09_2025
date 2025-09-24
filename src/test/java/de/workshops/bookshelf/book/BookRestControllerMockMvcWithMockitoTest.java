package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.workshops.bookshelf.MethodSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
@Import(MethodSecurityConfiguration.class)
class BookRestControllerMockMvcWithMockitoTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  BookService bookService;



  @Captor
  ArgumentCaptor<String> isbnCaptor;

  @Test
  @WithMockUser
  void getAllBooks() throws Exception {
    when(bookService.getAllBooks()).thenReturn(List.of(new Book(), new Book(), new Book()));

    var mvcResult = mockMvc.perform(get("/book"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    var payload = mvcResult.getResponse().getContentAsString();

    List<Book> books = objectMapper.readValue(payload, new TypeReference<>() {
    });
    assertThat(books).hasSize(3);
  }

  @Test
  @WithMockUser
  void expectBadRequest() throws Exception {
    mockMvc.perform(get("/book/123456789123456789"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser
  void getBookByIsbnForExistingIsbn() throws Exception {
    when(bookService.getBookByIsbn(isbnCaptor.capture())).thenReturn(new Book());

    mockMvc.perform(get("/book/123456789"))
        .andDo(print())
        .andExpect(status().isOk());

    assertThat(isbnCaptor.getValue()).isEqualTo("123456789");
  }

  @Test
  @WithMockUser
  void getBookByIsbnForNonExistingIsbn() throws Exception {
    doThrow(BookException.class).when(bookService).getBookByIsbn(anyString());

    mockMvc.perform(get("/book/123456789"))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void createBook_AllowedRole() throws Exception {
    var isbn = "123456789";
    var title = "The Hobbit";
    var author = "Tolkien";
    var description = "A fantasy book";

    mockMvc.perform(post("/book")
            .with(csrf())
            .content("""
                {
                    "isbn": "%s",
                    "title": "%s",
                    "author": "%s",
                    "description": "%s"
                }""".formatted(isbn, title, author, description))
            .contentType(APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  @WithMockUser
  void createBook_ForbiddenRole() throws Exception {
    var isbn = "123456789";
    var title = "The Hobbit";
    var author = "Tolkien";
    var description = "A fantasy book";

    mockMvc.perform(post("/book")
            .with(csrf())
            .content("""
                {
                    "isbn": "%s",
                    "title": "%s",
                    "author": "%s",
                    "description": "%s"
                }""".formatted(isbn, title, author, description))
            .contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }
}