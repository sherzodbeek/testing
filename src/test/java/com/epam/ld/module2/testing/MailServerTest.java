package com.epam.ld.module2.testing;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class MailServerTest {

    @Test
    void shouldSendMessage() {
        MailServer mailServer = mock(MailServer.class);
        doNothing().when(mailServer).send(anyString(), anyString());
        mailServer.send("to@mail.com", "Message content");
        verify(mailServer).send(anyString(), anyString());
    }

}
