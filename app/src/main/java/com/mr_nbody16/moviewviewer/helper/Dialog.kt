package com.mr_nbody16.moviewviewer.helper

import android.app.AlertDialog
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import com.mr_nbody16.moviewviewer.databinding.DialogLayoutBinding

class Dialog private constructor(context: Context) {

    class BtnRequirements private constructor(
        val btnMsg: String,
        val clickListener: OnClickListener
    ) {
        companion object {
            fun get(btnMsg: String, clickListener: OnClickListener) =
                BtnRequirements(btnMsg, clickListener)
        }
    }


    private lateinit var msg: String
    private lateinit var alertDialog: AlertDialog
    private var _binding: DialogLayoutBinding? = null


    companion object {
        /**
         * This method creates a dialog with just a simple ok Button
         * */
        fun build(
            context: Context,
            contentMsg: String,
            buttonRequirements: BtnRequirements
        ): Dialog {
            return Dialog(context).also {
                it.msg = contentMsg
                it._binding =
                    DialogLayoutBinding.inflate(LayoutInflater.from(context)).also { binding ->
                        binding.dialogMessage.text = contentMsg
                        binding.dialogNegativeBtn.run {
                            text = buttonRequirements.btnMsg
                            setOnClickListener(buttonRequirements.clickListener)
                        }
                        binding.dialogPositiveBtn.visibility = View.GONE
                    }
                it.alertDialog = AlertDialog.Builder(context)
                    .setView(it._binding?.root)
                    .create()
            }
        }

        fun build(
            context: Context,
            contentMsg: String,
            positiveBtnRequirements: BtnRequirements,
            negativeButtonRequirements: BtnRequirements
        ): Dialog {
            return Dialog(context).also {
                it.msg = contentMsg
                it._binding =
                    DialogLayoutBinding.inflate(LayoutInflater.from(context)).also { binding ->
                        binding.dialogMessage.text = contentMsg
                        binding.dialogNegativeBtn.run {
                            text = negativeButtonRequirements.btnMsg
                            setOnClickListener(negativeButtonRequirements.clickListener)
                        }
                        binding.dialogPositiveBtn.run {
                            visibility = View.VISIBLE
                            text = positiveBtnRequirements.btnMsg
                            setOnClickListener(positiveBtnRequirements.clickListener)
                        }
                    }
                it.alertDialog = AlertDialog.Builder(context)
                    .setView(it._binding?.root)
                    .setCancelable(false)
                    .create()
            }
        }
    }


    fun show() {
        if (!alertDialog.isShowing)
            alertDialog.show()
    }

    fun dismiss() {
        if (alertDialog.isShowing)
            alertDialog.dismiss()
    }

    fun setCancelable(value : Boolean) {
        alertDialog.setCancelable(value)
    }


}