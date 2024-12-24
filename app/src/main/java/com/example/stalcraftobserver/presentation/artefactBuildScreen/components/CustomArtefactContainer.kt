package com.example.stalcraftobserver.presentation.artefactBuildScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stalcraftobserver.presentation.artefactBuildScreen.ArtefactBuildScreen
import com.example.stalcraftobserver.presentation.common.CustomImage
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme

@Composable
fun CustomArtefactGrid(
    modifier: Modifier = Modifier,
    maxCountCell: Int = 6,
    itemIds: List<String?> = emptyList(),
    onCellClick: (Int) -> Unit = {}
) {
    val cells = (0 until maxCountCell).map { index ->
        itemIds.getOrNull(index)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp, max = 200.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cells.size) { index ->
            ArtefactCell(
                modifier = Modifier,
                itemId = cells[index],
                onClick = { onCellClick(index) }
            )
        }
    }
}

@Composable
fun ArtefactCell(
    modifier: Modifier = Modifier,
    itemId: String?,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (itemId == null) {
            Text(
                text = "Пусто",
                color = Color.White,
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            CustomImage(
                imagePath = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
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
fun GreetingPreview12() {
    StalcraftObserverTheme {
        CustomArtefactGrid()
    }
}