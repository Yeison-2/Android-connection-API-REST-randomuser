# 🔨 Guía de Compilación - SimpleRest

## Prerrequisitos

Antes de compilar el proyecto, asegúrate de tener instalado:

- ✅ **Android Studio** Hedgehog (2023.1.1) o superior
- ✅ **JDK 11** o superior
- ✅ **Android SDK 24+** (mínimo)
- ✅ **Android SDK 36** (target)
- ✅ **Conexión a Internet** (para descargar dependencias)

---

## 📋 Pasos para Compilar

### 1. Abrir el Proyecto

1. Abre **Android Studio**
2. Selecciona `File → Open`
3. Navega a la carpeta del proyecto: `C:\Users\Usuario\AndroidStudioProjects\SimpleRest`
4. Haz clic en **OK**

### 2. Sincronizar Gradle

El proyecto se sincronizará automáticamente. Si no:

1. Haz clic en el ícono del elefante de Gradle en la barra superior
2. O ve a `File → Sync Project with Gradle Files`
3. Espera a que descargue todas las dependencias

**Dependencias que se descargarán:**
- Hilt 2.51.1
- Retrofit 2.9.0
- Room 2.8.4
- Coil 2.5.0
- Accompanist 0.31.5-beta
- Y todas las librerías de Jetpack Compose

⏱️ **Tiempo estimado**: 2-5 minutos (primera vez)

### 3. Compilar el Proyecto

#### Opción A: Desde Android Studio (Recomendado)

1. Haz clic en `Build → Make Project` (o `Ctrl+F9`)
2. Espera a que termine la compilación
3. Verifica que no haya errores en la ventana "Build"

#### Opción B: Desde Terminal/PowerShell

```powershell
# Navegar a la carpeta del proyecto
cd C:\Users\Usuario\AndroidStudioProjects\SimpleRest

# Dar permisos de ejecución (si es necesario)
# En Windows generalmente no es necesario

# Compilar el proyecto
.\gradlew build

# O para limpiar y compilar
.\gradlew clean build
```

### 4. Ejecutar la Aplicación

#### Preparar el Dispositivo

**Opción A: Emulador**
1. Abre AVD Manager: `Tools → Device Manager`
2. Crea o inicia un dispositivo virtual
3. Espera a que el emulador inicie completamente

**Opción B: Dispositivo Físico**
1. Activa las "Opciones de Desarrollador" en tu dispositivo
2. Activa "Depuración USB"
3. Conecta el dispositivo con un cable USB
4. Acepta la autorización en el dispositivo

#### Ejecutar

1. Selecciona el dispositivo en la lista desplegable
2. Haz clic en el botón **Run** (▶️) verde
3. O presiona `Shift + F10`

⏱️ **Tiempo de instalación**: 30-60 segundos

---

## 🐛 Solución de Problemas

### Error: "SDK location not found"

**Solución:**
Crea el archivo `local.properties` en la raíz del proyecto con:
```properties
sdk.dir=C\:\\Users\\Usuario\\AppData\\Local\\Android\\Sdk
```
(Ajusta la ruta según tu instalación)

### Error: "Gradle sync failed"

**Soluciones:**
1. Verifica tu conexión a Internet
2. Limpia el caché de Gradle:
   ```powershell
   .\gradlew clean --refresh-dependencies
   ```
3. Invalida cachés: `File → Invalidate Caches / Restart`

### Error: "Unresolved reference: hilt"

**Solución:**
1. Verifica que el archivo `build.gradle.kts` (raíz) tenga:
   ```kotlin
   plugins {
       id("com.google.dagger.hilt.android") version "2.51.1" apply false
   }
   ```
2. Sincroniza Gradle nuevamente

### Error: "Cannot resolve symbol 'R'"

**Solución:**
1. Limpia el proyecto: `Build → Clean Project`
2. Reconstruye: `Build → Rebuild Project`
3. Invalida cachés si persiste

### Error de permisos de Internet

**Solución:**
Verifica que `AndroidManifest.xml` tenga:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```
(Ya debería estar incluido)

### La aplicación se cierra inmediatamente

**Posibles causas:**
1. **Falta permiso de Internet** - Verifica el Manifest
2. **Error en Hilt** - Verifica que `SimpleRestApplication` tenga `@HiltAndroidApp`
3. **Error en la API** - Verifica los logs con Logcat

**Ver logs:**
```
View → Tool Windows → Logcat
Filtrar por: "SimpleRest" o "UserRepository"
```

---

## 📱 Verificar que Funciona

### Checklist Post-Instalación

1. ✅ La app se abre sin crashes
2. ✅ Se ve la pantalla con el título "Random Users"
3. ✅ Hay un botón "+" en la esquina superior derecha
4. ✅ Se muestra el mensaje "No hay usuarios" al inicio
5. ✅ Al tocar "+", aparece un shimmer/loading
6. ✅ Después de ~5 segundos, aparece un usuario con foto
7. ✅ La foto se carga correctamente (circular)
8. ✅ Se muestra nombre, apellido y ciudad
9. ✅ Hay un ícono de basura rojo
10. ✅ Al tocar la basura, sale un diálogo de confirmación
11. ✅ Al confirmar, el usuario se elimina
12. ✅ Al cerrar y abrir la app, los usuarios persisten

---

## 🔍 Comandos Útiles de Gradle

### Ver todas las tareas disponibles
```powershell
.\gradlew tasks
```

### Limpiar el proyecto
```powershell
.\gradlew clean
```

### Compilar sin ejecutar
```powershell
.\gradlew assembleDebug
```

### Compilar versión Release
```powershell
.\gradlew assembleRelease
```

### Ver dependencias
```powershell
.\gradlew app:dependencies
```

### Ejecutar tests
```powershell
.\gradlew test
```

---

## 📊 Información de Compilación

### Tiempos Aproximados

| Tarea | Primera Vez | Subsecuentes |
|-------|-------------|--------------|
| Sync Gradle | 3-5 min | 10-30 seg |
| Build | 2-4 min | 30-60 seg |
| Install APK | 30-60 seg | 20-40 seg |
| **TOTAL** | **6-10 min** | **1-2 min** |

### Tamaños

| Archivo | Tamaño Aprox. |
|---------|---------------|
| APK Debug | ~8-12 MB |
| APK Release | ~5-8 MB |
| Dependencias | ~200-300 MB |

---

## 🎯 Configuraciones de Build

### Debug (Desarrollo)
- Incluye símbolos de debug
- No minifica código
- Logging habilitado
- Más rápido de compilar

### Release (Producción)
- Código minificado con R8
- Ofuscación activada
- Sin logs de debug
- APK más pequeño y optimizado

Para generar APK Release firmado:
1. `Build → Generate Signed Bundle / APK`
2. Selecciona APK
3. Crea o selecciona un keystore
4. Completa los datos
5. Espera la generación

---

## 📞 Soporte

Si encuentras algún problema:

1. **Revisa los logs** en Logcat
2. **Limpia y reconstruye** el proyecto
3. **Verifica las versiones** de SDK y dependencias
4. **Consulta la documentación** oficial de Android

---

## ✅ Checklist Pre-Compilación

Antes de compilar, verifica:

- [ ] Android Studio actualizado
- [ ] SDK 36 instalado
- [ ] JDK 11+ configurado
- [ ] Conexión a Internet activa
- [ ] Espacio en disco (al menos 5 GB libres)
- [ ] Antivirus no bloqueando Gradle
- [ ] Variables de entorno configuradas (si usas terminal)

---

**¡Listo para compilar y ejecutar!** 🚀

Si todo está correcto, deberías tener la aplicación funcionando en menos de 10 minutos desde cero.

---

*Última actualización: Diciembre 2025*

