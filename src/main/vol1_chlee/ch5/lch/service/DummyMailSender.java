package main.vol1_chlee.ch5.lch.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {

    public void send(SimpleMailMessage mailMessage)
            throws MailException {}

    public void send(SimpleMailMessage[] mailMessage)
            throws MailException {}
}
