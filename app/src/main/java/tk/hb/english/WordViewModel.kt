package tk.hb.english

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.hb.english.data.db.HbDataBase
import tk.hb.english.data.db.entity.WordBean

/**
 * Created by HONGBO on 2020/7/27 18:45
 */
class WordViewModel : ViewModel() {

    private var thisIndex: Int = 1

    private var showWord: MutableLiveData<WordBean> = MutableLiveData()

    fun resetWord() {
        Thread(Runnable {
            thisIndex++
            var data = HbDataBase.getDb()?.wordDao()?.queryWord(thisIndex)
            if (data == null) {
                thisIndex = 1
                data = HbDataBase.getDb()?.wordDao()?.queryWord(thisIndex)
            }
            Handler(Looper.getMainLooper()).post {
                showWord.postValue(data)
            }
        }).start()
    }

    fun getWord(): MutableLiveData<WordBean> {
        getFirstWord()
        return showWord
    }

    fun getFirstWord() {
        showWord.postValue(
            WordBean(
                1,
                "Hongbo",
                "|`Hongboo|",
                "n:人名",
                "",
                1,
                0
            )
        )
    }
}
