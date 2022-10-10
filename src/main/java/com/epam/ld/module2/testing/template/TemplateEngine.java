package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;

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
        return "From: " + template.getFrom() + "\n" +
                "To: " + client.getAddresses() + "\n" +
                "Subject: " + template.getSubject() + "\n" +
                content;
    }
}
