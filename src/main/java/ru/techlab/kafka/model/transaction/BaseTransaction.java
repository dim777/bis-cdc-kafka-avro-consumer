package ru.techlab.kafka.model.transaction;

/**
 * Created by rb052775 on 02.08.2017.
 */
public class BaseTransaction implements Transaction {
    @Override
    public String getId() {
        return null;
    }

    @Override
    public String accountDebit() {
        return null;
    }

    @Override
    public String accountCredit() {
        return null;
    }

    @Override
    public double getAmount() {
        return 0;
    }
}
