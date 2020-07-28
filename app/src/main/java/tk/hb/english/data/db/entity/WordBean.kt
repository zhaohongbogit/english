package tk.hb.english.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by HONGBO on 2020/7/27 18:51
 */
@Entity(tableName = "word")
data class WordBean(
    @PrimaryKey
    val id: Int,
    val content: String,
    val pronunciation: String?,
    val explanation: String?,
    val sentence: String?,
    val favorite: Int,
    val state: Int
)