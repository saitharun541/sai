//AddAdmin Page

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AddAdmin extends HttpServlet
{
  Connection con;
  public void init(ServletConfig sc)throws ServletException
  {
    super.init(sc);
     try
    {
       Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
       con=DriverManager.getConnection("jdbc:odbc:elearn","scott","tiger");
    }   
    catch(Exception e){System.out.println(e); }
  }  

      public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
             res.setContentType("text/html");
             PrintWriter pw=res.getWriter();
             pw.println("<html><body><center><font color=blue><br><h2>Add a New Adminstrator </h2></center><form method=POST action=/servlet/AddAdmin><br><br><br><table align='center'><tr><td>Admin Name </td><td><input type=text  name='aname'></td></tr><tr><td>Password</td><td><input type=password  name='pass'></td></tr><tr><td><input type=submit value=Add></td></tr></table></form></body></html>");
        }
     public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,
                                            IOException
     {
             res.setContentType("text/html");
             PrintWriter pw=res.getWriter();
               String aname = req.getParameter("aname");
          String pass = req.getParameter("pass");
	 try{
PreparedStatement ps = con.prepareStatement("insert into admin values(?,?) ");
	ps.setString( 1, aname);
	ps.setString( 2, pass);
	ps.executeUpdate();
 	ps.close();	
    	pw.println("<html><body bgcolor=#FFFFFF><center><font color=blue><h2>Admin Name Created  Succesfully</h2><br><br><br><a href=http://localhost:8080/admin.html>Admin Home </a></body></html>");		
                 }catch(Exception e){     e.printStackTrace();           }       
     }	
 }
