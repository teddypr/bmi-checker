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
                new BodyData(3, "ムラタ　サブロウ", 26, 167.9, 101.3),
                new BodyData(4, "タカハシ　カズキ", 31, 170.6, 67.8)

        );
        doReturn(bodyData).when(bmiMapper).findAll();

        // Act
        List<BodyData> actual = bmiService.findAll();

        // Assert
        assertThat(actual).isEqualTo(bodyData);

        // スタブの呼び出しを検証
        verify(bmiMapper).findAll();

    }

    @Test
    void 存在する名前の頭文字を指定した時に正常に従業員情報が返されること() {
        // Arrange
        String startsWith = "タ";
        List<BodyData> expectedBodyDataList = Arrays.asList(
                new BodyData(1, "タナカ　イチロウ", 20, 171.5, 60.2),
                new BodyData(4, "タカハシ　カズキ", 31, 170.6, 67.8)
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
    public void 存在するIDを指定した時に正常に従業員情報が返されること() {
        // Arrange
        doReturn(Optional.of(new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0))).when(bmiMapper).findById(2);

        // Act
        BodyData actual = bmiService.findId(2);

        // Assert
        assertThat(actual).isEqualTo(new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0));
        assertThat(actual.getBmi()).isEqualTo(26.861206922865602);

        // スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(2);
    }


    @Test
    public void 存在しないIDを指定した時に例外が発生すること() {
        //Arrange
        doReturn(Optional.empty()).when(bmiMapper).findById(100);

        // Assert & Act
        assertThatThrownBy(() -> {
            bmiService.findId(100);
        }).isInstanceOf(DataNotFoundException.class)
                .hasMessage("存在しない従業員 ID です: " + 100);

        // スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(100);

    }


    /**
     * Create処理テスト
     */
    @Test
    public void 正常に新規の従業員情報が登録できること() {
        // Arrange
        BodyData newBodyData = new BodyData("トヨタ トミ", 26, 163.4, 53.3);

        //Act
        doNothing().when(bmiMapper).insert(newBodyData);

        //Assert
        bmiService.insert(newBodyData.getName(), newBodyData.getAge(), newBodyData.getHeight(), newBodyData.getWeight());

        // スタブの呼び出しを検証
        verify(bmiMapper, times(1)).insert(any(BodyData.class));

    }

    @Test
    public void 重複する名前で登録しようとした時に例外が発生すること() {
        // Arrange
        BodyData existingBodyData = new BodyData(1, "トヨタ トミ", 26, 163.4, 53.3);
        doReturn(Optional.of(existingBodyData)).when(bmiMapper).findByName("トヨタ トミ");

        // Assert & Act
        assertThatThrownBy(() -> bmiService.insert("トヨタ トミ", 26, 163.4, 53.3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("同姓同名の従業員が既に存在します");

        // スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findByName("トヨタ トミ");
        verify(bmiMapper, times(0)).insert(any(BodyData.class));
    }


    /**
     * Update処理テスト
     */
    @Test
    public void 従業員情報が正常に更新できること() {
        //Arrange
        BodyData existingBodyData = new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0);
        doReturn(Optional.of(existingBodyData)).when(bmiMapper).findById(2);
        doNothing().when(bmiMapper).update(any(BodyData.class));

        // Act
        BodyData updatedBodyData = bmiService.update(2, "Update User", 35, 184.0, 89.0);

        // Assert
        assertThat(updatedBodyData).isEqualTo(existingBodyData);

        // スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(2);
        verify(bmiMapper, times(1)).update(existingBodyData);

    }

    @Test
    public void 存在しないIDの従業員情報を更新しようとした時に例外が発生すること() {
        // Arrange
        int nonExistentId = 999;
        doReturn(Optional.empty()).when(bmiMapper).findById(nonExistentId);

        // Assert & Act
        assertThatThrownBy(() -> bmiService.update(nonExistentId, "タナカ　イチロウ", 20, 171.5, 60.2))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("存在しない従業員 ID です: " + nonExistentId);

        // スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(nonExistentId);
        verify(bmiMapper, times(0)).update(any(BodyData.class));

    }

    @Test
    public void 更新時に重複する名前でエラーが発生すること() {
        // Arrange
        BodyData existingBodyData = new BodyData(1, "トヨタ トミ", 26, 163.4, 53.3);
        doReturn(Optional.of(existingBodyData)).when(bmiMapper).findById(2);
        doReturn(Optional.of(new BodyData(3, "トヨタ トミ", 30, 170.0, 70.0))).when(bmiMapper).findByName("トヨタ トミ");

        // Assert & Act
        assertThatThrownBy(() -> bmiService.update(2, "トヨタ トミ", 30, 170.0, 70.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("同姓同名の従業員が既に存在します");

        // スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(2);
        verify(bmiMapper, times(1)).findByName("トヨタ トミ");
        verify(bmiMapper, times(0)).update(any(BodyData.class));
    }


    /**
     * Delete処理テスト
     */
    @Test
    public void 指定したIDに紐づいて従業員情報が正常に削除できること() {
        // Arrange
        BodyData existingBodyData = new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0);
        doReturn(Optional.of(existingBodyData)).when(bmiMapper).findById(2);
        doNothing().when(bmiMapper).delete(2);

        // Act
        BodyData actual = bmiService.delete(2);

        // Assert
        assertThat(actual).isEqualTo(existingBodyData);
        verify(bmiMapper, times(1)).findById(2);
        verify(bmiMapper, times(1)).delete(2);
    }

    @Test
    public void 存在しないIDの従業員情報を削除しようとした時に例外が発生すること() {
        // Arrange
        int nonExistentId = 999;
        doReturn(Optional.empty()).when(bmiMapper).findById(nonExistentId);

        // Assert & Act
        assertThatThrownBy(() -> bmiService.delete(nonExistentId))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("存在しない従業員 ID です: " + nonExistentId);

        // スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(nonExistentId);
        verify(bmiMapper, times(0)).delete(nonExistentId);
    }

}
