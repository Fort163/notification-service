package com.quick.recording.emailservice.dto.validation.impl;

import com.quick.recording.emailservice.dto.validation.Emails;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class EmailsConstraintValidator implements ConstraintValidator<Emails, List<String>> {

    @Override
    public void initialize(Emails constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> ccEmails, ConstraintValidatorContext context) {
        if (Objects.isNull(ccEmails)) return true;
        try {
            for (String ccEmail : ccEmails) {
                InternetAddress address = new InternetAddress(ccEmail);
                address.validate();
            }
            return true;
        } catch (AddressException e) {
            return false;
        }
    }

}
