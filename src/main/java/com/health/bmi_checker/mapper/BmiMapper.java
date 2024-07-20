package com.health.bmi_checker.mapper;

import com.health.bmi_checker.entity.BodyData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BmiMapper {

    //全件取得
    @Select("SELECT * FROM BMIs")
    List<BodyData> findAll();

    //部分一致検索
    @Select("SELECT * FROM BMIs WHERE name LIKE CONCAT(#{startsWith}, '%')")
    List<BodyData> findByNameStartingWith(String startsWith);

    //該当する ID を持つレコードを検索
    @Select("SELECT * FROM BMIs WHERE id = #{id}")
    Optional<BodyData> findById(int id);

    //インサート用
    @Insert("INSERT INTO BMIs (name, age, height, weight) VALUES (#{name}, #{age}, #{height}, #{weight})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(BodyData bodyData);

}
