package tk.hb.english

import android.app.AlertDialog
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
                R.id.bottomDel -> delWord()
                R.id.bottomClear -> restoreWord()
                R.id.bottomSave -> saveWord()
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

    private fun delWord() {
        showDialog("删除", "确定标记为已学会吗？标记删除后，再次执行clear操作会再次回复。", object : OnDialogListener {
            override fun onResult() {
                viewModel.delWord()
            }
        })
    }

    private fun restoreWord() {
        showDialog("清除", "确定清除所有标记吗？标记清除后，所有数据将变成未标记初始状态。", object : OnDialogListener {
            override fun onResult() {
                viewModel.restoreWord()
            }
        })
    }

    private fun saveWord() {
        showDialog("更新", "确定更新当前词组内容吗？确定更新后，当前修改后的内容会替换词库内容。", object : OnDialogListener {
            override fun onResult() {
                viewModel.saveWord(editTextTextMultiLine.text.toString())
            }
        })
    }

    private fun showDialog(title: String, message: String, listener: OnDialogListener) {
        AlertDialog.Builder(activity).setTitle(title)
            .setMessage(message)
            .setNeutralButton("取消") { _, _ -> }
            .setPositiveButton("确定") { _, _ -> listener.onResult() }
            .show()
    }

    private interface OnDialogListener {
        fun onResult()
    }
}