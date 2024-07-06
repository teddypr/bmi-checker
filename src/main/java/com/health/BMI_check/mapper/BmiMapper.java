package com.health.BMI_check.mapper;

import com.health.BMI_check.entity.BodyData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BmiMapper {

    //全件取得
    // SELECT * FROM テーブル名
    @Select("SELECT * FROM BMIs")
    List<BodyData> findAll();

    //部分一致検索
    // １行目：SELECT * FROM テーブル名 WHERE カラム名 LIKE CONTACT(#{条件}, `検索文字`)
    // ２行目：欲しいデータ + find〇〇 + （型＋クエリ文字）
    @Select("SELECT * FROM BMIs WHERE name LIKE CONCAT(#{startsWith}, '%')")
    List<BodyData> findByNameStartingWith(String startsWith);

    //該当する ID を持つレコードを検索
    // 1行目：SELECT * FROM テーブル名 WHERE カラム名 =#{カラム名}
    // 2行目：欲しいデータ（空の可能性のあり） + find〇〇 + (型＋パス変数)
    @Select("SELECT * FROM BMIs WHERE id = #{id}")
    Optional<BodyData> findById(int id);

}
