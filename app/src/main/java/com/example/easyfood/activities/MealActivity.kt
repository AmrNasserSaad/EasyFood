package com.example.easyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.pojo.Meal
import com.example.easyfood.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String

    private lateinit var mealMvvm : MealViewModel

    private lateinit var youtubeLink : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

       mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]

        observeMealDtailsLiveData()

        getMealInformationFromIntent()

        setInformationInView()

        mealMvvm.getMealDetails(mealId)

        onYoutubeImageClick()

    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)

        }
    }

    private fun observeMealDtailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(t: Meal) {
                val meal = t

                binding.tvCategoryInfo.text = "Category : ${meal!!.strCategory}"
                binding.tvAreaInfo.text = "Area  : ${meal!!.strArea}"
                binding.tvContent.text = meal.strInstructions

                youtubeLink = meal.strYoutube!!
            }

        })
    }

    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent

        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }


}