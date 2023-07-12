package com.example.pocLibraryProject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pocLibraryProject.model.LibraryBooks;
import com.example.pocLibraryProject.repository.LibraryBooksRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class LibraryBooksController {
	
	@Autowired
	LibraryBooksRepository libraryBooksRepository;
	
	@GetMapping("/libraryBooks")
	public ResponseEntity<List<LibraryBooks>> getAllLibraryBooks(@RequestParam(required = false) String title) {
	    try {
	      List<LibraryBooks> libraryBooks = new ArrayList<LibraryBooks>();

	      if (title == null)
	    	  libraryBooksRepository.findAll().forEach(libraryBooks::add);
	      else
	    	  libraryBooksRepository.findByTitleContaining(title).forEach(libraryBooks::add);

	      if (libraryBooks.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(libraryBooks, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  @GetMapping("/libraryBooks/{id}")
	  public ResponseEntity<LibraryBooks> getLibraryBooksById(@PathVariable("id") long id) {
	    Optional<LibraryBooks> libraryBooksData = libraryBooksRepository.findById(id);

	    if (libraryBooksData.isPresent()) {
	      return new ResponseEntity<>(libraryBooksData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	  @PostMapping("/createLibraryBooks")
	  public ResponseEntity<LibraryBooks> createLibraryBooks(@RequestBody LibraryBooks libraryBooks) {
	    try {
	    	LibraryBooks createTutorials = libraryBooksRepository
	          .save(new LibraryBooks(libraryBooks.getTitle(), libraryBooks.getDescription(), false));
	      return new ResponseEntity<>(createTutorials, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  @PutMapping("/updateLibraryBooks/{id}")
	  public ResponseEntity<LibraryBooks> updateLibraryBooks(@PathVariable("id") long id, @RequestBody LibraryBooks libraryBooks) {
	    Optional<LibraryBooks> libraryBooksData = libraryBooksRepository.findById(id);

	    if (libraryBooksData.isPresent()) {
	    	LibraryBooks updateLibraryBooks = libraryBooksData.get();
	      updateLibraryBooks.setTitle(libraryBooks.getTitle());
	      updateLibraryBooks.setDescription(libraryBooks.getDescription());
	      updateLibraryBooks.setPublished(libraryBooks.isPublished());
	      return new ResponseEntity<>(libraryBooksRepository.save(updateLibraryBooks), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	  @DeleteMapping("/deleteTutorial/{id}")
	  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
	    try {
	    	libraryBooksRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  @DeleteMapping("/deleteAllTutorials")
	  public ResponseEntity<HttpStatus> deleteAllTutorials() {
	    try {
	    	libraryBooksRepository.deleteAll();
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	  }

	  @GetMapping("/findByPublished/published")
	  public ResponseEntity<List<LibraryBooks>> findByPublished() {
	    try {
	      List<LibraryBooks> findByPublished = libraryBooksRepository.findByPublished(true);

	      if (findByPublished.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(findByPublished, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

}
