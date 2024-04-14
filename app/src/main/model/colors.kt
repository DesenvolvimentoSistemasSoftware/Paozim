class Color(val primary: String, val secondary: String, val tertiary: String)

fun getAnalogPalette(palette: Triple<ColorPalette, ColorPalette, ColorPalette>): ColorPalette {
    return ColorPalette("#DBA627", "#DB7027", "#DBBB71")
}

fun getComplementaryPalette(palette: Triple<ColorPalette, ColorPalette, ColorPalette>): ColorPalette {
    return ColorPalette("#DBA627", "#2B2E33", "#275FDB")
}

fun getRedAndGreenPalette(palette: Triple<ColorPalette, ColorPalette, ColorPalette>): ColorPalette {
    return ColorPalette("#DB4536", "#37DB80", "#4E8667")
}