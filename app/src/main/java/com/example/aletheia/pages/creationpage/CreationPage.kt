package com.example.aletheia.pages.creationpage

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.delay

import androidx.compose.ui.graphics.graphicsLayer


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.indication
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.aletheia.AletheiaTheme
import com.example.aletheia.NoRippleIconButton
import com.example.aletheia.R
import com.example.aletheia.RetrofitInstance
import com.example.aletheia.SQL
import com.example.aletheia.ThemeViewModel
import com.example.aletheia.pages.profilepage.getProfileImage
import com.example.aletheia.pages.profilepage.resizeBitmap
import com.example.aletheia.sendAI
import com.example.aletheia.viewmodels.Chat
import com.example.aletheia.viewmodels.ChatHistoryModel
import com.example.aletheia.viewmodels.ProfileViewModel
import com.example.aletheia.viewmodels.ChatHistoryModelFactory
import com.example.aletheia.viewmodels.ConversationHistory
import com.example.aletheia.viewmodels.GetPages
import com.example.aletheia.viewmodels.GlobalViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.io.ByteArrayOutputStream
import java.util.UUID
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CreationPage(navcontroller: NavHostController, profileViewModel: ProfileViewModel, chatHistoryModel: ChatHistoryModel, globalViewModel: GlobalViewModel, getPages: GetPages, themeViewModel: ThemeViewModel, username: String) {

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    val pagerState = rememberPagerState(0, 0f, {2})

    val newPost by getPages.newPost.collectAsState()
    LaunchedEffect(newPost) {
        if (newPost) {
            pagerState.animateScrollToPage(1,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
        } else {
            pagerState.animateScrollToPage(0,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == 0) {
            globalViewModel.setWhichPage("creationpage")
        } else if (pagerState.currentPage == 1) {
            globalViewModel.setWhichPage("newpostpage")
        }
    }

    AletheiaTheme(themeViewModel, isDarkTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HorizontalPager(
                userScrollEnabled = false,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) {
                    page ->
                when (page) {

                    1 -> {
                        NewPostPage(profileViewModel, themeViewModel, username, getPages, chatHistoryModel) //-> à modifier, doit être particulier à un post
                    }

                    0 -> {

                        AIModelsPage(navcontroller, chatHistoryModel, globalViewModel, getPages, themeViewModel, username)
                    }
                }
            }
        }
    }


}

@Composable
fun isKeyboardOpen(): Boolean {
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    return remember { derivedStateOf { keyboardHeight > 0 } }.value
}





@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AIModelsPage(navcontroller: NavHostController, chatHistoryModel: ChatHistoryModel, globalViewModel: GlobalViewModel, getPages: GetPages, themeViewModel: ThemeViewModel, username: String) {
    var userInput by remember { mutableStateOf("") }
    val AIOutput by chatHistoryModel.AIOutput.collectAsState()


    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val HapticPref by globalViewModel.hapticPref.collectAsState()

    val context = LocalContext.current // Utilisation de LocalContext pour obtenir le contexte
    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()


    val bordercolor = MaterialTheme.colorScheme.onBackground.copy(0.1f)

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()





    var isLoading by remember { mutableStateOf(false) }
    var begin by remember { mutableStateOf(false) }

    val sendicon = painterResource(id = R.drawable.sendicon)
    val stopicon = painterResource(id = R.drawable.stopicon)
    val logoclair = painterResource(id = R.drawable.aletheialogosombre)
    val logosombre = painterResource(id = R.drawable.aletheialogoclair)
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val isVisible by chatHistoryModel.isVisible.collectAsState()



    val vibrator = context.getSystemService(Vibrator::class.java)

    val index_models = chatHistoryModel.index_models.collectAsState()

    val chatHistory = chatHistoryModel.chatHistory.collectAsState().value
    val listTitles = chatHistoryModel.ListTitles.collectAsState()
    val index by chatHistoryModel.index.collectAsState()
    Log.d("index", "index : $index, $index_models")
    //val scrollPosition = prefs.getInt("listState_$index", 0)
    val messages by remember { mutableStateOf(ConversationHistory(listOf(Chat("User",mutableStateOf("")), Chat("Assistant",mutableStateOf(""))), index)) }


    val listState = rememberLazyListState()

    //var load by remember { mutableStateOf(false) }

    LaunchedEffect(index) {
        val restored = chatHistoryModel.restoreLazyListState()
        listState.scrollToItem(restored.firstVisibleItemIndex, restored.firstVisibleItemScrollOffset)
    }

    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        chatHistoryModel.saveLazyListState(listState)
    }

    val drawerstatecollect by globalViewModel.drawerState.collectAsState()

    var previousOffset by remember { mutableStateOf(0) }
    // Observer les changements de la taille de la liste
    LaunchedEffect(listState) {

        snapshotFlow { listState.layoutInfo.viewportEndOffset }
            .collectLatest { newViewportEndOffset ->
                if (globalViewModel.drawerState.value.isClosed) {
                    if (newViewportEndOffset < previousOffset) {
                        if (listState.firstVisibleItemIndex == (chatHistory
                                .filter { it.index == index }[0].history.size - 1)
                        ) {
                            listState.scrollBy(newViewportEndOffset.toFloat())
                        } else {
                            if (chatHistory
                                    .filter { it.index == index }[0].history.isNotEmpty()
                            ) {
                                listState.scrollToItem(
                                    chatHistory
                                        .filter { it.index == index }[0].history.size - 1, 0
                                )
                            }
                            listState.scrollBy(newViewportEndOffset.toFloat())
                        }
                    }
                    previousOffset = newViewportEndOffset
                }
            }

    }

    var goToAiResponse by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            goToAiResponse = true
        } else if (goToAiResponse) {
            val currentHistory = chatHistory.find { it.index == index }?.history
            if (!currentHistory.isNullOrEmpty()) {
                listState.scrollToItem(currentHistory.size - 1, 0)
            } else {
                listState.scrollToItem(0)
            }
            goToAiResponse = false
        }
    }




    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (!isVisible) 0.3f else 0f,
        animationSpec = tween(durationMillis = 600)
    )


    val addModelToList by chatHistoryModel.addModelToList.collectAsState()


    val myModels by chatHistoryModel.myModels.collectAsState()

    LaunchedEffect(drawerstatecollect.isClosed) {
        focusManager.clearFocus()
        chatHistoryModel.setIsVisible(globalViewModel.drawerState.value.isClosed)


        
        if (globalViewModel.drawerState.value.isOpen) {
            focusManager.clearFocus()
        }

        if (addModelToList) {
            if (drawerstatecollect.isOpen) {
                chatHistoryModel.toggleAddModelToList()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            chatHistoryModel.setIsVisible(false)
            if (addModelToList) {
                chatHistoryModel.toggleAddModelToList()
            }

        }
    }
    val firstTimeCreationPage by globalViewModel.firstTimeCreationPage.collectAsState()






    LaunchedEffect(Unit) {

        chatHistoryModel.setTmpNetHandler(true)
        RetrofitInstance.setNetworkAvailable(true)
        globalViewModel.drawerState.value = drawerState
    }

    /*
    val keyboardheight = WindowInsets.ime.getBottom(LocalDensity.current) //-> cette ligne fait ramer
    val lastItemIndex by remember { derivedStateOf{(chatHistoryModel.getChatHistory()[index] + messages).size - 1} }
    LaunchedEffect(Unit) {
        if (keyboardheight > 0) {
            listState.scrollToItem(lastItemIndex)
        }
    }ù

     */



    LaunchedEffect(isFocused) {
        if (isFocused) {
            chatHistoryModel.setIsFocused(true)
            if (addModelToList) {
                chatHistoryModel.toggleAddModelToList()
            }
        } else {
            chatHistoryModel.setIsFocused(false)
        }
    }

    var _offset by remember { mutableStateOf(0.dp) }

    val offset by animateDpAsState(
        targetValue = _offset,
        animationSpec = tween(durationMillis = 100)
    )


    LaunchedEffect(addModelToList) {
        if (addModelToList) {
            focusManager.clearFocus()
        }
    }

    val focusRequester = remember { FocusRequester() }

    val startOutput by chatHistoryModel.startOutput.collectAsState()


    val listChatTitles by chatHistoryModel.ListTitles.collectAsState()

    val maxTextFieldLength = 1200 //à réfléchir



    val isRegenerating by chatHistoryModel.isRegenerating.collectAsState()
    val regeneratePrompt by chatHistoryModel.regeneratePrompt.collectAsState()


    /*
    val imageBitmap by chatHistoryModel.imageBitmap.collectAsState()
    val responseBitmap by chatHistoryModel.responseBitmap.collectAsState()

     */



    LaunchedEffect(isRegenerating) {
        if (isRegenerating) {
            if (regeneratePrompt.isNotBlank() && !isLoading) {
                if (firstTimeCreationPage) {
                    globalViewModel.setFirstTimeCreationPage(false)
                }
                if (addModelToList) {
                    chatHistoryModel.toggleAddModelToList()
                }

                if (HapticPref) {
                    vibrator?.vibrate(
                        VibrationEffect.createPredefined(
                            VibrationEffect.EFFECT_CLICK
                        )
                    ) // Petite vibration
                }

                if (chatHistory.isEmpty()) {
                    chatHistoryModel.newChat()
                    chatHistoryModel.putIndex(chatHistory.lastOrNull()?.index ?: chatHistoryModel.chatHistory.value[0].index)
                }

                begin = true


                val input = mutableStateOf(regeneratePrompt)
                chatHistoryModel.setRegeneratePrompt("")
                messages.history.filter { it.role == "User" }[0].content = input
                chatHistoryModel.chat.value = messages

                focusManager.clearFocus()
                _offset = 0.dp

                //chatHistoryModel.setIsVisible(false)

                coroutineScope.launch {
                    listState.animateScrollToItem(chatHistory.filter { it.index == index }[0].history.size)
                }



                isLoading = true
                val charContextLength = 12000
                val context =
                    chatHistory.filter { it.index == index }[0].history.joinToString(
                        "\n"
                    ) { "${it.role} : ${it.content.value}" }
                        .takeLast(charContextLength)

                Log.d("context", "context : $context")

                coroutineScope.launch {
                    val response = withContext(Dispatchers.IO) {
                        RetrofitInstance.api.sendPrompt(
                            sendAI(
                                model = myModels.filter { it.id == index_models.value }[0].name,
                                prompt = "$context\n" + input.value //rajouter un contexte global à travers les conversations avec les infos importantes de l'utilisateur (demander directement au modèle de lister les infos)
                            )
                        )

                    }
                    response
                        .onStart {


                        }
                        .onCompletion {
                            // Marquer la fin du chargement
                            withContext(Dispatchers.Main) {
                                delay(10)
                                isLoading = false
                                chatHistoryModel.addChat()
                                messages.history = listOf(Chat("User",mutableStateOf("")), Chat("Assistant",mutableStateOf("")))
                                chatHistoryModel.clearOutput()
                                chatHistoryModel.setStartOutput(false)

                                if (chatHistoryModel.getListTitles().none { it.index == index }) {
                                    Log.d("onStart", index)
                                    chatHistoryModel.addTitle("NewChat_$index", index)
                                }
                            }

                        }
                        .collect { part ->
                            // Mettre à jour l'UI avec chaque partie de la réponse

                            withContext(Dispatchers.Main) {

                                // Ajouter le nouveau morceau à la réponse en cours
                                chatHistoryModel.addToOutput(part)
                                if (HapticPref) {
                                    vibrator?.vibrate(
                                        VibrationEffect.createPredefined(
                                            VibrationEffect.EFFECT_TICK
                                        )
                                    ) // Petite vibration
                                }
                                delay(10)
                                // Mettre à jour le message avec la réponse complète jusqu'à présent
                                //messages.history = messages.history.filterNot { it.role == "Assistant" }
                                messages.history.filter { it.role == "Assistant" }[0].content.value = AIOutput
                                Log.d("messages history filter", "messages history filter : ${messages.history.filter { it.role == "Assistant" }[0].content}")
                                chatHistoryModel.setStartOutput(true)

                                chatHistoryModel.chat.value = messages
                                Log.d("messages collect", "messages : $messages")
                                Log.d("collect : ", "part : $part")

                                // Faire défiler la liste pour afficher la dernière réponse
                                //listState.scrollTo(500.dp)
                            }
                        }


                    /*
                    messages =
                        messages.filterNot { it.first == "Assistant" && it.second == "Chargement..." }
                    messages = messages + ("Assistant" to response)
                    chatHistoryModel.chat.value = messages
                    chatHistoryModel.addChat()
                    messages = listOf()

                    isLoading = false

                     */


                }

            }
            chatHistoryModel.setIsRegenerating(false)
        }
    }


    val RetrofitNetValue by RetrofitInstance.isNetworkAvailable.collectAsState()
    val networkAvailable by chatHistoryModel.networkAvailable.collectAsState()
    val tmpNetHandler by chatHistoryModel.tmpNetHandler.collectAsState()



    var isFirstLaunch by remember { mutableStateOf(true) }


    /*
    LaunchedEffect(RetrofitNetValue) {
        if (isFirstLaunch) {
            isFirstLaunch = false // Marque le premier lancement comme terminé
            return@LaunchedEffect
        }
        if (!RetrofitNetValue && HapticPref) {
            triggerRisingVibrationWithClick(context)
        }
    }

     */



    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {

        ModalNavigationDrawer(
            gesturesEnabled = !(listTitles.value.isEmpty() && chatHistory.filter { it.index == index }.firstOrNull()?.history.isNullOrEmpty()),
            drawerState = drawerState,
            drawerContent = {
                ChatSidebar( myModels, index_models.value, drawerState, globalViewModel, themeViewModel, chatHistoryModel)
            }
        ) {



            Box(
                modifier = Modifier
                    .fillMaxSize()

            ) {

                if (tmpNetHandler) {
                    Box(modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures {
                                focusManager.clearFocus() // Enlève le focus des champs
                            }
                        }
                        .fillMaxSize()) {
                        ChatLoadingScreen()
                    }
                } else {

                    if (networkAvailable && RetrofitNetValue && (firstTimeCreationPage && chatHistoryModel.getListTitles().isEmpty() && chatHistory.filter { it.index == index }.firstOrNull()?.history.isNullOrEmpty()) && (index_models.value == "meta-llama-3.1-8b-instruct") && !begin ) {

                        Log.d("AIModelsPage", "1")


                        Box(
                            modifier = Modifier
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        if (addModelToList) {
                                            chatHistoryModel.toggleAddModelToList()
                                        }
                                        focusManager.clearFocus() // Enlève le focus des champs
                                    }
                                }
                                .windowInsetsPadding(WindowInsets.ime)
                                .fillMaxSize()

                        ) {
                            var displayedtext by remember { mutableStateOf("") }
                            val text = stringResource(id = R.string.intro_first_model)

                            val alpha by animateFloatAsState(
                                targetValue = if (visible) 1f else 0f,
                                animationSpec = tween(durationMillis = 300)
                            )

                            LaunchedEffect(Unit) {
                                visible = false
                                displayedtext = ""
                                text.forEach { char ->
                                    delay(30)
                                    displayedtext += char
                                }
                                visible = true
                            }
                            Text(
                                text = displayedtext,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .offset(y = -100.dp)
                                    .padding(16.dp)
                            )
                            if (visible) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.align(Alignment.Center)
                                        .padding(end = 20.dp) //cas particulier

                                ) {
                                    Image(
                                        painter = if (isDarkTheme) logosombre else logoclair,
                                        contentDescription = null,
                                        modifier = Modifier.size(100.dp)
                                            .alpha(alpha),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = "x",
                                        fontSize = 15.sp,
                                        modifier = Modifier.alpha(alpha)
                                    )
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.metalogo), //remplacer par le logo du partenaire
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(start = 20.dp) //cas particulier
                                            .size(80.dp)
                                            .alpha(alpha),
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }
                    } else {
                        Log.d("AIModelsPage", "2")

                        Box(modifier = Modifier

                            .pointerInput(Unit) {
                                detectTapGestures {
                                    if (addModelToList) {
                                        chatHistoryModel.toggleAddModelToList()
                                    }
                                    focusManager.clearFocus() // Enlève le focus des champs
                                }
                            }
                            .windowInsetsPadding(WindowInsets.ime) //ajoute un padding aussi pour le lazycolumn mais à l'intérieur de celui-ci, ce qui fait qu'il faut scroller dans le contenu pour voir le padding
                            .fillMaxSize()

                        ) {
                            Log.d("AIModelsPage", "index : $index")
                            if (networkAvailable && RetrofitNetValue && (chatHistory.filter { it.index == index }.firstOrNull()?.history.isNullOrEmpty()) && !isLoading) {
                                Log.d("AIModelsPage", "3")
                                var displayedtext by remember { mutableStateOf("") }
                                val text = stringResource(id = R.string.new_chat_ia) + " " + "$username..."
                                LaunchedEffect(Unit) {
                                    displayedtext = ""
                                    text.forEach { char ->
                                        delay(30)
                                        displayedtext += char
                                    }
                                }
                                Text(
                                    text = displayedtext,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .offset(y = -100.dp)
                                )
                            } else {

                                Log.d("AIModelsPage", "4")



                                if (RetrofitNetValue) {
                                    LazyColumn(
                                        state = listState,
                                        modifier = Modifier

                                            //.alpha(if (load) 0f else 1f) //pour cacher les animatescroll
                                            //.heightIn(max = maxHeight - 160.dp) // Ajuste la hauteur pour laisser de l'espace pour le Row
                                            .fillMaxSize()



                                    ) {
                                        items(
                                            (chatHistory.filter { it.index == index }
                                                .firstOrNull()?.history ?: listOf(
                                                Chat("User", mutableStateOf("")),
                                                Chat("Assistant", mutableStateOf("")))) + messages.history
                                        ) { (sender, message, chatId) ->
                                            ChatBubble(
                                                navcontroller,
                                                lastMessage = message == messages.history.filter { it.content == message}.firstOrNull()?.content,
                                                getPages,
                                                sender,
                                                message.value,
                                                isLoading,
                                                themeViewModel,
                                                username,
                                                chatHistoryModel,
                                                chatId
                                            )


                                        }
                                        item {

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
                                            if (!startOutput && isLoading) {
                                                Icon(
                                                    painter = if (isDarkTheme) logosombre else logoclair,
                                                    contentDescription = "Icône avec dégradé",
                                                    modifier = Modifier
                                                        .size(50.dp).offset(x = 16.dp)
                                                        .graphicsLayer {
                                                            compositingStrategy = CompositingStrategy.Offscreen
                                                        }
                                                        .drawWithCache {
                                                            // Définir ton brush (ici, un dégradé linéaire)


                                                            val brush = Brush.linearGradient(
                                                                colors = listOf(primaryColor, onbackground),
                                                                start = Offset(waveOffset, 0f),
                                                                end = Offset(waveOffset + 200f, 200f)
                                                            )
                                                            onDrawWithContent {
                                                                drawContent()
                                                                drawRect(brush = brush, blendMode = BlendMode.SrcIn)
                                                            }
                                                        }
                                                )
                                            }
                                            Spacer(
                                                modifier = Modifier
                                                    .height(120.dp)
                                            )
                                        }
                                    }
                                } else {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.erreur_de_connexion),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = MaterialTheme.colorScheme.error
                                            ),
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        IconButton(
                                            onClick = {
                                                RetrofitInstance.setNetworkAvailable(true)
                                                chatHistoryModel.setTmpNetHandler(true)

                                            },
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.reprompticon),
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }

                                /*
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .fillMaxHeight()
                                        .offset(x = 12.5.dp)
                                        .width(8.dp)
                                        .simpleVerticalScrollbar(state = listState, width = 8.dp)
                                )

                                 */
                            }

                        }
                    }
                }

                var inDrag by remember { mutableStateOf(false) }
                val offsetY = remember { Animatable(0f) }
                val alphaBackground by animateFloatAsState(
                    targetValue = if (addModelToList) 0.8f else 0f,
                    animationSpec = tween(durationMillis = 300)
                )
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Black.copy(alphaBackground))
                        .then (
                            if (addModelToList)
                                Modifier
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = {
                                            chatHistoryModel.toggleAddModelToList()
                                        }
                                    )
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
                                                    offsetY.snapTo(min(newOffset, 0f)) // Keep it ≥ 0
                                                }
                                            },
                                            onDragEnd = {
                                                coroutineScope.launch {
                                                    // Only dismiss if:
                                                    // 1. We're beyond the threshold AND
                                                    // 2. The last direction was downward (positive dragDirection)
                                                    inDrag = false
                                                    if (offsetY.value < -100 && dragDirection < 0) {
                                                        offsetY.animateTo(-5000f, animationSpec = tween(300))
                                                        chatHistoryModel.toggleAddModelToList()
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
                            else Modifier
                        )

                )
                AnimatedVisibility(
                    visible = addModelToList,
                    enter = slideInVertically(
                        initialOffsetY = { -it }, // Limite du décalage initial
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = if (inDrag) ExitTransition.None else slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = 300)
                    ),
                ) {

                    Box(
                        modifier = Modifier
                            .offset { IntOffset(0, offsetY.value.roundToInt()) } // Suivi fluide du drag

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
                                            offsetY.snapTo(min(newOffset, 0f)) // Keep it ≥ 0
                                        }
                                    },
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            // Only dismiss if:
                                            // 1. We're beyond the threshold AND
                                            // 2. The last direction was downward (positive dragDirection)
                                            inDrag = false
                                            if (offsetY.value < -100 && dragDirection < 0) {
                                                offsetY.animateTo(-5000f, animationSpec = tween(300))
                                                chatHistoryModel.toggleAddModelToList()
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
                            .fillMaxWidth()
                            .height(250.dp)
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                val shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                                val outline = shape.createOutline(size, layoutDirection, this)
                                drawOutline(
                                    outline = outline,
                                    color = bordercolor, // Couleur de la bordure
                                    style = Stroke(width = strokeWidth)
                                )

                            }
                            .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                            .background(MaterialTheme.colorScheme.background),
                    ) {
                        AddModelPage(
                            chatHistoryModel,
                            globalViewModel,
                            getPages,
                            themeViewModel,
                            username
                        )
                    }

                }

                /*
                var textFieldValue by remember { mutableStateOf("") }
                var previousText by remember { mutableStateOf("") }

                 */
                AnimatedVisibility(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures {}
                        }
                        .windowInsetsPadding(WindowInsets.ime)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    visible = isVisible,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                ) {
                    val keyboardController = LocalSoftwareKeyboardController.current

                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    if (!tmpNetHandler && RetrofitNetValue) {
                                        focusRequester.requestFocus()
                                        keyboardController?.show()
                                    }

                                }
                            )
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                val shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                                val outline = shape.createOutline(size, layoutDirection, this)
                                drawOutline(
                                    outline = outline,
                                    color = bordercolor, // Couleur de la bordure
                                    style = Stroke(width = strokeWidth)
                                )

                            }
                            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .background(MaterialTheme.colorScheme.background),

                        ) {
                        Row(
                            modifier = Modifier
                                .height(50.dp + offset)

                                ,


                            verticalAlignment = Alignment.CenterVertically,

                            ) {

                            Box(
                                modifier = Modifier.padding(4.dp).fillMaxSize()

                            ) {
                                Log.d(
                                    "AIMODELSPAGE",
                                    "index_models.value : $index_models, myModels : $myModels"
                                )

                                if (!isFocused && userInput.isEmpty()) Text(
                                    text = if (RetrofitNetValue && !tmpNetHandler)stringResource(id = R.string.envoyer_msg_a) + " " + myModels.filter { it.id == index_models.value }[0].name else stringResource(id = R.string.attente_de_co),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                    ),
                                    modifier = Modifier.padding(start = 20.dp, top = 16.dp)
                                )



                                BasicTextField(
                                    enabled = !tmpNetHandler && RetrofitNetValue,
                                    onTextLayout = { textLayoutResult: TextLayoutResult ->
                                        _offset = 20.dp * textLayoutResult.lineCount.coerceAtMost(7) - 20.dp
                                    },
                                    interactionSource = interactionSource,
                                    value = userInput,
                                    onValueChange = { newValue ->

                                        if (newValue.length <= maxTextFieldLength) {
                                            userInput = newValue
                                        } else {
                                            userInput = newValue.substring(0, maxTextFieldLength)
                                        }

                                        /*
                                        // Calcul du nombre de sauts de ligne
                                        val lineCount = newValue.count { it == '\n' }

                                        // Ajustement dynamique de l'offset
                                          // Limitez à 7 lignes par exemple

                                        // Gestion de la suppression
                                        if (newValue.length < previousText.length) {
                                            val deletedContent = previousText.substring(
                                                newValue.length,
                                                previousText.length
                                            )
                                            if (deletedContent.contains("\n")) {
                                                // Logique de réduction de l'offset si une ligne est supprimée
                                            }
                                        }

                                        // Mise à jour des états
                                        previousText = newValue
                                        textFieldValue = newValue

                                         */
                                    },
                                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onBackground,

                                        ),
                                    singleLine = false,
                                    maxLines = 8,


                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .focusRequester(focusRequester)
                                        .offset(y = 16.dp)
                                        .padding(start = 20.dp, end = 20.dp),
                                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.None),

                                    /*
                                    keyboardActions = KeyboardActions(onSend = {
                                        if (userInput.isNotBlank() && !isLoading) {
                                            if (chatHistoryModel.getChatHistory().isEmpty()) {
                                                chatHistoryModel.newChat()
                                                chatHistoryModel.putIndex(chatHistoryModel.getChatHistory().size - 1)
                                            }
                                            begin = true
                                            val input = userInput
                                            userInput = ""
                                            messages = messages + ("User" to input)
                                            chatHistoryModel.chat.value = messages
                                            focusManager.clearFocus()
                                            isVisible = false

                                            coroutineScope.launch {
                                                listState.animateScrollToItem(chatHistoryModel.getChatHistory()[index].size)
                                            }
                                            messages = messages + ("Assistant" to "Chargement...")
                                            chatHistoryModel.chat.value = messages

                                            isLoading = true
                                            val charContextLength = 12000 //à estimer...

                                            val context = chatHistoryModel.getChatHistory()[index].joinToString("\n") { "${it.first} : ${it.second}" }.takeLast(charContextLength)

                                            coroutineScope.launch {

                                                val response = withContext(Dispatchers.IO) {
                                                    simulateAiResponse(
                                                        RetrofitInstance.api.sendPrompt(
                                                            "$context\n" + input
                                                        )
                                                    )
                                                }

                                                messages =
                                                    messages.filterNot { it.first == "Assistant" && it.second == "Chargement..." }
                                                messages = messages + ("Assistant" to response)
                                                chatHistoryModel.chat.value = messages
                                                chatHistoryModel.addChat()
                                                messages = listOf()

                                                isLoading = false

                                            }

                                        }
                                    })
                                        */


                                )
                            }

                            /*
                        OutlinedTextField(
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = if (isDarkTheme) Color(0xFF1A1A1A) else Color.White,
                                unfocusedContainerColor = if (isDarkTheme) Color(0xFF1A1A1A) else Color.White,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                            ),
                            interactionSource = interactionSource,
                            value = userInput,
                            onValueChange = {
                                userInput = it
                            },
                            placeholder = {
                                if (!isFocused) Text(
                                    stringResource(id = R.string.envoyer_msg_a) + " " + models[index_models.value],
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            },
                            modifier = Modifier
                                .weight(1f),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                            keyboardActions = KeyboardActions(onSend = {
                                if (userInput.isNotBlank() && !isLoading) {
                                    if (chatHistoryModel.getChatHistory().isEmpty()) {
                                        chatHistoryModel.newChat()
                                        chatHistoryModel.putIndex(chatHistoryModel.getChatHistory().size - 1)
                                    }
                                    begin = true
                                    val input = userInput
                                    userInput = ""
                                    messages = messages + ("User" to input)
                                    chatHistoryModel.chat.value = messages
                                    focusManager.clearFocus()
                                    isVisible = false

                                    coroutineScope.launch {
                                        listState.animateScrollToItem(chatHistoryModel.getChatHistory()[index].size)
                                    }
                                    messages = messages + ("Assistant" to "Chargement...")
                                    chatHistoryModel.chat.value = messages

                                    isLoading = true
                                    val charContextLength = 12000 //à estimer...

                                    val context = chatHistoryModel.getChatHistory()[index].joinToString("\n") { "${it.first} : ${it.second}" }.takeLast(charContextLength)

                                    coroutineScope.launch {

                                        val response = withContext(Dispatchers.IO) {
                                            simulateAiResponse(
                                                RetrofitInstance.api.sendPrompt(
                                                    "$context\n" + input
                                                )
                                            )
                                        }

                                        messages =
                                            messages.filterNot { it.first == "Assistant" && it.second == "Chargement..." }
                                        messages = messages + ("Assistant" to response)
                                        chatHistoryModel.chat.value = messages
                                        chatHistoryModel.addChat()
                                        messages = listOf()

                                        isLoading = false

                                    }

                                }
                            })
                        )

                         */

                        }

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
                                .align(Alignment.End)
                            ,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {


                            if (true) { //condition à changer
                                NoRippleIconButton( //noripple à changer
                                    onClick = {
                                        //start the process
                                    },
                                    modifier = Modifier.size(50.dp)
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.pythiaconnectstart),
                                        contentDescription = "Connect to AI",
                                        modifier = Modifier.size(100.dp),
                                        tint = MaterialTheme.colorScheme.onBackground.copy(0.5f), //copy à enlever
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = {
                                        //stop the process
                                    },
                                    modifier = Modifier.size(50.dp)
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.pythiaconnectstop),
                                        contentDescription = "Connect to AI",
                                        modifier = Modifier.size(100.dp),
                                        tint = MaterialTheme.colorScheme.onBackground,
                                    )
                                }
                            }

                            IconButton(
                                colors = IconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onBackground,
                                    containerColor = MaterialTheme.colorScheme.background,
                                    disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                    disabledContainerColor = MaterialTheme.colorScheme.background.copy(0.5f)
                                ),
                                enabled = chatHistory
                                    .firstOrNull { it.index == index }
                                    ?.history
                                    ?.isNotEmpty() ?: false,
                                onClick = {

                                    if (chatHistory.any { it.history.isEmpty() }) {
                                        chatHistoryModel.putIndex(
                                            chatHistory
                                                .find { it.history.isEmpty() }
                                                !!.index
                                        )
                                    } else {
                                        chatHistoryModel.newChat()
                                        chatHistoryModel.putIndex(
                                            chatHistory.lastOrNull()?.index
                                                ?: UUID.randomUUID().toString()
                                        )
                                    }
                                    //triggerRisingVibrationWithClick(context)

                                },
                            ) {
                                Icon(painter = painterResource(id = R.drawable.newchaticon_ai), contentDescription = "NewChat",
                                    modifier = Modifier.size(35.dp),
                                )
                            }

                            if (!isLoading) {
                                IconButton(
                                    onClick = {
                                        try {
                                            chatHistoryModel.setNetwork(true)
                                            if (userInput.isNotBlank() && !isLoading) {
                                                if (firstTimeCreationPage) {
                                                    globalViewModel.setFirstTimeCreationPage(false)
                                                }
                                                if (addModelToList) {
                                                    chatHistoryModel.toggleAddModelToList()
                                                }

                                                if (HapticPref) {
                                                    vibrator?.vibrate(
                                                        VibrationEffect.createPredefined(
                                                            VibrationEffect.EFFECT_CLICK
                                                        )
                                                    ) // Petite vibration
                                                }

                                                if (chatHistory.isEmpty()) {
                                                    chatHistoryModel.newChat()
                                                    chatHistoryModel.putIndex(chatHistory.lastOrNull()?.index ?: chatHistoryModel.chatHistory.value[0].index)
                                                }

                                                begin = true


                                                val input = mutableStateOf(userInput)
                                                userInput = ""
                                                messages.history.filter { it.role == "User" }[0].content = input
                                                chatHistoryModel.chat.value = messages

                                                focusManager.clearFocus()
                                                _offset = 0.dp

                                                //chatHistoryModel.setIsVisible(false)

                                                coroutineScope.launch {
                                                    try {
                                                        chatHistoryModel.setNetwork(true)
                                                        listState.animateScrollToItem(
                                                            chatHistory
                                                                .filter { it.index == index }[0].history.size
                                                        )
                                                    } catch (e: Exception) {
                                                        chatHistoryModel.setNetwork(false)
                                                        Log.d("AI MODELS PAGE", "Exception sending prompt : $e")
                                                    }
                                                }



                                                isLoading = true
                                                val charContextLength = 12000
                                                Log.d("AIModelPage", "MARKER")

                                                val filtered = chatHistory.filter { it.index == index }
                                                Log.d("filtered", "size : ${filtered.size}")

                                                var AIcontext by mutableStateOf("")

                                                if (filtered.isNotEmpty()) {
                                                    AIcontext = filtered[0].history.joinToString("\n") {
                                                        "${it.role} : ${it.content.value}"
                                                    }.takeLast(charContextLength)

                                                    Log.d("context", "context : $AIcontext")
                                                } else {
                                                    Log.w("AIModelPage", "No history found for index $index")
                                                }


                                                coroutineScope.launch {
                                                    try {
                                                        chatHistoryModel.setNetwork(true)
                                                        Log.d("ImageGenLog", "condition : ${myModels.find { it.id == index_models.value }?.genType == "text"}")
                                                        if (myModels.find { it.id == index_models.value }?.genType == "text") {
                                                            val response = withContext(Dispatchers.IO) {
                                                                RetrofitInstance.api.sendPrompt(
                                                                    sendAI(
                                                                        model = myModels.filter { it.id == index_models.value }[0].name,
                                                                        prompt = "$AIcontext\n" + input.value //rajouter un contexte global à travers les conversations avec les infos importantes de l'utilisateur (demander directement au modèle de lister les infos)
                                                                    )
                                                                )

                                                            }
                                                            response
                                                                .onStart {


                                                                }
                                                                .onCompletion {
                                                                    // Marquer la fin du chargement
                                                                    withContext(Dispatchers.Main) {
                                                                        delay(10)
                                                                        isLoading = false
                                                                        chatHistoryModel.addChat()
                                                                        messages.history = listOf(Chat("User",mutableStateOf("")), Chat("Assistant",mutableStateOf("")))
                                                                        chatHistoryModel.clearOutput()
                                                                        chatHistoryModel.setStartOutput(false)

                                                                        if (chatHistoryModel.getListTitles().none { it.index == index }) {
                                                                            Log.d("onStart", index)
                                                                            chatHistoryModel.addTitle("NewChat_$index", index)
                                                                        }
                                                                    }
                                                                }
                                                                .collect { part ->
                                                                    // Mettre à jour l'UI avec chaque partie de la réponse

                                                                    withContext(Dispatchers.Main) {

                                                                        // Ajouter le nouveau morceau à la réponse en cours
                                                                        chatHistoryModel.addToOutput(part)
                                                                        if (HapticPref) {
                                                                            vibrator?.vibrate(
                                                                                VibrationEffect.createPredefined(
                                                                                    VibrationEffect.EFFECT_TICK
                                                                                )
                                                                            ) // Petite vibration
                                                                        }
                                                                        delay(10)
                                                                        // Mettre à jour le message avec la réponse complète jusqu'à présent
                                                                        //messages.history = messages.history.filterNot { it.role == "Assistant" }
                                                                        messages.history.filter { it.role == "Assistant" }[0].content.value = AIOutput
                                                                        Log.d("messages history filter", "messages history filter : ${messages.history.filter { it.role == "Assistant" }[0].content}")
                                                                        chatHistoryModel.setStartOutput(true)

                                                                        chatHistoryModel.chat.value = messages
                                                                        Log.d("messages collect", "messages : $messages")
                                                                        Log.d("collect : ", "part : $part")

                                                                        // Faire défiler la liste pour afficher la dernière réponse
                                                                        //listState.scrollTo(500.dp)
                                                                    }
                                                                }


                                                            /*
                                                            messages =
                                                                messages.filterNot { it.first == "Assistant" && it.second == "Chargement..." }
                                                            messages = messages + ("Assistant" to response)
                                                            chatHistoryModel.chat.value = messages
                                                            chatHistoryModel.addChat()
                                                            messages = listOf()

                                                            isLoading = false

                                                             */
                                                        } else if (myModels.find { it.id == index_models.value }?.genType == "image") {
                                                            Log.d("Image generation log", "index_models.value : $index_models")
                                                            //chatHistoryModel.fetchImage(username, input.value, index_models.value)

                                                            withContext(Dispatchers.IO) {
                                                                try {
                                                                    val response = RetrofitInstance.api.generateImage(
                                                                        SQL().generateImage(username, input.value, index_models.value)
                                                                    )

                                                                    val bitmap = BitmapFactory.decodeStream(response.byteStream()) ?: return@withContext
                                                                    val resized = resizeBitmap(bitmap, 1024, 1024)

                                                                    val stream = ByteArrayOutputStream()
                                                                    resized.compress(Bitmap.CompressFormat.JPEG, 50, stream)
                                                                    val encoded = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)

                                                                    messages.history.firstOrNull { it.role == "Assistant" }?.content?.value = encoded

                                                                    withContext(Dispatchers.Main) {
                                                                        chatHistoryModel.setStartOutput(true)
                                                                        chatHistoryModel.chat.value = messages
                                                                        delay(10)
                                                                        isLoading = false
                                                                        chatHistoryModel.addChat()
                                                                        messages.history = listOf(
                                                                            Chat("User", mutableStateOf("")),
                                                                            Chat("Assistant", mutableStateOf(""))
                                                                        )
                                                                        chatHistoryModel.clearOutput()
                                                                        chatHistoryModel.setStartOutput(false)
                                                                        if (chatHistoryModel.getListTitles().none { it.index == index }) {
                                                                            chatHistoryModel.addTitle("NewChat_$index", index)
                                                                        }
                                                                    }

                                                                } catch (e: Exception) {
                                                                    Log.e("ImageGenLog", "Exception durant le traitement image", e)
                                                                }
                                                            }

                                                        }


                                                    } catch (e: Exception) {
                                                        chatHistoryModel.setNetwork(false)
                                                        Log.d("AI MODELS PAGE", "Exception sending prompt : $e")

                                                    }
                                                }

                                            }
                                        } catch (e: Exception) {
                                            chatHistoryModel.setNetwork(false)
                                            Log.d("AI MODELS PAGE", "Exception sending prompt : $e")
                                        }
                                    },

                                ) {
                                    Icon(
                                        sendicon,
                                        contentDescription = "Send",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.size(35.dp)

                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = {},
                                ) {
                                    Icon(
                                        stopicon,
                                        contentDescription = "Stop",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.size(35.dp)
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


@RequiresApi(Build.VERSION_CODES.O)
fun triggerRisingVibrationWithClick(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    if (vibrator.hasVibrator()) {
        // Phase de montée : vibration longue avec amplitude croissante
        val riseDuration = longArrayOf(200) // 500 ms de vibration
        val riseAmplitude = intArrayOf(8) // Amplitude moyenne (128 sur 255)

        // Phase de clic : vibration courte et forte
        val clickDuration = longArrayOf(50) // 50 ms de vibration
        val clickAmplitude = intArrayOf(100) // Amplitude maximale

        // Combiner les deux phases
        val waveform = riseDuration + clickDuration
        val amplitudes = riseAmplitude + clickAmplitude

        // Créer l'effet de vibration
        val vibrationEffect = VibrationEffect.createWaveform(waveform, amplitudes, -1) // Ne pas répéter

        // Déclencher la vibration
        vibrator.vibrate(vibrationEffect)
    }
}