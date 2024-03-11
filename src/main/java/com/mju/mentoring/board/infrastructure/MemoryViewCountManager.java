package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.ViewCountManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MemoryViewCountManager implements ViewCountManager {

    private final Map<Long, Set<Long>> views = new HashMap<>();

    @Override
    public boolean isAlreadyRead(final Long boardId, final Long writerId) {
        initSet(boardId);
        return views.get(boardId)
            .contains(writerId);
    }

    @Override
    public void read(final Long boardId, final Long writerId) {
        initSet(boardId);
        views.get(boardId)
            .add(writerId);
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void clearSet() {
        views.clear();
    }

    private void initSet(final Long boardId) {
        if (!views.containsKey(boardId)) {
            views.put(boardId, new HashSet<>());
        }
    }
}
