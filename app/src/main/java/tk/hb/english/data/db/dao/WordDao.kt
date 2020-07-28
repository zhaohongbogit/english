package tk.hb.english.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import tk.hb.english.data.db.entity.WordBean

/**
 * Created by HONGBO on 2020/7/27 19:33
 */
@Dao
interface WordDao {

    @Query("SELECT * FROM WORD")
    fun queryAll(): List<WordBean>

    @Query("SELECT * FROM WORD WHERE id=:id")
    fun queryWord(id: Int): WordBean

    @Query("UPDATE WORD SET STATE=0")
    fun updateStateAll()

    @Update
    fun updateWord(wordBean: WordBean)

    @Delete
    fun deleteWord(wordBean: WordBean)
}