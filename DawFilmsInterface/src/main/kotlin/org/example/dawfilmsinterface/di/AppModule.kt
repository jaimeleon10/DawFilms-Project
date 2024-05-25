package org.example.dawfilmsinterface.di


import org.example.dawfilmsinterface.cache.Cache
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepository
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepositoryImpl
import org.example.dawfilmsinterface.clientes.cache.ClienteCache
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.validators.ClienteValidator
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.example.dawfilmsinterface.clientes.services.ClienteServiceImpl
import org.example.dawfilmsinterface.clientes.storage.ClienteStorage
import org.example.dawfilmsinterface.clientes.storage.ClienteStorageImpl
import org.example.dawfilmsinterface.cine.viewModels.LoginViewModel
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.example.dawfilmsinterface.productos.repositories.butacas.ButacaRepository
import org.example.dawfilmsinterface.productos.repositories.butacas.ButacaRepositoryImpl
import org.example.dawfilmsinterface.productos.repositories.complementos.ComplementoRepositoryImpl
import org.example.dawfilmsinterface.productos.repositories.complementos.ComplementoRepository
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.productos.service.ProductoServiceImpl
import org.example.dawfilmsinterface.productos.cache.ProductosCache
import org.example.dawfilmsinterface.productos.storage.genericStorage.ProductosStorage
import org.example.dawfilmsinterface.productos.storage.genericStorage.ProductosStorageImpl
import org.example.dawfilmsinterface.productos.storage.storageCsv.StorageCsv
import org.example.dawfilmsinterface.productos.storage.storageCsv.StorageCsvImpl
import org.example.dawfilmsinterface.productos.storage.storageImage.StorageImage
import org.example.dawfilmsinterface.productos.storage.storageImage.StorageImageImpl
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJson
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJsonImpl
import org.example.dawfilmsinterface.productos.storage.storageXml.StorageXml
import org.example.dawfilmsinterface.productos.storage.storageXml.StorageXmlImpl
import org.example.dawfilmsinterface.productos.storage.storageZip.StorageZip
import org.example.dawfilmsinterface.productos.storage.storageZip.StorageZipImpl
import org.example.dawfilmsinterface.productos.validators.ButacaValidator
import org.example.dawfilmsinterface.productos.validators.ComplementoValidator
import org.example.dawfilmsinterface.productos.viewmodels.ActualizarButacaViewModel
import org.example.dawfilmsinterface.productos.viewmodels.GestionComplementosViewModel
import org.example.dawfilmsinterface.ventas.repositories.VentaRepository
import org.example.dawfilmsinterface.ventas.repositories.VentaRepositoryImpl
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.example.dawfilmsinterface.ventas.services.VentaServiceImpl
import org.example.dawfilmsinterface.ventas.storage.VentaStorage
import org.example.dawfilmsinterface.ventas.storage.VentaStorageImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::Config)

    singleOf(::SqlDeLightManager)


    //Cliente
    singleOf(::ClienteRepositoryImpl) {
        bind<ClienteRepository>()
    }

    singleOf(::ClienteServiceImpl) {
        bind<ClienteService>()
    }

    /*singleOf(::ClienteCache) {
        bind<Cache<Long, Cliente>>()
    }*/

    single { ClienteCache (Config().cacheSize) } bind Cache::class

    singleOf(::ClienteValidator)

    singleOf(::ClienteStorageImpl) {
        bind<ClienteStorage>()
    }


    //Productos
    singleOf(::ButacaRepositoryImpl) {
        bind<ButacaRepository>()
    }

    singleOf(::ComplementoRepositoryImpl) {
        bind<ComplementoRepository>()
    }

    single {
        ProductoServiceImpl(get(), get(), get(), get(), get())
    } bind ProductoService::class

    single { ProductosCache(Config().cacheSize) } bind Cache::class

    singleOf(::StorageCsvImpl) {
        bind<StorageCsv>()
    }

    singleOf(:: ButacaValidator)

    singleOf(::ComplementoValidator)

    single {
        ProductosStorageImpl(get(), get(), get(), get(), get(), get())
    } bind ProductosStorage::class

    singleOf(::StorageImageImpl) {
        bind<StorageImage>()
    }

    singleOf(::StorageJsonImpl) {
        bind<StorageJson>()
    }

    singleOf(::StorageXmlImpl) {
        bind<StorageXml>()
    }

    single { StorageZipImpl(get(), get()) } bind StorageZip::class


    //Ventas
    singleOf(::VentaRepositoryImpl) {
        bind<VentaRepository>()
    }

    singleOf(::VentaServiceImpl) {
        bind<VentaService>()
    }

    singleOf(::VentaStorageImpl) {
        bind<VentaStorage>()
    }

    singleOf(::ActualizarButacaViewModel)

    singleOf(::GestionComplementosViewModel)

    singleOf(::LoginViewModel)
}