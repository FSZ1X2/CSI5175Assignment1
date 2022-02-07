package com.example.csi5175assignment1project_zixunxiang_yaqingzhu.ui.gameb

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.Game2Activity
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.databinding.FragmentGamebBinding

class GameBFragment : Fragment() {

    private var _binding: FragmentGamebBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelB =
            ViewModelProvider(this).get(GameBViewModel::class.java)

        _binding = FragmentGamebBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //dynamically change text showed in textView
        val textView: TextView = binding.textGameBIntro
        viewModelB.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        //enter game B scene
        val start1button: Button = binding.entryGameBButton
        start1button.setOnClickListener {
            val intent = Intent(activity, Game2Activity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}