//Examination.java


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Random;

public class Examination extends HttpServlet
{
  Connection con;
java.util.Vector v;
HttpSession hs;
Random r=new Random();
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
hs=req.getSession(true);
String uname=(String)hs.getValue("uname");
String pass=(String)hs.getValue("pass");
if((uname == null) || (pass == null))
{
pw.println("<html><body><center><font color=blue><br><h3>First Login using your Userid and Password then Access Course Infomation </h3></font><br><a href='http://localhost:8080/login.html'>Sign In </a></center></body></html>");
return;
}
hs.putValue("uname",uname);
             pw.println("<html><body><font color=blue><h2><center>Select  Exam For an Existing  Course</center> </h2><form method=POST action=/servlet/Examination><br><br><table align='center'><tr><td><b>Choose Course ID:<select name='CID'> ");
try{
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery("select course_id from courses order by 1");
	while( rs.next())
	pw.println("<option>" + rs.getString(1) + "<br>" );
	rs.close();
	st.close();
	pw.println("</select></b></td></tr><tr><td><input type=submit  value='--Select--'></td><tr></table></form></font></body></html>");
	}catch(Exception e){
		e.printStackTrace();
	}	        
        }

      public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
     {
try{
             res.setContentType("text/html");
             PrintWriter out=res.getWriter();
hs=req.getSession(true);
String cid=req.getParameter("CID");
String uname=(String)hs.getValue("uname");
hs.putValue("uname",uname);
           out.println("<html><body><font color=blue><h2><center>Examination Paper For the Course Id :"+cid+"</center></h2><br><br><form action=/servlet/Result method=GET>");
Statement st=con.createStatement();
ResultSet rs=st.executeQuery("select * from question_answer where course_id='"+cid+"'");
v=new java.util.Vector();
while(rs.next())
{
Questions q=new Questions();
q.cid=rs.getString(1);
q.qid=rs.getString(2);
q.qd=rs.getString(3);
q.op1=rs.getString(4);
q.op2=rs.getString(5);
q.op3=rs.getString(6);
q.op4=rs.getString(7);
q.ans=rs.getString(8);
v.addElement(q);
}
st.close();
rs.close();
System.out.println("Vector :"+v);
Integer i=new Integer(0);
System.out.println(System.getProperty("user.dir"));
//int r1=r.nextInt(1000);
long r1=Math.round((Math.random()*1000));
System.out.println(System.getProperty("user.dir"));
ObjectOutputStream obj=new ObjectOutputStream(new FileOutputStream("obj"+r1));
v.addElement(i);
obj.writeObject(v);
obj.close();
res.sendRedirect("http://127.0.0.1:8080/servlet/Result?fn=obj"+r1);
          }catch(Exception e){
	e.printStackTrace();
          }
   }
}