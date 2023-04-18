package uca.jorch.roomwordsamplejorch.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uca.jorch.roomwordsamplejorch.Nombre

@Dao
interface NombreDao {

    @Query("SELECT * FROM nombre ORDER BY nombre ASC")
    fun getAll(): Flow<List<Nombre>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nombre: Nombre)

    @Query("DELETE FROM nombre")
    suspend fun deleteAll()
}