package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// このクラスにはDBのbookテーブルを格納する
@Entity
@Table(name = "book")
public class Book {
	// 取得してきた値を格納するための変数たち。
	// PRIMARY KEYを明示
	@Id
	// book新規作成対応。idを自動インクリメントで作成してくれる
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long code;
	private String name;
	private Long price;
	private String author;
	
	// getterの作成(Thymeleaf参照用)
	public Long getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public Long getPrice() {
		return price;
	}
	public String getAuthor() {
		return author;
	}
	// 価格編集用のセッター
	public void setCode(Long code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}
