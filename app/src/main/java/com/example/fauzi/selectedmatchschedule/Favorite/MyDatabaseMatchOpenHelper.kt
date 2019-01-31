package com.example.fauzi.selectedmatchschedule.favorite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.fauzi.selectedmatchschedule.favorite.match.FavoriteMatch
import com.example.fauzi.selectedmatchschedule.favorite.team.Favorite
import org.jetbrains.anko.db.*

class MyDatabaseMatchOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {
    companion object {
        private var instance: MyDatabaseMatchOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseMatchOpenHelper {
            if (instance == null) {
                instance = MyDatabaseMatchOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseMatchOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoriteMatch.TABLE_FAVORITE, true,
                FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteMatch.ID_EVENT to TEXT,
                FavoriteMatch.LEAGUE_ID to TEXT,
                FavoriteMatch.HOME_ID to TEXT,
                FavoriteMatch.AWAY_ID to TEXT,
                FavoriteMatch.DATE to TEXT,
                FavoriteMatch.HOME_SCORE to TEXT,
                FavoriteMatch.AWAY_SCORE to TEXT,
                FavoriteMatch.HOME_VS_AWAY to TEXT
        )

        db.createTable(Favorite.TABLE_FAVORITE_TEAM, true,
                Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorite.TEAM_ID to TEXT + UNIQUE,
                Favorite.TEAM_NAME to TEXT,
                Favorite.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavoriteMatch.TABLE_FAVORITE, true)
        db.dropTable(Favorite.TABLE_FAVORITE_TEAM, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseMatchOpenHelper
    get() = MyDatabaseMatchOpenHelper.getInstance(applicationContext)