package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BoardFakeRepository implements BoardRepository {

    private final Map<Long, Board> db = new HashMap<>();
    private Long id = 1L;

    @Override
    public Board save(final Board board) {
        Board savedBoard = Board.builder()
            .id(id)
            .writer(board.getWriter())
            .description(board.copyDescription())
            .view(board.getView())
            .build();

        db.put(id++, savedBoard);
        return savedBoard;
    }

    @Override
    public List<Board> findAll(final Long boardId, final int size, final String search) {
        return db.keySet().stream()
            .filter(id -> {
                if (boardId == null) {
                    return true;
                }
                return id < boardId;
            })
            .limit(size)
            .map(db::get)
            .toList();
    }

    @Override
    public Optional<Board> findById(final Long id) {
        return db.values()
            .stream()
            .filter(board -> board.getId().equals(id))
            .findAny();
    }

    @Override
    public Optional<Board> viewById(final Long id) {
        return db.values()
            .stream()
            .filter(board -> board.getId().equals(id))
            .findAny();
    }

    @Override
    public void delete(final Board board) {
        if (!db.containsValue(board)) {
            return;
        }

        Long boardId = db.keySet()
            .stream()
            .filter(key -> db.get(key).equals(board))
            .findAny()
            .get();

        db.remove(boardId);
    }

    @Override
    public void deleteAllById(final List<Long> ids) {
        ids.stream()
            .filter(db::containsKey)
            .forEach(db::remove);
    }

    @Override
    public List<Board> findBoardsByBoardsId(final List<Long> ids) {
        return db.values().stream()
            .filter(board -> ids.contains(board.getId()))
            .toList();
    }

    @Override
    public void updateWriterName(final Long writerId, final String newWriterName) {
        return;
    }
}
