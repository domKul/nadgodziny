package dominik.nadgodziny.domain.overtime;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryDBForTests implements OvertimeRepository {

    Map<Long, OvertimeEntity> database = new ConcurrentHashMap<>();

    @Override
    public void flush() {

    }

    @Override
    public <S extends OvertimeEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends OvertimeEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<OvertimeEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public OvertimeEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public OvertimeEntity getById(Long aLong) {
        return null;
    }

    @Override
    public OvertimeEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends OvertimeEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends OvertimeEntity> List<S> findAll(Example<S> example) {
        if (Objects.nonNull(example)) {
            return (List<S>) example;
        }
        return Collections.emptyList();
    }


    @Override
    public <S extends OvertimeEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends OvertimeEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends OvertimeEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends OvertimeEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends OvertimeEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends OvertimeEntity> S save(S entity) {
        Random random = new Random();
        long id = random.nextLong(0, 99);
        OvertimeEntity overtime = new OvertimeEntity(entity.getOvertimeDate(),
                entity.getStatus(),
                entity.getDuration());
        overtime.setId(id);
        OvertimeEntity result = database.put(id, overtime);
        return (S) result;
    }

    @Override
    public <S extends OvertimeEntity> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    public Optional<OvertimeEntity> findById(Long aLong) {
        return Optional.ofNullable(database.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        OvertimeEntity overtimeEntity = database.get(aLong);
        return Objects.nonNull(overtimeEntity);
    }

    @Override
    public List<OvertimeEntity> findAll() {
        return database.values().stream()
                .toList();
    }

    @Override
    public List<OvertimeEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        database.remove(aLong);
    }

    @Override
    public void delete(OvertimeEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends OvertimeEntity> entities) {

    }

    @Override
    public void deleteAll() {
        database.clear();
    }

    @Override
    public List<OvertimeEntity> findAll(Sort sort) {
        return database.values().stream().toList();
    }

    @Override
    public Page<OvertimeEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<OvertimeEntity> findAllByYearAndStatus(int year, String status) {
       return database.values().stream()
                .filter(overtimeEntity -> overtimeEntity.getOvertimeDate().getYear() == year)
                .filter(overtimeEntity -> overtimeEntity.getStatus().equals(status))
                .toList();
    }

    @Override
    public List<OvertimeEntity> findAllByYearAndMonth(int year, int month) {
       return database.values().stream()
                .filter(overtimeEntity -> overtimeEntity.getOvertimeDate().getYear() == year)
                .filter(overtimeEntity -> overtimeEntity.getOvertimeDate().getMonthValue() == month)
                .toList();
    }
}
