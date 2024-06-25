package com.millerr.bmisideprojectapplication

import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.millerr.bmisideprojectapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: BmiViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(BmiViewModel::class.java)
        viewModel.bmiResult.observe(this) {bmiResult ->
            if (bmiResult.toFloat() > 0)
            binding.bmiNum.text = bmiResult.toString()
            else
                binding.bmiNum.text = ""

        }
        viewModel.status.observe(this) { status ->
            if (status != BmiStatus.INIT){
                binding.feeback.text = status.toString()
                AlertDialog.Builder(this)
                    .setTitle("BMI訊息")
                    .setMessage(status.toString())
                    .setPositiveButton("OK", null)
                    .setNegativeButton("RESET"){ dialog,which ->
                        binding.weightEditText.text.clear()
                        binding.heightEditText.text.clear()
                        binding.bmiNum.text = ""
                        binding.feeback.text = ""
                    }
                    .show()

            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun calculateBMI(view: View){
        viewModel.calBmi(binding.weightEditText.text.toString().toFloat(),
            binding.heightEditText.text.toString().toFloat())

    }
}