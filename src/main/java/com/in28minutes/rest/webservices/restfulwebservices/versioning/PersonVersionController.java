package com.in28minutes.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersionController {

	// URI versioning
	@GetMapping("/v1/person")
	public PersonV1 getPersonV1() {
		return new PersonV1("Dipraj Shahane");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 getPersonV2() {
		return new PersonV2(new Name("Dipraj","Shahane"));
	}
	
	// request param versioning
	@GetMapping(value = "/person/param", params = "version=1") // /person/param?version=1
	public PersonV1 getPersonParamV1() {
		return new PersonV1("Dipraj Shahane");
	}
	
	@GetMapping(value = "/person/param", params = "version=2") // /person/param?version=2
	public PersonV2 getPersonParamV2() {
		return new PersonV2(new Name("Dipraj","Shahane"));
	}
	
	// header versioning
	@GetMapping(value = "/person/header", headers = "X-API-VERSION=1") // /person/header & Add Header as X-API-VERSION=1 (key "X-API-VERSION" = value "1")
	public PersonV1 getPersonHeaderV1() {
		return new PersonV1("Dipraj Shahane");
	}
	
	@GetMapping(value = "/person/header", headers = "X-API-VERSION=2") // /person/header & Add Header as X-API-VERSION=2
	public PersonV2 getPersonHeaderV2() {
		return new PersonV2(new Name("Dipraj","Shahane"));
	}
	
	// Accept header Versioning / Produces Versioning / MIME Versioning
	@GetMapping(value = "/person/produces", produces = "application/vnd.api-v1+json") // /person/produces & Add Header as Accept=application/vnd.api-v1+json
	public PersonV1 getPersonProducesV1() {
		return new PersonV1("Dipraj Shahane");
	}
	
	@GetMapping(value = "/person/produces", produces = "application/vnd.api-v2+json") // /person/produces & Add Header as Accept=application/vnd.api-v2+json
	public PersonV2 getPersonProducesV2() {
		return new PersonV2(new Name("Dipraj","Shahane"));
	}
}
