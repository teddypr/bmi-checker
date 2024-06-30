package com.example.mybatisdemo.mapper;

import com.example.mybatisdemo.entity.Name;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper // MyBatisのMapperである⽬印として@Mapperアノテーションを付与する
public interface NameMapper { // classではなくinterfaceで定義する

    @Select("SELECT * FROM names")
    List<Name> findAll();

    // prefixは接頭辞という意味
    @Select("SELECT * FROM names WHERE name LIKE CONCAT(#{prefix}, '%')")
    List<Name> findByNameStartingWith(String prefix);

    @Select("SELECT * FROM names WHERE id = #{id}")
    Optional<Name> findById(int id); //Optional はListと違い、値があるかもしれないしないかもしれない時に使う

}
