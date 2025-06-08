package com.example.TodoApisSpring;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private static List<Todo> todoList;

    public TodoController(){
        todoList = new ArrayList<>();
        todoList.add(new Todo(1 , false , "TODO 1" , 1));
        todoList.add(new Todo(2 , false , "TODO 2" , 2));
    }

    @GetMapping("/")
    public List<Todo> getTodos(){
        return todoList;
    }

    @PostMapping("/")
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo){
        todoList.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }


    @GetMapping("/{todoId}")
    public ResponseEntity<?> getTodoId(@PathVariable int todoId) {
        for (Todo todolist : todoList) {
            if (todolist.getId() == todoId) {
                return ResponseEntity.status(HttpStatus.OK).body(todolist);
            }
        }

        ErrorResponse errorResponse = new ErrorResponse("Todo with ID " + todoId + " not found", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<?> deleteId(@PathVariable int todoId){
        Iterator<Todo> iterator = todoList.iterator();
        while (iterator.hasNext()){
            Todo todo  = iterator.next();
            if(todo.getId() == todoId){
                iterator.remove();
                System.out.println("Trying to delete ID: " + todoId);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        ErrorResponse errorResponse = new ErrorResponse("Todo with ID " + todoId + " not found", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<?> updateById(@PathVariable int todoId , @RequestBody Todo newTodo){
        for (int i = 0; i < todoList.size(); i++) {
            Todo todo = todoList.get(i);
            if (todo.getId() == todoId) {
                todoList.set(i, newTodo);
                return ResponseEntity.ok("Todo updated successfully");
            }
        }
        ErrorResponse errorResponse = new ErrorResponse("Todo with ID " + todoId + " not found", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
