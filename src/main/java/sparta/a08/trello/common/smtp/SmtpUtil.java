package sparta.a08.trello.common.smtp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sparta.a08.trello.common.smtp.dto.MailRequest;

@Service
@Slf4j(topic = "SmtpUtil")
@RequiredArgsConstructor
public class SmtpUtil {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;

    public void sendMail(MailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();

        //메일을 보내는 이메일 (서비스에서 운영하는 이메일)
        message.setFrom(from);

        //메일을 받는 이메일
        message.setTo(request.getTo());

        message.setSubject(request.getTitle());
        message.setText(request.getContent());

        javaMailSender.send(message);

        log.info("메일 전송: From: {}, To: {}", from, request.getTo());
    }
}
