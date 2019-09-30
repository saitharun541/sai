//UserValid Page

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class UserValid extends HttpServlet
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
try
{
res.setContentType("text/html");
PrintWriter pw=res.getWriter();
HttpSession hs=req.getSession(true);
String uname=req.getParameter("username");
String pass=req.getParameter("password");
Statement st=con.createStatement();
ResultSet rs=st.executeQuery("select  * from ereg where userid =" +"'"+uname+"'"+"and password = "+"'"+pass+"'");
if(!rs.next())
{
pw.println("<html><body><center><font color=blue><br><h3>Not a Valid UserName Or Password Try with Valid User Name & Password  </h3><input type=button value=Back onClick='history.back()'></center>");
}
else
{
hs.putValue("uname",uname);
hs.putValue("pass",pass);
pw.println("<html><body><center><font color=blue><br><h3>Welcome to Elearning Site "+rs.getString(3)+"</h3><hr>");
pw.println("<br><table align='center'><tr><td><a href ='http://localhost:8080/servlet/Courses'>Availale Courses </a></td></tr>");
pw.println("<tr><td></td></tr><tr><td><a href ='http://localhost:8080/servlet/Examination'>Examination</a></td></tr></table>");
pw.println("</center></body><html>");
}
}
catch(Exception e){     e.printStackTrace();           
}       
}
public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
doGet(req,res);
}
public void destroy() {
        try{
              if(con != null)
                con.close();
         }catch(Exception e){
         }
   }
}