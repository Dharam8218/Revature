package com.revplay;

import com.revplay.controller.UserController;
import com.revplay.model.User;
import com.revplay.service.UserService;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private InputStream originalIn;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testRegister() throws Exception {

        String input =
                "Dharamveer\n" +
                        "dharam@gmail.com\n" +
                        "12345\n" +
                        "Blue\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        UserController controller = new UserController();
        UserController.sc = new java.util.Scanner(System.in);

        // Stub service (NO DB)
        controller.userService = new UserService() {
            @Override
            public String register(User user) {
                return "Registration successful";
            }
        };

        controller.register();

        String output = out.toString();
        assertTrue(output.contains("Registration"));
    }

    @Test
    void testLogin() throws Exception {

        String input =
                "dharam@gmail.com\n" +
                        "dharam@123\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        UserController controller = new UserController();
        UserController.sc = new java.util.Scanner(System.in);

        controller.userService = new UserService() {
            @Override
            public User login(String email, String password) {
                return new User("Dharam", email, password, "Blue");
            }
        };

        User user = controller.login();
        assertNotNull(user);
        assertEquals("dharam@gmail.com", user.getEmail());
    }
}
