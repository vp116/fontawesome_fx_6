// IconLoader.java (avec EnumMap)
package com.cardosama.fontawesome_fx_6;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestionnaire de chargement des icônes FontAwesome depuis des fichiers JSON.
 * <p>
 * Cette classe charge et met en cache les associations entre les noms d'icônes
 * et leurs codes Unicode correspondants pour les différents types de polices
 * FontAwesome (Solid, Regular, Brand, etc.)
 * <p>
 * Auteur : Cardo Sama
 * Version : 1.1
 */
public class IconLoader {
    private static final Logger LOGGER = Logger.getLogger(IconLoader.class.getName());
    private static final Map<FontAwesomeType, Map<String, String>> ICON_MAP = new EnumMap<>(FontAwesomeType.class);

    // Liste des fichiers d'icônes associés à chaque type
    private static final Map<FontAwesomeType, String> TYPE_FILE_MAP = new EnumMap<>(FontAwesomeType.class);

    static {
        // Initialiser TYPE_FILE_MAP
        TYPE_FILE_MAP.put(FontAwesomeType.SOLID, "solid-icons.json");
        TYPE_FILE_MAP.put(FontAwesomeType.REGULAR, "regular-icons.json");
        TYPE_FILE_MAP.put(FontAwesomeType.BRAND, "brands-icons.json");
        TYPE_FILE_MAP.put(FontAwesomeType.DUOTONE, "duotone-icons.json");
        TYPE_FILE_MAP.put(FontAwesomeType.LIGHT, "light-icons.json");
        TYPE_FILE_MAP.put(FontAwesomeType.THIN, "thin-icons.json");

        // Charger les icônes pour chaque type
        TYPE_FILE_MAP.forEach((type, filename) ->
                ICON_MAP.put(type, loadIcons(filename))
        );
    }

    private IconLoader() {
    }

    /**
     * Charge les icônes depuis un fichier JSON spécifié.
     *
     * @param filename Nom du fichier JSON contenant les définitions d'icônes
     * @return Map associant les noms d'icônes à leur représentation Unicode
     */
    private static Map<String, String> loadIcons(String filename) {
        // Chemin relatif depuis la racine des ressources
        String resourcePath = "icons/" + filename;
        Map<String, String> icons = new HashMap<>();

        try (InputStream is = IconLoader.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                LOGGER.warning("Fichier non trouvé dans le classpath: " + resourcePath);
                return Collections.emptyMap();
            }

            try (JsonReader reader = Json.createReader(is)) {
                JsonObject json = reader.readObject();

                json.forEach((key, value) -> {
                    String hexValue = value.toString().replace("\"", "");
                    // Padding pour les valeurs hexa courtes (ex: "e00" -> "0e00")
                    hexValue = String.format("%4s", hexValue).replace(' ', '0');

                    try {
                        int codePoint = Integer.parseInt(hexValue, 16);
                        icons.put(key, String.valueOf(Character.toChars(codePoint)));
                    } catch (NumberFormatException e) {
                        LOGGER.warning("Valeur hexadécimale invalide pour '" + key + "': " + hexValue);
                    }
                });
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur d'E/S lors du traitement du fichier " + filename, e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du traitement du fichier " + filename, e);
        }

        LOGGER.info("Chargé " + icons.size() + " icônes depuis " + filename);
        return Collections.unmodifiableMap(icons);
    }

    /**
     * Récupère la représentation Unicode d'une icône par son nom et son type.
     *
     * @param iconName Le nom de l'icône (ex: "user", "home", etc.)
     * @param type     Le type de police FontAwesome (SOLID, REGULAR, etc.)
     * @return La chaîne Unicode représentant l'icône, ou null si non trouvée
     */
    public static String getUnicode(String iconName, FontAwesomeType type) {
        if (iconName == null || type == null) {
            return null;
        }
        return ICON_MAP.getOrDefault(type, Collections.emptyMap()).get(iconName);
    }

    /**
     * Vérifie si une icône existe pour un type donné.
     *
     * @param iconName Le nom de l'icône à vérifier
     * @param type     Le type de police FontAwesome
     * @return true si l'icône existe, false sinon
     */
    public static boolean hasIcon(String iconName, FontAwesomeType type) {
        if (iconName == null || type == null) {
            return false;
        }
        return ICON_MAP.getOrDefault(type, Collections.emptyMap()).containsKey(iconName);
    }
}
