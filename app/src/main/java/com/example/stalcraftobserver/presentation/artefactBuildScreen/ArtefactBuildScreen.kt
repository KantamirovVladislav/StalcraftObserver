package com.example.stalcraftobserver.presentation.artefactBuildScreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.presentation.artefactBuildScreen.components.CustomArtefactGrid
import com.example.stalcraftobserver.presentation.common.TopAppBarWithoutSearch
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme

@Composable
fun ArtefactBuildScreen(
    modifier: Modifier = Modifier,
    navController: NavController = NavController(LocalContext.current)
) {
    var artefactItemIds by remember {
        mutableStateOf(List<String?>(size = 6) { null })
    }


    CustomArtefactGrid(
        modifier = Modifier.padding(6.dp),
        maxCountCell = artefactItemIds.size,
        itemIds = artefactItemIds,
        onCellClick = { cellIndex ->
            val updatedList = artefactItemIds.toMutableList()
            updatedList[cellIndex] = "artefact$cellIndex"
            artefactItemIds = updatedList
        }
    )
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
fun GreetingPreview12() {
    StalcraftObserverTheme {
        ArtefactBuildScreen()
    }
}