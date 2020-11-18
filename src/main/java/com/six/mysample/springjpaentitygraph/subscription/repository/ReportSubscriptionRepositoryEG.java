package com.six.mysample.springjpaentitygraph.subscription.repository;

import com.six.mysample.springjpaentitygraph.subscription.domain.ReportStatusDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportSubscription;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportSubscriptionRepositoryEG extends JpaRepository<ReportSubscription, UUID> {

    @EntityGraph(value = "reportSubscription.all", type = EntityGraphType.LOAD)
    ReportSubscription findByBusinessId(@NotNull Integer businessId);

    @EntityGraph(value = "reportSubscription.all", type = EntityGraphType.LOAD)
    ReportSubscription findByBusinessIdAndBusinessPartnerReference(@NotNull Integer businessId, String businessPartnerReference);

    @EntityGraph(value = "reportSubscription.all", type = EntityGraphType.LOAD)
    List<ReportSubscription> findByBusinessPartnerReference(@NotNull String businessPartnerReference);

    @EntityGraph(value = "reportSubscription.all", type = EntityGraphType.LOAD)
    List<ReportSubscription> findByStatus(ReportStatusDto reportStatusDto);

}
