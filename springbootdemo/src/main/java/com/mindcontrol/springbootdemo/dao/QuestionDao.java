package com.mindcontrol.springbootdemo.dao;

import com.mindcontrol.springbootdemo.model.Question;
import com.mindcontrol.springbootdemo.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionDao {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title,content,user_id,created_date,comment_count ";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")values(" +
            "#{title},#{content},#{userId},#{createdDate},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where id = #{id}"})
    Question selectById(int id);

    List<Question> selectLatestQuestions(@Param("userId")int userId,
                               @Param("offset")int offset,
                               @Param("limit")int limit);

    @Update({"update ",TABLE_NAME," set comment_count = #{commentCount} where id = #{id}"})
    int updateCommentCount(@Param("id") int id,
                           @Param("commentCount") int commentCount);
}
