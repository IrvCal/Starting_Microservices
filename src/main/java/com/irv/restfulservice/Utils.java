package com.irv.restfulservice;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

public class Utils {
    public static final MappingJacksonValue filtering(Object o, String filterBeanName, String... elements){
        SimpleBeanPropertyFilter filter =
                SimpleBeanPropertyFilter
                        .filterOutAllExcept(elements);

        FilterProvider filters =
                new SimpleFilterProvider()
                        .addFilter(filterBeanName, filter);
        MappingJacksonValue mappingJacksonValue = new
                MappingJacksonValue(o);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;

    }
}
