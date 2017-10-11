package ru.techlab.kafka.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import ru.techlab.kafka.model.LoanQualityResult;

import java.util.stream.Stream;

/**
 * Created by dim777 on 05.10.17.
 */
@Repository
public interface LoanQualityResultRepository extends CassandraRepository<LoanQualityResult> {
    @Query("select l from LoanQualityResult l")
    Stream<LoanQualityResult> findAllReturnStream();
}
