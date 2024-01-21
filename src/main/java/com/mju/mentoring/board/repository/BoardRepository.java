package com.mju.mentoring.board.repository;

import com.mju.mentoring.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

//Board 엔티티에 대한 DB 접근을 함, Jparepository를 상속받아 메소드를 제공 받음
//Repository -> JpaRepository를 상속받을 때는 해당 Repository안에 빈이 등록되어있기 때문에 쓰지 않아도 된다.
@Transactional
public interface BoardRepository extends JpaRepository<Board, Long> {

}
