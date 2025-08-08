package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//インターフェースの作成JpaRepositoryを継承　データの参照に使用する。
public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByCodeEquals(Long code);
}
