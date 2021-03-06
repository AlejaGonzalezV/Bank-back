package com.bank.bankback.component.api.user;

import com.bank.bankback.dao.UserDao;
import com.bank.bankback.repository.UserRepository;
import lombok.SneakyThrows;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.config.additional-location=classpath:component-test.yml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ListUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        var user = new UserDao(123123L, "Franco Reyes", "franco123", true);
        userRepository.save(user);
    }

    @Test
    @SneakyThrows
    public void listUsersSuccessfully() {
        var response = mockMvc.perform(get("/users")).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(response.getContentType(), equalTo(MediaType.APPLICATION_JSON.toString()));
    }

    @Test
    @SneakyThrows
    public void listUsersWithRightSchema() {
        var response = mockMvc.perform(get("/users")).andReturn().getResponse();

        var jsonSchema = new JSONObject(new JSONTokener(ListUserTest.class.getResourceAsStream("/users.json")));
        var jsonArray = new JSONArray(response.getContentAsString());

        System.out.println("-------------------------------");
        System.out.println(jsonSchema);
        System.out.println("-------------------------------");

        var schema = SchemaLoader.load(jsonSchema);
        schema.validate(jsonArray);

    }

}
