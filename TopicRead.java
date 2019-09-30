//TopicRead.java

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

public class TopicRead extends HttpServlet {
 private Connection con;
PrintWriter out;
 public void init(ServletConfig conf)throws ServletException {
     super.init(conf);
     try{         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
       con=DriverManager.getConnection("jdbc:odbc:elearn","scott","tiger");
    }catch(Exception e){
        throw new ServletException(e.getMessage());
    }
  }
  public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {     
try{res.setContentType("text/html");
String sid=null,cid=null;
out = res.getWriter();
java.util.Enumeration e =req.getParameterNames();
while(e.hasMoreElements())
{
cid=req.getParameter(e.nextElement().toString());
sid=req.getParameter(e.nextElement().toString());
}
Statement st=con.createStatement();
String sql="select  * from session_details where course_id =" +"'"+cid+"'"+" and session_no = "+"'"+sid+"'";
System.out.println(sql);
ResultSet rs=st.executeQuery(sql);
out.println("<html><body><center><font color=blue><h3>");
if(rs.next())
{
out.println("Session Details </h3></font></center>");
BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(rs.getString(3))));
String line="";
while((line=in.readLine()) !=null)
out.println(line);
return ;
}
else
{
out.println("<html><body><center><font color=blue><h3><br><br><br>");
out.println("Course Content Yet to Register in to the DataBase<br><br><br><input type=button value=Back onclick='history.back()'></h3></center></body></html>");
}
st.close();
rs.close();
}
catch(Exception e){             out.println("<pre>");
      e.printStackTrace(out);
     out.println("</pre>");
    }
    out.close();
   }
  public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {     
doGet(req,res);
}
}	