package dominik.nadgodziny.domain.overtime;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "Overtime")
@NoArgsConstructor
@EqualsAndHashCode
class OvertimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate creationDate;
    private LocalDate overtimeDate;
    private String status;
    private int duration;


    public OvertimeEntity(LocalDate overtimeDate, String status, int duration) {
        this.creationDate = LocalDate.now();
        this.overtimeDate = overtimeDate;
        this.status = status;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ID " + id + " ||  wpisano " +
                creationDate +
                " ||  data nadgodzin " +
                overtimeDate +
                " ||  rodzaj " +
                status +
                " ||  czas pracy " +
                duration + " godzin ";
    }
}
