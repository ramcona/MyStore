package id.rafli.mystore.utilities

import android.app.Activity
import id.rafli.mystore.helper.Config.DIRECTORY_IMAGE
import id.rafli.mystore.helper.Validasi
import id.rafli.mystore.mediapicker.Image.ImagePicker
import id.rafli.mystore.mediapicker.Video.VideoPicker

class Picker(var activity: Activity) {

    fun ambilGambarKamera(){
        if (Validasi().ijinKamera(activity)) {
            ImagePicker.Builder(activity)
                .mode(ImagePicker.Mode.CAMERA)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(DIRECTORY_IMAGE)
                .extension(ImagePicker.Extension.PNG)
                .scale(600, 600)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build()
        }
    }

    fun ambilGambarGaleri(){
        if (Validasi().ijinDokumen(activity)){
        ImagePicker.Builder(activity)
            .mode(ImagePicker.Mode.GALLERY)
            .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
            .directory(DIRECTORY_IMAGE)
            .extension(ImagePicker.Extension.PNG)
            .scale(600, 600)
            .allowMultipleImages(false)
            .enableDebuggingMode(true)
            .build()
            }
    }

    fun ambilGambarSemua(){
        if (Validasi().ijinDokumenAndCamera(activity)){
        ImagePicker.Builder(activity)
            .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
            .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
            .directory(DIRECTORY_IMAGE)
            .extension(ImagePicker.Extension.PNG)
            .scale(600, 600)
            .allowMultipleImages(false)
            .enableDebuggingMode(true)
            .build()

        }
    }

    fun ambilVideo(){
        if (Validasi().ijinDokumenAndCamera(activity)){
            VideoPicker.Builder(activity)
                .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
                .directory(DIRECTORY_IMAGE)
                .extension(VideoPicker.Extension.MP4)
                .enableDebuggingMode(Validasi().isDebug())
                .build()
        }

    }
}