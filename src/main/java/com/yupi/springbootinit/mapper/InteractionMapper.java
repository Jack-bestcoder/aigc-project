package com.yupi.springbootinit.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface InteractionMapper {

    @Select("${sql}")
    List<Map<String, Object>> executeSql(@Param("sql") String sql);
}

