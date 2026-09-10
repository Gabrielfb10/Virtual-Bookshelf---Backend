package com.acirio.virtual_bookshelf.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenProvider {

    @Value("${jwt.expiration}")
    private Long expírationTime;

    @Value("${jwt.secret}")
    private String key;

    public String generateToken(Authentication authentication) { //o authentication é o metodo que guarda as informações do usuario no formato UserDetails quando a autenticação é bem sucessidade
        UserDetails user = (UserDetails) authentication.getPrincipal(); //Pega o objeto do usuario autenticado e o cast serve para afirmar que o formato desse objeto é sim UserDetails
        return buildToken(user.getUsername());
    }

    private String buildToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expírationTime);
        //Contem as informações que o token tera
        return Jwts.builder()
                .subject(username) //O subject é o identificador do usuario, poder ser username, email ou qualquer outra coisa, nesse caso, é o email
                .issuedAt(now) //Momento da criação
                .expiration(expiration) //Tempo de expiração
                .signWith(getSigningKey()) //Assinatura do sistema
                .compact(); //Compacta tudo
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(key.getBytes()); //Gera uma assinatura a partir dos bytes chave secreta declarada
    }

    public boolean isTokenValid(String token) {
        try{
            getClaims(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject(); //Metodo que obetem o subject contido no payload do token
    }

    private Claims getClaims(String token) {
        return Jwts.parser()// O parser é responsavel por pegar a string que o token é e identificar as suas partes (header, payload e assinatura)
                .verifyWith(getSigningKey()) //Define qual chave de assinatura deve ser usada para fazer a verificação e outras regras que poderiam ser necessarias
                .build()// Constroi o parser que age como um analisador
                .parseSignedClaims(token) // A partir da assinatura passada no metodo anterior, o parser checa se o token nao está expirado, se esta formado corretamente e se possui a assinatura correta, caso contrario, interrompe o fluxo
                .getPayload(); // Apos as validacoes, pega o payload do token, a parte que contem as informações relevantes do usuario
    }
}
