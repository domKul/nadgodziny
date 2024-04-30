package dominik.nadgodziny.domain;

import java.util.List;

class OvertimeFacadeFetchImpl implements OvertimeReader {
    public OvertimeFacadeFetchImpl(List<Overtime> overtimes) {
        this.overtimes = overtimes;
    }

    List<Overtime> overtimes;
    @Override
    public List<Overtime> findAllOvertimes() {
        return overtimes;
    }

    @Override
    public List<Overtime> findOvertimeByMonth(int month) {
        return overtimes.stream()
                .filter(o->o.getOvertimeDate().getMonthValue() == month)
                .toList();
    }

    @Override
    public int getSumOfAllOvertimeHoursByMonth(int month) {
        return 0;
    }


    @Override
    public int getSumOfHoursByGivenStatusOfGivenMonth(int month,String status) {
        return 0;
    }
}
