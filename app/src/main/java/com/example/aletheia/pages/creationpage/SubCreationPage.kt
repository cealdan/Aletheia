package com.example.aletheia.pages.creationpage

import android.app.Activity
import android.content.ClipData
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.launch
import androidx.navigation.NavHostController



import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.delay

import androidx.compose.ui.graphics.graphicsLayer


import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.aletheia.AletheiaTheme
import com.example.aletheia.NoRippleIconButton
import com.example.aletheia.R
import com.example.aletheia.RetrofitInstance
import com.example.aletheia.SQL
import com.example.aletheia.ThemeViewModel
import com.example.aletheia.sendAI
import com.example.aletheia.showFloatingToast
import com.example.aletheia.viewmodels.AIModel
import com.example.aletheia.viewmodels.ChatTitle
import com.example.aletheia.viewmodels.ChatHistoryModel
import com.example.aletheia.viewmodels.GetPages
import com.example.aletheia.viewmodels.GlobalViewModel
import com.example.aletheia.viewmodels.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import java.text.Normalizer
import java.util.UUID
import kotlin.math.max
import kotlin.math.roundToInt
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.content.FileProvider
import androidx.core.view.WindowCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.aletheia.SendImage
import com.example.aletheia.pages.profilepage.saveProfileImage
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask


@Composable
fun AddModelPage(chatHistoryModel: ChatHistoryModel, globalViewModel: GlobalViewModel, getPages: GetPages, themeViewModel: ThemeViewModel, username: String) {

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    val index_models = chatHistoryModel.index_models.collectAsState()

    val ajout_txt = stringResource(id = R.string.ajouté)

    val context = LocalContext.current

    val pagerState = rememberPagerState(0, 0f, {2}) //nombre de pages à ajuster
    val lazyState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    var inText by remember { mutableStateOf(false) }
    var inImage by remember { mutableStateOf(false) }
    var inVideo by remember { mutableStateOf(false) }

    var currentData by remember { mutableStateOf("") }

    LaunchedEffect(inText, inImage, inVideo) {
        if (inText) {
            chatHistoryModel.loadmodelsFromDB("text")
        } else if (inImage) {
            chatHistoryModel.loadmodelsFromDB("image")
        } else if (inVideo) {
            chatHistoryModel.loadmodelsFromDB("video")
        }
    }

    LaunchedEffect(Unit) {
        if (pagerState.currentPage == 1) {
            pagerState.scrollToPage(0)
        }
    }


    val availableModels by chatHistoryModel.modelsFromDB.collectAsState()
    var scrolled by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (scrolled) 0f else 1f,
        animationSpec = tween(durationMillis = 150)
    )


    val myModels by chatHistoryModel.myModels.collectAsState()


    DisposableEffect(Unit) {
        onDispose {
            coroutineScope.launch {
                scrolled = false
                chatHistoryModel.clearModelsFromDB()
                pagerState.animateScrollToPage(0)
                currentData = ""
                inText = false
                inImage = false
                inVideo = false
            }
        }
    }

    val isNetworkAvailable by RetrofitInstance.isNetworkAvailable.collectAsState()


    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {


        VerticalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp)
                            .alpha(alpha)
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            /*
                            Box(
                                modifier = Modifier.align(Alignment.Start)
                                    .border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp) )

                            ) {
                                Text(
                                    text = stringResource(id = R.string.ajout_de_modele),
                                    style = TextStyle(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontSize = 10.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            
                             */
                            Text(
                                text = stringResource(id = R.string.type_de_contenu),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                        /*
                        Text(
                            text = stringResource(id = R.string.info_contenu),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(16.dp).align(Alignment.Center)
                        )

                         */
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(top = 50.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center

                        ) {
                            Row(

                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Spacer(modifier = Modifier.width(16.dp))
                                Button(
                                    //border = ButtonDefaults.outlinedButtonBorder(false),

                                    onClick = {
                                        coroutineScope.launch {
                                            scrolled = true
                                            delay(150) //empêche d'emporter des modèles de certaines catégories dans d'autres en cliquant trop vite
                                            pagerState.animateScrollToPage(1)
                                            currentData = "text"
                                            inText = true
                                        }
                                    },
                                    colors = ButtonColors(
                                        MaterialTheme.colorScheme.primary.copy(0.5f),
                                        MaterialTheme.colorScheme.onBackground,
                                        MaterialTheme.colorScheme.surface,
                                        MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                    )

                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            stringResource(id = R.string.texte),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            painter = painterResource(id = R.drawable.texticon),
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Button(
                                    //border = ButtonDefaults.outlinedButtonBorder(false),

                                    enabled = true,
                                    onClick = {
                                        coroutineScope.launch {
                                            scrolled = true
                                            delay(150)
                                            pagerState.animateScrollToPage(1)
                                            currentData = "image"
                                            inImage = true
                                        }
                                    },
                                    colors = ButtonColors(
                                        MaterialTheme.colorScheme.primary.copy(0.5f),
                                        MaterialTheme.colorScheme.onBackground,
                                        MaterialTheme.colorScheme.surface,
                                        MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                    )

                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            stringResource(id = R.string.images),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            painter = painterResource(id = R.drawable.imageicon),
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }
                                }

                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                border = ButtonDefaults.outlinedButtonBorder(false),
                                enabled = false,
                                onClick = {
                                    coroutineScope.launch {
                                        scrolled = true
                                        delay(150)
                                        pagerState.animateScrollToPage(1)
                                        currentData = "video"
                                        inVideo = true
                                    }
                                },
                                colors = ButtonColors(
                                    MaterialTheme.colorScheme.primary.copy(0.5f),
                                    MaterialTheme.colorScheme.onBackground,
                                    MaterialTheme.colorScheme.surface,
                                    MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        stringResource(id = R.string.videos),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        painter = painterResource(id = R.drawable.videoicon),
                                        contentDescription = "Delete",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                            }
                        }


                    }
                }
                1 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {

                        LazyColumn(
                            state = lazyState,
                            modifier = Modifier.align(Alignment.TopCenter)
                        ) {
                            items(availableModels) { item ->
                                val modelName = item[0]
                                val modelID = item[1]
                                val genData = item[2]



                                if (genData == currentData) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .then(
                                                if (myModels.contains(
                                                        AIModel(
                                                            modelName,
                                                            modelID,
                                                            genData
                                                        )
                                                    )
                                                ) {
                                                    Modifier
                                                } else {
                                                    Modifier.clickable {
                                                        if (!myModels.filter { it.name == modelName }
                                                                .isNotEmpty()
                                                        ) {


                                                            val changedModels =
                                                                myModels.toMutableList()
                                                            changedModels.add(
                                                                AIModel(
                                                                    modelName,
                                                                    modelID,
                                                                    genData
                                                                )
                                                            )
                                                            chatHistoryModel.setMyModels(
                                                                changedModels
                                                            )


                                                            /*
                                                            chatHistoryModel.addModelToList(
                                                                modelName,
                                                                listOf()
                                                            )
                                                             */
                                                            //chatHistoryModel.clearChatHistory()
                                                            showFloatingToast(
                                                                context,
                                                                modelName + " " + ajout_txt
                                                            )
                                                            //val activity = context as? Activity
                                                            //activity?.recreate()
                                                            //chatHistoryModel.putIndexModels(index_models.value + 1) //ordre important
                                                        }
                                                    }
                                                }
                                            )

                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                modelName,
                                                style = MaterialTheme.typography.bodyLarge.copy(
                                                    fontSize = 20.sp,
                                                    color = if (myModels.contains(AIModel(modelName, modelID, genData))) MaterialTheme.colorScheme.onBackground.copy(0.5f) else MaterialTheme.colorScheme.onBackground

                                                ),
                                                modifier = Modifier.padding(3.dp)
                                            )
                                            if (myModels.contains(AIModel(modelName, modelID, genData))) {
                                                Spacer(modifier = Modifier.width(16.dp))
                                                Icon(
                                                    painter = painterResource(id = R.drawable.check),
                                                    contentDescription = "Check",
                                                    modifier = Modifier.size(30.dp),
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            if (availableModels.isEmpty()) {
                                item {
                                    if (!isNetworkAvailable) {
                                        Text(
                                            text = stringResource(id = R.string.no_models),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = MaterialTheme.colorScheme.error
                                            ),
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    } else {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }

                        }
                        Row(
                            modifier = Modifier.align(Alignment.BottomStart),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        scrolled = false
                                        chatHistoryModel.clearModelsFromDB()
                                        pagerState.animateScrollToPage(0)
                                        currentData = ""
                                        inText = false
                                        inImage = false
                                        inVideo = false
                                    }
                                },
                                colors = ButtonColors(
                                    MaterialTheme.colorScheme.primary.copy(0.5f),
                                    MaterialTheme.colorScheme.onBackground,
                                    Color.Gray,
                                    Color.Gray
                                )

                            ) {
                                Text(stringResource(id = R.string.retour),
                                    style = MaterialTheme.typography.bodyLarge)
                            }
                            Spacer(modifier = Modifier.width(16.dp))

                        }
                    }
                }
            }

        }


    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationTopBar(chatHistoryModel: ChatHistoryModel, globalViewModel: GlobalViewModel, getPages: GetPages, themeViewModel: ThemeViewModel, username: String) {
    val context = LocalContext.current // Utilisation de LocalContext pour obtenir le contexte
    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)
    val coroutineScope = rememberCoroutineScope()

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    var showDropdown by remember { mutableStateOf(false) }

    val index_models = chatHistoryModel.index_models.collectAsState()

    val myModels by chatHistoryModel.myModels.collectAsState()


    val newPost by getPages.newPost.collectAsState()

    val onbackground = MaterialTheme.colorScheme.onBackground

    val addModeltoList by chatHistoryModel.addModelToList.collectAsState()

    val model_suppr =  stringResource(id = R.string.modele_suppr)

    var showDialog by remember { mutableStateOf(false) }

    val drawerstatecollect by globalViewModel.drawerState.collectAsState()

    val assistantModel by globalViewModel.assistantModel.collectAsState()

    val index by chatHistoryModel.index.collectAsState()
    var dropDownModel by remember { mutableStateOf(false) }

    val chatHistory by chatHistoryModel.chatHistory.collectAsState()

    val listChatTitles by chatHistoryModel.ListTitles.collectAsState()

    Log.d("creation top bar", "index_models.value: $index_models")
    Log.d("creation top bar", "myModels: $myModels")

    val tmpNetHandler by chatHistoryModel.tmpNetHandler.collectAsState()
    val RetrofitNetValue by RetrofitInstance.isNetworkAvailable.collectAsState()

    // Appliquer le thème
    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {

        Surface(
            modifier = Modifier
                .height(70.dp)

                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = if (!addModeltoList) onbackground.copy(0.1f) else Color.Transparent,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                },
            color = MaterialTheme.colorScheme.background,
            //shadowElevation = 8.dp
        ) {

            TopAppBar(
                title = {
                    if (RetrofitNetValue && !tmpNetHandler) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier

                                    .then(
                                        if (myModels.filter { it.id == index_models.value }[0].name == assistantModel) {
                                            Modifier

                                        } else {
                                            Modifier.clickable(
                                                indication = null,
                                                interactionSource = remember { MutableInteractionSource() },
                                                onClick = {
                                                    dropDownModel = true
                                                }
                                            )
                                        }
                                    ),
                                text = myModels.filter { it.id == index_models.value }[0].name,
                            )
                            if (myModels.filter { it.id == index_models.value }[0].name == assistantModel && !newPost) {
                                Icon(
                                    painter = painterResource(id = R.drawable.advicefromaiicon),
                                    contentDescription = "Main model",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(25.dp)
                                )
                            } else if (!newPost) {

                                NoRippleIconButton(
                                    onClick = {
                                        dropDownModel = true
                                    },
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Icon(
                                        if (!dropDownModel) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                        contentDescription = "DropDown",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                DropdownMenu(
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                                    expanded = dropDownModel,
                                    onDismissRequest = { dropDownModel = false }
                                ) {


                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                stringResource(id = R.string.supprimer_modele),
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = Color.Red
                                                )
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Delete model",
                                                modifier = Modifier.size(20.dp),
                                                tint = Color.Red
                                            )
                                        },
                                        onClick = {
                                            showDialog = true
                                            dropDownModel = false
                                        }
                                    )

                                }
                            }
                        }
                    }

                        },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        if (!newPost) {
                            IconButton(
                                onClick = {
                                    if (drawerstatecollect.isOpen) {
                                        coroutineScope.launch {
                                            drawerstatecollect.close()
                                        }
                                    }
                                    chatHistoryModel.toggleAddModelToList()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.addmodelicon),
                                    contentDescription = "Add",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(25.dp)
                                )
                            }

                            Box() {

                                IconButton(onClick = {
                                    showDropdown = true
                                    if (addModeltoList) {
                                        chatHistoryModel.toggleAddModelToList()
                                    }

                                }) {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        contentDescription = "Models",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                                DropdownMenu(
                                    shape = RoundedCornerShape(16.dp),
                                    expanded = showDropdown && RetrofitNetValue && !tmpNetHandler,
                                    onDismissRequest = { showDropdown = false },
                                    containerColor = MaterialTheme.colorScheme.surface,
                                ) {
                                    myModels.forEach { aimodel ->
                                        val name = aimodel.name
                                        val id = aimodel.id
                                        val genType = aimodel.genType
                                        //val modelInfos = myModels.find { it.name == model }
                                        val dropdowmenuitemICON =
                                            if (genType == "text") {
                                                if (name == assistantModel) {
                                                    painterResource(id = R.drawable.advicefromaiicon)
                                                } else {
                                                    painterResource(id = R.drawable.texticon)
                                                }
                                            } else if (genType == "image") {
                                                painterResource(id = R.drawable.imageicon)
                                            } else { //video
                                                painterResource(id = R.drawable.videoicon)
                                            }

                                        DropdownMenuItem(
                                            trailingIcon = {
                                                Icon(
                                                    painter = dropdowmenuitemICON,
                                                    contentDescription = "Delete",
                                                    tint = MaterialTheme.colorScheme.onBackground,
                                                    modifier = Modifier.size(if (name == assistantModel) 20.dp else 15.dp)
                                                )
                                            },
                                            colors = MenuDefaults.itemColors(
                                                textColor = MaterialTheme.colorScheme.onBackground,
                                                disabledTextColor = MaterialTheme.colorScheme.primary
                                            ),
                                            enabled = id != index_models.value,
                                            text = {
                                                Text(
                                                    name,
                                                    style = MaterialTheme.typography.bodyLarge.copy(
                                                        fontWeight = if (id == index_models.value) FontWeight.Bold else FontWeight.Normal
                                                    )
                                                )
                                            },
                                            onClick = {
                                                showDropdown = false
                                                chatHistoryModel.clearChatHistory()
                                                chatHistoryModel.putIndexModels(id)
                                                chatHistoryModel.putIndex(
                                                    chatHistoryModel.getChatHistory()
                                                        .firstOrNull()?.index ?: UUID.randomUUID()
                                                        .toString()
                                                ) //éventuellement à changer : mettre le dernier index actif du modèle

                                                Log.d(
                                                    "creation top bar",
                                                    "index_models.value: $index_models, index : $index"
                                                )
                                                /*Toast.makeText(context, model, Toast.LENGTH_SHORT)
                                                .show()*/
                                                val activity = context as? Activity
                                                activity?.recreate()


                                            },

                                            )
                                    }
                                }
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
                        if (newPost) {
                            IconButton(
                                onClick = {

                                    getPages.setNewPost(false)

                                }
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.gobackicon
                                    ),
                                    contentDescription = "NewPost",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }

                        if (!newPost && !(listChatTitles.isEmpty() && chatHistory.filter { it.index == index }
                                .firstOrNull()?.history.isNullOrEmpty())) {
                            IconButton(onClick = {

                                coroutineScope.launch {
                                    if (globalViewModel.drawerState.value.isClosed) {

                                        globalViewModel.drawerState.value.open()
                                    } else {

                                        globalViewModel.drawerState.value.close()
                                    }

                                }

                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.menuicon),
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                        }
                    }

                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,

                    ),

                )

        }
        if (showDialog) {
            BasicAlertDialog(
                onDismissRequest = { showDialog = false },
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
                ) {
                    Column {
                        Text(
                            stringResource(id = R.string.supprimer_modele),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 20.sp
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)

                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            stringResource(id = R.string.warning_suppr_modele),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.Center
                            ),

                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)

                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                            thickness = 1.dp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(

                                modifier = Modifier
                                    .height(50.dp)
                                    .weight(1f)
                                    .clickable(
                                        indication = LocalIndication.current,
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = {
                                            showDialog = false
                                        }
                                    ),

                                ) {
                                Text(stringResource(id = R.string.annuler), style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                    modifier = Modifier.align(Alignment.Center))
                            }
                            VerticalDivider(
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                thickness = 1.dp
                            )
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .weight(1f)
                                    .clickable(
                                        indication = LocalIndication.current,
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = {
                                            showDialog = false
                                            chatHistoryModel.deleteAllChatHistory()
                                            chatHistoryModel.removeAllTitles()

                                            val changedModels = myModels.toMutableList()
                                            changedModels.removeIf { it.id == index_models.value }
                                            chatHistoryModel.setMyModels(changedModels)


                                            //chatHistoryModel.removeModel(myModels.filter { it.id == index_models.value }[0].name)
                                            chatHistoryModel.putIndexModels(
                                                chatHistoryModel.myModels.value.lastOrNull()?.id
                                                    ?: "meta-llama-3.1-8b-instruct"
                                            )
                                            chatHistoryModel.putIndex(
                                                chatHistoryModel.getChatHistory()
                                                    .firstOrNull()?.index ?: UUID.randomUUID()
                                                    .toString()
                                            )


                                            /*
                                            val newmodels =
                                                models.toMutableList() // Créer une liste mutable
                                            newmodels.remove(myModels.filter { it.id == index_models.value }[0].name) // Supprimer l'élément
                                            chatHistoryModel.setModels(newmodels) // Mettre à jour les modèles
                                             */

                                            val activity = context as? Activity
                                            activity?.recreate()
                                            showFloatingToast(context, model_suppr)
                                        }
                                    ),

                                ) {
                                Text(stringResource(id = R.string.confirmer), style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.align(Alignment.Center))
                            }
                        }
                    }
                }
            }
        }
    }
}









// Pas parfait : à améliorer

@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier {
    var drag by remember { mutableStateOf(false) }

    val targetAlpha = if (state.isScrollInProgress || drag) 1f else 0f
    val duration = if (state.isScrollInProgress || drag) 150 else 500
    val primary = MaterialTheme.colorScheme.primary
    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )
    var containerHeight by remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()


    return this
        .onGloballyPositioned { coordinates ->
            containerHeight = coordinates.size.height.toFloat()
        }
        // Ajout de la détection du drag sur le scrollbar
        .pointerInput(Unit) {
            detectVerticalDragGestures(
                onDragStart = {
                    drag = true
                },
                onDragEnd = {
                    drag = false
                },
                onDragCancel = {
                    drag = false
                },
                onVerticalDrag = { change, dragAmount ->
                    change.consumeAllChanges()
                    // Calcul d'une hauteur moyenne d'item pour estimer la hauteur totale du contenu
                    val averageItemHeight =
                        state.layoutInfo.visibleItemsInfo.firstOrNull()?.size?.toFloat() ?: 1f
                    val totalContentHeight = state.layoutInfo.totalItemsCount * averageItemHeight
                    // Le facteur correspond à la proportion entre la hauteur totale du contenu et la hauteur du conteneur
                    val factor = totalContentHeight / containerHeight
                    coroutineScope.launch {
                        // On met à jour le scroll en fonction du delta du drag multiplié par ce facteur
                        state.scrollBy(dragAmount * factor)


                    }

                }
            )
        }
        .drawWithContent {
            drawContent()

            val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
            val scrollOffset = state.firstVisibleItemScrollOffset
            val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f || drag
            val proportion = state.layoutInfo.visibleItemsInfo.first().size * 1f

            // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
            if (needDrawScrollbar && firstVisibleElementIndex != null) {
                val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
                val scrollbarOffsetY =
                    (firstVisibleElementIndex + scrollOffset / proportion) * elementHeight
                val scrollbarHeight = 100f

                drawRoundRect(
                    cornerRadius = CornerRadius(x = width.toPx() / 2, y = width.toPx() / 2),
                    color = if (drag) primary else primary.copy(0.3f),
                    topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                    size = Size(width.toPx(), scrollbarHeight),
                    alpha = alpha
                )
            }
        }
}



@Composable
fun ChatSidebar(_models: List<AIModel>, index_models: String, drawerState: DrawerState, globalViewModel: GlobalViewModel, themeViewModel: ThemeViewModel, chatHistoryModel: ChatHistoryModel) {
    val coroutineScope = rememberCoroutineScope()

    val chatHistories = List(chatHistoryModel.chatHistory.collectAsState().value.size){ "Chat $it" }
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val context = LocalContext.current // Utilisation de LocalContext pour obtenir le contexte
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val listTitles by chatHistoryModel.listTitles.collectAsState()
    val index by chatHistoryModel.index.collectAsState()

    LaunchedEffect(listTitles) {
        Log.d("ChatSidebar", "listTitles: $listTitles")
    }

    val editTitle by chatHistoryModel.editTitle.collectAsState()

    val maxTextFieldLength = 30 //à réfléchir

    val isFocusedSize by animateSizeAsState(
        targetValue = if (isFocused || editTitle) Size(LocalConfiguration.current.screenWidthDp.dp.value, LocalConfiguration.current.screenHeightDp.dp.value) else Size(260.dp.value, LocalConfiguration.current.screenHeightDp.dp.value),
        animationSpec = tween(durationMillis = 300)
    )

    val focusRequester = remember { FocusRequester() }


    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {

        BackHandler {
            if (isFocused) {
                focusManager.clearFocus()
            }
        }

        Column(modifier = Modifier
            .background(
                MaterialTheme.colorScheme.background
            )
            /*
            brush = Brush.verticalGradient(
                colors =
                if (!isDarkTheme) {
                    listOf(Color.White, Color(0xFFBD7BE3) , Color(0xFF0728A1))
                } else {
                    listOf(Color(0xFF1A1A1A), Color(0xFF1A1A1A))
                }
            )
        )

             */
            .size(isFocusedSize.width.dp, isFocusedSize.height.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { focusManager.clearFocus() }
            )
            .width(250.dp)
            .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {


                Box(
                    modifier = Modifier

                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground.copy(0.05f),
                            RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.background)

                        .padding(horizontal = 8.dp, vertical = 8.dp),
                ) {
                    // Label qui reste même quand unfocused
                    if (!isFocused && searchQuery.text.isBlank()) {
                        Text(
                            text = stringResource(id = R.string.chercherchats),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            ),
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 32.dp, top = 2.5.dp)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Leading Icon
                        Icon(
                            modifier = Modifier.clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = {
                                    if (!isFocused) {
                                        focusRequester.requestFocus()
                                    } else {
                                        focusManager.clearFocus()
                                        searchQuery = TextFieldValue("")
                                    }
                                }
                            ),
                            imageVector = if (!isFocused) Icons.Default.Search else Icons.Default.ArrowBack,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // BasicTextField
                        BasicTextField(
                            interactionSource = interactionSource,
                            value = searchQuery,
                            singleLine = true,
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            onValueChange = {
                                if (it.text.length <= maxTextFieldLength) {
                                    searchQuery = it
                                }
                                            },
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .fillMaxWidth()
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(chatHistoryModel.getListTitles()) { title ->
                    Log.d("ChatSidebar", "index: $index")
                    Log.d("ChatSidebar", "chatHistories index of chat : ${listTitles.filter { it.title == title.title }.firstOrNull()?.index}")
                    Log.d("ChatSidebar", "list titles index : ${listTitles.filter { it.title == title.title }.firstOrNull()?.index}, $listTitles, ${listTitles.size}")
                    if (title.title.contains(searchQuery.text, ignoreCase = true)) {
                        Log.d("ChatSidebar", "chat : $title")
                        ChatItem(
                            title.index,
                            chatHistoryModel,
                            themeViewModel,
                            title
                        ) {


                            chatHistoryModel.putIndex(title.index)
                            chatHistoryModel.getChatHistory()
                            Log.d("ScrollPosition", "Putindex : ${listTitles.filter { it.title == title.title }[0].index}")
                            coroutineScope.launch {
                                drawerState.close()
                            }

                        }
                    }
                }
            }


        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatItem(index: String, chatHistoryModel: ChatHistoryModel, themeViewModel: ThemeViewModel, _chatTitle: ChatTitle, onSelectChat: (String) -> Unit) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var chatTitle by remember { mutableStateOf("") }
    val listTitles = chatHistoryModel.getListTitles()
    var displayedText by remember { mutableStateOf("") }

    var pressed by remember { mutableStateOf(false) }
    val theIndex = chatHistoryModel.index.collectAsState()

    var showWarning by remember { mutableStateOf(false) }

    var edit by remember { mutableStateOf(false) }

    val editFocusRequester = remember { FocusRequester() }
    val context = LocalContext.current


    var isFocused by remember { mutableStateOf(false) }

    val maxTextFieldLength = 30 //à réfléchir

    LaunchedEffect(isFocused) {
        if (displayedText != "") {
            if (!isFocused) {
                chatHistoryModel.setEditTitle(false)
                edit = false
                chatHistoryModel.updateTitle(index, displayedText)
            } else {
                chatHistoryModel.setEditTitle(true)
            }
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                if (_chatTitle.title != "NewChat_$index") {
                    try {
                        Log.d("ChatItem", "_chattile : $_chatTitle")
                        displayedText = _chatTitle.title
                    }
                    catch (e: Exception) {
                        Log.d("ChatItem", "listTitles is not empty")
                        coroutineScope.launch {
                            val response = withContext(Dispatchers.IO) {
                                RetrofitInstance.api.sendPrompt(
                                    sendAI(
                                        model = "Meta Llama 3.1",
                                        prompt = "Give me a title in maximum three words, and answer nothing else, only the title, based on this prompt and in language used in this prompt : ${chatHistoryModel.getChatHistory().filter { it.index == index }[0].history.find { it.role == "User" }?.content?.value}"
                                    )
                                )
                            }
                            response
                                .onStart {
                                    displayedText = ""
                                }
                                .onCompletion {
                                    chatHistoryModel.updateTitle(index, chatTitle)

                                }
                                .collect { chunk ->
                                    chatTitle += chunk
                                    displayedText += chunk

                                }
                        }



                    }
                } else {
                    Log.d("ChatItem", "listTitles is empty")
                    coroutineScope.launch {
                        Log.d("ChatItem", "coroutine")
                        val response = withContext(Dispatchers.IO) {
                            RetrofitInstance.api.sendPrompt(
                                sendAI(
                                    model = "Meta Llama 3.1",
                                    prompt = "Give me a title in maximum three words, and answer nothing else, only the title, based on this prompt and in language used in this prompt : ${chatHistoryModel.getChatHistory().filter { it.index == index }.firstOrNull()?.history?.find { it.role == "User" }?.content?.value}"
                                )
                            )
                        }
                        response
                            .onStart {
                                displayedText = ""
                            }
                            .onCompletion {
                                chatHistoryModel.updateTitle(index, chatTitle)
                                Log.d("ChatItem", "chatTitle : $chatTitle")

                            }
                            .collect { chunk ->
                                chatTitle += chunk
                                displayedText += chunk

                            }
                    }

                }

            } catch (e: Exception) {
                Log.d("ChatItem", "Error : $e")
                displayedText = _chatTitle.title
            }
        }

    }
    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {

        LaunchedEffect(edit) {
            if (edit) {
                editFocusRequester.requestFocus()
            }
        }

        if (showWarning) {
            BasicAlertDialog(
                onDismissRequest = {
                    showWarning = false
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
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(id = R.string.delete),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 20.sp
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)

                        )
                        Text(stringResource(id = R.string.supprimer_alerte), style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                            thickness = 1.dp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(

                                modifier = Modifier
                                    .height(50.dp)
                                    .weight(1f)
                                    .clickable(
                                        indication = LocalIndication.current,
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = {
                                            showWarning = false
                                        }
                                    ),

                                ) {
                                Text(stringResource(id = R.string.annuler), style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                    modifier = Modifier.align(Alignment.Center))
                            }
                            VerticalDivider(
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                thickness = 1.dp
                            )
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .weight(1f)
                                    .clickable(
                                        indication = LocalIndication.current,
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = {
                                            chatHistoryModel.removeTitle(index)
                                            chatHistoryModel.deleteChatHistory(index)
                                            var id = ""
                                            for (i in 0 until chatHistoryModel.getChatHistory().size) {
                                                if (chatHistoryModel.getChatHistory()[chatHistoryModel.getChatHistory().size - i - 1].history.isNotEmpty()
                                                ) {
                                                    id =
                                                        chatHistoryModel.getChatHistory()[chatHistoryModel.getChatHistory().size - i - 1].index
                                                    break
                                                }
                                            }
                                            chatHistoryModel.putIndex(id)
                                            val activity = context as? Activity
                                            activity?.recreate()
                                        }
                                    ),

                                ) {
                                Text(stringResource(id = R.string.confirmer), style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.align(Alignment.Center))
                            }
                        }




                    }
                }
            }
        }
        val onbackground = MaterialTheme.colorScheme.onBackground
        Row(modifier = Modifier

            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .border(
                1.dp,
                if (!isDarkTheme && edit) onbackground.copy(0.05f) else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .background(if (index == theIndex.value) MaterialTheme.colorScheme.primary.copy(0.3f) else Color.Transparent)


            .combinedClickable(
                onClick = { onSelectChat(chatTitle) },
                onLongClick = {
                    pressed = true

                }
            ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            if (edit) {
                var editDisplayedText by remember {
                    mutableStateOf(TextFieldValue(text = displayedText, selection = TextRange(displayedText.length)))
                }
                BasicTextField(
                    value = editDisplayedText,
                    onValueChange = {
                        if (it.text.length <= maxTextFieldLength) {
                            editDisplayedText = it
                            displayedText = editDisplayedText.text
                        }
                                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            edit = false
                        }
                    ),
                    modifier = Modifier

                        .onFocusChanged {
                            isFocused = it.isFocused
                        }
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .focusRequester(editFocusRequester)
                        .align(Alignment.CenterVertically)
                        .padding(8.dp),
                )

            } else {
                Log.d("ChatItem", "displayedText : $displayedText")
                Text(
                    maxLines = 1,  // Limite à une seule ligne
                    overflow = TextOverflow.Ellipsis,  // Ajoute "..." si le texte est trop long
                    text = displayedText,
                    style = MaterialTheme.typography.bodyLarge.copy(),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(8.dp)
                )
            }

            DropdownMenu(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                expanded = pressed,
                onDismissRequest = { pressed = false }
            ) {

                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(id = R.string.edit),
                            style = MaterialTheme.typography.bodyMedium.copy(
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Edit, contentDescription = "Edit",
                            modifier = Modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        edit = true
                        pressed = false


                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(id = R.string.partager),
                            style = MaterialTheme.typography.bodyMedium.copy(
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Share, contentDescription = "Edit",
                            modifier = Modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        //partager le chat complet
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(id = R.string.delete),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Red
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Delete, contentDescription = "Delete",
                            modifier = Modifier.size(25.dp),
                            tint = Color.Red
                        )
                    },
                    onClick = {
                        showWarning = true
                        pressed = false
                    }
                )
            }
        }
    }
}


@Composable
fun isKeyboardVisible(): Boolean {
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return imeVisible
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatBubble(navcontroller: NavHostController, lastMessage: Boolean, getPages: GetPages, sender: String, message: String, isLoading: Boolean, themeViewModel: ThemeViewModel, username: String, chatHistoryModel: ChatHistoryModel, chatId: String) {

    val context = LocalContext.current // Utilisation de LocalContext pour obtenir le contexte

    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    val expanded = remember { mutableStateOf(false) }

    val clipboardManager = LocalClipboardManager.current



    val myModels by chatHistoryModel.myModels.collectAsState()
    val index_models by chatHistoryModel.index_models.collectAsState()

    val index by chatHistoryModel.index.collectAsState()

    //var select by remember { mutableStateOf(false) }

    var gen by remember { mutableStateOf(false) }
    val logoclair = painterResource(id = R.drawable.aletheialoadingscreenclair)
    val logosombre = painterResource(id = R.drawable.aletheialoadingscreensombre)
    val copyicon = ImageVector.vectorResource(id = R.drawable.copyicon)

    val startOutput by chatHistoryModel.startOutput.collectAsState()


    val gradient = Brush.horizontalGradient(
        colors = listOf(MaterialTheme.colorScheme.onBackground, MaterialTheme.colorScheme.primary)
    )

    var error by remember { mutableStateOf(false) }

    var viewImage by remember { mutableStateOf(false) }


    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {





        val primaryColor = MaterialTheme.colorScheme.primary
        val infiniteTransition = rememberInfiniteTransition()

        val onbackground = MaterialTheme.colorScheme.onBackground
        val waveOffset by infiniteTransition.animateFloat(
            initialValue = 300f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        var showWarningChatDeletion by remember { mutableStateOf(false) }

        if (showWarningChatDeletion) {
            BasicAlertDialog(
                onDismissRequest = { showWarningChatDeletion = false },
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
                ) {
                    Column {

                        Text(
                            stringResource(id = R.string.delete),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 20.sp
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)

                        )
                        Text(
                            stringResource(id = R.string.chat_deletion),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.Center
                            ),

                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(horizontal = 16.dp)

                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                            thickness = 1.dp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(

                                modifier = Modifier
                                    .height(50.dp)
                                    .weight(1f)
                                    .clickable(
                                        indication = LocalIndication.current,
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = {
                                            showWarningChatDeletion = false
                                        }
                                    ),

                                ) {
                                Text(stringResource(id = R.string.annuler),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.onBackground
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(8.dp))
                            }
                            VerticalDivider(
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                thickness = 1.dp
                            )
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .weight(1f)
                                    .clickable(
                                        indication = LocalIndication.current,
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = {
                                            showWarningChatDeletion = false
                                            chatHistoryModel.deleteChat(chatId)
                                            val activity = context as? Activity
                                            activity?.recreate()
                                        }
                                    ),

                                ) {
                                Text(stringResource(id = R.string.confirmer),
                                    textAlign = TextAlign.Center,

                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.primary
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(8.dp))
                            }
                        }
                    }
                }
            }
        }



        if (message.isNotEmpty()) {
            Box(
                modifier = Modifier

                    .fillMaxWidth()
                    .padding(
                        start = if (sender == "User") 100.dp else 15.dp,
                        end = if (sender == "User") 15.dp else 15.dp
                    ),
                contentAlignment = if (sender == "User") Alignment.CenterEnd else Alignment.CenterStart
            ) {

                Column(
                    modifier = Modifier
                        .padding(
                            top = if (sender == "User") 10.dp else 0.dp,
                            bottom = if (sender == "User") 10.dp else 0.dp
                        )
                        .shadow(
                            if (sender == "User") 2.dp else 0.dp,
                            shape = RoundedCornerShape(16.dp)
                        )


                        .clip(RoundedCornerShape(16.dp))
                        .then(
                            if (sender == "Assistant" && startOutput) {
                                Modifier
                            } else {
                                Modifier.combinedClickable(
                                    onClick = {
                                        if (sender == "Assistant" && myModels.find { it.id == index_models }?.genType == "image") {
                                            viewImage = true
                                        }
                                    }, // Rien sur un simple clic
                                    onLongClick = { expanded.value = true } // Ouvre le menu
                                )
                            }
                        )


                        .background(
                            if (sender == "User") {
                                MaterialTheme.colorScheme.surface
                            } else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(12.dp)
                        .wrapContentSize()
                ) {



                    if (message != "Chargement...") {

                        val annotatedString = buildAnnotatedString {
                            // Ajouter le texte
                            if (message.contains("Error:")) {
                                error = true
                                append(stringResource(id = R.string.err_ai))
                            } else {
                                error = false
                                append(message)
                            }

                            // Ajouter une annotation pour l'icône
                            if (lastMessage && sender == "Assistant" && isLoading) {
                                appendInlineContent("icon", "icon")
                            }
                        }
                        if (sender == "User") {
                            Text(
                                text = annotatedString,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    brush = if (expanded.value && !error) gradient else null,
                                    fontStyle = if (error) FontStyle.Italic else FontStyle.Normal,
                                ),
                                color = if (!error) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error,

                                inlineContent = mapOf(
                                    "icon" to InlineTextContent(
                                        Placeholder(
                                            width = 20.sp, // Largeur de l'icône
                                            height = 20.sp, // Hauteur de l'icône
                                            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter // Alignement vertical
                                        )
                                    ) {
                                        if (lastMessage && sender == "Assistant" && isLoading/*isLoading && sender == "Assistant" && message == "Chargement..."*/) {
                                            gen = true
                                            Spacer(modifier = Modifier.height(16.dp))
                                            Icon(
                                                painter = if (isDarkTheme) logosombre else logoclair,
                                                contentDescription = "Icône avec dégradé",
                                                modifier = Modifier
                                                    .size(25.dp)
                                                    .graphicsLayer {
                                                        compositingStrategy =
                                                            CompositingStrategy.Offscreen
                                                    }
                                                    .drawWithCache {


                                                        val brush = Brush.linearGradient(
                                                            colors = listOf(
                                                                primaryColor,
                                                                onbackground
                                                            ),
                                                            start = Offset(waveOffset, 0f),
                                                            end = Offset(waveOffset + 200f, 200f)
                                                        )
                                                        onDrawWithContent {
                                                            drawContent()
                                                            drawRect(
                                                                brush = brush,
                                                                blendMode = BlendMode.SrcIn
                                                            )
                                                        }
                                                    }
                                            )
                                        }
                                    }
                                ),
                            )

                        } else if (myModels.find { it.id == index_models }?.genType == "text" && sender == "Assistant") {
                            Text(
                                text = annotatedString,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    brush = if (expanded.value && !error) gradient else null,
                                    fontStyle = if (error) FontStyle.Italic else FontStyle.Normal,
                                ),
                                color = if (!error) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error,

                                inlineContent = mapOf(
                                    "icon" to InlineTextContent(
                                        Placeholder(
                                            width = 20.sp, // Largeur de l'icône
                                            height = 20.sp, // Hauteur de l'icône
                                            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter // Alignement vertical
                                        )
                                    ) {
                                        if (lastMessage && isLoading/*isLoading && sender == "Assistant" && message == "Chargement..."*/) {
                                            gen = true
                                            Spacer(modifier = Modifier.height(16.dp))
                                            Icon(
                                                painter = if (isDarkTheme) logosombre else logoclair,
                                                contentDescription = "Icône avec dégradé",
                                                modifier = Modifier
                                                    .size(25.dp)
                                                    .graphicsLayer {
                                                        compositingStrategy =
                                                            CompositingStrategy.Offscreen
                                                    }
                                                    .drawWithCache {
                                                        // Définir ton brush (ici, un dégradé linéaire)


                                                        val brush = Brush.linearGradient(
                                                            colors = listOf(
                                                                primaryColor,
                                                                onbackground
                                                            ),
                                                            start = Offset(waveOffset, 0f),
                                                            end = Offset(waveOffset + 200f, 200f)
                                                        )
                                                        onDrawWithContent {
                                                            drawContent()
                                                            drawRect(
                                                                brush = brush,
                                                                blendMode = BlendMode.SrcIn
                                                            )
                                                        }
                                                    }
                                            )
                                        }
                                    }
                                ),
                            )
                        } else if (myModels.find { it.id == index_models }?.genType == "image" && sender == "Assistant") {
                            val byteArray = Base64.decode(message, Base64.DEFAULT)
                            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                            if (lastMessage && isLoading) {
                                Icon(
                                    painter = if (isDarkTheme) logosombre else logoclair,
                                    contentDescription = "Icône avec dégradé",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .graphicsLayer {
                                            compositingStrategy =
                                                CompositingStrategy.Offscreen
                                        }
                                        .drawWithCache {
                                            val brush = Brush.linearGradient(
                                                colors = listOf(
                                                    primaryColor,
                                                    onbackground
                                                ),
                                                start = Offset(waveOffset, 0f),
                                                end = Offset(waveOffset + 200f, 200f)
                                            )
                                            onDrawWithContent {
                                                drawContent()
                                                drawRect(
                                                    brush = brush,
                                                    blendMode = BlendMode.SrcIn
                                                )
                                            }
                                        }
                                )
                            } else {
                                bitmap?.asImageBitmap()?.let {
                                    Image(
                                        bitmap = it,
                                        contentDescription = "Image générée",
                                        modifier = Modifier.size(300.dp).align(Alignment.Start)
                                    )
                                }
                            }
                        }


                        if (sender == "Assistant" && !startOutput) {
                            DropdownMenu(
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value = false }) {

                                if (!error) {
                                    DropdownMenuItem( //logique à déterminer
                                        text = {
                                            Text(
                                                stringResource(id = R.string.post),
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                painterResource(id = R.drawable.posticon),
                                                contentDescription = "Poster",
                                                modifier = Modifier.size(30.dp),
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        },
                                        onClick = {
                                            if (myModels.filter { it.id == index_models }
                                                    .firstOrNull()?.genType == "text") {
                                                getPages.setNewPostContentOrURL(message)
                                                getPages.setNewPostContentType("text")
                                                getPages.setNewPostPrompt(
                                                    chatHistoryModel.getChatHistory()
                                                        .filter { it.index == index }
                                                        .firstOrNull()?.history?.takeWhile { it.role != "Assistant" || it.content.value != message }
                                                        ?.lastOrNull { it.role == "User" }?.content?.value
                                                        ?: ""
                                                )
                                                chatHistoryModel.setIsVisible(false)
                                                getPages.setNewPost(true)
                                                expanded.value = false
                                            } else if (myModels.filter { it.id == index_models }
                                                    .firstOrNull()?.genType == "image") {
                                                getPages.setNewPostContentOrURL(message)
                                                getPages.setNewPostContentType("image")
                                                getPages.setNewPostPrompt(
                                                    chatHistoryModel.getChatHistory()
                                                        .filter { it.index == index }
                                                        .firstOrNull()?.history?.takeWhile { it.role != "Assistant" || it.content.value != message }
                                                        ?.lastOrNull { it.role == "User" }?.content?.value
                                                        ?: ""
                                                )
                                                chatHistoryModel.setIsVisible(false)
                                                getPages.setNewPost(true)
                                                expanded.value = false

                                            } else if (myModels.filter { it.id == index_models }
                                                    .firstOrNull()?.genType == "video") {

                                            }
                                        }
                                    )
                                }

                                DropdownMenuItem( //logique à déterminer
                                    text = {
                                        Text(
                                            stringResource(id = R.string.réessayer),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.reprompticon),
                                            contentDescription = "Réessayer",
                                            tint = if (!error) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                                            modifier = Modifier.size(25.dp)

                                        )
                                    },
                                    onClick = {
                                        chatHistoryModel.regenerate(chatId)
                                        expanded.value = false
                                    }
                                )

                                if (!error) {

                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                stringResource(id = R.string.copier),
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                copyicon,
                                                contentDescription = "Copier",
                                                tint = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.size(25.dp)

                                            )
                                        },
                                        onClick = {
                                            if (myModels.find { it.id == index_models }?.genType == "text") {
                                                // Pour le texte, utilisez votre approche actuelle
                                                clipboardManager.setText(AnnotatedString(message))
                                            } else if (myModels.find { it.id == index_models }?.genType == "image") {
                                                try {
                                                    // Décoder la string base64 en bitmap
                                                    val imageBytes = Base64.decode(message, Base64.DEFAULT)
                                                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                                                    // Sauvegarder l'image temporairement et obtenir son URI
                                                    val imageUri = saveImageToCache(bitmap, context)

                                                    val clip = ClipData.newUri( context.contentResolver, "Image", imageUri )
                                                    // Créer un ClipEntry pour l'image
                                                    val clipEntry = ClipEntry(clip)

                                                    // Copier dans le presse-papier
                                                    clipboardManager.setClip(clipEntry)

                                                } catch (e: Exception) {
                                                    Log.e("ImageGenLog", "Erreur lors de la copie de l'image", e)
                                                }
                                            }

                                            expanded.value = false
                                        }
                                    )

                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                stringResource(id = R.string.partager),
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.shareicon),
                                                contentDescription = "Partager",
                                                tint = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.size(25.dp)

                                            )
                                        },
                                        onClick = {
                                            if (myModels.find { it.id == index_models }?.genType == "text") {
                                                val sendIntent = Intent().apply {
                                                    action = Intent.ACTION_SEND
                                                    putExtra(Intent.EXTRA_TEXT, message)
                                                    type = "text/plain"
                                                }
                                                context.startActivity(Intent.createChooser(sendIntent, "Partager via"))
                                            } else if (myModels.find { it.id == index_models }?.genType == "image") {
                                                // Assuming 'imageBase64' is your Base64 string without the "data:image/jpeg;base64," prefix
                                                try {
                                                    // Decode base64 to bitmap
                                                    val imageBytes = android.util.Base64.decode(message, android.util.Base64.DEFAULT)
                                                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                                                    // Create temporary file
                                                    val file = File(context.cacheDir, "shared_image.jpg")
                                                    val outputStream = FileOutputStream(file)
                                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                                                    outputStream.flush()
                                                    outputStream.close()

                                                    // Get URI using FileProvider
                                                    val imageUri = FileProvider.getUriForFile(
                                                        context,
                                                        "${context.packageName}.fileprovider", // Make sure this matches your manifest
                                                        file
                                                    )

                                                    val sendIntent = Intent().apply {
                                                        action = Intent.ACTION_SEND
                                                        putExtra(Intent.EXTRA_STREAM, imageUri)
                                                        type = "image/jpeg"
                                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                                    }
                                                    context.startActivity(Intent.createChooser(sendIntent, "Partager via"))
                                                } catch (e: Exception) {
                                                    Log.e("Image Chat bubble", "Erreur lors de la copie de l'image", e)
                                                }
                                            }
                                            expanded.value = false
                                        }
                                    )
                                }
                            }
                        } else if (sender == "User") {
                            DropdownMenu(
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value = false }) {


                                DropdownMenuItem(
                                    colors = MenuDefaults.itemColors(MaterialTheme.colorScheme.onSurface),
                                    text = {
                                        Text(
                                            stringResource(id = R.string.copier),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            copyicon,
                                            contentDescription = "Copier",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(25.dp)

                                        )
                                    },
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(message))

                                        expanded.value = false
                                    }
                                )

                                DropdownMenuItem(
                                    colors = MenuDefaults.itemColors(MaterialTheme.colorScheme.onSurface),
                                    text = {
                                        Text(
                                            stringResource(id = R.string.delete),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Red
                                            )
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.Red,
                                            modifier = Modifier.size(25.dp)

                                        )
                                    },
                                    onClick = {
                                        showWarningChatDeletion = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        var visible by remember { mutableStateOf(false) }

        BackHandler {
            if (viewImage) {
                visible = false
                viewImage = false
            } else {
                navcontroller.popBackStack()
            }
        }

        if (viewImage) {
            Popup(
                onDismissRequest = { viewImage = false },
                properties = PopupProperties(
                    clippingEnabled = true,
                )
            ) {


                LaunchedEffect(Unit) {
                    visible = true
                }
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(300)),
                    exit = fadeOut(tween(300))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ) {


                        var scale by remember { mutableStateOf(1f) }
                        var offset by remember { mutableStateOf(Offset.Zero) }
                        var rotation by remember { mutableStateOf(0f) }
                        val byteArray = Base64.decode(message, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

                        // Facteur de vitesse pour le déplacement
                        val panSpeed = 1f


                        // Niveau de zoom pour le double tap
                        val doubleTapZoomScale = 2.5f  // Le niveau de zoom quand on double-tape


                        bitmap.asImageBitmap().let { imageBitmap ->
                            // On calcule les dimensions du conteneur et de l'image
                            val imageSize = remember {
                                mutableStateOf(IntSize.Zero)
                            }
                            val containerSize = remember {
                                mutableStateOf(IntSize.Zero)
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onSizeChanged { containerSize.value = it }
                            ) {
                                Image(
                                    bitmap = imageBitmap,
                                    contentDescription = "Image zoomable",
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .onSizeChanged { imageSize.value = it }
                                        .graphicsLayer(
                                            scaleX = scale,
                                            scaleY = scale,
                                            rotationZ = rotation,
                                            translationX = offset.x,
                                            translationY = offset.y,
                                            transformOrigin = TransformOrigin(0.5f, 0.5f)
                                        )
                                        .pointerInput(Unit) {
                                            detectTransformGestures { centroid, pan, zoom, rotate ->
                                                // Limiter le zoom : minimum 1.0 (taille originale), maximum 5.0
                                                scale = (scale * zoom).coerceIn(1f, 5f)

                                                // Appliquer le facteur de vitesse au déplacement
                                                val acceleratedPan = Offset(pan.x * (panSpeed * scale), pan.y * (panSpeed * scale))

                                                // Calcul des limites de déplacement
                                                val scaledWidth = imageSize.value.width * scale
                                                val scaledHeight = imageSize.value.height * scale

                                                val maxX = (scaledWidth - containerSize.value.width) / 2f
                                                val maxY = (scaledHeight - containerSize.value.height) / 2f

                                                val newOffsetX = if (scaledWidth > containerSize.value.width) {
                                                    (offset.x + acceleratedPan.x).coerceIn(-maxX, maxX)
                                                } else {
                                                    0f
                                                }

                                                val newOffsetY = if (scaledHeight > containerSize.value.height) {
                                                    (offset.y + acceleratedPan.y).coerceIn(-maxY, maxY)
                                                } else {
                                                    0f
                                                }

                                                offset = Offset(newOffsetX, newOffsetY)
                                            }
                                        }
                                        // Gestion du double tap pour zoomer/dézoomer
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onDoubleTap = { tapOffset ->
                                                    // Calcule la position relative du tap sur l'image
                                                    // pour zoomer vers le point où l'utilisateur a tapé
                                                    val tapPosition = Offset(
                                                        x = tapOffset.x - size.width / 2,
                                                        y = tapOffset.y - size.height / 2
                                                    )

                                                    // Vérifier si nous sommes déjà zoomés
                                                    if (scale > 1.01f) {
                                                        // Si déjà zoomé, revenir à l'échelle initiale et centrer
                                                        scale = 1f
                                                        offset = Offset.Zero
                                                    } else {
                                                        // Sinon, zoomer au niveau spécifié
                                                        scale = doubleTapZoomScale

                                                        // Ajuster le décalage pour que le point tapé reste sous le doigt
                                                        // en tenant compte de la différence d'échelle
                                                        val scaleFactor = doubleTapZoomScale - 1f

                                                        offset = Offset(
                                                            x = -tapPosition.x * scaleFactor,
                                                            y = -tapPosition.y * scaleFactor
                                                        )

                                                        // Appliquer les limites de déplacement
                                                        val scaledWidth = imageSize.value.width * scale
                                                        val scaledHeight = imageSize.value.height * scale
                                                        val maxX = (scaledWidth - containerSize.value.width) / 2f
                                                        val maxY = (scaledHeight - containerSize.value.height) / 2f

                                                        offset = Offset(
                                                            x = offset.x.coerceIn(-maxX, maxX),
                                                            y = offset.y.coerceIn(-maxY, maxY)
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                )
                            }
                        }

                        IconButton(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = { viewImage = false }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.closeicon),
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        val downloaded = stringResource(id = R.string.downloaded)

                        Row(
                            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                modifier = Modifier,
                                onClick = {
                                    val formatter =
                                        SimpleDateFormat("ddMMyyyyHHmm", Locale.getDefault())
                                    val timestamp = formatter.format(Date())
                                    val filename =
                                        "Aletheia_${myModels.find { it.id == index_models }?.name}_$timestamp.jpg"

                                    saveBase64ImageToStorage(
                                        context,
                                        message,
                                        filename
                                    )
                                    showFloatingToast(context, downloaded)

                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.downloadicon),
                                    contentDescription = "Save image",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(30.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(30.dp))

                            IconButton(
                                modifier = Modifier,
                                onClick = {

                                    getPages.setNewPostContentOrURL(message)
                                    getPages.setNewPostContentType("image")
                                    getPages.setNewPostPrompt(
                                        chatHistoryModel.getChatHistory()
                                            .filter { it.index == index }
                                            .firstOrNull()?.history?.takeWhile { it.role != "Assistant" || it.content.value != message }
                                            ?.lastOrNull { it.role == "User" }?.content?.value
                                            ?: ""
                                    )
                                    chatHistoryModel.setIsVisible(false)
                                    getPages.setNewPost(true)
                                    expanded.value = false
                                    visible = false
                                    viewImage = false


                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.posticon),
                                    contentDescription = "Post",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(30.dp)

                                )
                            }
                        }


                    }
                }
            }
        }



    }
}

fun saveBase64ImageToStorage(context: Context, base64Image: String, filename: String) {
    try {
        // Decode base64 string to byte array
        val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)

        // For Android 10 (API 29) and above, use MediaStore
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            context.contentResolver.let { resolver ->
                val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                imageUri?.let { uri ->
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        outputStream.write(decodedBytes)
                    }
                }
            }
        } else {
            // For Android 9 and below
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            FileOutputStream(image).use { outputStream ->
                outputStream.write(decodedBytes)
            }
            // Make the file visible in gallery
            MediaScannerConnection.scanFile(context, arrayOf(image.absolutePath), null, null)
        }

    } catch (e: Exception) {
        Log.e("ImageSaver", "Error saving image: ${e.message}")
    }
}

private fun saveImageToCache(bitmap: android.graphics.Bitmap, context: android.content.Context): android.net.Uri {
    // Créer le dossier cache s'il n'existe pas
    val cachePath = java.io.File(context.cacheDir, "images")
    cachePath.mkdirs()

    // Créer le fichier image
    val file = java.io.File(cachePath, "image_${System.currentTimeMillis()}.png")

    // Sauvegarder le bitmap dans le fichier
    val outputStream = java.io.FileOutputStream(file)
    bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, outputStream)
    outputStream.close()

    // Obtenir l'URI via FileProvider
    return androidx.core.content.FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPostPage(
    profileViewModel: ProfileViewModel,
    themeViewModel: ThemeViewModel,
    username: String,
    getPages: GetPages,
    chatHistoryModel: ChatHistoryModel
) {


    val contentType by getPages.newPostContentType.collectAsState()
    val prompt by getPages.newPostPrompt.collectAsState()
    val collectContentOrURL by getPages.newPostContentOrURL.collectAsState()

    var contentOrURL = collectContentOrURL

    LaunchedEffect(Unit) {
        themeViewModel.toggleReelSysBar(false)
    }



    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val focusManager = LocalFocusManager.current

    val pagerState = rememberPagerState(pageCount = {2})
    var caption by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    var showSeeCaption by remember { mutableStateOf(false) }
    var showSeePrompt by remember { mutableStateOf(false) }
    var showChooseAudio by remember { mutableStateOf(false) }

    var editContent by remember { mutableStateOf(false) }

    BackHandler {

        if (editContent) {
            editContent = false
        } else if (showSeeCaption) {
            showSeeCaption = false
        } else if (showSeePrompt) {
            showSeePrompt = false
        } else if (showChooseAudio) {
            showChooseAudio = false
        } else {
            getPages.setNewPost(false)
        }
    }

    val customizePostList = listOf(
        listOf(stringResource(id = R.string.edit), { editContent = true }),
        listOf(stringResource(id = R.string.add_caption), { showSeeCaption = true }),
        listOf(stringResource(id = R.string.see_prompt), { showSeePrompt = true }),
        listOf(stringResource(id = R.string.choose_audio), { showChooseAudio = true })
    )

    var showMenu by remember { mutableStateOf(true) }

    val onbackground = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)

    val myModels by chatHistoryModel.myModels.collectAsState()

    val index_models by chatHistoryModel.index_models.collectAsState()

    val context = LocalContext.current // Utilisation de LocalContext pour obtenir le contexte
    val posted = stringResource(id = R.string.posted)

    var showDialogPost by remember { mutableStateOf(false) }


    val voice by profileViewModel.voice.collectAsState()
    val audioPrompt by profileViewModel.audioPrompt.collectAsState()
    val audioModel by profileViewModel.audioModel.collectAsState()

    AletheiaTheme(themeViewModel, isDarkTheme) {



        if (editContent) {
            BasicAlertDialog(
                onDismissRequest = { editContent = false },
                properties = DialogProperties(usePlatformDefaultWidth = false),
                modifier = Modifier

                    .windowInsetsPadding(WindowInsets.ime)


                    .clip(RoundedCornerShape(16.dp))
                    .height(400.dp)
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.background
                    )

            ) {
                var editableContent by remember { mutableStateOf(TextFieldValue(contentOrURL)) }
                val interactionSourceContent = remember { MutableInteractionSource() }
                val focusRequesterContent = remember { FocusRequester() }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)

                ) {
                    BasicTextField(
                        interactionSource = interactionSourceContent,
                        value = editableContent,
                        singleLine = false,
                        maxLines = 20,
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        onValueChange = {
                            editableContent = it
                            contentOrURL = it.text
                        },
                        modifier = Modifier
                            .focusRequester(focusRequesterContent)
                            .fillMaxSize()
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp)

                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = { editContent = false },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.check),
                                contentDescription = "Check",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }

                }
            }
        }

        var loading by remember { mutableStateOf(false) }

        if (showDialogPost) {
            BasicAlertDialog(
                onDismissRequest = { showDialogPost = false },
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
                ) {
                    Column {

                        if (!loading) {
                            Text(
                                stringResource(id = R.string.post_history),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 20.sp
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(16.dp)

                            )
                            Text(
                                stringResource(id = R.string.warning_post),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    textAlign = TextAlign.Center
                                ),

                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(horizontal = 16.dp)

                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                thickness = 1.dp
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(75.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(

                                    modifier = Modifier
                                        .height(75.dp)
                                        .weight(1f)
                                        .then (
                                            if (loading) {
                                                Modifier
                                            } else {
                                                Modifier
                                                    .clickable(
                                                        indication = LocalIndication.current,
                                                        interactionSource = remember { MutableInteractionSource() },
                                                        onClick = {
                                                            coroutineScope.launch {
                                                                loading = true
                                                                /*
                                                            if (contentType == "image") {
                                                                RetrofitInstance.api.saveImage(
                                                                    SendImage(
                                                                        "",
                                                                        bitmapToBase64(imageBitmap!!),
                                                                        imageName
                                                                    )
                                                                )
                                                            }

                                                             */

                                                                val audioUrl =
                                                                    RetrofitInstance.api.generateAudio(
                                                                        SQL().generateAudio(
                                                                            username,
                                                                            contentOrURL,
                                                                            audioPrompt,
                                                                            voice
                                                                        )
                                                                    )


                                                                RetrofitInstance.api.insert_content(
                                                                    SQL().insertIntoContent(
                                                                        UserName = username,
                                                                        ContentType = contentType,
                                                                        ContentURL = contentOrURL,
                                                                        AudioURL = audioUrl, //à changer : générer aussi l'audio voulu
                                                                        ContentPrompt = prompt,
                                                                        AIContentModel = myModels.filter { it.id == index_models }[0].name,
                                                                        AIAudioModel = audioModel,
                                                                        Caption = caption
                                                                    )
                                                                )
                                                                profileViewModel.addToUserPages(
                                                                    listOf(
                                                                        username,
                                                                        contentType,
                                                                        contentOrURL,
                                                                        audioUrl,
                                                                        prompt,
                                                                        myModels.filter { it.id == index_models }[0].name,
                                                                        audioModel,
                                                                        caption
                                                                    )
                                                                ) //ajoute sur le profil de l'utilisateur
                                                                showDialogPost = false

                                                                getPages.setNewPost(false)
                                                                chatHistoryModel.setIsVisible(true)
                                                                showFloatingToast(context, posted)

                                                                profileViewModel.setAudioPrompt("")
                                                                profileViewModel.setAudioModel("ChatGPT 4o TTS")
                                                                profileViewModel.setVoice("alloy")

                                                            }
                                                        }
                                                    )
                                            }
                                        ),

                                    ) {
                                    Text(stringResource(id = R.string.annuler_post),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.onBackground
                                    ),
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(8.dp))
                                }
                                VerticalDivider(
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                    thickness = 1.dp
                                )
                                Box(
                                    modifier = Modifier
                                        .height(75.dp)
                                        .weight(1f)
                                        .then (
                                            if (loading) {
                                                Modifier
                                            } else {
                                                Modifier
                                                    .clickable(
                                                        indication = LocalIndication.current,
                                                        interactionSource = remember { MutableInteractionSource() },
                                                        onClick = {
                                                            //rajouter une logique pour partager l'historique du contenu
                                                            coroutineScope.launch {
                                                                loading = true
                                                                if (contentType == "image") {
                                                                    RetrofitInstance.api.saveImage(
                                                                        SendImage(
                                                                            "",
                                                                            contentOrURL,
                                                                            username + "_${UUID.randomUUID()}"
                                                                        )
                                                                    )
                                                                }

                                                                val audioUrl = if (contentType == "text") RetrofitInstance.api.generateAudio(
                                                                    SQL().generateAudio(
                                                                        username,
                                                                        contentOrURL,
                                                                        audioPrompt,
                                                                        voice
                                                                    )
                                                                ) else ""

                                                                if (contentType == "image" || contentType == "video") {
                                                                    profileViewModel.setAudioModel("Suno AI")
                                                                }


                                                                RetrofitInstance.api.insert_content(
                                                                    SQL().insertIntoContent(
                                                                        UserName = username,
                                                                        ContentType = contentType,
                                                                        ContentURL = if (contentType == "text") contentOrURL else if (contentType == "image") username + "_${UUID.randomUUID()}" else "",
                                                                        AudioURL = audioUrl,
                                                                        ContentPrompt = prompt,
                                                                        AIContentModel = myModels.filter { it.id == index_models }[0].name,
                                                                        AIAudioModel = audioModel,
                                                                        Caption = caption
                                                                    )
                                                                )
                                                                profileViewModel.addToUserPages(
                                                                    listOf(
                                                                        username,
                                                                        contentType,
                                                                        contentOrURL,
                                                                        audioUrl,
                                                                        prompt,
                                                                        myModels.filter { it.id == index_models }[0].name,
                                                                        audioModel,
                                                                        caption
                                                                    )
                                                                ) //ajoute sur le profil de l'utilisateur
                                                                showDialogPost = false

                                                                getPages.setNewPost(false)
                                                                chatHistoryModel.setIsVisible(true)
                                                                showFloatingToast(context, posted)

                                                                profileViewModel.setAudioPrompt("")
                                                                profileViewModel.setAudioModel("ChatGPT 4o TTS")
                                                                profileViewModel.setVoice("alloy")

                                                            }
                                                        }
                                                    )
                                            }
                                        ),

                                    ) {
                                    Text(stringResource(id = R.string.confirmer_post),
                                        textAlign = TextAlign.Center,

                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            color = MaterialTheme.colorScheme.primary
                                        ),
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(8.dp))
                                }
                            }
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                //.windowInsetsPadding(WindowInsets.ime)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                        showSeeCaption = false
                        showSeePrompt = false
                        showChooseAudio = false
                    })
                }
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            when (contentType) {
                "text" ->
                    LazyColumn(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .alpha(if (editContent) 0f else 1f)
                            .fillMaxSize()
                            //.clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surface)

                    ) {
                        item {
                            Text(
                                text = contentOrURL,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        }
                    }

                "image" -> decodeBase64ToBitmap(contentOrURL)?.asImageBitmap()?.let {
                    Image(
                        it,
                        contentDescription = "Post image",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                "video" -> {} //A voir plus tard
            }




            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.background.copy(0.8f))
                    .width(150.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                if (showMenu) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        customizePostList.forEach { item ->
                            val name = item[0] as String
                            val onclick = item[1] as () -> Unit

                            //à modifier : choose audio pour images/vidéos -> musique (pas tts, ou tts pour le prompt ?)
                            if ((name == stringResource(id = R.string.edit) || name == stringResource(id = R.string.choose_audio)) && contentType != "text") {
                                return@forEach //équivalent de "continue" dans une boucle
                            }


                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(
                                        if (!showSeeCaption && !showSeePrompt && !showChooseAudio && !editContent) {
                                            Modifier.clickable(
                                                onClick = onclick,
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = LocalIndication.current
                                            )
                                        } else {
                                            Modifier
                                        }
                                    )

                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clip(CircleShape)
                                        .size(30.dp)

                                        .background(
                                            MaterialTheme.colorScheme.onBackground,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (name == stringResource(id = R.string.see_prompt)) R.drawable.prompticon else if (name == stringResource(
                                                    id = R.string.add_caption
                                                )
                                            ) R.drawable.captionicon else if (name == stringResource(
                                                    id = R.string.choose_audio
                                                )
                                            ) R.drawable.audioicon else R.drawable.editicon
                                        ),
                                        contentDescription = "Edit",
                                        tint = MaterialTheme.colorScheme.background,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                                Text(
                                    text = name,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (!showSeeCaption && !showSeePrompt && !showChooseAudio && !editContent) {
                                    Modifier.clickable(
                                        onClick = { showDialogPost = true },
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = LocalIndication.current
                                    )
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)

                                .clip(CircleShape)

                                .size(30.dp)
                                .background(
                                    MaterialTheme.colorScheme.onBackground,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.posticon),
                                contentDescription = "Edit",
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.post),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                NoRippleIconButton(
                    onClick = {
                        showMenu = !showMenu
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = if (showMenu) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Toggle menu",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }



            val offsetY = remember { Animatable(0f) }
            var inDrag by remember { mutableStateOf(false) }


            val alphaBackground by animateFloatAsState(
                targetValue = if (showSeeCaption || showSeePrompt || showChooseAudio) 0.8f else 0f,
                animationSpec = tween(durationMillis = 300)
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alphaBackground))
                .then (
                    if (showSeeCaption || showSeePrompt || showChooseAudio) {
                        Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    if (showSeeCaption) {
                                        showSeeCaption = false
                                    } else if (showSeePrompt) {
                                        showSeePrompt = false
                                    } else if (showChooseAudio) {
                                        showChooseAudio = false
                                    }
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
                                                if (showSeeCaption) {
                                                    showSeeCaption = false
                                                } else if (showSeePrompt) {
                                                    showSeePrompt = false
                                                } else if (showChooseAudio) {
                                                    showChooseAudio = false
                                                }
                                                delay(300)
                                                offsetY.snapTo(0f)
                                            } else {
                                                offsetY.animateTo(0f, animationSpec = tween(300))
                                            }
                                        }
                                    }
                                )
                            }
                    } else {
                        Modifier
                    }
                )

            )







            AnimatedVisibility(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {}
                    )
                    .align(Alignment.BottomCenter)
                ,
                visible = showChooseAudio,
                enter = slideInVertically(
                    initialOffsetY = { it }, // Limite du décalage initial
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = if (inDrag) ExitTransition.None else slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                Column(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            var dragDirection = 0f
                            detectVerticalDragGestures(
                                onVerticalDrag = { change, dragAmount ->
                                    change.consume() // Prevent conflicts with other gestures
                                    inDrag = true
                                    // Track latest drag direction for decision making
                                    dragDirection = dragAmount
                                    coroutineScope.launch {
                                        val newOffset = offsetY.value + dragAmount
                                        offsetY.snapTo(max(newOffset, 0f)) // Keep it ≥ 0
                                    }
                                },
                                onDragEnd = {
                                    coroutineScope.launch {
                                        // Only dismiss if:
                                        // 1. We're beyond the threshold AND
                                        // 2. The last direction was downward (positive dragDirection)
                                        inDrag = false
                                        if (offsetY.value > 100 && dragDirection > 0) {
                                            offsetY.animateTo(5000f, animationSpec = tween(300))
                                            showChooseAudio = false
                                            delay(300)
                                            offsetY.snapTo(0f) // Smooth return to zero
                                        } else {
                                            // Otherwise animate back to starting position
                                            offsetY.animateTo(
                                                0f,
                                                animationSpec = tween(300)
                                            )
                                        }
                                    }
                                }
                            )
                        }
                        .offset { IntOffset(0, offsetY.value.roundToInt()) } // Suivi fluide du drag
                        .fillMaxWidth()
                        .height(500.dp)
                        /*
                        .drawBehind {
                            val strokeWidth = 1.dp.toPx()
                            val shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                            val outline = shape.createOutline(size, layoutDirection, this)
                            drawOutline(
                                outline = outline,
                                color = onbackground, // Couleur de la bordure
                                style = Stroke(width = strokeWidth)
                            )

                        }

                         */
                        .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                        .background(MaterialTheme.colorScheme.background)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(modifier = Modifier.width(50.dp).height(5.dp).clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.onBackground.copy(0.5f)))
                    AudioPreview(profileViewModel, themeViewModel, voice, audioPrompt)
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }

            var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
            val interactionSource = remember { MutableInteractionSource() }
            val maxTextFieldLength = 300
            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current
            AnimatedVisibility(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {}
                    )
                    .align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.ime)

                ,
                visible = showSeeCaption,
                enter = slideInVertically(
                    initialOffsetY = { it }, // Limite du décalage initial
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = if (inDrag) ExitTransition.None else slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                Column(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            var dragDirection = 0f
                            detectVerticalDragGestures(
                                onVerticalDrag = { change, dragAmount ->
                                    change.consume() // Prevent conflicts with other gestures
                                    inDrag = true
                                    // Track latest drag direction for decision making
                                    dragDirection = dragAmount
                                    coroutineScope.launch {
                                        val newOffset = offsetY.value + dragAmount
                                        offsetY.snapTo(max(newOffset, 0f)) // Keep it ≥ 0
                                    }
                                },
                                onDragEnd = {
                                    coroutineScope.launch {
                                        // Only dismiss if:
                                        // 1. We're beyond the threshold AND
                                        // 2. The last direction was downward (positive dragDirection)
                                        inDrag = false
                                        if (offsetY.value > 100 && dragDirection > 0) {
                                            offsetY.animateTo(5000f, animationSpec = tween(300))
                                            showSeeCaption = false
                                            delay(300)
                                            offsetY.snapTo(0f) // Smooth return to zero
                                        } else {
                                            // Otherwise animate back to starting position
                                            offsetY.animateTo(
                                                0f,
                                                animationSpec = tween(300)
                                            )
                                        }
                                    }
                                }
                            )
                        }
                        .offset { IntOffset(0, offsetY.value.roundToInt()) } // Smooth drag tracking
                        .fillMaxWidth()
                        .height(500.dp)
                        /*
                        .drawBehind {
                            val strokeWidth = 1.dp.toPx()
                            val shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                            val outline = shape.createOutline(size, layoutDirection, this)
                            drawOutline(
                                outline = outline,
                                color = onbackground, // Couleur de la bordure
                                style = Stroke(width = strokeWidth)
                            )

                        }

                         */
                        .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                        .background(MaterialTheme.colorScheme.background)
                    ,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier.width(50.dp).height(5.dp).clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.onBackground.copy(0.5f)))
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            /*.border(
                                1.dp,
                                MaterialTheme.colorScheme.onBackground.copy(0.1f),
                                RoundedCornerShape(8.dp)
                            )*/
                    ) {
                        if (textFieldValue.text.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.nocaptionyet),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                ),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        BasicTextField(
                            interactionSource = interactionSource,
                            value = textFieldValue,
                            singleLine = false,
                            maxLines = 10,
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            onValueChange = {
                                if (it.text.length <= maxTextFieldLength) {
                                    textFieldValue = it
                                    caption = it.text
                                }
                            },
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.confirmer),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.primary,
                            ),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(top = 8.dp, end = 50.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    coroutineScope.launch {
                                        focusManager.clearFocus()
                                        showSeeCaption = false
                                        delay(500)
                                        offsetY.snapTo(0f)
                                    }
                                }
                                .padding(4.dp)
                        )

                    }
                    Spacer(modifier = Modifier.height(50.dp))
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
                                showSeePrompt = false
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
                                            showSeePrompt = false
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
                                                showSeePrompt = false
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
                                text = prompt,
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


        }

    }
}

fun decodeBase64ToBitmap(base64String: String): Bitmap? {
    return try {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        Log.e("ImageViewModel", "Erreur lors du décodage de l'image", e)
        null
    }
}


@Composable
fun AudioPreview(profileViewModel: ProfileViewModel, themeViewModel: ThemeViewModel, currentVoice: String, audioPrompt: String) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    val previewURL = RetrofitInstance.BASE_URL + "audio/tts_preview"

    val voicesURL = mapOf(
        "alloy" to "openai-fm-alloy-calm",
        "ash" to "openai-fm-ash-calm",
        "ballad" to "openai-fm-ballad-calm",
        "coral" to "openai-fm-coral-calm",
        "echo" to "openai-fm-echo-calm",
        "fable" to "openai-fm-fable-calm",
        "onyx" to "openai-fm-onyx-calm",
        "nova" to "openai-fm-nova-calm",
        "shimmer" to "openai-fm-shimmer-calm",
        "verse" to "openai-fm-verse-calm",
        "sage" to "openai-fm-sage-calm"
    )

    // Create a shared state to track the currently playing player
    val currentlyPlayingPlayer = remember { mutableStateOf<ExoPlayer?>(null) }

    AletheiaTheme(themeViewModel, isDarkTheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.voice),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(voicesURL.entries.toList()) { (voiceName, voiceId) ->
                    AudioPlayerCard(
                        profileViewModel,
                        currentVoice = currentVoice,
                        audioPrompt = audioPrompt,
                        voiceName = voiceName,
                        voiceId = voiceId,
                        previewURL = previewURL,
                        currentlyPlayingPlayer = currentlyPlayingPlayer
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerCard(
    profileViewModel: ProfileViewModel,
    currentVoice: String,
    audioPrompt: String,
    voiceName: String,
    voiceId: String,
    previewURL: String,
    currentlyPlayingPlayer: MutableState<ExoPlayer?>
) {
    val context = LocalContext.current
    val playerView = remember { PlayerView(context) }
    val player = remember { ExoPlayer.Builder(context).build() }
    val voiceDescription = getVoiceDescription(voiceName)
    val audioUrl = "$previewURL/$voiceId"

    var audioDialog by remember { mutableStateOf(false) }

    DisposableEffect(player) {
        playerView.player = player
        val mediaItem = MediaItem.fromUri(audioUrl)

        player.setMediaItem(mediaItem)
        player.prepare()

        onDispose {
            player.release()
            // Clean up reference when this player is disposed
            if (currentlyPlayingPlayer.value == player) {
                currentlyPlayingPlayer.value = null
            }
        }
    }

    if (audioDialog) {
        BasicAlertDialog(
            onDismissRequest = { audioDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier

                .windowInsetsPadding(WindowInsets.ime)


                .clip(RoundedCornerShape(16.dp))
                .height(500.dp)
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.background
                )

        ) {
            var editableAudioPrompt by remember { mutableStateOf(TextFieldValue(audioPrompt)) }
            val interactionSourceContent = remember { MutableInteractionSource() }
            val focusRequesterContent = remember { FocusRequester() }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)

            ) {
                Text(
                    text = stringResource(id = R.string.audioPrompt),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                BasicTextField(
                    interactionSource = interactionSourceContent,
                    value = editableAudioPrompt,
                    singleLine = false,
                    maxLines = 10,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    onValueChange = {
                        editableAudioPrompt = it
                        profileViewModel.setAudioPrompt(it.text)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .focusRequester(focusRequesterContent)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp)

                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(0.8f).offset(y = -16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.annuler),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                audioDialog = false
                            }
                            .padding(16.dp)
                    )


                    Text(
                        text = stringResource(id = R.string.confirmer),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                profileViewModel.setAudioPrompt(editableAudioPrompt.text)
                                profileViewModel.setAudioModel("ChatGPT 4o TTS")
                                profileViewModel.setVoice(voiceName)
                                audioDialog = false
                            }
                            .padding(16.dp)
                    )
                }

            }
        }
    }



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = voiceName.capitalize(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier

                        .wrapContentWidth()
                        .clip(RoundedCornerShape(8.dp))

                        .then (
                            if (voiceName == currentVoice) {
                                Modifier
                            } else {
                                Modifier
                                    .clickable(
                                        onClick = {
                                            audioDialog = true
                                        },
                                        enabled = true,
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = LocalIndication.current
                                    )
                            }
                        )

                        .background(if (voiceName == currentVoice) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary)

                ) {
                    Text(
                        text = if (voiceName == currentVoice) stringResource(id = R.string.used) else stringResource(id = R.string.use),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp)

                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                // Track progress state
                val isPlaying = remember { mutableStateOf(false) }
                val currentPosition = remember { mutableStateOf(0L) }
                val duration = remember { mutableStateOf(1L) } // Initialize to 1 to avoid division by zero

                // Update the playback states
                DisposableEffect(player) {
                    val listener = object : Player.Listener {
                        override fun onPlaybackStateChanged(state: Int) {
                            if (player.duration > 0) {
                                duration.value = player.duration
                            }
                        }

                        override fun onIsPlayingChanged(isPlayingValue: Boolean) {
                            isPlaying.value = isPlayingValue

                            // Update the currently playing player when this one starts playing
                            if (isPlayingValue) {
                                // If a different player is already playing, pause it
                                val previousPlayer = currentlyPlayingPlayer.value
                                if (previousPlayer != null && previousPlayer != player && previousPlayer.isPlaying) {
                                    previousPlayer.pause()
                                }
                                currentlyPlayingPlayer.value = player
                            }
                        }
                    }

                    player.addListener(listener)
                    onDispose {
                        player.removeListener(listener)
                    }
                }

                // Watch for changes to the currently playing player
                LaunchedEffect(currentlyPlayingPlayer.value) {
                    // If another player became the current player, pause this one
                    if (currentlyPlayingPlayer.value != null &&
                        currentlyPlayingPlayer.value != player &&
                        player.isPlaying) {
                        player.pause()
                    }
                }

                // Use LaunchedEffect to update position periodically on the main thread
                LaunchedEffect(isPlaying.value) {
                    while(isPlaying.value) {
                        // Only update if we have valid values
                        if (player.currentPosition >= 0 && player.duration > 0) {
                            currentPosition.value = player.currentPosition
                            duration.value = player.duration
                        }
                        delay(100) // Update 10 times per second
                    }
                }

                // Calculate progress safely
                val progressValue = remember(currentPosition.value, duration.value) {
                    if (duration.value > 0) {
                        (currentPosition.value.toFloat() / duration.value.toFloat()).coerceIn(0f, 1f)
                    } else {
                        0f // Default to 0 if we don't have valid duration
                    }
                }

                // Progress bar - use the safely calculated progress value
                LinearProgressIndicator(
                    progress = { progressValue },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    drawStopIndicator = {
                        drawStopIndicator(
                            drawScope = this,
                            stopSize = 0.dp,
                            color = Color.Transparent,
                            strokeCap = StrokeCap.Butt
                        )
                    }
                )

                // Play/Pause button
                IconButton(
                    onClick = {
                        if (player.isPlaying) {
                            player.pause()
                        } else {
                            player.seekTo(0)
                            player.play()
                        }
                    },
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .size(40.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        painter = if (isPlaying.value)
                            painterResource(id = R.drawable.pauseicon) else painterResource(id = R.drawable.playicon),
                        contentDescription = if (isPlaying.value) "Pause" else "Play",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Text(
                text = voiceDescription,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

// Extension function to capitalize the first letter
fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

@Composable
fun getVoiceDescription(voiceName: String): String {
    return when (voiceName) {
        "alloy" -> "A versatile voice with a balanced neutral tone."
        "ash" -> "A versatile voice with a neutral tone, higher pitch than Alloy."
        "ballad" -> "A voice with calm pacing and natural inflection."
        "coral" -> "A warm and clear voice with a pleasant timbre."
        "echo" -> "A voice with deeper resonance and clear articulation."
        "fable" -> "A voice with an enchanting and lyrical quality."
        "onyx" -> "A voice with strong, deep tones and authoritative presence."
        "nova" -> "A bright and energetic voice with medium pitch."
        "shimmer" -> "A crisp voice with a light, airy quality."
        "verse" -> "A voice with melodic inflection, great for narration."
        "sage" -> "A tranquil voice with measured, thoughtful delivery."
        else -> "A high-quality AI voice."
    }
}


/* Pour générer une image à partir d'un prompt, à améliorer et à intégrer dans la creationpage pour les modèles de génération d'image




var prompt by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    var showImage by remember { mutableStateOf(false) }

    val imageBitmap by getPages.imageBitmap.collectAsState()

    var displayedPrompt by remember { mutableStateOf("") }

    var imageName by remember { mutableStateOf("") }


// TextField pour changer la valeur de 'prompt'
                OutlinedTextField(
                    value = prompt,
                    onValueChange = { prompt = it },
                    label = { Text(stringResource(R.string.prompt), style = MaterialTheme.typography.bodyMedium) },
                    modifier = Modifier.fillMaxWidth(0.8f), // Largeur de 80% de l'écran
                )
                Spacer(modifier = Modifier.height(16.dp)) // Espacement

                // Bouton à droite pour envoyer le prompt
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                getPages.fetchImage(prompt)
                                displayedPrompt = prompt
                                prompt = ""
                                showImage = true
                                imageName = "${Random.nextInt(1, 1000)}_${removeAccents(username)}" //modifier en utilisant UUID cf.chathistorymodel
                            }
                        }, // Fonction pour envoyer le prompt
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text("Send", style = MaterialTheme.typography.bodyLarge) //mettre une icone
                    }
                }


                if (showImage) {

                    if (imageBitmap != null) {
                        Text(
                            text = displayedPrompt,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Image(
                            bitmap = imageBitmap!!.asImageBitmap(),
                            contentDescription = "Image générée",
                            modifier = Modifier.size(300.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        RetrofitInstance.api.saveImage(
                                            SendImage(
                                                "",
                                                bitmapToBase64(imageBitmap!!),
                                                imageName
                                            )
                                        )
                                        RetrofitInstance.api.matchNode(
                                            SQL().insertIntoContent(
                                                UserName = username,
                                                ContentType = "image",
                                                ContentURL = imageName,
                                                AudioURL = "audio", //à changer : générer aussi l'audio voulu
                                                ContentPrompt = displayedPrompt,
                                                AIContentModel = "Schnell",
                                                AIAudioModel = "Aiva AI",
                                                Caption = ""
                                            )
                                        )
                                        getPages.addToUserPages(listOf(username, "image", imageName, "audio", displayedPrompt, "Schnell", "Aiva AI", "")) //ajoute sur le profil de l'utilisateur
                                    }
                                }, // Fonction pour envoyer le prompt
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Text("Post", style = MaterialTheme.typography.bodyLarge) //mettre une icone
                            }
                        }
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp
                        )
                    }
                }


 */





@Composable
fun ChatLoadingScreen() {
    var dotIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            dotIndex = (dotIndex + 1) % 3
            delay(300)
        }
    }

    val onbackground = MaterialTheme.colorScheme.onBackground
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(3) { index ->
                val alpha by animateFloatAsState(if (index == dotIndex) 1f else 0.3f, label = "alpha")
                Canvas(modifier = Modifier.size(16.dp)) {
                    drawCircle(color = onbackground.copy(alpha = alpha))
                }
            }
        }
    }
}