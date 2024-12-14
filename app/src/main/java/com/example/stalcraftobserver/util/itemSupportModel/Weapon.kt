package com.example.stalcraftobserver.util.itemSupportModel

import com.example.stalcraftobserver.data.manager.Lines

data class Weapon(
    val name: Map<Lines?, Lines?>,
    val rank: Map<Lines?, Lines?>,
    val category: Map<Lines?, Lines?>, // Категория
    val weight: Map<Lines?, Double?>, // Вес
    val durability: Map<Lines?, Double?>, // Прочность
    val ammoType: Map<Lines?, Lines?>, // Тип боеприпаса
    val damage: Map<Lines?, Double?>, // Урон
    val damageDecreaseStart: Map<Lines?, Double?>,
    val endDamage: Map<Lines?, Double?>,
    val damageDecreaseEnd: Map<Lines?, Double?>,
    val magazineCapacity: Map<Lines?, Double?>, // Объем магазина
    val maxDistance: Map<Lines?, Double?>, // Максимальная дистанция
    val rateOfFire: Map<Lines?, Double?>, // Скорострельность
    val reload: Map<Lines?, Double?>, // Перезарядка
    val tacticalReload: Map<Lines?, Double?>, // Тактическая перезарядка
    val ergonomics: Map<Lines?, Double?>, //Эргономика
    val spread: Map<Lines?, Double?>, // Разброс,
    val hipFireSpread: Map<Lines?, Double?>, // Разброс от бедра
    val verticalRecoil: Map<Lines?, Double?>, // Вертикальная отдача
    val horizontalRecoil: Map<Lines?, Double?>, // Горизонтальная отдача
    val drawTime: Map<Lines?, Double?>, // Доставание
    val aimingTime: Map<Lines?, Double?>, // Прицеливание
    val damageModifier: Map<Lines?, Double?>, // Доп. хар.
    val features: Map<Lines?, Double?>, // Особенности

)