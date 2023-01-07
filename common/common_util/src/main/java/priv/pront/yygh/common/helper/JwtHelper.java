package priv.pront.yygh.common.helper;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-07 10:49
 */
public class JwtHelper {

//    过期时间
    private static long tokenExpiration = 24 * 60 * 60 * 10;
//    tocken签名密钥
    private static String tokenSignKey = "123456";

    /**
     * 根据字符串生成Token
     * @param userId 用户ID
     * @param userName 用户名称
     * @return
     */
    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
//                分类
                .setSubject("YYGH-USER")
//                过期时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
//                签名哈希
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }


    /**
     * 根据token字符串得到用户ID
     * @param token token字符串
     * @return
     */
    public static Long getUserId(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }

    /**
     * 根据token字符串得到用户名称
     * @param token token字符串
     * @return
     */
    public static String getUserName(String token) {
        if (StringUtils.isEmpty(token)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("userName");
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "55");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUserName(token));
    }

}
