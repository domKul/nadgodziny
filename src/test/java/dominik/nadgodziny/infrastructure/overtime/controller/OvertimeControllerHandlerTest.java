package dominik.nadgodziny.infrastructure.overtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dominik.nadgodziny.domain.overtime.dto.OvertimeCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OvertimeControllerHandlerTest extends OvertimesDataExamples {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void shouldHandleOvertimeNotFound() throws Exception {
        //Given
        String notFoundMessage = "Data Not Found";

        //When && Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/overtimes"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(notFoundMessage));
    }


    @Test
    void shouldHandleMissingYearQueryParam() throws Exception {
        //Given
        String expectedResponse = "Required request parameter 'year' for method parameter type int is not present";

        //  When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/overtimes/status")
                .queryParam("status", "zlecenie")
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        resultActions.andExpect(status().isBadRequest());
        assertThat(contentAsString).contains(expectedResponse);
    }

    @Test
    void shouldHandleMissingStatusQueryParam() throws Exception {
        //Given
        String expectedResponse = "Required request parameter 'status' for method parameter type String is not present";

        //  When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/overtimes/status")
                .queryParam("year", "2024")
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        resultActions.andExpect(status().isBadRequest());
        assertThat(contentAsString).contains(expectedResponse);
    }

    @Test
    void shouldHandleOvertimeValidation() throws Exception {
        //Given
        OvertimeCreateDto overtimeCreateDto = new OvertimeCreateDto(null, null, -4);
        String expectedResponse =  "{\"duration\":\"duration must be a positive number\"," +
                "\"overtimeDate\":\"date must not be null\"," +
                "\"status\":\"status must not be null\"}";

        //When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/overtimes")
                .content(objectMapper.writeValueAsString(overtimeCreateDto))
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(contentAsString).contains(expectedResponse);
    }


}
