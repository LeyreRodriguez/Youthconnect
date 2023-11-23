package com.example.youthconnect.View.BottomNavigationScreens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.youthconnect.Model.Users.Child
import com.example.youthconnect.R
import com.example.youthconnect.ViewModel.ChildViewModel
import com.example.youthconnect.ViewModel.ParentViewModel
import com.example.youthconnect.ui.theme.Green
import com.example.youthconnect.ui.theme.Red


@Composable
fun ParentsProfileScreen(parentId : String,
                       viewModel: ChildViewModel = viewModel(),
                       parentViewModel: ParentViewModel = viewModel(),
                       modifier : Modifier = Modifier.background(color = Color.White),
                         navController: NavHostController
) {

    val childViewModel: ChildViewModel = viewModel()
    val parentViewModel: ParentViewModel = viewModel()

    val childState by childViewModel.childState.collectAsState(initial = emptyList())
    val parentState by parentViewModel.parentState.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        parentViewModel.getCurrentUserById(parentId)

    }

    if (parentState.isNotEmpty()) {
        val parent = parentState.first()

        LaunchedEffect(Unit) {
            childViewModel.obtenerChildsPorParentID(parent.ID)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
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
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()) {
                    val configuration = LocalConfiguration.current
                    val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp }
                    Image(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .border(
                                BorderStroke(4.dp, remember {
                                    Brush.sweepGradient(
                                        listOf(
                                            Green, Red
                                        )
                                    )
                                }),
                                CircleShape
                            )
                            .padding(4.dp)
                            .clip(CircleShape)
                    )

                    Text(
                        text = parent.FullName,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.annie_use_your_telescope)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                            letterSpacing = 0.9.sp,
                        ), modifier = Modifier
                            .padding(start = 15.dp, top = 10.dp)
                    )



                    Spacer(modifier = Modifier.size(40.dp))
                }



                Column ( modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "My kids",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = FontFamily(Font(R.font.annie_use_your_telescope)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                            letterSpacing = 0.9.sp,
                        ), modifier = Modifier
                            .padding(start = 15.dp, top = 10.dp)
                    )



                    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                        items(items = childState) { item ->
                            user(navController = navController, item)
                        }
                    }
                }

            }


        }

    }

}


@Preview(showBackground = true)
@Composable

fun ParentsProfileView(){
    Box(
        modifier = Modifier.fillMaxSize(),
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
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()) {
                val configuration = LocalConfiguration.current
                val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp }
                Image(
                    painter = painterResource(id = R.drawable.user_icon),
                    contentDescription = "icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .border(
                            BorderStroke(4.dp, remember {
                                Brush.sweepGradient(
                                    listOf(
                                        Green, Red
                                    )
                                )
                            }),
                            CircleShape
                        )
                        .padding(4.dp)
                        .clip(CircleShape)
                )

                Text(
                    text = "Juana Teresa Quintana Monroy",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.annie_use_your_telescope)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        letterSpacing = 0.9.sp,
                    ), modifier = Modifier
                        .padding(start = 15.dp, top = 10.dp)
                )



                Spacer(modifier = Modifier.size(40.dp))
            }



            Column ( modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "My kids",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontFamily = FontFamily(Font(R.font.annie_use_your_telescope)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        letterSpacing = 0.9.sp,
                    ), modifier = Modifier
                        .padding(start = 15.dp, top = 10.dp)
                )


                val childState = listOf<String>("Florencio Rodriguez Rodriguez", "Juani Quintana Monroy")

                val child = Child("Leyre Rodriguez Quintana",
                    "54148418R",
                    "4ºESO",
                    "Lr#575098",
                    false,
                    false,
                    false,
                    null,
                    listOf("45534729L", "45854715D"),
                    "45854785H")
                LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                    items(items = childState) { item ->
                        user(navController = rememberNavController(), child)
                    }
                }
            }

        }


    }
}

@Composable
fun user(navController : NavHostController, child : Child){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                navController.navigate("child_profile_screen/${child.ID}")
            }
            .padding(4.dp)

    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.user_icon),
                contentDescription = "icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(30.dp)
                    .border(
                        BorderStroke(4.dp, remember {
                            Brush.sweepGradient(
                                listOf(
                                    Green, Red
                                )
                            )
                        }),
                        CircleShape
                    )
                    .padding(4.dp)
                    .clip(CircleShape)
            )

            Text(
                text = child.FullName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(start = 10.dp)

            )




        }
    }
}
