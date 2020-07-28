package tk.hb.english

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var viewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.let { ViewModelProvider(it).get(WordViewModel::class.java) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //底部菜单
        bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.bottomDel -> {
                    viewModel.delWord()
                }
                R.id.bottomClear -> {
                    viewModel.restoreWord()
                }
                R.id.bottomSave -> {
                    viewModel.saveWord(editTextTextMultiLine.text.toString())
                    editTextTextMultiLine.isEnabled = false
                }
                R.id.bottomGo -> {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
            }
            true
        }
        bottomAppBar.setNavigationOnClickListener {
            //TODO 侧边栏
        }
        fabNext.setOnClickListener { viewModel.goNext() }
        fabPre.setOnClickListener { viewModel.goBack() }
    }

    override fun onStart() {
        super.onStart()
        viewModel.restoreIndex() //恢复上次阅读记录
        viewModel.getWord().observe(this, Observer {
            textView4.text = it.id.toString()
            textView.text = it.content
            textView2.text = it.pronunciation
            textView3.text = it.explanation
            editTextTextMultiLine.setText(it.sentence)
        })
    }

    override fun onPause() {
        //退出前先保存阅读记录
        viewModel.saveIndex()
        super.onPause()
    }
}