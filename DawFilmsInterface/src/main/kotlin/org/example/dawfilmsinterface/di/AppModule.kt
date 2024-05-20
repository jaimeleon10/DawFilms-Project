package org.example.dawfilmsinterface.di


import org.example.dawfilmsinterface.clientes.repositories.ClienteRepository
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepositoryImpl
import org.example.dawfilmsinterface.clientes.cache.ClienteCache
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.example.dawfilmsinterface.clientes.services.ClienteServiceImpl
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.example.dawfilmsinterface.productos.repositories.butacas.ButacaRepository
import org.example.dawfilmsinterface.productos.repositories.butacas.ButacaRepositoryImpl
import org.example.dawfilmsinterface.productos.repositories.complementos.ComplementoRepositoryImpl
import org.example.dawfilmsinterface.productos.repositories.complementos.ComplementoRepository
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.productos.service.ProductoServiceImpl
import org.example.dawfilmsinterface.productos.cache.ProductosCache
import org.example.dawfilmsinterface.productos.storage.storageCsv.StorageCsv
import org.example.dawfilmsinterface.productos.storage.storageCsv.StorageCsvImpl
import org.example.dawfilmsinterface.productos.storage.storageImage.StorageImage
import org.example.dawfilmsinterface.productos.storage.storageImage.StorageImageImpl
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJson
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJsonImpl
import org.example.dawfilmsinterface.productos.storage.storageXml.StorageXml
import org.example.dawfilmsinterface.productos.storage.storageXml.StorageXmlImpl
import org.example.dawfilmsinterface.ventas.repositories.VentaRepository
import org.example.dawfilmsinterface.ventas.repositories.VentaRepositoryImpl
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.example.dawfilmsinterface.ventas.services.VentaServiceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
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

    singleOf(::ClienteCache)


    //Productos
    singleOf(::ButacaRepositoryImpl) {
        bind<ButacaRepository>()
    }

    singleOf(::StorageCsvImpl) {
        bind<StorageCsv>()
    }

    singleOf(::StorageImageImpl) {
        bind<StorageImage>()
    }

    singleOf(::StorageJsonImpl) {
        bind<StorageJson>()
    }

    singleOf(::StorageXmlImpl) {
        bind<StorageXml>()
    }

    //Complemento
    singleOf(::ComplementoRepositoryImpl) {
        bind<ComplementoRepository>()
    }

    //Productos
    singleOf(::ProductoServiceImpl) {
        bind<ProductoService>()
    }

    singleOf(::ProductosCache)


    //Ventas
    singleOf(::VentaRepositoryImpl) {
        bind<VentaRepository>()
    }

    singleOf(::VentaServiceImpl) {
        bind<VentaService>()
    }
}