package kr.co.sist.cinema.admin.vo;

/**
 * ���� ���� - ���� ���� ��ȭ�� ��ȸ
 * @author owner
 */
public class SCABookMovieListVO {
	private String movie_code, movie_title;

	public SCABookMovieListVO(String movie_code, String movie_title) {
		this.movie_code = movie_code;
		this.movie_title = movie_title;
	} // SCABookMovieListVO

	public String getMovie_code() {
		return movie_code;
	}

	public String getMovie_title() {
		return movie_title;
	}

	@Override
	public String toString() {
		return "SCABookMovieListVO [movie_code=" + movie_code + ", movie_title=" + movie_title + "]";
	} // toString
	
} // class
