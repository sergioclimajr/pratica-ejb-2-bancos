package com.gugawag.pdist.servlets;

import com.gugawag.pdist.ejbs.MensagemDAO;
import com.gugawag.pdist.model.Mensagem;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/mensagem.do"})
public class MensagemServlet extends javax.servlet.http.HttpServlet {

    @EJB
    private MensagemDAO mensagemDao;

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        String operacao = request.getParameter("oper");
        PrintWriter resposta = response.getWriter();
        switch (operacao) {
            case "1": {
                long id = Long.parseLong(request.getParameter("id"));
                String mensagemTexto = request.getParameter("mensagem");
                try {
                    Mensagem novaMensagem = new Mensagem(id, mensagemTexto);
                    mensagemDao.inserir(novaMensagem);
                    resposta.println("Mensagem inserida com sucesso!");
                } catch (RuntimeException e) {
                    resposta.println("Erro ao inserir mensagem: " + e.getMessage());
                }
                break;
            }
            case "2": {
                for (Mensagem mensagem : mensagemDao.listar()) {
                    resposta.println("ID: " + mensagem.getId() + " - Mensagem: " + mensagem.getMensagem());
                }
                break;
            }
        }
    }
}

