package org.example.dawfilmsinterface.di


import org.example.dawfilmsinterface.cache.Cache
import org.example.dawfilmsinterface.cine.services.storage.CineStorageZip
import org.example.dawfilmsinterface.cine.services.storage.CineStorageZipImpl
import org.example.dawfilmsinterface.cine.services.storageHtml.StorageHtmlRecaudacion
import org.example.dawfilmsinterface.cine.services.storageHtml.StorageHtmlRecaudacionImpl
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepository
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepositoryImpl
import org.example.dawfilmsinterface.clientes.cache.ClienteCache
import org.example.dawfilmsinterface.clientes.validators.ClienteValidator
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.example.dawfilmsinterface.clientes.services.ClienteServiceImpl
import org.example.dawfilmsinterface.clientes.storage.ClienteStorage
import org.example.dawfilmsinterface.clientes.storage.ClienteStorageImpl
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
import org.example.dawfilmsinterface.productos.validators.ButacaValidator
import org.example.dawfilmsinterface.productos.validators.ComplementoValidator
import org.example.dawfilmsinterface.cine.viewmodels.GestionButacaViewModel
import org.example.dawfilmsinterface.cine.viewmodels.GestionComplementosViewModel
import org.example.dawfilmsinterface.cine.viewmodels.SeleccionarButacaViewModel
import org.example.dawfilmsinterface.cine.viewmodels.SeleccionarComplementoViewModel
import org.example.dawfilmsinterface.cine.viewmodels.CarritoViewModel
import org.example.dawfilmsinterface.cine.viewmodels.ConfirmarCompraViewModel
import org.example.dawfilmsinterface.cine.viewmodels.ListadoComplementosViewModel
import org.example.dawfilmsinterface.cine.viewmodels.EstadoCineViewModel
import org.example.dawfilmsinterface.cine.viewmodels.ObtenerRecaudacionViewModel
import org.example.dawfilmsinterface.cine.viewmodels.MenuAdminViewModel
import org.example.dawfilmsinterface.cine.viewmodels.RecuperarPasswordViewModel
import org.example.dawfilmsinterface.cine.viewmodels.RegistroViewModel
import org.example.dawfilmsinterface.ventas.repositories.VentaRepository
import org.example.dawfilmsinterface.ventas.repositories.VentaRepositoryImpl
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.example.dawfilmsinterface.ventas.services.VentaServiceImpl
import org.example.dawfilmsinterface.ventas.storage.storageHtml.StorageHtml
import org.example.dawfilmsinterface.ventas.storage.storageHtml.StorageHtmlImpl
import org.example.dawfilmsinterface.ventas.storage.storageVenta.VentaStorage
import org.example.dawfilmsinterface.ventas.storage.storageVenta.VentaStorageImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::Config)

    singleOf(::SqlDeLightManager)

    // Cine

    singleOf(::CineStorageZipImpl) {
        bind<CineStorageZip>()
    }

    singleOf(::StorageHtmlRecaudacionImpl) {
        bind<StorageHtmlRecaudacion>()
    }

    // Cliente
    singleOf(::ClienteRepositoryImpl) {
        bind<ClienteRepository>()
    }

    singleOf(::ClienteServiceImpl) {
        bind<ClienteService>()
    }

    single { ClienteCache (Config().cacheSize) } bind Cache::class

    singleOf(::ClienteValidator)

    singleOf(::ClienteStorageImpl) {
        bind<ClienteStorage>()
    }


    // Productos
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

    singleOf(::ButacaValidator)

    singleOf(::ComplementoValidator)

    single {
        ProductosStorageImpl(get(), get(), get(), get(), get())
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

    // Ventas
    singleOf(::VentaRepositoryImpl) {
        bind<VentaRepository>()
    }

    singleOf(::VentaServiceImpl) {
        bind<VentaService>()
    }

    singleOf(::VentaStorageImpl) {
        bind<VentaStorage>()
    }

    singleOf(::StorageHtmlImpl) {
        bind<StorageHtml>()
    }

    // ViewModels
    singleOf(::RecuperarPasswordViewModel)

    singleOf(::GestionButacaViewModel)

    singleOf(::GestionComplementosViewModel)

    singleOf(::LoginViewModel)

    singleOf(::SeleccionarButacaViewModel)

    singleOf(::SeleccionarComplementoViewModel)

    singleOf(::CarritoViewModel)

    singleOf(::ConfirmarCompraViewModel)

    singleOf(::ObtenerRecaudacionViewModel)

    singleOf(::ListadoComplementosViewModel)

    singleOf(::EstadoCineViewModel)

    singleOf(::MenuAdminViewModel)

    singleOf(::RegistroViewModel)
}