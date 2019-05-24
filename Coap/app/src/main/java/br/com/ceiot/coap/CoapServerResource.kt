package br.com.ceiot.coap

import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.server.resources.CoapExchange

/**
 * TODO (4) Implementação do recurso do Service
 */
class CoapServerResource(name: String) : CoapResource(name) {

    override fun handleGET(exchange: CoapExchange) {
        // respond to the request
        exchange.respond("Olá CEIOT")
    }
}