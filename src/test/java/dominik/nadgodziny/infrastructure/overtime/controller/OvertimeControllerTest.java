package dominik.nadgodziny.infrastructure.overtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dominik.nadgodziny.domain.overtime.dto.OvertimeCreateDto;
import dominik.nadgodziny.domain.overtime.dto.OvertimeStatisticsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OvertimeControllerTest extends OvertimesDataExamples {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Add initial data to the database
        overtimesLoopAdder(twoOvertimesExamplesWithStatusZlecenie());
        overtimesLoopAdder(twoOvertimesExamplesWithStatusNadgodziny());
    }


    @Test
    void findAllOvertimesReturnsListOfOvertimes() throws Exception {
        //Given & When
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/overtimes")
                .contentType(MediaType.APPLICATION_JSON));
        //Then
        perform.andExpect(status().isOk());
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(4));
    }

    @Test
    void shouldFindOvertimesByStatusReturnsFilteredOvertimes() throws Exception {
        //Given &  When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/overtimes/status")
                .queryParam("year", "2024")
                .queryParam("status", "zlecenie")
                .contentType(MediaType.APPLICATION_JSON));
        //Then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(0));
    }

    @Test
    void shouldFindOvertimesByStatusReturnsFilteredOvertimesAndFindTwo() throws Exception {
        //Given &  When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/overtimes/status")
                .queryParam("year", "2023")
                .queryParam("status", "zlecenie")
                .contentType(MediaType.APPLICATION_JSON));
        //Then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

    @Test
    void shouldAddOvertimeSuccessfullyToDb() throws Exception {
        //Given
        OvertimeCreateDto overtimeCreateDto = new OvertimeCreateDto(LocalDate.now(),"nadgodziny",8);

        //When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/overtimes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(overtimeCreateDto)));

        //Then
        String contentAsString = result.andReturn().getResponse().getContentAsString();
        result.andExpect(status().isCreated());
        assertAll(
                ()->assertThat(contentAsString).contains(LocalDate.now().toString()),
                ()->assertThat(contentAsString).contains("nadgodziny"),
                ()->assertThat(contentAsString).contains("8")
        );
    }

    @Test
    void shouldFindStatisticsSuccessfullyFromDb() throws Exception {
        // Given
        OvertimeStatisticsDto expectedStatistics = new OvertimeStatisticsDto(Map.of(2023, 32));

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/overtimes/statistics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        String contentAsString = response.andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
        ObjectMapper objectMapper = new ObjectMapper();
        OvertimeStatisticsDto actualStatistics = objectMapper.readValue(contentAsString, OvertimeStatisticsDto.class);
        assertAll(
                ()-> assertThat(actualStatistics.stats()).isEqualTo(expectedStatistics.stats()),
                ()-> assertThat(actualStatistics.stats().containsKey(2023)).isTrue(),
                ()-> assertThat(actualStatistics.stats().containsValue(32)).isTrue()
        );
    }

}
