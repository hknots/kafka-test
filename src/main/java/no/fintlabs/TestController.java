package no.fintlabs;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final SecurityService securityService;

    @GetMapping
    public ResponseEntity<?> getData(@AuthenticationPrincipal Jwt jwt) {
        String username = (String) jwt.getClaims().get("cn");

        if (securityService.hasAccess(username, "elev")) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok().build();
    }

}
