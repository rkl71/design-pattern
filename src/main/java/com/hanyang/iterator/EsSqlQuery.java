package com.hanyang.iterator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class EsSqlQuery implements EsSqlQueryInterface<EsQueryIterator>{
    // 查询的SQL语句
    private String query;
    // fetchSize参数
    private Long fetchSize;
    // 分页查询的cursor参数
    private String cursor;

    // 分页查询时，只传入cursor参数即可
    public EsSqlQuery(String cursor){
        this.cursor = cursor;
    }

    public EsSqlQuery(String query, Long fetchSize){
        this.query = query;
        this.fetchSize = fetchSize;
    }

    // 创建具体的迭代器
    @Override
    public EsQueryIterator iterator() {
        return new EsQueryIterator(this.query, this.fetchSize);
    }
}
