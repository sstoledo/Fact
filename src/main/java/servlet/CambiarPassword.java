/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
@WebServlet(name = "CambiarPassword", urlPatterns = {"/CambiarPassword"})
public class CambiarPassword extends HttpServlet {

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
                int codi;
                HttpSession misession = request.getSession(false);
                if (misession != null) {
                    // Recuperar el atributo "logi"
                    codi = (int) misession.getAttribute("Codigo");

                } else {
                    // Manejar el caso en que no hay sesión activa
                    throw new Exception("No hay sesión activa.");
                }

                String password = request.getParameter("pass");
                String newPassword = request.getParameter("newpass");

                //Desciframos
                password = AESUtil.decrypt(password);
                newPassword = AESUtil.decrypt(newPassword);

                //Hasheamos el Password
                String passHash = SHA2Hasher.hash("1nf0rm4t1c4" + password, "SHA-512");
                

                //Hallamos al Cliente
                ClienteJpaController cliDAO = new ClienteJpaController();
                Cliente c = cliDAO.findCliente(codi);
                if (c != null) {
                    if (c.getPasCli().equals(passHash)) {
                        c.setPasCli(newPassword);
                        cliDAO.edit(c);
                        out.println("{\"resultado\":\"ok\"}");
                    } else {
                        out.println("{\"resultado\":\"Contraseña Anterior Incorrecta\"}");
                    }
                } else {
                    out.println("{\"resultado\":\"Cliente no encontrado\"}");
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
