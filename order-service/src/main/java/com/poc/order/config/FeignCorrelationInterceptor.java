package com.poc.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class FeignCorrelationInterceptor implements RequestInterceptor {

    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    @Override
    public void apply(RequestTemplate template) {
        String correlationId = MDC.get("correlationId");
        if (correlationId != null && !correlationId.isBlank()) {
            template.header(CORRELATION_ID_HEADER, correlationId);
        }
    }

}
