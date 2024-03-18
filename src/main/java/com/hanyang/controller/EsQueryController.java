package com.hanyang.controller;

import com.hanyang.iterator.EsSqlQuery;
import com.hanyang.service.EsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EsQueryController {
    @Autowired
    private EsQueryService esQueryService;

    @PostMapping("/queryEsBySql")
    public Object queryEsBySql(@RequestBody EsSqlQuery esSqlQuery) {
        return esQueryService.queryEsBySql(esSqlQuery);
    }
}
