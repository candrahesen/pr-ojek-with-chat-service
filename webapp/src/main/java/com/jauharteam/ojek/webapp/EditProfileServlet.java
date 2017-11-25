package com.jauharteam.ojek.webapp;

import com.ojek.common.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dery on 11/8/17.
 */
public class EditProfileServlet extends WebappServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!checkIsLoggedIn(req)) {
            resp.sendRedirect(config.getBaseUrl() + "login");
            return;
        }

        try {
            String accessToken = getAccessToken(req).getAccessToken();
            req.setAttribute("accessToken", accessToken);
            req.setAttribute("userService", getOjekUserService());
            req.setAttribute("config", config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/editprofile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File filesDir = new File(servletContext.getRealPath("/data"));
        File photosDir = new File(servletContext.getRealPath("/photos"));
        if (!filesDir.exists())
            filesDir.mkdir();
        if (!photosDir.exists())
            photosDir.mkdir();
        fileFactory.setRepository(filesDir);
        ServletFileUpload uploader = new ServletFileUpload(fileFactory);
        Map<String,String> parameters = new HashMap<>();
        Map<String,FileItem> fileItemMap = new HashMap<>();
        try {
            List<FileItem> fileItemList = uploader.parseRequest(req);
            for (FileItem item : fileItemList) {
                fileItemMap.put(item.getFieldName(), item);
                if (item.getContentType() == null || item.getContentType().equals("field-data"))
                    parameters.put(item.getFieldName(), item.getString());
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            return;
        }

        String accessToken = getAccessToken(req).getAccessToken();
        String name = parameters.get("name");
        String phone = parameters.get("phone");
        String driverStr = parameters.get("driver");
        Boolean isDriver = false;
        if (driverStr != null && driverStr.length() > 0)
            isDriver = driverStr.equals("on");

        if(!checkIsLoggedIn(req)) return;

        User user = getOjekUserService().getUser(accessToken);
        user.setName(name);
        user.setPhone(phone);
        user.setDriver(isDriver);

        FileItem photo = fileItemMap.get("uploaded_file");
        if (photo != null && photo.getSize() > 0)
            try {
                photo.write(new File(photosDir + File.separator + user.getUsername()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        user.setProfpicUrl(config.getBaseUrl() + "photos/" + user.getUsername());

        if(!getOjekUserService().editUser(accessToken, user)){
            req.setAttribute("errorMessage", "Internal server error.");
        }
        resp.sendRedirect(config.getBaseUrl() + "profile");
    }
}
