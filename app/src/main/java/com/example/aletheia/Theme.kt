package com.example.aletheia
import android.app.Activity
import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(context: Context, username: String, systemTheme: Boolean) : ViewModel() {

    private val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)

    // Stocke l'état du thème dans un StateFlow pour une mise à jour fluide
    private val _isDarkTheme = MutableStateFlow(prefs.getBoolean("isDarkTheme", systemTheme))
    val isDarkTheme = _isDarkTheme.asStateFlow() // Lecture seule

    private val _reelSysBar = MutableStateFlow(false)
    val reelSysBar: StateFlow<Boolean> = _reelSysBar


    fun toggleReelSysBar(value: Boolean) {
        _reelSysBar.value = value
    }

    fun toggleTheme(theme: Boolean, dark : Boolean, light : Boolean, system : Boolean) {
        _isDarkTheme.value = theme

        // Sauvegarde du thème dans SharedPreferences
        viewModelScope.launch {
            prefs.edit().putBoolean("dark", dark).apply()
            prefs.edit().putBoolean("light", light).apply()
            prefs.edit().putBoolean("system", system).apply()


            prefs.edit().putBoolean("isDarkTheme", theme).apply()
        }
    }
}


class ThemeViewModelFactory(private val context: Context, private val username: String, private val systemTheme : Boolean) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(context, username, systemTheme) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



// Couleurs pour le thème clair
val LightPrimary = Color(0xFF0AD6F1)
val LightSecondary = Color(0xFF000000 ) // Couleur secondaire pour le thème clair
//val LightBackground = Color(0xFFF3F2EE) // Couleur de fond pour le thème clair
val LightBackground = Color(0xFFFFFFFF) // Couleur de surface pour le thème clair
//val LightSurface = Color(0xFFF1FDFD)
//val LightSurface = Color(0xFFEDFDFD)
val LightSurface = Color(0xFFE6F1F1)



// Couleurs pour le thème sombre
val DarkPrimary = Color(0xFF0AD6F1)
val DarkSecondary = Color(0xFFFFFFFF) // Couleur secondaire pour le thème sombre
//val DarkBackground = Color(0xFF111515)
//val DarkBackground = Color(0xFF000B0C) // Couleur de fond pour le thème sombre
val DarkBackground = Color(0xFF000E10)
//val DarkBackground = Color(0xFF111111) // Couleur de fond pour le thème sombre
//val DarkBackground = Color(0xFF1F1F1F) // Couleur de surface pour le thème sombre
//val DarkSurface = Color(0xFF2C2C2C)
val DarkSurface = Color(0xFF182121)


// Schéma de couleurs pour le thème clair
val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = Color.White, // Couleur du texte sur la couleur primaire
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = Color.Red,
)

// Schéma de couleurs pour le thème sombre
val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color.Red,
)


val CustomFontFamily = FontFamily(
    Font(R.font.sarala_regular, FontWeight.Normal),
    Font(R.font.sarala_bold, FontWeight.Bold),
)



val CustomTypography = Typography(
    bodySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 12.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = CustomFontFamily,
        fontSize = 15.sp
    ),
    titleMedium = TextStyle(
        fontFamily = CustomFontFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontFamily = CustomFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
)


@Composable
fun ChangeStatusBarAndSplashColor(isDarkTheme: Boolean, themeViewModel: ThemeViewModel) {
    val statusBarColor = if (isDarkTheme) Color.Black else Color.White
    val window = (LocalActivity.current as Activity).window
    val context = LocalContext.current
    val reelSystemBar = themeViewModel.reelSysBar.collectAsState().value
    var windowLight by remember { mutableStateOf(isDarkTheme) }

    SideEffect {
        // Changer la couleur de la barre d'état


        // Définir si les icônes de la barre d'état sont claires ou sombres
        if (reelSystemBar) {
            windowLight = false
        } else {
            windowLight = !isDarkTheme
        }



        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = !isDarkTheme //windowLight
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = !isDarkTheme

    }
}



@Composable
fun AletheiaTheme(
    themeViewModel: ThemeViewModel,
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    ChangeStatusBarAndSplashColor(darkTheme, themeViewModel)

    MaterialTheme(
        typography = CustomTypography,
        colorScheme = colorScheme,
        content = content
    )
}
