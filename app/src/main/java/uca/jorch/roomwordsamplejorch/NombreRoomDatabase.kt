package uca.jorch.roomwordsamplejorch

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uca.jorch.roomwordsamplejorch.dao.NombreDao

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Nombre::class), version = 1, exportSchema = false)
abstract class NombreRoomDatabase : RoomDatabase() {

    abstract fun nombreDao(): NombreDao

    private class NombreDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var nombreDao = database.nombreDao()

                    // Delete all content here.
                    nombreDao.deleteAll()

                    // Add sample words.
                    var nombre = Nombre("Hello")
                    nombreDao.insert(nombre)
                    nombre = Nombre("World!")
                    nombreDao.insert(nombre)

                    // TODO: Add your own words!
                    nombre = Nombre("TODO!")
                    nombreDao.insert(nombre)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NombreRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NombreRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NombreRoomDatabase::class.java,
                    "Nombre"
                )
                    .addCallback(NombreDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}