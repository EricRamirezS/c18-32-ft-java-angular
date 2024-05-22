package tech.nocountry.c1832ftjavaangular.service;

import com.mailersend.sdk.Recipient;

import java.util.Collection;

public interface EmailService {

    /**
     * Sends an HTML email to the specified recipients with the given subject and message.
     *
     * @param recipients the recipients of the email
     * @param subject    the subject of the email
     * @param message    the HTML content of the email
     * @return true if the email was sent successfully, false otherwise
     */
    boolean sendHtmlEmail(Collection<Recipient> recipients, String subject, String message);

    /**
     * Sends a plain text email to the specified recipients with the given subject and message.
     * If useDefaultTemplate is true, the message is formatted using a default template.
     *
     * @param recipients         the recipients of the email
     * @param subject            the subject of the email
     * @param message            the plain text content of the email
     * @param useDefaultTemplate whether to use a default template for the message
     * @return true if the email was sent successfully, false otherwise
     */
    boolean sendPlainEmail(Collection<Recipient> recipients, String subject, String message, boolean useDefaultTemplate);

    /**
     * Sends a plain text email to the specified recipients with the given subject and message.
     *
     * @param recipients the recipients of the email
     * @param subject    the subject of the email
     * @param message    the plain text content of the email
     * @return true if the email was sent successfully, false otherwise
     */
    boolean sendPlainEmail(Collection<Recipient> recipients, String subject, String message);

}
