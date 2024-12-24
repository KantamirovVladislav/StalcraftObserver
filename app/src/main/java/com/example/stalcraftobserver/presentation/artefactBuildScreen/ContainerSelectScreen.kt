package com.example.stalcraftobserver.presentation.artefactBuildScreen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stalcraftobserver.presentation.common.CustomImage
import com.example.stalcraftobserver.presentation.common.TopAppBarWithoutSearch
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme
import com.example.stalcraftobserver.util.NavigationItem

@Composable
fun ContainerSelectScreen(
    modifier: Modifier = Modifier,
    navController: NavController = NavController(LocalContext.current),
    containerId: String? = null
) {
    val isChoosed = remember { mutableStateOf(true) }
    val isContinueClicked = remember { mutableStateOf(false) }

    TopAppBarWithoutSearch(
        navController = navController,
        title = "Сборка"
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f)
            ) {
                if (!containerId.isNullOrBlank()) {
                    CustomImage(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/containers/$containerId.png",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    Text(
                        text = "Контейнер не выбран",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f)
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = !isContinueClicked.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        OutlinedButton(
                            onClick = {
                                val categories = listOf("containers")
                                navController.navigate(
                                    NavigationItem.SelectItem.createRoute("container", categories)
                                )
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (!isChoosed.value) "Выбрать контейнер" else "Изменить контейнер",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (isChoosed.value) {
                            OutlinedButton(
                                onClick = {
                                    isContinueClicked.value = true
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text(
                                    text = "Продолжить",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = isContinueClicked.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    ArtefactBuildScreen(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController
                    )
                }
            }
        }
    }
}



@Composable
fun SelectContainerButton(
    modifier: Modifier = Modifier,
    navController: NavController,
    isNotEmpty: Boolean = false,
    itemSlot: String
) {
    OutlinedButton(
        onClick = {
            val categories = listOf("containers")

            navController.navigate(
                NavigationItem.SelectItem.createRoute(itemSlot, categories)
            )
        },
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = if (!isNotEmpty) "Выбрать контейнер" else "Изменить контейнер",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(device = "spec:width=411dp,height=891dp,dpi=420") // Pixel 4
@Preview(device = "spec:width=360dp,height=740dp,dpi=320") // Nexus 5
@Preview(device = "spec:width=320dp,height=480dp,dpi=160") // Small Phone
@Preview(device = "spec:width=600dp,height=1024dp,dpi=240") // 7-inch Tablet
@Preview(device = "spec:width=800dp,height=1280dp,dpi=240") // 10-inch Tablet
@Preview(device = "spec:width=1024dp,height=1366dp,dpi=264") // iPad Pro 10.5
@Preview(device = "spec:width=1440dp,height=2560dp,dpi=560") // Pixel XL
@Preview(device = "spec:width=1080dp,height=1920dp,dpi=480") // Full HD Phone
@Preview(device = "spec:width=1440dp,height=2960dp,dpi=560") // Galaxy S8
@Preview(device = "spec:width=768dp,height=1024dp,dpi=160") // Nexus 7
@Composable
fun GreetingPreview() {
    StalcraftObserverTheme {
        ContainerSelectScreen()
    }
}