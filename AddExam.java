//AddExam.java


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AddExam extends HttpServlet
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
             pw.println("<html><body><font color=blue><h2><center>Add Exam For an Existing  Course</center> </h2><form method=POST action=/servlet/AddExam><br><br><table align='center'><tr><td><b>Choose Course ID:<select name='CID'> ");
try{
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery("select course_id from courses order by 1");
	while( rs.next())
	pw.println("<option>" + rs.getString(1) + "<br>" );
	rs.close();
	st.close();
	pw.println("</select></b></td></tr><tr><td><input type=submit  value='--Add--'></td><tr></table></form></font></body></html>");
	}catch(Exception e){
		e.printStackTrace();
	}	        
        }

      public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
     {
             res.setContentType("text/html");
             PrintWriter out=res.getWriter();
       String cid=req.getParameter("CID");
           out.println("<html><body><font color=blue><h2><center>Examination Paper For the Course Id :"+cid+"</center></h2><br><br><form action=/servlet/InsertExam method=POST><table border=1 align='center'>");
        try{
  out.println("<tr><td>Course Id </td><td><input type=text name=cid value="+cid+"></td></tr>");
  out.println("<tr><td>Question  Id </td><td><input type=text name=qid></td></tr>");
out.println("<tr><td>Question Description </td><td><input type=text name=qd size=50></td></tr>");
out.println("<tr><td>Option 1 </td><td><input type=text name=op1></td></tr>");
out.println("<tr><td>Option 2 </td><td><input type=text name=op2></td></tr>");
out.println("<tr><td>Option 3 </td><td><input type=text name=op3></td></tr>");
out.println("<tr><td>Option 4 </td><td><input type=text name=op4></td></tr>");
out.println("<tr><td>Answer </td><td><input type=text name=ans></td></tr>");
out.println("<tr><td><input type=submit value='Insert'></td><td><input type=reset value='Clear'></td></tr></form></font></body></html>");
           }catch(Exception e){
	e.printStackTrace();
          }
   }
}