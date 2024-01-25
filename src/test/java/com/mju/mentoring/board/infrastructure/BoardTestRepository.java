package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import com.mju.mentoring.board.domain.BoardText;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BoardTestRepository implements BoardRepository {

    private final Map<Long, Board> store = new HashMap<>();
    private Long id = 1L;

    @Override
    public void save(final Board board) {
        Board newBoard = Board.builder()
                .id(id)
                .boardText(new BoardText(board.getTitle(), board.getContent()))
                .build();

        store.put(id, newBoard);
        id++;
    }

    @Override
    public Optional<Board> findById(final Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Board> findAll() {
        return store.values()
                .stream()
                .toList();
    }

    @Override
    public void delete(final Board board) {
        store.remove(board.getId());
    }
}
