# FontAwesome FX 6 - JavaFX Component

A JavaFX component for easily displaying FontAwesome icons (version 6) in your JavaFX applications with dynamic customization.

## Features

- Supports all FontAwesome 6 icons (Solid, Regular, Brands)
- Dynamic properties for icon name, type, size, and color
- FXML compatible
- Automatic font loading
- Simple API for quick integration

## Installation

1. Add the JAR file to your project's classpath
2. Ensure the FontAwesome fonts are included in your resources

## Usage

### Basic Initialization

First, initialize FontAwesome in your application startup:

```java
FontAwesome.initialize();
```

### Creating an Icon

```java
// Create an icon with default values (solid star, 16px, black)
FontAwesomeIconView icon = new FontAwesomeIconView();

// Create with specific icon
FontAwesomeIconView userIcon = new FontAwesomeIconView("user");

// Create with all parameters
FontAwesomeIconView customIcon = new FontAwesomeIconView(
    "circle-user", 
    FontAwesomeType.SOLID, 
    24, 
    Color.BLUE
);
```

### Property Binding

All properties are bindable:

```java
// Change properties dynamically
icon.setIconName("home");
icon.setType(FontAwesomeType.REGULAR);
icon.setSize(32);
icon.setColor(Color.RED);

// Or bind to other properties
icon.sizeProperty().bind(slider.valueProperty());
```

### Icon Naming Convention

Icon names follow these rules:
- Original FontAwesome names with hyphens become camelCase in code
  - `circle-user` → `circleUser`
  - `arrow-right` → `arrowRight`
  - `file-invoice-dollar` → `fileInvoiceDollar`

### Available Icon Types

```java
FontAwesomeType.SOLID    // Solid style (default)
FontAwesomeType.REGULAR  // Regular style
FontAwesomeType.BRANDS   // Brand icons
```

### FXML Usage

```xml
<FontAwesomeIconView 
    iconName="circleUser" 
    type="SOLID" 
    size="24" 
    color="#3498db"/>
```

## Example Icons

Here are some common icons and their Java names:

| Original Name       | Java Name          | Type    |
|---------------------|--------------------|---------|
| user                | user               | SOLID   |
| circle-user         | circleUser         | REGULAR |
| arrow-right         | arrowRight         | SOLID   |
| house               | house              | SOLID   |
| envelope            | envelope           | REGULAR |
| github              | github             | BRANDS  |
| file-invoice-dollar | fileInvoiceDollar  | SOLID   |

## Requirements

- Java 8 or higher
- JavaFX 8 or higher

## License

This project is licensed under the [MIT License](LICENSE).

## Author

Cardo Sama - Version 1.1
