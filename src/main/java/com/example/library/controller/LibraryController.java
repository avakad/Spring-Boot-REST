package com.example.library.controller;
import com.example.library.exceptions.NotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class LibraryController {
    /*
    @RequestMapping("/all")
    @GetMapping("/all")
    public ResponseEntity getBooks() {
        try {
            return ResponseEntity.ok("Server works");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
     */

        private int counter = 4;

        private List<Map<String, String>> books = new ArrayList<Map<String, String>>() {{
            add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First book"); }});
            add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second book"); }});
        }};

        @GetMapping
        public List<Map<String, String>> list() {
            return books;
        }

        @GetMapping("{id}")
        public Map<String, String> getOne(@PathVariable String id) {
            return getBook(id);
        }

        private Map<String, String> getBook(@PathVariable String id) {
            return books.stream()
                    .filter(message -> message.get("id").equals(id))
                    .findFirst()
                    .orElseThrow(NotFoundException::new);
        }

        @PostMapping
        public Map<String, String> create(@RequestBody Map<String, String> book) {
            book.put("id", String.valueOf(counter++));

            books.add(book);

            return book;
        }

        @PutMapping("{id}")
        public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> book) {
            Map<String, String> bookFromDb = getBook(id);

            bookFromDb.putAll(book);
            bookFromDb.put("id", id);

            return bookFromDb;
        }

        @DeleteMapping("{id}")
        public void delete(@PathVariable String id) {
            Map<String, String> book = getBook(id);

            books.remove(book);
        }
}