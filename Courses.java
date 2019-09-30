//Courses.java

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
public class Courses extends HttpServlet {
 private Connection con;
 
 public void init(ServletConfig conf)throws ServletException {
     super.init(conf);
     try{     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
       con=DriverManager.getConnection("jdbc:odbc:elearn","scott","tiger");
    }catch(Exception e){
        throw new ServletException(e.getMessage());
    }
  }
  public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {     res.setContentType("text/html");
PrintWriter out = res.getWriter();
HttpSession hs=req.getSession(true);
String uname=(String)hs.getValue("uname");
String pass=(String)hs.getValue("pass");
if((uname == null) || (pass == null))
{
out.println("<html><body><center><font color=blue><br><h3>First Login using your Userid and Password then Access Course Infomation </h3></font><br><a href='http://localhost:8080/login.html'>Sign In </a></center></body></html>");
return;
}
    try{
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select course_cat,course_name from courses");
       //ResultSetMetaData rsmd = rs.getMetaData();
       //int cols = rsmd.getColumnCount();
out.println("<center> <font size=5 face='Geneva, Arial, Helvetica, ans-serif'><cite>Courses Information </cite></font></center>");
       out.println("<table width=75% border=1 align=center bordercolor='#0000FF'>");
       out.println("<tr> <font color=red>");
       out.println("<th> Course Category </th>");
       out.println("<th> Course Name</th>");
       out.println("</font> </tr>");
       while(rs.next()){
           out.println("<tr>");
           for(int i=1;i<=2;i++){
         String s = rs.getString(i);
        if(s==null || s.equals(""))
              out.println("<td> </td>");
         else{
	        out.println("<td>");
	if(i==2)
	  out.println("<a href=http://localhost:8080/servlet/CourseDetails?id="+URLEncoder.encode(s)+">"+s+"</td>");
   

	else
	  out.println(s + "</td>");
            }

          }
          out.println("</tr>");
       }  
       rs.close();
       st.close();       out.println("</table>");
     }catch(Exception e){             out.println("<pre>");
      e.printStackTrace(out);
     out.println("</pre>");
    }
    out.close();
   }
   public void destroy() {
        try{
              if(con != null)
                con.close();
         }catch(Exception e){
         }
   }
}  