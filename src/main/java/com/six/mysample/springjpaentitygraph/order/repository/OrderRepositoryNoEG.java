package com.six.mysample.springjpaentitygraph.order.repository;

import com.six.mysample.springjpaentitygraph.order.domain.Order;
import com.six.mysample.springjpaentitygraph.order.domain.OrderStatusDto;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryNoEG extends JpaRepository<Order, UUID> {

	Order findByBusinessId(@NotNull Integer businessId);
	Order findByBusinessPartnerReferenceAndBusinessId(@NotNull String businessPartnerReference, @NotNull Integer businessId);

	@Query(value = "SELECT o FROM Order o JOIN o.orderSteps os JOIN os.orderArtifacts oa WHERE oa.artifactFileName = ?1")
	List<Order> findByArtifactFileName(@NotNull String artifactFileName);

	List<Order> findAllByOrderStatusDtoIsIn(List<OrderStatusDto> orderStatusDtos);

	List<Order> findByBusinessPartnerReferenceOrderByIdDesc(@NotNull String businessPartnerReference);
}
