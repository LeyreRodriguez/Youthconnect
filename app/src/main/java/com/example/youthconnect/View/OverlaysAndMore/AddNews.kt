package com.example.youthconnect.View.OverlaysAndMore

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.youthconnect.Model.Constants.ALL_IMAGES
import com.example.youthconnect.Model.Enum.NavScreen
import com.example.youthconnect.Model.Object.News
import com.example.youthconnect.Model.Sealed.Response
import com.example.youthconnect.View.Authentication.CustomOutlinedTextField
import com.example.youthconnect.ViewModel.NewsViewModel
import com.example.youthconnect.ViewModel.NotificationViewModel
import java.util.UUID


@SuppressLint("SuspiciousIndentation")
@Composable
fun AddNews(onDismiss: () -> Unit , navController: NavController) {
    val newsViewModel: NewsViewModel = hiltViewModel()

    val id = UUID.randomUUID().toString()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember {
        mutableStateOf(newsViewModel.addImageToStorageResponse as? Uri)
    }
    val listState = rememberLazyListState()

    val galleryLauncher = rememberLauncherForActivityResult(contract = GetContent()) { selectedUri ->
        selectedUri?.let {
            imageUri = it
            newsViewModel.addNewsToStorage(selectedUri, id)
        }
    }

    val viewModel: NotificationViewModel = viewModel()
    var isBroadcast by remember { mutableStateOf(false) }
    val mcontext = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icons.Outlined.Newspaper },
        title = { Text(text = "Inserte una nueva noticia") },
        text = {
            LazyColumn(state = listState) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomOutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = "Titulo",
                            leadingIconImageVector = Icons.Default.Title
                        )

                        CustomOutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = "Descripcion",
                            leadingIconImageVector = Icons.Default.Description
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isBroadcast,
                                onCheckedChange = { isChecked -> isBroadcast = isChecked },
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(text = "Notificar a los usuarios")
                        }



                        Button(
                            onClick = { galleryLauncher.launch(ALL_IMAGES) }
                        ) {
                            Text("Seleccionar imagen")
                        }

                        imageUri?.let { uri ->
                            Image(
                                painter = rememberImagePainter(uri),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(200.dp)
                                    .padding(vertical = 8.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))


                            )
                        }
                    }

                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val news = News(id, title, description)


                    newsViewModel.getNewsImageFromDatabase()

                    if (newsViewModel.addImageToStorageResponse is Response.Success) {
                        val imageUrl = (newsViewModel.addImageToStorageResponse as Response.Success).data.toString()

                        newsViewModel.addNewsToDatabase(Uri.parse(imageUrl), news)
                        Toast.makeText(mcontext, "Noticia agregada correctamente", Toast.LENGTH_SHORT).show()
                    }

                    val state = viewModel.state
                    if (state.isEnteringToken && isBroadcast) {
                        viewModel.sendMessage(isBroadcast = true, news)
                        Toast.makeText(mcontext, "Notificación enviada correctamente", Toast.LENGTH_SHORT).show()

                    }




                    title = ""
                    description = ""

                    navController.navigate(NavScreen.NewsScreen.name)
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text("Cancelar")
            }
        }
    )
}




