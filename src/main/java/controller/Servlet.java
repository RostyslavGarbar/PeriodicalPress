package controller;

import controller.command.Command;
import controller.command.LogInCommand;
import controller.command.RegisterCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {
    private final Map<String, Command> commands;

    public Servlet() {
        commands = new HashMap<>();
    }

    @Override
    public void init() {
        commands.put("login", new LogInCommand());
        commands.put("register", new RegisterCommand());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI().replaceAll(".*/app/" , "");

        Command command = commands.getOrDefault(path , (r) -> "/index.jsp");
        String page = command.execute(request);
        if (page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/api"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
