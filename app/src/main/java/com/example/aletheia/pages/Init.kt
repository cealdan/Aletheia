package com.example.aletheia.pages

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.aletheia.AletheiaTheme
import com.example.aletheia.MainActivity
import com.example.aletheia.R
import com.example.aletheia.RetrofitInstance
import com.example.aletheia.SQL
import com.example.aletheia.ThemeViewModel
import com.example.aletheia.ThemeViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Locale


class Init : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent()
        {
            InitialPage()
        }
    }
}




fun completeSetup(context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    val editor = prefs.edit()

    // Sauvegarder que les champs sont complétés
    editor.putBoolean("is_setup_complete", true)
    editor.apply()
    // Rediriger vers la page principale après l'initialisation
    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    context.startActivity(intent)
    val activity = context as? Activity
    activity?.finish()

}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialPage() {
    var username by remember { mutableStateOf("") }
    var username_login by remember { mutableStateOf("") }
    var password_login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    //var age by remember { mutableStateOf("") }
    var permission by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var alreadyuser by remember { mutableStateOf(false) }
    var usernameexists by remember { mutableStateOf(false) }
    var notauser by remember { mutableStateOf(false) }
    var wrongcredentials by remember { mutableStateOf(false) }
    var networkerror by remember { mutableStateOf(false) }
    var emailerror by remember { mutableStateOf(false) }



    var dateOfBirth by remember { mutableStateOf("") }
    var isDatePickerVisible by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            val formattedDate = "$dayOfMonth/${month + 1}/$year"
            dateOfBirth = formattedDate
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    if (isDatePickerVisible) {
        datePickerDialog.show()
        isDatePickerVisible = false
    }



    val context = LocalContext.current
    val prefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    val coroutineScope = rememberCoroutineScope()

    var essais_connexion by remember { mutableStateOf(0) }

    val bloquefromprefs = prefs.getBoolean("bloque", false)

    var bloque by remember { mutableStateOf(bloquefromprefs) }

    val startTimefromprefs = prefs.getLong("startTime", 0L)

    var startTime by remember { mutableStateOf<Long?>(startTimefromprefs) }
    var elapsedTime by remember { mutableStateOf<Long>(0L) }

    val tmpstartime_frompref = prefs.getLong("tmpstartTime", 0L)

    var tmpstartTime by remember { mutableStateOf<Long?>(tmpstartime_frompref) }
    var tmpelapsedTime by remember { mutableStateOf<Long>(0L) }

    LaunchedEffect(tmpstartTime) {

        if (tmpstartTime != 0L) {
            while (tmpelapsedTime < 3000) {
                tmpelapsedTime = SystemClock.elapsedRealtime() - tmpstartTime!!
                delay(1000) // rafraîchissement toutes les secondes
            }
            if (tmpelapsedTime >= 3000) {
                essais_connexion = 0
                tmpstartTime = 0L
                prefs.edit().putLong("tmpstartTime", 0L).apply()
                tmpelapsedTime = 0
            }
        }
    }

    LaunchedEffect(startTime) {
        if (bloque) {
            while (elapsedTime < 30000) {
                elapsedTime = SystemClock.elapsedRealtime() - startTime!!
                delay(1000) // rafraîchissement toutes les secondes
            }
            bloque = false
            prefs.edit().putBoolean("bloque", bloque).apply()


            essais_connexion = 0
            startTime = 0L
            prefs.edit().putLong("startTime", 0L).apply()

            elapsedTime = 0
        }
    }

    val activity = context as? Activity

    val transition = rememberInfiniteTransition()

    // Animation pour déplacer la flèche de haut en bas
    val offsetX by transition.animateFloat(
        initialValue = -5f, // position de départ (en haut)
        targetValue = 5f, // position finale (en bas)
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing), // Durée de l'animation
            repeatMode = RepeatMode.Reverse // Faire aller et revenir
        )
    )

    val themeViewModel: ThemeViewModel = viewModel(
        factory = ThemeViewModelFactory(context, "", isSystemInDarkTheme())
    )



    // Appliquer le thème
    AletheiaTheme(themeViewModel, darkTheme = true) {
        BackHandler {
        }
        var currentStep by remember { mutableStateOf(1) }

        var passwordVisible by remember { mutableStateOf(false) }
        val pagerState = rememberPagerState(0, 0f, { 2 })
        val focusManager = LocalFocusManager.current

        val pageAlpha = remember { mutableStateListOf(0f, 0f, 0f, 0f) }


        LaunchedEffect(pagerState.currentPage) {
            val previousPage = if (pagerState.currentPage > 0) pagerState.currentPage - 1 else 0
            pageAlpha[previousPage] = 0f

            val nextPage = if (pagerState.currentPage < 3) pagerState.currentPage + 1 else 3
            pageAlpha[nextPage] = 0f



            delay(100)
            pageAlpha[pagerState.currentPage] = 1f
        }


        Box(modifier = Modifier.fillMaxSize()) {




            Image(
                painterResource(id = R.drawable.initbackground),
                contentDescription = "background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))


            HorizontalPager(
                state = pagerState,
                modifier = Modifier

                    .fillMaxSize()

            ) { page ->

                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(durationMillis = 500) // Durée de l'animation (500ms)
                    )
                ) {
                    val alpha by animateFloatAsState(
                        targetValue = pageAlpha[page], // Utiliser l'alpha spécifique à la page
                        animationSpec = tween(durationMillis = 2000)
                    )


                    LazyColumn(
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = {
                                    focusManager.clearFocus() // Enlève le focus des champs
                                })
                            }
                            .fillMaxSize()
                            .imePadding()

                            //.background(MaterialTheme.colorScheme.background)
                            .alpha(alpha)
                            .windowInsetsPadding(WindowInsets.systemBars),



                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {

                            when (page) {

                                1 -> {

                                    IconButton(
                                        onClick = {
                                            CoroutineScope(Dispatchers.Main).launch {
                                                val nextPage = 0
                                                pagerState.scrollToPage(nextPage)

                                            }
                                        },

                                        ) {
                                        Icon(
                                            imageVector = Icons.Filled.KeyboardArrowLeft, // Icône de la flèche
                                            contentDescription = "Flèche ",
                                            modifier = Modifier.size(40.dp)
                                                .offset(x = Dp(offsetX)),
                                            tint = Color.White
                                        )
                                    }
                                    Text(stringResource(id = R.string.se_connecter),
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            color = Color.White
                                        ),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(16.dp))
                                            .clickable{
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    val nextPage = 0
                                                    pagerState.scrollToPage(nextPage)

                                                }
                                            }
                                            .padding(8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = stringResource(id = R.string.bienvenue),
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontSize = 36.sp,
                                            color = Color.White
                                        ),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(bottom = 24.dp))

                                    when (currentStep) {
                                        1 -> {
                                            TextField(
                                                textStyle = MaterialTheme.typography.bodyMedium,
                                                value = username,
                                                onValueChange = { username = it },
                                                label = { Text(stringResource(id = R.string.nom_utilisateur),
                                                    style = MaterialTheme.typography.bodyLarge,
                                                ) },

                                                modifier = Modifier

                                                    .fillMaxWidth().padding(20.dp),
                                                colors = TextFieldDefaults.colors(
                                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                                    focusedContainerColor = MaterialTheme.colorScheme.background,

                                                    )
                                            )
                                        }
                                        2 -> {
                                            TextField(
                                                textStyle = MaterialTheme.typography.bodyMedium,
                                                value = password,
                                                onValueChange = { password = it },
                                                label = { Text(stringResource(id = R.string.mot_de_passe),
                                                    style = MaterialTheme.typography.bodyLarge,
                                                ) },

                                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                                modifier = Modifier.fillMaxWidth().padding(20.dp),
                                                colors = TextFieldDefaults.colors(
                                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                                    focusedContainerColor = MaterialTheme.colorScheme.background,

                                                    )
                                            )
                                        }
                                        3 -> {
                                            TextField(
                                                textStyle = MaterialTheme.typography.bodyMedium,
                                                value = email,
                                                onValueChange = { email = it },
                                                label = { Text(stringResource(id = R.string.adresse_mail),
                                                    style = MaterialTheme.typography.bodyLarge,
                                                ) },

                                                modifier = Modifier.fillMaxWidth().padding(20.dp),
                                                colors = TextFieldDefaults.colors(
                                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                                    focusedContainerColor = MaterialTheme.colorScheme.background,

                                                    )
                                            )
                                        }
                                        4 -> {
                                            TextField(
                                                textStyle = MaterialTheme.typography.bodyMedium,
                                                value = firstname,
                                                onValueChange = { firstname = it },
                                                label = { Text(stringResource(id = R.string.prénom),
                                                    style = MaterialTheme.typography.bodyLarge,
                                                ) },

                                                modifier = Modifier.fillMaxWidth().padding(20.dp),
                                                colors = TextFieldDefaults.colors(
                                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                                    focusedContainerColor = MaterialTheme.colorScheme.background,

                                                    )
                                            )
                                        }
                                        5 -> {
                                            TextField(
                                                textStyle = MaterialTheme.typography.bodyMedium,
                                                value = lastname,
                                                onValueChange = { lastname = it },
                                                label = { Text(stringResource(id = R.string.nom_de_famille),
                                                    style = MaterialTheme.typography.bodyLarge,
                                                ) },

                                                modifier = Modifier.fillMaxWidth().padding(20.dp),
                                                colors = TextFieldDefaults.colors(
                                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                                    focusedContainerColor = MaterialTheme.colorScheme.background,

                                                    )
                                            )
                                        }
                                        6 -> {

                                            TextField(
                                                textStyle = MaterialTheme.typography.bodyMedium,
                                                value = dateOfBirth,
                                                onValueChange = { dateOfBirth = it },
                                                label = { Text(stringResource(id = R.string.date_de_naissance),
                                                    style = MaterialTheme.typography.bodyLarge)
                                                },
                                                modifier = Modifier.fillMaxWidth().padding(20.dp),
                                                readOnly = true,
                                                colors = TextFieldDefaults.colors(
                                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                                    focusedContainerColor = MaterialTheme.colorScheme.background
                                                ),
                                                trailingIcon = {
                                                    IconButton(onClick = { isDatePickerVisible = true }) {
                                                        Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                                                    }
                                                }
                                            )

                                            /*
                                            TextField(
                                                value = age,
                                                onValueChange = { age = it },
                                                label = { Text(stringResource(id = R.string.âge),
                                                    color = MaterialTheme.colorScheme.onBackground
                                                ) },

                                                modifier = Modifier.fillMaxWidth().padding(20.dp),
                                                colors = TextFieldDefaults.colors(
                                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                                    focusedContainerColor = MaterialTheme.colorScheme.background,

                                                    )
                                            )

                                             */
                                        }


                                    }

                                    Row(
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                                    ) {
                                        // Bouton Retour
                                        Button(
                                            onClick = { if (currentStep > 1) currentStep-- },
                                            enabled = currentStep > 1 // Désactive le bouton si on est à la première étape
                                        ) {
                                            Text(stringResource(id = R.string.retour), style = MaterialTheme.typography.bodyLarge)
                                        }
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Button(
                                            onClick = { if (currentStep in 1..5) {
                                                if (currentStep == 3) {
                                                    if (isValidEmail(email)) {
                                                        currentStep++
                                                    } else {
                                                        emailerror = true
                                                    }
                                                } else {
                                                    currentStep++
                                                }

                                            } else if (currentStep == 6) {
                                                if (username.isNotEmpty() && email.isNotEmpty() && firstname.isNotEmpty() &&
                                                    lastname.isNotEmpty() && dateOfBirth.isNotEmpty() && password.isNotEmpty() && permission
                                                ) {
                                                    if (isValidEmail(email)) {
                                                        val sharedPref =
                                                            context.getSharedPreferences(
                                                                "pref",
                                                                Context.MODE_PRIVATE
                                                            )
                                                        with(sharedPref.edit()) {
                                                            putString(
                                                                "username",
                                                                username.lowercase().replaceFirstChar {
                                                                    if (it.isLowerCase()) it.titlecase(
                                                                        Locale.getDefault()
                                                                    ) else it.toString()
                                                                }.replace(" ", "")
                                                            )  // Enregistrez le username
                                                            apply()
                                                        }
                                                        with(sharedPref.edit()) {
                                                            putString("firstname", firstname)
                                                            apply()
                                                        }
                                                        with(sharedPref.edit()) {
                                                            putString("lastname", lastname)
                                                            apply()
                                                        }
                                                        CoroutineScope(Dispatchers.IO).launch {
                                                            try {


                                                                if (
                                                                    RetrofitInstance.api.is_new_username(
                                                                        SQL().isNewUserName(
                                                                            username.lowercase().replaceFirstChar {
                                                                                if (it.isLowerCase()) it.titlecase(
                                                                                    Locale.getDefault()
                                                                                ) else it.toString()
                                                                            }.replace(" ", "")
                                                                        )
                                                                    )
                                                                    == "1"
                                                                ) {
                                                                    if (
                                                                        RetrofitInstance.api.is_new_user(
                                                                            SQL().isNewUser(
                                                                                username.lowercase().replaceFirstChar {
                                                                                    if (it.isLowerCase()) it.titlecase(
                                                                                        Locale.getDefault()
                                                                                    ) else it.toString()
                                                                                },
                                                                                password,
                                                                                email,
                                                                                firstname,
                                                                                lastname,
                                                                                dateOfBirth,
                                                                                1
                                                                            )
                                                                        )
                                                                        == "1"
                                                                    ) {
                                                                        alreadyuser = true
                                                                    } else {
                                                                        usernameexists = true

                                                                    }
                                                                } else {

                                                                    RetrofitInstance.api.insert_user(
                                                                        SQL().insertUser(
                                                                            username.lowercase().replaceFirstChar {
                                                                                if (it.isLowerCase()) it.titlecase(
                                                                                    Locale.getDefault()
                                                                                ) else it.toString()
                                                                            }.replace(" ", ""),
                                                                            password,
                                                                            email,
                                                                            firstname,
                                                                            lastname,
                                                                            dateOfBirth,
                                                                            1
                                                                        )
                                                                    )

                                                                    completeSetup(context)
                                                                    activity?.finish()

                                                                }

                                                            } catch (e: Exception) {
                                                                withContext(Dispatchers.Main) {
                                                                    networkerror = true
                                                                }
                                                                Log.d("SQL", "error : ${e.message}", e)
                                                            }
                                                        }
                                                    } else {
                                                        emailerror = true
                                                    }
                                                } else {
                                                    showError = true
                                                }


                                            }
                                            },
                                            enabled = when (currentStep) {
                                                1 -> username.isNotEmpty() && !username.contains(" ")
                                                2 -> password.isNotEmpty()
                                                3 -> email.isNotEmpty()
                                                4 -> firstname.isNotEmpty()
                                                5 -> lastname.isNotEmpty()
                                                6 -> dateOfBirth.isNotEmpty() && permission

                                                else -> false
                                            }  // Désactive le bouton si on est à la première étape
                                        ) {
                                            Text(if (currentStep < 6) stringResource(id = R.string.suivant) else stringResource(id = R.string.créer_un_compte),
                                                style = MaterialTheme.typography.bodyLarge)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    if (currentStep == 6) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                checked = permission,
                                                onCheckedChange = { permission = it }
                                            )
                                            Text(stringResource(id = R.string.conditions_utilisation), style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
                                        }
                                    }



                                    var timeLeft by remember { mutableIntStateOf(3) }



                                    if (emailerror) {
                                        Text(
                                            stringResource(id = R.string.email_invalide),
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (timeLeft > 0) {
                                            LaunchedEffect(key1 = timeLeft) {
                                                delay(1000)  // Attend 1 seconde
                                                timeLeft -= 1  // Décrémente le temps restant
                                            }
                                        }
                                        if (timeLeft == 0) {
                                            showError = false
                                            timeLeft = 3
                                        }
                                    }
                                    if (username.contains(" ")) {
                                        Text(
                                            stringResource(id = R.string.espace_username),
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (timeLeft > 0) {
                                            LaunchedEffect(key1 = timeLeft) {
                                                delay(1000)  // Attend 1 seconde
                                                timeLeft -= 1  // Décrémente le temps restant
                                            }
                                        }
                                        if (timeLeft == 0) {
                                            showError = false
                                            timeLeft = 3
                                        }
                                    }
                                    if (showError) {
                                        Text(
                                            stringResource(id = R.string.infos_manquantes),
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (timeLeft > 0) {
                                            LaunchedEffect(key1 = timeLeft) {
                                                delay(1000)  // Attend 1 seconde
                                                timeLeft -= 1  // Décrémente le temps restant
                                            }
                                        }
                                        if (timeLeft == 0) {
                                            showError = false
                                            timeLeft = 3
                                        }
                                    }
                                    if (usernameexists) {
                                        Text(
                                            stringResource(id = R.string.nom_util_existe),
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (timeLeft > 0) {
                                            LaunchedEffect(key1 = timeLeft) {
                                                delay(1000)  // Attend 1 seconde
                                                timeLeft -= 1  // Décrémente le temps restant
                                            }
                                        }
                                        if (timeLeft == 0) {
                                            usernameexists = false
                                            timeLeft = 3
                                        }
                                    }
                                    if (networkerror) {
                                        Text(
                                            stringResource(id = R.string.err_reseau),
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (timeLeft > 0) {
                                            LaunchedEffect(key1 = timeLeft) {
                                                delay(1000)  // Attend 1 seconde
                                                timeLeft -= 1  // Décrémente le temps restant
                                            }
                                        }
                                        if (timeLeft == 0) {
                                            networkerror = false
                                            timeLeft = 3
                                        }
                                    }
                                    if (alreadyuser) {
                                        Text(
                                            stringResource(id = R.string.util_existe),
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (timeLeft > 0) {
                                            LaunchedEffect(key1 = timeLeft) {
                                                delay(1000)  // Attend 1 seconde
                                                timeLeft -= 1  // Décrémente le temps restant
                                            }
                                        }
                                        if (timeLeft == 0) {
                                            alreadyuser = false
                                            timeLeft = 3
                                        }
                                    }
                                }

                                0 -> {
                                    /*
                                    Button(
                                        onClick = {
                                            CoroutineScope(Dispatchers.Main).launch {
                                                prefs.edit().putBoolean("is_first_time", true)
                                                    .apply()
                                                val intent =
                                                    Intent(context, Presentation::class.java)
                                                intent.flags =
                                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                                context.startActivity(intent)


                                            }
                                        },

                                        modifier = Modifier
                                    ) {
                                        Text(stringResource(id = R.string.découvrir))
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))

                                     */
                                    Text(
                                        text = stringResource(id = R.string.commencez_maintenant),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(bottom = 24.dp),
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontSize = 36.sp,
                                            color = Color.White
                                        ),

                                        )
                                    TextField(
                                        textStyle = MaterialTheme.typography.bodyMedium,
                                        value = username_login,
                                        onValueChange = { username_login = it },
                                        label = {
                                            Text(
                                                stringResource(id = R.string.nom_utilisateur),
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },

                                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                                        colors = TextFieldDefaults.colors(
                                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                            focusedContainerColor = MaterialTheme.colorScheme.background,

                                            )
                                    )
                                    TextField(
                                        textStyle = MaterialTheme.typography.bodyMedium,
                                        value = password_login,
                                        onValueChange = { password_login = it },
                                        label = {
                                            Text(
                                                stringResource(id = R.string.mot_de_passe),
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },

                                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                        modifier = Modifier

                                            .fillMaxWidth().padding(20.dp),
                                        colors = TextFieldDefaults.colors(
                                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                            focusedContainerColor = MaterialTheme.colorScheme.background,

                                            )
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))

                                    if (!bloque) {
                                        Button(
                                            onClick = {
                                                // Vérifier que tous les champs sont remplis et que la case est cochée
                                                if (username_login.isNotEmpty() && password_login.isNotEmpty()) {
                                                    val sharedPref =
                                                        context.getSharedPreferences(
                                                            "pref",
                                                            Context.MODE_PRIVATE
                                                        )
                                                    with(sharedPref.edit()) {
                                                        putString(
                                                            "username",
                                                            username_login.lowercase().replaceFirstChar {
                                                                if (it.isLowerCase()) it.titlecase(
                                                                    Locale.getDefault()
                                                                ) else it.toString()
                                                            }.replace(" ", "")
                                                        )  // Enregistrez le username
                                                        apply()
                                                    }



                                                    CoroutineScope(Dispatchers.IO).launch {
                                                        try {


                                                            if (RetrofitInstance.api.is_new_username(
                                                                    SQL().isNewUserName(
                                                                        username_login.lowercase().replaceFirstChar {
                                                                            if (it.isLowerCase()) it.titlecase(
                                                                                Locale.getDefault()
                                                                            ) else it.toString()
                                                                        }.replace(" ", "")
                                                                    )
                                                                ) == "1"
                                                            ) {
                                                                if (
                                                                    RetrofitInstance.api.login(
                                                                        SQL().login(
                                                                            username_login.lowercase().replaceFirstChar {
                                                                                if (it.isLowerCase()) it.titlecase(
                                                                                    Locale.getDefault()
                                                                                ) else it.toString()
                                                                            }.replace(" ", ""),
                                                                            password_login
                                                                        )
                                                                    ) == "1"
                                                                ) {
                                                                    val co_firstname =
                                                                        RetrofitInstance.api.get_user_name(
                                                                            SQL().getuserfisrtname(
                                                                                username_login.lowercase().replaceFirstChar {
                                                                                    if (it.isLowerCase()) it.titlecase(
                                                                                        Locale.getDefault()
                                                                                    ) else it.toString()
                                                                                }.replace(" ", "")
                                                                            )
                                                                        )

                                                                    with(sharedPref.edit()) {
                                                                        putString(
                                                                            "firstname",
                                                                            co_firstname
                                                                        )
                                                                        apply()
                                                                    }

                                                                    val co_lastname =
                                                                        RetrofitInstance.api.get_user_name(
                                                                            SQL().getuserlastname(
                                                                                username_login.lowercase().replaceFirstChar {
                                                                                    if (it.isLowerCase()) it.titlecase(
                                                                                        Locale.getDefault()
                                                                                    ) else it.toString()
                                                                                }.replace(" ", "")
                                                                            )
                                                                        )

                                                                    with(sharedPref.edit()) {
                                                                        putString(
                                                                            "lastname",
                                                                            co_lastname
                                                                        )
                                                                        apply()
                                                                    }
                                                                    completeSetup(context)
                                                                    activity?.finish()

                                                                } else {
                                                                    if (essais_connexion <= 4) {
                                                                        wrongcredentials = true
                                                                        essais_connexion++
                                                                        tmpstartTime =
                                                                            SystemClock.elapsedRealtime()
                                                                        prefs.edit().putLong("tmpstartTime", tmpstartTime!!).apply()


                                                                    } else {
                                                                        bloque = true
                                                                        prefs.edit().putBoolean("bloque", bloque).apply()

                                                                        startTime =
                                                                            SystemClock.elapsedRealtime() // en millisecondes depuis le démarrage de l'appareil
                                                                        prefs.edit().putLong("startTime", startTime!!).apply()

                                                                    }

                                                                }
                                                            } else {
                                                                if (essais_connexion <= 4) {
                                                                    notauser = true
                                                                    essais_connexion++
                                                                    tmpstartTime =
                                                                        SystemClock.elapsedRealtime()
                                                                    prefs.edit().putLong("tmpstartTime", tmpstartTime!!).apply()



                                                                } else {
                                                                    bloque = true
                                                                    prefs.edit().putBoolean("bloque", bloque).apply()

                                                                    startTime =
                                                                        SystemClock.elapsedRealtime() // en millisecondes depuis le démarrage de l'appareil
                                                                    prefs.edit().putLong("startTime", startTime!!).apply()

                                                                }
                                                            }

                                                        } catch (e: Exception) {
                                                            withContext(Dispatchers.Main) {
                                                                networkerror = true
                                                            }
                                                            Log.d("SQL", "error : ${e.message}", e)
                                                        }
                                                    }
                                                } else {
                                                    showError = true
                                                }
                                            },

                                            modifier = Modifier
                                                .clickable {}
                                        ) {
                                            Text(stringResource(id = R.string.s_identifier), style = MaterialTheme.typography.bodyLarge)
                                        }
                                    } else {
                                        Column(
                                            modifier = Modifier.padding(horizontal = 16.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                        ) {
                                            Text(stringResource(id = R.string.compte_bloqué),
                                                color = MaterialTheme.colorScheme.error,
                                                style = MaterialTheme.typography.bodyMedium,
                                                textAlign = TextAlign.Center)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text("${30 - (elapsedTime / 1000)}s", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(50.dp))
                                    Text(
                                        stringResource(id = R.string.s_inscrire),
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            color = Color.White
                                        ),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(16.dp))
                                            .clickable{
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    val nextPage = 1
                                                    pagerState.scrollToPage(nextPage)

                                                }
                                            }
                                            .padding(8.dp)


                                    )
                                    IconButton(
                                        onClick = {

                                            CoroutineScope(Dispatchers.Main).launch {
                                                val nextPage = 1
                                                pagerState.scrollToPage(nextPage)

                                            }
                                        },

                                        ) {
                                        Icon(
                                            imageVector = Icons.Filled.KeyboardArrowRight, // Icône de la flèche
                                            contentDescription = "Flèche ",
                                            modifier = Modifier.size(40.dp)
                                                .offset(x = Dp(offsetX)),
                                            tint = Color.White
                                        )
                                    }


                                    var timeLeft by remember { mutableIntStateOf(3) }

                                    if (notauser) {
                                        Text(
                                            stringResource(id = R.string.util_incorrect),
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (timeLeft > 0) {
                                            LaunchedEffect(key1 = timeLeft) {
                                                delay(1000)  // Attend 1 seconde
                                                timeLeft -= 1  // Décrémente le temps restant
                                            }
                                        }
                                        if (timeLeft == 0) {
                                            notauser = false
                                            timeLeft = 3
                                        }
                                    }
                                    if (wrongcredentials) {
                                        Text(
                                            stringResource(id = R.string.mdp_incorrect),
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (timeLeft > 0) {
                                            LaunchedEffect(key1 = timeLeft) {
                                                delay(1000)  // Attend 1 seconde
                                                timeLeft -= 1  // Décrémente le temps restant
                                            }
                                        }
                                        if (timeLeft == 0) {
                                            wrongcredentials = false
                                            timeLeft = 3
                                        }
                                    }
                                    if (showError) {
                                        Text(
                                            stringResource(id = R.string.infos_manquantes),
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (timeLeft > 0) {
                                            LaunchedEffect(key1 = timeLeft) {
                                                delay(1000)  // Attend 1 seconde
                                                timeLeft -= 1  // Décrémente le temps restant
                                            }
                                        }
                                        if (timeLeft == 0) {
                                            showError = false
                                            timeLeft = 3
                                        }
                                    }
                                    if (networkerror) {
                                        Text(
                                            stringResource(id = R.string.err_reseau),
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (timeLeft > 0) {
                                            LaunchedEffect(key1 = timeLeft) {
                                                delay(1000)  // Attend 1 seconde
                                                timeLeft -= 1  // Décrémente le temps restant
                                            }
                                        }
                                        if (timeLeft == 0) {
                                            networkerror = false
                                            timeLeft = 3
                                        }
                                    }


                                }
                            }
                        }
                    }

                }

            }

        }


    }
}
