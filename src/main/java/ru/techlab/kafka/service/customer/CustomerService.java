package ru.techlab.kafka.service.customer;

import ru.techlab.kafka.model.BaseCustomer;
import ru.xegex.risks.libs.ex.customer.CustomerNotFoundEx;

/**
 * Created by rb052775 on 30.09.2017.
 */
public interface CustomerService {
    BaseCustomer getCustomer(String id) throws CustomerNotFoundEx;
    Iterable<BaseCustomer> getAll();
}
