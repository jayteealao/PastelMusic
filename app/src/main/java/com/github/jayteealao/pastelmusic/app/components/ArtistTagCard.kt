package com.github.jayteealao.pastelmusic.app.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

/**
 * A tag-style card component displaying artist/album information
 * with a distinctive luggage tag aesthetic.
 *
 * Features:
 * - Configurable hook position at top
 * - Album artwork display with custom component
 * - Song count
 * - Artist name
 * - Action button
 * - Colored bottom accent strip
 * - Colored 3D shadow effect matching the original design
 *
 * @param artistName Name of the artist
 * @param songCount Number of songs
 * @param artworkUri URI of the artwork image
 * @param modifier Modifier for the card
 * @param accentColor Color for both the shadow and bottom accent
 * @param hookOffset Horizontal offset for hook position (0.dp = centered)
 * @param onCardClick Callback when card is clicked
 * @param onActionClick Callback when action button is clicked
 */
@Composable
fun ArtistTagCard(
    artistName: String,
    songCount: Int,
    artworkUri: Uri?,
    modifier: Modifier = Modifier,
    accentColor: Color = Color(0xFF4CAF50),
    hookOffset: Dp = 0.dp,
    onCardClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .width(180.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // Colored shadow layer (z-index 0 - back)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.TopCenter)
                .offset(y = 52.dp) // 32dp hook container + 12dp hook offset + 8dp shadow offset
                .clip(RoundedCornerShape(24.dp))
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(24.dp)
                )
                .background(accentColor)
                .zIndex(0f)
        ) {
            // Invisible spacer to match main card height
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Album artwork using custom component
                TagArtwork(
                    artworkUri = artworkUri,
                    contentDescription = "Album art for $artistName",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Info section with song count, artist name, and action button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // Song count
                        Text(
                            text = "$songCount Song${if (songCount != 1) "s" else ""}",
                            style = MaterialTheme.typography.caption,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        // Artist name
                        Text(
                            text = artistName,
                            style = MaterialTheme.typography.body1,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Action button
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color.Black)
                            .clickable(onClick = onActionClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Main card (z-index 1 - middle)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.TopCenter)
                .offset(y = 44.dp) // 32dp hook container + 12dp hook offset
                .clip(RoundedCornerShape(24.dp))
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(24.dp)
                )
                .background(Color.White)
                .clickable(onClick = onCardClick)
                .padding(16.dp)
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Album artwork using custom component
            TagArtwork(
                artworkUri = artworkUri,
                contentDescription = "Album art for $artistName",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Info section with song count, artist name, and action button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Song count
                    Text(
                        text = "$songCount Song${if (songCount != 1) "s" else ""}",
                        style = MaterialTheme.typography.caption,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // Artist name
                    Text(
                        text = artistName,
                        style = MaterialTheme.typography.body1,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 16.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Action button
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                        .clickable(onClick = onActionClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Hook at the top (z-index 2 - front)
        TagHook(
            modifier = Modifier
                .height(32.dp)
                .width(16.dp)
                .align(Alignment.TopCenter)
                .offset(x = hookOffset, y = 12.dp)
                .zIndex(2f)
        )
    }
}
