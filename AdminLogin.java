//AdminLogin Page

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AdminLogin extends HttpServlet
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
	
      public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,
                                            IOException
     {
             res.setContentType("text/html");
             PrintWriter pw=res.getWriter();
             
             String uname=req.getParameter("username");
             String pwd=req.getParameter("password");
	 try{
                    	Statement st=con.createStatement();   
                      	ResultSet rs=st.executeQuery("select  * from admin where uname =" +"'"+uname+"'"+" and pwd = "+"'"+pwd+"'"); 
                   if(rs.next())
	{ 
                    	pw.println("<html><body bgcolor=#FFFFFF><center><h2> Welcome To Admin Page  <font color=blue >" +uname +"</font></h2>");      
		pw.println("<br><table align='center'><tr><td><a href ='http://localhost:8080/servlet/AddAdmin'>Add An Adiministrator</a></td></tr>");
pw.println("<tr><td></td></tr><tr><td><a href ='http://localhost:8080/servlet/AddCourse'>Add New Course</a></td></tr>");
//pw.println("<tr><td></td></tr><tr><td><a href ='http://localhost:8080/servlet/DelCourse'>Delete Course</a></td></tr>");
pw.println("<tr><td></td></tr><tr><td><a href ='http://localhost:8080/servlet/ModifyCourse' >Modify </td></tr>");
		pw.println("<tr><td></td></tr><tr><td><a href ='http://localhost:8080/servlet/AddExam' >Add New Exam<a></td></tr>");
		//pw.println("<tr><td></td></tr><tr><td><a href ='http://localhost:8080/servlet/ModifyExam' >Modify Exam</a></td></tr></table>");
                             	pw.println("</center></body><html>");
                          }
                        else{  
	    	pw.println("<html><body bgcolor=#FFFFFF><center><h2><font color=red><h2> You are not an Authorised Person to View this Page </h2><br>"); 
                                    pw.println("<input type=button value='Go Back' onClick=history.back()>");
		pw.println("</center></body><html>");
                                 }
                 
                    rs.close();
                 }catch(Exception e){     e.printStackTrace();           }       
     }	
 }
