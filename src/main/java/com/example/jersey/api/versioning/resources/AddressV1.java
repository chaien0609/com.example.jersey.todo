package com.example.jersey.api.versioning.resources;

import javax.validation.constraints.Pattern;

public class AddressV1 {

	private String address;

    @Pattern(regexp = "(\\d{5}) (.*)")
    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }
}
