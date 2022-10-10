package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.exception.PlaceholderMissedException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TemplateEngineTest {


    @Test
    void shouldGenerateMessage() {
        Template template = new Template();
        template.setFrom("from@mail.com");
        template.setSubject("Greeting mail!");
        template.setContent("Hello ${firstName} ${lastName}\nGreeting from ${from}");
        Map<String, Object> mailModel = new HashMap<>();
        mailModel.put("${firstName}", "John");
        mailModel.put("${lastName}", "Doe");
        mailModel.put("${from}", "from@mail.com");
        template.setModel(mailModel);
        Client client = new Client();
        client.setAddresses("to@mail.com");

        String expectedResult = "From: " + "from@mail.com\n" +
                "To: " + "to@mail.com\n" +
                "Subject: " + "Greeting mail!\n" +
                "Hello John Doe\nGreeting from from@mail.com";
        TemplateEngine engineTest = new TemplateEngine();
        String result = engineTest.generateMessage(template, client);
        assertEquals(expectedResult, result);
    }

    @Test
    void shouldThrowExceptionWhenPlaceholderNotProvided() {
        Template template = new Template();
        template.setFrom("from@mail.com");
        template.setSubject("Greeting mail!");
        template.setContent("Hello ${firstName} ${lastName}\nGreeting from ${from}");
        Map<String, Object> mailModel = new HashMap<>();
        mailModel.put("${firstName}", "John");
        mailModel.put("${lastName}", "Doe");
        template.setModel(mailModel);
        Client client = new Client();
        client.setAddresses("to@mail.com");

        TemplateEngine engineTest = new TemplateEngine();
        assertThrows(PlaceholderMissedException.class, () -> engineTest.generateMessage(template, client));
    }

}
