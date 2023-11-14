package com.quick.recording.emailservice.dto.validation;

import com.quick.recording.emailservice.dto.validation.impl.TemplateConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {TemplateConstraintValidator.class})
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Template {

    String message() default "{template.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
