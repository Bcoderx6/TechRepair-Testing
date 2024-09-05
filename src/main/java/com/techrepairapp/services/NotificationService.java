package com.techrepairapp.services;

import com.techrepairapp.models.Customer;
import com.techrepairapp.models.Employee;

import io.github.cdimascio.dotenv.Dotenv;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class NotificationService {

    private final String username;
    private final String password;

    public NotificationService() {
        // Load environment variables
        Dotenv dotenv = Dotenv.load();
        this.username = dotenv.get("GMAIL_USERNAME");
        this.password = dotenv.get("GMAIL_PASSWORD");

        // Optional: Print the loaded variables for debugging
        System.out.println("Gmail Username: " + username);
        System.out.println("Gmail Password: " + password);
    }

    private Session createSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        if (username == null || password == null) {
            throw new IllegalStateException("Gmail username or password is not set.");
        }

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private void sendEmail(String recipient, String subject, String body) {
        if (recipient == null || recipient.isEmpty()) {
            System.err.println("Recipient email address is null or empty.");
            return;
        }

        try {
            Message message = new MimeMessage(createSession());
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void notifyCustomer(Customer customer, String message) {
        System.out.println("Sending notification to Customer: " + customer.getName());
        sendEmail(customer.getEmail(), "Customer Notification", message);
    }

    public void notifyEmployee(Employee employee, String message) {
        System.out.println("Sending notification to Employee: " + employee.getName());
        sendEmail(employee.getEmail(), "Employee Notification", message);
    }
}
