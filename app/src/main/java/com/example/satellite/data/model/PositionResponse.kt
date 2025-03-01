package com.example.satellite.data.model

data class PositionResponse(
    val list: List<PositionItem>
)

data class PositionItem(
    val id: String,
    val positions: List<PositionData>
)

data class PositionData(
    val posX: Float,
    val posY: Float
)