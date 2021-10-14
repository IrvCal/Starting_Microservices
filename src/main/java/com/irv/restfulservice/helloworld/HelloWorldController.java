package com.irv.restfulservice.helloworld;

import com.irv.restfulservice.helloworld.HelloWorldBean;
import org.springframework.web.bind.annotation.*;

@RestController//cual es la diferencia entre RestController y este
public class HelloWorldController {

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
}
