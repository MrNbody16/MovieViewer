package com.mr_nbody16.moviewviewer.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.mr_nbody16.moviewviewer.R
import com.mr_nbody16.moviewviewer.databinding.CompyDetailItemModelBinding
import com.mr_nbody16.moviewviewer.models.CompanyResponse
import com.mr_nbody16.moviewviewer.models.ConfigurationResponse
import com.mr_nbody16.moviewviewer.models.GenresResponse
import com.mr_nbody16.moviewviewer.models.ProductionCompany

class CompaniesAdapter(private val context: Context) :
    RecyclerView.Adapter<CompaniesAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: CompyDetailItemModelBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var details: List<ProductionCompany?>? = null
    private var configuration: ConfigurationResponse? = null
    private var loadingIndicator: CircularProgressDrawable =
        CircularProgressDrawable(context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }

    fun setDetails(details: List<ProductionCompany?>?) {
        this.details = details
        notifyDataSetChanged()
    }

    fun setConfig(config: ConfigurationResponse?) {
        configuration = config
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CompyDetailItemModelBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = details?.size ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(details?.get(position)) {
            holder.binding.also { binding ->
                Glide.with(context)
                    .load(
                        configuration?.imagesConfig?.base_url +
                                configuration?.imagesConfig?.logo_sizes?.get(4) + this?.logo_path
                    ).placeholder(loadingIndicator)
                    .error(R.drawable.warning_amber)
                    .into(binding.companyImage)
                binding.companyName.text = this?.name?:""
            }
        }
    }

}