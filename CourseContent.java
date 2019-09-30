//CourseContent.java

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

public class CourseContent extends HttpServlet {
 private Connection con;
 public void init(ServletConfig conf)throws ServletException {
     super.init(conf);
     try{      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
       con=DriverManager.getConnection("jdbc:odbc:elearn","scott","tiger");
    }catch(Exception e){
        throw new ServletException(e.getMessage());
    }
  }
  public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {     res.setContentType("text/html");
     PrintWriter out = res.getWriter();
String sid=req.getParameter("cc");
String cid=req.getParameter("cid");
 try{
out.println("<html><body><center><font color=blue><h3>Session Information</h3></font></center>");
out.println("<form action='/servlet/CourseContent' method=POST><hr>");
out.println("<input type=hidden name=cid value="+cid+"><input type=hidden name=sid value="+sid+">");
out.println("<font color=red><b>Paste The Content Below In Html Format :</b></font><textarea name=ta cols=70 rows=20></textarea><br><input type=submit value=Paste>&nbsp;&nbsp;&nbsp;<input type=reset value=Clear>");
     }catch(Exception e){             out.println("<pre>");
      e.printStackTrace(out);
     out.println("</pre>");
    }
    out.close();
   }
public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {     try{
res.setContentType("text/html");
PrintWriter out = res.getWriter();
String cid=req.getParameter("cid");
String sid=req.getParameter("sid");
String fc=req.getParameter("ta");
String fname="c:\\"+cid+sid+".html";
FileOutputStream fout=new FileOutputStream(fname);
fout.write(fc.getBytes(),0,fc.length());
fout.flush();
PreparedStatement ps=con.prepareStatement("insert into session_details values(?,?,?)");
ps.setString(1,cid);
ps.setString(2,sid);
ps.setString(3,fname);
int i=ps.executeUpdate();
out.println("<html><body><center><font color=blue><h3>Updated Successfully</h3></font><br><input type=button value=Close onCLick='window.close()'></center></body></html>");
}catch(Exception e){
e.printStackTrace();
         }
}
public void destroy() {
        try{
              if(con != null)
                con.close();
         }catch(Exception e){
         }
   }
}
