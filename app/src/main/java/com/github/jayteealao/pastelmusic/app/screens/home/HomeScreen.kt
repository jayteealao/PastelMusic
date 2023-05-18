package com.github.jayteealao.pastelmusic.app.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.Visibility
import androidx.constraintlayout.compose.layoutId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.jayteealao.pastelmusic.app.components.AppBar
import com.github.jayteealao.pastelmusic.app.components.BottomNavBar
import com.github.jayteealao.pastelmusic.app.components.MediaCard
import com.github.jayteealao.pastelmusic.app.components.PlayerCard
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.mediaservice.PlaybackState
import com.github.jayteealao.pastelmusic.app.ui.components.PastelCard
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelTheme
import com.github.jayteealao.pastelmusic.app.ui.theme.fontFamily


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {

    val searchText by remember {
        mutableStateOf("")
    }

    var selected by remember {
        mutableStateOf(OVERVIEW)
    }

    val albums by homeViewModel.albums.collectAsStateWithLifecycle(listOf())
    val songs by homeViewModel.songs.collectAsStateWithLifecycle(listOf())
    val playbackState by homeViewModel.playbackState.collectAsStateWithLifecycle()
    val lazyGridState = rememberLazyGridState()

    val showBars by remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex < 2
        }
    }

    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .padding(horizontal = 16.dp)
                .wrapContentSize()) {
                AppBar()
            }
        },
        bottomBar = {
            Box(modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .wrapContentSize()) {
                BottomNavBar()
            }
        }
    ) {
        MotionComposeHeader(
            lazyGridState, selected, albums, songs, playbackState, homeViewModel,
            progress = 0f,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
        ) { selected = it }
    }
}

@Composable
private fun PastelSearchBar(searchText: String) {
    var searchText1 = searchText
    BasicTextField(
        value = searchText1,
        onValueChange = { searchText1 = it },
        modifier = Modifier.padding(vertical = 16.dp)
    ) { innerTextField ->

        PastelCard(
            modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 16.dp)
        ) {
            var size by remember {
                mutableStateOf(IntSize.Zero)
            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .background(color = PastelTheme.colors.pastelGray100)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                }
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(48.dp)
                        .background(Color.Black)

                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .fillMaxWidth()
                ) {
                    innerTextField()
                }
            }
        }

    }
}

@Composable
fun HomeTabBar(onSelected: (String) -> Unit = {}) {
    val tabs = listOf(OVERVIEW, SONGS, ALBUMS, ARTISTS)
    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, title ->
                val interactionSource = remember { MutableInteractionSource() }
                val isSelected = index == selectedTab
                val color = if (isSelected) PastelTheme.colors.pastelYellow else Color.White

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                0.0f to Color.White,
                                0.45f to Color.White,
                                0.45f to color,
                                1.0f to color
                            )
                        )
                        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            selectedTab = index
                            onSelected(title)
                        }
                        .indication(
                            interactionSource = interactionSource,
                            indication = rememberRipple(
                                color = PastelTheme.colors.pastelYellow,
                                bounded = false,
//                                radius = 24.dp
                            )
                        ),
                    //                    .weight(1f)
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.Black
        )
    }
}

@Composable
//private fun ColumnScope.SplashText() {
private fun SplashText() {
    Text(
        text = "Listening Everyday",
        modifier = Modifier
            .padding(vertical = 16.dp),
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        fontFamily = fontFamily
    )
    Text(
        text = "Explore millions of music according to your taste.",
        modifier = Modifier
            .padding(vertical = 8.dp),
        style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray)
    )
}


private fun startConstraintSet() = ConstraintSet {
    val splashText = createRefFor("splashText")
    val searchBar = createRefFor("searchBar")
    val homeTabBar = createRefFor("homeTabBar")
    val musicGrid = createRefFor("musicGrid")

    val topGuideline = createGuidelineFromTop(200.dp)

    constrain(splashText) {
        height = Dimension.wrapContent
        width = Dimension.matchParent
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(searchBar.top)
    }

    constrain(searchBar) {
        height = Dimension.wrapContent
        top.linkTo(splashText.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(homeTabBar.top)
    }

    constrain(homeTabBar) {
        height = Dimension.wrapContent
        top.linkTo(searchBar.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(topGuideline)
    }

    constrain(musicGrid) {
        height = Dimension.fillToConstraints
        linkTo(
            start = parent.start,
            end = parent.end,
            top = topGuideline,
            bottom = parent.bottom
        )
    }
}

private fun endConstraintSet() = ConstraintSet {
    val splashText = createRefFor("splashText")
    val searchBar = createRefFor("searchBar")
    val homeTabBar = createRefFor("homeTabBar")
    val musicGrid = createRefFor("musicGrid")

    constrain(splashText) {
        visibility = Visibility.Gone
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(searchBar) {
        visibility = Visibility.Gone
        top.linkTo(splashText.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(homeTabBar) {
        height = Dimension.wrapContent
        top.linkTo(searchBar.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(musicGrid) {
        height = Dimension.fillToConstraints
        top.linkTo(homeTabBar.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionComposeHeader(
    lazyGridState: LazyGridState,
    selected: String,
    albums: List<Album>,
    songs: List<Song>,
    playbackState: PlaybackState,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    onSelected: (String) -> Unit = {}
) {
    MotionLayout(
        start = startConstraintSet(),
        end = endConstraintSet(),
        progress = progress,
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier
            .layoutId("splashText")
        ) {
            SplashText()
        }
        Box(modifier = Modifier
            .layoutId("searchBar")
        ) {
            PastelSearchBar(searchText = "")
        }
        Box(modifier = Modifier
            .layoutId("homeTabBar")
        ) {
            HomeTabBar(onSelected)

        }
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .layoutId("musicGrid")
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier,
                state = lazyGridState,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                when(selected) {
                    OVERVIEW -> items(albums) { album ->
                        MediaCard(album) {
                            homeViewModel.playAlbum(album)
                        }
                    }
                    SONGS -> items(songs) { song ->
                        MediaCard(song) {
                            homeViewModel.playSong(song)
                        }
                    }
                    ALBUMS -> items(albums) { album ->
                        MediaCard(album) {
                            homeViewModel.playAlbum(album)
                        }
                    }
                    ARTISTS -> items(albums) { album ->
                        MediaCard(album) {
                            homeViewModel.playAlbum(album)
                        }
                    }
                }
            }
            PlayerCard(
                modifier = Modifier.align(Alignment.BottomCenter).layoutId("playerCard"),
                playbackState = playbackState,
                controls = homeViewModel.controls
            )
        }
    }
}
private const val OVERVIEW = "Overview"
private const val SONGS = "Songs"
private const val ALBUMS = "Albums"
private const val ARTISTS = "Artists"

class ParameterForMCH: PreviewParameterProvider<Float> {
    override val count: Int
        get() = super.count
    override val values: Sequence<Float> = sequenceOf(
//        1f,
        0f
    )
}
@Preview
@Composable
fun PreviewHomescreen() {
    HomeScreen()
}

@Preview
@Composable
fun MinguoChronology(
    @PreviewParameter(ParameterForMCH::class) progress: Float
) {
//    MotionComposeHeader(progress = progress)
}

