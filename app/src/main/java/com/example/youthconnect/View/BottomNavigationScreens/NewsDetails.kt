package com.example.youthconnect.View.BottomNavigationScreens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.youthconnect.Model.Object.News
import com.example.youthconnect.R
import com.example.youthconnect.ViewModel.NewsViewModel
import com.example.youthconnect.ViewModel.UserViewModel
import com.example.youthconnect.ui.theme.Green
import com.example.youthconnect.ui.theme.Red

@Composable
fun NewsDetails(newsId : String,
                modifier : Modifier = Modifier.background(color = Color.White)
) {

    val NewsViewModel : NewsViewModel = hiltViewModel()
    val UserViewModel : UserViewModel = hiltViewModel()
    var news by remember { mutableStateOf<News?>(null) }
    val imageUrlState = remember { mutableStateOf("") }

    LaunchedEffect(NewsViewModel) {
        try {

            news = NewsViewModel.getNewsById(newsId)
        } catch (e: Exception) {
            Log.e("Firestore", "Error en ChildList", e)
        }
    }

    LaunchedEffect(Unit) {
        UserViewModel.getProfileImage(
            onSuccess = { url ->
                imageUrlState.value = url
            },
            onFailure = { exception ->
                // Manejar el error, por ejemplo, mostrar un mensaje
            }
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                // Dibuja un rectángulo blanco como fondo
                drawRect(Color.White)

                // Define el pincel para el borde con el gradiente del Brush
                val borderBrush = Brush.horizontalGradient(
                    listOf(
                        Color(0xFFE15554),
                        Color(0xFF3BB273),
                        Color(0xFFE1BC29),
                        Color(0xFF4D9DE0)
                    )
                )

                // Dibuja el borde con el pincel definido
                drawRect(
                    brush = borderBrush,
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width, size.height),
                    style = Stroke(width = 15.dp.toPx()) // Ancho del borde
                )
            }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(15.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                news?.Title?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = FontFamily(Font(R.font.annie_use_your_telescope)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                            letterSpacing = 0.9.sp,
                        ), modifier = Modifier
                            .padding(start = 15.dp, top = 10.dp)
                    )
                }

            }



            val configuration = LocalConfiguration.current
            val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp }
            news?.Image?.let {
                CoilImage(
                    imageUrl = it,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f), // Proporción de aspecto cuadrado, puedes ajustarlo según tus necesidades
                    contentScale = ContentScale.Crop,
                    width = screenWidth,
                    height = Dp(300.0F) // Altura inicial
                )
            }

            news?.Description?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.annie_use_your_telescope)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        letterSpacing = 0.9.sp,
                    ), modifier = Modifier
                        .padding(start = 15.dp, top = 10.dp)
                )
            }


        }


    }


}

