package com.example.taskTracker.util;

import com.example.taskTracker.security.AppUserDetails;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

public class SecurityUtils {
    public static Mono<AppUserDetails> getCurrentUserDetails() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (AppUserDetails) ctx.getAuthentication().getPrincipal());
    }
}
