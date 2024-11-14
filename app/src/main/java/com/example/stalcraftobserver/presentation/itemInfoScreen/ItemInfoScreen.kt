package com.example.stalcraftobserver.presentation.itemInfoScreen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.R
import com.example.stalcraftobserver.data.manager.Element
import com.example.stalcraftobserver.data.manager.InfoBlock
import com.example.stalcraftobserver.domain.model.ItemInfoViewModel
import com.example.stalcraftobserver.presentation.itemInfoScreen.common.ArmorInfoScreen
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme

@Composable
fun ItemInfoScreen(
    id: String,
    modifier: Modifier = Modifier,
    viewModel: ItemInfoViewModel
) {
    val info by viewModel.info.collectAsState()

    val elementValue = remember(info){
        info?.infoBlocks?.filterIsInstance<InfoBlock.List>()
    }

    val textValue = remember(info) {
        info?.infoBlocks?.filterIsInstance<InfoBlock.Text>()
    }

    val keyValueValues = remember(info) {
        elementValue?.flatMap { it.elements!! }?.filterIsInstance<Element.KeyValueElement>()
    }

    val numericValues = remember(info) {
        elementValue?.flatMap { it.elements!! }?.filterIsInstance<Element.NumericElement>()
    }

    val textValues = remember(info) {
        elementValue?.flatMap { it.elements!! }?.filterIsInstance<Element.TextElement>()
    }


    LaunchedEffect(Unit) {
        viewModel.getItemWithId(id)
    }

    info?.let {
        ArmorInfoScreen(
        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/armor/combat/1rpl6.png",
        item = it
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
fun GreetingPreviewItemInfoScreen() {
    StalcraftObserverTheme {
        //ItemInfoScreen()
    }
}