package com.six.mysample.springjpaentitygraph.subscription.repository;

import com.six.mysample.springjpaentitygraph.subscription.domain.ReportStatusDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportSubscription;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportSubscriptionRepositoryNoEG extends JpaRepository<ReportSubscription, UUID> {

    ReportSubscription findByBusinessId(@NotNull Integer businessId);
    ReportSubscription findByBusinessIdAndBusinessPartnerReference(@NotNull Integer businessId, String businessPartnerReference);
    List<ReportSubscription> findByBusinessPartnerReference(@NotNull String businessPartnerReference);
    List<ReportSubscription> findByStatus(ReportStatusDto reportStatusDto);

}
