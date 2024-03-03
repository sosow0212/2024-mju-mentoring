package com.mju.mentoring.board.domain;

public interface ViewCountManager {

    boolean isAlreadyRead(final Long boardId, final Long writerId);

    void read(final Long boardId, Long writerId);
}
