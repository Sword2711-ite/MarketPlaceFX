package marketplace.utils;

import marketplace.models.Producto;
import marketplace.models.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class DataStore {
    private static DataStore instance;
    private Map<Integer, Producto> productos;
    private Map<String, Usuario> usuarios;
    private int nextProductoId;

    private static final String PRODUCTOS_FILE = "productos.json";
    private static final String USUARIOS_FILE = "usuarios.json";
    private Gson gson;

    private DataStore() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        productos = new HashMap<>();
        usuarios = new HashMap<>();
        cargarDatos();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void cargarDatos() {
        // Cargar productos
        File productosFile = new File(PRODUCTOS_FILE);
        if (productosFile.exists()) {
            try (Reader reader = new FileReader(productosFile)) {
                Type type = new TypeToken<List<Producto>>(){}.getType();
                List<Producto> listaProductos = gson.fromJson(reader, type);
                if (listaProductos != null) {
                    productos = listaProductos.stream()
                            .collect(Collectors.toMap(Producto::getId, p -> p));
                    nextProductoId = productos.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
                cargarProductosEjemplo();
            }
        } else {
            cargarProductosEjemplo();
            guardarProductos();
        }

        // Cargar usuarios
        File usuariosFile = new File(USUARIOS_FILE);
        if (usuariosFile.exists()) {
            try (Reader reader = new FileReader(usuariosFile)) {
                Type type = new TypeToken<List<Usuario>>(){}.getType();
                List<Usuario> listaUsuarios = gson.fromJson(reader, type);
                if (listaUsuarios != null) {
                    usuarios = listaUsuarios.stream()
                            .collect(Collectors.toMap(Usuario::getUsername, u -> u));
                }
            } catch (IOException e) {
                e.printStackTrace();
                cargarUsuariosEjemplo();
            }
        } else {
            cargarUsuariosEjemplo();
            guardarUsuarios();
        }
    }

    private void cargarProductosEjemplo() {
        productos.put(1, new Producto(1, "Laptop Gaming", 1299.99, 10, "Electronica"));
        productos.put(2, new Producto(2, "Mouse Gaming", 59.99, 50, "Electronica"));
        productos.put(3, new Producto(3, "Teclado Mecanico", 89.99, 30, "Electronica"));
        productos.put(4, new Producto(4, "Monitor 27 pulgadas", 249.99, 15, "Electronica"));
        productos.put(5, new Producto(5, "Audifonos Bluetooth", 349.99, 20, "Audio"));
        nextProductoId = 6;
    }

    private void cargarUsuariosEjemplo() {
        usuarios.put("admin", new Usuario("admin", "admin123", "Administrador", "admin@marketplace.com", true));
        usuarios.put("juan", new Usuario("juan", "juan123", "Juan Perez", "juan@email.com", false));
        usuarios.put("maria", new Usuario("maria", "maria123", "Maria Lopez", "maria@email.com", false));
    }

    public void guardarProductos() {
        try (Writer writer = new FileWriter(PRODUCTOS_FILE)) {
            List<Producto> listaProductos = new ArrayList<>(productos.values());
            gson.toJson(listaProductos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarUsuarios() {
        try (Writer writer = new FileWriter(USUARIOS_FILE)) {
            List<Usuario> listaUsuarios = new ArrayList<>(usuarios.values());
            gson.toJson(listaUsuarios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Producto getProducto(int id) {
        return productos.get(id);
    }

    public List<Producto> getAllProductos() {
        return new ArrayList<>(productos.values());
    }

    public Usuario getUsuario(String username) {
        return usuarios.get(username);
    }

    public boolean validarUsuario(String username, String password) {
        Usuario usuario = usuarios.get(username);
        return usuario != null && usuario.getPassword().equals(password);
    }

    public void agregarProducto(Producto producto) {
        producto.setId(nextProductoId++);
        productos.put(producto.getId(), producto);
        guardarProductos();
    }

    public void actualizarProducto(Producto producto) {
        productos.put(producto.getId(), producto);
        guardarProductos();
    }

    public void eliminarProducto(int id) {
        productos.remove(id);
        guardarProductos();
    }

    public int getNextProductoId() {
        return nextProductoId;
    }
}