//ModifyCourse Page

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ModifyCourse extends HttpServlet
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
             HttpSession ses = req.getSession(true);
	ses.invalidate();
             PrintWriter pw=res.getWriter();
             pw.println("<html><body><font color=blue><h2><center>Modify an Existing  Course</center> </h2><form method=POST action=/servlet/ModifyCourse><br><br><table align='center'><tr><td><b>Choose Course ID:<select name='CID'> ");
try{
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery("select course_id from courses order by 1");
	while( rs.next())
	pw.println("<option>" + rs.getString(1) + "<br>" );
	rs.close();
	st.close();
	pw.println("</select></b></td></tr><tr><td><input type=submit  value='--GO--'></td><tr></table></form></font></body></html>");
	}catch(Exception e){
		e.printStackTrace();
	}	        
        }
	

	
      public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
     {
             res.setContentType("text/html");
             PrintWriter pw=res.getWriter();
             HttpSession ses = req.getSession(true);
             if (ses.getValue("CID") == null)
	ses.putValue("CID" , req.getParameter("CID"));
             String cid = (String)   ses.getValue("CID"); 	
             System.out.println(cid);
           pw.println("<html><body><font color=blue><h2><center>Modify The Course Id :"+cid+"Details</center></h2><br><br><form action=/servlet/ModifyCourse method=POST><table border=1 align='center'><tr color='cyan'><th>Delete</th><th>Session Number</th><th>Topic</th></tr>");
        Statement st = null;
        try{
              String add=req.getParameter("ADD");
               System.out.println(add);
          if(  add!= null && ! add.equals("")){
               PreparedStatement ps = con.prepareStatement("insert into course_details values( ?,?,?) ");
	ps.setString(1 ,cid);
	ps.setString(2 ,req.getParameter("session_NO"));
	ps.setString(3 ,req.getParameter("topic") );
	ps.executeUpdate();
	ps.close();
           }
	 
          if( req.getParameter("DEL") != null && ! req.getParameter("DEL").equals("")){
          	  String[] values = req.getParameterValues( "C");
             PreparedStatement  ps =con.prepareStatement("delete from course_details where course_id=? and session_no=?");        
                for(int i=0;i<values.length;i++){
		System.out.println("deleting " + values[i]);
                    ps.setString( 1 , cid);
	   ps.setString(2 , values[i]);
	 ps.executeUpdate();
	}
	ps.close();
         }
                      
	 st =con.createStatement();
	ResultSet rs = st.executeQuery("select * from course_details where course_id='" + cid + "' order by session_no" );
            while( rs.next()){
cid=rs.getString(1);
String sess=rs.getString(2);
String topic=rs.getString(3);
	pw.println("<tr><td><input type=checkbox name=C value=" + sess + "></td><td>" + sess + "</td><td><a href='#' onClick=window.open('http://localhost:8080/servlet/CourseContent?cid="+cid+"&cc="+sess+"','CourseContent','toolbar=0,scrollbars=1,width=500,height=500'); return false;>" + topic + "</a></td></tr>");	
              }
	rs.close();
	st.close();
             pw.println("<tr><td><input type=submit value=Add name=ADD></td><td><input type=text name=session_NO></td><td><input type=text name=topic></td></tr><tr><td cellspan=3>");
              pw.println("<input type=submit value='Delete All' name='DEL'></td></tr></table></form></font></body></html>"); 
           }catch(Exception e){
	e.printStackTrace();
          }
   }
}