package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.workshops.bookshelf.JsonTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(BookRestController.class)
@Import({BookService.class, BookRepository.class, JsonTestConfiguration.class})
//@Import({BookService.class, BookRepository.class})
class BookRestControllerMockMvcTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getAllBooks() throws Exception {
    var mvcResult = mockMvc.perform(get("/book"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    var payload = mvcResult.getResponse().getContentAsString();

    List<Book> books = objectMapper.readValue(payload, new TypeReference<>() {});
    assertThat(books).hasSize(3);
  }

  @Test
  void expectBadRequest() throws Exception {
    mockMvc.perform(get("/book/123456789123456789"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

//  @TestConfiguration
//  static class TestConfig {
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
//      return builder -> builder.featuresToEnable(SerializationFeature.INDENT_OUTPUT);
//    }
//  }
}