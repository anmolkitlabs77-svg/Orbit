package com.example.jetpackcomposearchi.dashboard.apod

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackcomposearchi.R
import com.example.jetpackcomposearchi.dashboard.apod.viewModel.picbyDayVM
import com.example.jetpackcomposearchi.network.room_space.SpaceWorker
import com.example.jetpackcomposearchi.other.helper
import kotlinx.coroutines.launch

@Composable
fun Apod(){

    val context = LocalContext.current
    val viewModel : picbyDayVM = hiltViewModel()
    val pictures by viewModel.pictures.collectAsState(initial = emptyList())
    var selectedIndex = remember { mutableStateOf(0) }
    val selectedPicture = pictures.getOrNull(selectedIndex.value)
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val exp = "The explosion is over, but the consequences continue. About twelve thousand years ago, a relatively normal star in the constellation Vela suddenly exploded, creating a strange point of light briefly visible to humans living near the beginning of recorded history.  The outer layers of the star crashed into the interstellar medium, driving a shock wave that is still visible today.  The featured image, taken piecemeal over 60 hours from the Khomas Region of Namibia, captures some of that filamentary and gigantic shock in visible light, with details highlighted by hydrogen (red) and oxygen (blue) emissions. As gas flies away from the detonated star, it decays and reacts with the interstellar medium, producing light in many different colors and energy bands. Remaining at the center of the Vela Supernova Remnant is a pulsar, a star as dense as nuclear matter that spins around more than ten times in a single second.   Explore the Universe: Random APOD Generator"


    LaunchedEffect(Unit) {
        viewModel.callWorker(context)
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState,)
                .background(colorResource(R.color.black))
        ) {
            val shape = RoundedCornerShape(18.dp)
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clip(shape)
                    .border(
                        2.dp,
                        color = colorResource(R.color.app_blue),
                        shape = shape
                    )

            ) {

                AsyncImage(
                    model = selectedPicture?.imageUrl,
                    contentDescription = selectedPicture?.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )

                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(shape)
                            .background(colorResource(R.color.black))
                            .border(
                                2.dp,
                                color = colorResource(R.color.app_blue),
                                shape = shape
                            )
                    ) {
                        Text(
                            text = helper.format2(selectedPicture?.date ?: helper.currentDate()),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .align(Alignment.TopStart)

                        )
                    }
                Text(
                    text = selectedPicture?.title ?: "Saturn at Night",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.BottomStart)
                )
            }


            Text(
                "ABOUT THIS IMAGE",
                color = colorResource(R.color.text_blue),
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .clip(shape)
                    .background(colorResource(R.color.black))
                    .border(
                        2.dp,
                        color = colorResource(R.color.app_blue),
                        shape = shape
                    )
            ) {

                Text(
                    text =  selectedPicture?.explanation ?: exp,
                    fontSize = 14.sp,
                    color = colorResource(R.color.text2_blue),
                    modifier = Modifier.padding(10.dp)

                )
            }

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clip(shape)
                    .background(colorResource(R.color.black))
                    .border(
                        2.dp,
                        color = colorResource(R.color.app_blue),
                        shape = shape
                    )
            ) {
                Text(
                    text = selectedPicture?.copyright ?: "José Mtanous",
                    fontSize = 14.sp,
                    color = colorResource(R.color.text2_blue),
                    modifier = Modifier.padding(10.dp)
                )
            }

            if(selectedIndex.value >= 0) {
                Text(
                    "RECENT IMAGES",
                    color = colorResource(R.color.text_blue),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }

            LazyRow(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            ) {

                items(pictures.size) {it->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(end = 10.dp)
                            .clickable(){
                                selectedIndex.value = it
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(0)
                                }
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .height(90.dp)
                                .width(120.dp)
                                .clip(shape)
                                .border(
                                    1.dp,
                                    color = if(selectedIndex.value == it) { colorResource(R.color.text_blue)} else colorResource(R.color.app_blue),
                                    shape = shape
                                )
                        )
                        {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(pictures[it].imageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "NASA APOD",
                                contentScale = ContentScale.Crop
                            )
                        }

                        Text(
                            helper.formatDate(pictures[it].date),
                            fontSize = 14.sp,
                            color = colorResource(R.color.text2_blue),
                            modifier = Modifier.align(Alignment.CenterHorizontally)

                        )
                    }
                }
            }
        }
}