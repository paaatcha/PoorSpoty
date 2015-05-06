package br.PoorSpoty.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.PoorSpoty.beans.LoginBean;

@WebFilter("/pag_admin/*")
public class AutorizacaoFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		/*-----------------------------------------------------------*/
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		
		LoginBean auth = (LoginBean) request.getSession().getAttribute("auth");
		
		if(auth != null && auth.isLogado() && !request.getRequestURI().endsWith("/login.xhtml")){
			 // User is logged in, so just continue request.
            chain.doFilter(request, response);
		}else{
			response.sendRedirect(request.getContextPath() + "/index.faces");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
