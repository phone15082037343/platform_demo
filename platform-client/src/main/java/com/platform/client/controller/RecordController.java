package com.platform.client.controller;

import com.platform.client.api.RecordClient;
import com.platform.module.entity.params.HistogramParams;
import com.platform.module.entity.params.TermsParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import java.io.IOException;
import java.util.Map;

@Api(value = "ES查询", tags = "ES定制化查询接口")
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordClient recordClient;

    @PostMapping("/histogram")
    @ApiOperation(value = "查询", notes = "ES查询histogram")
    public Map<String, Double> histogram(@ApiParam(value = "参数") @RequestBody HistogramParams params) {
        return recordClient.histogram(params);
    }

    @ApiOperation(value = "terms查询", notes = "ES查询terms")
    @PostMapping("/terms")
    public Map<String, Double> terms(@ApiParam(value = "terms聚合") @RequestBody TermsParams params) throws IOException {
        return recordClient.terms(params);
    }

    /**
     * TODO
     */
    @ApiOperation(value = "hello", notes = "hello world")
    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        System.out.println("header:" + request.getHeader("user"));
        return recordClient.hello();
    }

}
