package com.quick.recording.notification.service.util;

import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import com.quick.recording.gateway.enumerated.TemplateEnum;
import lombok.AllArgsConstructor;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class TemplateUtil {

    //TODO
    //private final ResourcePatternResolver resourceResolver;
    private final MessageUtil messageUtil;

    public Pair<String, Boolean> getTemplate(TemplateEnum templateEnum){
        return switch (templateEnum){
            case QR_B2B_CODE -> Pair.of("qr_code.html",true);
            case REGISTRATION -> Pair.of("registration.html",true);
            case QR_B2B_NOTIFICATION -> Pair.of("qr_b2b.html",true);
        };
    }

    public List<File> getImage(TemplateEnum templateEnum){
        return switch (templateEnum){
            case QR_B2B_CODE, QR_B2B_NOTIFICATION -> Stream.of("/images/qr_logo.png").map(this::getFileByPath).toList();
            case REGISTRATION -> List.of();
        };
    }

    private File getFileByPath(String path){
        URL url = Optional
                .ofNullable(getClass().getResource(path))
                .orElseThrow(() -> new NotFoundException(
                        messageUtil.create("file.not.found.by.selected.path", path)
                ));
        return new File(url.getFile());
    }

}
