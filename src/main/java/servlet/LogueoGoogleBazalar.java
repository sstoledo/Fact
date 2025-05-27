/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.JwtUtil;

/**
 *
 * @author bazal
 */
@WebServlet(name = "LogueoGoogleBazalar", urlPatterns = {"/logueogoogle"})
public class LogueoGoogleBazalar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Leer JSON con id_token
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        String jsonString = sb.toString();

        String idTokenString = "";
        try {
            JsonObject json = new Gson().fromJson(jsonString, JsonObject.class);
            idTokenString = json.get("id_token").getAsString();
        } catch(Exception e){
            out.println("{\"resultado\":\"error\"}");
            return;
        }

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("292390968005-lih4qij2b7ull49dm0si62rqr0qff7s6.apps.googleusercontent.com"))
                .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                // Aquí puedes validar o registrar el usuario en tu BD

                // Crear JWT propio para tu app
                String token = JwtUtil.generarToken(email);

                // Crear sesión y devolver JSON con token
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", email);

                out.println("{\"resultado\":\"ok\",\"token\":\"" + token + "\"}");

            } else {
                out.println("{\"resultado\":\"error\"}");
            }
        } catch(Exception e){
            e.printStackTrace();
            out.println("{\"resultado\":\"error\"}");
        }
    }
}
