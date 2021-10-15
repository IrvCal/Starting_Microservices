package com.irv.restfulservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

@RestController
public class UserResource {
    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/user")
    public List<User> retriveAllUsers() {
        return userDaoService.findAll();
    }


    @GetMapping("/user/{id}")
    //Se cambia el User por un EntityModel
    public EntityModel<User> retriveUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        //se pretende adjuntar un link para que se
        //pueda regresar, no se puede agregar el endpoint Hardcoreado
        //se tienen que hacer un obj para que sea dinamico

        if (user == null) {
            throw new UserNotFoundException("id:" + id);
        }
        //un entityModel es un modelo del paramtro User por ejemplo
        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(this.getClass())
                        .retriveAllUsers());

        model.add(linkToUsers.withRel("link-to-users"));//etiqueta o rel para identificar el link

        return model;
    }

    @PostMapping("/user")
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        User userCreated = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();
        System.out.println(location);
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id){
         User user = userDaoService.deleteUser(id);
        if(user == null) throw new UserNotFoundException("id: "+id);
    }

}
