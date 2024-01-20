package com.mju.mentoring.board.repository;

import com.mju.mentoring.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Board 엔티티에 대한 DB 접근을 함, Jparepository를 상속받아 메소드를 제공 받음
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
