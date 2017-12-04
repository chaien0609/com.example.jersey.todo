package com.example.jersey.api.versioning.services;

import com.example.jersey.api.versioning.domains.Address;

public class AddressService {

	private Address address;

    public Address load() {
        return address;
    }

    public void save(final Address address) {
        this.address = address;
    }
}
