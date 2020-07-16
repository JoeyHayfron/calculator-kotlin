package com.engineerskasa.calculator_kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.lang.NumberFormatException
import java.math.BigDecimal

class BigDecimalViewModel : ViewModel() {

    //operands
    private var operand1: BigDecimal? = null
    private var pendingOperation = "="

    private val result = MutableLiveData<BigDecimal>()
    val stringResult: LiveData<String>
        get() = Transformations.map(result) {it.toString()}

    private val newNumber = MutableLiveData<String>()
    val stringNewNumber: LiveData<String>
        get() = newNumber

    private val operation = MutableLiveData<String>()
    val stringOperation : LiveData<String>
        get() = operation

    fun digitPressed(caption: String) {
        newNumber.value = newNumber.value + caption
    }

    fun operandPressed(op: String) {
        try {
            val value = newNumber.value?.toBigDecimal()
            if(value != null){
                performOperation(value, op)
            }
        } catch (e: NumberFormatException) {
            newNumber.value = ""
        }

        pendingOperation = op
        operation.value = pendingOperation
    }

    fun negPressed() {
        if (newNumber.value.toString().isEmpty()) {
            newNumber.value = "-"
        } else {
            try {
                newNumber.value = (newNumber.value.toString().toBigDecimal() * BigDecimal.valueOf(-1)).toString()
            } catch (e: NumberFormatException) {
                newNumber.value = ""
            }
        }
    }

    private fun performOperation(value: BigDecimal, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {

            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == BigDecimal.valueOf(0.0))
                    BigDecimal.valueOf(Double.NaN)
                else {
                    operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "+" -> operand1 = operand1!! + value
                "-" -> operand1 = operand1!! - value
            }
        }
        result.value = operand1
        newNumber.value = ""
    }

}