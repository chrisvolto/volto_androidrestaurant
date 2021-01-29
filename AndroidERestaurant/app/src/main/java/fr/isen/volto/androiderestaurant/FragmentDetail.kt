package fr.isen.volto.androiderestaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

class FragmentDetail: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val inflater = LayoutInflater.from(context)

        return inflater.inflate(R.layout.fragment_dish_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var imagePreview: ImageView = view.findViewById(R.id.photo)

        arguments?.getString("URL")?.let{
            Picasso.get().load(it).into(imagePreview)
        }
    }

    companion object {
        fun newInstance(picture: String): FragmentDetail{
            return FragmentDetail().apply {  arguments =  Bundle().apply { putString("URL", picture) } }
        }
    }

}
