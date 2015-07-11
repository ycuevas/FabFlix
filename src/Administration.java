

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



/**
 * Servlet implementation class Administration
 */
public class Administration extends HttpServlet {
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
    public Administration() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @throws SQLException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    public void grant(String privilege, String user, String host) throws SQLException
    {
    	ResultSet rs;
    	
    	stment.executeQuery("GRANT "+privilege+ " ON *.* TO '"+user+"'@'"+host+"';");
    	rs = stment.executeQuery("FLUSH PRIVILEGES;");
    		
    }
    
    
    public void revoke(String privilege, String user, String host) throws SQLException
    {
    	ResultSet rs;
    	stment.executeQuery("REVOKE "+privilege+ " ON *.* FROM '"+user+"'@'"+host+"';");
    	rs = stment.executeQuery("FLUSH PRIVILEGES;");
    	
    	
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs;
		ArrayList<String> userList = new ArrayList<String>();
		try {
			Context initCtx = new InitialContext();	   
		    Context envCtx = (Context) initCtx.lookup("java:comp/env");	
	        DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
	        Connection dbcon = ds.getConnection();
            Statement statement = dbcon.createStatement();
			stment = dbcon.createStatement();
			
			Map<String, String[]> entries = (Map<String, String[]>)request.getParameterMap();
			Map <String, String> options = new HashMap<String, String>(); 
			
			for (Map.Entry<String, String[]> entry: entries.entrySet())
			{
				options.put(entry.getKey(), Arrays.toString(entry.getValue()).substring(1, Arrays.toString(entry.getValue()).length()-1));
		
			}

			{
			for (Map.Entry<String, String> vals: options.entrySet())
			{
				ResultSet host = stment.executeQuery("select Host from mysql.user where mysql.user.User = '"+options.get("options")+"';");
				host.next();

				if (vals.getKey().contains("add"))
				{				
					grant(vals.getValue(),options.get("options"),host.getString(1));
				}
				else if (vals.getKey().contains("rm"))
				{
					revoke(vals.getValue(),options.get("options"),host.getString(1));
				}
				
			}
			rs = stment.executeQuery("select user from mysql.user, employees WHERE user = email;");
					
			while(rs.next())
			{
				userList.add(rs.getString("user"));	
			}
			request.setAttribute("userList", userList);
			request.getRequestDispatcher("/administration.jsp").forward(request,response);
			
		}} catch (SQLException | NamingException e ) {
			e.printStackTrace();
		}
		
		

		
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
