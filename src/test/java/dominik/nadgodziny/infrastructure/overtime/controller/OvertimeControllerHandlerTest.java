package dominik.nadgodziny.infrastructure.overtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OvertimeControllerHandlerTest extends OvertimesExamples{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldHandleOvertimeNotFoundException() throws Exception {
        //Given
        String notFoundMessage = "Data Not Found";


        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/overtimes"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(notFoundMessage));


       //Then
    }
}
