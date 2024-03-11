package com.mju.mentoring.board.application;

import com.mju.mentoring.member.domain.MemberNickNameChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardEventHandler {

    private final BoardService boardService;

    @EventListener
    public void changeWriter(final MemberNickNameChangedEvent event) {
        boardService.changeWriterName(event.getMemberId(), event.getNewNickname());
    }
}
