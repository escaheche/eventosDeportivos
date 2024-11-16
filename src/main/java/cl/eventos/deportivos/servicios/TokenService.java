package cl.eventos.deportivos.servicios;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import cl.eventos.deportivos.modelos.Usuario;

@Service
public class TokenService {

    // Aquí puedes almacenar los tokens en una base de datos o en caché como Redis
    // Por simplicidad, uso un mapa en memoria. En producción, deberías usar una DB o caché.
    private Map<Usuario, String> tokenStore = new HashMap<>();

    // Revocar el token anterior de un usuario
    public void revokeTokenByUser(Usuario usuario) {
        if (tokenStore.containsKey(usuario)) {
            tokenStore.remove(usuario); // Eliminar el token anterior
        }
    }

    // Guardar un nuevo token para un usuario
    public void saveTokenForUser(String token, Usuario usuario) {
        tokenStore.put(usuario, token); // Guardar el nuevo token
    }

    // Revocar un token específico
    public void revokeToken(String token) {
        tokenStore.values().removeIf(existingToken -> existingToken.equals(token));
    }

    // Validar si un token es válido o no
    public boolean isTokenValid(String token) {
        return tokenStore.containsValue(token);
    }
}
