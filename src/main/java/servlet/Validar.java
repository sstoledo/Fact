package servlet;

import DAO.ClienteJpaController;
import DTO.Cliente;
import Encrypters.AESUtil;
import Hash.SHA2Hasher;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Giancarlos
 */
@WebServlet(name = "Validar", urlPatterns = {"/Validar"})
public class Validar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            try {
                String logi = request.getParameter("logi");
                String passCifrado = request.getParameter("pass");
                //Desciframos
                String pass = AESUtil.decrypt(passCifrado);
                System.out.println("Descifrado: " + pass);
                //Hasheamos el Password
                String passHash = SHA2Hasher.hash("1nf0rm4t1c4" + pass, "SHA-512");
                //Validamos
                System.out.println(passHash);
                ClienteJpaController cliDAO = new ClienteJpaController();
                Cliente c = cliDAO.validar(logi, passHash);
                if (c != null) {
                    HttpSession misession = request.getSession(true);
                    misession.setAttribute("Codigo", c.getCodCli());
                    misession.setAttribute("logueoOk", "1");
                    out.println("{\"resultado\":\"ok\"}");
                } else {
                    out.println("{\"resultado\":\"error\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.print(e);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
