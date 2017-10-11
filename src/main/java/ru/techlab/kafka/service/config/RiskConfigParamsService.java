package ru.techlab.kafka.service.config;

import ru.techlab.kafka.model.rest.LoanQualityCategory;
import ru.techlab.kafka.model.rest.LoanQualityCategoryMatrix;
import ru.techlab.kafka.model.rest.LoanServCoeff;

import java.util.List;

/**
 * Created by rb052775 on 05.10.2017.
 */
public interface RiskConfigParamsService {
    List<LoanQualityCategory> getAllLoanQualityCategories();
    List<LoanServCoeff> getAllLoanServCoeffs();
    List<LoanQualityCategoryMatrix> getAllLoanQualityCategoryMatrix();
}
