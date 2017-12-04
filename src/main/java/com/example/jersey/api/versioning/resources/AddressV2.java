package com.example.jersey.api.versioning.resources;

public class AddressV2 {

	private String zip;

    private String town;

    public String getZip() {
        return zip;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getTown() {
        return town;
    }

    public void setTown(final String town) {
        this.town = town;
    }
}
