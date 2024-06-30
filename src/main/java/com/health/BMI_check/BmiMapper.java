package com.health.BMI_check;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BmiMapper {

    @Select("SELECT * FROM BMIs")
    List<BodyData> findAll();

    @Select("SELECT * FROM BMIs WHERE bmi LIKE CONCAT(#{prefix}, '%')")
    List<BodyData> findByNameStartingWith(String prefix);

    @Select("SELECT * FROM names WHERE id = #{id}")
    Optional<BodyData> findById(int id);

}
