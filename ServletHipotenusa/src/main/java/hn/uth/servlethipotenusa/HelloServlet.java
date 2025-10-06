package hn.uth.servlethipotenusa;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    private static final String NOMBRE_ESTUDIANTE = "Ninrrol Edgardo Garcia";
    private static final String NUMERO_CUENTA = "202310080085";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Configurar la respuesta como HTML
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 2. Obtener los parámetros
        String paramOperacion = request.getParameter("operacion");
        String paramLadoA = request.getParameter("ladoA");
        String paramLadoB = request.getParameter("ladoB");

        String nombreOperacion = "Cálculo de Hipotenusa (Teorema de Pitágoras)";
        String entrada = "";
        String respuesta = "";

        // 3. Lógica de Cálculo
        if ("HIPOTENUSA".equals(paramOperacion)) {
            try {
                double ladoA = Double.parseDouble(paramLadoA);
                double ladoB = Double.parseDouble(paramLadoB);

                // FÓRMULA CORRECTA: c = sqrt(a² + b²)
                double hipotenusa = Math.sqrt(Math.pow(ladoA, 2) + Math.pow(ladoB, 2));

                entrada = String.format("Lado A: %.2f, Lado B: %.2f", ladoA, ladoB);
                respuesta = String.format("%.4f", hipotenusa);

            } catch (NumberFormatException e) {
                entrada = "Datos Inválidos";
                respuesta = "ERROR: Los valores de entrada deben ser números.";
            }
        } else {
            // Manejo de otros casos si se añade otra operación
            nombreOperacion = "Operación Desconocida";
            entrada = "N/A";
            respuesta = "ERROR: Operación no soportada.";
        }

        // 4. Generación del HTML COMPLETO (Formato de la Tarea)
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Respuesta del Servlet</title>");
        out.println("<style>"); // Añadir CSS para mejorar la tabla (opcional)
        out.println("body { font-family: Arial, sans-serif; margin: 30px; }");
        out.println("table { border-collapse: collapse; width: 60%; margin-top: 15px; }");
        out.println("th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        // Título de la Tarea con Nombre y Cuenta (Etiquetas H2)
        out.println("<h2>Servlet Tarea 1: <b>" + NOMBRE_ESTUDIANTE + "</b></h2>");
        out.println("<h2>Cuenta: <b>" + NUMERO_CUENTA + "</b></h2>");

        // Operación Realizada
        out.println("<h3>Operación Realizada: " + nombreOperacion + "</h3>");

        // Tabla de Entrada y Respuesta
        out.println("<table>");
        out.println("<thead><tr><th>Entrada</th><th>Respuesta</th></tr></thead>");
        out.println("<tbody><tr>");
        out.println("<td>" + entrada + "</td>");
        out.println("<td>" + respuesta + "</td>");
        out.println("</tr></tbody>");
        out.println("</table>");

        // Botón para volver a la página de entrada
        out.println("<p><a href=\"index.html\">Calcular otra hipotenusa</a></p>");

        out.println("</body>");
        out.println("</html>");
    }
}