package com.platform.provider.controller;

import com.platform.module.entity.DateRange;
import com.platform.module.entity.params.HistogramParams;
import com.platform.module.entity.params.TermsParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;

@Api(value = "ES查询", tags = "ES定制化查询接口")
@RestController
@RequestMapping("/record")
public class RecordController {

    private final static String AGG_NAME = "_agg";
    @Value("${my.es.index.record}")
    private String index;

    @Autowired
    private RestHighLevelClient client;

    @PostMapping("/histogram")
    @ApiOperation(value = "查询", notes = "ES查询histogram")
    public Map<String, Double> histogram(@ApiParam(value = "参数") @RequestBody HistogramParams params) throws IOException {

        DateHistogramAggregationBuilder dateHistogramAggregationBuilder = AggregationBuilders.dateHistogram(AGG_NAME)
                .field("createTime")
                .timeZone(ZoneId.of("+08:00"));
        switch (params.getInterval()) {
            case "year":
                dateHistogramAggregationBuilder.dateHistogramInterval(DateHistogramInterval.YEAR).format("yyyy");
                break;
            case "day":
                dateHistogramAggregationBuilder.dateHistogramInterval(DateHistogramInterval.DAY).format("yyyy-MM-dd");
                break;
            case "week":
                dateHistogramAggregationBuilder.dateHistogramInterval(DateHistogramInterval.WEEK).format("yyyy-MM-dd");
                break;
            default:
                dateHistogramAggregationBuilder.dateHistogramInterval(DateHistogramInterval.MONTH).format("yyyy-MM");
                break;
        }

        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource().size(0);
        DateRange dateRange = params.getDateRange();
        if (dateRange != null) {
            // 时间范围
            dateRange(searchSourceBuilder, dateRange);

            Date begin = dateRange.getBegin();
            Date end = dateRange.getBegin();
            if (begin != null && end != null) {
                // 聚合, 时间边界
                dateHistogramAggregationBuilder.extendedBounds(new ExtendedBounds(begin.getTime(), end.getTime()));
            }
        }

        // 自聚合，求和
        dateHistogramAggregationBuilder.subAggregation(AggregationBuilders.sum(AGG_NAME).field("cost"));
        // 设置聚合参数
        searchSourceBuilder.aggregation(dateHistogramAggregationBuilder);

        // 返回结果
        Map<String, Double> map = new LinkedHashMap<>();

        // ES聚合统计
        SearchResponse searchResponse = client.search(new SearchRequest(index).source(searchSourceBuilder), RequestOptions.DEFAULT);
        ParsedDateHistogram parsedDateHistogram = searchResponse.getAggregations().get(AGG_NAME);
        parsedDateHistogram.getBuckets().forEach(bucket -> analyse(bucket, map));

        return map;
    }

    @ApiOperation(value = "terms查询", notes = "ES查询terms")
    @PostMapping("/terms")
    public Map<String, Double> terms(@ApiParam(value = "terms聚合") @RequestBody TermsParams params) throws IOException {

        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms(AGG_NAME).field("type").size(params.getSize());
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource().size(0);
        DateRange dateRange = params.getDateRange();
        if (dateRange != null) {
            // 时间范围
            dateRange(searchSourceBuilder, dateRange);
        }
        termsAggregationBuilder.subAggregation(AggregationBuilders.sum(AGG_NAME).field("cost"));
        // 聚合参数
        searchSourceBuilder.aggregation(termsAggregationBuilder);

        // 返回结果
        Map<String, Double> map = new TreeMap<>();
        // ES聚合统计
        SearchResponse searchResponse = client.search(new SearchRequest(index).source(searchSourceBuilder), RequestOptions.DEFAULT);
        ParsedTerms parsedTerms = searchResponse.getAggregations().get(AGG_NAME);
        parsedTerms.getBuckets().forEach(bucket -> analyse(bucket, map));
        return map;
    }

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        System.out.println("provider:" + request.getHeader("user"));
        return "hello world";
    }

    private void dateRange(SearchSourceBuilder searchSourceBuilder, DateRange dateRange) {
        // 查询，时间范围
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("createTime");
        Date begin = dateRange.getBegin();
        if (begin != null) {
            rangeQueryBuilder.gte(begin.getTime());
        }
        Date end = dateRange.getEnd();
        if (end != null) {
            rangeQueryBuilder.lt(end.getTime());
        }
        searchSourceBuilder.query(rangeQueryBuilder);
    }

    private void analyse(MultiBucketsAggregation.Bucket bucket, Map<String, Double> map) {
        ParsedSum parsedSum = bucket.getAggregations().get(AGG_NAME);
        map.put(bucket.getKeyAsString(), new BigDecimal(parsedSum.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

}
