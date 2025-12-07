# 🌐 SimpleRest - Aplicación Android con API REST

<div align="center">

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpack-compose&logoColor=white)

</div>

## 📝 Descripción

**SimpleRest** es una aplicación Android moderna desarrollada en **Kotlin** que demuestra las mejores prácticas para consumir APIs REST y gestionar datos locales. La aplicación consume la API pública [RandomUser.me](https://randomuser.me/api/) para obtener perfiles de usuarios aleatorios, los almacena en una base de datos local SQLite utilizando **Room**, y presenta los datos a través de una interfaz de usuario moderna construida con **Jetpack Compose**.

### 🎯 Objetivo del Proyecto

Este proyecto sirve como una demostración completa de arquitectura Android moderna, implementando:
- Consumo de API REST con manejo de errores
- Persistencia de datos local con SQLite/Room
- Interfaz de usuario declarativa con Jetpack Compose
- Patrón arquitectónico MVVM (Model-View-ViewModel)
- Inyección de dependencias con Hilt/Dagger
- Programación reactiva con Coroutines y LiveData

## ✨ Características

- ✅ **Obtención de usuarios aleatorios** desde la API de RandomUser
- ✅ **Almacenamiento local** persistente con Room Database
- ✅ **Interfaz moderna** construida completamente con Jetpack Compose
- ✅ **Indicadores de carga** con efectos shimmer
- ✅ **Arquitectura MVVM** para separación de responsabilidades
- ✅ **Inyección de dependencias** con Hilt para código modular
- ✅ **Carga de imágenes** desde URLs remotas con Coil
- ✅ **Diseño responsive** siguiendo Material Design 3
- ✅ **Teoría del color aplicada** con paleta profesional
- ✅ **Manejo de errores** robusto en todas las capas

## 🏗️ Arquitectura

El proyecto sigue el patrón **MVVM (Model-View-ViewModel)** con las siguientes capas:

```
┌─────────────────────────────────────────┐
│           UI Layer (Compose)            │
│  - MainActivity.kt                      │
│  - Composables (MyApp, UserCard, etc)   │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│         ViewModel Layer                 │
│  - UserViewModel.kt                     │
│  - Maneja estado y lógica de UI         │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│        Repository Layer                 │
│  - UserRepository.kt                    │
│  - UserRepositoryImpl.kt                │
└─────────┬──────────────┬────────────────┘
          │              │
┌─────────▼────────┐  ┌──▼───────────────┐
│  Remote Source   │  │  Local Source    │
│  RestDataSource  │  │  DbDataSource    │
│  (Retrofit)      │  │  (Room)          │
└──────────────────┘  └──────────────────┘
```

## 🛠️ Tecnologías Utilizadas

### Core
- **Kotlin** - Lenguaje de programación moderno para Android
- **Jetpack Compose** - Framework declarativo para construir UI nativas
- **Material Design 3** - Sistema de diseño de Google

### Arquitectura & Patrones
- **MVVM** (Model-View-ViewModel) - Patrón arquitectónico
- **Repository Pattern** - Abstracción de fuentes de datos
- **Dependency Injection** - Con Hilt/Dagger 2

### Networking
- **Retrofit 2.9.0** - Cliente HTTP type-safe para Android
- **Gson Converter** - Serialización/deserialización JSON
- **OkHttp Logging Interceptor** - Para debugging de peticiones HTTP

### Persistencia de Datos
- **Room 2.8.4** - Biblioteca de persistencia sobre SQLite
- **Room KTX** - Extensiones Kotlin para Room con soporte de Coroutines

### Inyección de Dependencias
- **Hilt 2.51.1** - Framework de inyección de dependencias construido sobre Dagger
- **Hilt Navigation Compose** - Integración de Hilt con Compose Navigation

### Asincronía
- **Kotlin Coroutines** - Para operaciones asíncronas
- **LiveData** - Datos observables lifecycle-aware

### UI/UX
- **Coil 2.5.0** - Carga de imágenes desde URLs
- **Accompanist Placeholder** - Efectos de carga (shimmer)

## 📦 Dependencias Principales

```kotlin
dependencies {
    // Hilt - Inyección de dependencias
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    
    // Retrofit - Cliente HTTP
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // Room - Base de datos
    implementation("androidx.room:room-runtime:2.8.4")
    implementation("androidx.room:room-ktx:2.8.4")
    kapt("androidx.room:room-compiler:2.8.4")
    
    // Coil - Carga de imágenes
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Accompanist - Placeholder/Shimmer
    implementation("com.google.accompanist:accompanist-placeholder-material:0.31.5-beta")
}
```

## 🎨 Diseño y UX

### Teoría del Color

La aplicación implementa una paleta de colores cuidadosamente seleccionada basada en principios de diseño:

- **Primario**: Azul vibrante (#1976D2) - Profesional y confiable
- **Secundario**: Cyan (#00BCD4) - Complementario y armónico
- **Acento**: Naranja (#FF6F00) - Para llamadas a la acción
- **Superficie**: Blanco/Gris oscuro - Según el tema
- **Fondo**: Gris muy claro/Negro suave - Para contraste

### Experiencia de Usuario

- **Estados claros**: Vacío, cargando, con datos
- **Feedback visual**: Efectos shimmer durante la carga
- **Confirmaciones**: Diálogos antes de acciones destructivas
- **Accesibilidad**: Contraste adecuado y tamaños de texto legibles
- **Animaciones sutiles**: Para transiciones fluidas

## 📱 Funcionalidades

### 1. Agregar Usuarios
Presiona el botón **+** en la barra superior para obtener un usuario aleatorio de la API. El botón se desactiva durante la carga para evitar múltiples peticiones simultáneas.

### 2. Visualizar Lista
Los usuarios se muestran en cards elegantes con:
- Foto de perfil circular
- Nombre completo
- Ciudad de residencia
- Botón de eliminación

### 3. Eliminar Usuarios
Cada card tiene un botón de eliminar que muestra un diálogo de confirmación antes de eliminar permanentemente el usuario de la base de datos.

### 4. Persistencia
Todos los usuarios se almacenan localmente usando Room, por lo que los datos persisten entre sesiones de la aplicación.

## 🚀 Instalación y Configuración

### Requisitos Previos
- Android Studio Hedgehog o superior
- JDK 11 o superior
- SDK de Android 24+ (Android 7.0 Nougat o superior)

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/SimpleRest.git
cd SimpleRest
```

2. **Abrir en Android Studio**
- File → Open → Seleccionar la carpeta del proyecto

3. **Sincronizar dependencias**
- Esperar a que Gradle sincronice todas las dependencias
- Si hay problemas, hacer clic en "Sync Project with Gradle Files"

4. **Ejecutar la aplicación**
- Conectar un dispositivo Android o iniciar un emulador
- Presionar el botón "Run" (▶️) o usar `Shift + F10`

## 📂 Estructura del Proyecto

```
app/src/main/java/com/zyrdev/simplerest/
├── MainActivity.kt                      # Actividad principal
├── UserViewModel.kt                     # ViewModel para lógica de UI
├── SimpleRestApplication.kt             # Clase Application con Hilt
├── datasource/
│   ├── RestDataSource.kt               # Interface Retrofit
│   └── DbDataSource.kt                 # Room Database
├── di/
│   ├── DataSourceModule.kt             # Módulo de dependencias (Retrofit, Room)
│   └── RepositoryModule.kt             # Módulo de repositorios
├── model/
│   ├── User.kt                         # Entidad User y DAO
│   ├── ApiResponse.kt                  # Modelos de respuesta API
│   ├── UserName.kt                     # Modelo de nombre
│   ├── UserLocation.kt                 # Modelo de ubicación
│   └── UserPicture.kt                  # Modelo de imagen
├── repository/
│   └── UserRepository.kt               # Interface y implementación
└── ui/
    └── theme/
        ├── Color.kt                    # Paleta de colores
        ├── Theme.kt                    # Tema Material 3
        └── Type.kt                     # Tipografía
```

## 🔍 Cómo Funciona

### Flujo de Datos

1. **Usuario presiona el botón "+"**
2. **ViewModel** llama a `addUser()`
3. **Repository** hace petición a la API con Retrofit
4. **Datos se transforman** de JSON a objetos Kotlin
5. **Usuario se guarda** en Room Database
6. **LiveData actualiza** automáticamente la UI
7. **LazyColumn renderiza** la nueva lista

### Petición a la API

```kotlin
// Endpoint utilizado
https://randomuser.me/api/?inc=name,location,picture

// Respuesta (simplificada)
{
  "results": [{
    "name": { "first": "John", "last": "Doe" },
    "location": { "city": "New York" },
    "picture": { "thumbnail": "https://..." }
  }]
}
```

## 🧪 Testing

El proyecto incluye soporte para:
- Unit tests (JUnit)
- Instrumentation tests (Espresso)

Para ejecutar los tests:
```bash
./gradlew test           # Unit tests
./gradlew connectedAndroidTest  # Instrumentation tests
```

## 🐛 Solución de Problemas

### Error de compilación con Hilt
- Asegúrate de tener `kapt` configurado en build.gradle
- Verifica que `@HiltAndroidApp` esté en la clase Application
- Limpia el proyecto: Build → Clean Project

### Error de red
- Verifica que `INTERNET` permission esté en AndroidManifest.xml
- Comprueba la conexión a internet del dispositivo

### Room Database no actualiza
- Verifica que estés usando `observeAsState()` para LiveData
- Comprueba que los DAOs sean `suspend` functions

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## 👥 Autor

Desarrollado por **ZyrDev**

## 🙏 Agradecimientos

- [RandomUser.me](https://randomuser.me/) - Por proporcionar la API gratuita
- [Material Design](https://material.io/) - Por las guías de diseño
- Comunidad de Android Developers

---

<div align="center">
  <p>⭐ Si te gusta este proyecto, considera darle una estrella ⭐</p>
  <p>Made with ❤️ and Kotlin</p>
</div>

