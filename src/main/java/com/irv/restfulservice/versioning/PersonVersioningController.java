package com.irv.restfulservice.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    /**
     *Se puede hacer versionamiento de la API con
     * /v1/ en la url
     * NOTA= Es conocido como URI VERSIONING
     * /param?PARAM como parametro en la url
     * en el HEADER
     * PRODUCES
     */
    @GetMapping("/v1/person")
    public PersonV1 personV1(){
        return new PersonV1("Bob CHarlie");
    }
    @GetMapping("/v2/person")
    public PersonV2 personV2(){
        return new PersonV2(new Name("Julio","Cesar"));
    }

    /**
     * ?PARAM
     * NOTA= Es conocido como REQUEST PARAMETER VERSIONING
     */
//  la otra es mandar el # de version desde variable en URL
//    localhost:8080/person/param?version1
    @GetMapping(value = "/person/param", params = "version1")
    public PersonV1 paramV1(){
        return new PersonV1("Person Uno");
    }
////    localhost:8080/person/param?version2
    @GetMapping(value = "/person/param",params = "version2")
    public PersonV2 paramV2(){
        return new PersonV2(new Name("Person", "Uno"));
    }
    /**
     * HEADER
     * en postman se hace la variable con el nombre
     * VARIABLE-VERSION-HEADERS y en value el # de version
     * NOTA= Es conocido como HEADER VERSIONING
     */
    @GetMapping(value = "/person/header"
            ,headers = "VARIABLE-VERSION-HEADERS=1")
    public PersonV1 headerV1(){
        return new PersonV1("Person Uno");
    }
    @GetMapping(value = "/person/header"
            ,headers = "VARIABLE-VERSION-HEADERS=2")
    public PersonV2 headerV2(){
        return new PersonV2(new Name("Person", "Uno"));
    }
    /**
     * PRODUCES
     * tambien es un parametro que va en Header
     * pero cambia su Request, aqui se indica con
     * Accept la version que es application/vnd.company.app-v1+json
     * o la otra
     * NOTA= Es conocido como ACCEPT HEADER o
     * MIME TYPE VERSIONING
     */
    @GetMapping(value = "/person/produces"
            ,produces = "application/vnd.company.app-v1+json")
    public PersonV1 producesV1(){
        return new PersonV1("Person Uno");
    }
    @GetMapping(value = "/person/produces"
            ,produces = "application/vnd.company.app-v2+json")
    public PersonV2 producesV2(){
        return new PersonV2(new Name("Person", "Uno"));
    }
}
/*
Importante

Para seleccionar cual tipo de versionamineto utiliazr
es importante entender que al menos para el header y produces
se tuvo que utilizar postman y que desde ahi se le movio
Pero eso en un navegador norma NO se puede hacer, por lo que
ahi es mas conveniente usar parametros o v1/
*/