package com.example.TodoApisSpring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoController {

    private static List<Todo> todoList;

    public TodoController(){
        todoList = new ArrayList<>();
        todoList.add(new Todo(1 , false , "TODO 1" , 1));
        todoList.add(new Todo(2 , false , "TODO 2" , 2));
    }

    @GetMapping("/todos")
    public List<Todo> getTodos(){
        return todoList;
    }

    @PostMapping("/todos")
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo){
        todoList.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

}
