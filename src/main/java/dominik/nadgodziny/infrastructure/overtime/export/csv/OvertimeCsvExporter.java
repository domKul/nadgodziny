package dominik.nadgodziny.infrastructure.overtime.export.csv;

import com.opencsv.CSVWriter;
import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
class OvertimeCsvExporter implements CsvConverter {

    private final OvertimeFacade overtimeFacade;

    @Value("${convert.csv.path}")
    private String FILEPATH;

    public void writeOvertimesToCSV() {
        List<OvertimeResponseDto> overtimes = overtimeFacade.findAll();
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILEPATH))) {
            String[] headers = csvHeader();
            writer.writeNext(headers);
            for (OvertimeResponseDto overtime : overtimes) {
                String[] row = writeLine(overtime);
                writer.writeNext(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  String[] writeLine(OvertimeResponseDto overtime) {
        return new String[]{
                String.valueOf(overtime.id()),
                overtime.overtimeDate().toString(),
                String.valueOf(overtime.duration()),
                overtime.status()
        };
    }
}
