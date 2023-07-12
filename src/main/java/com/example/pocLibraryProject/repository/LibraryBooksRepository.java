package com.example.pocLibraryProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pocLibraryProject.model.LibraryBooks;


public interface LibraryBooksRepository extends JpaRepository<LibraryBooks, Long> {
	//returns  List of LibraryBooks with published having value as input published
  List<LibraryBooks> findByPublished(boolean published);
  
  //returns  List of LibraryBooks which title contains input title.
  List<LibraryBooks> findByTitleContaining(String title);
}
