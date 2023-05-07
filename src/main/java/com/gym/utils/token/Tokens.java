package com.gym.utils.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 71made
 * @Date: 2023/05/04 02:33
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public final class Tokens {

    /**
     * 默认过期时间
     */
    private static final long DEFAULT_EXPIRED_TIME = 24 * 60 * 60 * 1000;

    /**
     * 默认密钥
     */
    private static final String DEFAULT_SECRET_KEY = "Oc2X2EK6EAB4AJon";

    //
    // 构建 Token
    // ----------------------------------------------------------------------------------------------------
    public static Token createToken(String secretKey, Map<String, String> claimMap, long expiredTime) {
        // 过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expiredTime);
        // 设置秘钥和加密算法
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        // 设置 token 头部
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        // 携带参数生成 token
        JWTCreator.Builder builder = JWT.create()
                .withHeader(header);

        for (Map.Entry<String, String> claim : claimMap.entrySet()) {
            builder.withClaim(claim.getKey(), claim.getValue());
        }
        // 并附上签名
        String tokenStr = builder.withExpiresAt(expireDate).sign(algorithm);
        return new Token(tokenStr);
    }

    public static Token createToken(String secretKey, Claims claims, long expiredTime) {
        return createToken(secretKey, claims.claimMap, expiredTime);
    }

    public static Token createToken(Map<String, String> claimMap, long expiredTime) {
        return createToken(DEFAULT_SECRET_KEY, claimMap, expiredTime);
    }

    public static Token createToken(Claims claims, long expiredTime) {
        return createToken(DEFAULT_SECRET_KEY, claims, expiredTime);
    }

    public static Token createToken(String secretKey, long expiredTime) {
        return createToken(secretKey, new HashMap<>(), expiredTime);
    }

    public static Token createToken(long expiredTime) {
        return createToken(DEFAULT_SECRET_KEY, expiredTime);
    }

    public static Token createToken(Map<String, String> claimMap) {
        return createToken(DEFAULT_SECRET_KEY, claimMap, DEFAULT_EXPIRED_TIME);
    }

    public static Token createToken(Claims claims) {
        return createToken(DEFAULT_SECRET_KEY, claims, DEFAULT_EXPIRED_TIME);
    }

    public static Token createToken() {
        return createToken(DEFAULT_EXPIRED_TIME);
    }

    //
    // 解析 Token
    // ----------------------------------------------------------------------------------------------------
    public static String analysis(String tokenStr, String claimKey) {
        return JWT.decode(tokenStr).getClaim(claimKey).asString();
    }

    public static String analysis(Token token, String claimKey) {
        return analysis(token.getToken(), claimKey);
    }

    //
    // 校验 Token
    // ----------------------------------------------------------------------------------------------------
    public static boolean verify(String secretKey, String tokenStr) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            verifier.verify(tokenStr);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public static boolean verify(String tokenStr) {
        return verify(DEFAULT_SECRET_KEY, tokenStr);
    }

    public static boolean verify(Token token) {
        return verify(DEFAULT_SECRET_KEY, token.getToken());
    }

    //
    // Token 过期检验
    // ----------------------------------------------------------------------------------------------------
    public static boolean isExpired(String tokenStr) {
        return JWT.decode(tokenStr).getExpiresAt().before(new Date());
    }

    public static boolean isExpired(Token token) {
        return isExpired(token.getToken());
    }

    public static boolean isAlmostExpired(String tokenStr, long deadline) {
        return JWT.decode(tokenStr).getExpiresAt().getTime() - deadline <= 0;
    }

    public static boolean isAlmostExpired(Token token, long deadline) {
        return isAlmostExpired(token.getToken(), deadline);
    }

    //
    // VerifyToken 注解检测
    // ----------------------------------------------------------------------------------------------------
    public static boolean hasVerifyToken(Class<?> clz) {
        if (clz.isAnnotationPresent(VerifyToken.class)) {
            return clz.getAnnotation(VerifyToken.class).required();
        }
        return false;
    }

    public static boolean hasVerifyToken(Method method) {
        if (method.isAnnotationPresent(VerifyToken.class)) {
            return method.getAnnotation(VerifyToken.class).required();
        }
        return hasVerifyToken(method.getDeclaringClass());
    }

    /**
     * 封装 claimMap
     */
    public static final class Claims {
        private Claims() {}
        private final Map<String, String> claimMap = new HashMap<>();

        public Claims add(String key, String value) {
            this.claimMap.put(key, value);
            return this;
        }

        public Claims add(Map<String, String> map) {
            this.claimMap.putAll(map);
            return this;
        }

        public Claims remove(String key) {
            this.claimMap.remove(key);
            return this;
        }

        public boolean contains(String key) {
            return this.claimMap.containsKey(key);
        }
    }

    public static Claims claims() {
        return new Claims();
    }
}
