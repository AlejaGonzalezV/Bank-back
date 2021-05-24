package com.bank.bankback.contract.api.provider;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import com.bank.bankback.controller.UserController;
import com.bank.bankback.model.User;
import com.bank.bankback.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@PactBroker(authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}"), url = "${PACT_BROKER_URL}")
@Provider("BankBack")
@ExtendWith(MockitoExtension.class)
public class UserTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactVerificationTestTemplate(PactVerificationContext context){
        context.verifyInteraction();
    }

    @BeforeEach
    public void changeContext(PactVerificationContext context){
        MockMvcTestTarget testTarget = new MockMvcTestTarget();
        testTarget.setControllers(userController);
        context.setTarget(testTarget);
    }

    @State("has users")
    public void listUsers(){
        User user = new User();
        user.setName("Jasper");
        user.setUsername("Jas123");
        user.setActive(true);
        user.setDocument(65656);

        ArrayList<User> users = new ArrayList<User>();
        users.add(user);

        Mockito.when(userService.getAll()).thenReturn(users);

    }

    @State("document user")
    public void deleteUser() throws Exception {

        Long iddocument = 1007554028L;

        Mockito.doAnswer((i) -> {
            Assertions.assertEquals(iddocument, i.getArgument(0));
            return null;
        }).when(userService).delete(iddocument);
    }

    @State("new user")
    public void saveUser() throws Exception {
        User user = new User();
        user.setName("Jasper");
        user.setUsername("Jas123");
        user.setActive(true);
        user.setDocument(65656);

        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);
    }

    @State("edit user")
    public void editUser() throws Exception {
        User user = new User();
        user.setName("Jasper");
        user.setUsername("Jas123");
        user.setActive(true);
        user.setId(80);
        user.setDocument(1111);

        Mockito.when(userService.replace(Mockito.eq(user.getDocument()), Mockito.any(User.class))).thenReturn(user);
    }
}
