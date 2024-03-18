package com.hanyang.service;

import com.hanyang.iterator.EsQueryIterator;
import com.hanyang.iterator.EsSqlQuery;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class EsQueryService {
    public Object queryEsBySql(EsSqlQuery esSqlQuery) {
        EsQueryIterator iterator = esSqlQuery.iterator();
        Stream<Map<String, Object>> resultStream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        return resultStream.collect(Collectors.toList());
    }
}
