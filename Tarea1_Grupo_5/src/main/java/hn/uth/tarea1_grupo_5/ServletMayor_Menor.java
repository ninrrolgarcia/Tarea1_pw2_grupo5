package hn.uth.tarea1_grupo_5;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "m-m",
        value = {"/m-m"})

public class ServletMayor_Menor extends HttpServlet {
    public void init() {
        // Inicialización, si es necesario
    }

    // Método para generar la estructura HTML de la página de resultados
    private void generarHtmlHeader(PrintWriter out, String titulo) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"es\">");
        out.println("<head>");
        out.println("    <meta charset=\"UTF-8\">");
        out.println("    <title>Resultado de la Operación</title>");
        out.println("    <link rel=\"stylesheet\" href=\"estilos2.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"container\">");
        out.println("    <h1>Resultado de la Operación</h1>");
        out.println("    <div class=\"section\">");
        out.println("        <h2>" + titulo + "</h2>");
        out.println("<div class='integrantes'>");
        out.println("<h2>Integrantes:</h2>");
        out.println("<p>Emely Flores - 2024100609--</p>");
        out.println("<p>Andre Nicolle Bocanegra Diaz - 202410061003</p>");
        out.println("</div>");
    }

    // Método para generar el footer HTML
    private void generarHtmlFooter(PrintWriter out) {
        out.println("    </div>"); // Cierra .section
        out.println("    <br>");
        out.println("    <a href=\"Menu_Navegacion.html\" class=\"section a\">Volver al Menú</a>");
        out.println("</div>"); // Cierra .container
        out.println("</body>");
        out.println("</html>");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            int n1 = Integer.parseInt(request.getParameter("num1"));
            int n2 = Integer.parseInt(request.getParameter("num2"));
            int n3 = Integer.parseInt(request.getParameter("num3"));
            int mayor = Math.max(n1, Math.max(n2, n3));
            int menor = Math.min(n1, Math.min(n2, n3));

            generarHtmlHeader(out, "Resultado del Ejercicio: Mayor y Menor");

            // Generar la tabla con los resultados del POST
            out.println("        <table>");
            out.println("            <tr><th>Datos de Entrada</th><th>N. Mayor</th><th>N. Menor</th></tr>");
            out.println("            <tr>");
            out.println("                <td>" + n1 + ", " + n2 + ", " + n3 + "</td>");
            out.println("                <td>" + mayor + "</td>");
            out.println("                <td>" + menor + "</td>");
            out.println("            </tr>");
            out.println("        </table>");

            generarHtmlFooter(out);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error: Por favor, ingrese números enteros válidos en todos los campos.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String n_numeros = request.getParameter("numeros");

        if (n_numeros == null || n_numeros.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error: Debe ingresar una lista de números separados por comas.");
            return;
        }

        try {
            String[] listado = n_numeros.split(",");
            int[] secuencia_numeros = new int[listado.length];

            for(int i = 0; i < listado.length; ++i) {
                // Limpiar espacios antes de parsear
                secuencia_numeros[i] = Integer.parseInt(listado[i].trim());
            }

            // --- LÓGICA DE FRECUENCIA ---
            int masFrecuente = 0;
            int frecuencia_maxima = 0;

            for(int numero : secuencia_numeros) {
                int contador_frecuencia = 0;

                for(int siguienteNumero : secuencia_numeros) {
                    if (numero == siguienteNumero) {
                        ++contador_frecuencia;
                    }
                }

                if (contador_frecuencia > frecuencia_maxima) {
                    masFrecuente = numero;
                    frecuencia_maxima = contador_frecuencia;
                }
            }
            // --- FIN LÓGICA DE FRECUENCIA ---

            generarHtmlHeader(out, "Resultado del Ejercicio: Valor más Frecuente");

            // Generar la tabla con los resultados del GET
            out.println("        <table>");
            out.println("            <tr><th>Datos de Entrada</th><th>N. más Frecuente</th></tr>");
            out.println("            <tr>");
            out.println("                <td>" + n_numeros + "</td>");
            out.println("                <td>" + masFrecuente + " (Frecuencia: " + frecuencia_maxima + ")</td>");
            out.println("            </tr>");
            out.println("        </table>");

            generarHtmlFooter(out);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error: Asegúrese de que todos los valores son números enteros válidos.");
        }
    }

    public void destroy() {
        // Limpieza, si es necesaria
    }
}