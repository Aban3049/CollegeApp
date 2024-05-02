package com.pandaapps.abmsstudies.NoticeBoard.models


data class modelNotice(
    val uid: String,
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val timestamp: Long
) {
    constructor() : this("", "", "", "", "", "", 0) // Initialize with empty values
}

