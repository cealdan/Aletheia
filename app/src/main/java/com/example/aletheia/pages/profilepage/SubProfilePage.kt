package com.example.aletheia.pages.profilepage

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.launch

import kotlinx.coroutines.CoroutineExceptionHandler


import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.aletheia.AletheiaTheme
import com.example.aletheia.NoRippleIconButton
import com.example.aletheia.PrefsHelper
import com.example.aletheia.R
import com.example.aletheia.RetrofitInstance
import com.example.aletheia.SQL
import com.example.aletheia.SendImage
import com.example.aletheia.ThemeViewModel
import com.example.aletheia.pages.Init
import com.example.aletheia.showFloatingToast
import com.example.aletheia.viewmodels.AIModel
import com.example.aletheia.viewmodels.ChatHistoryModel
import com.example.aletheia.viewmodels.GetPages
import com.example.aletheia.viewmodels.GlobalViewModel
import com.example.aletheia.viewmodels.ProfileViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt


data class GalleryImage(
    val id: Long,
    val uri: Uri,
    val isSelected: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePicker(
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var images by remember { mutableStateOf(listOf<GalleryImage>()) }
    var hasPermission by remember { mutableStateOf(false) }
    var showGallery by remember { mutableStateOf(false) }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        if (isGranted) {
            loadImages(context) { loadedImages ->
                images = loadedImages
                showGallery = true
                Log.d("CustomGalleryPicker", "Loaded ${images.size} images")
            }
        }
    }

    // Clickable text to open gallery
    Text(
        text = stringResource(id = R.string.edit_profilepic),
        style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.clickable (
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {
                coroutineScope.launch {
                    val permission = Manifest.permission.READ_MEDIA_IMAGES
                    val currentPermission = ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED

                    if (!currentPermission) {
                        Log.d("CustomGalleryPicker", "Requesting permission")
                        permissionLauncher.launch(permission)
                    } else {
                        loadImages(context) { loadedImages ->
                            images = loadedImages
                            showGallery = true
                            Log.d("CustomGalleryPicker", "Loaded ${images.size} images")
                        }
                    }
                }
            }
        )
    )
    if (showGallery) {
        BasicAlertDialog(
            onDismissRequest = { showGallery = false },
        ) {
            if (images.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No images found")
                }
            } else {
                Column(
                    modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier.weight(1f),
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(images) { image ->
                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        onImageSelected(image.uri)
                                        showGallery = false
                                    }
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(image.uri),
                                    contentDescription = "Gallery Image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .clickable(
                                    indication = LocalIndication.current,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        showGallery = false
                                    }
                                ),

                            ) {
                            Text(
                                stringResource(id = R.string.annuler),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}

// Separate function to load images
fun loadImages(
    context: Context,
    onImagesLoaded: (List<GalleryImage>) -> Unit
) {
    Thread {
        val images = mutableListOf<GalleryImage>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        try {
            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val contentUri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )
                    images.add(GalleryImage(id, contentUri))
                }

                Log.d("CustomGalleryPicker", "Loaded ${images.size} images")
            }
        } catch (e: Exception) {
            Log.e("CustomGalleryPicker", "Error loading images", e)
        }

        // Return to main thread
        android.os.Handler(android.os.Looper.getMainLooper()).post {
            onImagesLoaded(images)
        }
    }.start()
}



fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val scaleFactor = min(maxWidth.toFloat() / width, maxHeight.toFloat() / height)

    return Bitmap.createScaledBitmap(
        bitmap,
        (width * scaleFactor).toInt(),
        (height * scaleFactor).toInt(),
        true
    )
}


suspend fun saveProfileImage(profileViewModel: ProfileViewModel, prefs: SharedPreferences, bitmap: Bitmap, username : String) {
    val stream = ByteArrayOutputStream()
    val resizedBitmap = resizeBitmap(bitmap, 1024, 1024) // Ajuste selon ton besoin
    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
    val byteArray = stream.toByteArray()
    val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)

    profileViewModel.setProfilePic(bitmap)


    prefs.edit().putString("profilepic::$username", encoded).apply()

    RetrofitInstance.api.saveImage(SendImage("profile", encoded, username))


}

suspend fun getProfileImage(prefs: SharedPreferences, username: String): Bitmap? {
    val encoded = prefs.getString("profilepic::$username", null)?: RetrofitInstance.api.profilePic(SQL().getProfilePic(username))

    val myUsername = PrefsHelper.getPrefs().getString("username", "DefaultUsername") ?: "DefaultUsername"



    if (encoded == "null") {
        Log.d("getProfileImage", "encoded is null : $username")
        return null
    } else {

        if (myUsername == username) {
            prefs.edit().putString("profilepic::$myUsername", encoded).apply()
        }

        val byteArray = Base64.decode(encoded, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}


/*
@Composable
fun ExpandableCardScreen(getPages: GetPages, navcontroller: NavController, getCampaigns: GetCampaigns, themeViewModel: ThemeViewModel, username: String) {
    var selectedCard by remember { mutableStateOf<List<String>?>(null) }
    Log.d("ExpandableCardScreen", "Selected Card: $selectedCard")
    val prefs = LocalContext.current.getSharedPreferences(username, Context.MODE_PRIVATE)
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    AletheiaTheme (themeViewModel, darkTheme = isDarkTheme) {
        BackHandler {
            getCampaigns.toggleProfileShow()

        }
        CardList(getPages, navcontroller, getCampaigns, username, themeViewModel, selectedCard) { campaignData ->
            selectedCard = if (selectedCard == campaignData) null else campaignData
            Log.d("ExpandableCardScreen", "Selected Card: $selectedCard")
        }
    }
}

@Composable
fun CardList(getPages: GetPages, navcontroller: NavController, getCampaigns: GetCampaigns, username: String, themeViewModel: ThemeViewModel, selectedCard: List<String>?, onCardClick: (List<String>) -> Unit) {
    val context = LocalContext.current
    val CompletedCampaigns = getCampaigns.getSavedPages()
    Log.d("CardList", "Completed Campaigns: $CompletedCampaigns")
    val prefs = LocalContext.current.getSharedPreferences(username, Context.MODE_PRIVATE)
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val listState = rememberLazyListState()

    AletheiaTheme (themeViewModel, darkTheme = isDarkTheme) {
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(bottom = 8.dp, top = 115.dp),
            contentPadding = PaddingValues(bottom = 50.dp, top = 8.dp)
        ) {
            items(CompletedCampaigns) { campaign ->
                Log.d("CardList", "Campaign: $campaign")
                ExpandableCard(
                    getPages,
                    navcontroller,
                    getCampaigns,
                    username,
                    campaignData = campaign,
                    isExpanded = selectedCard == campaign,
                    onClick = { onCardClick(campaign) },
                    themeViewModel = themeViewModel
                )
                Log.d("AfterCardList", "Campaign: $campaign")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ExpandableCard(getPages: GetPages, navcontroller: NavController, getCampaigns: GetCampaigns, username: String, campaignData: List<String>, isExpanded: Boolean, onClick: () -> Unit, themeViewModel: ThemeViewModel) {
    val cardHeight by animateDpAsState(targetValue = if (isExpanded) 400.dp else 100.dp)

    var CampaignName = campaignData.getOrNull(0) ?: "N/A"
    var Date = campaignData.getOrNull(1) ?: "N/A"
    var UserName = campaignData.getOrNull(2) ?: "N/A"
    var CampaignType = campaignData.getOrNull(3) ?: "N/A"
    var GenType = campaignData.getOrNull(4) ?: "N/A"
    var CampaignDescription = campaignData.getOrNull(5) ?: "N/A"
    var NumberOfDataPts = campaignData.getOrNull(6) ?: "N/A"
    var PerDataPtPrice = campaignData.getOrNull(7) ?: "N/A"
    var AssociatedContent = campaignData.getOrNull(8) ?: "N/A"



    val prefs = LocalContext.current.getSharedPreferences(username, Context.MODE_PRIVATE)
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    AletheiaTheme (themeViewModel, darkTheme = isDarkTheme){
        var expandedItemId by remember { mutableStateOf<String?>(null) }
        val campaignId = campaignData.getOrNull(0) ?: "Unknown" // Utiliser un ID unique

        SwipeableOption(
            navcontroller,
            getPages,
            getCampaigns,
            themeViewModel,
            campaignData,
            isExpanded = expandedItemId == campaignId,
            onExpand = {
                expandedItemId = if (expandedItemId == campaignId) null else campaignId
            },
            onSwipe = {
                getCampaigns.addSavedPage(campaignData)
            }
        )


    }
}

 */



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalWearMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Settings(chatHistoryModel: ChatHistoryModel, globalViewModel: GlobalViewModel, profileViewModel: ProfileViewModel, themeViewModel: ThemeViewModel, username: String) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    //val coroutineScope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    var themeSelector by remember { mutableStateOf(false) }
    var hapticSelector by remember { mutableStateOf(false) }
    var adviceFromAISelector by remember { mutableStateOf(false) }
    var editProfile by remember { mutableStateOf(false) }

    val theme_txt = stringResource(id = R.string.theme)
    val haptic_txt = stringResource(id = R.string.retour_haptique)
    val adviceFromAI_txt = stringResource(id = R.string.assistant_ia)
    val editProfile_txt = stringResource(id = R.string.editprofile)

    val preferences_txt = stringResource(id = R.string.preferences)

    val preferencesList = listOf(
        listOf(stringResource(id = R.string.theme), {
            themeSelector = true
            profileViewModel.setCurrentPref(theme_txt)

        }, painterResource(id = R.drawable.themeicon)),
        listOf(stringResource(id = R.string.retour_haptique), {
            hapticSelector = true
            profileViewModel.setCurrentPref(haptic_txt)

        }, painterResource(id = R.drawable.hapticicon)),
        listOf(stringResource(id = R.string.assistant_ia), {
            adviceFromAISelector = true
            profileViewModel.setCurrentPref(adviceFromAI_txt)

        }, painterResource(id = R.drawable.advicefromaiicon)),


    )

    var showDialog by remember { mutableStateOf(false) }

    val filteredPreferences = if (searchQuery.text.isBlank()) { // Utiliser `searchQuery.text`
        preferencesList
    } else {
        preferencesList.filter { sublist ->
            (sublist.first() as? String)?.contains(searchQuery.text, ignoreCase = true) == true
        }

    }




    val maxTextFieldLength = 30 //à réfléchir


    val focusRequester = remember { FocusRequester() }

    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {
        BackHandler {
            if (editProfile) {
                editProfile = false
                profileViewModel.setCurrentPref(preferences_txt)
            } else if (hapticSelector) {
                hapticSelector = false
                profileViewModel.setCurrentPref(preferences_txt)
            } else if (themeSelector) {
                themeSelector = false
                profileViewModel.setCurrentPref(preferences_txt)
            } else if (adviceFromAISelector) {
                adviceFromAISelector = false
                profileViewModel.setCurrentPref(preferences_txt)
            } else {
                profileViewModel.toggleGoSettings()
            }
        }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        }
                    )
                }
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
/*
            Text(
                text = stringResource(id = R.string.preferences),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 30.dp)
                    .align(Alignment.CenterHorizontally)
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)

 */


            val dark = prefs.getBoolean("dark", false)
            val light = prefs.getBoolean("light", !isSystemInDarkTheme())
            val system = prefs.getBoolean("system", isSystemInDarkTheme())

            AnimatedVisibility(
                visible = themeSelector,
                enter = slideInHorizontally(
                    initialOffsetX = { it }, // Commence à la largeur complète de l'élément
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = ExitTransition.None
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ThemeSelector(
                        profileViewModel,
                        dark,
                        light,
                        system,
                        isDarkTheme
                    ) { selectedDarkTheme, dark, light, system ->
                        themeViewModel.toggleTheme(selectedDarkTheme, dark, light, system)
                    }

                    // Bouton pour revenir en mode réduit
                    Text(
                        text = stringResource(id = R.string.retour),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { themeSelector = false }
                            .padding(4.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = hapticSelector,
                enter = slideInHorizontally(
                    initialOffsetX = { it }, // Commence à la largeur complète de l'élément
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = ExitTransition.None
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    HapticSelector(profileViewModel, globalViewModel)
                    Text(
                        text = stringResource(id = R.string.retour),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { hapticSelector = false }
                            .padding(4.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = adviceFromAISelector,
                enter = slideInHorizontally(
                    initialOffsetX = { it }, // Commence à la largeur complète de l'élément
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = ExitTransition.None
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AdviceFromAISelector(chatHistoryModel, profileViewModel, globalViewModel)
                    Text(
                        text = stringResource(id = R.string.retour),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { adviceFromAISelector = false }
                            .padding(4.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = editProfile,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = ExitTransition.None
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    EditProfile(themeViewModel, username, profileViewModel, editProfile)
                    Text(
                        text = stringResource(id = R.string.retour),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                editProfile = false
                                profileViewModel.setCurrentPref(preferences_txt)}
                            .padding(4.dp)
                    )
                }
            }

            if (!hapticSelector && !themeSelector && !adviceFromAISelector && !editProfile) {

                Column(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    focusManager.clearFocus()
                                }
                            )
                        },
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
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
                                .background(MaterialTheme.colorScheme.surface)

                                .padding(horizontal = 8.dp, vertical = 8.dp),
                        ) {
                            // Label qui reste même quand unfocused
                            if (!isFocused && searchQuery.text.isBlank()) {
                                Text(
                                    text = stringResource(id = R.string.cherchersettings),
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

                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .shadow(
                                elevation = 2.dp,
                                shape = RoundedCornerShape(16.dp),
                                clip = false
                            )
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        items(filteredPreferences) { preference ->
                            val name = preference[0] as String
                            val onclick = preference[1] as () -> Unit
                            val icon = preference[2] as Painter
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()

                                    .clickable(
                                        onClick = onclick
                                    )
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                NoRippleIconButton(
                                    modifier = Modifier.size(30.dp),
                                    onClick = onclick
                                ) {
                                    Icon(
                                        painter = icon,
                                        contentDescription = "Theme",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.size(if (name == adviceFromAI_txt) 25.dp else 20.dp)
                                    )
                                }

                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                )
                            }
                            if (preference != filteredPreferences.last()) {
                                HorizontalDivider(
                                    color = MaterialTheme.colorScheme.onBackground.copy(
                                        0.05f
                                    ),
                                    modifier = Modifier.padding(start = 50.dp, end = 20.dp)
                                )
                            }
                        }

                    }
                    if (searchQuery.text.isBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(id = R.string.editprofile),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .padding(horizontal = 8.dp).padding(top = 8.dp)
                                .clickable(
                                    onClick = { editProfile = true
                                        profileViewModel.setCurrentPref(editProfile_txt)},
                                    enabled = true,
                                    role = Role.Button,
                                    interactionSource = interactionSource,
                                    indication = null
                                )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(id = R.string.deconnexion),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Red
                            ),
                            modifier = Modifier
                                .padding(horizontal = 8.dp).padding(top = 8.dp)
                                .clickable(
                                    onClick = { showDialog = true },
                                    enabled = true,
                                    role = Role.Button,
                                    interactionSource = interactionSource,
                                    indication = null
                                )
                        )
                    }

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
                                    stringResource(id = R.string.deconnexion),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 20.sp
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(16.dp)

                                )
                                Text(
                                    stringResource(id = R.string.deconnexion_alerte),
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
                                                    showDialog = false
                                                }
                                            ),

                                        ) {
                                        Text(stringResource(id = R.string.annuler),
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                color = MaterialTheme.colorScheme.onBackground
                                            ),
                                            modifier = Modifier.align(Alignment.Center).padding(8.dp))
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
                                                    val prefs_global =
                                                        context.getSharedPreferences("pref", Context.MODE_PRIVATE)
                                                    prefs_global.edit().putBoolean("is_setup_complete", false)
                                                        .apply()

                                                    val intent = Intent(context, Init::class.java)
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

                                                    context.startActivity(intent)
                                                }
                                            ),

                                        ) {
                                        Text(stringResource(id = R.string.confirmer),
                                            textAlign = TextAlign.Center,

                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                color = MaterialTheme.colorScheme.primary
                                            ),
                                            modifier = Modifier.align(Alignment.Center).padding(8.dp))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(themeViewModel: ThemeViewModel, username: String, profileViewModel: ProfileViewModel, localEditProfile: Boolean) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()


    val preferences_txt = stringResource(id = R.string.preferences)

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)

    val stateProfilePic by profileViewModel.profilePic.collectAsState()


    var profilePic by remember { mutableStateOf<Bitmap?>(stateProfilePic) }


    val name by profileViewModel.name.collectAsState()
    val bio by profileViewModel.biography.collectAsState()

    var customname by remember { mutableStateOf(name) }
    var custombio by remember { mutableStateOf(bio) }

    val maxName = 15 //à réfléchir
    val maxBio = 150 //à réfléchir

    val focusManager = LocalFocusManager.current

    var editName by remember { mutableStateOf(false) }
    var editBio by remember { mutableStateOf(false) }

    val editProfile by profileViewModel.editProfile.collectAsState()

    val alphabasic = if (editName || editBio) 0f else 1f

    var empty by remember { mutableStateOf(false) }

    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {


        if (editName) {
            BasicAlertDialog(
                onDismissRequest = {
                    if (customname.isBlank()) {
                        empty = true
                    } else {
                        editName = false
                    }

                },
                properties = DialogProperties(usePlatformDefaultWidth = false),
                modifier = Modifier
                    .windowInsetsPadding(insets = WindowInsets.ime)
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    //.widthIn(200.dp, 300.dp)
                    .background(
                        MaterialTheme.colorScheme.background),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.edit_name),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = customname.length.toString() + "/15",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    BasicTextField(
                        value = customname,
                        onValueChange = {
                            if (it.length <= maxName) {
                                customname = it
                                empty = false
                            }
                        },
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (customname.isBlank()) {
                                    empty = true
                                } else {
                                    editName = false
                                }
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)) // Applique la forme arrondie à l'ensemble

                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, if (!empty) MaterialTheme.colorScheme.onBackground.copy(0.05f) else MaterialTheme.colorScheme.error, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    )
                    IconButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            if (customname.isBlank()) {
                                empty = true
                            } else {
                                editName = false
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = "Valider",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }

        if (editBio) {
            BasicAlertDialog(
                onDismissRequest = { editBio = false },
                properties = DialogProperties(usePlatformDefaultWidth = false),
                modifier = Modifier
                    .windowInsetsPadding(insets = WindowInsets.ime)

                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    //.widthIn(200.dp, 300.dp)
                    .background(
                        MaterialTheme.colorScheme.background),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.edit_bio),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = custombio.length.toString() + "/150",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    BasicTextField(
                        value = custombio,
                        onValueChange = {
                            if (it.length <= maxBio) {
                                custombio = it
                            }
                        },
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        singleLine = false,
                        maxLines = 5,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)) // Applique la forme arrondie à l'ensemble

                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.05f), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    )
                    IconButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            editBio = false

                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = "Valider",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

        }



        Column(
            modifier = Modifier.fillMaxWidth().alpha(if (editProfile || localEditProfile) 1f else 0f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { focusManager.clearFocus() }
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (profilePic != null) {
                Image(
                    bitmap = profilePic!!.asImageBitmap(),
                    contentDescription = "Photo de profil",
                    modifier = Modifier
                        .padding(top = 20.dp)
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
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.onBackground

                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ImagePicker { uri ->
                if (uri != null) {
                    profilePic =
                        MediaStore.Images.Media.getBitmap(
                            context.contentResolver,
                            uri
                        )


                }
            }
            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier.fillMaxWidth().alpha(alphabasic)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.edit_name),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(8.dp)
                )
                BasicTextField(
                    enabled = false,
                    value = customname,
                    onValueChange = {
                        if (it.length <= maxName) {
                            customname = it
                        }
                    },
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { editName = true }
                        )
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)) // Applique la forme arrondie à l'ensemble

                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.05f), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth().alpha(alphabasic)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.edit_bio),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(8.dp)
                )
                BasicTextField(
                    enabled = false,
                    value = custombio,
                    onValueChange = {
                        if (it.length <= maxBio) {
                            custombio = it
                        }
                    },
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    singleLine = false,
                    maxLines = 5,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { editBio = true }
                        )
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)) // Applique la forme arrondie à l'ensemble

                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.05f), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                )
            }
            //changer le username? -> à gérer avec la database (primary key...)

            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = stringResource(id = R.string.confirmer),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.align(Alignment.End)
                    .padding(top = 8.dp, end = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        if (profilePic != null) {

                            coroutineScope.launch {
                                saveProfileImage(
                                    profileViewModel,
                                    prefs,
                                    profilePic!!,
                                    username
                                )
                                /*
                                profilePic = bitmap
                                saveProfileImage(
                                    profileViewModel,
                                    prefs,
                                    profilePic!!,
                                    username
                                )

                                 */
                            }
                        }
                        profileViewModel.setName(customname)
                        profileViewModel.setBiography(custombio)
                        focusManager.clearFocus()
                        coroutineScope.launch {
                            if (editProfile) {
                                profileViewModel.toggleEditProfile()
                            } else {
                                profileViewModel.toggleGoSettings()
                            }
                        }
                        profileViewModel.setCurrentPref("")
                    }
                    .padding(4.dp)
            )

        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdviceFromAISelector(
    chatHistoryModel: ChatHistoryModel,
    profileViewModel: ProfileViewModel,
    globalViewModel: GlobalViewModel
) {
    val primary = MaterialTheme.colorScheme.primary

    val context = LocalContext.current

    var changeCustomPrompt by remember { mutableStateOf(false) }

    var changeAssistantModel by remember { mutableStateOf(false) }

    val assistantModel by globalViewModel.assistantModel.collectAsState()
    val adviceFromAIPref by globalViewModel.adviceFromAIPref.collectAsState()
    val customPromptToAI by globalViewModel.customPromptToAI.collectAsState()

    val adviceOn = stringResource(id = R.string.assistant_on)
    val adviceOff = stringResource(id = R.string.assistant_off)

    val focusrequester = remember { FocusRequester() }

    val availableModels by chatHistoryModel.modelsFromDB.collectAsState()

    val maxTextFieldLength = 130 //à réfléchir

    val isNetworkAvailable by RetrofitInstance.isNetworkAvailable.collectAsState()

    val myModels by chatHistoryModel.myModels.collectAsState()

    LaunchedEffect(changeAssistantModel) {
        if (changeAssistantModel && availableModels.isEmpty()) {
            chatHistoryModel.loadmodelsFromDB("text")
        }
    }


    DisposableEffect(Unit) {
        onDispose {
            profileViewModel.setCurrentPref("")
        }
    }

    /*
    LaunchedEffect(changeCustomPrompt) {
        if (changeCustomPrompt) {
            focusrequester.requestFocus()
        }
    }
    
     */

    Row(
        modifier = Modifier.padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = if (adviceFromAIPref) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape
                )
                .background(Color.Transparent)
                .clickable {
                    globalViewModel.toggleAdviceFromAIPref()


                    if (globalViewModel.adviceFromAIPref.value) {
                        showFloatingToast(context, adviceOn)
                    } else {
                        showFloatingToast(context, adviceOff)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (adviceFromAIPref) {
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = primary)
                }
            }
        }
        Text(text = if (adviceFromAIPref) adviceOn else adviceOff,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier

                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    globalViewModel.toggleAdviceFromAIPref()

                    if (globalViewModel.adviceFromAIPref.value) {
                        showFloatingToast(context, adviceOn)
                    } else {
                        showFloatingToast(context, adviceOff)
                    }
                }
                .padding(8.dp)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { changeCustomPrompt = true },
            modifier = Modifier.weight(1f)
                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(32.dp),
                    clip = false
                ),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonColors(containerColor = MaterialTheme.colorScheme.surface, contentColor = MaterialTheme.colorScheme.onBackground, disabledContainerColor = MaterialTheme.colorScheme.surface.copy(0.5f), disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(0.5f))

        ) {
            Text(text = stringResource(id = R.string.custom_prompt),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp))
        }
        Button(
            enabled = myModels.filter { it.genType == "text" }.size > 1,
            onClick = { changeAssistantModel = true },
            modifier = Modifier.weight(1f)
                .then(
                    if (myModels.size <= 1) {
                        Modifier
                    } else {
                        Modifier.shadow(
                            elevation = 1.dp,
                            shape = RoundedCornerShape(32.dp),
                            clip = false
                        )
                    }
                ),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonColors(containerColor = MaterialTheme.colorScheme.surface, contentColor = MaterialTheme.colorScheme.onBackground, disabledContainerColor = MaterialTheme.colorScheme.surface.copy(0.5f), disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(0.5f))
        ) {
            Text(text = stringResource(id = R.string.assistant_model),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp))
        }
    }
    if (myModels.filter { it.genType == "text" }.size <= 1) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.infoicon),
                contentDescription = "Info",
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.onBackground.copy(0.5f)
            )
            Text(
                text = stringResource(id = R.string.seulement_un_modele),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
    }


    if (changeCustomPrompt) {

        BasicAlertDialog(
            onDismissRequest = {
                changeCustomPrompt = false
                               },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.ime)

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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(id = R.string.custom_prompt_about),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    /*
                    Box(
                        modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                    ) {
                        SelectionContainer {
                            Text(
                                text = customPromptToAI,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                     */
                    var customPrompt by remember { mutableStateOf(customPromptToAI) }

                    BasicTextField(
                        value = customPrompt,
                        onValueChange = {
                            if (it.length <= maxTextFieldLength) {
                                customPrompt = it
                            }
                                        },
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        singleLine = false,
                        maxLines = 3,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                changeCustomPrompt = false
                                globalViewModel.setCustomPromptToAI(customPrompt)
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusrequester)
                            .clip(RoundedCornerShape(16.dp)) // Applique la forme arrondie à l'ensemble
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.05f), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(1.dp, RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(8.dp)
                            )
                            .background(
                                MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable(
                                onClick = {
                                    changeCustomPrompt = false
                                    globalViewModel.setCustomPromptToAI(customPrompt)

                                },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = LocalIndication.current
                            )
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.valider),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                }
            }
        }
    }
    if (changeAssistantModel) {
        BasicAlertDialog(
            onDismissRequest = {
                changeAssistantModel = false
                chatHistoryModel.clearModelsFromDB()
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
                    Icon(
                        painter = painterResource(id = R.drawable.advicefromaiicon),
                        contentDescription = "assistant",
                        modifier = Modifier.size(50.dp).align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(availableModels.filter { myModels.contains(AIModel(it[0], it[1], it[2])) }) { item ->
                            val modelName = item[0]
                            val modelID = item[1]
                            val genData = item[2]

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(16.dp))

                                    .then (
                                        if (modelName == assistantModel) {
                                            Modifier
                                        } else Modifier.clickable {
                                            globalViewModel.setAssistantModel(modelName)
                                            changeAssistantModel = false
                                            chatHistoryModel.clearModelsFromDB()
                                            showFloatingToast(context, modelName)
                                        }
                                    )


                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        modelName,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = 20.sp,
                                            color = if (modelName == assistantModel) MaterialTheme.colorScheme.onBackground.copy(0.5f) else MaterialTheme.colorScheme.onBackground
                                        ),
                                        modifier = Modifier.padding(3.dp)
                                    )
                                    if (modelName == assistantModel) {
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
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center),
                                            color = MaterialTheme.colorScheme.primary
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



}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HapticSelector(
    profileViewModel: ProfileViewModel,
    globalViewModel: GlobalViewModel
) {
    val primary = MaterialTheme.colorScheme.primary

    val hapticvalue by globalViewModel.hapticPref.collectAsState()

    val context = LocalContext.current
    val vibrator = context.getSystemService(Vibrator::class.java)

    val hapticon = stringResource(id = R.string.haptique_on)
    val hapticoff = stringResource(id = R.string.haptique_off)


    DisposableEffect(Unit) {
        onDispose {
            profileViewModel.setCurrentPref("")
        }
    }

    Row(
        modifier = Modifier.padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = if (hapticvalue) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape
                )
                .background(Color.Transparent)
                .clickable {
                    globalViewModel.toggleHapticPref()

                    if (globalViewModel.hapticPref.value) {
                        vibrator?.vibrate(
                            VibrationEffect.createOneShot(
                                50,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                        showFloatingToast(context, hapticon)
                    } else {
                        showFloatingToast(context, hapticoff)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (hapticvalue) {
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = primary)
                }
            }
        }
        Text(text = stringResource(id = if (hapticvalue) R.string.haptique_on else R.string.haptique_off),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier

                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    globalViewModel.toggleHapticPref()

                    if (globalViewModel.hapticPref.value) {
                        vibrator?.vibrate(
                            VibrationEffect.createOneShot(
                                50,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                        showFloatingToast(context, hapticon)
                    } else {
                        showFloatingToast(context, hapticoff)
                    }
                }
                .padding(8.dp)
        )
    }


}


@Composable
fun ThemeSelector(
    profileViewModel: ProfileViewModel,
    dark : Boolean,
    light : Boolean,
    system : Boolean,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean, Boolean, Boolean, Boolean) -> Unit
) {
    val primary = MaterialTheme.colorScheme.primary
    val systemTheme = isSystemInDarkTheme()
    var dark by remember { mutableStateOf(dark) }
    var light by remember { mutableStateOf(light) }
    var system by remember { mutableStateOf(system) }


    DisposableEffect(Unit) {
        onDispose {
            profileViewModel.setCurrentPref("")
        }
    }

    Row(
        modifier = Modifier.padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bouton pour le thème sombre
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = if (dark) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape
                )
                .background(Color.Transparent)
                .clickable {
                    light = false
                    system = false
                    dark = true
                    onThemeChange(true, dark, light, system)
                },
            contentAlignment = Alignment.Center
        ) {
            // Si le thème sombre est actif, on affiche un disque plein
            if (dark) {
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = primary)
                }
            }
        }
        Text(text = stringResource(id = R.string.theme_sombre),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier

                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    light = false
                    system = false
                    dark = true
                    onThemeChange(true, dark, light, system)
                }
                .padding(8.dp)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bouton pour le thème clair
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = if (light) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape
                )
                .background(Color.Transparent)

                .clickable {
                    dark = false
                    system = false
                    light = true
                    onThemeChange(false, dark, light, system)
                },
            contentAlignment = Alignment.Center
        ) {
            // Si le thème clair est actif, on affiche un disque plein
            if (light) {
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = primary)
                }
            }
        }
        Text(text = stringResource(id = R.string.theme_clair),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier

                .clip(RoundedCornerShape(16.dp))

                .clickable {
                    dark = false
                    system = false
                    light = true
                    onThemeChange(false, dark, light, system)
                }
                .padding(8.dp)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bouton pour le thème clair
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = if (system) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape
                )
                .background(Color.Transparent)
                .clickable {
                    dark = false
                    light = false
                    system = true
                    onThemeChange(systemTheme, dark, light, system)
                },
            contentAlignment = Alignment.Center
        ) {
            // Si le thème clair est actif, on affiche un disque plein
            if (system) {
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = primary)
                }
            }
        }
        Text(text = stringResource(id = R.string.theme_systeme),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier

                .clip(RoundedCornerShape(16.dp))

                .clickable {
                    dark = false
                    light = false
                    system = true
                    onThemeChange(systemTheme, dark, light, system)
                }
                .padding(8.dp)
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(profileViewModel: ProfileViewModel, themeViewModel: ThemeViewModel, username: String) {
    val context = LocalContext.current // Utilisation de LocalContext pour obtenir le contexte
    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)
    val coroutineScope = rememberCoroutineScope()

    //val profileShow by getCampaigns.profileShow.collectAsState()

    //val inSettings by profileViewModel.goSettings.collectAsState()

    val indexPage by profileViewModel.indexPage.collectAsState()

    val currentPref by profileViewModel.currentPref.collectAsState()


    val focusManager = LocalFocusManager.current
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()


    val editProfile by profileViewModel.editProfile.collectAsState()
    val goToFollowersFollowing by profileViewModel.goToFollowersFollowing.collectAsState()

    // Appliquer le thème
    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {
        val onbackground = MaterialTheme.colorScheme.onBackground


        Surface(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = if (indexPage == 0 && !editProfile) onbackground.copy(0.1f) else Color.Transparent,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                },
            color = if (indexPage == 0) MaterialTheme.colorScheme.background else Color.Transparent,
        ) {

            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxHeight(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (indexPage == 0 && !editProfile) username else {
                                if (currentPref == "") stringResource(id = R.string.preferences) else currentPref
                            },
                            style = TextStyle(
                                fontFamily = if (indexPage == 0 && !editProfile) FontFamily(Font(R.font.pacifico_regular)) else FontFamily(
                                    Font(R.font.sarala_regular)
                                ),
                                fontSize = 20.sp,
                            ),
                            maxLines = 1
                        )
                    }
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxHeight()
                    ) {

                        if (indexPage != 1 && !editProfile) {
                            /*
                        IconButton(onClick = { showDialog = true }) { // Ouvre la boîte de dialogue
                            Icon(
                                exitIcon,
                                contentDescription = "Logout",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }

                     */
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    profileViewModel.setProfileLazy(true)
                                    profileViewModel.toggleGoSettings()
                                }

                            }) {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "Settings",
                                    tint = MaterialTheme.colorScheme.onBackground
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
                        if (indexPage == 1 || editProfile || goToFollowersFollowing.first) {
                            IconButton(
                                modifier = Modifier.padding(end = 8.dp),
                                onClick = {

                                    coroutineScope.launch {
                                        if (editProfile) {
                                            profileViewModel.toggleEditProfile()
                                            profileViewModel.setCurrentPref("")
                                        } else if (goToFollowersFollowing.first) {
                                            profileViewModel.toggleGoToFollowersFollowing("")
                                        } else {
                                            profileViewModel.toggleGoSettings()
                                        }
                                    }

                                    focusManager.clearFocus()


                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.gobackicon),
                                    contentDescription = "Close",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(30.dp)
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
    }
}




/*
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DisplayOnProfile(getPages: GetPages, navController: NavHostController, username: String, themeViewModel: ThemeViewModel, profileHeight: Int, profileViewModel: ProfileViewModel) {
    // Liste simulée de contenu (pour l'exemple)

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()


    val coroutineScope = rememberCoroutineScope()

    /*
    LaunchedEffect(Unit) {
        getPages.loadPages(username)
    }

     */

    val userPages = getPages.getUserPages()
    Log.d("DisplayOnProfile", "User Pages: $userPages")

    val horizontalPagerState = rememberPagerState(0, 0f, { 2 })
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp


    val savedPages = getPages.getSavedPages()
    Log.d("DisplayOnProfile", "Saved Pages: $savedPages")

    val text = stringResource(id = R.string.aucun_contenu)
    var placeHolder by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {

        coroutineScope.launch {
            delay(300)
            placeHolder = text
        }
    }


    //var surfaceHeight by remember { mutableStateOf(0) }



    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {


        Surface(
            modifier = Modifier

                /*
                .onSizeChanged { size ->
                    surfaceHeight = size.height // Hauteur en pixels
                }

                 */

                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.background,
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier,
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
                    Spacer(modifier = Modifier.padding(horizontal = 50.dp)) // Laisser un espace de 16dp de chaque côté
                    Column(
                        modifier = Modifier,
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
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground,
                    thickness = 3.dp,
                    modifier = Modifier
                        .offset( x = 75.dp)
                        .align(Alignment.Start)
                        .offset {
                            val offsetValue = (horizontalPagerState.currentPage + horizontalPagerState.currentPageOffsetFraction) * 1.25 * screenWidth.value //un peu au pif
                            IntOffset(offsetValue.toInt(), 0) // Se déplace progressivement sur 50.dp max
                        }
                        .width(60.dp)
                        .padding(top = 5.dp)
                        .height(3.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

            }

        }


        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize(),
                state = horizontalPagerState,
                userScrollEnabled = true,
                verticalAlignment = Alignment.Top,
            ) { page ->
                when (page) {
                    0 -> { //userContent
                        if (userPages.isEmpty()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = placeHolder,
                                    modifier = Modifier.padding(50.dp),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }

                        } else {
                            FlowRow(

                                maxItemsInEachRow = 3
                            ) {
                                userPages?.forEach { content ->
                                    //var Date = content.getOrNull(0) ?: "N/A"
                                    var UserName = content.getOrNull(0) ?: "N/A"
                                    var ContentType = content.getOrNull(1) ?: "N/A"
                                    var ContentURL = content.getOrNull(2) ?: "N/A" //URL si video ou image et contenu du texte si text
                                    var AudioURL = content.getOrNull(3) ?: "N/A"
                                    var ContentPrompt = content.getOrNull(4) ?: "N/A"
                                    var AIContentModel = content.getOrNull(5) ?: "N/A"
                                    var AIAudioModel = content.getOrNull(6) ?: "N/A"
                                    var Caption = content.getOrNull(7) ?: "N/A"

                                    Box(
                                        modifier = Modifier

                                            .width(120.dp)
                                            .aspectRatio(9f / 16f) // Aspect ratio 9:16 pour chaque "vidéo"
                                            .padding(2.dp)
                                            .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.05f), RoundedCornerShape(8.dp))
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
                                                            .fillMaxSize()
                                                            .background(brush = brush)
                                                    )
                                                }, // Indicateur de chargement
                                                error = {

                                                    Box(
                                                        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onBackground)

                                                    ) {
                                                        Icon(
                                                            modifier = Modifier.size(25.dp).align(Alignment.Center),
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
                                                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                                                )
                                            }
                                        }


                                    }
                                }
                            }

                        }
                    }
                    1 -> { //savedContent
                        if (savedPages.isEmpty()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = placeHolder,
                                    modifier = Modifier.padding(50.dp),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }


                        } else {
                            FlowRow(

                                maxItemsInEachRow = 3
                            ) {
                                savedPages?.forEach { content ->
                                    //var Date = content.getOrNull(0) ?: "N/A"
                                    var UserName = content.getOrNull(0) ?: "N/A"
                                    var ContentType = content.getOrNull(1) ?: "N/A"
                                    var ContentURL = content.getOrNull(2) ?: "N/A" //URL si video ou image et contenu du texte si text
                                    var AudioURL = content.getOrNull(3) ?: "N/A"
                                    var ContentPrompt = content.getOrNull(4) ?: "N/A"
                                    var AIContentModel = content.getOrNull(5) ?: "N/A"
                                    var AIAudioModel = content.getOrNull(6) ?: "N/A"
                                    var Caption = content.getOrNull(7) ?: "N/A"

                                    Box(
                                        modifier = Modifier

                                            .width(120.dp)
                                            .aspectRatio(9f / 16f) // Aspect ratio 9:16 pour chaque "vidéo"
                                            .padding(2.dp)
                                            .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.05f), RoundedCornerShape(8.dp))
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
                                                            .fillMaxSize()
                                                            .background(brush = brush)
                                                    )
                                                }, // Indicateur de chargement
                                                error = {

                                                    Box(
                                                        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onBackground)

                                                    ) {
                                                        Icon(
                                                            modifier = Modifier.size(25.dp).align(Alignment.Center),
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
                                                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
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
        }
    }
}

*/

/*
val myTrace = Firebase.performance.newTrace("Profile Page AsyncImage")
                            myTrace.start()
 */



@Composable
fun FollowersFollowingPage(themeViewModel: ThemeViewModel, profileViewModel: ProfileViewModel, username: String, usernamevisited : String) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    val horizontalState = rememberPagerState(0, 0f, { 2 })

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val profilePics = remember { mutableStateOf<Map<String, Bitmap?>>(emptyMap()) }


    val prefs = LocalContext.current.getSharedPreferences(username, Context.MODE_PRIVATE)

    val collectFollowers by profileViewModel.followers.collectAsState()
    val collectFollowing by profileViewModel.following.collectAsState()


    var followers by remember { mutableStateOf<List<String>>(emptyList()) }
    var following by remember { mutableStateOf<List<String>>(emptyList()) }



    val goToFollowersFollowing by profileViewModel.goToFollowersFollowing.collectAsState()

    val whichOne = goToFollowersFollowing.second
    val followedstring = stringResource(id = R.string.followed)
    val unfollowedstring = stringResource(id = R.string.unfollowed)

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("VisitProfilePage", "Erreur coroutine attrapée : ${throwable.message}")
    }

    LaunchedEffect(Unit) {
        if (usernamevisited == username) {
            followers = collectFollowers
            following = collectFollowing
            Log.d("FollowersFollowingPage", "Followers: $followers, Following: $following")
        } else {
            coroutineScope.launch(exceptionHandler) {
                val gson = Gson()
                val followersJson = RetrofitInstance.api.get_user_data(
                    SQL().getUserData(
                        usernamevisited,
                        "followers"
                    )
                )
                val followingJson = RetrofitInstance.api.get_user_data(
                    SQL().getUserData(
                        usernamevisited,
                        "followings"
                    )
                )
                followers = gson.fromJson(followersJson, object : TypeToken<List<String>>() {}.type)
                following = gson.fromJson(followingJson, object : TypeToken<List<String>>() {}.type)
            }
        }
        if (whichOne == "followers") {
            horizontalState.scrollToPage(0)
        } else if (whichOne == "following") {
            horizontalState.scrollToPage(1)
        }
    }

    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {





        Surface(
            modifier = Modifier


                /*
            .onSizeChanged { size ->
                surfaceHeight = size.height // Hauteur en pixels
            }

             */

                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.background,
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
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(0.5f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = LocalIndication.current,
                                onClick = {
                                    coroutineScope.launch {
                                        horizontalState.animateScrollToPage(0)
                                    }
                                },
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center


                    ) {
                        Text(
                            text = stringResource(id = R.string.followers),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            modifier = Modifier.padding(8.dp)
                        )

                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(0.5f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = LocalIndication.current,
                                onClick = {
                                    coroutineScope.launch {
                                        horizontalState.animateScrollToPage(1)
                                    }
                                }
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.following),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            modifier = Modifier.padding(8.dp)

                        )


                    }
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground,
                    thickness = 3.dp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .offset {
                            val offsetValue =
                                (horizontalState.currentPage + horizontalState.currentPageOffsetFraction) * 1.5 * screenWidth.value
                            IntOffset(
                                offsetValue.toInt(),
                                0
                            )
                        }
                        .fillMaxWidth(0.5f)
                        .padding(top = 5.dp)
                        .height(3.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))

                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = horizontalState,
                    verticalAlignment = Alignment.Top,
                ) {
                        page ->
                    when (page) {
                        0 -> { //followers
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Top
                            ) {
                                items(followers) { follower ->
                                    LaunchedEffect(follower) {
                                        coroutineScope.launch {
                                            try {
                                                val profilePic = getProfileImage(prefs, follower)

                                                // Créer une nouvelle copie de la Map avec la nouvelle valeur
                                                profilePics.value = profilePics.value.toMutableMap().apply {
                                                    this[follower] = profilePic
                                                }

                                            } catch (e: Exception) {
                                                profilePics.value = profilePics.value.toMutableMap().apply {
                                                    this[follower] = null
                                                }
                                            }
                                        }
                                    }
                                    Row(
                                        modifier = Modifier

                                            .fillMaxWidth()

                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                            .padding(8.dp)

                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable(
                                                onClick = {
                                                    //access profile
                                                },
                                                enabled = true,
                                                role = Role.Button,
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = LocalIndication.current
                                            ),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically

                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (profilePics.value[follower] != null) {
                                                Image(
                                                    bitmap = profilePics.value[follower]!!.asImageBitmap(),
                                                    contentDescription = "Photo de profil",
                                                    modifier = Modifier
                                                        .size(60.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop
                                                )
                                            } else {
                                                Icon(
                                                    imageVector = Icons.Default.AccountCircle,
                                                    contentDescription = "Photo de profil par défaut",
                                                    modifier = Modifier.size(60.dp),
                                                    tint = MaterialTheme.colorScheme.onBackground

                                                )
                                            }
                                            Text(
                                                text = follower,
                                                style = TextStyle(
                                                    fontFamily = FontFamily(Font(R.font.pacifico_regular)),
                                                    fontSize = 15.sp,
                                                ),
                                                modifier = Modifier
                                                    .padding(10.dp)

                                            )
                                        }
                                        if (username != follower) {
                                            val inFollowing = collectFollowing.contains(follower)
                                            IconButton(
                                                modifier = Modifier.size(50.dp),
                                                onClick = {
                                                    if (!inFollowing) {
                                                        profileViewModel.follow(follower)
                                                    } else {
                                                        profileViewModel.unfollow(follower)
                                                    }
                                                    showFloatingToast(context, (if (inFollowing) unfollowedstring else followedstring) + " " + follower)


                                                }
                                            ) {
                                                if (!inFollowing) {
                                                    Icon(
                                                        painter = if (isDarkTheme) painterResource(id = R.drawable.subscribe_icon) else painterResource(id = R.drawable.subscribe_icon_dark),
                                                        contentDescription = "Subscribe",
                                                        tint = Color.Unspecified, //les deux couleurs sont définies dans le .xml
                                                        modifier = Modifier
                                                            .size(30.dp)
                                                    )
                                                } else {
                                                    Icon(
                                                        painter = if (isDarkTheme) painterResource(id = R.drawable.subscribed_icon) else painterResource(id = R.drawable.subscribed_icon_dark),
                                                        contentDescription = "Subscribed",
                                                        tint = Color.Unspecified, //les deux couleurs sont définies dans le .xml
                                                        modifier = Modifier
                                                            .size(30.dp)
                                                    )
                                                }
                                            }
                                        }


                                    }


                                }

                            }

                        }
                        1 -> { //following
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Top
                            ) {
                                items(following) { followed ->
                                    LaunchedEffect(followed) {
                                        coroutineScope.launch {
                                            try {
                                                val profilePic = getProfileImage(prefs, followed)

                                                // Créer une nouvelle copie de la Map avec la nouvelle valeur
                                                profilePics.value = profilePics.value.toMutableMap().apply {
                                                    this[followed] = profilePic
                                                }

                                            } catch (e: Exception) {
                                                profilePics.value = profilePics.value.toMutableMap().apply {
                                                    this[followed] = null
                                                }
                                            }
                                        }
                                    }



                                    Row(
                                        modifier = Modifier



                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 8.dp)


                                            .padding(8.dp)

                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable(
                                                onClick = {
                                                    //access profile
                                                },
                                                enabled = true,
                                                role = Role.Button,
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = LocalIndication.current
                                            ),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically

                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (profilePics.value[followed] != null) {
                                                Image(
                                                    bitmap = profilePics.value[followed]!!.asImageBitmap(),
                                                    contentDescription = "Photo de profil",
                                                    modifier = Modifier
                                                        .size(60.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop
                                                )
                                            } else {
                                                Icon(
                                                    imageVector = Icons.Default.AccountCircle,
                                                    contentDescription = "Photo de profil par défaut",
                                                    modifier = Modifier.size(60.dp),
                                                    tint = MaterialTheme.colorScheme.onBackground

                                                )
                                            }

                                            Text(
                                                text = followed,
                                                style = TextStyle(
                                                    fontFamily = FontFamily(Font(R.font.pacifico_regular)),
                                                    fontSize = 15.sp,
                                                ),
                                                modifier = Modifier
                                                    .padding(10.dp)

                                            )
                                        }
                                        if (username != followed) {
                                            val inFollowing = collectFollowing.contains(followed)
                                            IconButton(
                                                modifier = Modifier.size(50.dp),
                                                onClick = {
                                                    if (!inFollowing) {
                                                        profileViewModel.follow(followed)
                                                    } else {
                                                        profileViewModel.unfollow(followed)
                                                    }
                                                    showFloatingToast(context, (if (inFollowing) unfollowedstring else followedstring) + " " + followed)

                                                }
                                            ) {
                                                if (!inFollowing) {
                                                    Icon(
                                                        painter = if (isDarkTheme) painterResource(id = R.drawable.subscribe_icon) else painterResource(id = R.drawable.subscribe_icon_dark),
                                                        contentDescription = "Subscribe",
                                                        tint = Color.Unspecified, //les deux couleurs sont définies dans le .xml
                                                        modifier = Modifier
                                                            .size(30.dp)
                                                    )
                                                } else {
                                                    Icon(
                                                        painter = if (isDarkTheme) painterResource(id = R.drawable.subscribed_icon) else painterResource(id = R.drawable.subscribed_icon_dark),
                                                        contentDescription = "Subscribed",
                                                        tint = Color.Unspecified, //les deux couleurs sont définies dans le .xml
                                                        modifier = Modifier
                                                            .size(30.dp)
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

            }

        }



    }

}


@Composable
fun FollowersFollowingLoadingScreen() {
    val shimmerColors = listOf(
        Color.White,
        Color.White.copy(0.8f)
    )


    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = -800f,
        targetValue = 1500f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim.value, translateAnim.value),
        end = Offset(translateAnim.value + 500f, translateAnim.value + 500f)
    )

    Box(modifier = Modifier.fillMaxSize().background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                MaterialTheme.colorScheme.background
            )
        )
    ))
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Shimmer effect for the profile picture
            Row(
                modifier = Modifier.size(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(brush = brush)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp)
                        .background(brush = brush)
                )
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )


        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
// Shimmer effect for the profile picture
            Row(
                modifier = Modifier.size(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(brush = brush)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp)
                        .background(brush = brush)
                )
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )


        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
// Shimmer effect for the profile picture
            Row(
                modifier = Modifier.size(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(brush = brush)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp)
                        .background(brush = brush)
                )
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )


        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
// Shimmer effect for the profile picture
            Row(
                modifier = Modifier.size(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(brush = brush)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp)
                        .background(brush = brush)
                )
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )


        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
// Shimmer effect for the profile picture
            Row(
                modifier = Modifier.size(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(brush = brush)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp)
                        .background(brush = brush)
                )
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )


        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
// Shimmer effect for the profile picture
            Row(
                modifier = Modifier.size(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(brush = brush)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp)
                        .background(brush = brush)
                )
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )


        }

    }

}



