package com.health.BMI_check.mapper;

import com.health.BMI_check.entity.BodyData;
import org.apache.ibatis.annotations.Mapper;
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

}
