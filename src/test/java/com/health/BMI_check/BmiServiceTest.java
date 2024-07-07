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

    @Test
    public void 全てのユーザーが正常に返されること() throws Exception {
        //Arrange
        List<BodyData> mockBodyDataList = Arrays.asList(
                new BodyData(1, "タナカ　イチロウ", 20, 171.5, 60.2),
                new BodyData(2, "スズキ　ジロウ", 18, 181.0, 88.0),
                new BodyData(3, "ムラタ　サブロウ", 26, 167.9, 101.3)
        );

        when(bmiMapper.findAll()).thenReturn(mockBodyDataList);

        //Act
        List<BodyData> bodyDataList = bmiService.findAll();

        //Assert
        assertThat(bodyDataList).isNotNull();
        assertThat(bodyDataList.size()).isEqualTo(3);

        //スタブの呼び出しを検証
        verify(bmiMapper, times(1)).findAll();

    }

    @Test
    public void 存在する名前の頭文字を指定した時に正常にユーザーが返されること() {
        //Assert
        String query = "タ";
        List<BodyData> mockBodyDataList = Arrays.asList(
                new BodyData(1, "タナカ イチロウ", 20, 171.5, 60.2)
        );

        when(bmiMapper.findByNameStartingWith(query)).thenReturn(mockBodyDataList);

        //Act
        List<BodyData> bodyDataList = bmiService.findByNamesStartingWith(query);

        //Assert
        assertThat(bodyDataList).isNotNull();
        assertThat(bodyDataList.size()).isEqualTo(1);
        assertThat(bodyDataList.get(0).getName()).isEqualTo("タナカ イチロウ");

        verify(bmiMapper, times(1)).findByNameStartingWith(query);
    }


    @Test
    public void 存在するユーザーのIDを指定したときに正常にユーザーが返されること() throws Exception {
        //Arrange スタブする Mapper の動きを決める
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
    public void 存在しないIDを指定した場合は例外が発生すること() throws DataNotFoundException {
        doReturn(Optional.empty()).when(bmiMapper).findById(1);
        assertThatThrownBy(() -> {
            bmiService.findName(1);
        }).isInstanceOf(DataNotFoundException.class)
                .hasMessage("Data not found");
    }

}