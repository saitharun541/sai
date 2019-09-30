//Registration Form

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class Registration extends HttpServlet
{
public Connection con;
PreparedStatement ps;
PrintWriter out;
String fname,mname,lname,suffix,workaddr,state,city,zip,country,workph,email,webaddr,job,uname,pass;
           public void init(ServletConfig sc)throws ServletException
	{
	 super.init(sc);
	 try
	  {
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
       con=DriverManager.getConnection("jdbc:odbc:elearn","scott","tiger");
ps=con.prepareStatement("insert into ereg values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	  }
	 catch(Exception e)
	  {
	    e.printStackTrace();
	  }
	}        //init
        public  void doPost(HttpServletRequest req, HttpServletResponse res)throws   ServletException,IOException
	{
try
{
	res.setContentType("text/html");
	out=res.getWriter();
	boolean b=readValues(req);
	if(!b)
	{
	out.println("<h3><font color=blue>Required Inputs are Insufficient </font></h3><br><form><input type=button value='Back' onClick='history.back()'></form>");
return;
}
Statement st=con.createStatement();
/*ResultSet rs1=st.executeQuery("select nvl(max(substr(userid,4)),0)+1 from ereg");
rs1.next();
String uid="ELN"+rs1.getString(1);
System.out.println("uid :"+uid);*/
ps.setString(1,uname);
     	ps.setString(2,pass);
	         	ps.setString(3,fname);
                                    ps.setString(4,mname);
       	        	ps.setString(5,lname);
                                    ps.setString(6,suffix);
                                    ps.setString(7,workaddr);
	         	ps.setString(8,city);
                                    ps.setString(9,state);
	         	ps.setString(10,country);	
	         	ps.setString(11,zip);
	        	ps.setString(12,workph);
   	         	ps.setString(13,email);
	         	ps.setString(14,job);
	         	ps.setString(15,webaddr);
	         	int i=ps.executeUpdate();
		if(i==1)
		   {
out.println("<html><head><title>Registration Successful</title></head>");
out.println("<body bgcolor=#FFFFFF>");
out.println("<strong><h1><center><font color=blue>E-LEARNING</font></center></h1></strong>");
out.println("<br><h2>Registration Successful </h2>");                             out.println("<font color=blue><h2> Welcome to the New Experience of E-learning");
out.println("</h2>Your User id For Further Correspondense is <font color=red><b>"+uname+"</b><br>");
out.println("<br><h3>The Following Courses Offered In this Site <a href='http://localhost:8080/servlet/Courses'>Courses</a></font>");
out.println("</body></html>");
}
}
catch(Exception e)
{
e.printStackTrace(out);
}  
}  //doPost
public boolean readValues(HttpServletRequest req)throws Exception
{
fname=req.getParameter("first_name");
mname=req.getParameter("mid_name");
lname=req.getParameter("last_name");
suffix=req.getParameter("suffix");
workaddr=req.getParameter("addr");
state=req.getParameter("state");
city=req.getParameter("city");
zip=req.getParameter("zip");
country=req.getParameter("country");
workph=req.getParameter("phone");
email=req.getParameter("email");
webaddr=req.getParameter("webaddr");
job=req.getParameter("job");
pass=req.getParameter("password");
uname=req.getParameter("user");

if((fname==null) || fname.equals("") || (workaddr == null) || workaddr.equals("") || (zip == null) || zip.equals("") || (workph == null) || workph.equals("") || (pass == null) || pass.equals("")||(uname == null) || uname.equals("") )
return false;
else
return true;
} 
} //main
