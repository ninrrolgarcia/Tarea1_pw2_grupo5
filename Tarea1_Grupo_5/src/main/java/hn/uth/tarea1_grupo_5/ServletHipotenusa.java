package hn.uth.tarea1_grupo_5;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Mapeo del Servlet: /calcularHipotenusa
@WebServlet(name = "hipotenusaServlet", value = "/calcularHipotenusa")
public class ServletHipotenusa extends HttpServlet {
    private String titulo;

    public void init() {
        titulo = "Servlet Tarea 1: Cálculo de Hipotenusa";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Parámetros de la solicitud (catetos 'a' y 'b')
        String paramLadoA = request.getParameter("ladoA");
        String paramLadoB = request.getParameter("ladoB");
        String operacion = request.getParameter("operacion"); // Se usará para saber si venimos del formulario

        // 1. Si los parámetros no existen o no se ha indicado la operación, muestra el formulario.
        if (!"calcular".equals(operacion) || paramLadoA == null || paramLadoB == null) {
            mostrarFormulario(out, "", "", "");
            return;
        }

        // 2. Si venimos del formulario y tenemos los datos, procesar el cálculo.
        procesarCalculo(paramLadoA, paramLadoB, out);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // En este caso, trataremos POST igual que GET (útil si usas POST en el formulario)
        doGet(request, response);
    }

    /**
     * Muestra el formulario de entrada o el formulario con el resultado.
     */
    private void mostrarFormulario(PrintWriter out, String ladoAInput, String ladoBInput, String resultadoHTML) {

        // --- INICIO: HTML Estático de la Interfaz ---
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + titulo + "</title>");
        out.println("<style>");
        out.println("body { font-family: Arial; background-color: #f5f5dc; text-align: center; margin: 0; padding: 20px; }");
        out.println(".container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println("h1 { color: #003366; margin-bottom: 20px; }");
        out.println(".btn { padding: 10px 20px; font-size: 16px; background-color: #003366; color: white; border: none; border-radius: 5px; cursor: pointer; margin: 5px; text-decoration: none; display: inline-block; }");
        out.println(".btn:hover { background-color: #002244; }");
        out.println("input[type='number'] { padding: 10px; width: 250px; font-size: 16px; margin: 10px; text-align: center; }");
        out.println("label { display: inline-block; width: 150px; text-align: right; margin-right: 10px; font-weight: bold; }");
        out.println(".error { color: red; margin: 20px; font-weight: bold; padding: 10px; background-color: #ffe6e6; border-radius: 5px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>" + titulo + "</h1>");

        // Formulario de entrada
        out.println("<form method='get' action='calcularHipotenusa'>");
        out.println("<input type='hidden' name='operacion' value='calcular'>");

        out.println("<div>");
        out.println("<label for='ladoA'>Lado A (Cateto):</label>");
        out.println("<input type='number' name='ladoA' id='ladoA' step='any' value='" + ladoAInput + "' required autofocus>");
        out.println("</div>");

        out.println("<div>");
        out.println("<label for='ladoB'>Lado B (Cateto):</label>");
        out.println("<input type='number' name='ladoB' id='ladoB' step='any' value='" + ladoBInput + "' required>");
        out.println("</div>");

        out.println("<br>");
        out.println("<input type='submit' value='Calcular Hipotenusa' class='btn'>");
        out.println("</form>");

        out.println("<div class='integrantes'>");
        out.println("<h2>Integrantes:</h2>");
        out.println("<p>Ninrrol Edgardo Garcia - 202310080085</p>");
        out.println("<p>Wilmer Orlando Murillo - 202020040016</p>");
        out.println("</div>");


        // --- FIN: HTML Estático de la Interfaz ---

        // Mostrar resultados/errores si se recibieron
        if (!resultadoHTML.isEmpty()) {
            out.println("<hr>");
            out.println(resultadoHTML); // Inserta la respuesta HTML generada por el cálculo
        }

        out.println("<a href='Menu_Navegacion.html' class='btn' style='position: absolute; top: 20px; left: 20px;'>");
        out.println("🏠 Volver al Menú Principal");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Realiza el cálculo de la hipotenusa y llama a la generación del HTML de respuesta de la tarea.
     */
    private void procesarCalculo(String paramLadoA, String paramLadoB, PrintWriter out) {
        String entrada = "";
        String respuesta = "";
        String htmlResultado;

        try {
            // Conversión y validación de números
            double ladoA = Double.parseDouble(paramLadoA);
            double ladoB = Double.parseDouble(paramLadoB);

            if (ladoA <= 0 || ladoB <= 0) {
                throw new NumberFormatException("Los lados deben ser positivos.");
            }

            // FÓRMULA: c = sqrt(a² - b²)
            double hipotenusa = Math.sqrt(Math.pow(ladoA, 2) - Math.pow(ladoB, 2));

            entrada = String.format("Lado A: %.2f, Lado B: %.2f", ladoA, ladoB);
            respuesta = String.format("%.4f", hipotenusa);

            // Éxito: Generar el HTML de respuesta con el formato de la tarea
            htmlResultado = generarRespuestaHTML("Cálculo de Hipotenusa", entrada, respuesta);

        } catch (NumberFormatException e) {
            // Error: Generar el HTML con mensaje de error
            entrada = String.format("Lado A: %s, Lado B: %s", paramLadoA, paramLadoB);
            respuesta = "ERROR: Valores inválidos. Ingrese números positivos.";

            htmlResultado = generarRespuestaHTML("Cálculo de Hipotenusa", entrada, respuesta);
            htmlResultado = "<div class='error'>" + respuesta + "</div>" + htmlResultado;
        }

        // Volver a mostrar el formulario, pero ahora con el resultado incrustado
        mostrarFormulario(out, paramLadoA, paramLadoB, htmlResultado);
    }

    /**
     * Genera la estructura HTML de respuesta que incluye Nombre, Cuenta y la Tabla,
     * cumpliendo con el formato de la tarea.
     */
    private String generarRespuestaHTML(String nombreOperacion, String entrada, String respuesta) {

        StringBuilder sb = new StringBuilder();

        sb.append("<div style='margin-top: 30px; padding: 20px; border: 1px solid #ccc; border-radius: 5px; text-align: left;'>");

        // Operación Realizada
        sb.append("<p style='font-style: italic;'>Operación Realizada: ").append(nombreOperacion).append("</p>");

        // Tabla de Entrada y Respuesta
        sb.append("<table style='width: 100%;'>")
                .append("<thead><tr><th>Entrada</th><th>Respuesta</th></tr></thead>")
                .append("<tbody><tr>")
                .append("<td>").append(entrada).append("</td>")
                .append("<td>").append(respuesta).append("</td>")
                .append("</tr></tbody>")
                .append("</table>");

        sb.append("</div>");

        return sb.toString();
    }
}