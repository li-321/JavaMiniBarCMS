package com.bar.json;

import com.bar.db.ExecuteDB;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author  Mr.tao
 * @date    2016-9-13 22:54:32
 * @version V2.0
 * @desc  根据ID获取music地址 MusicUrl.json?callback=88&id=2
 */
public class MusicUrlJson extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/json;charset=GB2312");
        try (PrintWriter out = response.getWriter()) {
            ExecuteDB DB = new ExecuteDB();
            String callback = request.getParameter("callback");
            String id = request.getParameter("id");
            if(id!=null && callback!=null){
                String sql = "SELECT musicurl FROM music where musicid="+id;  

                ResultSet rs = DB.exeQuery(sql);  

                JsonObject object = new JsonObject();  
                JsonArray array = new JsonArray();  

                while (rs.next()) {
                    JsonObject ob = new JsonObject();
                    ob.addProperty("url", rs.getString("musicurl"));
                    array.add(ob);

                }
                object.add("Music", array);  
                out.print(callback+"("+object.toString()+")");//使用jsonp进行访问json数据
            }else{
			out.print("你无权查看此页");
                }
        }
        catch(Exception ex){
            System.out.println(ex.toString());
	}
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
