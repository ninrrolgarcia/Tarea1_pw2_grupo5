package hn.uth.tarea1_grupo_5;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "conversorServlet", value = "/conversor-numeros")
public class ServletBinarios extends HttpServlet {
    private String titulo;

    public void init() {
        titulo = "Conversor de Números - Sistema Unificado";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String operacion = request.getParameter("operacion");
        String numero = request.getParameter("numero");
        String tipoConversion = request.getParameter("tipo");

        // Si no hay tipo de conversión seleccionado, mostrar menú principal
        if (tipoConversion == null || tipoConversion.isEmpty()) {
            mostrarMenuPrincipal(out);
            return;
        }

        // Si hay una operación de conversión y número, procesarla
        if ("convertir".equals(operacion) && numero != null && !numero.isEmpty()) {
            procesarConversion(tipoConversion, numero, out);
        } else {
            // Mostrar formulario específico para el tipo seleccionado
            mostrarFormularioConversion(tipoConversion, out, "", "");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    private void mostrarMenuPrincipal(PrintWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + titulo + "</title>");
        out.println("<style>");
        out.println("body { font-family: Arial; background-color: #f5f5dc; text-align: center; margin: 0; padding: 20px; }");
        out.println("h1 { color: #003366; margin-bottom: 30px; }");
        out.println(".container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println(".btn { padding: 15px 30px; font-size: 18px; background-color: #003366; color: white; border: none; border-radius: 5px; cursor: pointer; margin: 10px; text-decoration: none; display: inline-block; width: 250px; }");
        out.println(".btn:hover { background-color: #002244; }");
        out.println(".btn:disabled { background-color: #cccccc; cursor: not-allowed; }");
        out.println(".integrantes { margin-top: 30px; padding: 15px; background-color: #e8e8e8; border-radius: 5px; }");
        out.println(".menu-options { display: flex; flex-direction: column; align-items: center; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container'>");
        out.println("<h1>" + titulo + "</h1>");
        out.println("<p style='font-size: 18px; color: #666; margin-bottom: 30px;'>Selecciona el tipo de conversión que deseas realizar:</p>");

        out.println("<div class='menu-options'>");
        out.println("<a href='conversor-numeros?tipo=binario-decimal' class='btn'>Binario → Decimal</a>");
        out.println("<a href='conversor-numeros?tipo=decimal-binario' class='btn'>Decimal → Binario</a>");
        out.println("</div>");

        out.println("<div class='integrantes'>");
        out.println("<h2>Integrantes:</h2>");
        out.println("<p>Javier Alberto Espinoza - 201610010339</p>");
        out.println("<p>Levith Alexander Silva - 202030110007</p>");
        out.println("</div>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
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
        out.println("<style>");
        out.println("body { font-family: Arial; background-color: #f5f5dc; text-align: center; margin: 0; padding: 20px; }");
        out.println("h1 { color: #003366; margin-bottom: 30px; }");
        out.println(".container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println(".btn { padding: 10px 20px; font-size: 16px; background-color: #003366; color: white; border: none; border-radius: 5px; cursor: pointer; margin: 5px; text-decoration: none; display: inline-block; }");
        out.println(".btn:hover { background-color: #002244; }");
        out.println(".btn-secondary { background-color: #666; }");
        out.println(".btn-secondary:hover { background-color: #555; }");
        out.println("input[type='text'] { padding: 10px; width: 300px; font-size: 16px; margin: 10px; }");
        out.println("table { margin: 20px auto; border-collapse: collapse; width: 80%; }");
        out.println("th, td { padding: 12px; text-align: center; border: 1px solid #ddd; }");
        out.println("th { background-color: #003366; color: white; }");
        out.println(".integrantes { margin-top: 30px; padding: 15px; background-color: #e8e8e8; border-radius: 5px; }");
        out.println(".error { color: red; margin: 20px; font-weight: bold; padding: 10px; background-color: #ffe6e6; border-radius: 5px; }");
        out.println(".warning { color: #8B8000; margin: 10px; padding: 10px; background-color: #fffacd; border-radius: 5px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        // Botones de navegación
        out.println("<div style='position: absolute; top: 20px; left: 20px;'>");
        out.println("<a href='conversor-numeros' class='btn btn-secondary'>Menú Principal</a>");
        out.println("</div>");

        out.println("<div class='container'>");
        out.println("<h1>" + tituloConversion + "</h1>");

        // Mensaje informativo
        out.println("<div class='warning'>");
        out.println("<strong>⚠️ Atención:</strong> Solo puedes realizar una conversión a la vez. ");
        out.println("Para cambiar el tipo de conversión, regresa al menú principal.");
        out.println("</div>");

        // Formulario de conversión
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

        // Mostrar resultados en tabla si hay conversión exitosa
        if (!resultado.isEmpty() && !resultado.startsWith("Error:")) {
            out.println("<table>");
            out.println("<tr><th>Entrada</th><th>Salida</th></tr>");
            out.println("<tr><td>" + numeroInput + "</td><td><strong>" + resultado + "</strong></td></tr>");
            out.println("</table>");

            // Opciones después de la conversión
            out.println("<div style='margin-top: 30px; padding: 20px; background-color: #f0f8ff; border-radius: 5px;'>");
            out.println("<h3 style='color: #003366;'>¿Qué deseas hacer ahora?</h3>");
            out.println("<a href='conversor-numeros?tipo=" + tipoConversion + "' class='btn'>Realizar Otra Conversión</a>");
            out.println("<a href='conversor-numeros' class='btn btn-secondary'>Cambiar Tipo de Conversión</a>");
            out.println("</div>");
        }
        // Mostrar error si la conversión falló
        else if (resultado.startsWith("Error:")) {
            out.println("<div class='error'>" + resultado + "</div>");
            out.println("<a href='conversor-numeros?tipo=" + tipoConversion + "' class='btn'>Intentar de Nuevo</a>");
        }

        // Información de integrantes
        out.println("<div class='integrantes'>");
        out.println("<h2>Integrantes:</h2>");
        out.println("<p>Javier Alberto Espinoza - 201610010339</p>");
        out.println("<p>Levith Alexander Silva - 202030110007</p>");
        out.println("</div>");

        // Script para validación
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
        out.println("🏠 Volver al Menú Principal");
        out.println("</a>");
        out.println("</body>");
        out.println("</html>");
    }

    private void procesarConversion(String tipoConversion, String numero, PrintWriter out) {
        try {
            String resultado = "";

            if ("binario-decimal".equals(tipoConversion)) {
                // Convertir binario a decimal
                int decimal = Integer.parseInt(numero, 2);
                resultado = String.valueOf(decimal);
            } else if ("decimal-binario".equals(tipoConversion)) {
                // Convertir decimal a binario
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