package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var viewNumber: TextView
    private lateinit var viewOperation: TextView
    private lateinit var mainEditPanel: EditText
    private lateinit var cleanButton: Button
    private lateinit var backspaceButton: Button
    private lateinit var reOperationButton: Button
    private var number1: String = ""
    private var number2: String = ""
    private var operation: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewNumber = findViewById(R.id.viewNumber)
        viewOperation = findViewById(R.id.viewOperation)
        mainEditPanel = findViewById(R.id.mainEditPanel)
        cleanButton = findViewById(R.id.cleanButton)
        backspaceButton = findViewById(R.id.backspaceButton)
        reOperationButton = findViewById(R.id.reOperationButton)

        cleanButton.setOnClickListener {
            viewNumber.text = ""
            viewOperation.text = ""
            number1 = ""
            number2 = ""
            operation = ""
            mainEditPanel.text.clear()
        }

        backspaceButton.setOnClickListener {
            var numberEdit = mainEditPanel.text.toString()
            val numberView = viewNumber.text.toString()
            val operationText = viewOperation.text.toString()
            if(numberEdit != "" && numberView != "" && operationText == "" && numberEdit.length > 1){
                numberEdit = numberEdit.dropLast(1)
                mainEditPanel.text.clear()
                mainEditPanel.append(numberEdit)
                viewNumber.text = numberEdit
                return@setOnClickListener
            }
            if(numberEdit != "" && numberView != "" && operationText == "" && numberEdit.length == 1) {
                viewNumber.text = ""
                viewOperation.text = ""
                number1 = ""
                number2 = ""
                operation = ""
                mainEditPanel.text.clear()
            }
            if(numberEdit != ""){
                numberEdit = numberEdit.dropLast(1)
                mainEditPanel.text.clear()
                mainEditPanel.append(numberEdit)
                return@setOnClickListener
            }
            if(operation != ""){
                operation = ""
                reload()
                number1 = "";
                viewNumber.text = ""
                return@setOnClickListener
            }
            if(number1 != ""){
                number1 = number1.dropLast(1)
                reload()
                return@setOnClickListener
            }
        }

        reOperationButton.setOnClickListener {
            var number = mainEditPanel.text.toString()
            if(number != ""){
                number = validateNumber(number)
                number = if(number.toDouble() < 0)
                    number.drop(1)
                else
                    "-$number"
                mainEditPanel.text.clear()
                mainEditPanel.append(number)
            }
        }
    }

    // Слушатель нажатия на кнопки с цифрами и точкой
    fun clickButtonNumber(view: View) {
        if(view is Button){
            val number = mainEditPanel.text.toString()
            if(number == "0" && view.text.toString() == "0") {
                showError("Вы пытаетесь ввестии незначемые нули!")
                return Unit
            }
            if(number == "0" && view.text.toString() != "."){
                showError("После нуля должна идти точка!")
                return Unit
            }
            mainEditPanel.append(view.text)
        }
    }
    // Cлушатель нажатия на кнопки операций
    fun clickButtonOperation(view: View) {
        if((view is Button) && (mainEditPanel.text.toString() == "") && (viewNumber.text.toString() != "") && (viewOperation.text != "")){
            showError("Введите второе число!")
            return Unit
        }
        if((view is Button) && (mainEditPanel.text.toString() != "") && (viewNumber.text.toString() != "") && (viewOperation.text == "")){
            viewNumber.text = mainEditPanel.text.toString()
            addOperation(view.text.toString())
            viewOperation.text = operation
            mainEditPanel.text.clear()
            return Unit
        }
        if(view is Button){
            addOperation(view.text.toString())
            viewOperation.text = operation
        }
    }
    // Слушатель нажатия на кнопку =
    fun btnResult(view: View) {
        if((view is Button) && (mainEditPanel.text.toString() == "") && (viewNumber.text.toString() != "") && (viewOperation.text == "")){
            showError("Введите операцию и второе значение!")
            return Unit
        }
        if((view is Button) && (mainEditPanel.text.toString() == "") && (viewNumber.text.toString() != "") && (viewOperation.text != "")){
            showError("Введите второе число!")
            return Unit
        }
        if((view is Button) && (number1 != "") && (operation != "")){
            number2 = validateNumber(mainEditPanel.text.toString())
            calculateNumber()
            operation = ""
            reload()
            viewOperation.text = ""
            viewNumber.text = ""
            return Unit
        }
        val number = validateNumber(mainEditPanel.text.toString())
        if((view is Button) && (number != "") && (operation == "")){
            viewNumber.text = number
            mainEditPanel.text.clear()
        }
    }

    private fun validateNumber(textNumber:String):String{
        if(textNumber[0] == '.')
            return "0".plus(textNumber)
        if(textNumber[textNumber.length-1] == '.')
            return textNumber.plus('0')
        return textNumber
    }

    private fun addOperation(textOperation: String) {
        when {
            mainEditPanel.text.toString() == "" && operation == "" && viewNumber.text == "" -> {
                showError("Введите число перед выбором операции!")
            }
            mainEditPanel.text.toString() == "" && viewNumber.text == "" -> {
                operation = textOperation
            }
            operation == "" && viewNumber.text == "" -> {
                number1 = validateNumber(mainEditPanel.text.toString())
                viewNumber.text = number1
                operation = textOperation
                mainEditPanel.text.clear()
            }
            operation == "" && viewNumber.text != "" -> {
                number1 = validateNumber(viewNumber.text.toString())
                operation = textOperation
            }
            else -> {
                number2 = validateNumber(mainEditPanel.text.toString())
                calculateNumber()
                mainEditPanel.text.clear()
                operation = textOperation
            }
        }
    }


    private fun calculateNumber(){
        number1 = when(operation){
            "+" -> { (number1.toDouble() + number2.toDouble()).toString() }
            "-" -> { (number1.toDouble() - number2.toDouble()).toString() }
            "*" -> { (number1.toDouble() * number2.toDouble()).toString() }
            "/" -> {
                val result = if (number2.toDouble() == 0.0){
                    mainEditPanel.text.clear()
                    number1 = ""
                    number2 = ""
                    operation = ""
                    reload()
                    showError("Ошибка деления на 0!")
                    ""
                }
                else{
                    (number1.toDouble() / number2.toDouble()).toString()
                }
                result
            }
            else -> {
                showError("Что-то совсем сломалось, обращайтесь к разрабу")
                "0"
            }
        }
        number2 = ""
        viewNumber.text = number1
    }

    private fun showError(message:String){
        mainEditPanel.error = message
        mainEditPanel.requestFocus()
    }

    private fun reload(){
        mainEditPanel.text.clear()
        mainEditPanel.append(number1)
        viewOperation.text = operation
        viewNumber.text = number1
    }
}