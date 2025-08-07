package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


// コントローラの作成
@Controller
public class BookController {
	
	@Autowired
	private BookRepository bookRepository;
	// urlに/を指定した時に次のメソッドが実行される。
	@RequestMapping("/")
	public ModelAndView showBooks(ModelAndView mv) {
//		bookRepository.findAll()でdatabesebookの中身を全てリストに格納する。
		List<Book> books = bookRepository.findAll();
//		所得した値をオブジェクトに追加。
		mv.addObject("books", books);
//		book_search.htmlにセットする
		mv.setViewName("book_search");
		return mv;
	}

}
