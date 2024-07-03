package com.mobile.paozim.activities

import com.mobile.paozim.classes.signatureStuff.Signature
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.paozim.R
import com.mobile.paozim.classes.SimpleResponse
import com.mobile.paozim.classes.userStuff.UserInstance
import com.mobile.paozim.databinding.ActivityPayment2Binding
import com.mobile.paozim.retrofit.RetrofitInstance
import com.mobile.paozim.retrofit.API.SignatureAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.CompletableFuture


class PaymentActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityPayment2Binding
    private var itemID: Int = 0
    private var qtd: Int = 0
    private var price: Double = 0.0
    private var frequency: String = "Diário"
    private var period: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayment2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        itemID = intent.getIntExtra("item", 0)
        qtd = intent.getIntExtra("qtd", 0)
        price = intent.getDoubleExtra("total", 0.0)
        Log.d("OQUE", "ItemID: $itemID, qtd: $qtd, price: $price")

        setInformationViews()
        setListeners()
    }

    private fun setInformationViews(){
        binding.tvSignatureName.text = UserInstance.Usuario.nome
        binding.tvSignatureStreet.text = UserInstance.Usuario.endereco
        binding.tvSignatureNumber.text = UserInstance.Usuario.numResidencia.toString()
        binding.tvSignatureComplement.text = UserInstance.Usuario.complemento
        binding.tvSignatureCity.text = UserInstance.Usuario.cidade
        binding.tvSignatureState.text = UserInstance.Usuario.estado
        binding.tvSignatureCep.text = UserInstance.Usuario.CEP
        binding.tvSignatureNeighborhood.text = UserInstance.Usuario.bairro
        binding.tvSignatureTotal.text = "%.2f".format(price)
        binding.editTextNumber.setText(period.toString())
    }

    private fun setListeners(){
        binding.ivBack.setOnClickListener(){
            finish()
        }
        binding.rdgrPeriods.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                binding.btnDiario.id -> {
                    binding.tvPeriods.text = "dias"
                    frequency = "Diário"
                }
                binding.btnSemanal.id -> {
                    binding.tvPeriods.text = "semanas"
                    frequency = "Semanal"
                }
                binding.btnMensal.id -> {
                    binding.tvPeriods.text = "meses"
                    frequency = "Mensal"
                }
            }
        }
        binding.editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()){
                    period = s.toString().toInt()
                    val total = price * period
                    binding.tvSignatureTotal.text = "%.2f".format(total)
                }
            }
        })

        DateInputMask(binding.editTextDate).listen()
        DateInputMask(binding.editTextTime, true).listen()

        binding.btnAssinar.setOnClickListener(){
            if(binding.btnPix.isChecked && period > 0 && binding.editTextDate.text.toString().length == 8 && binding.editTextTime.text.toString().length == 5) {
                val oldFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
                val newFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val oldDate = oldFormat.parse(binding.editTextDate.text.toString())
                val newDate = newFormat.format(oldDate!!)

                sendSignature(newDate)
                showDialogBox().thenAccept { it ->
                    finish()
                }
            }
        }
    }

    private fun sendSignature(date : String) {
        val aux: Int = 15
        val signature = Signature(
            aux,
            itemID,
            UserInstance.Usuario.email,
            price * period,
            qtd,
            "Ativo",
            binding.editTextTime.text.toString(),
            date,
            frequency,
            0,
            period
        )

        val retIn = RetrofitInstance.getRetrofitInstance().create(SignatureAPI::class.java)
        retIn.addSignature(signature).enqueue(object : Callback<SimpleResponse> {
            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Toast.makeText(this@PaymentActivity2, "Falha ao enviar assinatura", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                Log.d("SIG", response.body().toString())
                Toast.makeText(this@PaymentActivity2, "Assinatura enviada com sucesso", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDialogBox(): CompletableFuture<Boolean> {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_pix_payment)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val copyBtn : Button = dialog.findViewById(R.id.btn_copy_code)
        val code : TextView = dialog.findViewById(R.id.tv_pix_code)
        val aux = CompletableFuture<Boolean>()

        copyBtn.setOnClickListener{
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("PIX Code", code.text.toString())
            clipboard.setPrimaryClip(clip)
            aux.complete(true)
            dialog.dismiss()
        }
        dialog.show()
        return aux
    }
}
class DateInputMask(val input : EditText, val isTime : Boolean = false) {

    fun listen() {
        input.addTextChangedListener(mDateEntryWatcher)
    }

    private val mDateEntryWatcher = object : TextWatcher {

        var edited = false
        var dividerCharacter = if(isTime) ":" else "/"

        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (edited) {
                edited = false
                return
            }

            var working = getEditText()
            working = manageDateDivider(working, 2, start, before)
            if(!isTime) {
                working = manageDateDivider(working, 5, start, before)
            }

            edited = true
            input.setText(working)
            input.setSelection(input.text.length)
        }
        private fun manageDateDivider(working: String, position : Int, start: Int, before: Int) : String{
            if (working.length == position) {
                return if (before <= position && start < position)
                    working + dividerCharacter
                else
                    working.dropLast(1)
            }
            return working
        }
        private fun getEditText() : String {
            return if (!isTime && input.text.length >= 10)
                input.text.toString().substring(0,10)
            else if (isTime && input.text.length >= 5)
                input.text.toString().substring(0,5)
            else
                input.text.toString()
        }
    }
}
