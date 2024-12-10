package com.example.stalcraftobserver.presentation.itemInfoScreen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stalcraftobserver.data.manager.Element
import com.example.stalcraftobserver.data.manager.InfoBlock
import com.example.stalcraftobserver.domain.model.viewModel.ItemInfoViewModel
import com.example.stalcraftobserver.presentation.itemInfoScreen.common.ArmorInfoScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.common.ArtefactInfoScreen
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ItemInfoScreen(
    id: String,
    modifier: Modifier = Modifier,
    viewModel: ItemInfoViewModel
) {
    val info by viewModel.info.collectAsState()



    LaunchedEffect(Unit) {
        viewModel.getItemWithId(id)
    }

    Scaffold {
        info?.let {
            if (it.category.contains("armor")){
                ArmorInfoScreen(
                    imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                    item = it
                )
            }
            else if (it.category.contains("artefact")){
                ArtefactInfoScreen(
                    imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                    item = it
                )
            }

        }
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
fun GreetingPreviewItemInfoScreen() {
    StalcraftObserverTheme {
        //ItemInfoScreen()
    }
}