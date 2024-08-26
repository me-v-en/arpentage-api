package com.example.meven.arpentage_api.repository;

import com.example.meven.arpentage_api.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface BookRepository extends CrudRepository<Book, Long> {
}
