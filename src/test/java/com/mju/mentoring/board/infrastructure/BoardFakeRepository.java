package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BoardFakeRepository implements BoardRepository {

    private final Map<Long, Board> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public Board save(final Board board) {
        Board saved = Board.builder()
                .id(id)
                .description(board.getDescription())
                .build();

        map.put(id, saved);
        id++;

        return saved;
    }

    @Override
    public Optional<Board> findById(final Long id) {
        return map.keySet().stream()
                .filter(key -> key.equals(id))
                .findAny()
                .map(map::get);
    }

    @Override
    public List<Board> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void deleteById(final Long id) {
        if (map.containsKey(id)) {
            map.remove(id);
        }
    }
}
