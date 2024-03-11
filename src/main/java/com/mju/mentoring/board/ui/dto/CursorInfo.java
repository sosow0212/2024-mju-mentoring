package com.mju.mentoring.board.ui.dto;

import com.mju.mentoring.board.domain.Board;
import java.util.List;
import org.springframework.util.CollectionUtils;

public record CursorInfo(Long lastId, boolean hasMorePage) {

    public static CursorInfo from(final List<Board> data, final int size) {
        if (CollectionUtils.isEmpty(data)) {
            return new CursorInfo(null, false);
        }

        boolean hasMorePage = true;
        int dataSize = data.size();
        if (dataSize < size) {
            hasMorePage = false;
        }

        return new CursorInfo(data.get(dataSize - 1).getId(), hasMorePage);
    }
}
