package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.exception.PlaceholderMissedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TemplateEngineTest {

    Template template;

    Client client;

    TemplateEngine templateEngine;

    @BeforeEach
    public void setUp() {
        template = new Template();
        template.setFrom("from@mail.com");
        template.setSubject("Greeting mail!");
        template.setContent("Hello #{firstName} #{lastName}\nGreeting from #{from}");
        Map<String, Object> mailModel = new HashMap<>();
        mailModel.put("#{firstName}", "John");
        mailModel.put("#{lastName}", "Doe");
        template.setModel(mailModel);

        client = new Client();
        client.setAddresses("to@mail.com");

        templateEngine = new TemplateEngine();
    }


    @Test
    void shouldGenerateMessage() {
        template.getModel().put("#{from}", "from@mail.com");

        String expectedResult = "From: " + "from@mail.com\n" +
                "To: " + "to@mail.com\n" +
                "Subject: " + "Greeting mail!\n" +
                "Hello John Doe\nGreeting from from@mail.com";

        String result = templateEngine.generateMessage(template, client);
        assertEquals(expectedResult, result);
    }

    @Test
    void shouldThrowExceptionWhenPlaceholderNotProvided() {
        assertThrows(PlaceholderMissedException.class, () -> templateEngine.generateMessage(template, client));
    }
}
