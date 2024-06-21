package com.mobile.paozim.activities

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobile.paozim.R
import com.mobile.paozim.classes.CartStuff.CartInstance
import com.mobile.paozim.classes.OrderStuff.Order
import com.mobile.paozim.classes.OrderStuff.OrderItem
import com.mobile.paozim.classes.Responses.SimpleResponse
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.databinding.ActivityPaymentBinding
import com.mobile.paozim.retrofit.OrderAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CompletableFuture

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInformationViews()

        binding.btnPagar.setOnClickListener(){
            if(binding.btnPix.isChecked) {
                sendOrder()
                showDialogBox().thenAccept { it ->
                    Intent().apply { setResult(RESULT_OK, this) }
                    finish()
                }
            }
        }
        binding.ivBack.setOnClickListener(){
            finish()
        }
    }
    private fun setInformationViews(){
        binding.tvOrderName.text = UserInstance.Usuario.nome
        binding.tvOrderStreet.text = UserInstance.Usuario.endereco
        binding.tvOrderNumber.text = UserInstance.Usuario.numResidencia.toString()
        binding.tvOrderComplement.text = UserInstance.Usuario.complemento
        binding.tvOrderCity.text = UserInstance.Usuario.cidade
        binding.tvOrderState.text = UserInstance.Usuario.estado
        binding.tvOrderCep.text = UserInstance.Usuario.CEP
        binding.tvOrderNeighborhood.text = UserInstance.Usuario.bairro
        binding.tvOrderTotal.text = "%.2f".format(CartInstance.getSubtotal() + CartInstance.Carro.shippingPrice!!)
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
            Toast.makeText(this, "Código copiado para a área de transferência", Toast.LENGTH_SHORT).show()
            aux.complete(true)
            dialog.dismiss()
        }
        dialog.show()
        return aux
    }
    private fun sendOrder(){
        val aux : Int = 15
        var orderItemList = mutableListOf<OrderItem>()
        for(item in CartInstance.Carro.itens){
            orderItemList.add(OrderItem(item.id, item.qtd, item.price))
        }
        val order = Order(
            aux,
            CartInstance.Carro.storeID!!.toInt(),
            UserInstance.Usuario.email,
            "",
            "",
            "",
            CartInstance.getSubtotal(),
            CartInstance.Carro.shippingPrice!!,
            aux,
            orderItemList
        )

        val retIn = RetrofitInstance.getRetrofitInstance().create(OrderAPI::class.java)
        retIn.createOrder(order).enqueue(object : Callback<SimpleResponse> {
            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Toast.makeText(this@PaymentActivity,t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                Log.d("ORDER1", response.body().toString())
                CartInstance.clearCart(this@PaymentActivity)
                Toast.makeText(this@PaymentActivity,"Seu pedido chegará em breve", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
