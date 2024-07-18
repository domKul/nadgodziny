package dominik.nadgodziny.infrastructure.overtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(OvertimeController.class)
@AutoConfigureMockMvc
public class OvertimeControllerTest extends OvertimesExamples{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OvertimeFacade overtimeFacade;


    @BeforeEach
    void setUp() {
        twoOvertimesExamplesWithStatusZlecenie();
    }

    @Test
    void findAllOvertimes_ReturnsListOfOvertimes() throws Exception {
        //Given
        when(overtimeFacade.findAll()).thenReturn(mockOvertimeResponseDtoList);

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/overtimes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));

        //Then
    }

    @Test
    void findOvertimesByStatus_ReturnsFilteredOvertimes() throws Exception {
        //Given
        when(overtimeFacade.findByStatusAndYear(anyInt(), anyString())).thenReturn(mockOvertimeResponseDtoList);

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/overtimes/status")
                        .queryParam("year", "2023")
                        .queryParam("status", "zlecenie")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));

        //Then
    }
}
