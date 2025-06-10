package com.example.aletheia

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable

import kotlinx.coroutines.launch


import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Base64
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import kotlinx.coroutines.delay


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.ImageViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.LocalContentAlpha
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.aletheia.pages.Init
import com.example.aletheia.pages.creationpage.CreationPage
import com.example.aletheia.pages.creationpage.CreationTopBar
import com.example.aletheia.pages.creationpage.triggerRisingVibrationWithClick
import com.example.aletheia.pages.homepage.HomePage
import com.example.aletheia.pages.homepage.HomeTopBar
import com.example.aletheia.pages.profilepage.Profile
import com.example.aletheia.pages.profilepage.TopBar
import com.example.aletheia.pages.profilepage.getProfileImage
import com.example.aletheia.pages.searchpage.SearchPage
import com.example.aletheia.viewmodels.ChatHistoryModel
import com.example.aletheia.viewmodels.ChatHistoryModelFactory
import com.example.aletheia.viewmodels.GetPages
import com.example.aletheia.viewmodels.GetPagesViewModelFactory
import com.example.aletheia.viewmodels.GlobalViewModel
import com.example.aletheia.viewmodels.GlobalViewModelFactory
import com.example.aletheia.viewmodels.ProfileViewModel
import com.example.aletheia.viewmodels.ProfileViewModelFactory
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.coroutineScope

import com.airbnb.lottie.compose.*


object PrefsHelper {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun getPrefs(): SharedPreferences {
        return appContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun LottieFullScreenAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.splashscreen_json)
    )
    val progress by animateLottieCompositionAsState(composition)

    Box(modifier = Modifier.fillMaxSize().background(color = Color(0xFF000E10))) {
        LottieAnimation(
            composition = composition,
            iterations = 1,
            modifier = Modifier.fillMaxSize()
        )
    }

}

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.S)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)






        splashscreen.setKeepOnScreenCondition { keepSplashScreen }

/*
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val iconView = splashScreenView.iconView // Cibler l'icône du splashscreen

            val fadeOut = ObjectAnimator.ofFloat(iconView, View.ALPHA, 1f, 0f)
            val scaleX = ObjectAnimator.ofFloat(iconView, View.SCALE_X, 1f, 1.2f)
            val scaleY = ObjectAnimator.ofFloat(iconView, View.SCALE_Y, 1f, 1.2f)



            fadeOut.duration = 500L
            scaleX.duration = 500L
            scaleY.duration = 500L

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(scaleX, scaleY)

            animatorSet.doOnEnd { splashScreenView.remove() }
            animatorSet.start()
        }

*/







        PrefsHelper.init(this)


        lifecycleScope.launch {
            delay(500)
            keepSplashScreen = false

        }








        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.light(
            android.graphics.Color.TRANSPARENT,
            android.graphics.Color.TRANSPARENT)
        )


        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        val sharedPref = getSharedPreferences("pref", MODE_PRIVATE)
        



/*
                val getpagesprefs = getSharedPreferences("getPagesPrefs::Céaldan", MODE_PRIVATE)
                getpagesprefs.edit().clear().apply()

                /*val getcampaignsprefs = getSharedPreferences("getCampaignsPrefs::Céaldan", MODE_PRIVATE)
                getpagesprefs.edit().clear().apply()*/

                sharedPref.edit().clear().apply()

                val userpref = getSharedPreferences("Céaldan", MODE_PRIVATE)
                userpref.edit().clear().apply()

                val modelpref = getSharedPreferences("chatviewmodelprefs::Céaldan", MODE_PRIVATE)
                modelpref.edit().clear().apply()

                val globalpref = getSharedPreferences("globalviewmodel::Céaldan", MODE_PRIVATE)
                globalpref.edit().clear().apply()


*/





        val userName = sharedPref.getString("username", "DefaultUsername") ?: "DefaultUsername"
        val firstName = sharedPref.getString("firstname", "DefaultFirstName") ?: "DefaultFirstName"
        val lastName = sharedPref.getString("lastname", "DefaultLastName") ?: "DefaultFirstName"
        val appState = checkAppState(this)

        when (appState) {

            /*1 -> {

                // Rediriger vers l'activité Presentation
                val intent = Intent(this, Presentation::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }

             */
            2 -> {

                // Rediriger vers l'activité Init
                val intent = Intent(this, Init::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }

            3 -> {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }



        setContent {
            AletheiaApp(userName, firstName, lastName)

        }


    }

}






fun checkAppState(context: Context): Int {
    val prefs: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    // Vérifier les états
    val isFirstTime = prefs.getBoolean("is_first_time", true)
    val isSetupComplete = prefs.getBoolean("is_setup_complete", false)

    Log.d("AppState", "is_first_time: $isFirstTime, is_setup_complete: $isSetupComplete")
    return when {
        //isFirstTime -> 1 // Presentation
        !isSetupComplete -> 2 // Init
        else -> 3 // MainActivity
    }
}

sealed class BottomNavIcon {
    data class Vector(val icon: ImageVector) : BottomNavIcon()
    data class Image(val painter: Painter) : BottomNavIcon()
    data class Bitmap(val bitmap: android.graphics.Bitmap) : BottomNavIcon()

}
data class BottomNavItem(val label: String, val icon: BottomNavIcon, val route: String)

@Composable
fun NoRippleIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                // 🔥 Set this to null to remove ripple on touch
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        val contentAlpha = if (enabled) LocalContentAlpha.current else ContentAlpha.disabled
        CompositionLocalProvider(LocalContentAlpha provides contentAlpha, content = content)
    }
}



@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AletheiaApp(userName: String, firstName : String, lastName : String) {
    val context = LocalContext.current // Utilisation de LocalContext pour obtenir le contexte
    val navController = rememberAnimatedNavController()  // Créez un NavController

    createNotificationChannel(context)





    val backgroundgradient  = painterResource(id = R.drawable.aletheiabackgroundsplashscreen)


    val themeViewModel: ThemeViewModel = viewModel(
        factory = ThemeViewModelFactory(context, userName, isSystemInDarkTheme())
    )
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()






    val viewModel: GetPages = viewModel(factory = GetPagesViewModelFactory(context, userName))

    //val getCampaigns: GetCampaigns = viewModel(factory = GetCampaignsViewModelFactory(context, userName))

    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(context, userName)
    )
    val globalViewModel: GlobalViewModel = viewModel(
        factory = GlobalViewModelFactory(context, userName)
    )

    val chatHistoryModel: ChatHistoryModel = viewModel(
        factory = ChatHistoryModelFactory(context, userName)
    )




    val inSettings by profileViewModel.goSettings.collectAsState()

    //val show by getCampaigns.show.collectAsState()
    //val profileShow by getCampaigns.profileShow.collectAsState()
    val newPost by viewModel.newPost.collectAsState()

    val reelSysBar by themeViewModel.reelSysBar.collectAsState()

    val whichPage by globalViewModel.whichPage.collectAsState()

    val editProfile by profileViewModel.editProfile.collectAsState()

    val goToFollowersFollowing by profileViewModel.goToFollowersFollowing.collectAsState()

    val visitedProfile by viewModel.visitProfile.collectAsState()

    // Appliquer le thème
    AletheiaTheme(themeViewModel, darkTheme = /*if (reelSysBar) reelSysBar else*/ isDarkTheme) {



        Scaffold(
            modifier = Modifier.background(Color.Transparent),

            bottomBar = {
                if (/*!show && !profileShow &&*/ !inSettings && !newPost && !editProfile && !goToFollowersFollowing.first) {
                    BottomBar(globalViewModel,profileViewModel, viewModel, navController, themeViewModel, userName)
                }
            },

            topBar = {

                if (whichPage == "profilepage") {
                    TopBar(
                        profileViewModel,
                        themeViewModel,
                        userName
                    )

                } else if (whichPage == "searchpage") {

                } else if (whichPage == "creationpage") {
                    CreationTopBar(
                        chatHistoryModel,
                        globalViewModel,
                        viewModel,
                        themeViewModel,
                        userName
                    )
                } else if (whichPage == "homepage") {
                    HomeTopBar(profileViewModel, globalViewModel, viewModel, themeViewModel, userName)

                } else if (whichPage == "settingspage") {
                    TopBar(
                        profileViewModel,
                        themeViewModel,
                        userName
                    )

                } else if (whichPage == "newpostpage") {
                    CreationTopBar(
                        chatHistoryModel,
                        globalViewModel,
                        viewModel,
                        themeViewModel,
                        userName
                    )

                }

            },

            content = { innerPadding ->

                Box(
                    modifier = Modifier
                        .background(Color.Transparent)

                        .consumeWindowInsets(innerPadding)
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {

                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "home",
                        enterTransition = { fadeIn(animationSpec = tween(1)) },
                        exitTransition = { fadeOut(animationSpec = tween(1)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(1)) },
                        popExitTransition = { fadeOut(animationSpec = tween(1)) }
                    ) {
                        composable(
                            "home",

                            ) {
                            HomePage(
                                profileViewModel,
                                globalViewModel,
                                navController,
                                viewModel,
                                themeViewModel,
                                userName
                            )
                        }
                        composable(
                            "creation",

                            ) {
                            CreationPage(navController, profileViewModel, chatHistoryModel,globalViewModel, viewModel, themeViewModel, userName)
                        }
                        composable(
                            "profil",

                            ) {
                            Profile(
                                chatHistoryModel,
                                globalViewModel,
                                profileViewModel,
                                viewModel,
                                navController,
                                userName,
                                firstName,
                                lastName,
                                themeViewModel
                            )
                        }
                        /*
                        composable(
                            "campaign",

                            ) {
                            CampaignPage(
                                getCampaigns,
                                navController,
                                viewModel,
                                themeViewModel,
                                userName
                            )
                        }

                         */
                        composable(
                            "search",

                            ) {
                            SearchPage(globalViewModel, themeViewModel, userName)
                        }
                    }

                }
            }
        )
    }

}


@Composable
fun isGestureNavigation(): Boolean {
    val view = LocalView.current
    var isGesture by remember { mutableStateOf(false) }

    val insets = ViewCompat.getRootWindowInsets(view)
    val navBarHeight = insets?.getInsets(WindowInsetsCompat.Type.navigationBars())?.bottom ?: 0
    isGesture = navBarHeight < 50 //testé expérimentalement avec mon tel

    return isGesture
}


@Composable
fun DetectRouteChange(navController: NavController, onRouteExit: (String) -> Unit) {
    var previousRoute by remember { mutableStateOf(navController.currentDestination?.route) }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            val newRoute = destination.route
            if (previousRoute != null && previousRoute != newRoute) {
                onRouteExit(previousRoute!!) // Déclenche l'action quand on quitte une route
            }
            previousRoute = newRoute
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomBar(globalViewModel: GlobalViewModel, profileViewModel: ProfileViewModel, viewModel: GetPages, navController: NavHostController, themeViewModel: ThemeViewModel, username: String) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    val currentroute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    val hapticPref by globalViewModel.hapticPref.collectAsState()


    // Appliquer le thème
    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {
        //val notifbadge = prefs.getInt("notifbadgeUser", 0)

        val homeIcon = ImageVector.vectorResource(id = R.drawable.homeicon)
        val assistantIcon = ImageVector.vectorResource(id = R.drawable.newposticon)
        val searchicon = ImageVector.vectorResource(id = R.drawable.simplesearchicon)

        val homeIconFilled = ImageVector.vectorResource(id = R.drawable.homeiconfilled)
        val assistantIconFilled = ImageVector.vectorResource(id = R.drawable.newposticon)
        val searchIconFilled = ImageVector.vectorResource(id = R.drawable.simplesearchicon)

        val goChat by viewModel.goChat.collectAsState()

        val newPost by viewModel.newPost.collectAsState()


        val profilePic by profileViewModel.profilePic.collectAsState()

        val creationMenu by viewModel.creationMenu.collectAsState()
        val backgroundgradient = painterResource(id = R.drawable.aletheiabackgroundsplashscreen)

        val onbackground = MaterialTheme.colorScheme.onBackground

        val drawerStateFromViewModel by globalViewModel.drawerState.collectAsState()

        val primary = MaterialTheme.colorScheme.primary

        val vibrator = context.getSystemService(Vibrator::class.java)

        val isNetWorkAvailable by RetrofitInstance.isNetworkAvailable.collectAsState()

        val showSeePrompt by viewModel.showSeePrompt.collectAsState()
        val showComments by viewModel.showComments.collectAsState()
        val threeDotsMenu by viewModel.threeDotsMenu.collectAsState()
        val sendContent by viewModel.sendContent.collectAsState()





        val progress = remember { Animatable(0f) } // Animation de la progression du cercle

        var profileSelected by remember { mutableStateOf(false) }
        LaunchedEffect(profileSelected) {
            if (profileSelected) {
                // Animation progressive du cercle
                progress.animateTo(
                    targetValue = 1f, // Dessine le cercle
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                )
            } else {
                progress.snapTo(0f) // Instantanément à 0
            }

        }

        val coroutineScope = rememberCoroutineScope()

        DetectRouteChange(navController) { exitedRoute ->
            if (exitedRoute == "profil") {
                if (profileSelected) {
                    profileSelected = false
                } else {
                    coroutineScope.launch {
                        progress.snapTo(0f)
                    }
                }

            }
        }

        LaunchedEffect(currentroute) {
            if (currentroute == "profil" && profileSelected == false) {
                progress.snapTo(1f)
            }
        }



        var isSelected by remember { mutableStateOf(false) }
        val scale by remember { mutableStateOf(Animatable(1f)) }

        LaunchedEffect(isSelected) {
            if (isSelected) {
                scale.animateTo(
                    targetValue = 1.2f, // La bulle disparaît
                    animationSpec = tween(300)
                )
                scale.animateTo(
                    targetValue = 1f, // La bulle disparaît
                    animationSpec = tween(200)
                )
                isSelected = false
            }
        }

        var isWobbling by remember { mutableStateOf(false) }

        val rotation by remember {
            mutableStateOf(Animatable(0f))
        }

        LaunchedEffect(isWobbling) {
            if (isWobbling) {
                rotation.animateTo(
                    targetValue = 10f, // Inclinaison à droite
                    animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                )
                rotation.animateTo(
                    targetValue = -10f, // Inclinaison à gauche
                    animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                )
                rotation.animateTo(0f, animationSpec = tween(300)) // Revient droit quand on stoppe
                isWobbling = false
            }
        }

        val rotationsearch = remember { Animatable(0f) }
        val scalesearch = remember { Animatable(1f) }
        var isAnimating by remember { mutableStateOf(false) }

        LaunchedEffect(isAnimating) {
            if (isAnimating) {
                // First set of animations (run simultaneously)
                coroutineScope {
                    launch {
                        rotationsearch.animateTo(
                            targetValue = 15f, // Rotation to the right
                            animationSpec = tween(300)
                        )
                    }
                    launch {
                        scalesearch.animateTo(
                            targetValue = 1.2f, // Scale up
                            animationSpec = tween(200)
                        )
                    }
                }

                // Second set of animations (run simultaneously)
                coroutineScope {
                    launch {
                        rotationsearch.animateTo(
                            targetValue = 0f, // Return to normal position
                            animationSpec = tween(200)
                        )
                    }
                    launch {
                        scalesearch.animateTo(
                            targetValue = 1f, // Return to normal size
                            animationSpec = tween(200)
                        )
                    }
                }

                isAnimating = false
            }
        }

        /*
        Surface(
            modifier = Modifier
                //.border(color = if (isDarkTheme) MaterialTheme.colorScheme.primary.copy(0.5f) else MaterialTheme.colorScheme.primary.copy(0.1f), width = 2.5.dp, shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                //.shadow(8.dp, shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp), ambientColor = MaterialTheme.colorScheme.primary, spotColor = MaterialTheme.colorScheme.primary)
                .drawBehind {
                    val strokeWidth = 4.dp.toPx()
                    val shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    val outline = shape.createOutline(size, layoutDirection, this)
                    drawOutline(
                        outline = outline,
                        color = if (isDarkTheme) Color.Black else Color.Transparent, // Couleur de la bordure
                        style = Stroke(width = strokeWidth)
                    )

                }
                .fillMaxWidth(), // Barre de navigation sur toute la largeur
            shape = RoundedCornerShape(
                topStart = 50.dp,
                topEnd = 50.dp
            ),
            shadowElevation = 8.dp, // Ombre derrière la barre

        ) {
         */
        Surface(
            modifier = Modifier
                .height(if (isGestureNavigation()) 50.dp else 80.dp)
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = if (currentroute != "creation") { onbackground.copy(0.1f) } else {if (drawerStateFromViewModel.isOpen || newPost) onbackground.copy(0.1f) else Color.Transparent},
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = strokeWidth
                    )
                },
            color = MaterialTheme.colorScheme.background,
        ){

            BottomAppBar(
                modifier = Modifier
                    .height(80.dp),
                containerColor = Color.Transparent,

            ) {
                val items = listOf(
                    Pair(BottomNavItem("Accueil", BottomNavIcon.Vector(homeIcon), "home"),
                        BottomNavItem("Accueil", BottomNavIcon.Vector(homeIconFilled), "home")),
                    Pair(BottomNavItem("Creation", BottomNavIcon.Vector(assistantIcon), "creation"),
                        BottomNavItem("Creation", BottomNavIcon.Vector(assistantIconFilled), "creation")),
                    Pair(BottomNavItem("Recherche", BottomNavIcon.Vector(searchicon), "search"),
                        BottomNavItem("Recherche", BottomNavIcon.Vector(searchIconFilled), "search")),
                    /*Pair(BottomNavItem("Campaign", BottomNavIcon.Vector(campaignicon), "campaign"),
                        BottomNavItem("Campaign", BottomNavIcon.Vector(campaigniconfilled), "campaign")),*/
                    Pair(BottomNavItem("Profil",
                        if (profilePic == null) BottomNavIcon.Vector(Icons.Default.AccountCircle)
                        else BottomNavIcon.Bitmap(profilePic!!), "profil"),
                        BottomNavItem("Profil",
                            if (profilePic == null) BottomNavIcon.Vector(Icons.Default.AccountCircle)
                            else BottomNavIcon.Bitmap(profilePic!!), "profil"))
                )

                items.forEach { item ->
                    val selectedIcon = if (currentroute == item.first.route) item.second else item.first
                    val firstPage by viewModel.isFirstPage.collectAsState()

                    NoRippleIconButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (currentroute == "home" && item == items[0]) {
                                viewModel.setCreationMenu(false)


                                if (!goChat && !firstPage && !(showSeePrompt || showComments || threeDotsMenu || sendContent)) {
                                    //getCampaigns.clearContentID()
                                    viewModel.refreshData()
                                    viewModel.setFirstPage(true)
                                } else {
                                    if (goChat) {
                                        viewModel.setGoChat(false)
                                    } else if (showSeePrompt || showComments || threeDotsMenu || sendContent) {
                                        viewModel.setShowSeePrompt(false)
                                        viewModel.setThreeDotsMenu(false)
                                        viewModel.setShowComments(false)
                                        viewModel.setSendContent(false)
                                    }
                                }
                            } else if (currentroute != "home" && item == items[0]) {
                                isSelected = true
                                navController.navigate(item.first.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }

                            } else if (currentroute == "creation" && item == items[1]) {



                            } else if (currentroute == "search" && item == items[2]) {

                            } else if (currentroute != "search" && item == items[2]) {
                                isAnimating = true
                                navController.navigate(item.first.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            } else if (currentroute == "profil" && item == items[3]) {
                                profileViewModel.setProfileLazy(true)

                            } else if (currentroute != "profil" && item == items[3]) {
                                profileSelected = true
                                navController.navigate(item.first.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            } else if (currentroute != "creation" && item == items[1]) {
                                isWobbling = true
                                /*if (hapticPref) {
                                    triggerRisingVibrationWithClick(context)
                                }*/
                                navController.navigate(item.first.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }/*else if (currentroute == "campaign" && item == items[3]) {
                                viewModel.clearCampaignID()
                                //getCampaigns.refreshData()
                            }*/ else {
                                navController.navigate(item.first.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    ) {

                        when (selectedIcon.icon) {
                            is BottomNavIcon.Vector ->


                                Icon(
                                imageVector = selectedIcon.icon.icon,
                                contentDescription = selectedIcon.label,
                                modifier = Modifier
                                    .size(if (selectedIcon.icon.icon == assistantIcon || selectedIcon.icon.icon == searchicon) 30.dp else 25.dp)
                                    .then (
                                        if (selectedIcon.icon.icon == homeIconFilled && currentroute == "home") {
                                            Modifier.graphicsLayer(scaleX = scale.value, scaleY = scale.value)
                                        } else if (selectedIcon.icon.icon == assistantIconFilled && currentroute == "creation") {
                                            Modifier.graphicsLayer(rotationZ = rotation.value) // Applique la rotation animée
                                        } else if (selectedIcon.icon.icon == searchIconFilled && currentroute == "search") {
                                            Modifier.graphicsLayer(
                                                rotationZ = rotationsearch.value,
                                                scaleX = scalesearch.value,
                                                scaleY = scalesearch.value
                                            )
                                        } else if (selectedIcon.icon.icon == Icons.Default.AccountCircle && currentroute == "profil") {
                                            Modifier.drawBehind {
                                                if (progress.value > 0) {
                                                    drawArc(
                                                        color = primary,
                                                        startAngle = -90f, // Commence en haut
                                                        sweepAngle = 360f * progress.value, // Anime le cercle progressivement
                                                        useCenter = false,
                                                        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                                                    )
                                                }
                                            }
                                        } else {
                                            Modifier
                                        }
                                    ),
                                tint = if (currentroute == item.first.route && selectedIcon.icon.icon != Icons.Default.AccountCircle) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onBackground
                            )
                            is BottomNavIcon.Bitmap ->

                                Image(
                                painter = BitmapPainter(selectedIcon.icon.bitmap.asImageBitmap()),
                                contentDescription = selectedIcon.label,
                                modifier = Modifier
                                    .size(30.dp)
                                    .drawBehind {
                                        if (progress.value > 0 && currentroute == "profil") {
                                            drawArc(
                                                color = primary,
                                                startAngle = -90f, // Commence en haut
                                                sweepAngle = 360f * progress.value, // Anime le cercle progressivement
                                                useCenter = false,
                                                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                                            )
                                        }
                                    }
                                    .clip(CircleShape)

                                    /*.border(2.dp,
                                        if (currentroute == item.first.route) MaterialTheme.colorScheme.primary
                                        else Color.Transparent,
                                        CircleShape
                                    )*/,
                                contentScale = ContentScale.Crop
                            )

                            is BottomNavIcon.Image -> Image(
                                painter = selectedIcon.icon.painter,
                                contentDescription = item.first.label,
                                modifier = Modifier.size(100.dp).border(
                                    2.dp,
                                    if (currentroute == item.first.route) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    CircleShape
                                ),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

        }
    }
}























