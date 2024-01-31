package com.quick.recording.emailservice.service;

import com.quick.recording.emailservice.dto.request.MailDTO;
import com.quick.recording.emailservice.enumeration.TemplateEnum;
import com.quick.recording.emailservice.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class MimeMessageCustom {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender javaMailSender;
    //
    private final TemplateEngine templateEngine;

    public MimeMessage getMimeMessage(MailDTO mail, MultipartFile[] files) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        Context context = new Context();
        context.setVariable("mail", mail);
        TemplateEnum template = TemplateEnum.valueOf(mail.getTemplate());
        helper.setText(templateEngine.process(template.getName(), context), true);
        File image;
        for (String path : template.getImages()) {
            image = getFileByPath(path);
            helper.addInline(image.getName(), image);
        }
        helper.setFrom(username);
        helper.setTo(new InternetAddress(mail.getTo()));
        if (Objects.nonNull(mail.getCc())) helper.setCc(mail.getCc().stream().toArray(String[] ::new));
        if (Objects.nonNull(mail.getBcc())) helper.setBcc(mail.getBcc().stream().toArray(String[] ::new));
        helper.setSubject(mail.getSubject());
        if (Objects.nonNull(files)) {
            for (MultipartFile file : files) {
                if (file.getSize() > 0) helper.addAttachment(file.getOriginalFilename(), file);
            }
        }
        return mimeMessage;
    }

    private File getFileByPath(String path){
        URL url = Optional
                .ofNullable(getClass().getResource(path))
                .orElseThrow(() -> new ResourceNotFoundException("file.not.found.by.selected.path", path));
        return new File(url.getFile());
    }
}
