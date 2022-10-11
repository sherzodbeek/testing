package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.exception.PlaceholderMissedException;

import java.util.regex.Pattern;

/**
 * The type Template engine.
 */
public class TemplateEngine {
    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {
        String content = template.getContent();
        for (String key : template.getModel().keySet()) {
            content = content.replace(key, template.getModel().get(key).toString());
        }
        Pattern regex = Pattern.compile("(.*)#\\{(.*?)}(.*)", Pattern.DOTALL);
        if (regex.matcher(content).find()) {
            throw new PlaceholderMissedException("Placeholder missed!");
        }
        return "From: " + template.getFrom() + "\n" +
                "To: " + client.getAddresses() + "\n" +
                "Subject: " + template.getSubject() + "\n" +
                content;
    }
}
