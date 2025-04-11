package co.edu.poli.ces3.pitagorasapi.model;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private int id;
    private String nombre;
    private String codigo;
    private String profesor;
    private int cupoMaximo;
    private int estudiantesInscritos;
    private String facultad;
    private List<String> prerequisitos;
    private int nivel;
    private String fechaInicio;

    public Curso() {
        this.prerequisitos = new ArrayList<>();
    }

    public Curso(int id, String nombre, String codigo, String profesor, int cupoMaximo, String facultad, int nivel, String fechaInicio) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.profesor = profesor;
        this.cupoMaximo = cupoMaximo;
        this.estudiantesInscritos = 0;
        this.facultad = facultad;
        this.prerequisitos = new ArrayList<>();
        this.nivel = nivel;
        this.fechaInicio = fechaInicio;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public int getEstudiantesInscritos() {
        return estudiantesInscritos;
    }

    public void setEstudiantesInscritos(int estudiantesInscritos) {
        this.estudiantesInscritos = estudiantesInscritos;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public List<String> getPrerequisitos() {
        return prerequisitos;
    }

    public void setPrerequisitos(List<String> prerequisitos) {
        this.prerequisitos = prerequisitos;
    }

    public void addPrerequisito(String codigoCurso) {
        this.prerequisitos.add(codigoCurso);
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", profesor='" + profesor + '\'' +
                ", cupoMaximo=" + cupoMaximo +
                ", estudiantesInscritos=" + estudiantesInscritos +
                ", facultad='" + facultad + '\'' +
                ", prerequisitos=" + prerequisitos +
                ", nivel=" + nivel +
                ", fechaInicio='" + fechaInicio + '\'' +
                '}';
    }
}
