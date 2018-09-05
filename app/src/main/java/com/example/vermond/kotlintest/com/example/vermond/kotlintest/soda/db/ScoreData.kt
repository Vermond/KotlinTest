package com.example.vermond.kotlintest.com.example.vermond.kotlintest.soda.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//Room 참고 링크
//https://manijshrestha.wordpress.com/2017/06/03/using-room-with-kotlin/
//https://developer.android.com/training/data-storage/room/
//https://github.com/muhrahmatullah/Kotlin-crud-with-Room

@Parcelize
@Entity(tableName = "score_data")
data class ScoreData(@ColumnInfo(name = "score")var score: Double,
                   @PrimaryKey(autoGenerate = true) var id: Long = 0) : Parcelable
