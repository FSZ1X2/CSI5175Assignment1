package com.example.csi5175assignment1project_zixunxiang_yaqingzhu.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.*
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
            val intent = Intent(activity, GameAQuestionEditing::class.java)
            startActivity(intent)
        }
        //check saved scores
        val checkScorebutton: Button = binding.checkScore
        checkScorebutton.setOnClickListener {
            val intent = Intent(activity, UserScorePage::class.java)
            startActivity(intent)
        }
        //report issues to developers
        val reportQbutton: Button = binding.reportButton
        reportQbutton.setOnClickListener {
            //call email app to create a report mail
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "plain/text"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("zxian010@uottawa.ca"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "CSI5175 project issue report")
            intent.putExtra(Intent.EXTRA_TEXT, "Please write down any issue you meet here...")
            startActivity(Intent.createChooser(intent, ""))
        }
        //check github resource and reference
        val githubbutton: Button = binding.checkRefernece
        githubbutton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/FSZ1X2/CSI5175Assignment1"))
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}