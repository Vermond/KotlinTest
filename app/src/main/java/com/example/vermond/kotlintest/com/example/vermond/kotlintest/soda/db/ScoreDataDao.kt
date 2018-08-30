package com.example.vermond.kotlintest.com.example.vermond.kotlintest.soda.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface ScoreDataDao {

    @Query("select * from score_data")
    fun getAllScore(): List<ScoreData>

    @Query("select * from score_data order by score desc limit :count")
    fun getScores(count: Int): List<ScoreData>

    @Query("delete from score_data")
    fun deleteAllScore()

    @Insert(onConflict = REPLACE)
    fun insertScore(score: ScoreData)

    @Delete
    fun deleteScore(score: ScoreData)
}