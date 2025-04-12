package co.edu.poli.ces3.pitagorasapi.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import co.edu.poli.ces3.pitagorasapi.dao.CursoDAO;
import co.edu.poli.ces3.pitagorasapi.model.Curso;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CursoServlet", urlPatterns = {"/cursos", "/cursos/facultad", "/cursos/ruta"})
public class CursoServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final CursoDAO cursoDAO = CursoDAO.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        try {
            // Parsear el JSON del cuerpo de la solicitud
            Curso nuevoCurso = gson.fromJson(request.getReader(), Curso.class);

            // Validar campos obligatorios
            if (nuevoCurso.getNombre() == null || nuevoCurso.getCodigo() == null ||
                    nuevoCurso.getProfesor() == null || nuevoCurso.getFacultad() == null ||
                    nuevoCurso.getFechaInicio() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse.addProperty("error", "Todos los campos son obligatorios");
                out.print(jsonResponse.toString());
                return;
            }

            // Agregar el curso
            cursoDAO.agregarCurso(nuevoCurso);

            // Respuesta exitosa
            response.setStatus(HttpServletResponse.SC_CREATED);
            jsonResponse.addProperty("mensaje", "Curso creado exitosamente");
            jsonResponse.add("curso", gson.toJsonTree(nuevoCurso));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.addProperty("error", e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("error", "Error interno del servidor");
        }

        out.print(jsonResponse.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String path = request.getServletPath();

        try {
            if (path.equals("/cursos/facultad")) {
                // Endpoint: GET /cursos/facultad?nombre=X
                String facultad = request.getParameter("nombre");
                if (facultad == null || facultad.trim().isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"El parámetro 'nombre' es requerido\"}");
                    return;
                }

                List<Curso> cursos = cursoDAO.obtenerCursosPorFacultad(facultad);
                out.print(gson.toJson(cursos));

            } else if (path.equals("/cursos/ruta")) {
                // Endpoint: GET /cursos/ruta?codigo=X
                String codigo = request.getParameter("codigo");
                if (codigo == null || codigo.trim().isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"El parámetro 'codigo' es requerido\"}");
                    return;
                }

                List<Curso> ruta = cursoDAO.encontrarRutaAprendizaje(codigo);
                out.print(gson.toJson(ruta));

            } else {
                // Endpoint: GET /cursos (todos los cursos)
                List<Curso> cursos = cursoDAO.obtenerTodosLosCursos();
                out.print(gson.toJson(cursos));
            }

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error interno del servidor\"}");
        }
    }
}