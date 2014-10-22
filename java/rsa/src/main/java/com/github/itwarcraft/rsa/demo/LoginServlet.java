package com.github.itwarcraft.rsa.demo;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.itwarcraft.rsa.RSAKeyFactory;
import com.github.itwarcraft.rsa.RSAUtil;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        System.out.println("--------------");
    }

	/**
	 * 生成密钥，打开login.jsp页面
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("login  get method");
		
		KeyPair key = RSAKeyFactory.getInstance().getKeyPair();
		request.getSession().setAttribute("key", key);
		RSAPublicKey pkey = (RSAPublicKey) key.getPublic();
		String module = pkey.getModulus().toString(16);
		request.setAttribute("module", module);
		String empoent = pkey.getPublicExponent().toString(16);
		request.setAttribute("empoent", empoent);
		System.out.println("module:"+module);
		System.out.println("empoent:"+empoent);
//		response.sendRedirect("login.jsp");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	/**
	 * 解密登陆密码，返回success.jsp
	 * 否则返回error.jsp
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("into post method");
		String pwd = request.getParameter("pwd");
		System.out.println("pwd:"+pwd);
		KeyPair key = (KeyPair) request.getSession().getAttribute("key");
		RSAPrivateKey rsap = (RSAPrivateKey) key.getPrivate();
		String path = null;
		try {
			//解密
			byte[] en_result = new BigInteger(pwd, 16).toByteArray();
			byte[] bs = RSAUtil.decrypt(rsap,en_result);
			String de_orig = new String(bs);
			StringBuffer sb = new StringBuffer();
			sb.append(de_orig);
			String uri_orig = sb.reverse().toString();
			
			//接收的数据做过编码处理，所以要还原
			String orig =URLDecoder.decode(uri_orig,"UTF-8");//
			System.out.println("result:"+orig);
			request.setAttribute("result", orig);
//			response.sendRedirect("success.jsp");
			path = "success.jsp";
 		} catch (Exception e) {
			e.printStackTrace();
//			response.sendRedirect("error.jsp");
			path = "error.jsp";
		}
		request.getRequestDispatcher(path).forward(request, response);
		
	}

}
