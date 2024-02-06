package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class BoardFakeRepository implements BoardRepository {

    private final Map<Long, Board> db = new HashMap<>();
    private Long id = 1L;

    @Override
    public Board save(final Board board) {
        Board savedBoard = Board.builder()
            .id(id)
            .description(board.copyDescription())
            .build();

        db.put(id++, savedBoard);
        return savedBoard;
    }

    @Override
    public List<Board> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Optional<Board> findById(final Long id) {
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
}
