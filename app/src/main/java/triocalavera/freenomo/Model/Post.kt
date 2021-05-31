package triocalavera.freenomo.Model

import android.os.Parcel
import android.os.Parcelable


data class Post(val uuid:String, val titulo:String,val categoria:String,val foto:String,val descripcion:String, val numero:String,val precio:String  ):
    Parcelable
   {
       override fun writeToParcel(dest: Parcel?, flags: Int) {
           TODO("Not yet implemented")
       }

       override fun describeContents(): Int {
           TODO("Not yet implemented")
       }
    }

