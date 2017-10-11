package ru.techlab.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.techlab.kafka.component.AppCache;
import ru.techlab.kafka.component.Receiver;
import ru.techlab.kafka.model.BaseCustomer;
import ru.techlab.kafka.model.LoanQualityResult;
import ru.techlab.kafka.model.customer.AvroCustomer;
import ru.techlab.kafka.model.rest.BaseConfig;
import ru.techlab.kafka.model.rest.LoanQualityCategory;
import ru.techlab.kafka.model.rest.LoanQualityCategoryMatrix;
import ru.techlab.kafka.model.rest.LoanServCoeff;
import ru.techlab.kafka.repository.LoanQualityResultRepository;
import ru.techlab.kafka.service.config.ConfigService;
import ru.techlab.kafka.service.config.RiskConfigParamsService;
import ru.techlab.kafka.service.quality.QualityService;
import ru.techlab.kafka.testConfig.Sender;
import ru.xegex.risks.libs.ex.quality.QualityConvertionEx;
import ru.xegex.risks.libs.model.loan.LoanServCoeffType;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BisCdcKafkaAvroConsumerApplicationTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(BisCdcKafkaAvroConsumerApplicationTests.class);
	private static AvroCustomer customer;

	private static ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private Sender sender;
	@Autowired
	private QualityService qualityService;
	@Autowired
	private Receiver receiver;
	@Autowired
	private ConfigService configService;
	@Autowired
	private AppCache appCache;
	@Autowired
	private RiskConfigParamsService riskConfigParamsService;
	@Autowired
	LoanQualityResultRepository loanQualityResultRepository;

	@Test
	public void a_get_config_params() throws IOException {
		String responseBody = "{\"id\":1,\"endOfDay\":\"2017-09-21\"}";

		BaseConfig mockConfig = mapper.readValue(responseBody, BaseConfig.class);

		Mockito
				.when(configService.getBaseConfig())
				.thenReturn(mockConfig);

		BaseConfig baseConfig = configService.getBaseConfig();
		appCache.setVar("BASE_CONFIG", baseConfig);
		Assert.assertEquals(Integer.valueOf(1), ((BaseConfig)appCache.getVar("BASE_CONFIG")).getId());
	}

	@Test
	public void b_get_risk_loan_serv_coeff() throws IOException {
		String responseBody = "[{\"type\":\"MID\",\"id\":5,\"isLegalEntitity\":false,\"forLastNDays\":180,\"moreOrEqThanDays\":30,\"lessThanDays\":60},{\"type\":\"GOOD\",\"id\":1,\"isLegalEntitity\":true,\"forLastNDays\":180,\"moreOrEqThanDays\":0,\"lessThanDays\":5},{\"type\":\"MID\",\"id\":2,\"isLegalEntitity\":true,\"forLastNDays\":180,\"moreOrEqThanDays\":5,\"lessThanDays\":30},{\"type\":\"GOOD\",\"id\":4,\"isLegalEntitity\":false,\"forLastNDays\":180,\"moreOrEqThanDays\":0,\"lessThanDays\":30},{\"type\":\"BAD\",\"id\":6,\"isLegalEntitity\":false,\"forLastNDays\":180,\"moreOrEqThanDays\":60,\"lessThanDays\":9999999},{\"type\":\"BAD\",\"id\":3,\"isLegalEntitity\":true,\"forLastNDays\":180,\"moreOrEqThanDays\":30,\"lessThanDays\":9999999}]";

		List<LoanServCoeff> mockLoanServCoeffs = mapper.readValue(responseBody,
				TypeFactory.defaultInstance().constructCollectionType(List.class,
						LoanServCoeff.class));

		Mockito
				.when(riskConfigParamsService.getAllLoanServCoeffs())
				.thenReturn(mockLoanServCoeffs);

		List<LoanServCoeff> loanServCoeffs = riskConfigParamsService.getAllLoanServCoeffs();
		appCache.setVar("LOAN_SERV_COEFFS", loanServCoeffs);
		Assert.assertEquals(6, ((List<LoanServCoeff>)appCache.getVar("LOAN_SERV_COEFFS")).size());
	}

	@Test
	public void c_get_risk_loan_quality_categories() throws IOException {
		String responseBody = "[{\"id\":5,\"type\":\"HOPELESS\",\"pmin\":100.0},{\"id\":1,\"type\":\"STANDARD\",\"pmin\":0.0},{\"id\":2,\"type\":\"NONSTANDARD\",\"pmin\":1.0},{\"id\":4,\"type\":\"PROBLEM\",\"pmin\":51.0},{\"id\":3,\"type\":\"DOUBTFUL\",\"pmin\":21.0}]";

		List<LoanQualityCategory> mockLoanQualityCategory = mapper.readValue(responseBody,
				TypeFactory.defaultInstance().constructCollectionType(List.class, LoanQualityCategory.class));

		Mockito
				.when(riskConfigParamsService.getAllLoanQualityCategories())
				.thenReturn(mockLoanQualityCategory);

		List<LoanQualityCategory> loanQualityCategories = riskConfigParamsService.getAllLoanQualityCategories();
		appCache.setVar("LOAN_QUALITY_CATEGORIES", loanQualityCategories);
		Assert.assertEquals(5, ((List<LoanQualityCategory>)appCache.getVar("LOAN_QUALITY_CATEGORIES")).size());
	}

	@Test
	public void d_get_risk_loan_quality_category_matrix() throws IOException {
		String responseBody = "[{\"loanServCoeffId\":1,\"loanQualityByFsType1\":1,\"loanQualityByFsType2\":2,\"loanQualityByFsType3\":3},{\"loanServCoeffId\":2,\"loanQualityByFsType1\":2,\"loanQualityByFsType2\":3,\"loanQualityByFsType3\":4},{\"loanServCoeffId\":3,\"loanQualityByFsType1\":3,\"loanQualityByFsType2\":4,\"loanQualityByFsType3\":5}]";

		List<LoanQualityCategoryMatrix> mockLoanQualityCategoryMatrix = mapper.readValue(responseBody,
				TypeFactory.defaultInstance().constructCollectionType(List.class, LoanQualityCategoryMatrix.class));

		Mockito
				.when(riskConfigParamsService.getAllLoanQualityCategoryMatrix())
				.thenReturn(mockLoanQualityCategoryMatrix);

		List<LoanQualityCategoryMatrix> loanQualityCategoryMatrix = riskConfigParamsService.getAllLoanQualityCategoryMatrix();
		appCache.setVar("LOAN_QUALITY_CATEGORY_MATRIX", loanQualityCategoryMatrix);
		Assert.assertEquals(3, ((List<LoanQualityCategory>)appCache.getVar("LOAN_QUALITY_CATEGORY_MATRIX")).size());
	}

	@Test
	public void e_getDataFromDb() {

	}

	@Test
	public void f_produceMessage() {
		customer = new AvroCustomer();
		customer.setGfc3r("3");
		customer.setGfcus("V65657");
		sender.send(customer);
	}

	@Test
	public void g_getMessage() {
		LOGGER.info(customer.toString());

		List<LoanQualityCategory> loanQualityCategories = ((List<LoanQualityCategory>)appCache.getVar("LOAN_QUALITY_CATEGORIES"));
		BaseCustomer baseCustomer = new BaseCustomer();
		baseCustomer.setId(customer.getGfcus());
		baseCustomer.setFinancialState(customer.getGfc3r());

		List<LoanQualityResult> loanQualityResults = loanQualityResultRepository
				.findAllReturnStream()
				.filter(lqr -> lqr.getCustomerName().equals(customer.getGfcus()))
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
	}
}
