package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		List<Book> books = bookRepository.findAll(Sort.by("code"));
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
			bookList = bookRepository.findAll(Sort.by("code"));
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
	// step4追加対応 更新処理の追加
	@RequestMapping("/edit")
	public ModelAndView showEditPage(@RequestParam("code") Long code, ModelAndView mv) {
//		bookRepositoryからcodeに一致する書籍データを取り出す。
		Optional<Book> choiceBook = bookRepository.findById(code);
		// 値があればbookという名前でbook_update.htmlにわたす。
		if (choiceBook.isPresent()) {
			mv.addObject("book", choiceBook.get());
			mv.setViewName("book_update"); // 更新画面に遷移
//			 万が一値が見つからなかった場合のエラーハンドリング
		} else {
			mv.addObject("message", "該当するデータはありません。");
			mv.setViewName("book_search"); // 元の画面に戻る
		}
		return mv;
	}
	
//	書籍情報更新用
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView updateBook(@RequestParam("code") Long code, @RequestParam("price") Long price, ModelAndView mv) {
//		対象の書籍のデータを扱うためのラッパー
		Optional<Book> choiceBook = bookRepository.findById(code);
		
		// データが入っているかをチェック
		if (choiceBook.isPresent()) {
			// 中身の取得
			Book book = choiceBook.get();
			book.setPrice(price);
			bookRepository.save(book);
		}
		
		// 一連の流れが終了したら全件取得してから一覧を表示
		List<Book> bookList = bookRepository.findAll(Sort.by("code"));
		mv.addObject("books", bookList);
		mv.setViewName("book_search");
		
		return mv;
	}
	
}
