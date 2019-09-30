//DelCourse.java

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class DelCourse extends HttpServlet
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
             pw.println("<html><body><font color=blue><h2><center>Delete an Existing  Course</center> </h2><form method=POST action=/servlet/DelCourse><br><br><table align='center'><tr><td><b>Choose Course ID:<select name='CID'> ");
try{
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery("select course_id from courses order by 1");
	while( rs.next())
	pw.println("<option>" + rs.getString(1) + "<br>" );
	rs.close();
	st.close();
	pw.println("</select></b></td></tr><tr><td><input type=submit  value='Delete'></td><tr></table></form></font></body></html>");
	}catch(Exception e){
		e.printStackTrace();
	}	        
        }
	

	
      public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
     {
             res.setContentType("text/html");
             PrintWriter pw=res.getWriter();
           pw.println("<html><body><font color=blue><h2><center>Deleting The Course Details</center></h2><br><br>");
        String cid=req.getParameter("CID");
System.out.println("Cid :"+cid);
        try{
        PreparedStatement ps = con.prepareStatement("delete from courses where course_id = ?");
	ps.setString(1 ,cid);
	ps.execute();
	ps.close();
             pw.println("You Have Successfully Remove the Course Details<br> <input type=button value='Go Back' onClick='history.back()'></form>");
           }catch(Exception e){
	e.printStackTrace();
          }
pw.println("</font></body></html>"); 
   }
}