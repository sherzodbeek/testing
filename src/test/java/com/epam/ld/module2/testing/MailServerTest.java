package com.epam.ld.module2.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MailServerTest {

    MailServer mailServer;

    @BeforeEach
    public void setUp() {
        mailServer = mock(MailServer.class);
    }


    @Test
    void shouldSendMessage() {
        doNothing().when(mailServer).send(anyString(), anyString());
        mailServer.send("to@mail.com", "Message content");
        verify(mailServer).send(anyString(), anyString());
    }
}
