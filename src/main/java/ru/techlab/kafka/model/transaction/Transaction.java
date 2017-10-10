package ru.techlab.kafka.model.transaction;

/**
 * Created by rb052775 on 02.08.2017.
 */
public interface Transaction {
    String getId();
    String accountDebit();
    String accountCredit();
    double getAmount();
}
