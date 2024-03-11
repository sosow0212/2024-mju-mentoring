package com.mju.mentoring.board.infrastructure;

import static com.mju.mentoring.board.domain.QBoard.board;

import com.mju.mentoring.board.domain.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Board> findAll(final Long boardId, final int size, final String search) {
        return queryFactory
            .selectFrom(board)
            .where(likeTitle(search), ltBookId(boardId))
            .orderBy(board.id.desc())
            .limit(size)
            .fetch();
    }

    private BooleanExpression ltBookId(final Long boardId) {
        if (boardId == null) {
            return null;
        }

        return board.id.lt(boardId);
    }

    private BooleanExpression likeTitle(final String search) {
        if (StringUtils.isNullOrEmpty(search)) {
            return null;
        }
        return board.description.title.like(search + "%");
    }
}
