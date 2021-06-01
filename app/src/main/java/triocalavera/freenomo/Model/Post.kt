package triocalavera.freenomo.Model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import java.time.format.DateTimeFormatter


@SuppressLint("ParcelCreator")
data class Post(val userName:String, val uuid:String, val titulo:String, val categoria:String, val foto:String, val descripcion:String, val numero:String, val precio:String, val fecha: String):
    Parcelable
   {
       override fun writeToParcel(dest: Parcel?, flags: Int) {

       }

       override fun describeContents(): Int {
           TODO("Not yet implemented")
       }
    }

