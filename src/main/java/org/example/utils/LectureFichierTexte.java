package org.example.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LectureFichierTexte {
    public String lire(String url) {
        String contenu = null;
        try {
            Path cheminFichier = Path.of(url);
            contenu = Files.readString(cheminFichier);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return contenu;
    }
}
