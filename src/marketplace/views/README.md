# 🛍️ MarketPlaceFX

**MarketPlaceFX** es una aplicación de comercio electrónico desarrollada con JavaFX que simula una tienda virtual completa. Cuenta con sistema de autenticación, catálogo de productos, carrito de compras, panel de administración y **procesos concurrentes** para una experiencia fluida.

---

## 📋 Tabla de Contenidos
- [Características](#-características)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Instalación y Configuración](#-instalación-y-configuración)
- [Ejecución](#-ejecución)
- [Credenciales de Acceso](#-credenciales-de-acceso)
- [Funcionalidades](#-funcionalidades)
- [Programación Concurrente](#-programación-concurrente)
- [Interfaz Gráfica](#-interfaz-gráfica)
- [Arquitectura](#-arquitectura)
- [Datos de Ejemplo](#-datos-de-ejemplo)
- [Roadmap](#-roadmap)
- [Contribución](#-contribución)
- [Licencia](#-licencia)

---

## ✨ Características

| Característica | Descripción |
|----------------|-------------|
| 🔐 **Sistema de Autenticación** | Login seguro para clientes y administradores |
| 🛒 **Catálogo de Productos** | Visualización con diseño de tarjetas moderno |
| 🛍️ **Carrito de Compras** | Gestión completa de productos seleccionados |
| 👑 **Panel de Administración** | Gestión de productos (CRUD completo) |
| 🎨 **Interfaz Moderna** | Diseño responsive con CSS personalizado |
| 📦 **Gestión de Stock** | Control automático de inventario |
| 💰 **Cálculo de Totales** | Subtotal y total con formato de moneda |
| ⚡ **Carga Concurrente** | Pantallas de carga con barra de progreso en hilos secundarios |

---

## 🚀 Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| **Java** | 17+ | Lenguaje de programación principal |
| **JavaFX** | 21 | Framework para interfaz gráfica |
| **JDK** | 17+ | Java Development Kit |
| **Git** | - | Control de versiones |
| **CSS** | 3 | Estilos personalizados |

---

## 📁 Estructura del Proyecto

```
MarketPlaceFX/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── marketplace/
│       │       │
│       │       ├── Main.java                          # Punto de entrada principal
│       │       │
│       │       ├── models/                            # MODELOS DE DATOS
│       │       │   ├── Producto.java                  # Entidad Producto
│       │       │   ├── Usuario.java                   # Entidad Usuario
│       │       │   ├── Carrito.java                   # Entidad Carrito
│       │       │   └── ItemCarrito.java               # Ítem del carrito
│       │       │
│       │       ├── controllers/                       # CONTROLADORES
│       │       │   ├── UsuarioController.java         # Lógica de login/usuarios
│       │       │   ├── ProductoController.java        # Lógica de productos
│       │       │   └── CarritoController.java         # Lógica del carrito
│       │       │
│       │       ├── views/                             # VISTAS JAVAFX
│       │       │   ├── LoginView.java                 # Pantalla de login
│       │       │   ├── CatalogoView.java              # Catálogo de productos
│       │       │   ├── CarritoView.java               # Carrito de compras
│       │       │   └── AdminView.java                 # Panel administrador
│       │       │
│       │       ├── utils/                             # UTILIDADES
│       │       │   └── DataStore.java                 # Almacenamiento en memoria
│       │       │
│       │       └── tasks/                             # TAREAS CONCURRENTES
│       │           └── CargaProductosTask.java        # Task para carga en segundo plano
│       │
│       └── resources/
│           └── css/
│               └── style.css                          # Estilos CSS
│
├── .gitignore                                          # Archivos ignorados por Git
└── README.md                                           # Documentación
```

---

## 🔧 Instalación y Configuración

### 1. Prerrequisitos

#### Java JDK 17 o superior
```bash
# Verificar versión de Java
java -version
```

#### JavaFX SDK 21
Descargar desde: https://gluonhq.com/products/javafx/

### 2. Clonar el repositorio
```bash
git clone https://github.com/Sword2711-ite/MarketPlaceFX.git
cd MarketPlaceFX
```

### 3. Configurar en IntelliJ IDEA

| Paso | Acción |
|------|--------|
| 1 | File → Open → Seleccionar `MarketPlaceFX` |
| 2 | File → Project Structure → Project → SDK: JDK 17+ |
| 3 | File → Project Structure → Libraries → + → Java → `C:\javafx-sdk-21\lib` |
| 4 | Run → Edit Configurations → + → Application |
| 5 | Main class: `marketplace.Main` |
| 6 | VM options: `--module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml` |

---

## ▶️ Ejecución

### Opción 1: IntelliJ IDEA
Hacer clic en el botón verde ▶️ Run

### Opción 2: Línea de comandos
```bash
javac --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -d out src/marketplace/**/*.java
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp out marketplace.Main
```

---

## 🔑 Credenciales de Acceso

### 👑 Administrador
| Campo | Valor |
|----------------|------------|
| **Usuario** | `admin` |
| **Contraseña** | `admin123` |

### 👤 Clientes de Prueba
| Usuario | Contraseña | Nombre |
|----------|-------------|--------------|
| `juan` | `juan123` | Juan Pérez |
| `maria` | `maria123` | María López |
| `carlos` | `carlos123` | Carlos Ruiz |

---

## 🎯 Funcionalidades

### 🔐 Módulo de Autenticación
- Login seguro con validación de credenciales
- Redirección automática según rol (Admin/Cliente)

### 🛒 Módulo Cliente
- **Catálogo**: Visualización de productos en tarjetas con imagen, precio, stock y selector de cantidad
- **Carrito**: Agregar productos, modificar cantidades, eliminar items, ver total
- **Compra**: Proceso de finalización con confirmación

### 👑 Módulo Administrador
- **Ver productos**: Listado completo
- **Agregar producto**: Formulario para nuevos productos
- **Eliminar producto**: Remover productos del catálogo
- **Ver usuarios**: Lista de usuarios registrados

---
## ⚡⚡ UPDATE 02/05/2026 ⚡⚡
## Programación Concurrente

### Descripción 
La aplicación implementa **procesos concurrentes** mediante `javafx.concurrent.Task` para evitar bloqueos en la interfaz gráfica durante la carga de datos.

### ¿Qué problema resuelve?
En aplicaciones reales, la carga de registros (usuarios, productos, etc.) desde una fuente externa puede tomar varios segundos. Si esta operación se ejecuta en el **hilo principal de JavaFX**, la interfaz se congela completamente y el usuario no puede interactuar.

### Solución implementada

| Aspecto | Detalle |
|---------|---------|
| **Clase** | `CargaProductosTask` (extiende `javafx.concurrent.Task<Void>`) |
| **Ubicación** | `src/marketplace/tasks/CargaProductosTask.java` |
| **Módulos afectados** | `CatalogoView` y `AdminView` |
| **Visualización** | Pantalla de carga con `ProgressBar` y mensajes dinámicos |
| **Ejecución** | Hilo secundario (`Thread`) con `setDaemon(true)` |

### Flujo de ejecución

```
Login → Pantalla de Carga (ProgressBar + mensaje dinámico)
              ↓
      Hilo secundario (Task) simula carga de datos
              ↓
      Al completar → muestra Catálogo / Panel Admin
```

### Características del hilo concurrente
- **Mensajes dinámicos**: "Conectando con el servidor...", "Cargando Catálogo... 3 de 5 productos", "Finalizando..."
- **Barra de progreso**: Avance visual de 0% a 100% en tiempo real
- **No bloqueante**: La UI permanece responsiva durante toda la carga
- **Preparado para escalar**: Diseñado para integrar fácilmente con APIs, archivos o bases de datos reales

### Código clave
```java
// Crear tarea concurrente
CargaProductosTask task = new CargaProductosTask("Catálogo");

// Vincular con componentes de la UI
progressBar.progressProperty().bind(task.progressProperty());
statusLabel.textProperty().bind(task.messageProperty());

// Al completar, mostrar la vista final
task.setOnSucceeded(e -> mostrarCatalogoReal(usuario));

// Ejecutar en hilo secundario
Thread hiloCarga = new Thread(task);
hiloCarga.setDaemon(true);
hiloCarga.start();
```

### Beneficios obtenidos

| Aspecto | Antes | Después |
|---------|-------|---------|
| **Rendimiento** | Carga en hilo principal (bloqueante) | Carga en hilo secundario (no bloqueante) |
| **Experiencia de usuario** | Pantalla congelada sin feedback | Pantalla de carga con progreso en tiempo real |
| **Fluidez** | UI irresponsiva durante carga | UI completamente responsiva, transiciones suaves |
| **Escalabilidad** | Imposible cargar grandes volúmenes | Preparado para cargar datos reales sin bloqueos |

---
## ⚡⚡ FIN DE "UPDATE" 02/05/2026 ⚡⚡

## 🎨 Interfaz Gráfica

### Estilos CSS Implementados
- Gradiente morado (#667eea → #764ba2) en pantalla de login y pantallas de carga
- Tarjetas de productos con sombra y efecto hover
- Botones con colores diferenciados (primario, éxito, peligro)
- Bordes redondeados y tipografía moderna
- Pantallas de carga con tarjeta blanca centrada y ProgressBar estilizado

---

## 🏗️ Arquitectura

### Patrón (Model-View-Controller)

```
Vista (View) → Controlador (Controller) → Modelo (Model)
      ↑                                            ↓
      └────────────────────────────────────────────┘
```

- **Modelos**: Producto, Usuario, Carrito, ItemCarrito
- **Controladores**: UsuarioController, ProductoController, CarritoController
- **Vistas**: LoginView, CatalogoView, CarritoView, AdminView
- **Utils**: DataStore (almacenamiento en memoria)
- **Tasks**: CargaProductosTask (procesos concurrentes)

---

## 📊 Datos de Ejemplo

### Productos Precargados

| ID | Nombre | Precio | Stock | Categoría |
|----|---------------------|-----------|-------|-------------|
| 1 | Laptop Gaming | $1,299.99 | 10 | Electrónica |
| 2 | Mouse Gaming | $59.99 | 50 | Electrónica |
| 3 | Teclado Mecánico | $89.99 | 30 | Electrónica |
| 4 | Monitor 27" | $249.99 | 15 | Electrónica |
| 5 | Audífonos Bluetooth | $349.99 | 20 | Audio |

### Usuarios Precargados

| Usuario | Nombre | Rol |
|---------|---------------|---------|
| admin | Administrador | Admin |
| juan | Juan Pérez | Cliente |
| maria | María López | Cliente |

---

## 🗺️ Roadmap

- [x] Sistema de autenticación (login/logout)
- [x] Catálogo de productos con tarjetas
- [x] Carrito de compras funcional
- [x] Panel de administración básico
- [x] **Programación concurrente con Task y ProgressBar**
- [ ] Persistencia de datos (archivos/JSON)
- [ ] Registro de nuevos usuarios
- [ ] Edición y eliminación de productos (Admin)
- [ ] Historial de compras por usuario
- [ ] Búsqueda y filtros en catálogo
- [ ] Imágenes de productos

---

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

---

## 📝 Licencia

Este proyecto es de uso académico para el curso de **Tópicos Avanzados de Programación** del Instituto Tecnológico de Ensenada.

---

**Hecho con ❤️ para el curso de Tópicos Avanzados de Programación**
