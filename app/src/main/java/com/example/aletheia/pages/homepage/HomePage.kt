package com.example.aletheia.pages.homepage

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

import androidx.compose.foundation.pager.rememberPagerState


import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.material3.CircularProgressIndicator


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.aletheia.AletheiaTheme
import com.example.aletheia.R
import com.example.aletheia.RetrofitInstance
import com.example.aletheia.ThemeViewModel
import com.example.aletheia.viewmodels.ProfileViewModel

import com.example.aletheia.viewmodels.GetPages
import com.example.aletheia.viewmodels.GlobalViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(profileViewModel: ProfileViewModel, globalViewModel: GlobalViewModel, navController: NavHostController, viewModel: GetPages, themeViewModel: ThemeViewModel, username: String) { // Pour le moment, les pages sont prises par paquets de 10 dans l'ordre de la base sql, mais il faudra un algorithme pour mélanger et proposer du contenu "approprié" selon la personne (cf. réseaux sociaux)
    var isVisible by remember { mutableStateOf(false) }

    val pages by viewModel.pages.collectAsState()
    val isEndlessScroll by viewModel.isEndlessScroll.collectAsState()


    val pagerState = rememberPagerState(0, 0f, {pages.size})
    var refresh by remember { mutableStateOf(false) }
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullToRefreshState()
    var first by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current // Utilisation de LocalContext pour obtenir le contexte
    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)





    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry) {
        snapshotFlow { navController.currentBackStackEntry }
            .collect { backStackEntry ->
                if (backStackEntry?.destination?.route != "home") {
                    themeViewModel.toggleReelSysBar(false)
                }
            }
    }

    //val contentID by getCampaigns.contentID.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                //getCampaigns.clearContentID()
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    /*
    LaunchedEffect(pages, contentID) {
        val targetPage = pages.indexOfFirst { it[0] == contentID && contentID.isNotEmpty() }
        if (targetPage != -1) {
            pagerState.scrollToPage(targetPage)
        }
    }

     */


    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val activity = LocalActivity.current

    val creationMenu by viewModel.creationMenu.collectAsState()

    val visitedProfile by viewModel.visitProfile.collectAsState()


    val goChat by viewModel.goChat.collectAsState()


    val filter by viewModel.filter.collectAsState()
    val isNetworkAvailable by viewModel.isNetworkAvailable.collectAsState()
    LaunchedEffect(Unit) {
        globalViewModel.setWhichPage("homepage")
        themeViewModel.toggleReelSysBar(true)

        if (pagerState.currentPage == 0) {
            viewModel.loadPages("", filter)
        }

        isVisible = true

    }

    val RetrofitNetValue by RetrofitInstance.isNetworkAvailable.collectAsState()

    val profileVisited by viewModel.visitProfile.collectAsState()

    val showSeePrompt by viewModel.showSeePrompt.collectAsState()
    val showComments by viewModel.showComments.collectAsState()
    val threeDotsMenu by viewModel.threeDotsMenu.collectAsState()
    val sendContent by viewModel.sendContent.collectAsState()




    // Appliquer le thème
    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {



        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            isRefreshing = isRefreshing,
            onRefresh = {
                if (profileVisited) {

                } else {
                    viewModel.refreshData()
                }

            },
            state = refreshState,
            indicator = {
                if (profileVisited) {

                } else {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = isRefreshing,
                        state = refreshState,
                        color = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.surface

                    )
                }
            }
        ) {

            Log.d("pages", pages.toString())
            if (pages.isEmpty()){
                VerticalPager(
                    userScrollEnabled = !goChat && !creationMenu && !profileVisited && !showSeePrompt && !showComments && !threeDotsMenu && !sendContent,
                    state = rememberPagerState(0, 0f, {1}),
                    modifier = Modifier
                        .fillMaxSize()


                ) { _ ->
                    DisplayContent(
                        profileViewModel,
                        globalViewModel,
                        viewModel,
                        listOf(),
                        username,
                        themeViewModel,
                        true,
                        navController
                    )
                }
            } else {

                VerticalPager(
                    userScrollEnabled = !goChat && !creationMenu && !profileVisited && !showSeePrompt && !showComments && !threeDotsMenu && !sendContent,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()


                ) { page ->


                    val isPage by remember { derivedStateOf { pagerState.currentPage == page } }



                    DisplayContent(
                        profileViewModel,
                        globalViewModel,
                        viewModel,
                        pages[page],
                        username,
                        themeViewModel,
                        isPage,
                        navController
                    )




                    LaunchedEffect(pagerState.currentPage) {
                        if (pagerState.currentPage == pages.size - 1 && isEndlessScroll) {
                            viewModel.loadPages("", filter)
                            viewModel.setFirstPage(false)

                        } else if (pagerState.currentPage == 0) {
                            viewModel.setFirstPage(true)
                        } else if (pagerState.currentPage != 0 && pagerState.currentPage != pages.size - 1) {
                            viewModel.setFirstPage(false)
                        }
                    }
                }
            }
        }
    }
}
