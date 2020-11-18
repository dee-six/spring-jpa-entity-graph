package com.six.mysample.springjpaentitygraph.order.repository;

import com.six.mysample.springjpaentitygraph.order.domain.Order;
import com.six.mysample.springjpaentitygraph.order.domain.OrderStatusDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepositoryEG extends JpaRepository<Order, UUID> {

    @EntityGraph(value = "order.detail", type = EntityGraphType.LOAD)
    Order findByBusinessId(@NotNull Integer businessId);

    @EntityGraph(value = "order.detail", type = EntityGraphType.LOAD)
    List<Order> findAll();

    @EntityGraph(value = "order.detail", type = EntityGraphType.LOAD)
    Order findByBusinessPartnerReferenceAndBusinessId(@NotNull String businessPartnerReference, @NotNull Integer businessId);

    /**
     * EntityGraps not required. Just load orders
     * @param artifactFileName
     * @return
     */
    @Query(value = "SELECT o FROM Order o JOIN o.orderSteps os JOIN os.orderArtifacts oa WHERE oa.artifactFileName = ?1")
    List<Order> findByArtifactFileName(@NotNull String artifactFileName);

    /**
     * EntityGraps not required. Just load orders
     * @return
     */
    List<Order> findAllByOrderStatusDtoIsIn(List<OrderStatusDto> orderStatusDtos);

    @EntityGraph(value = "order.detail", type = EntityGraphType.LOAD)
    List<Order> findByBusinessPartnerReferenceOrderByIdDesc(@NotNull String businessPartnerReference);

    @EntityGraph(value = "order.detail", type = EntityGraphType.LOAD)
    Optional<Order> findById(UUID uuid);
}
