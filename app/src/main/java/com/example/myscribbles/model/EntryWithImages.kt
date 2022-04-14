package com.example.myscribbles.model

import androidx.room.Embedded
import androidx.room.Relation

data class EntryWithImages(
    @Embedded val entry: Entry,
    @Relation(
        parentColumn = "id",
        entityColumn = "entryId"
    )
    val images: List<Image>
)
