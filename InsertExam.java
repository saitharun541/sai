//InsertExam.java


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class InsertExam extends HttpServlet
{
  Connection con;
PrintWriter out;
PreparedStatement ps;
  public void init(ServletConfig sc)throws ServletException
  {
    super.init(sc);
     try
    {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
       con=DriverManager.getConnection("jdbc:odbc:elearn","scott","tiger");
ps=con.prepareStatement("insert into question_answer values(?,?,?,?,?,?,?,?)");
    }   
    catch(Exception e){System.out.println(e); }
  }  

      public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
{
             res.setContentType("text/html");
out=res.getWriter();
out.println("<html><body>");
try
{
String cid=req.getParameter("cid");
String qid=req.getParameter("qid");
String qd=req.getParameter("qd");
String op1=req.getParameter("op1");
String op2=req.getParameter("op2");
String op3=req.getParameter("op3");
String op4=req.getParameter("op4");
String ans=req.getParameter("ans");
System.out.println(cid +"  "+qid +"  "+qd +"  "+op1 +"  "+op2 +"  "+op3 +"  "+op4 +"  "+ans); 
if(cid.equals("") || cid == null || qid.equals("") || qid == null || qd.equals("") || qd == null || op1.equals("") || op1 == null || op2.equals("") || op2 == null || op3.equals("") || op3 == null || op4.equals("") || op4 == null || ans.equals("") || ans == null)
{
out.println("<h3><font color=red><center>The Required Details are Insufficient <br><br><br><input type=button value=Back onClick='history.back()'> </center></h3>");
}
else
{
ps.setString(1,cid);
ps.setString(2,qid);
ps.setString(3,qd);
ps.setString(4,op1);
ps.setString(5,op2);
ps.setString(6,op3);
ps.setString(7,op4);
ps.setString(8,ans);
ps.execute();
out.println("<font color=blue><h3>Exam Details Added Successfully</font><br><br><a href=http://localhost:8080/admin.html>Admin Home</a></h3>");
}
}
catch(Exception e)
{
e.printStackTrace();
}
}
public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
{
doPost(req,res);
}
}