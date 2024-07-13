package com.health.BMI_check;

import com.health.BMI_check.entity.BodyData;
import com.health.BMI_check.mapper.BmiMapper;
import com.health.BMI_check.service.BmiService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BmiServiceTest {

    @InjectMocks
    BmiService bmiService;

    @Mock
    BmiMapper bmiMapper;

    /**
     * GETテスト
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
    public void 存在する名前の頭文字を指定した時に正常にユーザーが返されること() {
        // Arrange
        List<BodyData> HdBodydata = Arrays.asList(
                new BodyData(1, "タナカ イチロウ", 20, 171.5, 60.2)
        );
        when(bmiMapper.findByNameStartingWith("タ")).thenReturn(HdBodydata);

        // Act
        List<BodyData> actualList = bmiService.findByHeadName("タ");

        // Assert
        assertThat(actualList).isNotNull();
        assertThat(actualList.size()).isEqualTo(1);
        BodyData actual = actualList.get(0);
        assertThat(actual).isEqualToComparingFieldByField(new BodyData(1, "タナカ イチロウ", 20, 171.5, 60.2));
        assertThat(actual.getBmi()).isEqualTo(20.46766228357232);

        //スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findByNameStartingWith("タ");
    }

    @Test
    public void 存在するユーザーのIDを指定したときに正常にユーザーが返されること() {
        //Arrange
        doReturn(Optional.of(new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0))).when(bmiMapper).findById(2);

        //Act
        BodyData actual = bmiService.findName(2);

        //Assert
        assertThat(actual).isEqualToComparingFieldByField(new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0));
        assertThat(actual.getBmi()).isEqualTo(26.861206922865602);

        //スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(2);
    }

    //例外をthrowする場合の検証はどう書くのか　assertThatThrowBy　DoNothing
    @Test
    public void 存在しないIDを指定した場合は例外が発生すること() {
        //Arrange
        doReturn(Optional.empty()).when(bmiMapper).findById(100);

        //Assert
        assertThatThrownBy(() -> {
            bmiService.findName(100);
        }).isInstanceOf(DataNotFoundException.class)
                .hasMessage("Data not found");

        //スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findById(100);

    }

    /**
     * POSTコード
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

}
