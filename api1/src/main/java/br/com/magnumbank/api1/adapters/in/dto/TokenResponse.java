package br.com.magnumbank.api1.adapters.in.dto;

public record TokenResponse(String token, String tokenType) {
    public TokenResponse(String token){
        this(token, "Bearer");
    }
}
