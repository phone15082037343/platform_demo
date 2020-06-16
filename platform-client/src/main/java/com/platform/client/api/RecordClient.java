package com.platform.client.api;

import com.platform.client.conf.FeignConfiguration;
import com.platform.client.fallback.RecordClientFallbackFactory;
import com.platform.module.entity.params.HistogramParams;
import com.platform.module.entity.params.TermsParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.GET;
import java.util.Map;

@FeignClient(value = "platform-provider", path = "/record", configuration = FeignConfiguration.class, fallbackFactory = RecordClientFallbackFactory.class)
public interface RecordClient {

    @PostMapping("/histogram")
    Map<String, Double> histogram(@RequestBody HistogramParams params);

    @PostMapping("/terms")
    Map<String, Double> terms(@RequestBody TermsParams params);

    @GetMapping("/hello")
    String hello();

}
