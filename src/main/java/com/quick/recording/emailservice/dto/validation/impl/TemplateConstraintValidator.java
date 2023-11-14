package com.quick.recording.emailservice.dto.validation.impl;

import com.quick.recording.emailservice.dto.validation.Template;
import com.quick.recording.emailservice.enumeration.TemplateEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TemplateConstraintValidator implements ConstraintValidator<Template, String> {

    @Override
    public void initialize(Template constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            TemplateEnum.valueOf(value);
            return true;
        } catch (NullPointerException | IllegalArgumentException e) {
            return false;
        }
    }

}
