package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessengerTest {

    Messenger messenger;

    TemplateEngine templateEngine;

    MailServer mailServer;

    Client client;

    @BeforeEach
    public void setUp() {
        templateEngine = mock(TemplateEngine.class);
        mailServer = mock(MailServer.class);
        client = new Client();
        client.setAddresses("Test address");

        messenger = new Messenger(mailServer, templateEngine);
    }

    @Test
    void shouldSendMessage() {
        doReturn("Test content").when(templateEngine).generateMessage(any(Template.class), any(Client.class));
        doNothing().when(mailServer).send("Test address", "Test content");

        messenger.sendMessage(client, new Template());

        verify(templateEngine).generateMessage(any(Template.class), any(Client.class));
        verify(mailServer).send("Test address", "Test content");
    }
}
