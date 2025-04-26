// IconLoader.java
package com.cardosama.fontawesome_fx_6;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Composant JavaFX pour afficher une icône FontAwesome avec personnalisation dynamique.
 * <p>
 * Ce composant permet d'afficher facilement des icônes FontAwesome dans une application
 * JavaFX avec la possibilité de personnaliser le type d'icône, la taille et la couleur.
 * <p>
 * Exemple d'utilisation :
 * <pre>
 * // S'assurer que FontAwesome est initialisé
 * FontAwesome.initialize();
 *
 * // Créer une icône
 * FontAwesomeIconView icon = new FontAwesomeIconView();
 * icon.setIconName("user");
 * icon.setType(FontAwesomeType.SOLID);
 * icon.setSize(24);
 * icon.setColor(Color.BLUE);
 *
 * // Ajouter à un conteneur
 * myPane.getChildren().add(icon);
 * </pre>
 * <p>
 * Auteur : Cardo Sama
 * Version : 1.1
 */
public class FontAwesomeIconView extends Label {
    private static final Logger LOGGER = Logger.getLogger(FontAwesomeIconView.class.getName());

    // Propriétés
    private final StringProperty iconName = new SimpleStringProperty("star");
    private final ObjectProperty<FontAwesomeType> type = new SimpleObjectProperty<>(FontAwesomeType.SOLID);
    private final DoubleProperty size = new SimpleDoubleProperty(20);
    private final ObjectProperty<Paint> color = new SimpleObjectProperty<>(Color.BLACK);

    /**
     * Constructeur par défaut.
     * Initialise le composant et charge le fichier FXML associé.
     */
    public FontAwesomeIconView() {
        getStyleClass().add("font-awesome-icon");

        // Chargement du FXML
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fontawesomeIconView.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement du FXML pour FontAwesomeIconView", e);
        }

        // S'assurer que FontAwesome est initialisé
        if (!FontAwesome.isInitialized()) {
            FontAwesome.initialize();
        }

        // Écouteurs pour les changements de propriétés
        iconName.addListener((obs, oldVal, newVal) -> updateIcon());
        type.addListener((obs, oldVal, newVal) -> updateIcon());
        size.addListener((obs, oldVal, newVal) -> updateIcon());
        color.addListener((obs, oldVal, newVal) -> updateColor());
    }

    /**
     * Constructeur avec nom d'icône.
     *
     * @param iconName Le nom de l'icône à afficher
     */
    public FontAwesomeIconView(String iconName) {
        this();
        setIconName(iconName);
    }

    /**
     * Constructeur avec nom d'icône et type.
     *
     * @param iconName Le nom de l'icône à afficher
     * @param type     Le type de l'icône (SOLID, REGULAR, etc.)
     */
    public FontAwesomeIconView(String iconName, FontAwesomeType type) {
        this(iconName);
        setType(type);
    }

    /**
     * Constructeur complet.
     *
     * @param iconName Le nom de l'icône à afficher
     * @param type     Le type de l'icône (SOLID, REGULAR, etc.)
     * @param size     La taille de l'icône en pixels
     * @param color    La couleur de l'icône
     */
    public FontAwesomeIconView(String iconName, FontAwesomeType type, double size, Paint color) {
        this(iconName, type);
        setSize(size);
        setColor(color);
    }

    /**
     * Met à jour la couleur de l'icône.
     */
    private void updateColor() {
        if (color.get() != null) {
            setTextFill(color.get());
        }
    }

    /**
     * Met à jour l'icône affichée en fonction des propriétés.
     */
    private void updateIcon() {
        if (iconName.get() == null || iconName.get().isEmpty() || type.get() == null) {
            setText("");
            return;
        }

        String unicode = IconLoader.getUnicode(iconName.get(), type.get());
        if (unicode != null) {
            setText(unicode);

            // Obtenir la police avec la taille spécifiée
            String fontPath = FontAwesome.getFontPathForType(type.get());
            var iconFont = FontAwesome.getFont(fontPath, size.get());

            if (iconFont != null) {
                setFont(iconFont);
            } else {
                // Fallback : charger directement la police
                setFont(Font.loadFont(FontAwesome.class.getResourceAsStream(fontPath), size.get()));
            }
        } else {
            LOGGER.warning("Icône non trouvée : " + iconName.get() + " (type: " + type.get() + ")");
            setText("");
        }
    }

    // Getters et setters pour les propriétés

    /**
     * Obtient le nom de l'icône.
     *
     * @return Le nom de l'icône
     */
    public String getIconName() {
        return iconName.get();
    }

    /**
     * Définit le nom de l'icône.
     *
     * @param value Le nom de l'icône (ex: "user", "home", etc.)
     */
    public void setIconName(String value) {
        iconName.set(value);
    }

    /**
     * Obtient la propriété du nom de l'icône.
     *
     * @return La propriété StringProperty du nom de l'icône
     */
    public StringProperty iconNameProperty() {
        return iconName;
    }

    /**
     * Obtient le type de l'icône.
     *
     * @return Le type de l'icône
     */
    public FontAwesomeType getType() {
        return type.get();
    }

    /**
     * Définit le type de l'icône.
     *
     * @param value Le type de l'icône (ex: SOLID, REGULAR, etc.)
     */
    public void setType(FontAwesomeType value) {
        type.set(value);
    }

    /**
     * Obtient la propriété du type de l'icône.
     *
     * @return La propriété ObjectProperty du type de l'icône
     */
    public ObjectProperty<FontAwesomeType> typeProperty() {
        return type;
    }

    /**
     * Obtient la taille de l'icône.
     *
     * @return La taille de l'icône en pixels
     */
    public double getSize() {
        return size.get();
    }

    /**
     * Définit la taille de l'icône.
     *
     * @param value La taille de l'icône en pixels
     */
    public void setSize(double value) {
        size.set(value);
    }

    /**
     * Obtient la propriété de taille de l'icône.
     *
     * @return La propriété DoubleProperty de la taille de l'icône
     */
    public DoubleProperty sizeProperty() {
        return size;
    }

    /**
     * Obtient la couleur de l'icône.
     *
     * @return La couleur de l'icône
     */
    public Paint getColor() {
        return color.get();
    }

    /**
     * Définit la couleur de l'icône.
     *
     * @param value La couleur de l'icône
     */
    public void setColor(Paint value) {
        color.set(value);
    }

    /**
     * Obtient la propriété de couleur de l'icône.
     *
     * @return La propriété ObjectProperty de la couleur de l'icône
     */
    public ObjectProperty<Paint> colorProperty() {
        return color;
    }

    /**
     * Vérifie si l'icône spécifiée existe pour le type actuel.
     *
     * @param iconName Le nom de l'icône à vérifier
     * @return true si l'icône existe, false sinon
     */
    public boolean iconExists(String iconName) {
        return IconLoader.hasIcon(iconName, type.get());
    }
}