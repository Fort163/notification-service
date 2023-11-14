package com.quick.recording.emailservice.dto.request;

import com.quick.recording.emailservice.dto.validation.Emails;
import com.quick.recording.emailservice.dto.validation.Template;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import lombok.Data;


@Data
public class MailDTO implements Serializable {

    @Email(message = "{mail.dto.to.email}")
    @NotNull(message = "{mail.dto.to.not.null}")
    private String to;
    @Emails(message = "{mail.dto.cc.emails}")
    private List<String> cc;
    @Emails(message = "{mail.dto.bcc.emails}")
    private List<String> bcc;
    @Template(message = "{mail.dto.template.template}")
    private String template;
    @NotNull(message = "{mail.dto.subject.not.null}")
    private String subject;
    private String text;

}
