package com.irv.restfulservice.helloworld;

import com.irv.restfulservice.helloworld.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController//cual es la diferencia entre RestController y este
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;


    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello world");
    }
    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s",name));
    }
    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(
//            @RequestHeader(name = "Accept-Language", required = false)Locale locale
//            se supone que no es ta tan bien estar poniendo esto en todos los metodos que
//            ocpen de esto, es mejor llamar a la clase LocaleContextHolder
            ){
        return messageSource
                .getMessage("good.morning.message"
                        ,null
                        ,"Default massage"
//                        ,locale);
                        , LocaleContextHolder.getLocale());
    }
}
