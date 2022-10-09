package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemplateEngineTest {


    @Test
    void shouldGenerateMessage() {
        Template template = new Template();
        Client client = new Client();

        TemplateEngine engineTest = new TemplateEngine();
        String result = engineTest.generateMessage(template, client);
        assertNull(result);
    }


}
