package com.health.BMI_check;

import com.health.BMI_check.entity.BodyData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BmiMapper {

    //全件取得
    // SELECT * FROM テーブル名
    @Select("SELECT * FROM BMIs")
    List<BodyData> findAll();

    //部分一致検索
    // SELECT * FROM テーブル名 WHERE カラム名 LIKE CONTACT(#{条件}, `検索文字`)
    @Select("SELECT * FROM BMIs WHERE name LIKE CONCAT(#{startsWith}, '%')")
    List<BodyData> findByNameStartingWith(String startsWith);

    //SELECT * FROM テーブル名 WHERE カラム名 =#{カラム名}
//    @Select("SELECT * FROM BMIs WHERE id = #{id}")
//    Optional<BodyData> findById(int id);

}
