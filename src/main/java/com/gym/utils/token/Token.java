package com.gym.utils.token;

/**
 * @Author: 71made
 * @Date: 2023/05/04 02:33
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public class Token {

    // 生成的 token
    private String token;

    public Token() {}

    public Token(String tokenStr) {
        this.token = tokenStr;
    }

    @Override
    public String toString() {
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
