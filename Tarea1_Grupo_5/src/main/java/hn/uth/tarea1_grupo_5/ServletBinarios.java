package hn.uth.tarea1_grupo_5;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "conversorServlet", value = "/conversor-numeros")
public class ServletBinarios extends HttpServlet {
    private String titulo;

    private static final String INTEGRANTES =
            "<div class='integrantes'>" +
                    "<h2>Integrantes:</h2>" +
                    "<p>Javier Alberto Espinoza - 201610010339</p>" +
                    "<p>Levith Alexander Silva - 202030110007</p>" +
                    "</div>";

    public void init() {
        titulo = "Conversor de Números - Sistema Unificado";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String operacion = request.getParameter("operacion");
        String numero = request.getParameter("numero");
        String tipoConversion = request.getParameter("tipo");

        if (tipoConversion == null || tipoConversion.isEmpty()) {
            response.sendRedirect("formulario_binario.html");
            return;
        }

        if ("convertir".equals(operacion) && numero != null && !numero.isEmpty()) {
            procesarConversion(tipoConversion, numero, out);
        } else {
            mostrarFormularioConversion(tipoConversion, out, "", "");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    private void mostrarFormularioConversion(String tipoConversion, PrintWriter out, String numeroInput, String resultado) {
        String tituloConversion = "";
        String placeholder = "";
        String funcionValidacion = "";
        String mensajeError = "";

        if ("binario-decimal".equals(tipoConversion)) {
            tituloConversion = "Conversión de Binario a Decimal";
            placeholder = "Introduce un número binario (solo 0 y 1)";
            funcionValidacion = "soloBinario";
            mensajeError = "Error: Número binario inválido (solo se permiten 0 y 1)";
        } else if ("decimal-binario".equals(tipoConversion)) {
            tituloConversion = "Conversión de Decimal a Binario";
            placeholder = "Introduce un número decimal";
            funcionValidacion = "soloDecimal";
            mensajeError = "Error: Número decimal inválido";
        }


        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + tituloConversion + "</title>");


        out.println("<link rel=\"stylesheet\" href=\"estilo_binario.css\">");

        out.println("</head>");
        out.println("<body>");


        out.println("<div style='position: absolute; top: 20px; left: 20px;'>");
        out.println("<a href='formulario_binario.html' class='btn btn-secondary'>Menú Principal</a>");
        out.println("</div>");

        out.println("<div class='container'>");
        out.println("<h1>" + tituloConversion + "</h1>");


        out.println("<div class='warning'>");
        out.println("<strong>Atención:</strong> Solo puedes realizar una conversión a la vez. ");
        out.println("Para cambiar el tipo de conversión, regresa al menú principal.");
        out.println("</div>");


        out.println("<form method='get' action='conversor-numeros'>");
        out.println("<input type='hidden' name='operacion' value='convertir'>");
        out.println("<input type='hidden' name='tipo' value='" + tipoConversion + "'>");
        out.println("<br>");
        out.println("<input type='text' name='numero' value='" + (numeroInput != null ? numeroInput : "") + "' " +
                "onkeypress='return " + funcionValidacion + "(event)' onpaste='return false;' " +
                "placeholder='" + placeholder + "' required autofocus>");
        out.println("<br><br>");
        out.println("<input type='submit' value='Realizar Conversión' class='btn'>");
        out.println("</form>");


        if (!resultado.isEmpty() && !resultado.startsWith("Error:")) {
            out.println("<table>");
            out.println("<tr><th>Entrada</th><th>Salida</th></tr>");
            out.println("<tr><td>" + numeroInput + "</td><td><strong>" + resultado + "</strong></td></tr>");
            out.println("</table>");


            out.println("<div style='margin-top: 30px; padding: 20px; background-color: #f0f8ff; border-radius: 5px;'>");
            out.println("<h3 style='color: #003366;'>¿Qué deseas hacer ahora?</h3>");
            out.println("<a href='conversor-numeros?tipo=" + tipoConversion + "' class='btn'>Realizar Otra Conversión</a>");
            out.println("<a href='formulario_binario.html' class='btn btn-secondary'>Cambiar Tipo de Conversión</a>");
            out.println("</div>");
        }

        else if (resultado.startsWith("Error:")) {
            out.println("<div class='error'>" + resultado + "</div>");
            out.println("<a href='conversor-numeros?tipo=" + tipoConversion + "' class='btn'>Intentar de Nuevo</a>");
        }


        out.println(INTEGRANTES);

        out.println("<script>");
        if ("binario-decimal".equals(tipoConversion)) {
            out.println("function soloBinario(e) {");
            out.println("  var char = String.fromCharCode(e.which || e.keyCode);");
            out.println("  if (char !== '0' && char !== '1') { e.preventDefault(); return false; }");
            out.println("  return true;");
            out.println("}");
        } else if ("decimal-binario".equals(tipoConversion)) {
            out.println("function soloDecimal(e) {");
            out.println("  var char = String.fromCharCode(e.which || e.keyCode);");
            out.println("  if (char < '0' || char > '9') { e.preventDefault(); return false; }");
            out.println("  return true;");
            out.println("}");
        }
        out.println("</script>");

        out.println("</div>");
        out.println("<a href='Menu_Navegacion.html' class='btn' style='position: absolute; top: 20px; left: 20px;'>");
        out.println("Volver al Menú Principal");
        out.println("</a>");
        out.println("</body>");
        out.println("</html>");
    }

    private void procesarConversion(String tipoConversion, String numero, PrintWriter out) {
        try {
            String resultado = "";

            if ("binario-decimal".equals(tipoConversion)) {
                int decimal = Integer.parseInt(numero, 2);
                resultado = String.valueOf(decimal);
            } else if ("decimal-binario".equals(tipoConversion)) {
                int num = Integer.parseInt(numero);
                resultado = Integer.toBinaryString(num);
            }

            mostrarFormularioConversion(tipoConversion, out, numero, resultado);

        } catch (NumberFormatException e) {
            String mensajeError = "binario-decimal".equals(tipoConversion) ?
                    "Error: Número binario inválido (solo se permiten 0 y 1)" :
                    "Error: Número decimal inválido";
            mostrarFormularioConversion(tipoConversion, out, numero, mensajeError);
        }
    }


    public void destroy() {
    }
}