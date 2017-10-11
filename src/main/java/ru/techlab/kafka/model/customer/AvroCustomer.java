package ru.techlab.kafka.model.customer;

import lombok.Data;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;

/**
 * Created by rb052775 on 02.08.2017.
 */
@Data
public class AvroCustomer extends SpecificRecordBase implements Serializable {
    private static final long serialVersionUID = 3375159358757648792L;

    public static final org.apache.avro.Schema SCHEMA = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\n" +
            "\"name\":\"GFPF\",\n" +
            "\"namespace\":\"value.SOURCEDB.KFILSTL\",\n" +
            "\"fields\":[\n" +
            "{\"name\":\"GFCUS\", \"type\":{\"type\":\"string\", \"logicalType\":\"CHARACTER\", \"dbColumnName\":\"GFCUS\", \"length\":6}, \"default\":\"\"},\n" +
            "{\"name\":\"GFC3R\", \"type\":{\"type\":\"string\", \"logicalType\":\"CHARACTER\", \"dbColumnName\":\"GFC3R\", \"length\":2}, \"default\":\"\"}]\n" +
            "}");

    private String gfcus;
    private String gfc3r;

    @Override
    public Schema getSchema() {
        return SCHEMA;
    }

    @Override
    public Object get(int field) {
        if(field == 0) return gfcus;
        if(field == 1) return gfc3r;
        return null;
    }

    @Override
    public void put(int field, Object value) {
        if(field == 0) gfcus = (String) value;
        if(field == 1) gfc3r = (String) value;
    }
}
