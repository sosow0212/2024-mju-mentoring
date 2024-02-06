package com.mju.mentoring.board.domain;

//import jakarta.persistence.*; //와일드카드 지양
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Embedded;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //클래스를 엔티티로 선언 - 이 어노테이션을 붙임으로써 JPA가 해당 클래스를 관리하게 됨
@Data // lombok - 컨틀롤러의 board.get 이용 //@Data에 @Getter @Setter가 포함되어 있음.
@NoArgsConstructor  // 기본 생성자 생성 lombok
//엔티티 선언을 통해 DB에 저장되는 객체들을 구현한다.
public class Board {

    @Id //테이블의 기본키에 사용할 속성을 지정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 데이터베이스에 위임,// Mysql 데이터베이스의 경우 AUTO_INCREMENT를 사용하여 기본키를 생성
    private Long id; //기본키

    @Embedded
    private BoardContent boardContent;

    //private String title; // 글 제목
    //private String content; // 글 내용

    public Board(String title, String content) {
        this.boardContent = new BoardContent(title, content);
        //this.title = title;
        //this.content = content;
    }

    public void update(String title, String content) {
        this.boardContent.update(title, content);
        //this.title = title;
        //this.content = content;
    }
}
