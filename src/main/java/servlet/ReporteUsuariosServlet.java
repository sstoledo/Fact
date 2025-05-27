package servlet;

import DAO.ClienteJpaController;
import DTO.Cliente;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;

@WebServlet(name = "ReporteClientesServlet", urlPatterns = {"/ReporteClientesServlet"})
public class ReporteUsuariosServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        try {
            // 1. Obtener datos
            ClienteJpaController usuDAO = new ClienteJpaController();
            List<Cliente> listaClientes = usuDAO.findClienteEntities();

            // 2. Verificar datos
            if (listaClientes.isEmpty()) {
                throw new ServletException("No se encontraron usuarios");
            }

            // 3. Cargar reporte
            String jasperPath = getServletContext().getRealPath("/reportes/ReporteUsuarios.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(jasperPath));

            // 4. Llenar reporte
            Map<String, Object> params = new HashMap<>();

            // Establece la conexión a la base de datos
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/practica", "root", "");

// Llena el reporte usando la conexión
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conexion);

            // 5. Configurar el exportador HTML
            JRExporter exporter = new HtmlExporter();

            // Configurar la salida del exportador
            SimpleHtmlExporterOutput exporterOutput = new SimpleHtmlExporterOutput(response.getWriter());
            exporter.setExporterOutput(exporterOutput);

            // Configurar la entrada del exportador
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

            // Configurar la respuesta HTTP
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");

            // Exportar el reporte como HTML
            exporter.exportReport();

        } catch (JRException e) {
            throw new ServletException("Error Jasper: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ReporteUsuariosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ReporteUsuariosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
