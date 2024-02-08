package kr.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	private int rowCount = 10;

	//자바빈(VO) 초기화
	@ModelAttribute
	public BoardVO initCommand() {
		return new BoardVO();
	}
	
	@RequestMapping("/")
	public String main() {
		return "redirect:/list.do";
	}

	@RequestMapping("/list.do")
	public ModelAndView getList(
			@RequestParam(value="pageNum",defaultValue="1") int currentPage) {

		//총 레코드 수
		int count = boardService.getBoardCount();

		//페이징 처리
		PagingUtil page = 
				new PagingUtil(currentPage,count,rowCount,10,"list.do");

		//목록 호출
		List<BoardVO> list = null;
		if(count > 0) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("start", page.getStartRow()-1);
			map.put("rowCount", rowCount);

			list = boardService.getBoardList(map);
		}

		ModelAndView mav = new ModelAndView();
		//뷰 이름 설정
		mav.setViewName("selectList");
		//데이터 저장
		mav.addObject("count", count);
		mav.addObject("list",list);
		mav.addObject("page", page.getPage());

		return mav;
	}

	//글쓰기 폼
	@RequestMapping(value="/insert.do",method=RequestMethod.GET)
	public String form() {
		return "insertForm";
	}

	//글쓰기 처리
	@RequestMapping(value="/insert.do",method=RequestMethod.POST)
	public String submit(@Valid BoardVO boardVO,BindingResult result) {

		log.debug("<<BoardVO>> : " + boardVO);
		log.info("<<~~~~~~>> : " + boardVO);

		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}

		//글쓰기
		boardService.insertBoard(boardVO);

		return "redirect:/list.do";
	}

	//글 상세
	@RequestMapping("/detail.do")
	public ModelAndView detail(@RequestParam int num) {
		BoardVO board = 
				boardService.getBoard(num);

		return new ModelAndView("selectDetail","board",board);		
	}

	//수정 폼
	@RequestMapping(value="/update.do",method=RequestMethod.GET)
	public String formUpdate(@RequestParam int num,
			Model model) {

		model.addAttribute("boardVO", boardService.getBoard(num));

		return "updateForm";
	}	
	//글 수정 처리
	@RequestMapping(value="/update.do",method=RequestMethod.POST)
	public String submitUpdate(@Valid BoardVO boardVO,
			BindingResult result) {

		//유효성 체크 결과 오류가 있으면 폼을 호출
		if(result.hasErrors()) {
			return "updateForm";
		}

		//비밀번호 일치 여부 체크
		//DB에 저장된 비밀번호 구하기
		BoardVO dbBoard = boardService.getBoard(boardVO.getNum());

		//비밀번호 체크
		if(!dbBoard.getPasswd().equals(boardVO.getPasswd())) {
			result.rejectValue("passwd", "invalidPassword");
			return "updateForm";
		}

		boardService.updateBoard(boardVO);

		return "redirect:/list.do";
	}

	//글 삭제 폼
	@RequestMapping(value="/delete.do",method=RequestMethod.GET)
	public String formDelete(@RequestParam int num,
			Model model) {
		BoardVO boardVO = new BoardVO();
		boardVO.setNum(num);

		model.addAttribute("boardVO", boardVO);

		return "deleteForm";
	}

	//글 삭제 처리
	@RequestMapping(value="/delete.do",method=RequestMethod.POST)
	public String submitDelete(@Valid BoardVO boardVO,
			BindingResult result) {
		//유효성 체크 결과 오류가 있으면 폼을 호출
		//비밀번호가 전송 여부만 체크
		if(result.hasFieldErrors("passwd")) {
			return "deleteForm";
		}

		//비밀번호 일치 여부 체크
		//DB에 저장된 비밀번호 구하기
		BoardVO dbBoard = 
				boardService.getBoard(boardVO.getNum());

		//비밀번호 체크
		if(!dbBoard.getPasswd().equals(boardVO.getPasswd())) {
			result.rejectValue("passwd", "invalidPassword");
			return "deleteForm";
		}

		//글 삭제
		boardService.deleteBoard(boardVO.getNum());

		return "redirect:/list.do";
	}

}








