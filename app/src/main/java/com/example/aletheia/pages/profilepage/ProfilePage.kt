package com.example.aletheia.pages.profilepage

import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import kotlinx.coroutines.launch

import androidx.navigation.NavController


import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.DialogProperties
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import coil.compose.SubcomposeAsyncImage
import com.example.aletheia.AletheiaTheme
import com.example.aletheia.NoRippleIconButton
import com.example.aletheia.R
import com.example.aletheia.RetrofitInstance
import com.example.aletheia.ThemeViewModel
import com.example.aletheia.pages.Init
import com.example.aletheia.pages.creationpage.decodeBase64ToBitmap
import com.example.aletheia.viewmodels.ChatHistoryModel
import com.example.aletheia.viewmodels.GetPages
import com.example.aletheia.viewmodels.GlobalViewModel
import com.example.aletheia.viewmodels.ProfileViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import kotlinx.coroutines.coroutineScope
import java.io.ByteArrayOutputStream
import java.util.Locale
import kotlin.math.ln
import kotlin.math.pow


fun Number.formatAbbreviated(
    decimalDigits: Int = 1,
    suffixes: List<String> = listOf("k", "M", "G", "T")
): String {
    if (this.toLong() < 1000) return toString()

    val value = this.toDouble()
    val exp = (ln(value) / ln(1000.0)).toInt()
    val suffix = suffixes.getOrNull(exp - 1) ?: return toString()

    return "%.${decimalDigits}f%s".format(value / 1000.0.pow(exp), suffix)
        .replace(".0$suffix", suffix)  // Supprime .0 pour les valeurs entières
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun ProfilePage(profileViewModel: ProfileViewModel, getPages: GetPages, navController: NavHostController, username: String, firstName : String, lastName : String, themeViewModel: ThemeViewModel) {
    val context = LocalContext.current // Utilisation de LocalContext pour obtenir le contexte

    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    //val expandable by getCampaigns.profileShow.collectAsState()

    val coroutineScope = rememberCoroutineScope()




    val profilePic by profileViewModel.profilePic.collectAsState()






    Log.d("ProfilePage", "profilePic: $profilePic")




    var lazyState = rememberLazyListState()

    val profileLazy by profileViewModel.profileLazy.collectAsState()

    val biography by profileViewModel.biography.collectAsState()

    var toggleBio by remember { mutableStateOf(false) }
    var isOverflow by remember { mutableStateOf(false) }


    LaunchedEffect(profileLazy) {
        if (profileLazy) {
            lazyState.animateScrollToItem(0)
            profileViewModel.setProfileLazy(false)
        }
    }


    //////////////////////////



    /*
    LaunchedEffect(Unit) {
        getPages.loadPages(username)
    }

     */

    val userPages by profileViewModel.userPages.collectAsState()
    Log.d("DisplayOnProfile", "User Pages: $userPages")

    val horizontalPagerState = rememberPagerState(0, 0f, { 2 })
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp


    val savedPages by profileViewModel.savedPages.collectAsState()
    Log.d("DisplayOnProfile", "Saved Pages: $savedPages")

    val text = stringResource(id = R.string.aucun_contenu)
    var placeHolder by remember { mutableStateOf("") }

    val name by profileViewModel.name.collectAsState()

    val following by profileViewModel.following.collectAsState()
    val followers by profileViewModel.followers.collectAsState()

    val editProfile by profileViewModel.editProfile.collectAsState()

    val goToFollowersFollowing by profileViewModel.goToFollowersFollowing.collectAsState()
    LaunchedEffect(Unit) {

        if (name == "") {
            profileViewModel.setName("$firstName $lastName")
        }
        coroutineScope.launch {
            delay(300)
            placeHolder = text
        }
    }


    val editProfile_txt = stringResource(id = R.string.editprofile)

    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {





        LazyColumn(
            modifier = Modifier
                .fillMaxSize().alpha(if (editProfile || goToFollowersFollowing.first) 0f else 1f),
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
                        Box(
                            modifier = Modifier

                                .fillMaxWidth(0.9f)

                                .padding(bottom = 8.dp)
                                .shadow(
                                    elevation = 1.dp,
                                    shape = RoundedCornerShape(8.dp),
                                    clip = false
                                )
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(
                                    onClick = {
                                        profileViewModel.toggleEditProfile()
                                        profileViewModel.setCurrentPref(editProfile_txt)
                                    },
                                    enabled = true,
                                    role = Role.Button,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = LocalIndication.current
                                )
                                .background(MaterialTheme.colorScheme.surface)

                        ) {
                            Text(
                                text = stringResource(id = R.string.editprofile),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(10.dp)

                            )
                        }


                        Row(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            val referenceText = "W" // 'W' est souvent le caractère le plus large
                                .repeat(5) // 15 caractères

                            val textWidth = remember { mutableFloatStateOf(0f) }

                            val textLayoutResult = rememberTextMeasurer().measure(
                                text = AnnotatedString(referenceText),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                            textWidth.value = textLayoutResult.size.width.toFloat()


                            Box(
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { textWidth.value.toDp() })
                            ) {
                                Text(
                                    text = name,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    ),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
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
                                        text = userPages.size.formatAbbreviated(),
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
                                        text = followers.size.formatAbbreviated(),
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
                                        text = following.size.formatAbbreviated(),
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
                                text = biography.replace(Regex("\n\n+"), "\n"),
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
                                    ) // Se déplace progressivement sur 50.dp max
                                }
                                .fillMaxWidth(0.5f)
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

                        1 -> { //savedContent
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
                                                    model = decodeBase64ToBitmap(ContentURL),
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
                    }
                }

            }

        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Profile(chatHistoryModel: ChatHistoryModel, globalViewModel: GlobalViewModel, profileViewModel: ProfileViewModel, getPages: GetPages, navController: NavHostController, username: String, firstName : String, lastName : String, themeViewModel: ThemeViewModel) {



    val pagerState = rememberPagerState(0, 0f, { 2 })
    val context = LocalContext.current

    val goSettings by profileViewModel.goSettings.collectAsState()

    //val profileShow by getCampaigns.profileShow.collectAsState()

    Log.d("Profile", "goSettings: $goSettings")


    LaunchedEffect(goSettings) {
        if (goSettings) {
            if (pagerState.currentPage == 0) {
                pagerState.animateScrollToPage(1)
            }
        } else {
            if (pagerState.currentPage == 1) {
                pagerState.animateScrollToPage(0)
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        profileViewModel.setIndexPage(pagerState.currentPage)
        profileViewModel.setGoSettings(pagerState.currentPage == 1)

        if (pagerState.currentPage == 1) {
            globalViewModel.setWhichPage("settingspage")
        } else if (pagerState.currentPage == 0) {
            globalViewModel.setWhichPage("profilepage")
        }
    }

    val verticalPagerState = rememberPagerState(0, 0f, { 2 })

    val editProfile by profileViewModel.editProfile.collectAsState()
    val goToFollowersFollowing by profileViewModel.goToFollowersFollowing.collectAsState()



    LaunchedEffect(editProfile, goToFollowersFollowing) {
        if (editProfile || goToFollowersFollowing.first) {
            verticalPagerState.animateScrollToPage(1)
        } else {
            verticalPagerState.animateScrollToPage(0)
        }
    }


    HorizontalPager(
        userScrollEnabled = false,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) {
        page ->

            when (page) {
                0 -> {
                    Box(modifier = Modifier.fillMaxSize()) {

                        VerticalPager(
                            userScrollEnabled = false,
                            state = verticalPagerState,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            page ->
                            when (page) {
                                0 -> {
                                    BackHandler {
                                        if (!goSettings) {
                                            navController.popBackStack()
                                        }
                                    }

                                    ProfilePage(
                                        profileViewModel,
                                        getPages,
                                        navController,
                                        username,
                                        firstName,
                                        lastName,
                                        themeViewModel
                                    )
                                }
                                1 -> {
                                    if (goToFollowersFollowing.first) {
                                        Column(
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            BackHandler{
                                                if (goToFollowersFollowing.first) {
                                                    profileViewModel.toggleGoToFollowersFollowing("")
                                                }
                                            }
                                            FollowersFollowingPage(
                                                themeViewModel,
                                                profileViewModel,
                                                username,
                                                username
                                            )
                                        }
                                    } else {
                                        Column(
                                            modifier = Modifier.fillMaxSize().padding(24.dp)
                                        ) {
                                            BackHandler {
                                                if (editProfile) {
                                                    profileViewModel.toggleEditProfile()
                                                    profileViewModel.setCurrentPref("")
                                                }
                                            }
                                            EditProfile(themeViewModel, username, profileViewModel, false)

                                        }
                                    }


                                }
                            }

                        }



                    }
                }
                1 -> {
                    Box(modifier = Modifier.fillMaxSize()) {

                        Settings(chatHistoryModel, globalViewModel, profileViewModel, themeViewModel, username)


                    }
                }
            }
    }


}

