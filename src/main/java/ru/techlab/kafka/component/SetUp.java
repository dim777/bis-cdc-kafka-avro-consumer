package ru.techlab.kafka.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.techlab.kafka.repository.LoanQualityResultRepository;
import ru.techlab.kafka.service.config.ConfigService;
import ru.techlab.kafka.service.config.RiskConfigParamsService;

import javax.annotation.PostConstruct;

/**
 * Created by rb052775 on 06.10.2017.
 */
@Component
public class SetUp {
    @Autowired
    private RiskConfigParamsService riskConfigParamsService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private AppCache appCache;
    @Autowired
    private LoanQualityResultRepository loanQualityResultRepository;

    @PostConstruct
    public void setUpParams(){
        appCache.setVar("BASE_CONFIG", configService.getBaseConfig());
        appCache.setVar("LOAN_QUALITY_CATEGORIES", riskConfigParamsService.getAllLoanQualityCategories());
        appCache.setVar("LOAN_SERV_COEFF", riskConfigParamsService.getAllLoanServCoeffs());
        appCache.setVar("LOAN_QUALITY_CATEGORY_MATRIX", riskConfigParamsService.getAllLoanQualityCategoryMatrix());
        appCache.setVar("LOAN_QUALITY_RESULT_REPOSITORY", loanQualityResultRepository.findAllReturnList());
    }
}
