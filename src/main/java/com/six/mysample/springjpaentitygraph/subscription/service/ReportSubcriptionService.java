package com.six.mysample.springjpaentitygraph.subscription.service;

import com.six.mysample.springjpaentitygraph.subscription.domain.ReportSubscription;
import com.six.mysample.springjpaentitygraph.subscription.repository.ReportSubscriptionRepositoryEG;
import com.six.mysample.springjpaentitygraph.subscription.repository.ReportSubscriptionRepositoryNoEG;
import com.six.mysample.springjpaentitygraph.subscription.repository.ReportTypeRepositoryEG;
import com.six.mysample.springjpaentitygraph.subscription.repository.ReportTypeRepositoryNoEG;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ReportSubcriptionService {

    ReportSubscriptionRepositoryEG reportSubscriptionRepositoryEG;
    ReportTypeRepositoryEG reportTypeRepositoryEG;
    ReportSubscriptionRepositoryNoEG reportSubscriptionRepositoryNoEG;
    ReportTypeRepositoryNoEG reportTypeRepositoryNoEG;

    public ReportSubscription getbyIdEG(String id) {
        ReportSubscription rs = reportSubscriptionRepositoryEG.findByBusinessId(Integer.valueOf(id));
        //reportTypeRepositoryEG.findByBusinessId(rs.getReportType().getBusinessId());
        return rs;
    }

    public ReportSubscription getbyIdNoEG(String id) {
        return loadReportTypeFull(reportSubscriptionRepositoryNoEG.findByBusinessId(Integer.valueOf(id)));
    }

    private ReportSubscription loadReportTypeFull(ReportSubscription rs) {
        rs.getSubscribedFormatChannelLinks().size();
        rs.getSubscriptionParameters().size();
        rs.getReportType();

        rs.getReportType().getAllowedFormatChannelLinks().size();
        rs.getReportType().getReportParameterDefinitions().size();
        rs.getReportType().getReportTypeFrequencies().size();
        rs.getReportType().getReportParameterDefinitions().forEach(reportParameterDefinition -> reportParameterDefinition.getReportParameterConstraints().size());
        return rs;
    }

}
