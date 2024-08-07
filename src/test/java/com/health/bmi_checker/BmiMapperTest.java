package com.health.bmi_checker;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.health.bmi_checker.entity.BodyData;
import com.health.bmi_checker.mapper.BmiMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class BmiMapperTest {

    @Autowired
    private BmiMapper bmiMapper;

    @Test
    @DataSet(value = "datasets/bodydata.yml")
    void 全ての従業員情報が正常に返されること() {
        List<BodyData> bodyDataList = bmiMapper.findAll();
        assertThat(bodyDataList).hasSize(4);
        assertThat(bodyDataList).contains(
                new BodyData(1, "タナカ　イチロウ", 20, 171.5, 60.2),
                new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0),
                new BodyData(3, "ムラタ　サブロウ", 26, 167.9, 101.3),
                new BodyData(4, "タカハシ　カズキ", 31, 170.6, 67.8)
        );
    }

    @Test
    @DataSet(value = "datasets/bodydata.yml")
    void 指定した名前の頭文字で従業員情報が正常に返されること() {
        List<BodyData> bodyDataList = bmiMapper.findByNameStartingWith("タ");
        assertThat(bodyDataList).hasSize(2);
        assertThat(bodyDataList).containsExactlyInAnyOrder(
                new BodyData(1, "タナカ　イチロウ", 20, 171.5, 60.2),
                new BodyData(4, "タカハシ　カズキ", 31, 170.6, 67.8)
        );
    }

    @Test
    @DataSet(value = "datasets/bodydata.yml")
    void 指定したIDの従業員情報を取得できること() {
        Optional<BodyData> actual = bmiMapper.findById(1);
        assertThat(actual).hasValue(new BodyData(1, "タナカ　イチロウ", 20, 171.5, 60.2));
    }

    @Test
    @DataSet(value = "datasets/bodydata.yml")
    @ExpectedDataSet(value = "datasets/expectedInsertBodyData.yml", ignoreCols = "id")
    void 新規従業員情報が正常に挿入できること() {
        BodyData newBodyData = new BodyData(null, "トヨタ トミ", 26, 163.4, 53.3);
        bmiMapper.insert(newBodyData);

        List<BodyData> bodyDataList = bmiMapper.findAll();
        assertThat(bodyDataList).hasSize(5);
    }

    @Test
    @DataSet(value = "datasets/bodydata.yml")
    @ExpectedDataSet(value = "datasets/expectedUpdatedBodyData.yml")
    void 従業員情報が正常に更新できること() {
        BodyData existingBodyData = new BodyData(2, "スズキ ジロウ", 18, 181.0, 88.0);
        existingBodyData.setName("Updated Name");
        bmiMapper.update(existingBodyData);

        Optional<BodyData> updatedBodyData = bmiMapper.findById(2);
        assertThat(updatedBodyData).isPresent();
        assertThat(updatedBodyData.get().getName()).isEqualTo("Updated Name");
    }

    @Test
    @DataSet(value = "datasets/bodydata.yml")
    @ExpectedDataSet(value = "datasets/expectedDeletedBodyData.yml")
    void 指定したIDの従業員情報が正常に削除できること() {
        bmiMapper.delete(1);

        List<BodyData> bodyDataList = bmiMapper.findAll();
        assertThat(bodyDataList).hasSize(3);
    }

}
