package com.goodmit.hypergit.security.saml.dao;

import lombok.*;
import org.springframework.util.Assert;

import javax.print.DocFlavor;
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

    public String getValue() {
        return String.join(", ",values);
    }

}
