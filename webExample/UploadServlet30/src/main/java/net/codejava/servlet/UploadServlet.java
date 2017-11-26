package net.codejava.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//http://localhost:8080/servlet3Upload/jpload.jsp
@WebServlet(urlPatterns = "/UploadServlet", description = "This is my first annotated servlet",
        asyncSupported = true,
        initParams = {
                @WebInitParam(name = "saveDir", value = "D:/FileUpload"),
                @WebInitParam(name = "allowedTypes", value = "jpg,jpeg,gif,png")
        })
@ServletSecurity(
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "GET", rolesAllowed = "admin"),
               /* @HttpMethodConstraint(value = "POST", rolesAllowed = "admin",
                        transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL),*/
        }
)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,    // 2MB
        maxFileSize = 1024 * 1024 * 10,        // 10MB
        maxRequestSize = 1024 * 1024 * 50)    // 50MB
public class UploadServlet extends HttpServlet {

    /**
     * Name of the directory where uploaded files will be saved, relative to
     * the web application directory.
     */
    private static final String SAVE_DIR = "uploadFiles";

    /**
     * handles file upload
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
//		String savePath = appPath + File.separator + SAVE_DIR;
        String savePath = getInitParameter("saveDir");

        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            // refines the fileName in case it is an absolute path
            fileName = new File(fileName).getName();
            part.write(savePath + File.separator + fileName);

            try {
                DigestInputStream shaStream = new DigestInputStream(
                        part.getInputStream(), MessageDigest.getInstance("SHA-1"));
                byte[] shaDigest = shaStream.getMessageDigest().digest();
                System.out.println("shaDigest = " + shaDigest);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("message", "Upload has been done successfully!");
        getServletContext().getRequestDispatcher("/message.jsp").forward(
                request, response);
    }

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}