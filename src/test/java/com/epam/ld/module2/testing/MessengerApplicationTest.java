package com.epam.ld.module2.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class MessengerApplicationTest {


    @TempDir
    File template;

    @TempDir
    File result;

    ByteArrayInputStream inputStream;
    ByteArrayOutputStream outputStream;

    PrintStream printStream;

    MessengerApplication messengerApplication;

    @BeforeEach
    public void setUp() {
        inputStream = mock(ByteArrayInputStream.class);
        outputStream = mock(ByteArrayOutputStream.class);
//        inputStream = new ByteArrayInputStream("from@mail.com\nto@mail.com\ntestSubject\nHello #{testContent}\n#{testContent}:test".getBytes());
//        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        messengerApplication = new MessengerApplication(inputStream, printStream);
    }

    @Test
    void shouldStartConsoleMode() {
        when(inputStream.readAllBytes()).thenReturn("from@mail.com\nto@mail.com\ntestSubject\nHello #{testContent}\n#{testContent}:test".getBytes());
        messengerApplication.startConsoleMode();
        String result = outputStream.toString();
        String expected = "Enter From:\nEnter To:\nEnter subject:\nEnter content (use #{key} in content):\nEnter values in this format #{key1}:value1,#{key2}:value2\n"
                + "From: from@mail.com\nTo: to@mail.com\nSubject: testSubject\nHello test";
        assertEquals(expected, result);
    }

    @Test
    void shouldStartFileMode() throws IOException {
        File testTemplate = new File(template, "template.txt");
        List<String> templateLines = Arrays.asList(
                "From:from@mail.com",
                "To:to@mail.com",
                "Subject:Test subject",
                "Content:Hello #{name} from #{test}",
                "#{name}:John",
                "#{test}:TestMessenger");
        Files.write(testTemplate.toPath(), templateLines);

        File testResult = new File(result, "output.txt");

        messengerApplication.startFileMode(testTemplate.getPath(), testResult.getAbsolutePath());

        List<String> resultLines = Arrays.asList(
                "From: from@mail.com",
                "To: to@mail.com",
                "Subject: Test subject",
                "Hello John from TestMessenger");

        assertLinesMatch(resultLines, Files.readAllLines(testResult.toPath()));
    }

}
