package tk.hb.english

/**
 * Created by HONGBO on 2020/7/27 18:51
 */
data class WordBean(
    val id: Int,
    val content: String,
    val pronunciation: String,
    val explanation: String,
    val sentence: String,
    val favorite: Int,
    val state: Int
)