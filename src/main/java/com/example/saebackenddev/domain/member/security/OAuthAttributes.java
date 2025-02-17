package com.example.saebackenddev.domain.member.security;

import com.example.saebackenddev.domain.member.entity.MemberEntity;
import com.example.saebackenddev.domain.member.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey,
                           String username,
                           String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.username = username;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        throw new IllegalArgumentException("Unsupported OAuth provider: " + registrationId);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String username = extractUsernameFromEmail(email);

        return OAuthAttributes.builder()
                .username(username)
                .email(email)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .username(username)
                .email(email)
                .role(Role.USER)
                .build();
    }

    private static String extractUsernameFromEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
        return email.split("@")[0];
    }
}
