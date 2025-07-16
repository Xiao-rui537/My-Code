package com.xiaorui.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

/**
 * TokenUtil 工具类
 * 用于生成和解析 JWT Token，提供用户认证和授权功能。
 */
public class TokenUtil {

    // 密钥，用于签名和验证 Token 的合法性
    private static final String SECRET_KEY = "your-secret-key";

    // Token 的有效期（以毫秒为单位），这里设置为 1 小时
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;

    /**
     * 生成 JWT Token。
     *
     * @param username 用户名，作为 Token 的 subject
     * @param userId 用户 ID，作为自定义 Claim
     * @return 生成的 JWT Token
     */
    public static String generateToken(String username, String userId) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); // 使用 HMAC256 算法进行签名

        return JWT.create()
                .withSubject(username)  // 设置 Token 的主题（用户名）
                .withClaim("userId", userId)  // 添加自定义 Claim（用户 ID）
                .withIssuedAt(new Date())  // 设置 Token 的签发时间
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // 设置 Token 的过期时间
                .sign(algorithm);  // 使用指定的算法签名生成 Token
    }

    /**
     * 解析并验证 JWT Token。
     *
     * @param token 待验证的 JWT Token
     * @return 解码后的 Token 对象
     * @throws com.auth0.jwt.exceptions.JWTVerificationException 如果验证失败，会抛出异常
     */
    public static DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); // 使用与生成 Token 相同的算法进行验证
        return JWT.require(algorithm)  // 构建验证器
                .build()
                .verify(token);  // 验证并解析 Token
    }
}