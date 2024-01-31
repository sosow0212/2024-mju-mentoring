package com.mju.mentoring.global;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.test.context.TestExecutionListeners;

@Retention(RetentionPolicy.RUNTIME)
@TestExecutionListeners(listeners = DatabaseCleanListener.class, mergeMode = MERGE_WITH_DEFAULTS)
public @interface DatabaseCleaner {

}
