# 🛍️ MarketPlaceFX

**MarketPlaceFX** es una aplicación de comercio electrónico desarrollada con JavaFX que simula una tienda virtual completa. Cuenta con sistema de autenticación, catálogo de productos, carrito de compras, panel de administración completo (CRUD), persistencia de datos mediante archivos JSON y procesos concurrentes para una experiencia fluida.

---

# 📋 Tabla de Contenidos

- [Características](#-características)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Instalación y Configuración](#-instalación-y-configuración)
- [Ejecución](#-ejecución)
- [Credenciales de Acceso](#-credenciales-de-acceso)
- [Funcionalidades](#-funcionalidades)
- [Persistencia de Datos](#-persistencia-de-datos)
- [Programación Concurrente](#-programación-concurrente)
- [Interfaz Gráfica](#-interfaz-gráfica)
- [Arquitectura](#-arquitectura)
- [Validaciones Implementadas](#-validaciones-implementadas)
- [Problemas Técnicos Resueltos](#-problemas-técnicos-resueltos)
- [Datos de Ejemplo](#-datos-de-ejemplo)
- [Roadmap](#-roadmap)
- [Contribución](#-contribución)
- [Licencia](#-licencia)

---

# ✨ Características

| Característica | Descripción |
|---|---|
| 🔐 **Sistema de Autenticación** | Login seguro para clientes y administradores |
| 🛒 **Catálogo de Productos** | Visualización con diseño de tarjetas moderno |
| 🛍️ **Carrito de Compras** | Gestión completa de productos seleccionados |
| 👑 **Panel de Administración** | Gestión completa de productos (CRUD) |
| 💾 **Persistencia JSON** | Guardado automático de productos en archivos JSON |
| 🎨 **Interfaz Moderna** | Diseño responsive con CSS personalizado |
| 📦 **Gestión de Stock** | Control automático de inventario |
| 💰 **Cálculo de Totales** | Subtotal y total con formato de moneda |
| ⚡ **Carga Concurrente** | Pantallas de carga con barra de progreso en hilos secundarios |
| ✅ **Validación de Stock** | Verificación antes de agregar al carrito y antes de comprar |

---

# 🚀 Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|---|---|---|
| **Java** | 17+ | Lenguaje de programación principal |
| **JavaFX** | 17 / 21 / 25 | Framework para interfaz gráfica |
| **JDK** | 17+ | Java Development Kit |
| **Gson** | 2.10.1 | Serialización y deserialización JSON |
| **CSS** | 3 | Estilos personalizados |
| **Git** | - | Control de versiones |

---

# 📁 Estructura del Proyecto

```text
MarketPlaceFX/
├── src/
│   └── marketplace/
│       ├── Main.java
│       ├── models/
│       │   ├── Producto.java
│       │   ├── Usuario.java
│       │   ├── Carrito.java
│       │   └── ItemCarrito.java
│       ├── controllers/
│       │   ├── UsuarioController.java
│       │   ├── ProductoController.java
│       │   └── CarritoController.java
│       ├── views/
│       │   ├── LoginView.java
│       │   ├── CatalogoView.java
│       │   ├── CarritoView.java
│       │   └── AdminView.java
│       ├── utils/
│       │   └── DataStore.java
│       └── tasks/
│           └── CargaProductosTask.java
├── libs/
│   └── gson-2.10.1.jar
├── productos.json
├── usuarios.json
└── README.md
```

---

# 🔧 Instalación y Configuración

## Prerrequisitos

- Java JDK 17 o superior
- JavaFX SDK compatible con tu JDK
- Gson 2.10.1

## Configuración en IntelliJ IDEA

1. File → Open → Seleccionar `MarketPlaceFX`
2. File → Project Structure → Project → SDK: `JDK 17+`
3. File → Project Structure → Libraries → + → Java → Seleccionar `gson-2.10.1.jar`
4. Run → Edit Configurations → + → Application
5. Main class: `marketplace.Main`
6. VM options:

```bash
--module-path "C:\javafx-sdk-25\lib" --add-modules javafx.controls,javafx.fxml
```

---

# ▶️ Ejecución

## Opción 1: IntelliJ IDEA

Hacer clic en el botón verde ▶️ Run.

## Opción 2: Línea de comandos

```bash
javac -cp "libs\gson-2.10.1.jar" -d out --module-path "C:\javafx-sdk-25\lib" --add-modules javafx.controls,javafx.fxml src/marketplace/**/*.java

java -cp "out;libs\gson-2.10.1.jar" --module-path "C:\javafx-sdk-25\lib" --add-modules javafx.controls,javafx.fxml marketplace.Main
```

---

# 🔑 Credenciales de Acceso

## 👑 Administrador

| Campo | Valor |
|---|---|
| Usuario | `admin` |
| Contraseña | `admin123` |

## 👤 Clientes de Prueba

| Usuario | Contraseña | Nombre |
|---|---|---|
| `juan` | `juan123` | Juan Pérez |
| `maria` | `maria123` | María López |

---

# 🎯 Funcionalidades

## 🔐 Módulo de Autenticación

- Login seguro con validación de credenciales
- Redirección automática según rol (Administrador / Cliente)

## 🛒 Módulo Cliente

- Catálogo de productos con tarjetas modernas
- Visualización de precio, stock y categoría
- Selector de cantidad
- Carrito de compras funcional
- Agregar y eliminar productos
- Cálculo automático de subtotal y total
- Compra con validación de stock
- Descuento automático de inventario al comprar

## 👑 Módulo Administrador

- Ver productos registrados
- Agregar nuevos productos
- Editar productos existentes
- Eliminar productos
- Ver usuarios registrados
- Persistencia automática de cambios

---

# 💾 Persistencia de Datos

La aplicación implementa persistencia completa mediante archivos JSON, garantizando que los datos no se pierdan al cerrar la aplicación.

## Archivos generados

| Archivo | Descripción |
|---|---|
| `productos.json` | Lista completa de productos |
| `usuarios.json` | Usuarios registrados |

## Datos que persisten

- Productos agregados por el administrador
- Modificaciones de productos
- Eliminación de productos
- Cambios de stock después de compras
- Usuarios registrados

## Tecnología utilizada

Se utiliza la librería **Gson 2.10.1** de Google para serialización y deserialización de objetos Java a formato JSON.

---

# ⚡ Programación Concurrente

La aplicación implementa procesos concurrentes mediante `javafx.concurrent.Task` para evitar bloqueos en la interfaz gráfica durante la carga de datos.

## ¿Qué problema resuelve?

La carga de registros desde una fuente externa puede tomar varios segundos. Si esta operación se ejecuta en el hilo principal de JavaFX, la interfaz se congela completamente.

## Implementación

| Aspecto | Detalle |
|---|---|
| Clase | `CargaProductosTask` |
| Hereda de | `javafx.concurrent.Task<Void>` |
| Ubicación | `src/marketplace/tasks/CargaProductosTask.java` |
| Módulos afectados | `CatalogoView` |
| Visualización | `ProgressBar` + mensajes dinámicos |
| Ejecución | Hilo secundario (`Thread`) |

## Flujo de ejecución

```text
Login
   ↓
Pantalla de Carga
(ProgressBar + mensaje dinámico)
   ↓
Hilo secundario carga productos reales desde DataStore
   ↓
Muestra Catálogo
```

## Código principal

```java
CargaProductosTask task = new CargaProductosTask("Catálogo");

progressBar.progressProperty().bind(task.progressProperty());
statusLabel.textProperty().bind(task.messageProperty());

task.setOnSucceeded(e -> mostrarCatalogoReal(usuario));

Thread hiloCarga = new Thread(task);
hiloCarga.setDaemon(true);
hiloCarga.start();
```

## Beneficios obtenidos

- Interfaz fluida y responsiva
- Evita congelamientos
- Experiencia de usuario más profesional
- Escalable para futuras conexiones con bases de datos o APIs

---

# 🎨 Interfaz Gráfica

## Estilos CSS implementados

- Gradiente morado (`#667eea → #764ba2`) en login y pantallas de carga
- Tarjetas de productos con sombra y efecto hover
- Botones con colores diferenciados
- Bordes redondeados
- Diseño responsive
- Tipografía moderna
- Pantallas de carga con ProgressBar estilizado

---

# 🏗️ Arquitectura

## Patrón MVC (Model - View - Controller)

```text
Vista (View)
      ↓
Controlador (Controller)
      ↓
Modelo (Model)
      ↓
DataStore / JSON
```

## Componentes

### Modelos

- Producto
- Usuario
- Carrito
- ItemCarrito

### Vistas

- LoginView
- CatalogoView
- CarritoView
- AdminView

### Controladores

- UsuarioController
- ProductoController
- CarritoController

### Utilidades

- DataStore

### Tasks

- CargaProductosTask

---

# ✅ Validaciones Implementadas

- Validación de credenciales de usuario
- Validación de stock antes de agregar productos
- Validación antes de finalizar compras
- Prevención de stock negativo
- Validación de campos obligatorios
- Restricción de cantidades inválidas

---

# 🧠 Problemas Técnicos Resueltos

- Congelamiento de interfaz durante carga de datos
- Persistencia automática de información
- Actualización dinámica de stock
- Separación modular mediante MVC
- Comunicación entre vistas y controladores
- Gestión concurrente de procesos de carga

---

# 📊 Datos de Ejemplo

## Productos Precargados

| ID | Nombre | Precio | Stock | Categoría |
|---|---|---|---|---|
| 1 | Laptop Gaming | $1,299.99 | 10 | Electrónica |
| 2 | Mouse Gaming | $59.99 | 50 | Electrónica |
| 3 | Teclado Mecánico | $89.99 | 30 | Electrónica |
| 4 | Monitor 27 pulgadas | $249.99 | 15 | Electrónica |
| 5 | Audífonos Bluetooth | $349.99 | 20 | Audio |

## Usuarios Precargados

| Usuario | Nombre | Rol |
|---|---|---|
| admin | Administrador | Admin |
| juan | Juan Pérez | Cliente |
| maria | María López | Cliente |

---

# 🗺️ Roadmap

## ✅ Implementado

- Sistema de autenticación
- Catálogo de productos
- Carrito de compras
- CRUD completo de productos
- Persistencia JSON
- Validación de stock
- Descuento automático de inventario
- Programación concurrente con `Task`
- Interfaz moderna con CSS

## 🔜 Pendiente

- Registro de nuevos usuarios
- Historial de compras por usuario
- Búsqueda y filtros
- Imágenes de productos
- Integración con base de datos

---

# 🤝 Contribución

1. Fork el proyecto
2. Crear una rama:

```bash
git checkout -b feature/nueva-funcionalidad
```

3. Commit de cambios:

```bash
git commit -m "Agrega nueva funcionalidad"
```

4. Push a la rama:

```bash
git push origin feature/nueva-funcionalidad
```

5. Abrir un Pull Request

---

# 📝 Licencia

Este proyecto es de uso académico para el curso de **Tópicos Avanzados de Programación** del Instituto Tecnológico de Ensenada.

---

# ❤️ Autor

Hecho con ❤️ para el curso de Tópicos Avanzados de Programación.

