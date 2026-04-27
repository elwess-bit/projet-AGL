package com.smartplanner.services;

import com.smartplanner.models.ElementPlanning;
import com.smartplanner.models.Planning;
import com.smartplanner.models.Utilisateur;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

/**
 * Service pour générer des QR codes pointant vers les plannings
 */
public class QRCodeService {

    private static final String OUTPUT_DIR = "qrcodes/";
    private static final String HTML_DIR = "plannings_html/";

    public QRCodeService() {
        // Créer les répertoires s'ils n'existent pas
        new File(OUTPUT_DIR).mkdirs();
        new File(HTML_DIR).mkdirs();
    }

    /**
     * Génère un QR code pour un planning
     * @param planning le planning à encoder
     * @param utilisateur l'utilisateur propriétaire
     * @return le chemin du fichier QR code généré
     */
    public String genererQRCodePlanning(Planning planning, Utilisateur utilisateur) {
        try {
            // 1. Générer le fichier HTML du planning
            String htmlFilePath = genererHTMLPlanning(planning, utilisateur);

            // 2. Générer le QR code qui pointe vers ce fichier
            String qrCodePath = genererQRCode(htmlFilePath, planning.getId());

            return qrCodePath;
        } catch (Exception e) {
            System.err.println("Erreur lors de la génération du QR code: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Génère un fichier HTML du planning (responsive pour mobile)
     */
    private String genererHTMLPlanning(Planning planning, Utilisateur utilisateur) throws IOException {
        String htmlFileName = HTML_DIR + planning.getId() + ".html";
        
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"fr\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>📅 Planning - ").append(utilisateur.getNom()).append("</title>\n");
        html.append("    <style>\n");
        html.append(getCSS());
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <header>\n");
        html.append("            <h1>📅 PLANNING INTELLIGENT</h1>\n");
        html.append("            <p class=\"user\">Utilisateur: <strong>").append(utilisateur.getNom()).append("</strong></p>\n");
        html.append("            <p class=\"period\">Période: <strong>").append(planning.getDateDebut())
                .append(" → ").append(planning.getDateFin()).append("</strong></p>\n");
        html.append("        </header>\n");
        html.append("        <main>\n");
        
        // Ajouter les statistiques
        int total = planning.getElements().size();
        int completed = (int) planning.getElements().stream()
                .filter(e -> "TERMINEE".equals(e.getStatut()))
                .count();
        double percentage = total > 0 ? (double) completed / total * 100 : 0.0;
        
        html.append("            <section class=\"stats\">\n");
        html.append("                <div class=\"stat-card\">\n");
        html.append("                    <h3>📝 Total</h3>\n");
        html.append("                    <p class=\"stat-value\">").append(total).append("</p>\n");
        html.append("                </div>\n");
        html.append("                <div class=\"stat-card completed\">\n");
        html.append("                    <h3>✅ Terminées</h3>\n");
        html.append("                    <p class=\"stat-value\">").append(completed).append("</p>\n");
        html.append("                </div>\n");
        html.append("                <div class=\"stat-card\">\n");
        html.append("                    <h3>📈 Progression</h3>\n");
        html.append("                    <p class=\"stat-value\">").append(String.format("%.0f%%", percentage)).append("</p>\n");
        html.append("                </div>\n");
        html.append("            </section>\n");
        
        // Ajouter les tâches dans une table
        html.append("            <section class=\"tasks\">\n");
        html.append("                <h2>📋 Tâches du planning</h2>\n");
        html.append("                <table>\n");
        html.append("                    <thead>\n");
        html.append("                        <tr>\n");
        html.append("                            <th>Date</th>\n");
        html.append("                            <th>Heure</th>\n");
        html.append("                            <th>Tâche</th>\n");
        html.append("                            <th>Statut</th>\n");
        html.append("                        </tr>\n");
        html.append("                    </thead>\n");
        html.append("                    <tbody>\n");
        
        planning.getElements().stream()
                .sorted((e1, e2) -> e1.getHeureDebut().compareTo(e2.getHeureDebut()))
                .forEach(element -> {
                    String date = element.getHeureDebut().toLocalDate().toString();
                    String time = element.getHeureDebut().toLocalTime().toString();
                    String task = element.getDescription();
                    String status = element.getStatut();
                    String statusClass = "TERMINEE".equals(status) ? "completed" : 
                                       "EN_COURS".equals(status) ? "ongoing" : "pending";
                    
                    html.append("                        <tr class=\"").append(statusClass).append("\">\n");
                    html.append("                            <td>").append(date).append("</td>\n");
                    html.append("                            <td>").append(time).append("</td>\n");
                    html.append("                            <td>").append(task).append("</td>\n");
                    html.append("                            <td><span class=\"badge \">").append(status).append("</span></td>\n");
                    html.append("                        </tr>\n");
                });
        
        html.append("                    </tbody>\n");
        html.append("                </table>\n");
        html.append("            </section>\n");
        
        html.append("        </main>\n");
        html.append("        <footer>\n");
        html.append("            <p>Generated by Smart Daily Planner 📱</p>\n");
        html.append("            <p><small>").append(java.time.LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("</small></p>\n");
        html.append("        </footer>\n");
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>\n");
        
        // Écrire le fichier HTML
        Files.write(Paths.get(htmlFileName), html.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println("✅ Fichier HTML généré: " + htmlFileName);
        
        return htmlFileName;
    }

    /**
     * Retourne le CSS pour l'HTML du planning
     */
    private String getCSS() {
        return "* {\n" +
                "    margin: 0;\n" +
                "    padding: 0;\n" +
                "    box-sizing: border-box;\n" +
                "}\n" +
                "\n" +
                "body {\n" +
                "    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
                "    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "    min-height: 100vh;\n" +
                "    padding: 10px;\n" +
                "    color: #333;\n" +
                "}\n" +
                "\n" +
                ".container {\n" +
                "    max-width: 800px;\n" +
                "    margin: 0 auto;\n" +
                "    background: white;\n" +
                "    border-radius: 15px;\n" +
                "    box-shadow: 0 20px 60px rgba(0,0,0,0.3);\n" +
                "    overflow: hidden;\n" +
                "}\n" +
                "\n" +
                "header {\n" +
                "    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "    color: white;\n" +
                "    padding: 30px;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "\n" +
                "header h1 {\n" +
                "    font-size: 28px;\n" +
                "    margin-bottom: 10px;\n" +
                "}\n" +
                "\n" +
                ".user, .period {\n" +
                "    font-size: 14px;\n" +
                "    opacity: 0.9;\n" +
                "    margin: 5px 0;\n" +
                "}\n" +
                "\n" +
                "main {\n" +
                "    padding: 30px;\n" +
                "}\n" +
                "\n" +
                ".stats {\n" +
                "    display: grid;\n" +
                "    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));\n" +
                "    gap: 20px;\n" +
                "    margin-bottom: 40px;\n" +
                "}\n" +
                "\n" +
                ".stat-card {\n" +
                "    background: #f5f5f5;\n" +
                "    border-radius: 10px;\n" +
                "    padding: 20px;\n" +
                "    text-align: center;\n" +
                "    border-left: 5px solid #667eea;\n" +
                "}\n" +
                "\n" +
                ".stat-card.completed {\n" +
                "    border-left-color: #28a745;\n" +
                "    background: #f0f9f4;\n" +
                "}\n" +
                "\n" +
                ".stat-card h3 {\n" +
                "    font-size: 14px;\n" +
                "    color: #666;\n" +
                "    margin-bottom: 10px;\n" +
                "}\n" +
                "\n" +
                ".stat-value {\n" +
                "    font-size: 32px;\n" +
                "    font-weight: bold;\n" +
                "    color: #667eea;\n" +
                "}\n" +
                "\n" +
                ".tasks h2 {\n" +
                "    margin-bottom: 20px;\n" +
                "    color: #333;\n" +
                "}\n" +
                "\n" +
                "table {\n" +
                "    width: 100%;\n" +
                "    border-collapse: collapse;\n" +
                "    margin-bottom: 20px;\n" +
                "}\n" +
                "\n" +
                "table thead {\n" +
                "    background: #667eea;\n" +
                "    color: white;\n" +
                "}\n" +
                "\n" +
                "table th {\n" +
                "    padding: 15px;\n" +
                "    text-align: left;\n" +
                "    font-weight: 600;\n" +
                "}\n" +
                "\n" +
                "table td {\n" +
                "    padding: 12px 15px;\n" +
                "    border-bottom: 1px solid #eee;\n" +
                "}\n" +
                "\n" +
                "table tr:hover {\n" +
                "    background: #f9f9f9;\n" +
                "}\n" +
                "\n" +
                "table tr.completed {\n" +
                "    background: #f0f9f4;\n" +
                "}\n" +
                "\n" +
                ".badge {\n" +
                "    display: inline-block;\n" +
                "    padding: 5px 12px;\n" +
                "    border-radius: 20px;\n" +
                "    font-size: 12px;\n" +
                "    font-weight: 600;\n" +
                "    background: #ffc107;\n" +
                "    color: #333;\n" +
                "}\n" +
                "\n" +
                "table tr.completed .badge {\n" +
                "    background: #28a745;\n" +
                "    color: white;\n" +
                "}\n" +
                "\n" +
                "table tr.ongoing .badge {\n" +
                "    background: #17a2b8;\n" +
                "    color: white;\n" +
                "}\n" +
                "\n" +
                "footer {\n" +
                "    background: #f5f5f5;\n" +
                "    padding: 20px;\n" +
                "    text-align: center;\n" +
                "    color: #999;\n" +
                "    font-size: 12px;\n" +
                "    border-top: 1px solid #eee;\n" +
                "}\n" +
                "\n" +
                "@media (max-width: 600px) {\n" +
                "    header {\n" +
                "        padding: 20px;\n" +
                "    }\n" +
                "    \n" +
                "    header h1 {\n" +
                "        font-size: 20px;\n" +
                "    }\n" +
                "    \n" +
                "    main {\n" +
                "        padding: 15px;\n" +
                "    }\n" +
                "    \n" +
                "    table th, table td {\n" +
                "        padding: 8px;\n" +
                "        font-size: 12px;\n" +
                "    }\n" +
                "    \n" +
                "    .stat-card {\n" +
                "        padding: 15px;\n" +
                "    }\n" +
                "}";
    }

    /**
     * Génère un QR code en utilisant l'API gratuite qr-server.com
     */
    private String genererQRCode(String cheminFichier, String planningId) throws Exception {
        try {
            // Chemin complet (basé sur le répertoire courant)
            String cheminComplet = new File(cheminFichier).getAbsolutePath();
            String urlFichier = "file:///" + cheminComplet.replace("\\", "/");
            
            // Encoder l'URL pour le QR code
            String urlEncodee = URLEncoder.encode(urlFichier, StandardCharsets.UTF_8.toString());
            
            // URL de l'API gratuite pour générer un QR code
            String urlQRAPI = "https://api.qrserver.com/v1/create-qr-code/" +
                            "?size=300x300" +
                            "&data=" + urlEncodee;
            
            // Télécharger le QR code généré
            String nomFichierQR = OUTPUT_DIR + "QR_" + planningId + ".png";
            telechargerImage(urlQRAPI, nomFichierQR);
            
            System.out.println("✅ QR Code généré: " + nomFichierQR);
            System.out.println("📱 Pointant vers: " + cheminFichier);
            
            return nomFichierQR;
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la génération du QR code: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Télécharge une image depuis une URL et la sauvegarde localement
     */
    private void telechargerImage(String urlImage, String cheminSauvegarde) throws Exception {
        java.net.URL url = new java.net.URL(urlImage);
        java.io.InputStream in = url.openStream();
        Files.copy(in, Paths.get(cheminSauvegarde), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        in.close();
    }

    /**
     * Ouvre le QR code dans le navigateur par défaut
     */
    public void afficherQRCode(String cheminQRCode) {
        try {
            File qrFile = new File(cheminQRCode);
            if (!qrFile.exists()) {
                System.err.println("❌ Fichier QR code non trouvé: " + cheminQRCode);
                return;
            }
            
            // Ouvrir le fichier avec l'application par défaut
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Runtime.getRuntime().exec("cmd /c start " + qrFile.getAbsolutePath());
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                Runtime.getRuntime().exec("open " + qrFile.getAbsolutePath());
            } else {
                Runtime.getRuntime().exec("xdg-open " + qrFile.getAbsolutePath());
            }
            
            System.out.println("🖼️ QR Code affiché!");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'affichage du QR code: " + e.getMessage());
        }
    }

    /**
     * Affiche les instructions pour scanner le QR code
     */
    public void afficherInstructions(String cheminQRCode) {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("📱 QR CODE GÉNÉRÉ AVEC SUCCÈS!");
        System.out.println("═".repeat(60));
        System.out.println("\n✅ Votre QR code est prêt!");
        System.out.println("📍 Chemin: " + cheminQRCode);
        System.out.println("\n📲 COMMENT UTILISER:");
        System.out.println("   1. Ouvrez votre téléphone");
        System.out.println("   2. Ouvrez l'application d'appareil photo");
        System.out.println("   3. Pointez vers le QR code affiché à l'écran");
        System.out.println("   4. Cliquez sur le lien qui apparaît");
        System.out.println("   5. Votre planning s'affiche en HTML responsive!");
        System.out.println("\n💡 AVANTAGES:");
        System.out.println("   ✓ Accessible de n'importe où");
        System.out.println("   ✓ Interface mobile-friendly");
        System.out.println("   ✓ Pas besoin d'application supplémentaire");
        System.out.println("   ✓ Partage facile avec les autres");
        System.out.println("\n" + "═".repeat(60) + "\n");
    }
}