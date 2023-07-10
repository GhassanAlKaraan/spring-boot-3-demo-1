package com.example.demo.repository;

import com.example.demo.model.Content;
import com.example.demo.model.Status;
import com.example.demo.model.Type;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContentCollectionRepository {
    //just a simple repository to use as a small db I can work with in the memory
    private final List<Content> contentList = new ArrayList<>();

    //I need a constructor
    public ContentCollectionRepository(){

    }
    //method to return the list of content
    public List<Content> findAll(){
        return contentList;
    }
    //to not have to deal with nulls. null or not-null it doesn't matter
    public Optional<Content> findById(Integer id){
        return contentList.stream().filter(c->c.id().equals(id)).findFirst();
    }
    public void save(Content content){
        contentList.removeIf(c->c.id().equals(content.id()));
        contentList.add(content);
    }
    public boolean existsById(Integer id){
        return contentList.stream().filter(c->c.id().equals(id)).count() == 1;
    }

    public void delete(Integer id){
        contentList.removeIf(c->c.id().equals(id));
    }

    //after the dependency injection we do this:
    @PostConstruct
    private void init(){
        Content content = new Content(
                1,
                "INIT - My first blog post",
                "INIT - My first blog post",
                Status.IDEA,
                Type.ARTICLE,
                LocalDateTime.now(),
                null,
                "");
        contentList.add(content);
    }


}
