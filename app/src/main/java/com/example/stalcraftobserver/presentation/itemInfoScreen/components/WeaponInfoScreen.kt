package com.example.stalcraftobserver.presentation.itemInfoScreen.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.presentation.common.CustomImage
import com.example.stalcraftobserver.presentation.common.CustomOutlinedCard
import com.example.stalcraftobserver.presentation.common.PagedContent
import com.example.stalcraftobserver.util.ItemInfoHelper
import com.example.stalcraftobserver.util.ItemProperty

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WeaponInfoScreen(
    imagePath: String,
    item: ItemInfo,
    modifier: Modifier = Modifier
) {
    val generalParam = remember { mutableStateOf("") }
    val damageParam = remember { mutableStateOf("") }
    val weaponParam = remember { mutableStateOf("") }
    val magazineParam = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        generalParam.value =
            ItemInfoHelper.getStringFromKeys(item, ItemProperty.Weapon.General.generalKeys)

        damageParam.value = ItemInfoHelper.getStringDamageParam(item)

        weaponParam.value = ItemInfoHelper.getStringFromKeys(
            item,
            ItemProperty.Weapon.ToolTip.WeaponInfo.weaponInfoKeys
        )

        magazineParam.value = ItemInfoHelper.getStringFromKeys(
            item,
            ItemProperty.Weapon.ToolTip.MagazineInfo.magazineInfoKeys
        )
    }

    val pages = listOf(weaponParam.value, damageParam.value + "\n" + magazineParam.value)

    Scaffold {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Row {
                    CustomOutlinedCard(
                        data = generalParam.value,
                        modifier = Modifier.weight(1f)
                    )
                    CustomImage(
                        imagePath = imagePath,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }

            item {
                PagedContent(
                    pages = pages,
                    modifier = Modifier.fillParentMaxWidth(),
                ) { pageData, contentModifier ->
                    CustomOutlinedCard(
                        data = pageData,
                        modifier = contentModifier
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillParentMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Здесь уже PagesIndicatorRounder внутри PagedContent,
                    // если хотите вынести - можно убрать из PagedContent и оставить здесь
                    // но в данном примере он уже в PagedContent
                }
            }

            item {
                Row {
                    CustomOutlinedCard(data = magazineParam.value)
                }
            }
        }
    }
}
