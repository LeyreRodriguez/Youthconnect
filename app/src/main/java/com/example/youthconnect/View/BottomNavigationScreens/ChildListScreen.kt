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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.youthconnect.Model.Enum.NavScreen
import com.example.youthconnect.Model.Users.Child
import com.example.youthconnect.R
import com.example.youthconnect.ViewModel.ChildViewModel
import com.example.youthconnect.ViewModel.NewsViewModel
import com.example.youthconnect.ui.theme.Green
import com.example.youthconnect.ui.theme.Red



@Preview(showBackground = true)
@Composable
fun Preview(){
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

        val child2 = Child("Leyre Rodriguez Quintana",
            "54148418R",
            "4ºESO",
            "Lr#575098",
            false,
            false,
            false,
            null,
            listOf("45534729L", "45854715D"),
            "45854785H")

        val child3 = Child("Ari",
            "54148418R",
            "4ºESO",
            "Lr#575098",
            false,
            false,
            false,
            null,
            listOf("45534729L", "45854715D"),
            "45854785H")
        val childState = listOf<Child>(child,child3,child3)
        Column(modifier = Modifier.fillMaxHeight()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Children list",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontFamily = FontFamily(Font(R.font.annie_use_your_telescope)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        letterSpacing = 0.9.sp,
                    ), textAlign = TextAlign.Center
                )


                val textState = remember { mutableStateOf(TextFieldValue(""))}

                SearchView(state= textState, placeHolder= "Search here...", modifier = Modifier)

                val searchedText = textState.value.text

                LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                    items(items = childState) { item ->
                        list(navController = rememberNavController(), item)
                    }
                }
            }




        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    state: MutableState<TextFieldValue>,
    placeHolder: String,
    modifier: Modifier
) {
    var isTextFieldEmpty by remember { mutableStateOf(true) }


        TextField(
            value = state.value,
            onValueChange = {value->
                state.value = value
                isTextFieldEmpty = value.text.isEmpty()
            },
            modifier
                //.fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(30.dp))
                .border(2.dp, Color.DarkGray, RoundedCornerShape(30.dp)),


            placeholder = {
                Text(text = placeHolder)
            },


            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White
            ),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black, fontSize = 20.sp
            ),

            trailingIcon = {
                if (!isTextFieldEmpty) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Text",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .clickable {
                                state.value = TextFieldValue("")
                                isTextFieldEmpty = true
                            }
                            .padding(12.dp)
                    )
                }
            }
        )




}

@Composable
fun ChildListScreen(navController : NavHostController

){
    val childViewModel: ChildViewModel = viewModel()
    val childState by childViewModel.childState.collectAsState(initial = emptyList())


    LaunchedEffect(Unit) {
        childViewModel.getAllChilds()

    }

    if (childState.isNotEmpty()) {
        Log.i("UWU", childState.toString())
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


            Column(modifier = Modifier.fillMaxHeight()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Children list",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = FontFamily(Font(R.font.annie_use_your_telescope)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                            letterSpacing = 0.9.sp,
                        ), textAlign = TextAlign.Center
                    )


                    val textState = remember { mutableStateOf(TextFieldValue(""))}

                    SearchView(state= textState, placeHolder= "Search here...", modifier = Modifier)

                    val searchedText = textState.value.text

                    LazyColumn(modifier = Modifier.padding(10.dp)) {
                        items(items = childState.filter {
                            it.FullName.contains(searchedText, ignoreCase = true)
                        }, key = { it.ID }) { item ->
                            list(navController = navController, item)
                        }
                    }
                }

            }

        }

    }
}




@Composable
fun list(navController: NavHostController, child: Child) {
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
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_icon),
                    contentDescription = "icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
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
                    modifier = Modifier.padding(start = 10.dp),
                    textAlign = TextAlign.End
                )
            }

            val checked = remember { mutableStateOf(true) }
            Checkbox(
                checked = checked.value,
                onCheckedChange = { checked.value = it }
            )
        }
    }
}
