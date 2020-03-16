/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import dtos.RoleDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ngochuu
 */
public class AuthorFillter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private final List<String> customer;
    private final List<String> admin;
    private final List<String> quest;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AuthorFillter() {
        quest = new ArrayList<>();
        quest.add("");
        quest.add("footer.jsp");
        quest.add("header.jsp");
        quest.add("search.jsp");
        quest.add("slideShow.jsp");
        quest.add("car.jsp");
        quest.add("error.jsp");
        quest.add("index.jsp");
        quest.add("login.jsp");
        quest.add("registration.jsp");
        quest.add("verification.jsp");
        quest.add("welcome.jsp");

        quest.add("LoginAction");
        quest.add("RegistrationAction");
        quest.add("SendingMailAction");
        quest.add("VerificationAction");
        quest.add("SearchingCarAction");
        quest.add("ShowingCarAction");
        
        quest.add("LoginAction.action");
        quest.add("RegistrationAction.action");
        quest.add("SendingMailAction.action");
        quest.add("VerificationAction.action");
        quest.add("SearchingCarAction.action");
        quest.add("ShowingCarAction.action");

        customer = new ArrayList<>();
        customer.add("");
        customer.add("header.jsp");
        customer.add("footer.jsp");
        customer.add("search.jsp");
        customer.add("slideShow.jsp");
        customer.add("car.jsp");
        customer.add("cart.jsp");
        customer.add("error.jsp");
        customer.add("history.jsp");
        customer.add("index.jsp");
        customer.add("payment.jsp");
        customer.add("welcome.jsp");

        customer.add("LogoutAction");
        customer.add("SearchingCarAction");
        customer.add("ShowingCarAction");
        customer.add("AddingCartAction");
        customer.add("ApplyingPromotionAction");
        customer.add("DeletingFromCartAction");
        customer.add("DeletingHistoryAction");
        customer.add("FeedbackAction");
        customer.add("PaymentAction");
        customer.add("SearchingHistoryAction");
        customer.add("ShowingCartAction");
        customer.add("UpdatingQuantityCartAction");
        customer.add("UpdatingRentalDateCartAction");
        customer.add("UpdatingReturnDateCartAction");
        
        customer.add("LogoutAction.action");
        customer.add("SearchingCarAction.action");
        customer.add("ShowingCarAction.action");
        customer.add("AddingCartAction.action");
        customer.add("ApplyingPromotionAction.action");
        customer.add("DeletingFromCartAction.action");
        customer.add("DeletingHistoryAction.action");
        customer.add("FeedbackAction.action");
        customer.add("PaymentAction.action");
        customer.add("SearchingHistoryAction.action");
        customer.add("ShowingCartAction.action");
        customer.add("UpdatingQuantityCartAction.action");
        customer.add("UpdatingRentalDateCartAction.action");
        customer.add("UpdatingReturnDateCartAction.action");

        admin = new ArrayList<>();
        admin.add("");
        admin.add("header.jsp");
        admin.add("footer.jsp");
        admin.add("search.jsp");
        admin.add("slideShow.jsp");
        admin.add("car.jsp");
        admin.add("error.jsp");
        admin.add("index.jsp");
        admin.add("welcome.jsp");
        admin.add("createCar.jsp");
        admin.add("promotion.jsp");

        admin.add("LogoutAction");
        admin.add("SearchingCarAction");
        admin.add("ShowingCarAction");
        admin.add("UpdatingImageAction");
        admin.add("CreatingCarAction");
        admin.add("CreatingPromotionAction");
        admin.add("ManagingTransactionAction");
        admin.add("UpdatingRentalStatusAction");
        
        admin.add("CreatingCarAction.action");
        admin.add("CreatingPromotionAction.action");
        admin.add("ManagingTransactionAction.action");
        admin.add("UpdatingRentalStatusAction.action");
        admin.add("UpdatingImageAction.action");
        admin.add("LogoutAction.action");
        admin.add("SearchingCarAction.action");
        admin.add("ShowingCarAction.action");
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("RoleFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("RoleFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        log("Uri: " + uri);
        if ((uri.contains(".js") || uri.contains(".css") || uri.contains(".jpg") || uri.contains(".png") || uri.contains(".gif") || uri.contains(".jpeg")) && !uri.contains(".jsp")) {
            chain.doFilter(request, response);
        } else {
            int index = uri.lastIndexOf("/");
            String resource = uri.substring(index + 1);
            HttpSession session = req.getSession(false);
            log("AuthenFilter: " + resource);
            if ((session == null) || session.getAttribute("ROLE") == null) {
                if (quest.contains(resource)) {
                    chain.doFilter(request, response);
                } else {
                    res.sendRedirect("login.jsp");
                }
            } else {
                String role = ((RoleDTO) session.getAttribute("ROLE")).getRoleName();
                if (role.equals("admin")) {
                    if (admin.contains(resource)) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect("SearchingCarAction.action");
                    }
                } else if (role.equals("customer")) {
                    if (customer.contains(resource)) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect("SearchingCarAction.action");
                    }
                } else {
                    res.sendRedirect("SearchingCarAction.action");
                }
            }

        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthorFillter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthorFillter()");
        }
        StringBuffer sb = new StringBuffer("AuthorFillter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
