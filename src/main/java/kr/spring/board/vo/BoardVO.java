package kr.spring.board.vo;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO {
	private int num;
	@NotEmpty
	private String writer;
	@NotEmpty
	private String title;
	@NotEmpty
	private String passwd;
	@NotEmpty
	private String content;
	private Date reg_date;
}




