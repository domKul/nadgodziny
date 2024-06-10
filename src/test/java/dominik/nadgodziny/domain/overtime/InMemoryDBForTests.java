package dominik.nadgodziny.domain.overtime;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryDBForTests implements OvertimeRepository {

    Map<Long, Overtime> database = new ConcurrentHashMap<>();

    @Override
    public void flush() {

    }

    @Override
    public <S extends Overtime> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Overtime> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Overtime> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Overtime getOne(Long aLong) {
        return null;
    }

    @Override
    public Overtime getById(Long aLong) {
        return null;
    }

    @Override
    public Overtime getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Overtime> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Overtime> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Overtime> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Overtime> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Overtime> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Overtime> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Overtime, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Overtime> S save(S entity) {
        Random random = new Random();
        long id = random.nextLong(0, 99);
        Overtime overtime = new Overtime(entity.getOvertimeDate(),
                entity.getStatus(),
                entity.getDuration());
        overtime.setId(id);
        Overtime result = database.put(id, overtime);
        return (S) result;
    }

    @Override
    public <S extends Overtime> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Overtime> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Overtime> findAll() {
        return database.values().stream()
                .toList();
    }

    @Override
    public List<Overtime> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Overtime entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Overtime> entities) {

    }

    @Override
    public void deleteAll() {
        database.clear();
    }

    @Override
    public List<Overtime> findAll(Sort sort) {
        return database.values().stream().toList();
    }

    @Override
    public Page<Overtime> findAll(Pageable pageable) {
        return null;
    }
}
