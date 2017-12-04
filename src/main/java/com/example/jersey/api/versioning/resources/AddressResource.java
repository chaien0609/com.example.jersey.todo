package com.example.jersey.api.versioning.resources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.jersey.api.versioning.domains.Address;
import com.example.jersey.api.versioning.services.AddressService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="api")
@Path("/app")
public class AddressResource {

	private AddressService addressService;

	@GET
	@ApiOperation(value="Get Address by version specified in URL", notes="Get Address by version specified in URL")
	@Path("/apiurl/v1/address")
	@Produces(MediaType.APPLICATION_JSON)
	public AddressV1 getAddressUrlV1() {
		Address address = addressService.load();
		return convertToV1(address);
	}

	@GET
	@Path("/apiurl/v2/address")
	@Produces(MediaType.APPLICATION_JSON)
	public AddressV2 getAddressUrlV2() {
		Address address = addressService.load();
		return convertToV2(address);
	}
	
	@GET
	@Path("/apiheader/address")
	public Object getAddressHeader(@HeaderParam("X-API-Version") String version) {
        if(version.equalsIgnoreCase("v1")) {
        		return getAddressHeaderV1();
        }else if(version.equalsIgnoreCase("v2")) {
        		return getAddressHeaderV2();
        }
        
        return null;
    }
	
	public AddressV1 getAddressHeaderV1() {
        Address address = addressService.load();
        return convertToV1(address);
    }
	
	public AddressV2 getAddressHeaderV2() {
        Address address = addressService.load();
        return convertToV2(address);
    }
	
	@GET
	@Path("/apiaccept/address")
	@Produces(MediaType.APPLICATION_JSON)
	public AddressV1 getAddressAcceptV0() {
        Address address = addressService.load();
        return convertToV1(address);
    }
	
	@GET
	@Path("/apiaccept/address")
	@Produces("application/vnd.api.address-v1+json")
	public AddressV1 getAddressAcceptV1() {
        Address address = addressService.load();
        return convertToV1(address);
    }
	
	@GET
	@Path("/apiaccept/address")
	@Produces("application/vnd.api.address-v2+json")
	public AddressV2 getAddressAcceptV2() {
        Address address = addressService.load();
        return convertToV2(address);
    }
	
	private AddressV1 convertToV1(final Address address) {
		AddressV1 addressParamV1 = new AddressV1();
		addressParamV1.setAddress(address.getZip() + ' ' + address.getTown());
		return addressParamV1;
	}

	private AddressV2 convertToV2(final Address address) {
		AddressV2 addressParamV2 = new AddressV2();
		addressParamV2.setZip(address.getZip());
		addressParamV2.setTown(address.getTown());
		return addressParamV2;
	}

	private Address convertFromV1(final AddressV1 addressParamV1) {
		final String zip, town;

		Pattern p = Pattern.compile("(\\d{5}) (.*)");
		Matcher m = p.matcher(addressParamV1.getAddress());
		if (!m.matches()) {
			throw new IllegalArgumentException("unparsable address " + addressParamV1.getAddress());
		}

		zip = m.group(1);
		town = m.group(2);
		return new Address(zip, town);
	}

	private Address convertFromV2(final AddressV2 addressParamV2) {
		return new Address(addressParamV2.getZip(), addressParamV2.getTown());
	}
}
