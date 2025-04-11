package co.edu.poli.ces3.pitagorasapi.dao;

import co.edu.poli.ces3.pitagorasapi.model.Curso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CursoDAO {
    private static CursoDAO instance;
    private final Map<Integer, Curso> cursosPorId;
    private final Map<String, Curso> cursosPorCodigo;
    private int nextId;

    // Patrón Singleton para asegurar una única instancia del DAO
    private CursoDAO() {
        this.cursosPorId = new HashMap<>();
        this.cursosPorCodigo = new HashMap<>();
        this.nextId = 1;

        // Datos iniciales para pruebas
        inicializarDatos();
    }

    public static synchronized CursoDAO getInstance() {
        if (instance == null) {
            instance = new CursoDAO();
        }
        return instance;
    }

    private void inicializarDatos() {
        // Crear algunos cursos de ejemplo
        Curso curso1 = new Curso(nextId++, "Programación I", "PROG101", "Dr. Smith", 30,
                "Ingeniería", 1, "2023-03-15");
        agregarCurso(curso1);

        Curso curso2 = new Curso(nextId++, "Matemáticas Discretas", "MATE201", "Dr. Johnson", 25,
                "Ciencias Básicas", 2, "2023-03-20");
        agregarCurso(curso2);

        Curso curso3 = new Curso(nextId++, "Estructuras de Datos", "PROG202", "Dr. Williams", 20,
                "Ingeniería", 2, "2023-04-10");
        curso3.addPrerequisito("PROG101");
        agregarCurso(curso3);
    }

    public synchronized void agregarCurso(Curso curso) {
        // Validar que el código no exista
        if (cursosPorCodigo.containsKey(curso.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un curso con el código: " + curso.getCodigo());
        }

        // Validar cupo máximo
        if (curso.getCupoMaximo() <= 0) {
            throw new IllegalArgumentException("El cupo máximo debe ser mayor que cero");
        }

        // Validar prerequisitos
        for (String prerequisito : curso.getPrerequisitos()) {
            if (!cursosPorCodigo.containsKey(prerequisito)) {
                throw new IllegalArgumentException("El prerequisito " + prerequisito + " no existe");
            }
        }

        // Asignar ID si no tiene
        if (curso.getId() == 0) {
            curso.setId(nextId++);
        }

        cursosPorId.put(curso.getId(), curso);
        cursosPorCodigo.put(curso.getCodigo(), curso);
    }

    public List<Curso> obtenerTodosLosCursos() {
        return new ArrayList<>(cursosPorId.values());
    }

    public Curso obtenerCursoPorId(int id) {
        return cursosPorId.get(id);
    }

    public Curso obtenerCursoPorCodigo(String codigo) {
        return cursosPorCodigo.get(codigo);
    }

    public List<Curso> obtenerCursosPorFacultad(String facultad) {
        return cursosPorId.values().stream()
                .filter(curso -> curso.getFacultad().equalsIgnoreCase(facultad))
                .collect(Collectors.toList());
    }

    public List<Curso> encontrarRutaAprendizaje(String codigoCursoFinal) {
        List<Curso> ruta = new ArrayList<>();
        encontrarPrerequisitosRecursivo(codigoCursoFinal, ruta);

        // Ordenar por nivel académico
        ruta.sort((c1, c2) -> Integer.compare(c1.getNivel(), c2.getNivel()));

        return ruta;
    }

    private void encontrarPrerequisitosRecursivo(String codigoCurso, List<Curso> ruta) {
        Curso curso = cursosPorCodigo.get(codigoCurso);
        if (curso == null) {
            throw new IllegalArgumentException("Curso no encontrado: " + codigoCurso);
        }

        // Agregar prerequisitos primero (para mantener el orden correcto)
        for (String prerequisito : curso.getPrerequisitos()) {
            encontrarPrerequisitosRecursivo(prerequisito, ruta);
        }

        // Agregar el curso actual si no está ya en la ruta
        if (ruta.stream().noneMatch(c -> c.getCodigo().equals(curso.getCodigo()))) {
            ruta.add(curso);
        }
    }
}
