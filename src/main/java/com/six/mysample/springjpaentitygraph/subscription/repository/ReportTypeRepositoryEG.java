package com.six.mysample.springjpaentitygraph.subscription.repository;

import com.six.mysample.springjpaentitygraph.subscription.domain.ReportType;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTypeRepositoryEG extends JpaRepository<ReportType, UUID> {

    @EntityGraph(value = "reportType.allowedFormatChannel", type = EntityGraphType.LOAD)
    ReportType findByBusinessId(@NotNull Integer businesId);

    @EntityGraph(value = "reportType.allowedFormatChannel", type = EntityGraphType.LOAD)
    List<ReportType> findAll();
}