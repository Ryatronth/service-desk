package ru.ryatronth.sd.security.keycloak.groups;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class GroupPathSplitter {

    private static final Pattern SLASH_SPLIT = Pattern.compile("/+");

    public static List<String> split(String path) {
        if (path == null) {
            return List.of();
        }
        String p = path.trim();
        if (p.isEmpty()) {
            return List.of();
        }

        if (p.startsWith("/")) {
            p = p.substring(1);
        }
        if (p.endsWith("/")) {
            p = p.substring(0, p.length() - 1);
        }
        if (p.isBlank()) {
            return List.of();
        }

        String[] parts = SLASH_SPLIT.split(p);
        List<String> out = new ArrayList<>(parts.length);
        for (String part : parts) {
            if (part == null) {
                continue;
            }
            String s = part.trim();
            if (!s.isEmpty()) {
                out.add(s);
            }
        }
        return out;
    }

    public static boolean eq(String a, String b) {
        return a != null && a.equalsIgnoreCase(b);
    }

    public static String normSegment(String s) {
        if (s == null) {
            return "";
        }
        return s.trim().replaceAll("^/+", "").replaceAll("/+$", "");
    }

}
