package com.engineerskasa.calculator_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: CalculatorViewModel by viewModels()
        viewModel.stringNewNumber.observe(this, Observer<String> { stringNumber -> newNumber.setText(stringNumber) })
        viewModel.stringResult.observe(this, Observer<String> { stringResult -> result.setText(stringResult) })
        viewModel.stringOperation.observe(this, Observer<String> { stringOperation -> operation.setText(stringOperation)  })

        val listener = View.OnClickListener { v ->
            viewModel.digitPressed((v as Button).text.toString())
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            viewModel.operandPressed((v as Button).text.toString())
        }

        val negListener = View.OnClickListener {
            viewModel.negPressed()
        }
        buttonPlus.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonEquals.setOnClickListener(opListener)

        buttonNeg.setOnClickListener(negListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.scientific,R.id.normal -> {
                if(!item.isChecked){
                    item.isChecked = true
                    if(item.title == "Normal"){
                        val viewModel: CalculatorViewModel by viewModels()
                        viewModel.stringNewNumber.observe(this, Observer<String> { stringNumber -> newNumber.setText(stringNumber) })
                        viewModel.stringResult.observe(this, Observer<String> { stringResult -> result.setText(stringResult) })
                        viewModel.stringOperation.observe(this, Observer<String> { stringOperation -> operation.setText(stringOperation)  })
                    }else{
                        val viewModel: BigDecimalViewModel by viewModels()
                        viewModel.stringNewNumber.observe(this, Observer<String> { stringNumber -> newNumber.setText(stringNumber) })
                        viewModel.stringResult.observe(this, Observer<String> { stringResult -> result.setText(stringResult) })
                        viewModel.stringOperation.observe(this, Observer<String> { stringOperation -> operation.setText(stringOperation)  })
                    }
                }
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }
}