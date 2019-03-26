package com.dhu.bbs.dao;

import com.dhu.bbs.model.Inform;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InformDao {
    String TABLE_NAME = "inform";
    String INSERT_FIELDS = " select2, url, describes, email, date";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{select2},#{url},#{describes},#{email},#{date})"})
    void addInform(Inform inform);
}
