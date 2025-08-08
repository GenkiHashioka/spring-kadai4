package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
//	検索用のGETメソッドを作成する
	@RequestMapping("/search")
//	書籍コードが未入力の場合でもページが表示できるようにrequired = falseをつける
	public ModelAndView searchBooks(@RequestParam(name = "code", required = false) String codeStr, ModelAndView mv) {
//		bookListに書籍一覧を取得する.
		List<Book> bookList = new ArrayList<Book>();
		// 書籍コードが未入力の場合は全件取得する
		if (codeStr == null || codeStr.trim().isEmpty()) {
			bookList = bookRepository.findAll();
//			入力が数字であった場合に一致検索
		} else if (codeStr.chars().allMatch(Character::isDigit)) {
			bookList = bookRepository.findByCodeEquals(Long.parseLong(codeStr));
		}
		// 取得したリストの中身がなかった場合のエラーメッセージ
		if (bookList.isEmpty()) {
			mv.addObject("message", "該当するデータはありません。");
		}
		
		mv.addObject("books", bookList);
		mv.setViewName("book_search");
		return mv;
	}
}
