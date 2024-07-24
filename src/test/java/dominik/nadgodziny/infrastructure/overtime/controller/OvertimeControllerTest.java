package dominik.nadgodziny.infrastructure.overtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class OvertimeControllerTest extends OvertimesExamples {

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
        perform.andExpect(MockMvcResultMatchers.status().isOk());
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
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
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
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }
}
