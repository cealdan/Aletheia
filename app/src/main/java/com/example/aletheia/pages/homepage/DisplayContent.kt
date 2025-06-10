package com.example.aletheia.pages.homepage

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.os.Build
import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.rememberNavController

import kotlinx.coroutines.launch
import kotlin.random.Random

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState

import com.example.aletheia.showFloatingToast

import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.delay

import androidx.compose.ui.graphics.graphicsLayer


import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.aletheia.AletheiaTheme
import com.example.aletheia.NoRippleIconButton
import com.example.aletheia.R
import com.example.aletheia.ThemeViewModel
import com.example.aletheia.pages.profilepage.getProfileImage
import com.example.aletheia.viewmodels.EvalAiContentModel
import com.example.aletheia.viewmodels.GetPages
import kotlinx.coroutines.Job
import java.util.Locale
import kotlin.math.abs
import com.google.gson.reflect.TypeToken

import coil.compose.rememberImagePainter
import com.example.aletheia.RetrofitInstance
import com.example.aletheia.bitmapToBase64
import com.example.aletheia.SQL
import com.example.aletheia.SendImage
import com.example.aletheia.pages.creationpage.triggerRisingVibrationWithClick
import com.example.aletheia.pages.profilepage.FollowersFollowingPage
import com.example.aletheia.sendAI
import com.example.aletheia.viewmodels.GlobalViewModel
import com.example.aletheia.viewmodels.ProfileViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.text.Normalizer
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.sin
import androidx.activity.compose.LocalActivity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(profileViewModel: ProfileViewModel,globalViewModel: GlobalViewModel, getPages: GetPages, themeViewModel: ThemeViewModel, username: String) {
    val context = LocalContext.current // Utilisation de LocalContext pour obtenir le contexte
    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)
    val coroutineScope = rememberCoroutineScope()


    val goChat by getPages.goChat.collectAsState()
    val profileVisited by getPages.visitProfile.collectAsState()
    val usernamevisited by getPages.usernamevisited.collectAsState()

    val creationMenu by getPages.creationMenu.collectAsState()

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val onbackground = MaterialTheme.colorScheme.onBackground

    val adviceFromAI = prefs.getString("adviceFromAI", "") ?: ""
    val completed = prefs.getBoolean("completed", false)
    var displayedtext by remember { mutableStateOf(adviceFromAI) }
    val currentLocale = Locale.getDefault()
    val language = currentLocale.language
    val greetings_catch = stringResource(id = R.string.pas_de_co) + ", $username !"


    var showtext by remember { mutableStateOf(false) }

    val isFirstLaunch by getPages.isFirstLaunch.collectAsState()

    val adviceFromAIPref by globalViewModel.adviceFromAIPref.collectAsState()
    val customPromptToAI by globalViewModel.customPromptToAI.collectAsState()
    val assistantModel by globalViewModel.assistantModel.collectAsState()

    var refresh by remember { mutableStateOf(false) }
    var showRefresh by remember { mutableStateOf(true) }

    val alpharefresh by animateFloatAsState(
        targetValue = if (showRefresh) 1f else 0f,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
    )

    var loading by remember { mutableStateOf(false) }

    var showFilter by remember { mutableStateOf(false) }

    val goToFollowersFollowing by profileViewModel.goToFollowersFollowing.collectAsState()


    LaunchedEffect(refresh) {
        if (refresh) {
            showRefresh = false
            loading = true
            coroutineScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        RetrofitInstance.api.sendPrompt(
                            sendAI(
                                model = assistantModel,
                                prompt = "Give a tip to $username on this topic : $customPromptToAI. You will give an example. But BE SHORT and don't say anything else, start talking directly to $username. Answer in this language : $language"
                            )
                        )
                    }
                    response
                        .onStart {
                            displayedtext = ""
                            prefs.edit().putBoolean("completed", false).apply()
                        }
                        .onCompletion {
                            prefs.edit().putString("adviceFromAI", displayedtext).apply()
                            refresh = false
                            showRefresh = true
                        }
                        .collect { chunk ->
                            displayedtext += chunk
                            loading = false
                        }
                    prefs.edit().putBoolean("completed", true).apply()
                } catch (e: Exception) {
                    loading = false
                    displayedtext = ""
                    greetings_catch.forEach { char ->
                        delay(30)
                        displayedtext += char
                    }
                    refresh = false
                    showRefresh = true
                    prefs.edit().putString("adviceFromAI", displayedtext).apply()
                }
            }
        }
    }


    LaunchedEffect(adviceFromAIPref) {
        if (!adviceFromAIPref) {
            displayedtext = ""
            prefs.edit().putString("adviceFromAI", displayedtext).apply()
        } else {
            if (displayedtext.isEmpty() && completed) {
                showRefresh = false
                loading = true
                coroutineScope.launch {
                    try {
                        val response = withContext(Dispatchers.IO) {
                            RetrofitInstance.api.sendPrompt(
                                sendAI(
                                    model = assistantModel,
                                    prompt = "Give a tip to $username on this topic : $customPromptToAI. You will give an example. But BE SHORT and don't say anything else, start talking directly to $username. Answer in this language : $language"
                                )
                            )
                        }
                        response
                            .onStart {
                                displayedtext = ""
                                prefs.edit().putBoolean("completed", false).apply()
                            }
                            .onCompletion {
                                prefs.edit().putString("adviceFromAI", displayedtext).apply()
                                showRefresh = true
                            }
                            .collect { chunk ->
                                displayedtext += chunk
                                loading = false
                            }
                        prefs.edit().putBoolean("completed", true).apply()
                    } catch (e: Exception) {
                        loading = false
                        displayedtext = ""
                        greetings_catch.forEach { char ->
                            delay(30)
                            displayedtext += char
                        }
                        showRefresh = true
                        prefs.edit().putString("adviceFromAI", displayedtext).apply()
                    }
                }
            }
        }
    }

    // Appliquer le thème
    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {


        LaunchedEffect(Unit) {
            if ((isFirstLaunch || !completed) && adviceFromAIPref) {
                showRefresh = false
                loading = true
                coroutineScope.launch {
                    try {
                        val response = withContext(Dispatchers.IO) {
                            RetrofitInstance.api.sendPrompt(sendAI(model = assistantModel,prompt ="Give an adive to $username on this topic : $customPromptToAI. You will give an example. But BE SHORT and don't say anything else, start talking directly to $username. Answer in this language : $language"))
                        }
                        response
                            .onStart {
                                displayedtext = ""
                                prefs.edit().putBoolean("completed", false).apply()
                            }
                            .onCompletion {
                                prefs.edit().putString("adviceFromAI", displayedtext).apply()
                                showRefresh = true
                            }
                            .collect { chunk ->
                                displayedtext += chunk
                                loading = false
                            }
                        prefs.edit().putBoolean("completed", true).apply()
                    } catch (e: Exception) {
                        loading = false
                        displayedtext = ""
                        greetings_catch.forEach { char ->
                            delay(30)
                            displayedtext += char
                        }
                        showRefresh = true
                        prefs.edit().putString("adviceFromAI", displayedtext).apply()
                    }
                }
            }
        }

        Surface(
            modifier = Modifier
                .height(70.dp)

                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = onbackground.copy(0.1f),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                },
            color = MaterialTheme.colorScheme.background,
        ) {

            TopAppBar(
                title = {if (profileVisited) {
                    Row(modifier = Modifier.fillMaxHeight(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = usernamevisited,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.pacifico_regular)),
                                fontSize = 20.sp,
                            ),
                            maxLines = 1
                        )
                    }
                } },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        if (!goChat && !profileVisited) {
                            NoRippleIconButton(
                                modifier = Modifier,
                                onClick = {
                                    getPages.setGoChat(true)
                                    getPages.setCreationMenu(false)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.socialicon),
                                    contentDescription = "NewPost",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        } else if (profileVisited && !goToFollowersFollowing.first) {
                            NoRippleIconButton(
                                modifier = Modifier.rotate(180f),
                                onClick = {
                                    getPages.setVisitedProfile(false)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.gobackicon),
                                    contentDescription = "GoHome",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }

                },
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        if (goChat || goToFollowersFollowing.first) {
                            NoRippleIconButton(
                                modifier = Modifier,
                                onClick = {
                                    if (goChat) {
                                        getPages.setGoChat(false)
                                    } else if (goToFollowersFollowing.first) {
                                        profileViewModel.toggleGoToFollowersFollowing("")
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.gobackicon),
                                    contentDescription = "GoHome",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        } else if (!goChat && !profileVisited) {
                            Row(
                                modifier = Modifier.offset(x = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                IconButton(
                                    onClick = {
                                        showFilter = true
                                    },

                                    modifier = Modifier
                                        .size(40.dp)
                                )
                                {
                                    Image(
                                        painter = painterResource(id = R.drawable.fromgpt),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                if (adviceFromAIPref) {
                                    Column(
                                        modifier = Modifier.width(200.dp)
                                            .clip(RoundedCornerShape(16.dp))

                                            .clickable {
                                                showtext = true
                                                getPages.setShowHomeTips(true)
                                            }
                                    ) {
                                        Text(
                                            style = MaterialTheme.typography.bodyMedium,
                                            text = if (displayedtext.contains("Error:")) greetings_catch else displayedtext,
                                            textAlign = TextAlign.Center,
                                            maxLines = 1,  // Limite à une seule ligne
                                            overflow = TextOverflow.Ellipsis, // Tronque avec des "...",
                                            modifier = Modifier.align(Alignment.Start)
                                                .padding(8.dp)

                                        )
                                    }
                                }
                            }
                            val filter by getPages.filter.collectAsState()
                            if (showFilter) {
                                DropdownMenu(
                                    shape = RoundedCornerShape(8.dp),
                                    shadowElevation = 0.dp,
                                    tonalElevation = 0.dp,
                                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                                    expanded = showFilter,
                                    onDismissRequest = { showFilter = false }
                                ) {


                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                stringResource(id = R.string.texte),
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = if (filter == "text") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                                )
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.texticon),
                                                contentDescription = "Filter text",
                                                modifier = Modifier.size(20.dp),
                                                tint = if (filter == "text") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground

                                            )
                                        },
                                        onClick = {
                                            getPages.setFilter("text")
                                            showFilter = false
                                            getPages.refreshData()

                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                stringResource(id = R.string.images),
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = if (filter == "image") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                                )
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.imageicon),
                                                contentDescription = "Filter image",
                                                modifier = Modifier.size(20.dp),
                                                tint = if (filter == "image") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground

                                            )
                                        },
                                        onClick = {
                                            getPages.setFilter("image")
                                            showFilter = false
                                            getPages.refreshData()
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                stringResource(id = R.string.videos),
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = if (filter == "video") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                                )
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.videoicon),
                                                contentDescription = "Filter video",
                                                modifier = Modifier.size(20.dp),
                                                tint = if (filter == "video") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                            )
                                        },
                                        onClick = {
                                            getPages.setFilter("video")
                                            showFilter = false
                                            getPages.refreshData()
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                stringResource(id = R.string.nofilter),
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = if (filter == "None") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                                )
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.nofiltericon),
                                                contentDescription = "No filter",
                                                modifier = Modifier.size(20.dp),
                                                tint = if (filter == "None") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground

                                            )
                                        },
                                        onClick = {
                                            getPages.setFilter("None")
                                            showFilter = false
                                            getPages.refreshData()
                                        }
                                    )

                                }
                            }
                        }
                    }

                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                )

        }
        if (showtext) {
            BasicAlertDialog(
                onDismissRequest = {
                    showtext = false
                    getPages.setShowHomeTips(false)

                },
                properties = DialogProperties(usePlatformDefaultWidth = false),
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .widthIn(200.dp, 300.dp)
                    .background(
                        MaterialTheme.colorScheme.background
                    )
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (!loading) {
                            Icon(
                                painter = painterResource(id = R.drawable.advicefromaiicon),
                                contentDescription = "Close",
                                modifier = Modifier.size(50.dp).align(Alignment.CenterHorizontally),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally).size(50.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 4.dp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        SelectionContainer {
                            Text(
                                if (displayedtext.contains("Error:")) greetings_catch else displayedtext,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().alpha(alpharefresh),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (showRefresh) {
                                IconButton(
                                    onClick = {
                                        refresh = true
                                    },
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.reprompticon),
                                        contentDescription = "Regenerate",
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        //go to creationpage
                                    },
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.hometocreation),
                                        contentDescription = "Regenerate",
                                        modifier = Modifier.size(25.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }


                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayContent(profileViewModel: ProfileViewModel, globalViewModel: GlobalViewModel, getPages: GetPages, contentData: List<String>, username: String, themeViewModel: ThemeViewModel, isPage: Boolean, navController: NavHostController) {

    val context by rememberUpdatedState(LocalContext.current)

    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    var Date = contentData.getOrNull(0) ?: "N/A"
    var UserName = contentData.getOrNull(1) ?: "N/A"
    var ContentType = contentData.getOrNull(2) ?: "N/A"
    var ContentURL = contentData.getOrNull(3) ?: "N/A"
    var AudioURL = contentData.getOrNull(4) ?: "N/A"
    var ContentPrompt = contentData.getOrNull(5) ?: "N/A"
    var AIContentModel = contentData.getOrNull(6) ?: "N/A"
    var AIAudioModel = contentData.getOrNull(7) ?: "N/A"
    var Caption = contentData.getOrNull(8) ?: "N/A"




    val pagerState = rememberPagerState(1, 0f, { 3 })




    val goChat by getPages.goChat.collectAsState()
    LaunchedEffect(goChat) {
        if (pagerState.currentPage != 0) {
            val targetPage = if (goChat) 2 else 1
            if (pagerState.currentPage != targetPage) {
                pagerState.animateScrollToPage(
                    targetPage,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            }
        }
    }

// Écoute les changements du pagerState et met à jour goChat
    LaunchedEffect(pagerState.currentPage) {
        getPages.setGoChat(pagerState.currentPage == 2)
        getPages.setVisitedProfile(pagerState.currentPage == 0)
    }



    val profileVisited by getPages.visitProfile.collectAsState()

    var firstLaunch by remember { mutableStateOf(true) }


    LaunchedEffect(profileVisited) {
        val targetPage = if (profileVisited) 0 else 1
        if (pagerState.currentPage != targetPage) {
            pagerState.animateScrollToPage(
                targetPage,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
        }
    }

    val coroutineScope = rememberCoroutineScope()

    // Appliquer le thème
    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {

        val invisiblePages by getPages.invisiblePages.collectAsState()

        val networkAvailable by getPages.isNetworkAvailable.collectAsState()
        Log.d("networkAvailable", networkAvailable.toString())

        val RetrofitNetValue by RetrofitInstance.isNetworkAvailable.collectAsState()

        val hapticPref by globalViewModel.hapticPref.collectAsState()
        var isFirstLaunch by remember { mutableStateOf(true) }


        LaunchedEffect(Unit) {
            firstLaunch = false
            RetrofitInstance.setNetworkAvailable(true)
        }

        /*
        LaunchedEffect(RetrofitNetValue) {
            if (isFirstLaunch) {
                isFirstLaunch = false // Marque le premier lancement comme terminé
                return@LaunchedEffect
            }
            if (!RetrofitNetValue && hapticPref) {
                triggerRisingVibrationWithClick(context)
            }
        }

         */

        val goToFollowersFollowing by profileViewModel.goToFollowersFollowing.collectAsState()
        val profileVisited by getPages.visitProfile.collectAsState()

        val showSeePrompt by getPages.showSeePrompt.collectAsState()
        val showComments by getPages.showComments.collectAsState()
        val threeDotsMenu by getPages.threeDotsMenu.collectAsState()
        val sendContent by getPages.sendContent.collectAsState()

        val creationMenu by getPages.creationMenu.collectAsState()

        val activity = LocalActivity.current

        BackHandler {


            if (showSeePrompt || showComments || sendContent || threeDotsMenu) {
                getPages.setShowSeePrompt(false)
                getPages.setThreeDotsMenu(false)
                getPages.setShowComments(false)
                getPages.setSendContent(false)
            } else if (getPages.goChat.value) {
                getPages.setGoChat(false)
            } else if (goToFollowersFollowing.first && profileVisited) {
                profileViewModel.toggleGoToFollowersFollowing("")
            } else if (!goToFollowersFollowing.first && profileVisited) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            } else if (creationMenu) {
                getPages.setCreationMenu(false)
            } else if (profileVisited) {
                getPages.setVisitedProfile(false)
            }
            else {
                activity?.finishAffinity() // Ferme toutes les activités et quitte l'application
            }


        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            HorizontalPager(
                userScrollEnabled = contentData.isNotEmpty() && !goToFollowersFollowing.first && !profileVisited && !showSeePrompt && !showComments && !threeDotsMenu && !sendContent,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {

                    2 -> {
                        SocialPage(themeViewModel, username, getPages)
                    }

                    1 -> {
                        if (contentData !in invisiblePages) {

                            if (contentData.isNotEmpty()) {
                                Log.d("ContentData", contentData.toString())

                                FullScreenContent(
                                    profileViewModel,
                                    globalViewModel,
                                    username,
                                    contentData,
                                    audioUrl = RetrofitInstance.BASE_URL + "audio/$AudioURL",
                                    contentURL = RetrofitInstance.BASE_URL + "$ContentType/$ContentURL",
                                    themeViewModel,
                                    isPage,
                                    getPages,
                                    navController
                                )
                            } else {
                                if (RetrofitNetValue) {
                                    LoadingScreen()
                                } else {
                                    Box(
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.erreur_de_connexion),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = MaterialTheme.colorScheme.error
                                            ),
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    0 -> {




                        val verticalPagerState = rememberPagerState(0, 0f, { 2 })

                        val goToFollowersFollowing by profileViewModel.goToFollowersFollowing.collectAsState()



                        LaunchedEffect(goToFollowersFollowing) {
                            if (goToFollowersFollowing.first) {
                                verticalPagerState.animateScrollToPage(1)
                            } else {
                                verticalPagerState.animateScrollToPage(0)
                            }
                        }


                        VerticalPager(
                            userScrollEnabled = false,
                            state = verticalPagerState,
                            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
                        ) {
                                page ->
                            when (page) {
                                0 -> {
                                    VisitProfilePage(profileViewModel, username, getPages, themeViewModel, globalViewModel, contentData)
                                }
                                1 -> {
                                    if (goToFollowersFollowing.first) {
                                        Column(
                                            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
                                        ) {

                                            FollowersFollowingPage(
                                                themeViewModel,
                                                profileViewModel,
                                                username,
                                                contentData.getOrNull(1) ?: "N/A"
                                            )
                                        }
                                    } else {
                                        //parler à l'assistant

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

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun VisitProfilePage(profileViewModel: ProfileViewModel, username: String, getPages: GetPages, themeViewModel: ThemeViewModel, globalViewModel: GlobalViewModel, contentData: List<String>) {

    val usernamevisited = contentData.getOrNull(1) ?: "N/A"

    val context = LocalContext.current // Utilisation de LocalContext pour obtenir le contexte

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()


    val coroutineScope = rememberCoroutineScope()




    var initProfilePic : Bitmap? by remember { mutableStateOf(null) }
    var profilePic by remember {
        mutableStateOf(initProfilePic)
    }



    var lazyState = rememberLazyListState()

    val profileLazy by profileViewModel.profileLazy.collectAsState() //??

    var bio by remember { mutableStateOf("") }

    var toggleBio by remember { mutableStateOf(false) }
    var isOverflow by remember { mutableStateOf(false) }


    LaunchedEffect(profileLazy) {
        if (profileLazy) {
            lazyState.animateScrollToItem(0)
            profileViewModel.setProfileLazy(false)
        }
    }


    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)

    var userPages by remember { mutableStateOf(listOf<List<String>>()) }

    val nb_tabs = 1 //userposts, savedposts...

    val horizontalPagerState = rememberPagerState(0, 0f, { 1 })
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp


    var savedPages by remember { mutableStateOf(listOf<List<String>>()) }


    val text = stringResource(id = R.string.aucun_contenu)
    var placeHolder by remember { mutableStateOf("") }


    var name by remember { mutableStateOf("") }

    var following by remember { mutableStateOf(listOf<String>()) }
    var followers by remember { mutableStateOf(listOf<String>()) }

    val collectFollowing by profileViewModel.following.collectAsState()
    val collectFollowers by profileViewModel.followers.collectAsState()


    val editProfile by profileViewModel.editProfile.collectAsState()

    val goToFollowersFollowing by profileViewModel.goToFollowersFollowing.collectAsState()

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("VisitProfilePage", "Erreur coroutine attrapée : ${throwable.message}")
    }


    var loading by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(300)
            placeHolder = text
        }

        coroutineScope.launch(exceptionHandler) {
            try {
                getPages.setUsernamevisited(usernamevisited)

                name = RetrofitInstance.api.get_user_data(SQL().getUserData(
                    usernamevisited,
                    "customname"
                ))
                val firstname = RetrofitInstance.api.get_user_data(SQL().getuserfisrtname(usernamevisited))
                val lastname = RetrofitInstance.api.get_user_data(SQL().getuserlastname(usernamevisited))

                if (name == "NULL") {
                    name = "$firstname $lastname"
                }

                bio = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "biography"))
                if (bio == "NULL") {
                    bio = ""
                }

                val gson = Gson()
                var followingjson: String
                followingjson = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "followings"))

                if (followingjson != "NULL") {
                    val type = object : TypeToken<List<String>>() {}.type
                    following = gson.fromJson(followingjson, type)
                } else {
                    following = listOf()
                }

                var followersJson: String
                followersJson = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "followers"))
                if (followersJson != "NULL") {
                    val type = object : TypeToken<List<String>>() {}.type
                    followers = gson.fromJson(followersJson, type)
                } else {
                    followers = listOf()
                }

                var userjson: String
                userjson = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "userposts"))

                if (userjson != "NULL") {
                    val type = object : TypeToken<List<List<String>>>() {}.type
                    userPages = gson.fromJson(userjson, type)
                }


                var savedjson: String
                savedjson = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "savedposts"))

                if (savedjson != "NULL") {
                    val type = object : TypeToken<List<List<String>>>() {}.type
                    savedPages = gson.fromJson(savedjson, type)

                }






                initProfilePic = getProfileImage(prefs, usernamevisited)
                profilePic = initProfilePic

                loading = false

            } catch (e: Exception) {
                Log.d("VisitProfilePage", "Erreur : $e")
                RetrofitInstance.setNetworkAvailable(false)
            }

        }




    }
    val followedstring = stringResource(id = R.string.followed)
    val unfollowedstring = stringResource(id = R.string.unfollowed)

    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {



        if (loading) {
            Box(modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
            ){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize().alpha(if (editProfile || goToFollowersFollowing.first) 0f else 1f)
                    .background(MaterialTheme.colorScheme.background),
                state = lazyState,

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,

                ) {

                item {
                    Column(
                        modifier = Modifier


                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(0.9f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)

                                        .padding(bottom = 8.dp)
                                        .shadow(
                                            elevation = 1.dp,
                                            shape = RoundedCornerShape(8.dp),
                                            clip = false
                                        )
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable(
                                            onClick = {
                                                //parler à l'assistant
                                            },
                                            enabled = true,
                                            role = Role.Button,
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = LocalIndication.current
                                        )
                                        .background(MaterialTheme.colorScheme.surface)

                                ) {
                                    Text(
                                        text = stringResource(id = R.string.speakto) + " " + "Meta Llama 3.1",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onBackground
                                        ),
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(10.dp)

                                    )
                                }
                                if (username != usernamevisited) {
                                    val inFollowing = collectFollowing.contains(usernamevisited)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .padding(bottom = 8.dp)
                                            .shadow(
                                                elevation = 1.dp,
                                                shape = RoundedCornerShape(8.dp),
                                                clip = false
                                            )
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable(
                                                onClick = {
                                                    if (!inFollowing) {
                                                        profileViewModel.follow(usernamevisited)
                                                    } else {
                                                        profileViewModel.unfollow(usernamevisited)
                                                    }
                                                    showFloatingToast(
                                                        context,
                                                        (if (inFollowing) unfollowedstring else followedstring) + " " + usernamevisited
                                                    )

                                                },
                                                enabled = true,
                                                role = Role.Button,
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = LocalIndication.current
                                            )
                                            .background(MaterialTheme.colorScheme.surface)

                                    ) {
                                        Text(
                                            text = if (!inFollowing) stringResource(id = R.string.follow) else stringResource(
                                                id = R.string.unfollow
                                            ),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = MaterialTheme.colorScheme.onBackground
                                            ),
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .padding(10.dp)

                                        )
                                    }
                                }
                            }


                            Row(
                                modifier = Modifier.fillMaxWidth(0.8f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    ),
                                )
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.width(IntrinsicSize.Max).clip(RoundedCornerShape(8.dp))

                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = LocalIndication.current,
                                                onClick = {
                                                    coroutineScope.launch {
                                                        lazyState.animateScrollToItem(1)
                                                        horizontalPagerState.animateScrollToPage(
                                                            0
                                                        )
                                                    }
                                                }
                                            ).padding(8.dp),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = userPages.size.toString(),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.posts),
                                            style = MaterialTheme.typography.bodySmall
                                        )

                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = LocalIndication.current,
                                                onClick = {
                                                    profileViewModel.toggleGoToFollowersFollowing("followers")
                                                }
                                            ).padding(8.dp),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = followers.size.toString(),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.followers),
                                            style = MaterialTheme.typography.bodySmall
                                        )

                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = LocalIndication.current,
                                                onClick = {
                                                    profileViewModel.toggleGoToFollowersFollowing("following")

                                                }
                                            ).padding(8.dp),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = following.size.toString(),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.following),
                                            style = MaterialTheme.typography.bodySmall
                                        )

                                    }
                                }

                                if (profilePic != null) {


                                    Image(
                                        bitmap = profilePic!!.asImageBitmap(),
                                        contentDescription = "Photo de profil",
                                        modifier = Modifier
                                            .size(90.dp)
                                            .clip(CircleShape)
                                        /*.border(
                                            2.dp,
                                            MaterialTheme.colorScheme.onBackground,
                                            CircleShape
                                        )*/,
                                        contentScale = ContentScale.Crop
                                    )

                                } else {


                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "Photo de profil par défaut",
                                        modifier = Modifier.size(90.dp),
                                        tint = MaterialTheme.colorScheme.onBackground

                                    )
                                }


                            }

                            var nbLines by remember { mutableStateOf(0) }
                            Row(
                                modifier = Modifier.fillMaxWidth(0.9f).padding(bottom = 8.dp)
                                    .wrapContentHeight()
                                    /*
                                .animateContentSize(
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = FastOutSlowInEasing
                                    )
                                )

                                 */
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = {
                                            toggleBio = !toggleBio
                                        }
                                    ),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    overflow = TextOverflow.Ellipsis,
                                    onTextLayout = { textLayoutResult ->
                                        isOverflow = textLayoutResult.didOverflowHeight
                                        nbLines = textLayoutResult.lineCount
                                    },
                                    modifier = Modifier
                                        .padding(top = 20.dp),
                                    text = bio.replace(Regex("\n\n+"), "\n"),
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = if (toggleBio) Int.MAX_VALUE else 3
                                )


                                if (isOverflow || toggleBio && nbLines > 3) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        modifier = Modifier.padding(top = 4.dp)
                                            .align(Alignment.Bottom),
                                        text = if (!toggleBio) stringResource(id = R.string.more) else stringResource(
                                            id = R.string.less
                                        ),
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = 0.5f
                                            )
                                        )
                                    )
                                }
                            }


                        }
                    }


                }
                stickyHeader {
                    Surface(
                        modifier = Modifier


                            /*
                        .onSizeChanged { size ->
                            surfaceHeight = size.height // Hauteur en pixels
                        }

                         */

                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.background.copy(0.8f),
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f).fillMaxWidth(0.5f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center


                                ) {
                                    NoRippleIconButton(
                                        modifier = Modifier,
                                        onClick = {
                                            coroutineScope.launch {
                                                horizontalPagerState.animateScrollToPage(0)
                                            }
                                        },
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .size(25.dp),
                                            //.graphicsLayer(rotationZ = 90f),
                                            painter = painterResource(id = R.drawable.contenticon),
                                            contentDescription = "Posts",
                                            tint = if (horizontalPagerState.currentPage == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                        )

                                    }

                                }
                                /*
                                Column(
                                    modifier = Modifier.weight(1f).fillMaxWidth(0.5f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    NoRippleIconButton(
                                        onClick = {
                                            coroutineScope.launch {
                                                horizontalPagerState.animateScrollToPage(1)
                                            }
                                        },
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(25.dp),
                                            painter = painterResource(id = R.drawable.savedcontent),
                                            contentDescription = "Saved Posts",
                                            tint = if (horizontalPagerState.currentPage == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                        )

                                    }


                                }

                                 */
                            }
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.onBackground,
                                thickness = 3.dp,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .offset {
                                        val offsetValue =
                                            (horizontalPagerState.currentPage + horizontalPagerState.currentPageOffsetFraction) * 1.5 * screenWidth.value //un peu au pif
                                        IntOffset(
                                            offsetValue.toInt(),
                                            0
                                        )
                                    }
                                    .fillMaxWidth((1/nb_tabs).toFloat())
                                    .padding(top = 5.dp)
                                    .height(3.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )


                        }

                    }
                }
                item {

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalPager(
                        modifier = Modifier
                            .fillParentMaxHeight()
                            .fillMaxWidth(),
                        state = horizontalPagerState,
                        userScrollEnabled = true,
                        verticalAlignment = Alignment.Top,
                    ) { page ->
                        when (page) {
                            0 -> { //userContent
                                if (userPages.isEmpty()) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = placeHolder,
                                            modifier = Modifier.padding(50.dp).offset(y = -150.dp),
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                color = MaterialTheme.colorScheme.onBackground.copy(
                                                    0.5f
                                                )
                                            ),
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                } else {
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                                        verticalArrangement = Arrangement.spacedBy(1.dp),

                                        maxItemsInEachRow = 3
                                    ) {
                                        userPages?.forEach { content ->
                                            //var Date = content.getOrNull(0) ?: "N/A"
                                            var UserName = content.getOrNull(0) ?: "N/A"
                                            var ContentType = content.getOrNull(1) ?: "N/A"
                                            var ContentURL = content.getOrNull(2)
                                                ?: "N/A" //URL si video ou image et contenu du texte si text
                                            var AudioURL = content.getOrNull(3) ?: "N/A"
                                            var ContentPrompt = content.getOrNull(4) ?: "N/A"
                                            var AIContentModel = content.getOrNull(5) ?: "N/A"
                                            var AIAudioModel = content.getOrNull(6) ?: "N/A"
                                            var Caption = content.getOrNull(7) ?: "N/A"

                                            Box(
                                                modifier = Modifier

                                                    .width((screenWidth-2.dp)/3)
                                                    .aspectRatio(9f / 16f) // Aspect ratio 9:16 pour chaque "vidéo"
                                                    //.padding(2.dp)
                                                    /*.border(
                                                        1.dp,
                                                        MaterialTheme.colorScheme.onBackground.copy(
                                                            0.05f
                                                        ),
                                                        RoundedCornerShape(8.dp)
                                                    )*/
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .clickable {
                                                        //focus sur le post, possibilité de scroller dans les autres posts, comme insta
                                                    }
                                                    .background(MaterialTheme.colorScheme.surface)
                                            ) {

                                                if (ContentType == "image") {

                                                    SubcomposeAsyncImage(
                                                        filterQuality = FilterQuality.Low,
                                                        model = RetrofitInstance.BASE_URL + "$ContentType/$ContentURL",
                                                        contentDescription = "Image",
                                                        loading = {
                                                            val shimmerColors = listOf(
                                                                Color.White,
                                                                Color.White.copy(alpha = 0.6f)
                                                            )


                                                            val transition =
                                                                rememberInfiniteTransition()
                                                            val translateAnim =
                                                                transition.animateFloat(
                                                                    initialValue = -800f,
                                                                    targetValue = 1500f,
                                                                    animationSpec = infiniteRepeatable(
                                                                        animation = tween(
                                                                            durationMillis = 1000,
                                                                            easing = LinearEasing
                                                                        ),
                                                                        repeatMode = RepeatMode.Reverse
                                                                    )
                                                                )

                                                            val brush = Brush.linearGradient(
                                                                colors = shimmerColors,
                                                                start = Offset(
                                                                    translateAnim.value,
                                                                    translateAnim.value
                                                                ),
                                                                end = Offset(
                                                                    translateAnim.value + 500f,
                                                                    translateAnim.value + 500f
                                                                )
                                                            )

                                                            Box(
                                                                modifier = Modifier
                                                                    .fillMaxSize()
                                                                    .background(brush = brush)
                                                            )
                                                        }, // Indicateur de chargement
                                                        error = {

                                                            Box(
                                                                modifier = Modifier.fillMaxSize()
                                                                    .background(MaterialTheme.colorScheme.onBackground)

                                                            ) {
                                                                Icon(
                                                                    modifier = Modifier.size(25.dp)
                                                                        .align(Alignment.Center),
                                                                    painter = painterResource(id = R.drawable.erroricon),
                                                                    tint = MaterialTheme.colorScheme.error,
                                                                    contentDescription = "Error"
                                                                )
                                                            }
                                                        }, // Message d'erreur
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier.fillMaxSize()
                                                    )
                                                } else if (ContentType == "text") {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .align(Alignment.Center)
                                                    ) {
                                                        Text(
                                                            text = ContentURL,
                                                            overflow = TextOverflow.Ellipsis,
                                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                            ),
                                                            modifier = Modifier.align(Alignment.Center)
                                                                .padding(16.dp)
                                                        )
                                                    }
                                                }


                                            }
                                        }
                                    }

                                }
                            }

                            /*1 -> { //savedContent, pas visible ici
                                if (savedPages.isEmpty()) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = placeHolder,
                                            modifier = Modifier.padding(50.dp).offset(y = -150.dp),
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                color = MaterialTheme.colorScheme.onBackground.copy(
                                                    0.5f
                                                )
                                            ),
                                            textAlign = TextAlign.Center
                                        )
                                    }


                                } else {
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                                        verticalArrangement = Arrangement.spacedBy(1.dp),

                                        maxItemsInEachRow = 3
                                    ) {
                                        savedPages?.forEach { content ->
                                            //var Date = content.getOrNull(0) ?: "N/A"
                                            var UserName = content.getOrNull(0) ?: "N/A"
                                            var ContentType = content.getOrNull(1) ?: "N/A"
                                            var ContentURL = content.getOrNull(2)
                                                ?: "N/A" //URL si video ou image et contenu du texte si text
                                            var AudioURL = content.getOrNull(3) ?: "N/A"
                                            var ContentPrompt = content.getOrNull(4) ?: "N/A"
                                            var AIContentModel = content.getOrNull(5) ?: "N/A"
                                            var AIAudioModel = content.getOrNull(6) ?: "N/A"
                                            var Caption = content.getOrNull(7) ?: "N/A"

                                            Box(
                                                modifier = Modifier

                                                    .width((screenWidth-2.dp)/3)
                                                    .aspectRatio(9f / 16f) // Aspect ratio 9:16 pour chaque "vidéo"
                                                    //.padding(2.dp)
                                                    /*.border(
                                                        1.dp,
                                                        MaterialTheme.colorScheme.onBackground.copy(
                                                            0.05f
                                                        ),
                                                        RoundedCornerShape(8.dp)
                                                    )*/
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .clickable {
                                                        //focus sur le post, possibilité de scroller dans les autres posts, comme insta
                                                    }
                                                    .background(MaterialTheme.colorScheme.surface)
                                            ) {

                                                if (ContentType == "image") {

                                                    SubcomposeAsyncImage(
                                                        filterQuality = FilterQuality.Low,
                                                        model = RetrofitInstance.BASE_URL + "$ContentType/$ContentURL",
                                                        contentDescription = "Image",
                                                        loading = {
                                                            val shimmerColors = listOf(
                                                                Color.White,
                                                                Color.White.copy(alpha = 0.6f)
                                                            )


                                                            val transition =
                                                                rememberInfiniteTransition()
                                                            val translateAnim =
                                                                transition.animateFloat(
                                                                    initialValue = -800f,
                                                                    targetValue = 1500f,
                                                                    animationSpec = infiniteRepeatable(
                                                                        animation = tween(
                                                                            durationMillis = 1000,
                                                                            easing = LinearEasing
                                                                        ),
                                                                        repeatMode = RepeatMode.Reverse
                                                                    )
                                                                )

                                                            val brush = Brush.linearGradient(
                                                                colors = shimmerColors,
                                                                start = Offset(
                                                                    translateAnim.value,
                                                                    translateAnim.value
                                                                ),
                                                                end = Offset(
                                                                    translateAnim.value + 500f,
                                                                    translateAnim.value + 500f
                                                                )
                                                            )

                                                            Box(
                                                                modifier = Modifier
                                                                    .fillMaxSize()
                                                                    .background(brush = brush)
                                                            )
                                                        }, // Indicateur de chargement
                                                        error = {

                                                            Box(
                                                                modifier = Modifier.fillMaxSize()
                                                                    .background(MaterialTheme.colorScheme.onBackground)

                                                            ) {
                                                                Icon(
                                                                    modifier = Modifier.size(25.dp)
                                                                        .align(Alignment.Center),
                                                                    painter = painterResource(id = R.drawable.erroricon),
                                                                    tint = MaterialTheme.colorScheme.error,
                                                                    contentDescription = "Error"
                                                                )
                                                            }
                                                        }, // Message d'erreur
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier.fillMaxSize()
                                                    )
                                                } else if (ContentType == "text") {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .align(Alignment.Center)
                                                    ) {
                                                        Text(
                                                            text = ContentURL,
                                                            overflow = TextOverflow.Ellipsis,
                                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                            ),
                                                            modifier = Modifier.align(Alignment.Center)
                                                                .padding(16.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }*/

                        }
                    }

                }

            }
        }

    }
}


@Composable
fun SocialPage(themeViewModel: ThemeViewModel, username: String, getPages: GetPages) {



    LaunchedEffect(Unit) {
        themeViewModel.toggleReelSysBar(false)
    }

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}




@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullScreenContent(profileViewModel: ProfileViewModel, globalViewModel: GlobalViewModel, username: String, contentData: List<String>, audioUrl: String, contentURL: String, themeViewModel: ThemeViewModel, isPage: Boolean, getPages: GetPages,navController: NavHostController) {
    val context = LocalContext.current
    val audioFocusManager = remember { AudioFocusManager(context) }

    val HapticPref by globalViewModel.hapticPref.collectAsState()

    var Date = contentData.getOrNull(0) ?: "N/A"
    var UserName = contentData.getOrNull(1) ?: "N/A"
    var ContentType = contentData.getOrNull(2) ?: "N/A"
    var ContentURL = contentData.getOrNull(3) ?: "N/A" //URL si video ou image et contenu du texte si text
    var AudioURL = contentData.getOrNull(4) ?: "N/A"
    var ContentPrompt = contentData.getOrNull(5) ?: "N/A"
    var AIContentModel = contentData.getOrNull(6) ?: "N/A"
    var AIAudioModel = contentData.getOrNull(7) ?: "N/A"
    var Caption = contentData.getOrNull(8) ?: "N/A"



    val following by profileViewModel.following.collectAsState()
    val inFollowing = following.contains(UserName)

    val savedPosts by profileViewModel.savedPages.collectAsState()
    val isSaved = savedPosts.any { it[1] == ContentType && it[2] == ContentURL }

    val threeDotsItems = listOf(
        listOf(
            if (isSaved) painterResource(id = R.drawable.savedcontent) else painterResource(id = R.drawable.savecontenticon),
            if (isSaved) stringResource(id = R.string.saved) else stringResource(id = R.string.save),
            {
                if (!isSaved) {
                    profileViewModel.addToSavedPages(
                        listOf(
                            UserName,
                            ContentType,
                            ContentURL,
                            AudioURL,
                            ContentPrompt,
                            AIContentModel,
                            AIAudioModel,
                            Caption
                        )
                    )
                } else {
                    profileViewModel.removeFromSavedPages(
                        listOf(
                            UserName,
                            ContentType,
                            ContentURL,
                            AudioURL,
                            ContentPrompt,
                            AIContentModel,
                            AIAudioModel,
                            Caption
                        )
                    )
                }
            }
        )
    )



    /*
    getCampaigns.loadMorePages()


    val id = getCampaigns.pages.collectAsState().value.indexOfFirst { page ->
        page.any { subItem -> subItem == AssociatedCampaign }
    }

    val campaignpage = if (id != -1) getCampaigns.pages.collectAsState().value[id] else listOf()

     */




    /*
    val loadControl = DefaultLoadControl.Builder()
        .setBufferDurationsMs(
            5000, // Buffer min (5s)
            10000, // Buffer max (10s)
            500, // Playback min
            500  // Playback rebuffer
        )
        .build()

    val exoPlayer = ExoPlayer.Builder(context)
        .setLoadControl(loadControl)
        .build()
    exoPlayer.setMediaItem(MediaItem.fromUri(videoUrl))
    exoPlayer.prepare()

     */



    val exoPlayer = rememberExoPlayer(ContentType, context, contentURL, audioUrl)


    val navcontroller = rememberNavController()
    val currentRoute = navcontroller.currentBackStackEntryAsState().value?.destination?.route

    LaunchedEffect(currentRoute) {
        audioFocusManager.abandonAudioFocus()
    }



    LaunchedEffect(exoPlayer) {
        // Demander le focus audio avant de commencer
        exoPlayer.playWhenReady = true

        /*
        if (audioFocusManager.requestAudioFocus()) {

            exoPlayer.playWhenReady = true

        }

         */
    }

    val vibrator = context.getSystemService(Vibrator::class.java)


    val prefs = LocalContext.current.getSharedPreferences(username, Context.MODE_PRIVATE)
    val prefsglobal = LocalContext.current.applicationContext.getSharedPreferences(username, Context.MODE_PRIVATE)
    val muted = prefsglobal.getBoolean("muted_$username", false)

    var isMuted by remember { mutableStateOf(muted) }

    LaunchedEffect(isMuted) {
        if (isMuted) {
            audioFocusManager.abandonAudioFocus()
            exoPlayer.volume = 0f
        } else {
            audioFocusManager.requestAudioFocus()
            exoPlayer.volume = 1f
        }
        prefsglobal.edit().putBoolean("muted_$username", isMuted).apply()
        Log.d("launchedeffect muted", isMuted.toString())
    }

    val creationMenu by getPages.creationMenu.collectAsState()

    var progress by remember { mutableFloatStateOf(0f) }
    val screenWidth = LocalDensity.current.density * LocalConfiguration.current.screenWidthDp // Largeur de l'écran

    var drag by remember { mutableStateOf(false) }
    val isDarkTheme = themeViewModel.isDarkTheme.collectAsState().value
    var isVisible by remember { mutableStateOf(true) }

    var pause by remember { mutableStateOf(false) }
    var play by remember { mutableStateOf(false) }

    val alphaplay by animateFloatAsState(
        targetValue = if (play) 0f else 1f,
        animationSpec = tween(durationMillis = 1000)
    )

    val alphapause by animateFloatAsState(
        targetValue = if (pause) 0f else 1f,
        animationSpec = tween(durationMillis = 1000)
    )

    var off by remember { mutableStateOf(false) }
    var on by remember { mutableStateOf(false) }

    val alphaon by animateFloatAsState(
        targetValue = if (on) 0f else 1f,
        animationSpec = tween(durationMillis = 1000)
    )

    val alphaoff by animateFloatAsState(
        targetValue = if (off) 0f else 1f,
        animationSpec = tween(durationMillis = 1000)
    )

    var initProfilePic : Bitmap? by remember { mutableStateOf(null) }
    var profilePic by remember {
        mutableStateOf(initProfilePic)
    }
    val coroutineScope = rememberCoroutineScope()




    val showHomeTips by getPages.showHomeTips.collectAsState()

    LaunchedEffect(showHomeTips) {
        if (isPage) {
            if (showHomeTips) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
        }
    }

    LaunchedEffect(isPage) {
        if (isPage) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }


    val isFirstLaunch by getPages.isFirstLaunch.collectAsState()

    var displayedText by remember { mutableStateOf("") }

    var skipstream by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {

        if (isFirstLaunch) {
            if (isMuted) {
                audioFocusManager.requestAudioFocus()
                exoPlayer.volume = 1f
                isMuted = false


            }
            getPages.setFirstLaunch(false)
            prefsglobal.edit().putBoolean("muted_$username", isMuted).apply()
        }

        if (creationMenu) {
            exoPlayer.pause()
        }

        coroutineScope.launch {
            initProfilePic = getProfileImage(prefs, UserName)
            profilePic = initProfilePic
            Log.d("initprofile", "init profile : $initProfilePic, profilepic: $profilePic")

        }
        themeViewModel.toggleReelSysBar(true)
        if (ContentType == "text") {
            displayedText = ""
            ContentURL.forEach { char ->
                delay(30)
                if (!skipstream) {
                    displayedText += char
                }
            }
        }

    }



    DisposableEffect(Unit) {
        val lifecycleObserver = VideoLifecycleObserver(exoPlayer)
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)

        val handler = Handler(Looper.getMainLooper())
        val updateProgress = object : Runnable {
            override fun run() {
                val duration = exoPlayer.duration.coerceAtLeast(1) // éviter division par 0
                val position = exoPlayer.currentPosition
                progress = position.toFloat() / duration.toFloat()
                handler.postDelayed(this, 10) // Met à jour toutes les 500ms
            }
        }

        handler.post(updateProgress)

        onDispose {
            
            ProcessLifecycleOwner.get().lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
            audioFocusManager.abandonAudioFocus()
            handler.removeCallbacks(updateProgress)


        }
    }

    var toggleCaption by remember { mutableStateOf(false) }


    /* Fait lagguer
    val videoPath = "http://192.168.10.7:5000/video"
    val (width, height) = getVideoDimensions(videoPath)

    val aspectRatio = screenWidth / height.toFloat()

     */

    Box(modifier = Modifier

        .fillMaxSize()
    ) {
        Box(modifier = Modifier
            //.aspectRatio(9f / 16f)
            .fillMaxSize()
            .align(Alignment.Center)


        ) {
            AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {

                Box(
                    modifier = Modifier

                        .fillMaxSize().zIndex(2f),
                ) {




                    if (on) {
                        Box(
                            modifier = Modifier
                                .alpha(alphaon)
                                .align(Alignment.Center)
                                .size(45.dp) // Taille du cercle
                                .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.volume_on),
                                contentDescription = "Pause",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp).align(Alignment.Center)
                            )
                        }
                    }

                    if (off) {
                        Box(
                            modifier = Modifier
                                .alpha(alphaoff)
                                .align(Alignment.Center)

                                .size(45.dp) // Taille du cercle
                                .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.volume_off),
                                contentDescription = "Play",
                                tint = Color.White, // Teinte blanche
                                modifier = Modifier.size(30.dp)
                            )
                        }

                    }

                    if (pause) {
                        Box(
                            modifier = Modifier
                                .alpha(alphapause)
                                .align(Alignment.Center)

                                .size(45.dp) // Taille du cercle
                                .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.pauseicon),
                                contentDescription = "Pause",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp).align(Alignment.Center)
                            )
                        }
                    }

                    if (play) {
                        Box(
                            modifier = Modifier
                                .alpha(alphaplay)
                                .align(Alignment.Center)

                                .size(45.dp) // Taille du cercle
                                .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.playicon),
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp).align(Alignment.Center)
                            )
                        }
                    }

                    if (creationMenu) {
                        var clicked by remember { mutableStateOf(false) }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            NoRippleIconButton(
                                modifier = Modifier.padding(top = 8.dp).align(Alignment.TopStart),
                                onClick = {
                                    getPages.setCreationMenu(false)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.gobackicon),
                                    contentDescription = "GoHome",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(80.dp)
                                    .align(Alignment.BottomStart)
                                    .size(60.dp) // Taille du bouton circulaire
                                    .clip(CircleShape)
                                    .background(
                                        //MaterialTheme.colorScheme.primary.copy(alpha = if (!clicked) 0.3f else 1f),
                                        MaterialTheme.colorScheme.surface,
                                        shape = CircleShape
                                    ) // Couleur de fond et forme circulaire
                                    .clickable {
                                        clicked = true
                                        coroutineScope.launch {
                                            delay(300)
                                        }
                                        clicked = false
                                    }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.variationicon),
                                    contentDescription = "Variation",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(80.dp)

                                    .align(Alignment.TopEnd)
                                    .size(60.dp) // Taille du bouton circulaire
                                    .clip(CircleShape)
                                    .background(
                                        //MaterialTheme.colorScheme.primary.copy(alpha = if (!clicked) 0.3f else 1f),
                                        MaterialTheme.colorScheme.surface,
                                        shape = CircleShape
                                    ) // Couleur de fond et forme circulaire
                                    .clickable {
                                        clicked = true
                                        coroutineScope.launch {
                                            delay(300)
                                        }
                                        clicked = false


                                    }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.reprompticon),
                                    contentDescription = "RePrompt",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }


                    }


                    /* Fait lagguer et marche pas bien
                    if (drag) {
                        Box(
                            modifier = Modifier.align(Alignment.BottomCenter).offset(y = -110.dp).size(70.dp)
                        ) {
                            VideoPreview(progress, exoPlayer)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                     */




                }
            }



            AndroidView(
                factory = { ctx ->
                    val parent = FrameLayout(ctx)
                    LayoutInflater.from(ctx)
                        .inflate(R.layout.player_view, parent, false) as PlayerView
                },
                modifier = Modifier


                    /*
            .pointerInput(Unit) {
                detectHorizontalDragGestures (
                    onDragEnd = {
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        when {
                            dragAmount > 0 -> {
                                isVisible = false
                            }
                            dragAmount < 0 -> {
                                isVisible = true
                            }
                        }
                    }
                )
            }

             */

                    .then (
                        if (!creationMenu) {
                            Modifier
                                .combinedClickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    /*
                                    onLongClick = {
                                        if (HapticPref) {
                                            vibrator?.vibrate(
                                                VibrationEffect.createPredefined(
                                                    VibrationEffect.EFFECT_HEAVY_CLICK
                                                )
                                            )
                                        }
                                        if (!pause) {
                                            exoPlayer.pause()
                                        }
                                        getPages.setCreationMenu(true)
                                    },

                                     */

                                    onLongClick = {
                                        if (exoPlayer.volume == 0f) {
                                            audioFocusManager.requestAudioFocus()
                                            exoPlayer.volume = 1f
                                            isMuted = false
                                            on = true
                                            off = false
                                        } else {
                                            audioFocusManager.abandonAudioFocus()
                                            exoPlayer.volume = 0f
                                            isMuted = true
                                            on = false
                                            off = true
                                        }
                                    },

                                    onDoubleClick = {

                                    },
                                    onClick = {
                                        if (exoPlayer.isPlaying) {
                                            exoPlayer.pause()
                                            pause = true
                                            play = false
                                        } else {
                                            exoPlayer.play()
                                            play = true
                                            pause = false
                                        }
                                    }
                                )
                        } else {
                            Modifier


                        }
                    ),
                update = { playerView ->
                    playerView.player = exoPlayer
                }
            )
            if (ContentType == "image") {
                Log.d("FullScreenContent", contentURL)
                SubcomposeAsyncImage(
                    model = contentURL,
                    contentDescription = "Image",
                    loading = {
                        val shimmerColors = listOf(
                            Color.White,
                            Color.White.copy(alpha = 0.6f)
                        )


                        val transition = rememberInfiniteTransition()
                        val translateAnim = transition.animateFloat(
                            initialValue = -800f,
                            targetValue = 1500f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(
                                    durationMillis = 1000,
                                    easing = LinearEasing
                                ),
                                repeatMode = RepeatMode.Reverse
                            )
                        )

                        val brush = Brush.linearGradient(
                            colors = shimmerColors,
                            start = Offset(translateAnim.value, translateAnim.value),
                            end = Offset(translateAnim.value + 500f, translateAnim.value + 500f)
                        )

                        Box(
                            modifier = Modifier
                                .background(brush = brush)
                        )
                    }, // Indicateur de chargement
                    error = {
                        Box(
                            modifier = Modifier.fillMaxSize().background(Color.Black)

                        ) {
                            Text(
                                text = stringResource(id = R.string.erreur_de_connexion),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.error
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }, // Message d'erreur
                    contentScale = if (!creationMenu) ContentScale.Fit else ContentScale.Crop,
                    modifier = Modifier
                        /*
                        .scale(if (creationMenu) 0.5f else 1f)

                        .then (
                            if (creationMenu) {
                                Modifier.clip(CircleShape)
                                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            } else {
                                Modifier
                            }
                        )

                         */

                        .align(Alignment.Center).offset(y = /*if (!creationMenu) - 50.dp else 0.dp*/ -50.dp)

                )
                /*
                Image(
                    painter = rememberAsyncImagePainter(contentURL),
                    contentDescription = "Full screen image",
                    modifier = Modifier
                        .fillMaxSize() // Prend toute la taille de l'écran
                        .align(Alignment.Center).offset(y = - 50.dp)
                )

                 */
            }
            if (ContentType == "text") {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.TopCenter)
                        //.offset(y = /*if (!creationMenu) - 50.dp else 0.dp*/ -50.dp)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    LazyColumn(
                        modifier = Modifier

                            .then (
                                if (!creationMenu) {
                                    Modifier
                                        .combinedClickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            /*
                                            onLongClick = {
                                                if (HapticPref) {
                                                    vibrator?.vibrate(
                                                        VibrationEffect.createPredefined(
                                                            VibrationEffect.EFFECT_HEAVY_CLICK
                                                        )
                                                    )
                                                }
                                                if (!pause) {
                                                    exoPlayer.pause()
                                                }
                                                getPages.setCreationMenu(true)
                                            },

                                             */

                                            onLongClick = {
                                                if (exoPlayer.volume == 0f) {
                                                    audioFocusManager.requestAudioFocus()
                                                    exoPlayer.volume = 1f
                                                    isMuted = false
                                                    on = true
                                                    off = false
                                                } else {
                                                    audioFocusManager.abandonAudioFocus()
                                                    exoPlayer.volume = 0f
                                                    isMuted = true
                                                    on = false
                                                    off = true
                                                }
                                            },

                                            onDoubleClick = {

                                            },
                                            onClick = {
                                                if (exoPlayer.isPlaying) {
                                                    exoPlayer.pause()
                                                    pause = true
                                                    play = false
                                                } else {
                                                    exoPlayer.play()
                                                    play = true
                                                    pause = false
                                                }
                                            }
                                        )
                                } else {
                                    Modifier


                                }
                            )
                            .fillMaxWidth().height(450.dp)


                            /*
                            .scale(if (creationMenu) 0.5f else 1f)
                            .then (
                                if (creationMenu) {
                                    Modifier.clip(CircleShape)
                                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                } else {
                                    Modifier
                                }
                            )

                             */
                            //.clip(RoundedCornerShape(8.dp))
                            .background(/*Color(0xFF182121)*/ MaterialTheme.colorScheme.background)
                    ) {
                        item {
                            Text(
                                text = displayedText,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().align(Alignment.Start)
                    ) {
                        IconButton(
                            onClick = {
                                skipstream = true
                                displayedText = ContentURL
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.skip),
                                contentDescription = "Skip Streaming",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        if (displayedText == ContentURL) {
                            Spacer(modifier = Modifier.width(8.dp))
                            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            IconButton(
                                onClick = {
                                    val clip = android.content.ClipData.newPlainText("copy", displayedText)
                                    clipboardManager.setPrimaryClip(clip)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.copyicon),
                                    contentDescription = "Copy",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }




            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Place l'élément en bas à droite de l'écran
                    .padding(end = 4.dp).offset(y = -30.dp), // Ajuste le placement
                visible = isVisible,
                enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
            ) {
                if (ContentType != "text") {
                    Column(
                        modifier = Modifier
//.shadow(8.dp, shape = RoundedCornerShape(20.dp))

                            .background(
                                Color.Transparent
                            )
                            .wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        NoRippleIconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.likeicon), //ou likeiconfilled si liké
                                contentDescription = "Like",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        NoRippleIconButton(
                            onClick = {
                                getPages.setShowComments(true)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.commenticon),
                                contentDescription = "Comment",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        NoRippleIconButton(
                            onClick = {
                                getPages.setSendContent(true)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.sendcontenticon),
                                contentDescription = "Partager",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        if (true /*thread*/) {
                            NoRippleIconButton(
                                onClick = {
//accéder au thread si existant
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.threadicon),
                                    contentDescription = "Thread",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }


                        /*
                        if (AssociatedCampaign != "") {

                        val gotocampaignicon =
                        if (campaignpage[3] == "Supervised Learning") {
                        painterResource(id = R.drawable.supervisedlearningicon)
                        } else {
                        painterResource(id = R.drawable.gotocampaignicon) //normalement n'arrive jamais
                        }
                        NoRippleIconButton(
                        onClick = {
                        getPages.goCampaign(AssociatedCampaign)
                        navController.navigate("campaign") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                        }
                        }
                        ) {
                        Icon(
                        painter = gotocampaignicon,
                        contentDescription = "Campaigns",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                        )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        }

                        */

                        NoRippleIconButton(
                            onClick = {
                                getPages.setThreeDotsMenu(true)
                            },
                            modifier = Modifier

                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More",
                                tint = Color.White,
                                modifier = Modifier
                                    /*
                                    .pointerInput(Unit) {
                                    detectTapGestures(
                                    onDoubleTap = {
                                    if (exoPlayer.volume == 0f) {
                                    audioFocusManager.requestAudioFocus()
                                    exoPlayer.volume = 1f
                                    isMuted = false
                                    on = true
                                    off = false
                                    } else {
                                    audioFocusManager.abandonAudioFocus()
                                    exoPlayer.volume = 0f
                                    isMuted = true
                                    on = false
                                    off = true
                                    }
                                    },
                                    onTap = {
                                    threeDotsMenu = true
                                    }

                                    )
                                    }

                                    */
                                    .size(35.dp)
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
//.shadow(8.dp, shape = RoundedCornerShape(20.dp))

                            .background(
                                Color.Transparent
                            )
                            .wrapContentSize(),
                        horizontalAlignment = Alignment.End
                    ) {

                        Row(
                            modifier = Modifier

                                .background(
                                    Color.Transparent
                                )
                                .wrapContentSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            NoRippleIconButton(
                                onClick = {

                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.likeicon), //ou likeiconfilled si liké
                                    contentDescription = "Like",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            NoRippleIconButton(
                                onClick = {
                                    getPages.setShowComments(true)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.commenticon),
                                    contentDescription = "Comment",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            NoRippleIconButton(
                                onClick = {
                                    getPages.setSendContent(true)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.sendcontenticon),
                                    contentDescription = "Partager",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
//.shadow(8.dp, shape = RoundedCornerShape(20.dp))

                                .background(
                                    Color.Transparent
                                )
                                .wrapContentSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (true /*thread*/) {
                                NoRippleIconButton(
                                    onClick = {
//accéder au thread si existant
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.threadicon),
                                        contentDescription = "Thread",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            NoRippleIconButton(
                                onClick = {
                                    getPages.setThreeDotsMenu(true)
                                },
                                modifier = Modifier

                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "More",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(35.dp)
                                )
                            }
                        }
                    }

                }
            }







            val showSeePrompt by getPages.showSeePrompt.collectAsState()
            val threeDotsMenu by getPages.threeDotsMenu.collectAsState()
            val showComments by getPages.showComments.collectAsState()
            val sendContent by getPages.sendContent.collectAsState()

            val offsetY = remember { Animatable(0f) }
            var inDrag by remember { mutableStateOf(false) }



            val alphaBackground by animateFloatAsState(
                targetValue = if ((showSeePrompt || toggleCaption || showComments || sendContent || threeDotsMenu) && ContentType == "text") 0.8f else if ((showSeePrompt || toggleCaption || showComments || sendContent || threeDotsMenu) && ContentType != "text") 0.5f else 0f,
                animationSpec = tween(durationMillis = 500)
            )
            Box(modifier = Modifier
                .then(
                    if ((showSeePrompt || toggleCaption || showComments || sendContent || threeDotsMenu)) Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                toggleCaption = false
                                getPages.setShowSeePrompt(false)
                                getPages.setThreeDotsMenu(false)
                                getPages.setShowComments(false)
                                getPages.setSendContent(false)
                            }
                        )
                        .pointerInput(Unit) {
                            var dragDirection = 0f
                            detectVerticalDragGestures(
                                onVerticalDrag = { change, dragAmount ->
                                    change.consume()
                                    inDrag = true
                                    dragDirection = dragAmount

                                    coroutineScope.launch {
                                        val newOffset = offsetY.value + dragAmount
                                        offsetY.snapTo(max(newOffset, 0f))
                                    }
                                },
                                onDragEnd = {
                                    coroutineScope.launch {
                                        inDrag = false
                                        if (offsetY.value > 100 && dragDirection > 0) {
                                            offsetY.animateTo(5000f, animationSpec = tween(300))
                                            getPages.setShowSeePrompt(false)
                                            getPages.setThreeDotsMenu(false)
                                            getPages.setShowComments(false)
                                            getPages.setSendContent(false)
                                            delay(300)
                                            offsetY.snapTo(0f)
                                        } else {
                                            offsetY.animateTo(0f, animationSpec = tween(300))
                                        }
                                    }
                                }
                            )
                        }


                    else Modifier
                )


                .fillMaxSize()
                .background(Color.Black.copy(alpha = alphaBackground))
            )



            val logoclair = painterResource(id = R.drawable.aletheialogoclair)
            val logosombre = painterResource(id = R.drawable.aletheialogosombre)
            val logo = if (isDarkTheme) logosombre else logoclair

            /*
            NoRippleIconButton(
                modifier = Modifier
                    .size(100.dp)
                    .offset(y = 25.dp)
                    .background(Color.Transparent)
                    .align(Alignment.TopEnd),
                onClick = {

                }
            ) {
                Image(
                    painter = logoclair,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

             */

            if (!creationMenu) {
                AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 8.dp).offset(y = -30.dp),
                    visible = isVisible,
                    enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                    exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                ) {

                    Column(
                        modifier = Modifier
                            //.shadow(8.dp, shape = RoundedCornerShape(20.dp))

                            .background(
                                Color.Transparent
                            ),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom
                    ) {




                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .align(Alignment.Bottom)

                                    .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        getPages.setVisitedProfile(true)
                                    }
                                )
                            ) {
                                if (profilePic != null) {
                                    // Image du profil
                                    Image(
                                        bitmap = profilePic!!.asImageBitmap(),
                                        contentDescription = "Photo de profil",
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape),
                                        //.border(2.dp, Color.White, CircleShape), // Optionnel : ajouter une bordure blanche
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "Photo de profil par défaut",
                                        modifier = Modifier.size(60.dp),
                                        tint = Color.White

                                    )
                                }

                                val followedstring = stringResource(id = R.string.followed)
                                val unfollowedstring = stringResource(id = R.string.unfollowed)
                                if (UserName != username) {
                                    IconButton(
                                        modifier = Modifier.size(50.dp).align(Alignment.TopEnd).offset(
                                            x = (15).dp,
                                            y = (-15).dp
                                        ),
                                        onClick = {
                                            if (!inFollowing) {
                                                profileViewModel.follow(UserName)
                                            } else {
                                                profileViewModel.unfollow(UserName)
                                            }
                                            showFloatingToast(context, (if (inFollowing) unfollowedstring else followedstring) + " " + UserName)
                                        }
                                    ) {
                                        if (!inFollowing) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.subscribe_icon),
                                                contentDescription = "Subscribe",
                                                tint = Color.Unspecified, //les deux couleurs sont définies dans le .xml
                                                modifier = Modifier
                                                    .size(30.dp)
                                            )
                                        } else {
                                            Icon(
                                                painter = painterResource(id = R.drawable.subscribed_icon),
                                                contentDescription = "Subscribed",
                                                tint = Color.Unspecified, //les deux couleurs sont définies dans le .xml
                                                modifier = Modifier
                                                    .size(30.dp)
                                            )
                                        }
                                    }
                                }
                                /*/ Logo en haut à droite comme badge
                                Image(
                                    painter = logoclair,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(50.dp)  // Ajuste la taille du logo
                                        .align(Alignment.TopEnd) // Positionne le logo en haut à droite
                                        .offset(
                                            x = (15).dp,
                                            y = (-15).dp
                                        ), // Décalage pour mieux placer le logo sur le bord
                                    contentScale = ContentScale.Crop
                                )

                             */

                            }

                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(MaterialTheme.colorScheme.primary.copy(0.3f))
                                    ) {
                                        DefilementTextuel(
                                            text = AIContentModel,
                                            maxWidth = 100.dp,
                                            modifier = Modifier.padding(3.dp)
                                        )
                                        /*
                                        Text(
                                            modifier = Modifier.padding(3.dp),
                                            color = Color.White,
                                            text = AIContentModel,
                                            style = MaterialTheme.typography.bodyLarge,
                                        )*/
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(MaterialTheme.colorScheme.primary.copy(0.3f))
                                    ) {
                                        DefilementTextuel(
                                            text = AIAudioModel,
                                            maxWidth = 100.dp,
                                            modifier = Modifier.padding(3.dp)

                                        )
                                        /*
                                        Text(
                                            modifier = Modifier.padding(3.dp),
                                            color = Color.White,
                                            text = AIAudioModel,
                                            style = MaterialTheme.typography.bodyLarge,
                                            )*/
                                    }
                                }
                                Text(
                                    modifier = Modifier.clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = {
                                            getPages.setVisitedProfile(true)
                                        }
                                    ),
                                    text = UserName,
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.pacifico_regular)),
                                        fontSize = 17.5.sp,
                                        color = Color.White
                                    ),
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Row(
                                    modifier = Modifier.padding(top = 8.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            onClick = {
                                                getPages.setShowSeePrompt(true)
                                            }
                                        ),

                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.prompticon),
                                        contentDescription = "Prompt",
                                        tint = Color.White,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    DefilementTextuel(
                                        position = "start",
                                        text = ContentPrompt,
                                    )
                                }
                            }

                        }

                        var isOverflow by remember { mutableStateOf(false) }

                        Column(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(0.8f)
                                .then (
                                    if (isOverflow || toggleCaption) {
                                        Modifier.clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            onClick = { toggleCaption = !toggleCaption }
                                        )
                                    } else {
                                        Modifier
                                    }
                                )

                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize(
                                        animationSpec = tween(
                                            durationMillis = 500,
                                            easing = FastOutSlowInEasing
                                        )
                                    ),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top // Changé à Top pour un meilleur alignement
                            ) {
                                LazyColumn(
                                    modifier = Modifier.heightIn(max = 250.dp)
                                        .weight(1f) // Pour que le texte prenne l'espace disponible


                                ) {
                                    item {
                                        Text(
                                            overflow = TextOverflow.Ellipsis,
                                            onTextLayout = { textLayoutResult ->
                                                isOverflow = textLayoutResult.didOverflowHeight
                                            },
                                            modifier = Modifier
                                                .padding(top = 4.dp),
                                            text = Caption,
                                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                            maxLines = if (toggleCaption) Int.MAX_VALUE else 1
                                        )
                                    }
                                }
                                if (isOverflow || toggleCaption) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        modifier = Modifier.padding(top = 4.dp)
                                            .align(Alignment.Bottom),
                                        text = if (!toggleCaption) stringResource(id = R.string.more) else stringResource(
                                            id = R.string.less
                                        ),
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Color.White.copy(
                                                alpha = 0.5f
                                            )
                                        )
                                    )
                                }
                            }
                        }


                    }
                }



                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)

                        .height(25.dp)
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
// Calculer la progression basée sur la position horizontale du doigt
                                progress =
                                    (offset.x / screenWidth) // Assure que la progression reste entre 0 et 1
                                val newPosition = (progress * exoPlayer.duration).toLong()
                                exoPlayer.seekTo(newPosition)
                            }
                        }
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    progress =
                                        (offset.x / screenWidth) // Assure que la progression reste entre 0 et 1
                                    val newPosition =
                                        (progress * exoPlayer.duration).toLong()
                                    exoPlayer.seekTo(newPosition)
                                },
                                onDrag = { _, dragAmount ->
                                    drag = true

// Calculer la nouvelle position horizontale du doigt
                                    val newProgress =
                                        (progress + dragAmount.x / screenWidth).coerceIn(
                                            0f,
                                            1f
                                        )
                                    progress = newProgress

// Calculer la nouvelle position de la vidéo
                                    val newPosition =
                                        (newProgress * exoPlayer.duration).toLong()

// Déplacer la vidéo à la nouvelle position
                                    exoPlayer.seekTo(newPosition)

                                }
                            )
                        },

                    ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter).offset(y = -10.dp)
                            .height(5.dp),
                        trackColor = Color.Transparent,
                        color = MaterialTheme.colorScheme.primary,
                        strokeCap = StrokeCap.Butt,
                        drawStopIndicator = {
                            drawStopIndicator(
                                drawScope = this,
                                stopSize = 0.dp,
                                color = Color.Transparent,
                                strokeCap = StrokeCap.Butt
                            )
                        }
                    )
                }







                AnimatedVisibility(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {}
                        )
                        .align(Alignment.BottomCenter),
                    visible = showSeePrompt,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = if (inDrag) ExitTransition.None else slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
// Add these state variables at the appropriate scope
                    var dragDirection by remember { mutableStateOf(0f) }
                    var isDraggingDown by remember { mutableStateOf(false) }

                    val nestedScrollConnection = remember {
                        object : NestedScrollConnection {
                            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
// If we're already in a downward drag and now moving upward, intercept the scroll
                                if (inDrag && isDraggingDown && available.y < 0) {
                                    coroutineScope.launch {
                                        val newOffset = offsetY.value + available.y
                                        offsetY.snapTo(max(newOffset, 0f))
                                    }
                                    return available
                                }
                                return Offset.Zero
                            }

                            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
// If there's leftover scroll and we're at the top of the list
                                if (available.y != 0f && source == NestedScrollSource.UserInput) {
                                    if (available.y > 0) {
// Dragging down
                                        isDraggingDown = true
                                        inDrag = true
                                    } else if (inDrag && isDraggingDown) {
// We're already in a downward drag, so handle upward drag here too
                                    }

                                    coroutineScope.launch {
                                        val newOffset = offsetY.value + available.y
                                        offsetY.snapTo(max(newOffset, 0f))
                                    }
                                    dragDirection = available.y
                                    return available
                                }
                                return Offset.Zero
                            }

                            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
// Handle end of scroll fling
                                inDrag = false
                                isDraggingDown = false

                                if (offsetY.value > 100) {
                                    offsetY.animateTo(5000f, animationSpec = tween(300))
                                    getPages.setShowSeePrompt(false)
                                    delay(300)
                                    offsetY.snapTo(0f)
                                } else {
                                    offsetY.animateTo(0f, animationSpec = tween(300))
                                }
                                return available
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .pointerInput(Unit) {
                                var dragDirection = 0f
                                detectVerticalDragGestures(
                                    onVerticalDrag = { change, dragAmount ->
                                        change.consume()
                                        inDrag = true
                                        dragDirection = dragAmount

                                        coroutineScope.launch {
                                            val newOffset = offsetY.value + dragAmount
                                            offsetY.snapTo(max(newOffset, 0f))
                                        }
                                    },
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            inDrag = false
                                            if (offsetY.value > 100 && dragDirection > 0) {
                                                offsetY.animateTo(5000f, animationSpec = tween(300))
                                                getPages.setShowSeePrompt(false)
                                                delay(300)
                                                offsetY.snapTo(0f)
                                            } else {
                                                offsetY.animateTo(0f, animationSpec = tween(300))
                                            }
                                        }
                                    }
                                )
                            }


                            .offset {
                                IntOffset(0, offsetY.value.roundToInt())
                            }
                            .fillMaxWidth()
                            .wrapContentHeight() // This makes the Column wrap its content height
                            .heightIn(max = 500.dp)
                            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .background(MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(modifier = Modifier
                            .width(50.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.onBackground.copy(0.5f))
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.prompt),
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(16.dp)
                            )

                        }


                        val lazyListState = rememberLazyListState()

                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .nestedScroll(nestedScrollConnection)
                                .pointerInput(Unit) {
                                    detectVerticalDragGestures(
                                        onDragStart = {
// Reset drag state at start
                                            dragDirection = 0f
                                        },
                                        onVerticalDrag = { change, dragAmount ->
// Only handle this gesture if we're at the top of the list
// or already in a downward drag
                                            if (lazyListState.firstVisibleItemIndex == 0 &&
                                                lazyListState.firstVisibleItemScrollOffset == 0 ||
                                                (inDrag && isDraggingDown)) {

                                                change.consume()
                                                inDrag = true
                                                dragDirection = dragAmount

                                                if (dragAmount > 0) {
                                                    isDraggingDown = true
                                                }

                                                coroutineScope.launch {
                                                    val newOffset = offsetY.value + dragAmount
                                                    offsetY.snapTo(max(newOffset, 0f))
                                                }
                                            }
                                        },
                                        onDragEnd = {
                                            coroutineScope.launch {
                                                inDrag = false
                                                isDraggingDown = false

                                                if (offsetY.value > 100 && dragDirection > 0) {
                                                    offsetY.animateTo(5000f, animationSpec = tween(300))
                                                    getPages.setShowSeePrompt(false)
                                                    delay(300)
                                                    offsetY.snapTo(0f)
                                                } else {
                                                    offsetY.animateTo(0f, animationSpec = tween(300))
                                                }
                                            }
                                        }
                                    )
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                Text(
                                    text = ContentPrompt,
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontStyle = FontStyle.Italic
                                    ),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(16.dp)
                                )
                                Spacer(modifier = Modifier.height(50.dp))
                            }
                        }
                    }
                }

                AnimatedVisibility(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {}
                        )
                        .align(Alignment.BottomCenter),
                    visible = threeDotsMenu,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = if (inDrag) ExitTransition.None else slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
// Add these state variables at the appropriate scope
                    var dragDirection by remember { mutableStateOf(0f) }
                    var isDraggingDown by remember { mutableStateOf(false) }

                    val nestedScrollConnection = remember {
                        object : NestedScrollConnection {
                            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
// If we're already in a downward drag and now moving upward, intercept the scroll
                                if (inDrag && isDraggingDown && available.y < 0) {
                                    coroutineScope.launch {
                                        val newOffset = offsetY.value + available.y
                                        offsetY.snapTo(max(newOffset, 0f))
                                    }
                                    return available
                                }
                                return Offset.Zero
                            }

                            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
// If there's leftover scroll and we're at the top of the list
                                if (available.y != 0f && source == NestedScrollSource.UserInput) {
                                    if (available.y > 0) {
// Dragging down
                                        isDraggingDown = true
                                        inDrag = true
                                    } else if (inDrag && isDraggingDown) {
// We're already in a downward drag, so handle upward drag here too
                                    }

                                    coroutineScope.launch {
                                        val newOffset = offsetY.value + available.y
                                        offsetY.snapTo(max(newOffset, 0f))
                                    }
                                    dragDirection = available.y
                                    return available
                                }
                                return Offset.Zero
                            }

                            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
// Handle end of scroll fling
                                inDrag = false
                                isDraggingDown = false

                                if (offsetY.value > 100) {
                                    offsetY.animateTo(5000f, animationSpec = tween(300))
                                    getPages.setThreeDotsMenu(false)
                                    delay(300)
                                    offsetY.snapTo(0f)
                                } else {
                                    offsetY.animateTo(0f, animationSpec = tween(300))
                                }
                                return available
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .pointerInput(Unit) {
                                var dragDirection = 0f
                                detectVerticalDragGestures(
                                    onVerticalDrag = { change, dragAmount ->
                                        change.consume()
                                        inDrag = true
                                        dragDirection = dragAmount

                                        coroutineScope.launch {
                                            val newOffset = offsetY.value + dragAmount
                                            offsetY.snapTo(max(newOffset, 0f))
                                        }
                                    },
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            inDrag = false
                                            if (offsetY.value > 100 && dragDirection > 0) {
                                                offsetY.animateTo(5000f, animationSpec = tween(300))
                                                getPages.setThreeDotsMenu(false)
                                                delay(300)
                                                offsetY.snapTo(0f)
                                            } else {
                                                offsetY.animateTo(0f, animationSpec = tween(300))
                                            }
                                        }
                                    }
                                )
                            }


                            .offset {
                                IntOffset(0, offsetY.value.roundToInt())
                            }
                            .fillMaxWidth()
                            .height(500.dp) // This sets the maximum height to 500.dp
                            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .background(MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(modifier = Modifier
                            .width(50.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.onBackground.copy(0.5f))
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val lazyListState = rememberLazyListState()

                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier
                                .fillMaxSize()
                                .nestedScroll(nestedScrollConnection)
                                .pointerInput(Unit) {
                                    detectVerticalDragGestures(
                                        onDragStart = {
// Reset drag state at start
                                            dragDirection = 0f
                                        },
                                        onVerticalDrag = { change, dragAmount ->
// Only handle this gesture if we're at the top of the list
// or already in a downward drag
                                            if (lazyListState.firstVisibleItemIndex == 0 &&
                                                lazyListState.firstVisibleItemScrollOffset == 0 ||
                                                (inDrag && isDraggingDown)) {

                                                change.consume()
                                                inDrag = true
                                                dragDirection = dragAmount

                                                if (dragAmount > 0) {
                                                    isDraggingDown = true
                                                }

                                                coroutineScope.launch {
                                                    val newOffset = offsetY.value + dragAmount
                                                    offsetY.snapTo(max(newOffset, 0f))
                                                }
                                            }
                                        },
                                        onDragEnd = {
                                            coroutineScope.launch {
                                                inDrag = false
                                                isDraggingDown = false

                                                if (offsetY.value > 100 && dragDirection > 0) {
                                                    offsetY.animateTo(5000f, animationSpec = tween(300))
                                                    getPages.setThreeDotsMenu(false)
                                                    delay(300)
                                                    offsetY.snapTo(0f)
                                                } else {
                                                    offsetY.animateTo(0f, animationSpec = tween(300))
                                                }
                                            }
                                        }
                                    )
                                },
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            items(threeDotsItems) { item ->
                                val icon = item[0] as Painter
                                val title = item[1] as String
                                val onclick = item[2] as () -> Unit

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()

                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() },
                                            onClick = onclick
                                        )
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    NoRippleIconButton(
                                        modifier = Modifier.size(50.dp),
                                        onClick = onclick
                                    ) {
                                        Icon(
                                            painter = icon,
                                            contentDescription = "Icone",
                                            tint = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Normal
                                        ),
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                }


                AnimatedVisibility(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {}
                        )
                        .align(Alignment.BottomCenter),
                    visible = showComments,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = if (inDrag) ExitTransition.None else slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
// Add these state variables at the appropriate scope
                    var dragDirection by remember { mutableStateOf(0f) }
                    var isDraggingDown by remember { mutableStateOf(false) }

                    val nestedScrollConnection = remember {
                        object : NestedScrollConnection {
                            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
// If we're already in a downward drag and now moving upward, intercept the scroll
                                if (inDrag && isDraggingDown && available.y < 0) {
                                    coroutineScope.launch {
                                        val newOffset = offsetY.value + available.y
                                        offsetY.snapTo(max(newOffset, 0f))
                                    }
                                    return available
                                }
                                return Offset.Zero
                            }

                            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
// If there's leftover scroll and we're at the top of the list
                                if (available.y != 0f && source == NestedScrollSource.UserInput) {
                                    if (available.y > 0) {
// Dragging down
                                        isDraggingDown = true
                                        inDrag = true
                                    } else if (inDrag && isDraggingDown) {
// We're already in a downward drag, so handle upward drag here too
                                    }

                                    coroutineScope.launch {
                                        val newOffset = offsetY.value + available.y
                                        offsetY.snapTo(max(newOffset, 0f))
                                    }
                                    dragDirection = available.y
                                    return available
                                }
                                return Offset.Zero
                            }

                            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
// Handle end of scroll fling
                                inDrag = false
                                isDraggingDown = false

                                if (offsetY.value > 100) {
                                    offsetY.animateTo(5000f, animationSpec = tween(300))
                                    getPages.setShowComments(false)
                                    delay(300)
                                    offsetY.snapTo(0f)
                                } else {
                                    offsetY.animateTo(0f, animationSpec = tween(300))
                                }
                                return available
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .pointerInput(Unit) {
                                var dragDirection = 0f
                                detectVerticalDragGestures(
                                    onVerticalDrag = { change, dragAmount ->
                                        change.consume()
                                        inDrag = true
                                        dragDirection = dragAmount

                                        coroutineScope.launch {
                                            val newOffset = offsetY.value + dragAmount
                                            offsetY.snapTo(max(newOffset, 0f))
                                        }
                                    },
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            inDrag = false
                                            if (offsetY.value > 100 && dragDirection > 0) {
                                                offsetY.animateTo(5000f, animationSpec = tween(300))
                                                getPages.setShowComments(false)
                                                delay(300)
                                                offsetY.snapTo(0f)
                                            } else {
                                                offsetY.animateTo(0f, animationSpec = tween(300))
                                            }
                                        }
                                    }
                                )
                            }


                            .offset {
                                IntOffset(0, offsetY.value.roundToInt())
                            }
                            .fillMaxWidth()
                            .height(500.dp)
                            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .background(MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(modifier = Modifier
                            .width(50.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.onBackground.copy(0.5f))
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.comment),
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(16.dp)
                            )

                        }


                        val lazyListState = rememberLazyListState()

                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier
                                .fillMaxSize()
                                .nestedScroll(nestedScrollConnection)
                                .pointerInput(Unit) {
                                    detectVerticalDragGestures(
                                        onDragStart = {
// Reset drag state at start
                                            dragDirection = 0f
                                        },
                                        onVerticalDrag = { change, dragAmount ->
// Only handle this gesture if we're at the top of the list
// or already in a downward drag
                                            if (lazyListState.firstVisibleItemIndex == 0 &&
                                                lazyListState.firstVisibleItemScrollOffset == 0 ||
                                                (inDrag && isDraggingDown)) {

                                                change.consume()
                                                inDrag = true
                                                dragDirection = dragAmount

                                                if (dragAmount > 0) {
                                                    isDraggingDown = true
                                                }

                                                coroutineScope.launch {
                                                    val newOffset = offsetY.value + dragAmount
                                                    offsetY.snapTo(max(newOffset, 0f))
                                                }
                                            }
                                        },
                                        onDragEnd = {
                                            coroutineScope.launch {
                                                inDrag = false
                                                isDraggingDown = false

                                                if (offsetY.value > 100 && dragDirection > 0) {
                                                    offsetY.animateTo(5000f, animationSpec = tween(300))
                                                    getPages.setShowComments(false)
                                                    delay(300)
                                                    offsetY.snapTo(0f)
                                                } else {
                                                    offsetY.animateTo(0f, animationSpec = tween(300))
                                                }
                                            }
                                        }
                                    )
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                Spacer(modifier = Modifier.height(50.dp))
                            }
                        }
                    }
                }



                AnimatedVisibility(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {}
                        )
                        .align(Alignment.BottomCenter),
                    visible = sendContent,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = if (inDrag) ExitTransition.None else slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
// Add these state variables at the appropriate scope
                    var dragDirection by remember { mutableStateOf(0f) }
                    var isDraggingDown by remember { mutableStateOf(false) }

                    val nestedScrollConnection = remember {
                        object : NestedScrollConnection {
                            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
// If we're already in a downward drag and now moving upward, intercept the scroll
                                if (inDrag && isDraggingDown && available.y < 0) {
                                    coroutineScope.launch {
                                        val newOffset = offsetY.value + available.y
                                        offsetY.snapTo(max(newOffset, 0f))
                                    }
                                    return available
                                }
                                return Offset.Zero
                            }

                            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
// If there's leftover scroll and we're at the top of the list
                                if (available.y != 0f && source == NestedScrollSource.UserInput) {
                                    if (available.y > 0) {
// Dragging down
                                        isDraggingDown = true
                                        inDrag = true
                                    } else if (inDrag && isDraggingDown) {
// We're already in a downward drag, so handle upward drag here too
                                    }

                                    coroutineScope.launch {
                                        val newOffset = offsetY.value + available.y
                                        offsetY.snapTo(max(newOffset, 0f))
                                    }
                                    dragDirection = available.y
                                    return available
                                }
                                return Offset.Zero
                            }

                            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
// Handle end of scroll fling
                                inDrag = false
                                isDraggingDown = false

                                if (offsetY.value > 100) {
                                    offsetY.animateTo(5000f, animationSpec = tween(300))
                                    getPages.setSendContent(false)
                                    delay(300)
                                    offsetY.snapTo(0f)
                                } else {
                                    offsetY.animateTo(0f, animationSpec = tween(300))
                                }
                                return available
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .pointerInput(Unit) {
                                var dragDirection = 0f
                                detectVerticalDragGestures(
                                    onVerticalDrag = { change, dragAmount ->
                                        change.consume()
                                        inDrag = true
                                        dragDirection = dragAmount

                                        coroutineScope.launch {
                                            val newOffset = offsetY.value + dragAmount
                                            offsetY.snapTo(max(newOffset, 0f))
                                        }
                                    },
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            inDrag = false
                                            if (offsetY.value > 100 && dragDirection > 0) {
                                                offsetY.animateTo(5000f, animationSpec = tween(300))
                                                getPages.setSendContent(false)
                                                delay(300)
                                                offsetY.snapTo(0f)
                                            } else {
                                                offsetY.animateTo(0f, animationSpec = tween(300))
                                            }
                                        }
                                    }
                                )
                            }


                            .offset {
                                IntOffset(0, offsetY.value.roundToInt())
                            }
                            .fillMaxWidth()
                            .height(500.dp)
                            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .background(MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(modifier = Modifier
                            .width(50.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.onBackground.copy(0.5f))
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.sendto),
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(16.dp)
                            )

                        }


                        val lazyListState = rememberLazyListState()

                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier
                                .fillMaxSize()
                                .nestedScroll(nestedScrollConnection)
                                .pointerInput(Unit) {
                                    detectVerticalDragGestures(
                                        onDragStart = {
// Reset drag state at start
                                            dragDirection = 0f
                                        },
                                        onVerticalDrag = { change, dragAmount ->
// Only handle this gesture if we're at the top of the list
// or already in a downward drag
                                            if (lazyListState.firstVisibleItemIndex == 0 &&
                                                lazyListState.firstVisibleItemScrollOffset == 0 ||
                                                (inDrag && isDraggingDown)) {

                                                change.consume()
                                                inDrag = true
                                                dragDirection = dragAmount

                                                if (dragAmount > 0) {
                                                    isDraggingDown = true
                                                }

                                                coroutineScope.launch {
                                                    val newOffset = offsetY.value + dragAmount
                                                    offsetY.snapTo(max(newOffset, 0f))
                                                }
                                            }
                                        },
                                        onDragEnd = {
                                            coroutineScope.launch {
                                                inDrag = false
                                                isDraggingDown = false

                                                if (offsetY.value > 100 && dragDirection > 0) {
                                                    offsetY.animateTo(5000f, animationSpec = tween(300))
                                                    getPages.setSendContent(false)
                                                    delay(300)
                                                    offsetY.snapTo(0f)
                                                } else {
                                                    offsetY.animateTo(0f, animationSpec = tween(300))
                                                }
                                            }
                                        }
                                    )
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                Spacer(modifier = Modifier.height(50.dp))
                            }
                        }
                    }
                }




            }
        }
    }

}








@Composable
fun DefilementTextuel(
    position: String = "center",
    text: String,
    maxWidth: Dp = 200.dp, // Container max width
    scrollSpeed: Float = 100f, // Pixels per second
    modifier: Modifier = Modifier
) {
    var textWidth by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    val typo = MaterialTheme.typography.bodySmall
    // Calculate text width more accurately with TextMeasurer
    val textMeasurer = rememberTextMeasurer()
    LaunchedEffect(text) {
        val textSize = textMeasurer.measure(
            text = AnnotatedString(text),
            style = typo
        ).size
        textWidth = textSize.width
    }

    // Convert maxWidth to pixels for comparison
    val maxWidthPx = with(density) { maxWidth.toPx() }

    // Determine if text needs scrolling
    val needsScrolling = textWidth > maxWidthPx

    var premierDefilement by remember { mutableStateOf(true) }



    // Animation only if scrolling is needed
    val scrollPosition = if (needsScrolling) {
        // Calculate animation duration based on constant speed
        val pixelsToCover = textWidth + maxWidthPx
        val durationMillis = (pixelsToCover / scrollSpeed * 1000).toInt()

        val scrollState = rememberInfiniteTransition(label = "scroll")

        scrollState.animateFloat(
            initialValue = if (premierDefilement) 0f else maxWidthPx,
            targetValue = -textWidth.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "scrollPosition"
        )

    } else {
        // No animation needed, center the text
        remember { mutableStateOf((maxWidthPx - textWidth) / 2) }
    }


    if (premierDefilement && needsScrolling) {
        LaunchedEffect(Unit) {
            snapshotFlow { scrollPosition.value }
                .collect { value ->
                    // Comparaison avec une marge d'erreur pour les floats
                    if (value <= -textWidth * 0.99f) {
                        premierDefilement = false
                    }
                }
        }
    }

    Box(
        modifier = modifier
            .width(maxWidth)
            .clip(RectangleShape)
            .background(Color.Transparent)
            .padding(horizontal = 4.dp),
        contentAlignment = (if (needsScrolling) Alignment.CenterStart else if (position == "center") Alignment.Center else if (position == "start") Alignment.CenterStart else Alignment.CenterEnd) as Alignment
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White
            ),
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Visible,
            modifier = Modifier.then(
                if (needsScrolling) Modifier.offset { IntOffset(scrollPosition.value.toInt(), 0) } else Modifier
            )

        )
    }
}









class AudioFocusManager(private val context: Context) {

    private var audioFocusRequest: AudioFocusRequest? = null
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    private val audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            abandonAudioFocus() // Relâcher le focus proprement
        }
    }

    fun requestAudioFocus(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .build()

            audioManager.requestAudioFocus(audioFocusRequest!!) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        } else {
            audioManager.requestAudioFocus(
                audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            ) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }
    }

    fun abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest?.let {
                audioManager.abandonAudioFocusRequest(it)
            }
            audioFocusRequest = null
        } else {
            audioManager.abandonAudioFocus(audioFocusChangeListener)
        }
    }
}



/* Cette fonction fait lagguer
fun getVideoDimensions(videoPath: String): Pair<Int, Int> {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(videoPath)

    // Récupérer la largeur et la hauteur de la vidéo
    val width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toInt() ?: 0
    val height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toInt() ?: 0

    retriever.release()

    return Pair(width, height)
}



@Composable
fun VideoPreview(progress: Float, exoPlayer: ExoPlayer) {
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }

    // Mettre à jour l'image de l'aperçu à chaque changement de progression
    LaunchedEffect(progress) {
        val newPosition = (progress * exoPlayer.duration).toLong()
        val frameBitmap = extractVideoFrame(newPosition)
        imageBitmap.value = frameBitmap
    }

    // Afficher l'image à partir du Bitmap extrait
    imageBitmap.value?.let { bitmap ->
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Video Preview",
            modifier = Modifier
                .size(100.dp) // Tu peux ajuster la taille selon tes besoins
        )
    }
}

fun extractVideoFrame(positionMs: Long): Bitmap? {
    val retriever = MediaMetadataRetriever()
    // Assure-toi de définir correctement le chemin ou URI de ta vidéo
    retriever.setDataSource("http://192.168.10.7:5000/video")

    // Récupérer le frame à la position donnée (en microsecondes)
    return retriever.getFrameAtTime(positionMs * 1000, MediaMetadataRetriever.OPTION_CLOSEST)
}

 */


@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)

@Composable
fun rememberExoPlayer(ContentType: String, context: Context, videoUrl: String, audioUrl: String): ExoPlayer {
    return remember {
        ExoPlayer.Builder(context).build().apply {

            if (ContentType == "video") {
                val videoSource =
                    ProgressiveMediaSource.Factory(DefaultDataSource.Factory(context))
                        .createMediaSource(MediaItem.fromUri(videoUrl))

                val audioSource =
                    ProgressiveMediaSource.Factory(DefaultDataSource.Factory(context))
                        .createMediaSource(MediaItem.fromUri(audioUrl))

                val mergedSource = MergingMediaSource(true, true, videoSource, audioSource)
                setMediaSource(mergedSource)

            } else {
                val audioSource =
                    ProgressiveMediaSource.Factory(DefaultDataSource.Factory(context))
                        .createMediaSource(MediaItem.fromUri(audioUrl))
                setMediaSource(audioSource)

            }

            prepare()
            // Listener pour redémarrer la vidéo lorsqu'elle arrive à la fin
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    super.onPlaybackStateChanged(state)
                    if (state == ExoPlayer.STATE_ENDED) {
                        seekTo(0) // Retour au début
                        play()  // Redémarrer la vidéo
                    }
                }
            })
        }
    }
}


// LifecycleObserver pour mettre en pause la vidéo lorsque l'application passe en arrière-plan
class VideoLifecycleObserver(private val exoPlayer: ExoPlayer) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        exoPlayer.pause()  // Mettre en pause quand l'application est en arrière-plan
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        if (!exoPlayer.isPlaying) {
            exoPlayer.play()  // Reprendre la lecture si l'application revient au premier plan
        }
    }
}








@Composable
fun LoadingScreen() {
    val shimmerColors = listOf(
        Color.White.copy(0.8f),
        Color.White.copy(0.6f)
    )


    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = -800f,
        targetValue = 1500f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim.value, translateAnim.value),
        end = Offset(translateAnim.value, translateAnim.value + 500f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Shimmer effect for the video/image placeholder
        Box(
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .align(Alignment.Center).offset(y = - 50.dp)
                .background(brush = brush)
        )

        // Shimmer effect for the bottom UI elements
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .padding(bottom = 20.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Shimmer effect for the profile picture
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {


                // Shimmer effect for the username
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(brush = brush)
                )

                Spacer(modifier = Modifier.height(8.dp))
                // Shimmer effect for the prompt text
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(15.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(brush = brush)
                )
            }


        }

        // Shimmer effect for the side action buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 20.dp)
                .padding(16.dp)
        ) {
            // Shimmer effect for the like button
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Shimmer effect for the comment button
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Shimmer effect for the share button
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )
        }
    }
}



