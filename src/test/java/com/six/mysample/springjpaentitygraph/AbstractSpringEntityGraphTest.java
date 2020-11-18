package com.six.mysample.springjpaentitygraph;

import static org.assertj.core.api.Assertions.assertThat;

import com.six.mysample.springjpaentitygraph.configuration.DomainConfiguration;
import com.six.mysample.springjpaentitygraph.order.domain.Order;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportParameterDefinition;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportParameterTypeDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportSubscription;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportType;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(DomainConfiguration.class)
@Slf4j
public class AbstractSpringEntityGraphTest {

    protected void assertReportType(ReportType rt) {
        Assertions.assertThat(rt.getAllowedFormatChannelLinks()).isNotEmpty();
        Assertions.assertThat(rt.getReportParameterDefinitions()).isNotEmpty();
        Assertions.assertThat(rt.getReportTypeFrequencies()).isNotEmpty();

        rt.getAllowedFormatChannelLinks().forEach(s -> log.info(s.toString()));

        Optional<ReportParameterDefinition> rpc = rt
                .getReportParameterDefinitions().stream()
                .filter(reportParameterDefinition -> reportParameterDefinition.getParameterType().equals(
                        ReportParameterTypeDto.DATE)).findFirst();

        if (rpc.isPresent()) {
            Assertions.assertThat(rpc.get().getReportParameterConstraints().stream().findFirst().get().getId()).isNotNull();
        }
    }

    protected void assertReportSubcription(ReportSubscription rs) {
        Assertions.assertThat(rs.getSubscribedFormatChannelLinks()).isNotEmpty();
        Assertions.assertThat(rs.getSubscriptionParameters()).isNotEmpty();
        Assertions.assertThat(rs.getReportType().getId()).isNotNull();

        rs.getSubscribedFormatChannelLinks().forEach(subscribedFormatChannelLink -> {
                    log.debug(subscribedFormatChannelLink.getSFormatChannelLink().getChannel() + ", " + subscribedFormatChannelLink
                            .getSFormatChannelLink().getFormat());
                    Assertions.assertThat(subscribedFormatChannelLink.getSFormatChannelLink().getChannel()).isNotNull();
                }
        );

        assertReportType(rs.getReportType());
    }

    protected void assertOrder(Order order) {

        assertThat(order.getOrderSteps()).isNotEmpty();

        order.getOrderSteps().forEach(orderStep -> {
            assertThat(orderStep.getOrderInstructions()).isNotEmpty();
            assertThat(orderStep.getOrderStepStatuses()).isNotEmpty();
            assertThat(orderStep.getOrderArtifacts()).isNotEmpty();

            orderStep.getOrderInstructions().forEach(orderInstruction -> {
                assertThat(orderInstruction.getOrderParameterValues()).isNotEmpty();
            });

        });
        assertReportType(order.getReportType());
        assertReportSubcription(order.getReportSubscription());
    }
}
