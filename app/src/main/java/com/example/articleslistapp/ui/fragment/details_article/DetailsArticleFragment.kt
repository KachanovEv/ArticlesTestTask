package com.example.articleslistapp.ui.fragment.details_article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.articleslistapp.R
import kotlinx.android.synthetic.main.fragment_details_articles.*

class DetailsArticleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataFromArguments()

    }

    private fun setDataFromArguments() {
        if (arguments != null) {
            val content = DetailsArticleFragmentArgs.fromBundle(requireArguments()).content
            val title = DetailsArticleFragmentArgs.fromBundle(requireArguments()).title
            titleTv.text = title
            descriptionTv.text = content
        }
    }

}