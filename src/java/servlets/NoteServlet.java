package servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Note;

/**
 *
 * @author hsp28
 */
public class NoteServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            String path = getServletContext().getRealPath("/WEB-INF/note.txt");
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));

            String title = br.readLine();
            String content = br.readLine();

            Note note = new Note(title, content);
            request.setAttribute("note", note);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        String edit = request.getParameter("edit");

        if (edit != null)
        {
            getServletContext().getRequestDispatcher("/WEB-INF/editnote.jsp")
                    .forward(request, response);
        } else
        {
            getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
//        Creating a note object
        Note note2 = null;
        
//        Saving the note to the file
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        if (title.equals("") || content.equals(""))
        {
            request.setAttribute("emptyInput", "Please fill both the inputs");
            getServletContext().getRequestDispatcher("/WEB-INF/editnote.jsp")
            .forward(request, response);
        }
        else 
        {
            try
            {
                String path = getServletContext().getRealPath("/WEB-INF/note.txt");
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));

                pw.write(title);
                pw.write(content);

                note2 = new Note(title, content);
                request.setAttribute("note", note2);

    //            Closing the file after writing into it
                pw.close();
            } catch (Exception e)
            {

            }
            getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp")
                    .forward(request, response);
        }
    }
}
