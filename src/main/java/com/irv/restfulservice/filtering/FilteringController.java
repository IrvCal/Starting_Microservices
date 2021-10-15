package com.irv.restfulservice.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.irv.restfulservice.Utils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public SomeBean someBean() {
        return new SomeBean("value1", "value2", "value3");
    }

    //value1, value2 -> se tiene que retornar el Mapping por que es donde se configurarn los filtros
    @GetMapping("/filtering-dos")
    public MappingJacksonValue someBeanDos() {
        SomeBeanDos beanDos = new SomeBeanDos("value1", "value2", "value3");

        //para poder mandar unicamnte unas variables se usa:
        MappingJacksonValue mappingJacksonValue = new
                MappingJacksonValue(beanDos);

        SimpleBeanPropertyFilter filter =
                SimpleBeanPropertyFilter
                        .filterOutAllExcept("field1", "field2");

        FilterProvider filters =
                new SimpleFilterProvider()
                        // el truco aqui es que tiene que estar el nombre del filtro en el bean
                        .addFilter("SomeBean_FilterExample", filter);

        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

    //value2, value3
    @GetMapping("/filtering-list")
    public MappingJacksonValue someBeanListDos() {
        List<SomeBeanDos> list = Arrays.asList(
                new SomeBeanDos("value1", "value2", "value3"),
                new SomeBeanDos("value21", "value22", "value23"),
                new SomeBeanDos("value31", "value32", "value33")
        );
        return Utils.filtering(list, "SomeBean_FilterExample","field1","field3");
    }

}
