package com.revplay;

import com.revplay.controller.UserController;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
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

        UserController userController = new UserController();
        userController.register();

        String output = out.toString();

        assertTrue(output.contains("Registration"));
    }

}
