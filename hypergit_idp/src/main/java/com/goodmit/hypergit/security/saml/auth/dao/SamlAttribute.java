package com.goodmit.hypergit.security.saml.auth.dao;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(of = {"name","values"})
public class SamlAttribute {

    @NonNull
    private String name;
    @NonNull
    private List<String> values = new ArrayList<>();

    public SamlAttribute(@NonNull String name,@NonNull String value) {
        this.name = name;
        this.values.add(value);
    }

    public SamlAttribute(@NonNull String name, @NonNull List values) {
        this.name = name;
        this.values.addAll(values);
    }

    public String getValue() {
        return String.join(", ",values);
    }

}
