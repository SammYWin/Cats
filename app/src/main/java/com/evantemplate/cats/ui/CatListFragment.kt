package com.evantemplate.cats.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.evantemplate.cats.R
import com.evantemplate.cats.adapters.CatListAdapter
import com.evantemplate.cats.database.CatDatabase
import com.evantemplate.cats.databinding.FragmentCatListBinding
import com.evantemplate.cats.network.ImgCatApi
import com.evantemplate.cats.viewmodels.CatListViewModel
import com.evantemplate.cats.viewmodels.CatListViewModelFactory
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.*
import java.nio.file.Files
import java.nio.file.Path


class CatListFragment: Fragment() {

    private lateinit var binding: FragmentCatListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatListBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val dataBase = CatDatabase.getInstance(application).catDao

        val viewModelFactory = CatListViewModelFactory(dataBase)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(CatListViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel

        binding.rvCatsAll.adapter = CatListAdapter(
            { isOnLastPosition -> if (isOnLastPosition) viewModel.loadCats() },
            { cat ->
                if (!cat.isInFavorites) viewModel.addToFavorites(cat)
                else viewModel.deleteFromFavorites(cat)
/*                ImgCatApi.retrofitService.downloadImg()
                    .enqueue(object : retrofit2.Callback<ResponseBody> {
                        override fun onResponse(call: retrofit2.Call<ResponseBody>, response: Response<ResponseBody>
                        ) {
                            val path = requireContext().getExternalFilesDir(null)
                            val file = File(path,"file_name.jpg")

                            val bytes = response.body()!!.bytes()
                            val image = requireContext().getDrawable(R.drawable.test)!!.toBitmap()
                                //BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                            val outputStream: OutputStream = FileOutputStream(file)
                            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

//                            val inputStream = response.body()!!.byteStream()
//
//                            val fileReader = ByteArray(4096)
//
//                            while (true) {
//                                val read = inputStream.read(fileReader)
//                                if (read == -1) {
//                                    Log.d("M_CatListFragment", "IMAGE DOWNLOADED")
//                                    break
//                                }
//                                outputStream.write(fileReader, 0, read)
//                            }

                            outputStream.flush();
                            outputStream.close()

//                            try {
//                                val apkFile: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!
//                                val file = File(apkFile,"file_name.jpg")
//
//                                var inputStream: InputStream? = null
//                                var outputStream: OutputStream? = null
//
//
//                                try {
//                                    inputStream = response.body()!!.byteStream()
//                                    outputStream = FileOutputStream(file)
//
//                                    while (true) {
//                                        val read = inputStream!!.read(fileReader)
//                                        if (read == -1) {
//                                            Log.d("M_CatListFragment", "IMAGE DOWNLOADED")
//                                            break
//                                        }
//                                        outputStream.write(fileReader, 0, read)
//                                        outputStream.flush()
//                                    }
//                                }catch (e: IOException){
//                                    Log.d("M_CatListFragment", "ERROR DOWNLOAD IMAGE: $e")
//                                } finally {
//                                    if (inputStream != null) {
//                                        inputStream.close();
//
//                                    }
//
//                                    if (outputStream != null) {
//                                        outputStream.close();
//                                    }
//                                }
//                            } catch (ex: Exception) {  Log.d("M_CatListFragment", "ERROR DOWNLOAD IMAGE: $ex")}
                        }

                        override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                        }
                    })*/
            }
        )

        return binding.root
    }
}