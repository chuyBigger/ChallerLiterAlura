
# 📚 ChallergerLiterAlura – Avance Temporal

> Proyecto de consola en Java con Spring Boot que permite buscar libros utilizando la API de [GutenDex](https://gutendex.com), almacenarlos en base de datos y asegurar coincidencias exactas con ayuda de Gemini AI.

---

## 🔧 Tecnologías usadas

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL (o H2 en pruebas)
- Jackson (`ObjectMapper`)
- Gemini API (Google Generative Language)
- Gson
- API REST (`HttpClient`)

---

## 🎯 Funcionalidad actual

### ✅ 1. Menú interactivo

- Al iniciar el programa, se muestra un menú con opciones.
- Por ahora, implementada la búsqueda por título.

---

### ✅ 2. Flujo de búsqueda

Cuando el usuario busca un libro:

1. Se solicita **título** (obligatorio) y **autor** (opcional).
2. Se consulta a **Gemini API** para corregir o traducir el título al inglés.
3. Se busca en la base de datos (`Spring Data JPA`) si ya está registrado.
4. Si **no existe**, se consulta a **GutenDex API**.
5. Se filtran los resultados JSON buscando **coincidencia exacta** en `"title"`.
6. Si hay coincidencia:
   - Se convierte a `Libro` y se guarda en la base de datos.
7. Si no hay coincidencia:
   - Se lanza un mensaje de error claro al usuario.

---

## 📂 Estructura del proyecto

```
com.aluracursos.challergerliteralura
├── ChallergerLiterAluraApplication.java
│
├── principal
│   └── Principal.java                    # Clase de entrada para el menú
│
├── modelos
│   ├── Libro.java                        # Entidad principal persistente
│   ├── DatosLibro.java                   # DTO con datos originales del JSON
│   ├── Autor.java                        # Entidad o embebido del autor
│   ├── DatosAutor.java                   # DTO para parsear autores desde JSON
│   └── Idioma.java                       # Enum para representar idiomas
│
├── repositorio
│   └── LibroRepositorio.java             # Interfaz JpaRepository
│
├── service
│   ├── ConfigUrl.java                    # Arma URLs para buscar en la API
│   ├── ConsumoApi.java                   # Encapsula `HttpClient` para consumir Gutendex
│   ├── ConsultaGemini.java               # Lógica de traducción y verificación con Gemini
│   ├── ConvertirDatos.java               # Conversión JSON a objetos usando Jackson
│   └── IConvierteDatos.java              # Interfaz para ConvertirDatos
```

---

## ✅ Validaciones implementadas

- No se permite título vacío.
- Traducción y corrección del título antes de buscar.
- Búsqueda por coincidencia exacta de título en el JSON.
- Se evita duplicar libros ya registrados.
- Manejo de errores si la API falla o no se encuentra coincidencia.

---

## 📌 Pendientes

- [ ] Agregar más opciones en el menú (ver todos, eliminar, editar, etc.).
- [ ] REalizar las busquedas personalizadas en la base de datos.
