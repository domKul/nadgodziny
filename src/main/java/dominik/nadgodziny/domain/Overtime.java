package dominik.nadgodziny.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
class Overtime {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private LocalDate creationDate;
    private LocalDate overtimeDate;
    private String status;
    private int duration;

    public Overtime() {
    }

    public Overtime(LocalDate overtimeDate, String status, int duration) {
        this.creationDate = LocalDate.now();
        this.overtimeDate = overtimeDate;
        this.status = status;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getOvertimeDate() {
        return overtimeDate;
    }

    public void setOvertimeDate(LocalDate overtimeDate) {
        this.overtimeDate = overtimeDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Overtime overtime = (Overtime) o;
        return id == overtime.id && duration == overtime.duration && Objects.equals(creationDate, overtime.creationDate) && Objects.equals(overtimeDate, overtime.overtimeDate) && Objects.equals(status, overtime.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, overtimeDate, status, duration);
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
