package com.example.youthconnect.Model.Sealed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.youthconnect.Model.Enum.NavScreen



sealed class ItemsBottomBar(
    val icon: ImageVector,
    val title: String,
    val ruta: String
){

    object NewsBottom : ItemsBottomBar
        (
        Icons.Outlined.Newspaper ,
        "Noticias",
        NavScreen.NewsScreen.name
    )

    object QuizBottom : ItemsBottomBar
        (
        Icons.Outlined.Quiz,
        "Quiz",
        "DecideScreen"
    )

    object ChatBottom : ItemsBottomBar
        (
        Icons.Outlined.Chat,
        "Chat",
        NavScreen.ChatScreen.name
    )

    object ProfileBottom : ItemsBottomBar(
        Icons.Outlined.PersonOutline,
        "Perfil",
        NavScreen.Profile.name
    )



}