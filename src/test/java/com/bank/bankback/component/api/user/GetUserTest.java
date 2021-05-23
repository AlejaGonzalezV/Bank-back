package com.bank.bankback.component.api.user;

import com.bank.bankback.dao.UserDao;
import com.bank.bankback.dto.UserDto;
import com.bank.bankback.model.User;
import com.bank.bankback.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties= {"spring.config.aditional-location=classpath:component-test.yml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class GetUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        var user = new UserDao(123456789L, "Putin", "Rusia123", false);
        userRepository.save(user);
    }

    @Test
    @SneakyThrows
    public void userDetailWithSuccessStatusCodeAndContentType() {
        var response = mockMvc.perform(get("/users/123456789")).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(response.getContentType(), equalTo(MediaType.APPLICATION_JSON.toString()));

    }

    @Test
    @SneakyThrows
    public void detailWithTheRightUser() {
        var response = mockMvc.perform(get("/users/123456789")).andReturn().getResponse();
        var user = new ObjectMapper().readValue(response.getContentAsString(), User.class);

        assertThat(user.getDocument(), equalTo(123456789L));
        assertThat(user.getName(), equalTo("Putin"));
        assertThat(user.getUsername(), equalTo( "Rusia123"));
        assertThat(user.isActive(), equalTo(false));
    }

}
