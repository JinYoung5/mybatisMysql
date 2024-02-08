package kr.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.board.vo.BoardVO;

@Mapper
public interface BoardMapper {
	public void insertBoard(BoardVO board);
	@Select("SELECT COUNT(*) FROM was_db.mboard")
	public int getBoardCount();
	public List<BoardVO> getBoardList(Map<String,Object> map);
	@Select("SELECT * FROM was_db.mboard WHERE num=#{num}")
	public BoardVO getBoard(int num);
	@Update("UPDATE was_db.mboard SET writer=#{writer},title=#{title},content=#{content} WHERE num=#{num}")
	public void updateBoard(BoardVO board);
	public void deleteBoard(int num);
}








