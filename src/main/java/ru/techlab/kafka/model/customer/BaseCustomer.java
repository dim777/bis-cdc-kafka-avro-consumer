package ru.techlab.kafka.model.customer;

import lombok.Data;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;

/**
 * Created by rb052775 on 02.08.2017.
 */
@Data
public class BaseCustomer extends SpecificRecordBase implements Serializable {
    private static final long serialVersionUID = 3375159358757648792L;

    public static final org.apache.avro.Schema SCHEMA = new org.apache.avro.Schema.Parser().parse("{\"namespace\": \"ru.techlab.kafka.config.kafka\",\n" +
            "  \"type\": \"record\",\n" +
            "  \"name\": \"BaseCustomer\",\n" +
            "  \"fields\": [\n" +
            "    {\"name\": \"GFCUS\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCLC\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCUN\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCPNC\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFDAS\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFC1R\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFC2R\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFC3R\",  \"type\": \"string\"},\n" +
            "    {\"name\": \"GFC4R\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFC5R\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFP1R\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFP2R\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFP3R\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFP4R\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFP5R\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCTP\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCUB\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCUC\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCUD\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCUZ\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFSAC\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFACO\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCRF\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFLNM\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCA2\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCNAP\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCNAR\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCNAL\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCOD\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFDCC\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFDLM\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFITRT\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFBRNM\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCRB1\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCRB2\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFADJ\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFERCP\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFERCC\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFDRC\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFGRPS\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCUNA\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFDASA\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCUNM\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFCNAI\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFGRP\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFMTB\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFETX\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFYFON\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFDFRQ\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFFON\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFFOL\", \"type\": \"string\"},\n" +
            "    {\"name\": \"GFDEL\", \"type\": \"string\"}\n" +
            "  ]\n" +
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
