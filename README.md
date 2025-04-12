# Pitagoras-Api
Examen de clase 11/04/2025 - Juan David Castrillon Villa

# Platon API - Gestión de Cursos

Este proyecto implementa la Variante A (Gestión de Cursos) según el último dígito de mi documento de identidad (8).

## Requisitos
- Java 8 o superior
- Apache Tomcat 9.x
- Maven (opcional)

## Endpoints

### Registrar un nuevo curso
- **Método**: POST
- **URL**: `/cursos`
- **Body** (JSON):
  ```json
  {
    "nombre": "Nombre del curso",
    "codigo": "COD101",
    "profesor": "Nombre del profesor",
    "cupoMaximo": 30,
    "facultad": "Ingeniería",
    "nivel": 1,
    "fechaInicio": "2023-03-15",
    "prerequisitos": ["COD100"]
  }

### Obtener todos los cursos
- **Método**: GET
- **URL**: `/cursos`

### Obtener cursos por facultad
- **Método**: GET
- **URL**: `/cursos/facultad?nombre=Ingeniería` (ejemplo con facultad ya registrad)

### Obtener Ruta de Aprendizaje
- **Método**: GET
- **URL**: `/cursos/facultad?codigo=PROG101` (ejemplo con Materia ya registrada)

