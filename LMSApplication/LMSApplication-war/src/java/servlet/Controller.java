/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import ejb.session.stateless.StaffSessionBeanLocal;
import ejb.session.stateless.StaffSessionBeanRemote;
import entity.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import manager.StaffManager;

public class Controller extends HttpServlet {

    @EJB
    private StaffSessionBeanLocal staffSessionBeanLocal;
    
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StaffManager staffManager = new StaffManager(staffSessionBeanLocal);
            String path = request.getPathInfo();
            path = path.split("/")[1];
            
            switch(path) {
                
  
//                case "loginStaff":
//                    response.sendRedirect(request.getContextPath() + "StaffLoginPage.jsp"); 
//                    return;
                    
                case "doLoginStaff": {
                    try {
                        String username = request.getParameter("username");
                        String password = request.getParameter("password");
                        Staff staff = staffManager.loginStaff(username, password);
                        response.sendRedirect(request.getContextPath() + "/StaffHomePage.jsp");
                        return;
                    } catch (Exception e) {
                        response.sendRedirect(request.getContextPath() + "/InvalidLoginCredentialsError.jsp");

                    }
                    
                }
                    
                    
                default:
                    path = "error";
                    break;
                    
                    
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/error.jsp").forward(request, response);  
        }
    }
    
    //auto generated codes which will call processRequest for both //HTTP GET and POST requests
    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException { //
            processRequest(request, response);

    }
    
    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
            processRequest(request, response); 

    }
    
    
    @Override
    public String getServletInfo() {
        return "Short description"; 
    }
}