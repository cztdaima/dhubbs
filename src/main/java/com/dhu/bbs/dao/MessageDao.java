package com.dhu.bbs.dao;

import com.dhu.bbs.model.Message;
import com.dhu.bbs.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageDao {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = " title, image, like_count, comment_count, created_date, user_id";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
    ") values (#{title},#{image},#{like_count},#{comment_count}, #{created_date}, #{user_id})" })
    void messageAdd(Message message);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    Message selectById(int id);

//    @Update({"update", TABLE_NAME, " set password=#{password} where name=#{name}"})
//    void updatePassword(User user);
//
//    @Delete({"delete from", TABLE_NAME, "WHERE name=#{name}"})
//    void deleteByName(User user);
//
//    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
//    User selectById(int id);
//
//    @Select({"select id from", TABLE_NAME, "where name=#{name}"})
//    int selectIDByName(String name);

     List<Message> findByName(@Param("title") String title);

    List<Message> selectByUserIdAndOffset(@Param("user_id") int user_id , @Param("offset") int offset,
    @Param("limit") int limit);

}