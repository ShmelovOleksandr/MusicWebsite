package be.kdg.programming5.musicwebsite.controllers;

import be.kdg.programming5.musicwebsite.util.serializers.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class DownloadController {
    private final String JSON_ATTRIBUTE_NAME = "jsonData";
    protected String fileName = "data";

    protected void addJsonSessionAttribute(HttpSession session,
                                           List<?> objects){
        String objectsJsonString = convertToJson(objects);
        session.setAttribute(JSON_ATTRIBUTE_NAME, objectsJsonString);
    }

    protected String convertToJson(List<?> objectList){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        return gson.toJson(objectList);
    }

    @GetMapping("/download")
    protected void download(HttpSession session,
                            HttpServletResponse response) throws IOException {
        String objectsJson = (String) session.getAttribute(JSON_ATTRIBUTE_NAME);

        if (objectsJson != null) {
            response.setContentType("application/json");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".json");

            response.getWriter().write(objectsJson);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}