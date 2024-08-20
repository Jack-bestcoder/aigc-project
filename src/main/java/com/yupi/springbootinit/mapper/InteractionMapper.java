package com.yupi.springbootinit.mapper;

import com.yupi.springbootinit.model.entity.UserQuery;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface InteractionMapper {

    @Select("${sql}")
    List<Map<String, Object>> executeSql(@Param("sql") String sql);

    @Select("SELECT table_name FROM information_schema.tables WHERE table_schema = #{dbName}")
    List<String> selectAllTableNamesByDbName(@Param("dbName") String dbName);

    List<UserQuery> selectUserQueryByUserIdWithCursor(@Param("userId") long userId, @Param("cursor") long cursor, @Param("num") int num);

    @Insert("INSERT INTO u_user_query (user_id, prompt) VALUES (#{userId}, #{prompt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUserQuery(UserQuery userQuery);

    @Update("UPDATE u_user_query SET generate_sql = #{generateSql} WHERE id = #{id}")
    int updateGenerateSqlById(@Param("id") long id, @Param("generateSql") String generateSql);

}

