package com.mju.mentoring.mission.config;

import com.mju.mentoring.mission.ui.progress.helper.StringToProgressStatusConverter;
import com.mju.mentoring.mission.ui.progress.helper.StringToRewardStatusConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToProgressStatusConverter());
        registry.addConverter(new StringToRewardStatusConverter());
    }
}
