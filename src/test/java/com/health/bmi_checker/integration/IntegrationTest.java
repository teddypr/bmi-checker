package com.health.bmi_checker.integration;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class IntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Nested
    class ReadClass {

        @Test
        @DataSet(value = "datasets/bodydata.yml")
        void 全ての従業員情報が正常に返されること() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/BMIs")).andExpect(MockMvcResultMatchers.status().isOk())
                    //レスポンスボディの検証
                    .andExpect(MockMvcResultMatchers.content().json("""
                            [
                                {
                                    "id": 1,
                                    "name": "タナカ　イチロウ",
                                    "age": 20,
                                    "height": 171.5,
                                    "weight": 60.2,
                                    "bmi": 20.46766228357232
                                },
                                {
                                    "id": 2,
                                    "name": "スズキ　ジロウ",
                                    "age": 18,
                                    "height": 181.0,
                                    "weight": 88.0,
                                    "bmi": 26.861206922865602
                                },
                                {
                                    "id": 3,
                                    "name": "ムラタ　サブロウ",
                                    "age": 26,
                                    "height": 167.9,
                                    "weight": 101.3,
                                    "bmi": 35.9342059941661
                                },
                                {
                                    "id": 4,
                                    "name": "タカハシ　カズキ",
                                    "age": 31,
                                    "height": 170.6,
                                    "weight": 67.8,
                                    "bmi": 23.295478753011576
                                }
                            ]
                            """));

        }

        @Test
        @DataSet(value = "datasets/bodydata.yml")
        void 指定した名前の頭文字で従業員情報が正常に返されること() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/userNames?startsWith=タ")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json("""
                    [
                        {
                            "id": 4,
                            "name": "タカハシ　カズキ",
                            "age": 31,
                            "height": 170.6,
                            "weight": 67.8,
                            "bmi": 23.295478753011576
                        },
                        {
                            "id": 1,
                            "name": "タナカ　イチロウ",
                            "age": 20,
                            "height": 171.5,
                            "weight": 60.2,
                            "bmi": 20.46766228357232
                        }
                    ]
                    """));
        }

        @Test
        @DataSet(value = "datasets/bodydata.yml")
        @Transactional
        void 存在しない名前を指定したとき404エラーを返すこと() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/userNames?startsWith=ン"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("該当する従業員は存在しません"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/userNames"));
        }

        @Test
        @DataSet(value = "datasets/bodydata.yml")
        void 指定したIDの従業員情報を取得できること() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/userNames/1")).andExpect(MockMvcResultMatchers.status().isOk())

                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                "id": 1,
                                "name": "タナカ　イチロウ",
                                "age": 20,
                                "height": 171.5,
                                "weight": 60.2,
                                "bmi": 20.46766228357232
                            }
                            """));
        }

        @Test
        @DataSet(value = "datasets/bodydata.yml")
        @Transactional
        void 存在しないIDを指定したとき404エラーを返すこと() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.get("/userNames/0"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("存在しない従業員 ID です: " + 0))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/userNames/0"));

        }
    }

    @Nested
    class CreateClass {
        @Test
        @DataSet(value = "datasets/bodydata.yml")
        @ExpectedDataSet(value = "datasets/expectedInsertBodyData.yml", ignoreCols = "id")
        void 新規従業員情報が正常に挿入できること() throws Exception {
            String requestBody = """
                    {
                            "name": "トヨタ トミ",
                            "age": 26,
                            "height": 163.4,
                            "weight": 53.3
                    }
                    """;
            mockMvc.perform(MockMvcRequestBuilders.post("/BMIs")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                "message": "従業員情報　登録完了"
                            }
                            """));
        }

        @Test
        @DataSet(value = "datasets/bodydata.yml")
        void 不正なフォーマットで登録しようとしたとき400エラーを返すこと() throws Exception {
            String exceptionRequestBody = """
                    {
                            "name": "",
                            "age": "",
                            "height": 16345.4444,
                            "weight": ""
                    }
                    """;
            mockMvc.perform(MockMvcRequestBuilders.post("/BMIs")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(exceptionRequestBody))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json("""
                                            {
                                              "name": "名前は必須項目です",
                                              "weight": "体重はキログラム単位で正の数でなければなりません",
                                              "age": "年齢は正の整数でなければなりません",
                                              "height": "身長は小数点第 1 位までを入力してください"
                                            }
                            """));
        }

        @Test
        void 同姓同名の従業員が登録されたとき409エラーを返すこと() throws Exception {
            // テストデータの準備（同姓同名の従業員）
            String duplicateRequestBody = """
                    {
                            "name": "ヤマダ　タロウ",
                            "age": 34,
                            "height": 175.5,
                            "weight": 73
                    }
                    """;

            // 最初の登録
            mockMvc.perform(MockMvcRequestBuilders.post("/BMIs")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(duplicateRequestBody))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

            // 同じデータで再度登録
            mockMvc.perform(MockMvcRequestBuilders.post("/BMIs")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(duplicateRequestBody))
                    .andExpect(MockMvcResultMatchers.status().isConflict())
                    .andExpect(MockMvcResultMatchers.content().json("""
                                    {
                                        "message": "同姓同名の従業員が既に存在します"
                                    }
                            """));
        }

    }

    @Nested
    class UpdateClass {
        @Test
        @DataSet(value = "datasets/bodydata.yml")
        @ExpectedDataSet(value = "datasets/expectedUpdatedBodyData.yml")
        void 従業員情報が正常に更新できること() throws Exception {
            String requestBody = """
                    {
                            "name": "Updated Name",
                            "age": 18,
                            "height": 181.0,
                            "weight": 88.0
                    }
                    """;
            mockMvc.perform(MockMvcRequestBuilders.patch("/BMIs/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                "message": "従業員情報　更新完了"
                            }
                            """));
        }

        @Test
        @DataSet(value = "datasets/bodydata.yml")
        @Transactional
        void 存在しないIDでユーザーの更新をしようとすると404エラーを返すこと() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/BMIs/100"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("存在しない従業員 ID です: " + 100))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/BMIs/100"));

        }
    }

    @Nested
    class DeleteClass {
        @Test
        @DataSet(value = "datasets/bodydata.yml")
        @ExpectedDataSet(value = "datasets/expectedDeletedBodyData.yml")
        void 指定したIDの従業員情報が正常に削除できること() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/BMIs/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                "message": "従業員情報　削除完了"
                            }
                            """));
        }

        @Test
        @DataSet(value = "datasets/bodydata.yml")
        void 存在しないIDで削除を行おうとしたとき404エラーを返すこと() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/BMIs/0"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("存在しない従業員 ID です: " + 0))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/BMIs/0"));
        }
    }

}

