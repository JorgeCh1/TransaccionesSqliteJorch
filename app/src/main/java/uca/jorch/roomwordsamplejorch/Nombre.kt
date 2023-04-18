package uca.jorch.roomwordsamplejorch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nombre")
class Nombre(@PrimaryKey @ColumnInfo(name = "nombre") val word: String)