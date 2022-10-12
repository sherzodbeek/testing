package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.exception.PlaceholderMissedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

    // Parameterized
    @ParameterizedTest
    @MethodSource("provideFromPartOfMail")
    void shouldGenerateMessageParametrizedTest(String from, String expected) {
        template.getModel().put("#{from}", from);
        String result = templateEngine.generateMessage(template, client);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideFromPartOfMail() {
        String expectedResult1 = "From: " + "from@mail.com\n" +
                "To: " + "to@mail.com\n" +
                "Subject: " + "Greeting mail!\n" +
                "Hello John Doe\nGreeting from from@mail.com";

        String expectedResult2 = "From: " + "from@mail.com\n" +
                "To: " + "to@mail.com\n" +
                "Subject: " + "Greeting mail!\n" +
                "Hello John Doe\nGreeting from secondFrom@mail.com";

        return Stream.of(
                Arguments.of("from@mail.com", expectedResult1),
                Arguments.of("secondFrom@mail.com", expectedResult2)
        );
    }

    // Dynamic test
    @TestFactory
    Stream<DynamicTest> dynamicTestsShouldGenerateMessage() {
        String resultTemplate = "From: " + "from@mail.com\n" +
                "To: " + "to@mail.com\n" +
                "Subject: " + "Greeting mail!\n" +
                "Hello John Doe\nGreeting from update@mail.com";

        List<String> inputList = Arrays.asList("from@mail.com", "secondFrom@mail.com");

        return inputList.stream().map(mail -> DynamicTest.dynamicTest(
                "Update template",
                () -> {
                    String expectedResult = resultTemplate.replaceAll("update@mail\\.com", mail);
                    template.getModel().put("#{from}", mail);
                    assertEquals(expectedResult, templateEngine.generateMessage(template, client));
                }));
    }

    // Conditional test
    @Test
    @DisabledOnJre(JRE.JAVA_11)
    void shouldGenerateMessageIfJRE8() {
        template.getModel().put("#{from}", "from@mail.com");

        String expectedResult = "From: " + "from@mail.com\n" +
                "To: " + "to@mail.com\n" +
                "Subject: " + "Greeting mail!\n" +
                "Hello John Doe\nGreeting from from@mail.com";

        String result = templateEngine.generateMessage(template, client);
        assertEquals(expectedResult, result);
    }
}
