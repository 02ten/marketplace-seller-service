package com.seller.sellerservice.configuration;

import io.opentracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaegerConfiguration {
    @Bean
    public Tracer jaegerTracer() {
        return new io.jaegertracing.Configuration("Seller-service")
                .withSampler(new io.jaegertracing.Configuration.SamplerConfiguration().withType("const").withParam(1))
                .withReporter(new io.jaegertracing.Configuration.ReporterConfiguration()
                        .withLogSpans(true)
                        .withSender(new io.jaegertracing.Configuration.SenderConfiguration()
                                .withEndpoint("http://jaeger.jaeger:14268/api/traces"))) // HTTP endpoint
                .getTracer();
    }

}
