package com.prasanth.controller;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prasanth.Exception.ParseOrFilterException;
import com.prasanth.facade.CSVFilterFacade;
import com.prasanth.model.Row;

@Controller
public class UploadController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://temp//";

    @Autowired
    private CSVFilterFacade csvFilterFacade;
    
    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws ParseOrFilterException {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            Reader reader = Files.newBufferedReader(path);
            
            List<Row> rows = csvFilterFacade.handleUpload(reader);
            
            Files.delete(path);
            
            redirectAttributes.addFlashAttribute("message", "File has been filtered succesfully");
            redirectAttributes.addFlashAttribute("header", csvFilterFacade.buildHeader(rows));
            redirectAttributes.addFlashAttribute("rows", rows);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}