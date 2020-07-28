package tk.hb.english

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.hb.english.data.db.HbDataBase
import tk.hb.english.data.db.entity.WordBean

/**
 * Created by HONGBO on 2020/7/27 18:45
 */
const val sharePreKey: String = "KEY_SHARE_PRE"
const val saveIndexKey: String = "KEY_SAVE_INDEX"

class WordViewModel : ViewModel() {

    val sharePre: SharedPreferences by lazy {
        MyApplication.getAppContext().getSharedPreferences(
            sharePreKey,
            Context.MODE_PRIVATE
        )
    }

    private var thisIndex: Int = 1

    private var showWord: MutableLiveData<WordBean> = MutableLiveData()

    fun goBack() {
        thisIndex -= 2
        resetWord()
    }

    fun resetWord() {
        Thread(Runnable {
            thisIndex++
            var data = HbDataBase.instance.wordDao()?.queryWord(thisIndex)
            if (data == null) {
                thisIndex = 1
                data = HbDataBase.instance.wordDao()?.queryWord(thisIndex)
            }
            Handler(Looper.getMainLooper()).post {
                showWord.postValue(data)
            }
        }).start()
    }

    fun getWord(): MutableLiveData<WordBean> {
        if (showWord.value == null) {
            thisIndex--
            resetWord()
        }
        return showWord
    }

    /**
     * 保存修改过的内容
     */
    fun saveWord(content: String) {
        Thread(Runnable {
            var wordBean: WordBean = showWord.value as WordBean
            wordBean.sentence = content
            HbDataBase.instance.wordDao()?.updateContent(wordBean)
        }).start()
    }

    /**
     * 保存阅读记录
     */
    fun saveIndex() {
        sharePre.edit().putInt(saveIndexKey, thisIndex).commit()
    }

    /**
     * 恢复阅读记录
     */
    fun restoreIndex() {
        thisIndex = sharePre.getInt(saveIndexKey, 1)
    }
}
