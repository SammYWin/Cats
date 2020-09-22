package com.evantemplate.cats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evantemplate.cats.R
import com.evantemplate.cats.adapters.CatListAdapter
import com.evantemplate.cats.database.CatDatabase
import com.evantemplate.cats.interactors.Interactor
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.network.CatApi
import com.evantemplate.cats.presenters.CatListPresenter
import com.evantemplate.cats.repositories.DbCatsRepository
import com.evantemplate.cats.repositories.NetCatsRepository
import kotlinx.android.synthetic.main.fragment_cat_list.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class CatListFragment: MvpAppCompatFragment(), CatListView {

    @InjectPresenter
    lateinit var presenter: CatListPresenter
    lateinit var adapter: CatListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_cat_list, container, false)

        adapter = CatListAdapter(
            { isOnLastPosition -> if (isOnLastPosition) presenter.loadMoreCats() },
            { cat ->
//                if (!cat.isInFavorites) viewModel.addToFavorites(cat)
//                else viewModel.deleteFromFavorites(cat)
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

        rootView.rv_cats_all.adapter = adapter

        return rootView
    }

    override fun showAllCats(catList: List<Cat>) {
        adapter.updateData(catList)
    }

    override fun addToFavorite() {
        TODO("Not yet implemented")
    }

    @ProvidePresenter
    fun providePresenter(): CatListPresenter{
        val application = requireContext()
        val dataBase = CatDatabase.getInstance(application).catDao

        val netCatsRepository = NetCatsRepository(CatApi.retrofitService)
        val dbCatsRepository = DbCatsRepository(dataBase)

        val interactor = Interactor(netCatsRepository, dbCatsRepository)

        return CatListPresenter(interactor)
    }
}