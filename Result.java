//Result.java

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Result extends HttpServlet
{
String uname="";
Connection con;
java.util.Vector v;
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
 PrintWriter out=res.getWriter();
try
{
HttpSession hs=req.getSession(true);
uname=(String)hs.getValue("uname");
System.out.println("Uname :"+uname);
Vector v=null;
try
{
v=(Vector)hs.getValue("data");
}
catch(Exception e)
{
}
if(v==null)
{
ObjectInputStream in=new ObjectInputStream(new FileInputStream(req.getParameter("fn")));
v=(Vector)in.readObject();
System.out.println(v);
}
int i=((Integer)v.elementAt(v.size()-1)).intValue();

if(i>0)
{
String name=req.getParameter("op");
Questions temp=(Questions)v.elementAt(i-1);
temp.ar=name;
System.out.println("Answer :"+temp.ar);
v.setElementAt(temp,i-1);
hs.putValue("data",v);
}
if(i >=v.size()-1)
{
showResult(out,hs);
return;
}
Questions q=(Questions)v.elementAt(i);
v.setElementAt(new Integer(i+1),v.size()-1);
hs.putValue("data",v);
out.println("<form action='Result'><font color=blue><h2>Questions </h2><hr><br><table align='Center'>");
out.println("<tr><td>Question </td><td>"+q.qd+"</td></tr>");
out.println("<tr><td><input type=radio name=op value="+q.op1+"></td><td>"+q.op1+"</td></tr>");
out.println("<tr><td><input type=radio name=op value="+q.op2+"></td><td>"+q.op2+"</td></tr>");
out.println("<tr><td><input type=radio name=op value="+q.op3+"></td><td>"+q.op3+"</td></tr>");
out.println("<tr><td><input type=radio name=op value="+q.op4+"></td><td>"+q.op4+"</td></tr>");
out.println("<tr><td><input type=submit value='Submit'></td><td><input type=reset value='Clear'></td></tr>");
out.println("</table></font></form></body></html>");
}
catch(Exception e)
{
e.printStackTrace();
}
}
private void showResult(PrintWriter out,HttpSession hs)throws Exception
{
Vector v=(Vector)hs.getValue("data");
System.out.println("Uname :"+uname);
int marks=0,a=0;
for(int i=0;i<v.size()-1;i++)
{
Questions q=(Questions)v.elementAt(i);
if(q.ar.equals(q.ans))
{
marks+=5;
++a;
}
}
out.println("<font color=blue><h2><center>Result </center></h2><hr><br><br><h3>");
out.println("No of Correct Ans :<font color=red>"+a);
out.println("</font><br><br>No .of Marks :<font color=red>"+marks);
out.println("<br><br><center><a href='http://localhost:8080/index.htm'>Home </a></center></font></h3></font>");
}
}