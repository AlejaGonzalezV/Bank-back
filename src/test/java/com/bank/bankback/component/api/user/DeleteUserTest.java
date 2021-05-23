package com.bank.bankback.component.api.user;

import com.bank.bankback.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.config.additional-location=classpath:component-test.yml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class DeleteUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    @SneakyThrows
    public void deleteUserSuccessful() {

        var user = new DeleteUserTest.CreateUserRequestBody();
        user.setDocument(123123L);
        user.setName("Armando Mendoza");
        user.setUsername("armando123");
        user.setActive(true);

        var createAnimalRequestBody = new ObjectMapper().writeValueAsString(user);
        //Action Request
        var response = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createAnimalRequestBody))
                .andReturn()
                .getResponse();

        var userResponse = new ObjectMapper().readValue(response.getContentAsString(), DeleteUserTest.CreateUserResponse.class);

        //Assert Http Response
        assertThat(userResponse.getDocument(), equalTo(123123L));
        assertThat(userResponse.getName(), equalTo("Armando Mendoza"));
        assertThat(userResponse.getUsername(), equalTo( "armando123"));
        assertThat(userResponse.isActive(), equalTo(true));
        assertThat(userResponse.getId(), notNullValue());

        //Database Asserts
        var dbQuery = userRepository.findById(userResponse.getId());
        assertThat(dbQuery.isPresent(), is(true));

        var userDB = dbQuery.get();
        assertThat(userDB.getDocument(), equalTo(123123L));
        assertThat(userDB.getName(), equalTo("Armando Mendoza"));
        assertThat(userDB.getUsername(), equalTo( "armando123"));
        assertThat(userDB.isActive(), equalTo(true));

        var response2 =  this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}",123123L)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        dbQuery = userRepository.findById(userResponse.getId());
        assertThat(dbQuery.isPresent(), is(false));

    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class CreateUserResponse {
        private Long id;
        private Long document;
        private String name;
        private String username;
        private boolean active;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class CreateUserRequestBody {
        private Long id;
        private Long document;
        private String name;
        private String username;
        private boolean active;

    }
}
