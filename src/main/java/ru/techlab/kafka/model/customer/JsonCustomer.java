package ru.techlab.kafka.model.customer;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by rb052775 on 11.10.2017.
 */
@Data
public class JsonCustomer implements Serializable {
    private static final long serialVersionUID = 3375159358757648792L;

    private String gfcus;
    private String gfc3r;
}
