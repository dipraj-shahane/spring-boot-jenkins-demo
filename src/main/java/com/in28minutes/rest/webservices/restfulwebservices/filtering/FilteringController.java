package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue retrieveFilteredBean() {
		BeanToFilter beanToFilter = new BeanToFilter("Value1", "Value2", "Value3");

		PropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");

		FilterProvider filters = new SimpleFilterProvider().addFilter("DynamicBeanFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(beanToFilter);
		mapping.setFilters(filters);
		return mapping;
	}

	@GetMapping("/filtering-list")
	public MappingJacksonValue retrieveListOfFilteredBean() {

		List<BeanToFilter> filteredBeanList = Arrays.asList(new BeanToFilter("Value1", "Value2", "Value3"),
				new BeanToFilter("Value11", "Value22", "Value33"));
		
		PropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("DynamicBeanFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(filteredBeanList);
		mapping.setFilters(filters);

		return mapping;
	}
}
