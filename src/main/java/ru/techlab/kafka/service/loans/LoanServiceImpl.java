package ru.techlab.kafka.service.loans;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.techlab.kafka.model.AccountId;
import ru.techlab.kafka.model.BaseLoan;
import ru.techlab.kafka.repository.LoansRepository;
import ru.techlab.kafka.service.customer.CustomerService;
import ru.xegex.risks.libs.ex.convertion.ConvertionEx;
import ru.xegex.risks.libs.ex.customer.CustomerNotFoundEx;
import ru.xegex.risks.libs.ex.loans.LoanNotFoundException;
import ru.xegex.risks.libs.utils.DateTimeUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by rb052775 on 22.08.2017.
 */
@Service
public class LoanServiceImpl implements LoansService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Autowired
    private LoansRepository loansRepository;
    @Autowired
    private CustomerService customerService;

    @Override
    public BaseLoan getActiveAndNonPortfolioLoan(AccountId accountId) throws LoanNotFoundException, CustomerNotFoundEx {
        Optional<BaseLoan> loanOptional = loansRepository.findActiveAndNonPortfolioSimpleLoansByLoanId(accountId.getBranch(), accountId.getLoanAccountNumber(), accountId.getLoanAccountSuffix());
        BaseLoan loan = loanOptional.orElseThrow(() -> new LoanNotFoundException("No loan found"));
        loan.setBaseCustomer(Optional.ofNullable(customerService.getCustomer(accountId.getLoanAccountNumber())));
        return loan;
    }

    @Override
    public Stream<BaseLoan> getLoansByDtRangeAndActive(LocalDateTime dtFrom, LocalDateTime dtTill, boolean isActive) throws ConvertionEx {
        double dtFromAs400 = DateTimeUtils.convert2As400Format(dtFrom);
        double dtTillAs400 = DateTimeUtils.convert2As400Format(dtTill);
        if(isActive == true){
            return loansRepository.findActiveSimpleLoansByStartDateBetween(dtFromAs400, dtTillAs400);
        }
        return loansRepository.findSimpleLoansByStartDateBetween(dtFromAs400, dtTillAs400);
    }

    @Override
    public List<BaseLoan> getAllActiveAndNonPortfolioBaseLoans() {
        List<BaseLoan> loans = loansRepository.findAllActiveAndNonPortfolioBaseLoans();
        loans.forEach(loan ->
                {
                    try {
                        loan.setBaseCustomer(Optional.ofNullable(customerService.getCustomer(loan.getLoanAccountNumber())));
                    } catch (CustomerNotFoundEx customerNotFoundEx) {
                        customerNotFoundEx.printStackTrace();
                    }
                }
        );

        return loans;
    }

    /*@Override
    public static FuturesQuote process(List<? extends FuturesTick> ticks, TimeFrame timeFrame) throws ConvertionEx {
        if(ticks.isEmpty())
            throw new ConvertionEx("Couldn't convert ticks to quote: ticks list is empty.");

        BiFunction<FuturesTick, TimeFrame, GenFuturesQuote> baseQuoteFunction = (firstTick, tf) -> {
            LocalDateTime created = new LocalDateTime();
            GenFuturesQuote bq = new GenFuturesQuote<>();

            bq.setOpen((firstTick.getAsk() + firstTick.getBid())/2);
            bq.setFutureSecurity(firstTick.getSecurity());

            switch (timeFrame){
                case S1: created = firstTick.getCreated().withMillisOfSecond(0);
                    ///
            }
            bq.setCreated(created);
            return bq;
        };

        BiFunction<GenFuturesQuote, FuturesTick, GenFuturesQuote> accumulator = (quote, tick) -> {
            double midquote = (tick.getAsk() + tick.getBid())/2;
            if(quote.getHigh() < midquote) quote.setHigh(midquote);
            if(quote.getLow() > midquote) quote.setLow(midquote);
            return quote;
        };

        BinaryOperator<GenFuturesQuote> combiner = (quote, quoteToAdd) -> quoteToAdd;

        //<U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
        GenFuturesQuote q = ticks
                .stream()
                .limit(ticks.size() - 1)
                .skip(1)
                .reduce(baseQuoteFunction.apply(ticks.get(0), timeFrame), accumulator, combiner);

        BiFunction<GenFuturesQuote, FuturesTick, GenFuturesQuote> lastQuoteFunc = (quote, lastTick) -> {
            quote.setClose((lastTick.getAsk() + lastTick.getBid())/2);
            quote.setTimeFrame(timeFrame);
            return quote;
        };

        return lastQuoteFunc.apply(q, ticks.get(ticks.size() - 1));
    }*/
}
