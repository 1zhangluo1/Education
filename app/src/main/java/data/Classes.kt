package data

import android.os.Parcel
import android.os.Parcelable

data class Classes(val courseName: String, val teacherName: String, val courseId: Long) :
    Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(courseName)
        dest.writeString(teacherName)
        dest.writeLong(courseId)
    }

    companion object CREATOR : Parcelable.Creator<Classes> {
        override fun createFromParcel(parcel: Parcel): Classes {
            return Classes(parcel)
        }

        override fun newArray(size: Int): Array<Classes?> {
            return arrayOfNulls(size)
        }
    }

    private constructor(parcel: Parcel) : this(
        courseName = parcel.readString() ?: "",
        teacherName = parcel.readString() ?: "",
        courseId = parcel.readLong()
    )

}