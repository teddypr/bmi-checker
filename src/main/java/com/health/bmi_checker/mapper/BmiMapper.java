package com.health.bmi_checker.mapper;

import com.health.bmi_checker.entity.BodyData;
import org.apache.ibatis.annotations.*;

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

    //アップデート用
    @Update("UPDATE BMIs SET name = #{name}, age = #{age}, height = #{height}, weight = #{weight} WHERE id = #{id}")
    void update(BodyData bodyData);

    //デリート用
    @Delete("DELETE FROM BMIs WHERE id = #{id}")
    void delete(int id);

}
