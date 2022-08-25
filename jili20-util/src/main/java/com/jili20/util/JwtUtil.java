package com.jili20.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * Security 权限控制工具类
 *
 * @author bing_  @create 2022/1/11-11:14
 */
public class JwtUtil {

    /**
     * 有效期为
     * 60 * 60 * 2 * 1000L  2个小时 （ 单位毫秒吗？ ）
     */
    public static final Long JWT_TTL = 60 * 60 * 12 * 1000L;
    /**
     * 设置秘钥明文
     * 本类最底下 main 方法生成的 md 5 jwt 密钥
     */
    public static final String JWT_KEY = "4fdfc0ac8fcfd9ff4bd3d858be131b43";

    public static String getUUID() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * 生成 jtw - 没有过期时间参数
     *
     * @param subject token中要存放的数据（json格式）
     * @return
     */
    public static String createJWT(String subject) {
        // 设置过期时间 空
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    /**
     * 生成 jtw - 有过期时间参数
     *
     * @param subject   token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    /**
     *
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createRefreshJWT(String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    /**
     * 生成 JWT - 上面调用的方法
     *
     * @param subject
     * @param ttlMillis
     * @param uuid
     * @return
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                // 唯一的ID
                .setId(uuid)
                // 主题  可以是JSON数据
                .setSubject(subject)
                // 签发者
                .setIssuer("jili20")
                // 签发时间
                .setIssuedAt(now)
                // 使用 HS256 对称加密算法签名, 第二个参数为秘钥
                .signWith(signatureAlgorithm, secretKey)
                .setExpiration(expDate);
    }

    /**
     * 创建 token
     *
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    /**
     * 测试 token - 生成 - 解析
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        /**
         * 生成 JWT
         */
        //String jwt = createJWT("123456");
        //System.out.println(jwt);

        /*
         * 还原 JWT 为 123456
         * 时间过期会报错，须重新生成再解析
         */
        Claims claims = parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3YmE5ODdkZjljN2Y0NmEwYmY4YTQ2YjAyZDNjZmZhNiIsInN1YiI6IjEiLCJpc3MiOiJqaWxpMjAiLCJpYXQiOjE2NDIwNzgyNjUsImV4cCI6MTY0MjE4NjI2NX0.tXP9D6LLHP03oneFRQsOZYA9OR-fjD_3aUYlE6SgKFo");
        String subject = claims.getSubject();
        System.out.println(subject);
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    //public static void main(String[] args) {
    //    /**
    //     * 生成 md 5 jwt 4fdfc0ac8fcfd9ff4bd3d858be131b43 密钥
    //     *
    //     */
    //    String jwtKeyMd5Str = DigestUtils.md5DigestAsHex("jili20".getBytes());
    //    System.out.println(jwtKeyMd5Str);
    //}

}

