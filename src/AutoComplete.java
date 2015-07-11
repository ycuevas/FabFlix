

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;
import org.json.JSONException;






/**
 * Servlet implementation class AutoComplete
 */
public class AutoComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoComplete() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void getResult(){
    	
    	
//    	;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> resultList = new HashMap<String, String>();
		
		try {
			String mySearchText = request.getParameter("searchtext");
			String[] wordArray = mySearchText.split(" ");
			
			//System.out.println(wordArray.length); //DEBUG
			
			String query = "SELECT * FROM MOVIES WHERE MATCH (title) AGAINST ('";
			for (int i = 0; i < wordArray.length - 1; i++)
			{
				query += "+" + wordArray[i];
			}
			query += "+" + wordArray[wordArray.length - 1] + "*');";
			
			//System.out.println(query); //DEBUG
			
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
			Connection dbcon = ds.getConnection();
			Statement statement = dbcon.createStatement();
			ResultSet rs = statement.executeQuery(query);

			while(rs.next()){
				resultList.put(rs.getString("id"), rs.getString("title"));
			
			}
			
			JSONObject resultListConvert = new JSONObject(resultList);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(resultListConvert);
			out.flush();
			request.getRequestDispatcher("/list.jsp").forward(request, response);
		
		} 
		catch(Exception ex) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
