package com.example.littlelemon

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class MenuItemRoom(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface MenuDao {
    @Query("SELECT * FROM MenuItemRoom")
    fun getAll(): List<MenuItemRoom>

    @Query("SELECT * FROM MenuItemRoom WHERE id = :id")
    fun getMenuItemById(id: Int): MenuItemRoom?

    @Query("SELECT COUNT(*) FROM MenuItemRoom")
    fun getItemCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<MenuItemRoom>)
}

@Database(entities = [MenuItemRoom::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao
}

