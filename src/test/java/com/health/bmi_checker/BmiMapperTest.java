package com.health.bmi_checker;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.health.bmi_checker.entity.BodyData;
import com.health.bmi_checker.mapper.BmiMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BmiMapperTest {

    @Autowired
    private BmiMapper bmiMapper;

    @Test
    @DataSet(value = "datasets/bodydatas.yml")
    @Transactional
    void 指定したIDの従業員情報を取得できること() {
        Optional<BodyData> actual = bmiMapper.findById(1);
        assertThat(actual).hasValue(new BodyData(1, "タナカ　イチロウ", 20, 171.5, 60.2));
    }

}
