package com.health.BMI_check;

import com.health.BMI_check.entity.BodyData;
import com.health.BMI_check.mapper.BmiMapper;
import com.health.BMI_check.service.BmiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BmiServiceTest {

    @InjectMocks
    BmiService bmiService;

    @Mock
    BmiMapper bmiMapper;

    @Test
    public void 存在するユーザーのIDを指定したときに正常にユーザーが返されること() throws Exception {
        //Arrange スタブする Mapper の動きを決める
        doReturn(Optional.of(new BodyData(1, "タナカ　イチロウ", 20, 171.5, 60.2))).when(bmiMapper).findById(1);

        //act
        BodyData actual = bmiService.findName(1);

        //assert
        assertThat(actual).isEqualToComparingFieldByField(new BodyData(1, "タナカ　イチロウ", 20, 171.5, 60.2));
        assertThat(actual.getBmi()).isEqualTo(20.46766228357232);

        //スタブの呼び出しを検証
        verify(bmiMapper, Mockito.times(1)).findById(1);
    }

    //例外をthrowする場合の検証はどう書くのか　assertThatThrowBy　DoNothing
    @Test
    public void 存在しないIDを指定した場合は例外が発生すること() {
        doReturn(Optional.empty()).when(bmiMapper).findById(1);
        assertThatThrownBy(() -> {
            bmiService.findName(1);
        }).isInstanceOf(DataNotFoundException.class)
                .hasMessage("Data not found");
    }

}