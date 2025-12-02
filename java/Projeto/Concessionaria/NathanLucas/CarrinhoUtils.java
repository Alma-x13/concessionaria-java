package Projeto.Concessionaria.NathanLucas;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoUtils {

    private static final String COOKIE_NAME = "carrinho";

    // ADICIONAR ITEM NO COOKIE
    public static void adicionarItem(Long id, HttpServletRequest request, HttpServletResponse response) {

        List<Long> ids = lerIds(request);
        ids.add(id);

        Cookie cookie = new Cookie(COOKIE_NAME, join(ids));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7); // 7 dias
        response.addCookie(cookie);
    }

    public static List<Long> lerIds(HttpServletRequest request) {
    List<Long> ids = new ArrayList<>();

    if (request.getCookies() == null) return ids;

    for (Cookie c : request.getCookies()) {
        if (c.getName().equals(COOKIE_NAME)) {

            String valor = c.getValue();

           
            if (valor == null || valor.isBlank() || valor.equals("null")) {
                return ids;
            }

            for (String p : valor.split("\\|")) {
            
                if (p == null || p.isBlank() || p.equals("null")) continue;

                try {
                    ids.add(Long.parseLong(p));
                } catch (NumberFormatException e) {
                    System.out.println("ID inválido no cookie: " + p); 
                   
                }
            }
        }
    }
    return ids;
}


    // LIMPAR COOKIE
    public static void limparCarrinho(HttpServletResponse response) {
    Cookie c = new Cookie("carrinho", "");
    c.setPath("/");
    c.setMaxAge(0);
    response.addCookie(c);
}


    public static void removerItem(Long id, HttpServletRequest request, HttpServletResponse response) {

    List<Long> ids = lerIds(request);

    
    ids.remove(id);

    Cookie cookie = new Cookie(COOKIE_NAME, join(ids));
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60 * 24 * 7); 
    response.addCookie(cookie);
}


    // MÉTODO QUE ESTAVA FALTANDO
    private static String join(List<Long> ids) {
        return String.join("|",
                ids.stream()
                   .map(String::valueOf)
                   .toList()
        );
    }


    
}
