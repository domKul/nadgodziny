package dominik.nadgodziny.domain.overtime;

import java.util.List;

class OvertimeFacadeFetchImpl implements OvertimeReader {
    public OvertimeFacadeFetchImpl(List<OvertimeEntity> overtimes) {
        this.overtimes = overtimes;
    }

    List<OvertimeEntity> overtimes;
    public List<OvertimeEntity> findAllOvertimes() {
        return overtimes;
    }

    @Override
    public List<OvertimeEntity> findAllOvertimesByStatus(int year, String status) {
        return null;
    }

    @Override
    public List<OvertimeEntity> findOvertimeByMonthAndYear(int year, int month) {
        return overtimes.stream()
                .filter(o -> o.getOvertimeDate().getYear() == year)
                .filter(o->o.getOvertimeDate().getMonthValue() == month)
                .toList();
    }

    @Override
    public int sumOfAllOvertimeHoursByMonth(int year, int month) {
        return 0;
    }


    @Override
    public int sumOfHoursByGivenStatusOfGivenMonthAndGivenYear(int year, int month, String status) {
        return 0;
    }

    @Override
    public List<OvertimeEntity> sortAllOvertimesById() {
        return null;
    }


}
