package com.example.csi5175assignment1project_zixunxiang_yaqingzhu.ui.gamea

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.Game1Activity
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.HomePageActivity
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.R
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.databinding.FragmentGameaBinding

class GameAFragment : Fragment() {

    private var _binding: FragmentGameaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelA =
            ViewModelProvider(this).get(GameAViewModel::class.java)

        _binding = FragmentGameaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //dynamically change text showed in textView
        val textView: TextView = binding.textGameAIntro
        viewModelA.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        //enter game A scene
        val start1button: Button = binding.entryGameAButton
        start1button.setOnClickListener {
            val intent = Intent(activity, Game1Activity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}