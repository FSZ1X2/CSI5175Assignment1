package com.example.csi5175assignment1project_zixunxiang_yaqingzhu.ui.home

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
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.Game2Activity
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        //start game A directly
        val start1button: Button = binding.goGameA
        start1button.setOnClickListener {
            val intent = Intent(activity, Game1Activity::class.java)
            startActivity(intent)
        }
        //start game B directly
        val start2button: Button = binding.goGameb
        start2button.setOnClickListener {
            val intent = Intent(activity, Game2Activity::class.java)
            startActivity(intent)
        }
        //add questions to game A
        val addQtoAbutton: Button = binding.addQtoA
        addQtoAbutton.setOnClickListener {
            val intent = Intent(activity, Game2Activity::class.java)
            startActivity(intent)
        }
        //add images to game B
        val addImgtoBbutton: Button = binding.addImagetoB
        addImgtoBbutton.setOnClickListener {
            val intent = Intent(activity, Game2Activity::class.java)
            startActivity(intent)
        }
        //report issues to developers
        val reportQbutton: Button = binding.reportButton
        reportQbutton.setOnClickListener {
            //TODO: add email pop window
        }
        //check saved scores
        val checkScorebutton: Button = binding.checkScore
        checkScorebutton.setOnClickListener {
            //TODO: load user score saved on xml file
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}