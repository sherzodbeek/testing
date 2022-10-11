package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessengerTest {

    @Test
    void shouldSendMessage() {
        Messenger messenger = mock(Messenger.class);
        doNothing().when(messenger).sendMessage(any(Client.class), any(Template.class));

        messenger.sendMessage(new Client(), new Template());

        verify(messenger).sendMessage(any(Client.class), any(Template.class));
    }
}
