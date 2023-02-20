package com.example.splitter;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class HelloController {
    @FXML
    Text result = new Text();

    @FXML
    protected void unstackPO() throws IOException, InterruptedException {
        unstack("C:/Users/Nosara/Desktop/POs");
        result.setText("Done unstacking POs folder");
    }

    @FXML
    protected void stackPO() throws IOException, InterruptedException {
        stack("C:/Users/Nosara/Desktop/POs");
        result.setText("Done stacking POs folder");
    }

    @FXML
    protected void unstackNonPO() throws IOException, InterruptedException {
        unstack("C:/Users/Nosara/Desktop/Non-POs");
        result.setText("Done unstacking Non-POs folder");
    }

    @FXML
    protected void stackNonPO() throws IOException, InterruptedException {
        stack("C:/Users/Nosara/Desktop/Non-POs");
        result.setText("Done stacking Non-POs folder");
    }

    @FXML
    protected void unstackStatements() throws IOException, InterruptedException {
       unstack("C:/Users/Nosara/Desktop/Statements");
        result.setText("Done unstacking Non-POs folder");
    }

    @FXML
    protected void stackStatements() throws IOException, InterruptedException {
        stack("C:/Users/Nosara/Desktop/Statements");
        result.setText("Done stacking Statements folder");
    }

    private void stack(String folder) throws IOException {
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        pdfMerger.setDestinationFileName(folder +"/merged.pdf");

        final var files =  getFiles(folder);
        final var filesToDelete = getFiles(folder);

        files.forEach( f -> {
            try {
                pdfMerger.addSource(f);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        pdfMerger.mergeDocuments(null);

//        Files.delete

        filesToDelete.forEach(f -> {
            if(f.delete()){
                System.out.println("borrado");
            }
        });

    }
    private void unstack(String folder) throws IOException {
            getFiles(folder).forEach(f -> {
                    System.out.println(f.getName());
                    PDDocument document = null;
                    try {
                        document = PDDocument.load(f);
                        Splitter splitting = new Splitter();
                        List<PDDocument> Page = null;
                        Page = splitting.split(document);
                        Iterator<PDDocument> iteration
                                = Page.listIterator();

                        int j = 1;
                        while (iteration.hasNext()) {
                            PDDocument pd = iteration.next();
                            pd.save(folder +"/"+f.getName()+"("+ j++ +")"+".pdf");
                        }
                        System.out.println("Splitted Pdf Successfully.");
                        document.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (f.delete()){
                        System.out.println("borrado " + f.getName());
                    }
                }
                );
    }

    private Stream<File> getFiles(String folder) {
        File[] files = new File(folder).listFiles((dir, name) -> name.endsWith(".pdf"));
        assert files != null;
        return Arrays.stream(files);
    }
}