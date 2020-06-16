package com.platform.client.fallback;

import com.platform.client.api.RecordClient;
import com.platform.module.entity.params.HistogramParams;
import com.platform.module.entity.params.TermsParams;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class RecordClientFallbackFactory implements FallbackFactory<RecordClient> {

    @Override
    public RecordClient create(Throwable throwable) {
        throwable.printStackTrace();
        return new RecordClient() {
            @Override
            public Map<String, Double> histogram(HistogramParams params) {
                return new LinkedHashMap<>();
            }

            @Override
            public Map<String, Double> terms(TermsParams params) {
                return new HashMap<>();
            }

            @Override
            public String hello() {
                return "";
            }
        };
    }

}
