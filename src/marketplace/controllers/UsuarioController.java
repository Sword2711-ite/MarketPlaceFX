package marketplace.controllers;

import marketplace.models.Usuario;
import marketplace.utils.DataStore;

public class UsuarioController {
    private DataStore dataStore;
    private Usuario usuarioActual;

    public UsuarioController() {
        this.dataStore = DataStore.getInstance();
    }

    public boolean login(String username, String password) {
        if (dataStore.validarUsuario(username, password)) {
            usuarioActual = dataStore.getUsuario(username);
            return true;
        }
        return false;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean isAdmin() {
        return usuarioActual != null && usuarioActual.isEsAdmin();
    }
}