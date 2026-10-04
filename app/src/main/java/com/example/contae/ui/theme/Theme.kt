package com.example.contae.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ======================================================
// CORES DO MODO ESCURO
// ======================================================

private val ColorDarkBackground = Color(0xFF101410)
private val ColorDarkSurface = Color(0xFF181C18)
private val ColorDarkSurfaceVariant = Color(0xFF3F463F)

// ======================================================
// TEMA CLARO - CONTAÊ
// ======================================================

private val ContaEColorScheme = lightColorScheme(

    // Principal
    primary = VerdePrincipal,
    onPrimary = Branco,

    primaryContainer = VerdeClaro,
    onPrimaryContainer = VerdeEscuro,

    // Secundária
    secondary = VerdeEscuro,
    onSecondary = Branco,

    secondaryContainer = VerdeFundo,
    onSecondaryContainer = VerdeEscuro,

    // Terciária
    tertiary = VerdeOrcamento,
    onTertiary = Branco,

    tertiaryContainer = VerdeOrcamentoClaro,
    onTertiaryContainer = VerdeEscuro,

    // Fundo
    background = CinzaFundo,
    onBackground = Preto,

    // Superfície
    surface = Branco,
    onSurface = Preto,

    surfaceVariant = VerdeMuitoClaro,
    onSurfaceVariant = CinzaEscuro,

    // Bordas
    outline = CinzaClaro,
    outlineVariant = CinzaClaro,

    // Erros
    error = Vermelho,
    onError = Branco,

    errorContainer = VermelhoClaro,
    onErrorContainer = Vermelho
)

// ======================================================
// TEMA ESCURO - CONTAÊ
// ======================================================

private val ContaEDarkColorScheme = darkColorScheme(

    // Principal
    primary = VerdeClaro,
    onPrimary = VerdeEscuro,

    primaryContainer = VerdeEscuro,
    onPrimaryContainer = VerdeClaro,

    // Secundária
    secondary = VerdeClaro,
    onSecondary = VerdeEscuro,

    secondaryContainer = VerdeEscuro,
    onSecondaryContainer = VerdeClaro,

    // Terciária
    tertiary = VerdeOrcamento,
    onTertiary = Branco,

    tertiaryContainer = VerdeEscuro,
    onTertiaryContainer = VerdeClaro,

    // Fundo
    background = ColorDarkBackground,
    onBackground = Branco,

    // Superfície
    surface = ColorDarkSurface,
    onSurface = Branco,

    surfaceVariant = ColorDarkSurfaceVariant,
    onSurfaceVariant = CinzaClaro,

    // Bordas
    outline = Cinza,

    // Erros
    error = Color(0xFFFF6B6B),
    onError = Branco,

    errorContainer = Color(0xFF5C2020),
    onErrorContainer = Color(0xFFFFDAD6)
)

// ======================================================
// TEMA PRINCIPAL
// ======================================================

@Composable
fun ContaeTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) {
        ContaEDarkColorScheme
    } else {
        ContaEColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}