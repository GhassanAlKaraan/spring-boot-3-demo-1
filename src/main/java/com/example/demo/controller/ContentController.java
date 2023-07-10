package com.example.demo.controller;

import com.example.demo.model.Content;
import com.example.demo.repository.ContentCollectionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/content")
@CrossOrigin
public class ContentController {
private final ContentCollectionRepository repository;

//Dependency injection, to have an instance of repo when working with the controller.
//the framework makes the instance, you do this:

    //@Autowired // when we have 1 public constructor, no need for this annotation
    public ContentController(ContentCollectionRepository repository) {
        this.repository = repository;
    }

    // make a request and find all content pieces in the system:
    //Special request mapping:
    @GetMapping("") //path same as controller, no need to re-type
    public List<Content> findAll(){
        return repository.findAll();
    }

    //CRUD, create, read, update, delete... paging, sorting,...

    //findById functionality
    @GetMapping("/{id}")
    public Content findById(@PathVariable Integer id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found!"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("") //path same as controller, no need to re-type
    public void create(@Valid @RequestBody Content content){

        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Content content, @PathVariable Integer id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found!");
        }
        repository.save(content);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found!");
        }
        repository.delete(id);
    }


}
