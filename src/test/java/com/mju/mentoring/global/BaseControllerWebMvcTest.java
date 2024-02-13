package com.mju.mentoring.global;

import com.mju.mentoring.board.application.BoardService;
import com.mju.mentoring.board.ui.BoardController;
import com.mju.mentoring.member.application.auth.AuthService;
import com.mju.mentoring.member.domain.TokenManager;
import com.mju.mentoring.member.ui.auth.AuthController;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest({BoardController.class, AuthController.class})
public abstract class BaseControllerWebMvcTest {

    @MockBean
    protected BoardService boardService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected TokenManager<Long> tokenManager;
}
