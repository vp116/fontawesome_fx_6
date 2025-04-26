package com.cardosama.fontawesome_fx_6;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Classe légère pour afficher des glyphes FontAwesome comme icônes dans des boutons natifs.
 * <p>
 * Cette classe étend Text et est optimisée pour une utilisation simple avec des boutons JavaFX.
 * Elle fournit des méthodes pour changer rapidement l'icône, la taille et la couleur.
 * <p>
 * Exemple d'utilisation :
 * <pre>
 * Button btn = new Button("Mon Bouton");
 * FontAwesomeGlyph icon = new FontAwesomeGlyph("user", FontAwesomeType.SOLID);
 * btn.setGraphic(icon);
 * </pre>
 * <p>
 * Auteur : Cardo Sama
 * Version : 1.0
 */
public class FontAwesomeGlyph extends Text {
    private String iconName;
    private FontAwesomeType type;
    private double size;
    private Paint color;

    /**
     * Constructeur par défaut.
     * Crée une icône avec l'étoile SOLID par défaut.
     */
    public FontAwesomeGlyph() {
        this("star", FontAwesomeType.SOLID);
    }

    /**
     * Constructeur avec nom d'icône.
     *
     * @param iconName Le nom de l'icône à afficher
     */
    public FontAwesomeGlyph(String iconName) {
        this(iconName, FontAwesomeType.SOLID);
    }

    /**
     * Constructeur avec nom d'icône et type.
     *
     * @param iconName Le nom de l'icône à afficher
     * @param type     Le type de l'icône (SOLID, REGULAR, etc.)
     */
    public FontAwesomeGlyph(String iconName, FontAwesomeType type) {
        this(iconName, type, 16, Color.BLACK);
    }

    /**
     * Constructeur complet.
     *
     * @param iconName Le nom de l'icône à afficher
     * @param type     Le type de l'icône (SOLID, REGULAR, etc.)
     * @param size     La taille de l'icône en pixels
     * @param color    La couleur de l'icône
     */
    public FontAwesomeGlyph(String iconName, FontAwesomeType type, double size, Paint color) {
        // S'assurer que FontAwesome est initialisé
        if (!FontAwesome.isInitialized()) {
            FontAwesome.initialize();
        }

        this.iconName = iconName;
        this.type = type;
        this.size = size;
        this.color = color;

        updateIcon();
    }

    /**
     * Met à jour l'icône affichée en fonction des propriétés courantes.
     */
    private void updateIcon() {
        if (iconName == null || iconName.isEmpty() || type == null) {
            setText("");
            return;
        }

        String unicode = IconLoader.getUnicode(iconName, type);
        if (unicode != null) {
            setText(unicode);
            setFill(color);

            // Obtenir la police avec la taille spécifiée
            String fontPath = FontAwesome.getFontPathForType(type);
            Font iconFont = FontAwesome.getFont(fontPath, size);

            if (iconFont != null) {
                setFont(iconFont);
            } else {
                // Fallback : charger directement la police
                setFont(Font.loadFont(FontAwesome.class.getResourceAsStream(fontPath), size));
            }
        }
    }

    /**
     * Définit une nouvelle icône à afficher.
     *
     * @param iconName Le nom de la nouvelle icône
     * @param type     Le type de la nouvelle icône
     */
    public void setIcon(String iconName, FontAwesomeType type) {
        this.iconName = iconName;
        this.type = type;
        updateIcon();
    }

    /**
     * Change la taille de l'icône.
     *
     * @param size La nouvelle taille en pixels
     */
    public void setSize(double size) {
        this.size = size;
        updateIcon();
    }

    /**
     * Change la couleur de l'icône.
     *
     * @param color La nouvelle couleur
     */
    public void setColor(Paint color) {
        this.color = color;
        setFill(color);
    }

    /**
     * Obtient le nom de l'icône actuelle.
     *
     * @return Le nom de l'icône
     */
    public String getIconName() {
        return iconName;
    }

    /**
     * Obtient le type de l'icône actuelle.
     *
     * @return Le type de l'icône
     */
    public FontAwesomeType getType() {
        return type;
    }

    /**
     * Obtient la taille actuelle de l'icône.
     *
     * @return La taille en pixels
     */
    public double getSize() {
        return size;
    }

    /**
     * Obtient la couleur actuelle de l'icône.
     *
     * @return La couleur
     */
    public Paint getColor() {
        return color;
    }
}