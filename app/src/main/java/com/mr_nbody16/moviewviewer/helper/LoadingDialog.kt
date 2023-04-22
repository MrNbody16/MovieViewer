package com.mr_nbody16.moviewviewer.helper

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.mr_nbody16.moviewviewer.databinding.DialogLoadingBinding

class LoadingDialog private constructor(val context: Context) {

    private lateinit var alertDialog: AlertDialog
    private lateinit var msg: String
    private var _binding: DialogLoadingBinding? = null


    companion object {
        fun build(context: Context, msg: String): LoadingDialog {
            return LoadingDialog(context).also {
                it.msg = msg
                it._binding =
                    DialogLoadingBinding.inflate(LayoutInflater.from(context)).also { binding ->
                        binding.progressMessage.text = msg
                    }
                it.alertDialog = AlertDialog.Builder(context)
                    .setView(it?._binding?.root)
                    .setCancelable(false)
                    .create()
            }
        }
    }

    fun dismiss() {
        if (alertDialog.isShowing)
            alertDialog.dismiss()
    }

    fun show() {
        if (!alertDialog.isShowing)
            alertDialog.show()
    }

}