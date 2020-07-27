package tk.hb.english

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by HONGBO on 2020/7/27 18:45
 */
class WordViewModel : ViewModel() {

    private var showWord: MutableLiveData<WordBean> = MutableLiveData()

    fun resetWord() {
        showWord.postValue(WordBean(1, "Hongbo1", "|`Hongboo1|", "n:人名", "", 1, 0))
    }

    fun getWord(): MutableLiveData<WordBean> {
        getFirstWord()
        return showWord
    }

    fun getFirstWord() {
        showWord.postValue(WordBean(1, "Hongbo", "|`Hongboo|", "n:人名", "", 1, 0))
    }
}
