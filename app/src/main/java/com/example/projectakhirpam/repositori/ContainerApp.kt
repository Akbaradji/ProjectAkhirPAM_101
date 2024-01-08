package com.example.projectakhirpam.repositori

import android.content.Context
import com.example.projectakhirpam.data.Databasepembeli

interface ContainerApp{
    val repositoriPembeli:RepositoriPembeli
}
class ContainerDataApp(private val context: Context): ContainerApp{
    override val repositoriPembeli: RepositoriPembeli by lazy {
        OfflineRepositoriPembeli(Databasepembeli.getDatabase(context).pembeliDao())
    }
}