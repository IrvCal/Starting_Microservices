package com.irv.restfulservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

@RestController
public class UserResource {
    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/user")
    public List<User> findAll() {
        return userDaoService.findAll();
    }

    @GetMapping("/user/{id}")
    public User retriveUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id:" + id);

        }
        return user;
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
