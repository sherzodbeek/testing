package com.epam.ld.module2.testing;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class MessengerApplicationTest {

    @Test
    void shouldStartConsoleMode() {
        MessengerApplication messengerApplication = mock(MessengerApplication.class);
        messengerApplication.startConsoleMode();
        verify(messengerApplication).startConsoleMode();
    }

}
