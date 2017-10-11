package ru.techlab.kafka.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import ru.techlab.kafka.model.BaseCustomer;
import ru.techlab.kafka.model.LoanQualityResult;
import ru.techlab.kafka.model.customer.AvroCustomer;
import ru.techlab.kafka.model.customer.JsonCustomer;
import ru.techlab.kafka.model.rest.LoanQualityCategory;
import ru.techlab.kafka.repository.LoanQualityResultRepository;
import ru.techlab.kafka.service.config.ConfigService;
import ru.techlab.kafka.service.config.RiskConfigParamsService;
import ru.techlab.kafka.service.quality.QualityService;
import ru.xegex.risks.libs.ex.quality.QualityConvertionEx;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * Created by rb052775 on 02.08.2017.
 */
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private QualityService qualityService;
    @Autowired
    private AppCache appCache;
    @Autowired
    private LoanQualityResultRepository loanQualityResultRepository;

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${kafka.topics.customer}")
    public void receiveCustomer(JsonCustomer customer) {
        //LOGGER.info("received payload='{}'", payload);
        LOGGER.info("received payload='{}'", customer.toString());


        List<LoanQualityCategory> loanQualityCategories = ((List<LoanQualityCategory>)appCache.getVar("LOAN_QUALITY_CATEGORIES"));
        List<LoanQualityResult> loanQualityResultsCache = ((List<LoanQualityResult>)appCache.getVar("LOAN_QUALITY_RESULT_REPOSITORY"));

        BaseCustomer baseCustomer = new BaseCustomer();
        baseCustomer.setId(customer.getGfcus());
        baseCustomer.setFinancialState(customer.getGfc3r());

        List<LoanQualityResult> loanQualityResults = loanQualityResultsCache
                .stream()
                .filter(lqr -> lqr.getLoanAccountNumber().equals(customer.getGfcus()))
                .collect(Collectors.toList());

        loanQualityResults.forEach(loanQualityResult -> {
            try {
                LoanQualityCategory loanQualityCategory = qualityService.calculateLoanQualityCategory(baseCustomer.FIN_STATE_TYPE(), loanQualityResult.getLoanServCoeffType());
                loanQualityResult.setLoanQualityCategory(loanQualityCategory.getId());
                loanQualityResult.setFinState(baseCustomer.FIN_STATE_TYPE());
                loanQualityResult.setInterestRate(loanQualityCategory.getPMin());

            } catch (QualityConvertionEx qualityConvertionEx) {
                qualityConvertionEx.printStackTrace();
            }
        });

        Integer max = loanQualityResults
                .stream()
                .mapToInt(res -> res.getLoanQualityCategory())
                .max()
                .getAsInt();

        loanQualityResults.forEach( r -> {
            r.setLoanQualityCategoryForAllCustomerLoans(max);

            r.setInterestRateAll(
                    loanQualityCategories
                            .stream()
                            .filter(cat -> cat.getId().equals(max))
                            .findFirst()
                            .get()
                            .getPMin()
            );
        });
        loanQualityResultRepository.save(loanQualityResults);

        //latch.countDown();
    }

    /*@KafkaListener(topics = "${kafka.topics.transaction}")
    public void receiveAvroTransaction(BaseTransaction transaction) {
        LOGGER.info("received payload='{}'", transaction);
        latch.countDown();
    }*/

    /*@KafkaListener(topics = "${kafka.topics.transaction}")
    public void receiveCustomer2(String payload) {
        LOGGER.info("received payload='{}'", payload);
        latch.countDown();
    }*/
}
