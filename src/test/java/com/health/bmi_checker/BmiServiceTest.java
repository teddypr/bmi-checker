package com.health.bmi_checker;

import com.health.bmi_checker.controller.exceptionHandler.DataNotFoundException;
import com.health.bmi_checker.entity.BodyData;
import com.health.bmi_checker.mapper.BmiMapper;
import com.health.bmi_checker.service.BmiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BmiServiceTest {

    @InjectMocks
    BmiService bmiService;

    @Mock
    BmiMapper bmiMapper;

    /**
     * READ処理テスト
     */
    @Test
    public void 全てのユーザーが正常に返されること() {
        //Arrange　スタブするモックの動きを決める
        List<BodyData> bodyData = List.of(
                new BodyData(1, "タナカ　イチロウ", 20, 171.5, 60.2),
                new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0),
                new BodyData(3, "ムラタ　サブロウ", 26, 167.9, 101.3)
        );
        doReturn(bodyData).when(bmiMapper).findAll();

        //Act
        List<BodyData> actual = bmiService.findAll();

        //Assert
        assertThat(actual).isEqualTo(bodyData);

        //スタブの呼び出しを検証
        verify(bmiMapper).findAll();

    }

    @Test
    void 存在する名前の頭文字を指定した時に正常に従業員情報が返されること() {
        // Arrange
        String startsWith = "タ";
        List<BodyData> expectedBodyDataList = Arrays.asList(
                new BodyData(1, "タナカ　イチロウ", 20, 171.5, 60.2),
                new BodyData(18, "タカハシ　カズキ", 31, 170.6, 67.8)
        );

        when(bmiMapper.findByNameStartingWith(startsWith)).thenReturn(expectedBodyDataList);

        // Act
        List<BodyData> actualBodyDataList = bmiService.findAcronym(startsWith);

        // Assert
        assertEquals(expectedBodyDataList, actualBodyDataList);

        //スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findByNameStartingWith("タ");
    }


    @Test
    public void 存在しない名前の頭文字を指定した時に例外が発生すること() {
        //Arrange
        String startsWith = "ン";

        when(bmiMapper.findByNameStartingWith(startsWith)).thenReturn(Arrays.asList());

        // Act ＆ Assert
        DataNotFoundException thrown = assertThrows(
                DataNotFoundException.class,
                () -> bmiService.findAcronym(startsWith)
        );

        assertEquals("該当する従業員は存在しません", thrown.getMessage());

        //スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findByNameStartingWith("ン");
    }


    @Test
    public void 存在するユーザーのIDを指定した時に正常にユーザーが返されること() {
        //Arrange
        doReturn(Optional.of(new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0))).when(bmiMapper).findById(2);

        //Act
        BodyData actual = bmiService.findId(2);

        //Assert
        assertThat(actual).isEqualToComparingFieldByField(new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0));
        assertThat(actual.getBmi()).isEqualTo(26.861206922865602);

        //スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(2);
    }


    //例外をthrowする場合の検証はどう書くのか　assertThatThrowBy　DoNothing
    @Test
    public void 存在しないIDを指定した時に例外が発生すること() {
        //Arrange
        doReturn(Optional.empty()).when(bmiMapper).findById(100);

        //Assert
        assertThatThrownBy(() -> {
            bmiService.findId(100);
        }).isInstanceOf(DataNotFoundException.class)
                .hasMessage("該当する従業員は存在しません");

        //スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(100);

    }


    /**
     * Create処理テスト
     */
    @Test
    public void 正常に新規のユーザーが登録できること() {
        // Arrange
        BodyData newBodyData = new BodyData("トヨタ トミ", 26, 163.4, 53.3);

        //Act
        doNothing().when(bmiMapper).insert(newBodyData);

        //Assert
        bmiService.insert(newBodyData.getName(), newBodyData.getAge(), newBodyData.getHeight(), newBodyData.getWeight());

        // スタブの呼び出しを検証
        verify(bmiMapper, times(1)).insert(any(BodyData.class));

    }

    /**
     * Update処理テスト
     */
    @Test
    public void 従業員情報が正常に更新できること() {
        //Arrange
        BodyData existingBodyData = new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0);

        //Act
        doReturn(Optional.of(existingBodyData)).when(bmiMapper).findById(2);
        doNothing().when(bmiMapper).update(any(BodyData.class));
        BodyData actual = bmiService.update(2, "Update User", 35, 184.0, 89.0);

        // Update expected data manually
        existingBodyData.setName("Update User");
        existingBodyData.setAge(35);
        existingBodyData.setHeight(184.0);
        existingBodyData.setWeight(89.0);

        //Assert
        assertThat(actual).isEqualTo(existingBodyData);

        // スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(2);
        verify(bmiMapper, times(1)).update(existingBodyData);

    }

    @Test
    public void 存在しないIDの従業員情報を更新しようとした時に例外が発生すること() {
        // Arrange
        int nonExistentId = 999;

        // Act
        doReturn(Optional.empty()).when(bmiMapper).findById(nonExistentId);

        // Assert
        assertThatThrownBy(() -> bmiService.update(nonExistentId, "タナカ　イチロウ", 20, 171.5, 60.2))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("存在しない従業員 ID です: " + nonExistentId);

        // スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(nonExistentId);
        verify(bmiMapper, times(0)).update(any(BodyData.class));

    }


}
