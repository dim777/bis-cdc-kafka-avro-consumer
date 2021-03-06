package ru.techlab.kafka.service.quality;

import org.joda.time.LocalDateTime;
import ru.techlab.kafka.model.BaseLoan;
import ru.techlab.kafka.model.rest.LoanQualityCategory;
import ru.xegex.risks.libs.ex.customer.CustomerNotFoundEx;
import ru.xegex.risks.libs.ex.loans.LoanServCoeffNotFoundEx;
import ru.xegex.risks.libs.ex.quality.QualityConvertionEx;
import ru.xegex.risks.libs.model.customer.FinStateType;
import ru.xegex.risks.libs.model.loan.LoanServCoeffType;

/**
 * Created by rb052775 on 27.09.2017.
 */
public interface QualityService {
    LoanServCoeffType calculateLoanServCoeff(BaseLoan loan, LocalDateTime localDateTime) throws LoanServCoeffNotFoundEx, CustomerNotFoundEx;
    LoanQualityCategory calculateLoanQualityCategory(FinStateType finStateType, LoanServCoeffType loanServCoeffType) throws QualityConvertionEx;
}
