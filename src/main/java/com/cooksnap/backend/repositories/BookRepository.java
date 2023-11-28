package com.cooksnap.backend.repositories;

import com.cooksnap.backend.domains.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
