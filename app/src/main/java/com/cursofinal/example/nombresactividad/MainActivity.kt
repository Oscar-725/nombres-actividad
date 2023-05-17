package com.cursofinal.example.nombresactividad

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.cursofinal.example.nombresactividad.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val countries= arrayOf("Mexico","Francia","Argentina","Venezuela","Espana","Colombia")
        val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,countries)
        binding.actvpais.setAdapter(adaptador)

        binding.actvpais.setOnItemClickListener { adapterView, view, i, l ->

        }
        binding.etdate.setOnClickListener(){
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()
            picker.addOnPositiveButtonClickListener {
                var dateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }.format(it)
                    binding.etdate.setText(dateString)
            }
            picker.show(supportFragmentManager,picker.toString())

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.manu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_send){

            if (validFields()) {

            val name: String = findViewById<TextInputEditText>(R.id.Name).text.toString().trim()
            val surname = binding.Apellidos.text.toString().trim()
                val fechaDeCumple = binding.etdate.text.toString().trim()
                val estatura = binding.etheight.text.toString().trim()
                val pais = binding.actvpais.text.toString().trim()
                val lugarNacimiento = binding.tietlugarnacimiento.text.toString().trim()
                val notas = binding.tietnotas.text.toString().trim()

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.dialog_title))
            builder.setMessage(joinData(name,surname,fechaDeCumple,estatura,pais,lugarNacimiento,notas))
            builder.setPositiveButton(getString(R.string.dialog_ok), { dialogInterface, i ->
                with(binding){
                    Name.text?.clear()
                    Apellidos.text?.clear()
                    etdate.text?.clear()
                    etheight.text?.clear()
                    actvpais.text?.clear()
                    tietlugarnacimiento.text?.clear()
                    tietnotas.text?.clear()
                }
            })
                builder.setNegativeButton(getString(R.string.dialog_cancel),null)
                val dialog: AlertDialog= builder.create()
                dialog.show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun joinData (vararg fields: String): String {
        var result = ""

        fields.forEach { field ->
            if (field.isNotEmpty()) {
                result += "$field\n"
            }
        }
        return result
    }

    private fun validFields (): Boolean{
        var isValid = true

        if(binding.etheight.text.isNullOrEmpty()){

            binding.tilheight.run {
                error = context.getString(R.string.help_required)
                requestFocus()
            }
            isValid= false
        }
        else if (binding.etheight.text.toString().toInt()<50){

            binding.tilheight.run {
                error = context.getString(R.string.help_height_min)
                requestFocus() }
            isValid= false
        } else {
            binding.tilheight.error = null
        }

        if(binding.Name.text.isNullOrEmpty()){
            binding.Iname.run {
                error = context.getString(R.string.help_required)
                requestFocus()
            }
            isValid= false
        }else{
            binding.Iname.error = null
        }

        if(binding.Apellidos.text.isNullOrEmpty()){

            binding.Iapellidos.run {
                error = context.getString(R.string.help_required)
                requestFocus()
            }
            isValid= false
        }else{
            binding.Iapellidos.error = null
        }



        return isValid
    }
}