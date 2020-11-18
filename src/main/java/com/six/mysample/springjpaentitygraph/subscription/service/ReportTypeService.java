package com.six.mysample.springjpaentitygraph.subscription.service;

import com.six.mysample.springjpaentitygraph.subscription.domain.ReportType;
import com.six.mysample.springjpaentitygraph.subscription.repository.ReportTypeRepositoryEG;
import com.six.mysample.springjpaentitygraph.subscription.repository.ReportTypeRepositoryNoEG;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provide a description of the class
 * <p>
 * Created by tkr0d on 19.07.2017.
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ReportTypeService {

    @Autowired
    private ReportTypeRepositoryEG _reportTypeRepository;

    private ReportTypeRepositoryNoEG reportTypeRepositoryWitoutEG;

    public ReportType getTypeByIdEG(String reportTypeId) {
        ReportType rt = _reportTypeRepository.findByBusinessId(Integer.valueOf(reportTypeId));
        log.debug("report Type is loaded EG");
        return rt;
    }

    public ReportType getTypeByIdNoEG(String reportTypeId) {
        ReportType rt = reportTypeRepositoryWitoutEG.findByBusinessId(Integer.valueOf(reportTypeId));

        return loadReportTypeFull(rt);
    }

    private ReportType loadReportTypeFull(ReportType rt) {
        rt.getAllowedFormatChannelLinks().size();
        rt.getReportTypeFrequencies().size();
        rt.getReportParameterDefinitions().size();

        rt.getReportParameterDefinitions()
                .forEach(reportParameterDefinition -> reportParameterDefinition.getReportParameterConstraints().size());

        return rt;
    }

    public List<ReportType> getAllReportTypes() {
        List<ReportType> rts = _reportTypeRepository.findAll();
        return rts.stream().map(this::loadReportTypeFull).collect(Collectors.toList());
    }

    public List<ReportType> getAllReportTypesWithoutEG() {
        List<ReportType> rts = reportTypeRepositoryWitoutEG.findAll();
        return rts.stream().map(this::loadReportTypeFull).collect(Collectors.toList());
    }
}
