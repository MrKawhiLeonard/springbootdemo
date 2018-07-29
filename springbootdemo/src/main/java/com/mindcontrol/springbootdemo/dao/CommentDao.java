package com.mindcontrol.springbootdemo.dao;

import com.mindcontrol.springbootdemo.model.Comment;
import com.mindcontrol.springbootdemo.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDao {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id,created_date,entity_id,entity_type,content,status ";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")values(" +
            "#{userId},#{createdDate},#{entityId},#{entityType},#{content},#{status})"})
    int addComment(Comment comment);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
            " where id = #{id}"})
    Comment getCommentById(int id);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
            " where entity_id = #{entityId} and entity_type = #{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId,
                                         @Param("entityType") int entityType);

    @Select({"select count(id) from ",TABLE_NAME," where entity_id = #{entityId} and entity_type = #{entityType}"})
    int getCommentCount(@Param("entityId") int entityId,
                        @Param("entityType") int entityType);

    @Update({"update ",TABLE_NAME," set status = #{status} where id = #{id}"})
    int updateStatus(@Param("id") int id,
                      @Param("status") int status);
}
