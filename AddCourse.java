//AddCourse Page

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AddCourse extends HttpServlet
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
             pw.println("<html><body><center><font color=blue><h2>Add a New Course </h2></center><form method=POST action=/servlet/AddCourse><table align='center'><tr><td>Enter Course Name </td><td><input type=text  name='course_name'></td></tr><tr><td>Category</td><td><input type=text  name='category'></td></tr><tr><td>Number of Sessions</td><td><input type=text name=sessions></td></tr><tr><td><input type=submit value=Store></td><td><input type=reset value=Clear></td></tr></table></form></body></html>");
        }
     public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,
                                            IOException
     {
             res.setContentType("text/html");
             PrintWriter pw=res.getWriter();
               String coursename = req.getParameter("course_name");
          String sessions = req.getParameter("sessions");
          String category = req.getParameter("category");

	 try{
 Statement  st=con.createStatement();   
  ResultSet rs=st.executeQuery("select  nvl(max(substr(course_id,2)),0) + 1 from courses "); 
	rs.next();
      String cid = "c" + rs.getString(1);
  PreparedStatement ps = con.prepareStatement("insert into courses values(?,?,?,?) ");
	ps.setString( 1, cid);
	ps.setString( 2, coursename);
	ps.setString( 3, category);
	ps.setString( 4, sessions);
	ps.executeUpdate();
 	ps.close();	
	rs.close();
	st.close();
	    	pw.println("<html><body bgcolor=#FFFFFF><center><font color=blue><h2>Course Added Succesfully</h2><br>"); 
                  pw.println("<font color=red>The Course Id Generated was :</font>" + cid );
	pw.println("<br><br><b>Make Sure You Provide Session Beak Up's and Data so that students can make use of Your valuable course</b></font><br><br><a href=http://localhost:8080/admin.html>Admin Home </a></body></html>");		
                 }catch(Exception e){     e.printStackTrace();           }       
     }	
 }
