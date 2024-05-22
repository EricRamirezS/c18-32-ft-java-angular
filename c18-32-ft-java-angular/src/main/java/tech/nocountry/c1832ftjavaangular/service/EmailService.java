package tech.nocountry.c1832ftjavaangular.service;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Service()
public class EmailService {

    @Value("${mailersend.api.key}")
    private String apiKey;
    private final ResourceLoader resourceLoader;

    Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(ResourceLoader resourceLoader) {this.resourceLoader = resourceLoader;}

    private boolean sendEmail(Collection<Recipient> recipients, String subject, String content, boolean isHtml) {
//        if (!activeProfile.equals("prod")) {
//            logger.warn("Emails can only be sent in production environment");
//            return true;
//        }

        Email email = new Email();

        email.setFrom("Viajes Grupales", "do-not-reply@ericramirezs.com");
        email.AddRecipients(recipients.toArray(new Recipient[0]));

        email.setSubject(subject);
        if (isHtml) {
            email.setHtml(content);
        } else {
            email.setPlain(content);
        }

        MailerSend ms = new MailerSend();

        System.out.println(apiKey);
        ms.setToken(apiKey);
        try {
            ms.emails().send(email);
            logger.info("Email sent successfully with subject {}", subject);
            return true;
        } catch (MailerSendException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean sendHtmlEmail(Collection<Recipient> recipients, String subject, String message) {
        return sendEmail(recipients, subject, message, true);
    }

    public boolean sendPlainEmail(Collection<Recipient> recipients, String subject, String message,
                                  boolean useDefaultTemplate) {
        if (useDefaultTemplate) {
            try {
                Resource resource = resourceLoader.getResource("classpath:template/EmailTemplate.html");
                String template = resource.getContentAsString(StandardCharsets.UTF_8);
                message = String.format(template, subject, message);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            return sendHtmlEmail(recipients, subject, message);
        } else {
            return sendEmail(recipients, subject, message, false);
        }
    }

    public boolean sendPlainEmail(Collection<Recipient> recipients, String subject, String message) {
        return sendEmail(recipients, subject, message, false);
    }
}

