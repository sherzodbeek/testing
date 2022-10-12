package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MessengerApplication {

    private final Scanner scanner;
    private final PrintStream printStream;

    public MessengerApplication(InputStream inputStream, PrintStream printStream) {
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    public void startConsoleMode() {
        printStream.println("Enter From:");
        String from = scanner.nextLine();
        printStream.println("Enter To:");
        String to = scanner.nextLine();
        printStream.println("Enter subject:");
        String subject = scanner.nextLine();
        printStream.println("Enter content (use #{key} in content):");
        String content = scanner.nextLine();
        printStream.println("Enter values in this format #{key1}:value1,#{key2}:value2");
        String model = scanner.nextLine();

        Client client = new Client();
        client.setAddresses(to);

        Template template = new Template();
        template.setFrom(from);
        template.setSubject(subject);
        template.setContent(content);
        template.setModel(creteModel(model));

        TemplateEngine templateEngine = new TemplateEngine();

        printStream.print(templateEngine.generateMessage(template, client));
    }

    public void startFileMode(String inputFilePath, String outputFilePath) {
        File file = new File(inputFilePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String value;
            Template template = new Template();
            Client client = new Client();
            Map<String, Object> model = new HashMap<>();
            while ((value = reader.readLine()) != null) {
                String[] keyAndValue = value.split(":");
                switch (keyAndValue[0]) {
                    case "From":
                        template.setFrom(keyAndValue[1]);
                        break;
                    case "To":
                        client.setAddresses(keyAndValue[1]);
                        break;
                    case "Subject":
                        template.setSubject(keyAndValue[1]);
                        break;
                    case "Content":
                        template.setContent(keyAndValue[1]);
                        break;
                    default:
                        model.put(keyAndValue[0], keyAndValue[1]);
                }
            }
            template.setModel(model);
            TemplateEngine templateEngine = new TemplateEngine();
            String result = templateEngine.generateMessage(template, client);
            writer.write(result);
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }
    }

    private Map<String, Object> creteModel(String model) {
        String[] keyValuePairs = model.split(",");
        Map<String, Object> result = new HashMap<>();
        for (String keyValuePair : keyValuePairs) {
            String[] keyAndValue = keyValuePair.split(":");
            result.put(keyAndValue[0], keyAndValue[1]);
        }
        return result;
    }


    public static void main(String[] args) {
        MessengerApplication messengerApplication = new MessengerApplication(System.in, System.out);
        if (args.length == 0) {
            messengerApplication.startConsoleMode();
        } else {
            messengerApplication.startFileMode(args[0], args[1]);
        }
    }
}
