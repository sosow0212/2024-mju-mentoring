package com.mju.mentoring.global;

import com.mju.mentoring.board.application.BoardService;
import com.mju.mentoring.board.ui.BoardController;
import com.mju.mentoring.member.application.auth.AuthService;
import com.mju.mentoring.member.application.member.MemberService;
import com.mju.mentoring.member.domain.TokenManager;
import com.mju.mentoring.member.ui.auth.AuthController;
import com.mju.mentoring.member.ui.member.MemberController;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest({BoardController.class, AuthController.class, MemberController.class})
@AutoConfigureRestDocs
public abstract class BaseControllerWebMvcTest {

    @MockBean
    protected BoardService boardService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected TokenManager<Long> tokenManager;

    @MockBean
    protected MemberService memberService;
}
