package ru.techlab.kafka.component;

/**
 * Created by rb052775 on 06.10.2017.
 */
public interface AppCache {
    void setVar(String name, Object var);
    Object getVar(String name);
}
