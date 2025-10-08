package hn.uth.tarea1_grupo_5;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "hipotenusaServlet", value = "/calcularHipotenusa")
public class ServletHipotenusa extends HttpServlet {
    private String titulo;

    private static final String CSS_LINK = "<link rel=\"stylesheet\" href=\"estilo_hipotenusa.css\">"; // <--- Nuevo archivo

    public void init() {
        titulo = "Servlet Tarea 1: Cálculo de Hipotenusa (Fórmula Asignada)";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String paramLadoA = request.getParameter("ladoA");
        String paramLadoB = request.getParameter("ladoB");
        String operacion = request.getParameter("operacion");

        if (!"calcular".equals(operacion) || paramLadoA == null || paramLadoB == null) {
            mostrarFormulario(out, "", "", "");
            return;
        }

        procesarCalculo(paramLadoA, paramLadoB, out);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    private void mostrarFormulario(PrintWriter out, String ladoAInput, String ladoBInput, String resultadoHTML) {

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + titulo + "</title>");

        out.println(CSS_LINK);

        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>" + titulo + "</h1>");
        out.println("<div class='formula'>Fórmula Asignada: c = \u221A(a\u00B2 - b\u00B2)</div>");

        // Formulario de entrada
        out.println("<form method='get' action='calcularHipotenusa'>");
        out.println("<input type='hidden' name='operacion' value='calcular'>");

        out.println("<div>");
        out.println("<label for='ladoA'>Lado A (Hipotenusa/Mayor):</label>");
        out.println("<input type='number' name='ladoA' id='ladoA' step='any' value='" + ladoAInput + "' required autofocus>");
        out.println("</div>");

        out.println("<div>");
        out.println("<label for='ladoB'>Lado B (Cateto/Menor):</label>");
        out.println("<input type='number' name='ladoB' id='ladoB' step='any' value='" + ladoBInput + "' required>");
        out.println("</div>");

        out.println("<br>");
        out.println("<input type='submit' value='Calcular Resultado' class='btn'>");
        out.println("</form>");

        out.println("<div class='integrantes'>");
        out.println("<h2>Integrantes:</h2>");
        out.println("<p>Ninrrol Edgardo Garcia - 202310080085</p>");
        out.println("<p>Wilmer Orlando Murillo - 202020040056</p>");
        out.println("</div>");

        if (!resultadoHTML.isEmpty()) {
            out.println("<hr>");
            out.println(resultadoHTML);
        }

        out.println("<a href='Menu_Navegacion.html' class='btn' style='position: absolute; top: 20px; left: 20px;'>");
        out.println("Volver al Menú Principal");
        out.println("</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }


    private void procesarCalculo(String paramLadoA, String paramLadoB, PrintWriter out) {
        String entrada;
        String respuesta;
        String htmlResultado;

        try {
            double ladoA = Double.parseDouble(paramLadoA);
            double ladoB = Double.parseDouble(paramLadoB);

            double aCuadrado = Math.pow(ladoA, 2);
            double bCuadrado = Math.pow(ladoB, 2);
            double diferencia = aCuadrado - bCuadrado;

            if (ladoA <= 0 || ladoB <= 0) {
                throw new IllegalArgumentException("Los lados deben ser números positivos.");
            }

            if (diferencia < 0) {
                throw new ArithmeticException("ERROR: El Lado A (a) debe ser mayor que el Lado B (b) para la fórmula asignada.");
            }

            double resultadoFinal = Math.sqrt(diferencia);

            entrada = String.format("Lado A: %.2f, Lado B: %.2f", ladoA, ladoB);
            respuesta = String.format("%.4f", resultadoFinal);

            htmlResultado = generarRespuestaHTML("Cálculo del Lado c", entrada, respuesta);

        } catch (NumberFormatException e) {
            String errorMsg = "ERROR: Ingrese valores numéricos válidos.";

            entrada = String.format("Lado A: %s, Lado B: %s", paramLadoA, paramLadoB);
            respuesta = "ERROR";

            htmlResultado = generarRespuestaHTML("Cálculo del Lado c", entrada, respuesta);
            htmlResultado = "<div class='error'>" + errorMsg + "</div>" + htmlResultado;
        } catch (IllegalArgumentException | ArithmeticException e) {
            String errorMsg = e.getMessage();

            entrada = String.format("Lado A: %s, Lado B: %s", paramLadoA, paramLadoB);
            respuesta = "ERROR";

            htmlResultado = generarRespuestaHTML("Cálculo del Lado c", entrada, respuesta);
            htmlResultado = "<div class='error'>" + errorMsg + "</div>" + htmlResultado;
        }

        mostrarFormulario(out, paramLadoA, paramLadoB, htmlResultado);
    }

    private String generarRespuestaHTML(String nombreOperacion, String entrada, String respuesta) {

        StringBuilder sb = new StringBuilder();

        sb.append("<div style='margin-top: 30px; padding: 20px; border: 1px solid #ccc; border-radius: 5px; text-align: left;'>");

        sb.append("<p style='font-style: italic;'>Resultado de la Operación: ").append(nombreOperacion).append("</p>");

        // Tabla de Entrada y Respuesta
        sb.append("<table style='width: 100%;'>")
                .append("<thead><tr><th>Datos de Entrada</th><th>Resultado (c)</th></tr></thead>")
                .append("<tbody><tr>")
                .append("<td>").append(entrada).append("</td>")
                .append("<td><strong>").append(respuesta).append("</strong></td>")
                .append("</tr></tbody>")
                .append("</table>");

        sb.append("</div>");

        return sb.toString();
    }
}