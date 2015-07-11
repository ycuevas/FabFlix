

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class AllPrivileges
 */
public class AllPrivileges extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String jdbcDriverName = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost/";
	
	private Connection connection;
	private Statement stment;
	private String username;
	private String password;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllPrivileges() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	public List<List<String>> getUserData(HttpServletRequest request)
    {
    	List<List<String>> userData = new ArrayList<List<String>>();
    	try{
    		Context initCtx = new InitialContext();	   
		    Context envCtx = (Context) initCtx.lookup("java:comp/env");	
	        DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
	        Connection dbcon = ds.getConnection();
            Statement statement = dbcon.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM employees, mysql.user WHERE user = email;");
			while(rs.next())
			{
				userData.add(new ArrayList<String>());
				
			}

			int i = 0;
			
			if (rs.first())
			{
				do
				{
					userData.get(i).add(rs.getString("email"));
					userData.get(i).add(rs.getString("host"));
					userData.get(i).add(rs.getString("Select_priv"));
					userData.get(i).add(rs.getString("Insert_priv"));
					userData.get(i).add(rs.getString("Update_priv"));
					userData.get(i).add(rs.getString("Delete_priv"));
					userData.get(i).add(rs.getString("Execute_priv"));
					i+=1;
				} while (rs.next());
			}
    	}
    	
    	catch(SQLException | NamingException e){
    		e.printStackTrace();
    		
    	}
 	
    	return userData;
    }
  
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("userData", getUserData(request));
			response.setStatus(HttpServletResponse.SC_OK);
			request.getRequestDispatcher("/showprivileges.jsp").forward(request,response);
		}
		catch( Exception e){
			e.printStackTrace();
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
