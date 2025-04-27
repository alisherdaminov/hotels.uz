package hotels.uz.util;

import hotels.uz.dto.Auth.JwtDTO;
import hotels.uz.enums.ProfileRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUtil {
    private static final int tokenLiveTime = 2000 * 7200 * 48; // 2-day
    private static final String secretKey = "veryLongSecretmazgillattayevlasharaaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgiveryLongSecretmazgillattayevlasharaaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgiveryLongSecretmazgillattayevlasharaaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgi";

    public static String encode(String username, Integer userId, List<ProfileRole> profileRoleList) {
        String strEnumList = profileRoleList.stream().map(Enum::name).collect(Collectors.joining(","));
        Map<String, String> extraClaims = new HashMap<>();
        extraClaims.put("username", username);
        extraClaims.put("userId", userId.toString());
        extraClaims.put("roles", strEnumList);
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new java.util.Date(System.currentTimeMillis()))
                .expiration(new java.util.Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSecretKey())
                .compact();
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        Integer userId = Integer.valueOf((String) claims.get("userId"));
        String strRole = (String) claims.get("roles");
        List<ProfileRole> roleEnumList = Arrays.stream(strRole.split(",")).map(ProfileRole::valueOf).toList();
        return new JwtDTO(userId, username, roleEnumList);
    }


    private static SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
