package com.irv.restfulservice.user;

import com.irv.restfulservice.user.repository.PostRepository;
import com.irv.restfulservice.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAResource {//Se puede llamar Resource O Controller

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/jpa/user")
    public List<User> retriveAllUsers() {
        return userRepository.findAll();//me causa duda porque no esta el impl
    }


    @GetMapping("/jpa/user/{id}")
    //Se cambia el User por un EntityModel
    public EntityModel<User> retriveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        //se pretende adjuntar un link para que se
        //pueda regresar, no se puede agregar el endpoint Hardcoreado
        //se tienen que hacer un obj para que sea dinamico

        if (!user.isPresent()) {
            throw new UserNotFoundException("id:" + id);
        }
        //un entityModel es un modelo del paramtro User por ejemplo
        EntityModel<User> model = EntityModel.of(user.get());
        WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(this.getClass())
                        .retriveAllUsers());

        model.add(linkToUsers.withRel("link-to-users"));//etiqueta o rel para identificar el link

        return model;
    }

    @PostMapping("/jpa/user")
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        User userCreated = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();
        System.out.println(location);
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/jpa/user/{id}")
    public void deleteUser(@PathVariable int id){
         userRepository.deleteById(id);
    }

    @GetMapping("/jpa/user/{id}/post")
    public List<Post> retrieveUsersPost(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) throw new  UserNotFoundException("id not found: "+ id);
        return user.get().getPosts();
    }

    @PostMapping("/jpa/user/{id}/post")
    public ResponseEntity createPost(@PathVariable int id,@RequestBody Post post ){

        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) throw new  UserNotFoundException("id not found: "+ id);
        User user =  userOptional.get();
        post.setUser(user);
        postRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        System.out.println(location);
        return ResponseEntity.created(location).build();
    }
}
