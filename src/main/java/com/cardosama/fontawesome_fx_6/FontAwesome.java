package com.cardosama.fontawesome_fx_6;

import javafx.scene.text.Font;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestionnaire des polices FontAwesome pour JavaFX.
 * <p>
 * Cette classe gère le chargement des différentes polices FontAwesome
 * et fournit des références aux chemins des fichiers de police.
 * <p>
 * Auteur : Cardo Sama
 * Version : 1.1
 */
public final class FontAwesome {
    private static final Logger LOGGER = Logger.getLogger(FontAwesome.class.getName());

    // Chemins des polices
    public static final String SOLID = "fonts/fa-solid-900.ttf";
    public static final String REGULAR = "fonts/fa-regular-400.ttf";
    public static final String BRANDS = "fonts/fa-brands-400.ttf";
    public static final String DUOTONE = "fonts/fa-duotone-900.ttf";
    public static final String THIN = "fonts/fa-thin-100.ttf";
    public static final String LIGHT = "fonts/fa-light-300.ttf";

    // Cache des polices chargées
    private static final Map<String, Font> FONT_CACHE = new HashMap<>();

    // Mappage entre types et chemins de police
    private static final Map<FontAwesomeType, String> TYPE_PATH_MAP = new EnumMap<>(FontAwesomeType.class);

    static {
        TYPE_PATH_MAP.put(FontAwesomeType.SOLID, SOLID);
        TYPE_PATH_MAP.put(FontAwesomeType.REGULAR, REGULAR);
        TYPE_PATH_MAP.put(FontAwesomeType.BRAND, BRANDS);
        TYPE_PATH_MAP.put(FontAwesomeType.DUOTONE, DUOTONE);
        TYPE_PATH_MAP.put(FontAwesomeType.THIN, THIN);
        TYPE_PATH_MAP.put(FontAwesomeType.LIGHT, LIGHT);
    }

    private static boolean initialized = false;

    /**
     * Constructeur privé pour empêcher l'instanciation.
     * Cette classe ne contient que des méthodes statiques.
     */
    private FontAwesome() {
        // Classe utilitaire, ne doit pas être instanciée
    }

    /**
     * Initialise et charge toutes les polices FontAwesome dans le système.
     * Cette méthode doit être appelée au démarrage de l'application avant
     * d'utiliser les icônes FontAwesome.
     */
    public static void initialize() {
        if (!initialized) {
            LOGGER.info("Initialisation des polices FontAwesome...");

            // Chargement de toutes les polices
            boolean allLoaded = true;
            for (String fontPath : TYPE_PATH_MAP.values()) {
                allLoaded &= loadFont(fontPath);
            }

            initialized = allLoaded;

            if (initialized) {
                LOGGER.info("Toutes les polices FontAwesome ont été chargées avec succès.");
            } else {
                LOGGER.warning("Certaines polices FontAwesome n'ont pas pu être chargées.");
            }
        }
    }

    /**
     * Charge une police spécifique et la met en cache.
     *
     * @param fontPath Chemin vers le fichier de police
     * @return true si la police a été chargée avec succès, false sinon
     */
    private static boolean loadFont(String fontPath) {
        try {
            Font font = Font.loadFont(FontAwesome.class.getResourceAsStream(fontPath), 20);
            if (font != null) {
                FONT_CACHE.put(fontPath, font);
                LOGGER.fine("Police chargée : " + fontPath);
                return true;
            } else {
                LOGGER.warning("Échec du chargement de la police : " + fontPath);
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement de la police " + fontPath, e);
            return false;
        }
    }

    /**
     * Récupère une police chargée du cache.
     *
     * @param fontPath Chemin de la police
     * @param size Taille de la police
     * @return L'instance de Font de la police demandée, ou null si non disponible
     */
    public static Font getFont(String fontPath, double size) {
        if (!initialized) {
            initialize();
        }

        Font baseFont = FONT_CACHE.get(fontPath);
        if (baseFont != null && size != 20) {
            // Si la taille demandée est différente de la taille par défaut (20),
            // créer une nouvelle instance avec la taille demandée
            return Font.font(baseFont.getFamily(), size);
        }
        return baseFont;
    }

    /**
     * Vérifie si les polices ont été initialisées.
     *
     * @return true si les polices ont été chargées, false sinon
     */
    public static boolean isInitialized() {
        return initialized;
    }

    /**
     * Obtient le chemin de la police associé à un type FontAwesome.
     *
     * @param type Le type de police FontAwesome
     * @return Le chemin vers le fichier de police correspondant
     */
    public static String getFontPathForType(FontAwesomeType type) {
        return TYPE_PATH_MAP.getOrDefault(type, SOLID);
    }
}
