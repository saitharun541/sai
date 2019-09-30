//CourseDetails.java

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

public class CourseDetails extends HttpServlet {
 private Connection con;
 public void init(ServletConfig conf)throws ServletException {
     super.init(conf);
     try{         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
       con=DriverManager.getConnection("jdbc:odbc:elearn","scott","tiger");
    }catch(Exception e){
        throw new ServletException(e.getMessage());
    }
  }
  public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {     
res.setContentType("text/html");
     PrintWriter out = res.getWriter();
String id=req.getParameter("id");
System.out.println("name"+id);
     try{
        Statement st = con.createStatement();
	String sql = "select * from courses where course_name='"+id.trim()+"'";
	System.out.println(sql);
        ResultSet rs = st.executeQuery(sql);
       ResultSetMetaData rsmd = rs.getMetaData();
       int cols = rsmd.getColumnCount();
out.println("<center> <font size=5 face='Geneva, Arial, Helvetica, ans-serif'><cite>Courses Information </cite></font></center>");
       out.println("<table width=75% border=1 align=center bordercolor='#0000FF'>");
       out.println("<tr> <font color=red>");
	for(int i=1;i<=cols;i++)
         out.println("<th>" + rsmd.getColumnLabel(i) + "</th>");
        out.println("</font> </tr>");
       rs.next();
System.out.println("ssss");
           out.println("<tr>");
	String cid = "";
 String s;
           for(int i=1;i<=cols;i++){
                if(i==1)
                 {
	  cid = rs.getString(1);
                    s = cid;
                  }
                 else
	s = rs.getString(i);
        if(s==null || s.equals(""))
              out.println("<td> </td>");
         else
            out.println("<td>"+s+"</td>");                   
        
       }

       out.println("</tr></table>");         
       out.println("<hr><center>Details<hr>");      
       rs.close();
       st.close();
      st = con.createStatement( );
      rs = st.executeQuery("select * from course_details where course_id ='" +cid+ "' order  by session_no "  );        
          out.println("<table border=1 width=50%>");
      out.println("<tr><th>Session No</th><th>Topic</th></tr>");	
      while( rs.next())
{
           cid=rs.getString(1);
           String sessid=rs.getString(2);
           String topic=rs.getString(3);
	//out.println("<tr><td>" + rs.getString(2) + "</td><td><a href=/servlet/TopicRead?" + URLEncoder.encode("id=" + cid + "&sid=" + sessid  )  + ">" +  topic) + "</a></td></tr>");
out.println("<tr><td>" + sessid + "</td><td><a href=/servlet/TopicRead?id=" +cid + "&sid=" + sessid +">" +  topic + "</a></td></tr>");
      }
     out.println("</table></center>");	

         

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